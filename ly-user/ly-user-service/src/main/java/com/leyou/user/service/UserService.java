package com.leyou.user.service;

import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 检查注册账号是否可用
     * @param username
     * @return
     */
    public Boolean checkRegData(String type, String username, String phone) {
        User user = new User();
        switch (type) {
            // 用户名
            case "1" :
                user.setUsername(username);
                break;
            // 电话号码
            case "2":
                user.setPhone(phone);
                break;
        }
        return userMapper.selectCount(user) == 0;
    }

    /**
     * 新增用户
     * @param user
     * @param code
     * @return
     */
    public int register(User user, String code) {
        String key = SmsSendService.CAPTCHA_PREFI + user.getPhone();
        // 从redis取出验证码
        String preCode = stringRedisTemplate.opsForValue().get(key);
        if (preCode == null) {
            // 没有有效的验证码，需要重新发送验证码
            return -1;
        }
        if (!preCode.equals(code)) {
            // 验证码不正确
            return -2;
        }
        user.setId(null);
        user.setCreated(new Date());
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 对密码进行加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        // 写入数据库
        Boolean boo = userMapper.insertSelective(user) == 1;
        // 如果注册成功，删除redis对验证码
        if (boo) {
            try {
                stringRedisTemplate.delete(key);
            } catch (Exception e) {
                System.out.println("删除redis验证码失败");
            }
        }
        return 1;
    }

    /**
     * 根据用户名和密码查用户信息
      * @param username
     * @param password
     * @return
     */
    public User queryUserByUsernameAndPassword(String username, String password) {
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        if (user == null) return null;
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            return null;
        }
        return user;
    }

}
