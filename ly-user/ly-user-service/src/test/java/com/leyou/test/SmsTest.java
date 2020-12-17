package com.leyou.test;

import com.leyou.LyUserService;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyUserService.class)
public class SmsTest {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis() {
        // 存储数据
        stringRedisTemplate.opsForValue().set("testName", "测试爸爸哦");
        String val = stringRedisTemplate.opsForValue().get("testName");
        System.out.println(val);
    }
}
