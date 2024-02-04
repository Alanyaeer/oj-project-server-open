//package com.genshin.ojuser;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.genshin.ojcommon.domain.entity.User;
//import com.genshin.ojuser.mapper.UserMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//
//@SpringBootTest
//class OjuserApplicationTests {
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Autowired
//    private UserMapper userMapper;
//    @Test
//    void testRedis() {
//        redisTemplate.opsForValue().set("你好打发发", "10");
//        Object value = redisTemplate.opsForValue().get("你好打发发");
//        System.out.println("value:" + value);
//    }
//    @Test
//    void testMysqlAndMybatisPlus () {
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
//        User user = userMapper.selectOne(wrapper);
//        System.out.println(user);
//    }
//
//}
