package com.leyou.gateway.filter;

import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.CookieUtils;
import com.leyou.common.vo.Result;
import com.leyou.gateway.properties.FilterProperties;
import com.leyou.gateway.properties.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginFilter extends ZuulFilter {

    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        // 获取上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest request = context.getRequest();
        String requestURI = request.getRequestURI();
        Boolean isAllow = false;
        for (String allowPath : filterProperties.getAllowPaths()) {
            if (requestURI.startsWith(allowPath)) {
                isAllow = true;
            }
        }
        return !isAllow;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest request = context.getRequest();
        // 获取token
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        try {
            // 校验通过，什么都不做，放行
            JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            // 异常，403
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
            // TODO: 鉴权失败统一返回code：999
//            context.setResponseStatusCode(HttpStatus.OK.value());
//            context.setResponseBody("token失效了吧");
//            context.setResponseBody(new Result(999, "token失效", null));
//            throw new LyException(ExceptionEnum.TOKEN_ERROR);
        }
        return null;
    }
}
