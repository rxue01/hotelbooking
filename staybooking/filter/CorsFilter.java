// 出现前后端的跨域访问，需要后端支持"OPTIONS" ---》提供给浏览器，以支持跨域

package com.laioffer.staybooking.filter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // 此filter在所有filter之前
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type"); // authorization ---》存放token的地方

        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);// 若是option，直接返回OK
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);//若是其他，继续filter chain
        }
        //
    }
}
