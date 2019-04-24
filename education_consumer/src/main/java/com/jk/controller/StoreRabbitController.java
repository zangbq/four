package com.jk.controller;

import com.alibaba.fastjson.JSON;
import com.jk.model.VideoBean;
import com.jk.model.VideoMongo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class StoreRabbitController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @RabbitListener(queues = "videoMongoUserShopping_Id")
    public void receiveMes(String message){
        System.out.println("saveShopping 接到啦");
        //Object o = JSONObject.toJSON(message);
        VideoMongo mongoShopping = JSON.parseObject(message, VideoMongo.class);
        mongoTemplate.insert(mongoShopping, "t_videoid");
        System.out.println("=================saveShopping=============="+mongoShopping.toString());
    }

    @RabbitListener(queues = "findByShoppingId_Info")
    public void receiveMes1(String message){
        String[] split = message.split("0000");  // 将从rabbitmq中取出来的消息 截取出来 userid 放到redis的 key里面 , redis存储的时候通过userid存
        System.out.println(split[0]);
        System.out.println(split[1]);
        System.out.println("shopping 接到啦");
        VideoBean videoBean = JSON.parseObject(split[0], VideoBean.class);
        String userId = "11"; // 用于用户id
        redisTemplate.opsForValue().set(split[1],videoBean);
        System.out.println("=================shopping=============="+videoBean.toString());
    }

}
