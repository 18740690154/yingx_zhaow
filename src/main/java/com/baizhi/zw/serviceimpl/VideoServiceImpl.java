package com.baizhi.zw.serviceimpl;

import com.baizhi.zw.annotation.AddCaChe;
import com.baizhi.zw.annotation.AddLog;
import com.baizhi.zw.annotation.DelCaChe;
import com.baizhi.zw.dao.VideoDAO;

import com.baizhi.zw.entity.Video;
import com.baizhi.zw.entity.VideoExample;
import com.baizhi.zw.po.DetailVideoPo;
import com.baizhi.zw.po.LikeVideoPo;
import com.baizhi.zw.po.VideoPo;

import com.baizhi.zw.repository.VideoRepository;
import com.baizhi.zw.service.VideoService;
import com.baizhi.zw.util.AliyunOssUtil;

import com.baizhi.zw.util.InterceptVideoPhotoUtil;
import com.baizhi.zw.util.UUIDUtil;
import com.baizhi.zw.vo.DetailVideoVo;
import com.baizhi.zw.vo.LikeVideoVo;
import com.baizhi.zw.vo.VideoVo;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoDAO videoDAO;
    @Autowired
    HttpSession httpSession;
    @Autowired
    VideoService videoService;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @AddCaChe
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(Integer page, Integer rows) {

        Map<String, Object> map = new HashMap<>();
        //总条数:records
        VideoExample videoExample = new VideoExample();
        int records = videoDAO.selectCountByExample(videoExample);
        map.put("records", records);
        //总页数:total  总条数/每页展示的条数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //当前页:page
        map.put("page", page);
        //数据:rows
        //参数:从第几条数据展示,每页展示几条数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Video> videos = videoDAO.selectByRowBounds(new Video(), rowBounds);
        map.put("rows", videos);

        return map;
    }

    @DelCaChe
    @AddLog(value = "添加视频")
    @Override
    public String add(Video video) {
        String uuid = UUIDUtil.getUUID();
        video.setId(uuid);
        video.setPublishDate(new Date());
        video.setCategoryId("1");
        video.setUserId("1");
        video.setGroupId("1");

        //调用添加方法，向数据库加入数据
        videoDAO.insertSelective(video);

        return uuid;
    }

    @DelCaChe
    @AddLog(value = "修改视频")
    @Override
    public void update(Video video) {
        videoDAO.updateByPrimaryKeySelective(video);
    }


    @DelCaChe
    @AddLog(value = "删除视频")
    @Override
    public Map<String, Object> delete(Video video) {
        Map<String, Object> map = new HashMap<>();
        try {

            //根据id查询视频数据
            VideoExample videoExample = new VideoExample();
            videoExample.createCriteria().andIdEqualTo(video.getId());
            Video video1 = videoDAO.selectOneByExample(videoExample);

            //获取文件的视频路径和封面路径,并拆分文件的视频路径和封面路径
            String[] videoSplit = video1.getVideoPath().split("/");
            String[] coverSplit = video1.getCoverPath().split("/");

            //字符串拼接
            String videoName = videoSplit[videoSplit.length - 2] + "/" + videoSplit[videoSplit.length - 1];
            String coverName = coverSplit[coverSplit.length - 2] + "/" + coverSplit[coverSplit.length - 1];

            System.out.println("coverName = " + coverName);
            System.out.println("videoName = " + videoName);

            //删除数据
            videoDAO.deleteByExample(videoExample);
            //删除视频
            AliyunOssUtil.deleteFile(videoName);
            //删除封面
            AliyunOssUtil.deleteFile(coverName);
            //删除es中索引
            videoRepository.delete(video);


            /*删除上传至本地的视频
            //根据绝对路径获取相对路径
            String realPath = httpSession.getServletContext().getRealPath("/upload/video");
            //获取视频完整路径
            String fileName = realPath + "/" + video1.getVideoPath();

            File file = new File(fileName);
            //判断是否是一个文件，并且存在
            if (file.isFile() && file.exists()) {
                //删除视频
                boolean delete = file.delete();
                System.out.println(delete);
            }
            */

            map.put("message", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "删除失败");
        }
        return map;
    }

    @Override  //上传至本地
    public void uploadVideo(MultipartFile videoPath, String id, HttpSession httpSession) {
        //根据相对路径获取绝对路径
        String realPath = httpSession.getServletContext().getRealPath("/upload/video");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        //获取文件名
        String filename = videoPath.getOriginalFilename();

        String newName = new Date().getTime() + "-" + filename;

        try {
            //文件上传
            videoPath.transferTo(new File(realPath, newName));

            //视频路径和图片路径的修改
            VideoExample videoExample = new VideoExample();
            videoExample.createCriteria().andIdEqualTo(id);
            Video video = new Video();

            video.setVideoPath(newName);
            video.setCoverPath(newName);

            //调用修改方法
            videoDAO.updateByExampleSelective(video, videoExample);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override //上传至阿里云------第一种方法
    public void uploadVideoAliyun(MultipartFile videoPath, String id, HttpSession httpSession) {
        //获取文件名
        String fileName = videoPath.getOriginalFilename();
        String newName = new Date().getTime() + "-" + fileName;

        //视频上传至阿里云
        AliyunOssUtil.uploadFileBytes(videoPath, "video/" + newName);

        //截取视频第一帧做封面
        //频接视频完整路径
        String videoFileName = "https://yingx-zw.oss-cn-beijing.aliyuncs.com/video/" + newName;

        String realPath = httpSession.getServletContext().getRealPath("/upload/cover");

        File file = new File(realPath);
        if (!file.exists()) file.mkdirs();
        //拆分视频路径
        String[] split = newName.split("\\.");
        //拼接封面路径
        String coverName = split[0] + ".jpg";
        //拼接视频封面的本地路径
        String coverPath = realPath + "\\" + coverName;

        //截取封面并保存到本地
        try {
            InterceptVideoPhotoUtil.fetchFrame(videoFileName, coverPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //将封面图片上传至阿里云
        AliyunOssUtil.uploadFileLocal("cover/" + coverName, coverPath);

        //删除本地文件
        File coverFile = new File(coverPath);
        if (coverFile.isFile() && coverFile.exists()) {
            boolean delete = coverFile.delete();
            System.out.println("delete = " + delete);
        }

        //修改视频路径和封面路径
        //修改条件
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andIdEqualTo(id);
        //修改结果
        Video video = new Video();
        video.setVideoPath("https://yingx-zw.oss-cn-beijing.aliyuncs.com/video/" + newName);
        video.setCoverPath("https://yingx-zw.oss-cn-beijing.aliyuncs.com/cover/" + coverName);
        videoDAO.updateByExampleSelective(video, videoExample);
    }

    @Override //上传至阿里云----第二种方法
    public void uploadVideoAliyuns(MultipartFile videoPath, String id, HttpSession httpSession) {
        //获取文件名
        String fileName = videoPath.getOriginalFilename();
        String newName = new Date().getTime() + "-" + fileName;

        //视频上传至阿里云
        AliyunOssUtil.uploadFileBytes(videoPath, "video/" + newName);

        //拆分视频路径
        String[] split = newName.split("\\.");
        //拼接封面路径
        String coverName = split[0] + ".jpg";

        //截取视频第一帧做封面
        AliyunOssUtil.interceptVideo("video/" + newName, "cover/"+coverName);

        //修改视频路径和封面路径
        //修改条件
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andIdEqualTo(id);
        //修改结果
        Video video = new Video();
        video.setVideoPath("https://yingx-zw.oss-cn-beijing.aliyuncs.com/video/" + newName);
        video.setCoverPath("https://yingx-zw.oss-cn-beijing.aliyuncs.com/cover/" + coverName);
        videoDAO.updateByExampleSelective(video, videoExample);
        video.setId(id);
        Video video1 = videoDAO.selectOne(video);
        //向es中添加索引
        videoRepository.save(video1);
    }

    @AddCaChe
    @Override
    public List<VideoVo> queryByReleaseTime() {

        ArrayList<VideoVo> videoVos = new ArrayList<>();

        List<VideoPo> videoPos = videoDAO.queryByReleaseTime();
        for (VideoPo videoPo : videoPos) {
            //根据视频ID查询视频的点赞数

            //为videoVo赋值
            VideoVo videoVo = new VideoVo(videoPo.getId(),
                    videoPo.getVTitle(),
                    videoPo.getVBrief(),
                    videoPo.getVVideoPath(),
                    videoPo.getVCoverPath(),
                    videoPo.getVPublishDate(),
                    videoPo.getCateName(),
                    videoPo.getHeadImg(),
                    20
            );
            videoVos.add(videoVo);
        }

        return videoVos;
    }

    @AddCaChe
    @Override
    public List<LikeVideoVo> queryByLikeVideoName(String content) {
        ArrayList<LikeVideoVo> likeVideoVos = new ArrayList<>();

        List<LikeVideoPo> likeVideoPos = videoDAO.queryByLikeVideoName(content);
        for (LikeVideoPo likeVideoPo : likeVideoPos) {
            //根据视频ID查询视频的点赞数

            //为likeVideoVo赋值
            LikeVideoVo likeVideoVo = new LikeVideoVo(likeVideoPo.getId(),
                    likeVideoPo.getVTitle(),
                    likeVideoPo.getVCoverPath(),
                    likeVideoPo.getVVideoPath(),
                    likeVideoPo.getVPublishDate(),
                    likeVideoPo.getVBrief(),
                    35,
                    likeVideoPo.getCateName(),
                    likeVideoPo.getCId(),
                    likeVideoPo.getUId(),
                    likeVideoPo.getUUserName()
            );
            likeVideoVos.add(likeVideoVo);
        }

        return likeVideoVos;
    }

    @AddCaChe
    @Override
    public DetailVideoVo queryByVideoDetail(String videoId) {
        DetailVideoPo detailVideoPo = videoDAO.queryByVideoDetail(videoId);

        List<LikeVideoVo> likeVideoVos = videoService.queryByVideo(videoId,detailVideoPo.getCId(), detailVideoPo.getUId());

        DetailVideoVo detailVideoVo = new DetailVideoVo(detailVideoPo.getId(),
                detailVideoPo.getVTitle(),
                detailVideoPo.getVVideoPath(),
                detailVideoPo.getVCoverPath(),
                detailVideoPo.getVPublishDate(),
                detailVideoPo.getVBrief(),25,36,true,
                detailVideoPo.getCId(),
                detailVideoPo.getCateName(),
                detailVideoPo.getUId(),
                detailVideoPo.getHeadImg(),
                detailVideoPo.getUUserName(),
                likeVideoVos
        );
        return detailVideoVo;
    }

    @AddCaChe
    @Override
    public List<LikeVideoVo> queryByVideo(String videoId,String cateId, String userId) {
        ArrayList<LikeVideoVo> likeVideoVos = new ArrayList<>();

        List<LikeVideoPo> likeVideoPos = videoDAO.queryByVideo(videoId,cateId, userId);
        for (LikeVideoPo likeVideoPo : likeVideoPos) {
            //根据视频ID查询视频的点赞数

            //为likeVideoVo赋值
            LikeVideoVo likeVideoVo = new LikeVideoVo(likeVideoPo.getId(),
                    likeVideoPo.getVTitle(),
                    likeVideoPo.getVCoverPath(),
                    likeVideoPo.getVVideoPath(),
                    likeVideoPo.getVPublishDate(),
                    likeVideoPo.getVBrief(),
                    35,
                    likeVideoPo.getCateName(),
                    likeVideoPo.getCId(),
                    likeVideoPo.getUId(),
                    likeVideoPo.getUUserName()
            );
            likeVideoVos.add(likeVideoVo);
        }
        return likeVideoVos;
    }


    //根据类别id查询视频
    @AddCaChe
    @Override
    public List<LikeVideoVo> queryCateVideoList(String cateId) {
        ArrayList<LikeVideoVo> likeVideoVos = new ArrayList<>();

        List<LikeVideoPo> likeVideoPos = videoDAO.queryCateVideoList(cateId);
        for (LikeVideoPo likeVideoPo : likeVideoPos) {
            //根据视频ID查询视频的点赞数

            //为likeVideoVo赋值
            LikeVideoVo likeVideoVo = new LikeVideoVo(likeVideoPo.getId(),
                    likeVideoPo.getVTitle(),
                    likeVideoPo.getVCoverPath(),
                    likeVideoPo.getVVideoPath(),
                    likeVideoPo.getVPublishDate(),
                    likeVideoPo.getVBrief(),
                    35,
                    likeVideoPo.getCateName(),
                    likeVideoPo.getCId(),
                    likeVideoPo.getUId(),
                    likeVideoPo.getUUserName()
            );
            likeVideoVos.add(likeVideoVo);
        }

        return likeVideoVos;
    }

    @Override
    public List<Video> querySearchByPage(String content,Integer page,Integer rows) {
        //处理高亮的操作
        HighlightBuilder.Field field = new HighlightBuilder
                .Field("*")
                .preTags("<span style='color:red'>")
                .postTags("<span>")
                .requireFieldMatch(false);

        //查询条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingx")
                .withTypes("video")
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))
                .withPageable(PageRequest.of(page,rows))
                .build();

        AggregatedPage<Video> videos = elasticsearchTemplate.queryForPage(nativeSearchQuery, Video.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHit[] hits = searchResponse.getHits().getHits();

                List<Video> videoList = new ArrayList<>();
                for (SearchHit hit : hits) {
                    //获得源数据的集合
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    //获取高亮数据的集合
                    Map<String, HighlightField> highlightMap = hit.getHighlightFields();

                    //封装源数据的视频对象
                    Video video = new Video();
                    //为视频对象赋值
                    if (sourceAsMap.get("id") != null) {
                        video.setId(sourceAsMap.get("id").toString());
                    }
                    if (sourceAsMap.get("title") != null) {
                        video.setTitle(sourceAsMap.get("title").toString());
                    }
                    if (sourceAsMap.get("brief") != null) {
                        video.setBrief(sourceAsMap.get("brief").toString());
                    }
                    if (sourceAsMap.get("videoPath") != null) {
                        video.setVideoPath(sourceAsMap.get("videoPath").toString());
                    }
                    if (sourceAsMap.get("coverPath") != null) {
                        video.setCoverPath(sourceAsMap.get("coverPath").toString());
                    }
                    if (sourceAsMap.get("publishDate") != null) {
                        String publishDateStr = sourceAsMap.get("publishDate").toString();
                        //处理日期转换
                        Long longs = Long.valueOf(publishDateStr);
                        Date date = new Date(longs);
                        video.setPublishDate(date);
                    }
                    if (sourceAsMap.get("categoryId") != null) {
                        video.setCategoryId(sourceAsMap.get("categoryId").toString());
                    }
                    if (sourceAsMap.get("groupId") != null) {
                        video.setGroupId(sourceAsMap.get("groupId").toString());
                    }
                    if (sourceAsMap.get("userId") != null) {
                        video.setUserId(sourceAsMap.get("userId").toString());
                    }
                    //高亮数据替换非高亮数据
                    if (sourceAsMap.get("title") != null) {
                        if (highlightMap.get("title") != null) {
                            video.setTitle(highlightMap.get("title").getFragments()[0].toString());
                        }
                    }
                    if (sourceAsMap.get("brief") != null) {
                        if (highlightMap.get("brief") != null) {
                            video.setTitle(highlightMap.get("brief").getFragments()[0].toString());
                        }
                    }
                    videoList.add(video);
                }
                return new AggregatedPageImpl<>((List<T>) videoList);
            }
        });
        return videos.getContent();
    }

    @Override
    public List<Video> querySearch(String content) {
        //查询条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingx")
                .withTypes("video")
                .withQuery(QueryBuilders.queryStringQuery("视").field("title").field("brief"))
                .build();

        List<Video> videos = elasticsearchTemplate.queryForList(nativeSearchQuery, Video.class);

        return videos;
    }
}
