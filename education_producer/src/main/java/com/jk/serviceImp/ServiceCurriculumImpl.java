package com.jk.serviceImp;

import com.jk.mapper.UserMapper;
import com.jk.model.VideoBean;
import com.jk.serviceapi.CurriculumServiceApl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ServiceCurriculumImpl implements CurriculumServiceApl {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("findVideo")
    @ResponseBody
    @Override
    public List<VideoBean> findVideo() {
        return userMapper.findVideo();
    }

}
