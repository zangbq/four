package com.jk.repostry;

import com.jk.model.VideoBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface CurriculumRepostory extends ElasticsearchCrudRepository<VideoBean,String> {
}
