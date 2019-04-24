package com.jk.controller;

import com.alibaba.fastjson.JSON;
import com.jk.model.VideoBean;
import com.jk.model.VideoMongo;
import com.jk.serviceapi.CurriculumServiceApl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("shopping")
public class ShoppingController {

    @Autowired
    private CurriculumServiceApl curriculumServiceApl;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping("findVideoOneById")
    @ResponseBody
    public VideoBean findVideoOneById(@RequestParam("id") String id, HttpServletRequest request){
        request.getSession().setAttribute("user",id);
        //将前台传过来的商品id和 用户选择的商品数量放到mogo表中
        String userId = "11"; //用户id
        VideoMongo videoMongo = new VideoMongo();
        videoMongo.setUserId(userId);
        videoMongo.setShoppingId(id);
        String toJSONString = JSON.toJSONString(videoMongo);
        amqpTemplate.convertAndSend("videoMongoUserShopping_Id",toJSONString);
       // mongoTemplate.insert(mongoShopping,"t_shopping");saveShopping
        // 根据商品id 查询出来商品的详情
        VideoBean videoBean = curriculumServiceApl.findByShoppingId(id);
        String jsonString = JSON.toJSONString(videoBean);
        amqpTemplate.convertAndSend("findByShoppingId_Info",jsonString+"0000"+id);//将从session中取出来的id通过拼接放到rabbit中
        //redisTemplate.opsForValue().set("shopping",shopping);
        System.out.println(videoBean+"================================================根据id查询出来得信息");
        return videoBean;
    }
    /* @ResponseBody
    @RequestMapping("saveUser")
    public void saveUser(User user){
        String s = JSON.toJSONString(user);
        amqpTemplate.convertAndSend("saveUser",s);
        System.out.println("============================ww==c存进rabbitmq队列");
    }*/

    /**
     * 从reaids中取出来数据展示到购物车
     * @param request
     * @return    购物车取值的时候通过 session作用域中的userid取值
     */
    @RequestMapping("findShoppingMessage")
    @ResponseBody
    public VideoBean findShoppingMessage(HttpServletRequest request){
        String userId = "11"; // 用于用户id
       // String id = (String)request.getSession().getAttribute("shoppingId");
        VideoBean andSet = (VideoBean)redisTemplate.opsForValue().get(userId);
        //Shopping andSet1 = (Shopping)redisTemplate.opsForValue().getAndSet(userId, Shopping.class);
        System.out.println(andSet+"==============================从redis中取出来的数据");
        return andSet;
    }

}
