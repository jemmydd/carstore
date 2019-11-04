package com.luoyanjie.mechanical.sys;

import com.luoyanjie.mechanical.bean.common.Constant;
import com.luoyanjie.mechanical.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 只针对客户端
 *
 * @author luoyanjie
 * @date 2019-08-04 09:35
 * Coding happily every day!
 */
@Slf4j
//@WebFilter(filterName = "authenticationFilter", urlPatterns = {"*.action"})
//@Profile({"prod", "test"})
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        try {
            String requestUri = httpServletRequest.getRequestURI();
            Map<String, String> paramMap = RequestUtil.getParamMapFromRequest(httpServletRequest);

            String app = paramMap.get(Constant.PP_APP);
            String client = paramMap.get(Constant.PP_CLIENT);
            String version = paramMap.get(Constant.PP_VERSION);
            String token = paramMap.get(Constant.PP_TOKEN);
            String userIdStr = paramMap.get(Constant.PP_USER_ID);

            String requestInfo = String.format("****** AuthenticationFilter info, 应用: %s, 客户端: %s, 版本号: %s, token: %s, 用户ID: %s, 接口: %s",
                    app, client, version, token, userIdStr, requestUri);

            log.info(requestInfo);
        } catch (Exception ex) {
            log.error("AuthenticationFilter doFilter error: ");
            ex.printStackTrace();
        } finally {
            log.info("authenticationFilter finally");
        }
    }

    @Override
    public void destroy() {
    }
}
