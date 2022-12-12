package com.liumang.lmusic.filter;

import com.alibaba.fastjson.JSON;
import com.liumang.lmusic.common.BaseContext;
import com.liumang.lmusic.common.R;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

/**
 * 过滤器，，，检查用户是否登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /**
         * 1.获取本次请求的URI
         * 2.判断本次请求是否需要处理
         * 3.无需处理就放行
         * 4.判断登录状态，已经登录则放行
         * 5.如果未登录则返回未登录结果
         */
//        1.获取本次请求的URI
        String requestURI = request.getRequestURI();
//        定义不需要请求的路径
        String[] urls = new String[]{
            "/employee/login",
            "/employee/logout",
            "/user/login",
            "/user/register",
            "/LMusic/**",
        };
//        2.判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
//        3.无需处理就放行
        if (check){
            filterChain.doFilter(request,response);
            return;
        }
//        4.1 判断登录状态，已经登录则放行
        if (request.getSession().getAttribute("employee") != null){
            //基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
            //p42
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
//        4.2 判断登录状态，已经登录则放行
        if (request.getSession().getAttribute("user") != null){
            //基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }
//        5.如果未登录则返回未登录结果，和前端配合，通过输出流去返回数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
//    判断是否需要拦截的函数，在第3步调用
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
