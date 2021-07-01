package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "user-service",path = "api/user")
@FeignClient(value = "user-service",path = "api/user/inner")
public interface UserApi {
    @GetMapping("queryUserByUsernameAndPassword")
    public User queryUserByUsernameAndPassword(
         @RequestParam(value = "username") String username,
         @RequestParam(value = "password") String password
    );

    @GetMapping("queryUserByUsername")
    public User queryUserByUsername(
            @RequestParam(value = "username") String username
    );

}
