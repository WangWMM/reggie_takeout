package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.R;
import com.sun.prism.impl.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Wangmin
 * @date 2022/11/16 17:40
 */

/**
 * 1。获取本次请求URI
 * 2。判断本次请求是否需要处理
 * 3。如果不需要处理 直接放行
 * 4。如果已经登陆 直接放行
 * 5。如果未登录 返回未登录结果
 */

@Slf4j
@WebFilter(filterName = "logincheckFilter", urlPatterns ="/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1。获取本次请求URI
        String requestUri = request.getRequestURI();
        log.info("拦截到请求:{}",requestUri);
        //filterChain.doFilter(request,response);

        //不需要处理的请求路径
        String[] urls  = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login",
        };

        //2。判断本次请求是否需要处理
        boolean check = check(urls,requestUri);

        //3。如果不需要处理 直接放行
        if(check){
            log.info("本次请求{}不需要处理",requestUri);
            filterChain.doFilter(request,response);
            return;
        }

        //4。如果已经登陆 直接放行
        if(request.getSession().getAttribute("employee")!=null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }

        //5。如果未登录 返回未登录结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls          直接放行的url请求
     * @param requestURI    请求uri
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        AntPathMatcher pathmatcher = new AntPathMatcher();
        for(String url :urls){
            boolean match = pathmatcher.match(url,requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
