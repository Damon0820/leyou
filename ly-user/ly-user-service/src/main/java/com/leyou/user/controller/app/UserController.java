package com.leyou.user.controller.app;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("check")
    public ResponseEntity<Boolean> checkRegData(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "type", required = true) String type
    ) {
        Boolean checkEnable = userService.checkRegData(type, username, phone);
        if (checkEnable == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(checkEnable);
    }


    @GetMapping("register")
    public ResponseEntity<Integer> register(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "code") String code,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        User user = new User();
        user.setPhone(phone);
        user.setUsername(username);
        user.setPassword(password);
        return ResponseEntity.ok(userService.register( user, code));
    }

    @GetMapping("queryUserByUsernameAndPassword")
    public ResponseEntity<User> queryUserByUsernameAndPassword(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password
    ) {
        User user = userService.queryUserByUsernameAndPassword(username, password);
        if (user == null) {
            throw new LyException(ExceptionEnum.USER_LOGIN_ERROR);
//            return new ResponseEntity<>(, HttpStatus.OK);
        }
        return ResponseEntity.ok(user);
    }
}
