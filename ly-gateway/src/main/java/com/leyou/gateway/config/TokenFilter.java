package com.leyou.gateway.config;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenFilter extends ZuulFilter {
    //网关的过滤器如何编写
    //过滤类型 pre 表示在请求之前进行执行
    @Override
    public String filterType() {
        /**
         * pre：路由之前
         * routing：路由之时
         * post： 路由之后
         * error：发送错误调用
         */

        return FilterConstants.POST_TYPE;
    }
    //过滤器执行顺序，当一个请求在同一个阶段存在多个过滤器的时候，多个过滤器执行顺序。
    @Override
    public int filterOrder() {
        return 0;
    }
    //判断过滤器是否生效
    @Override
    public boolean shouldFilter() {
        return true;
    }
    //编写过滤器拦截业务逻辑代码
    @Override
    public Object run() throws ZuulException {
        //案例：拦截所有的服务接口，判断服务接口上是否有传递userToken参数。
        //1.获取上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //2.获取Request对象
        HttpServletRequest request = currentContext.getRequest();
        String serverName = request.getRequestURL().toString();
        System.out.println(serverName);

        //3.获取token的时候从请求头中获取
//        String userToken = request.getParameter("userToken");
//        if (StringUtils.isEmpty(userToken)){
//            //不会继续执行....不会去调用服务接口，网关服务直接响应给客户端
//            currentContext.setSendZuulResponse(false);
//            currentContext.setResponseBody("userToken is null");
//            currentContext.setResponseStatusCode(401);
//            return null;
//        }
        //正常执行调用其他服务接口...
        return null;
    }
}
