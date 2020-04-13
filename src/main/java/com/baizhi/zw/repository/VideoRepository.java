package com.baizhi.zw.repository;

import com.baizhi.zw.entity.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public  interface VideoRepository extends ElasticsearchRepository<Video, String> {
}
