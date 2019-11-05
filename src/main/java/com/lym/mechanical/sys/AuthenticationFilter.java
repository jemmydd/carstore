package com.lym.mechanical.sys;

import com.lym.mechanical.bean.common.Constant;
import com.lym.mechanical.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
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
