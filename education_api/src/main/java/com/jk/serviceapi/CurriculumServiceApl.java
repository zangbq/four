package com.jk.serviceapi;

import com.jk.model.VideoBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

public interface CurriculumServiceApl {

    @RequestMapping("findVideo")

    List<VideoBean> findVideo();

    @RequestMapping("findByShoppingId")
    @ResponseBody
    VideoBean findByShoppingId(@RequestParam("id")String id);

}
