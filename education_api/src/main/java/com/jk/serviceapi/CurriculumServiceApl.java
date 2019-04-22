package com.jk.serviceapi;

import com.jk.model.VideoBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

public interface CurriculumServiceApl {

    @RequestMapping("findVideo")
    @ResponseBody
    List<VideoBean> findVideo();
}
