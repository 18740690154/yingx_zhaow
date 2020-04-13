package com.baizhi.zw.test;

import com.baizhi.zw.dao.VideoDAO;
import com.baizhi.zw.po.DetailVideoPo;
import com.baizhi.zw.po.LikeVideoPo;
import com.baizhi.zw.service.VideoService;
import com.baizhi.zw.vo.LikeVideoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestVideo {
    @Autowired
    VideoDAO videoDAO;
    @Autowired
    VideoService videoService;

    @Test
    public void testLike(){
        List<LikeVideoPo> likeVideoPos = videoDAO.queryByLikeVideoName("视频");
        likeVideoPos.forEach(likeVideoPo -> System.out.println(likeVideoPo));
        int size = likeVideoPos.size();
        System.out.println("size = " + size);
    }

    @Test
    public void testDetail(){
        DetailVideoPo detailVideoPo = videoDAO.queryByVideoDetail("01d27cb0-5281-4bad-8c3e-8e349aeebf4d");

       List<LikeVideoPo> likeVideoPos = videoDAO.queryByVideo("01d27cb0-5281-4bad-8c3e-8e349aeebf4d",detailVideoPo.getCId(), detailVideoPo.getUId());

        for (LikeVideoPo likeVideoPo : likeVideoPos) {
            System.out.println("likeVideoPo = " + likeVideoPo);
        }

        System.out.println("detailVideoPo = " + detailVideoPo);

    }

    @Test
    public void testVideoByCateId(){
        //List<LikeVideoPo> likeVideoPos = videoDAO.queryCateVideoList("11");
        //likeVideoPos.forEach(likeVideoPo -> System.out.println(likeVideoPo));
        List<LikeVideoVo> likeVideoVos = videoService.queryCateVideoList("11");
        likeVideoVos.forEach(likeVideoVo -> System.out.println(likeVideoVo));
    }
}
