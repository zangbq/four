package com.jk.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
//启用redis session 把session存入redis中 实现session共享
//maxInactiveIntervalInSeconds 单位为秒 设置session过期时间
//默认为30分钟
@EnableRedisHttpSession
public class SessionConfig {

}
