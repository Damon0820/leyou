package com.leyou.user.controller.api;

import com.leyou.user.api.UserApi;
import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequestMapping("api/user/inner")
public class UserApiController implements UserApi {

    @Autowired
    private UserService userService;
    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        User user = userService.queryUserByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public User queryUserByUsername(String username) {
        User user = userService.queryUserByUsername(username);
        return user;
    }
}
