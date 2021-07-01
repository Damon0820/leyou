package com.leyou.auth.controller;

import com.leyou.auth.properties.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.CookieUtils;
import com.leyou.common.vo.Result;
import com.leyou.user.pojo.User;
import com.netflix.discovery.converters.Auto;
import com.netflix.ribbon.proxy.annotation.Http;
import con.leyou.auth.pojo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
//@EnableConfigurationProperties(JwtProperties.class);
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtProperties jwtProperties;

    /**
     * 登录授权
     * @param username
     * @param password
     * @return
     */
    @PostMapping("accredit")
    public ResponseEntity<Result<String>> authentication(
        @RequestParam(value = "username", required = true) String username,
        @RequestParam(value = "password", required = true) String password,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse
    ){
        String token = authService.authentication(username, password);
        if (StringUtils.isBlank(token)) {
            throw new LyException(ExceptionEnum.USER_LOGIN_ERROR);
        }
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
//        CookieUtils.setCookie(httpServletRequest, httpServletResponse, jwtProperties.);
        CookieUtils.setCookie(httpServletRequest, httpServletResponse, jwtProperties.getCookieName(), token, jwtProperties.getExpire(), true);
        return ResponseEntity.ok(Result.success(token));
    }

    /**
     * 登录验证
     */
    @GetMapping("verify")
    public ResponseEntity<Result<UserInfo>> verifyUser(
            @CookieValue("LY_TOKEN") String token,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            // 用户信息有效，重置token有效时间
            CookieUtils.setCookie(request, response, this.jwtProperties.getCookieName(), token, this.jwtProperties.getExpire());
            return ResponseEntity.ok(Result.success(userInfo));
        } catch (Exception e) {
            throw new  LyException(ExceptionEnum.TOKEN_ERROR);
        }

    }

    /**
     * 根据token获取完整用户信息
     */
    @GetMapping("queryUserInfoByToken")
    public ResponseEntity<Result<User>> queryUserInfoByToken(
            @CookieValue("LY_TOKEN") String token
    ) {
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            User user = authService.queryUserInfoByUsername(userInfo.getUsername());
            return ResponseEntity.ok(Result.success(user));
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.TOKEN_ERROR);
        }
    }
}
