package com.jk.mapper;


import com.jk.model.VideoBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserMapper {


    List<VideoBean> findVideo();

    VideoBean findByShoppingId(@Param("id") String id);
}
