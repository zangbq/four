package com.jk.controller;

import com.jk.model.VideoBean;
import com.jk.serviceapi.CurriculumServiceApl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("curriculum")
public class CurriculumController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CurriculumServiceApl curriculumServiceApl;

    private Jedis jedis = null;

    @RequestMapping("findRedisVideo")
    @ResponseBody
    public void findRedisVideo() {

        List<VideoBean> videoBeanList = curriculumServiceApl.findVideo();

        if (redisTemplate.opsForValue().getAndSet("videoBeanList",videoBeanList)!=null){

            redisTemplate.opsForValue().set("videoBeanList",videoBeanList);

        }
        for (VideoBean videoBean : videoBeanList) {
            //jedis.append("videoBeanList", videoBean.getCourseId());
           // redisTemplate.opsForValue().append("videoBeanList", videoBean.getCourseId());
        }
        videoBeanList.stream().map(VideoBean::getCourseId).map((l) -> String.valueOf(l)).collect(Collectors.toList());

      //  System.out.println("=========="+jedis.lpush("videoBeanList", videoBeanList.toArray(new String[videoBeanList.size()])) );

        System.out.println(redisTemplate.opsForValue().getAndSet("videoBeanList",videoBeanList));

    }

    @RequestMapping("getRedisSize")
    @ResponseBody
    public void getRedisSize(){
        List<String> strs = jedis.lrange("videoBeanList", 0, -1);
        for(String str:strs){
            System.out.println(str);
        }
    }


}
