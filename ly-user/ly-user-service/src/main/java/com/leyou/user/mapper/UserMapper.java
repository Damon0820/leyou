package com.leyou.user.mapper;

import com.leyou.user.pojo.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    /**
     * 查询用户账号是否已经被注册
     */
    int selectCountByNameOrPhone(User user);
}
