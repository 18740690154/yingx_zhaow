package com.baizhi.zw.test;


import com.baizhi.zw.entity.Video;
import com.baizhi.zw.repository.VideoRepository;
import com.baizhi.zw.service.VideoService;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestES {
    @Autowired
    VideoRepository videoRepository;

    @Autowired
    VideoService videoService;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void saveVideo(){
        Map<String, Object> map = videoService.queryByPage(1, 100);
        List<Video> rows = (List) map.get("rows");
        for (Video row : rows) {
            videoRepository.save(row);
        }
    }

    @Test
    public void testQuerySearchVideo(){

        //查询条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingx")
                .withTypes("video")
                .withQuery(QueryBuilders.queryStringQuery("视").field("title").field("brief"))
                .build();

        List<Video> videos = elasticsearchTemplate.queryForList(nativeSearchQuery, Video.class);

        videos.forEach(video -> System.out.println(video));
    }

}
