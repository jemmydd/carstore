package com.lym.mechanical.sys;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.lym.mechanical.bean.common.Constant;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.component.result.ResultCode;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

@Slf4j
@WebFilter(filterName = "adminFilter", urlPatterns = {"*.admin"})
@Profile({"prod", "test"})
public class AdminFilter implements Filter {
    @Autowired
    private CarUserDOMapper carUserDOMapper;

    private static final List<String> WHITE_LIST = Lists.newArrayList(
            "/adminLogin/login.admin"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");

        String requestUri = httpServletRequest.getRequestURI();
        log.info("requestUri: " + requestUri);
        if (WHITE_LIST.contains(requestUri)) {
            chain.doFilter(request, response);
        } else {
            boolean flag = false;
            String token = httpServletRequest.getHeader(Constant.PP_TOKEN);

            if (!StringUtils.isEmpty(token)) {
                CarUserDO carUserDO = carUserDOMapper.selectByName("admin");
                if (Objects.equals(token, carUserDO.getSessionKey())) {
                    flag = true;
                }
            }

            log.info("flag: " + flag);
            if (flag) {
                chain.doFilter(request, response);
            } else {
                error(httpServletRequest, httpServletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private static void error(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        Gson gson = new Gson();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(byteArrayOutputStream);

        byte[] bytes = gson.toJson(ResultUtil.error(null, ResultCode.TOKEN_EXCEPTION)).getBytes(StandardCharsets.UTF_8);
        gzip.write(bytes);
        gzip.close();

        bytes = byteArrayOutputStream.toByteArray();

        httpServletResponse.setHeader("Content-Encoding", "gzip");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.setContentLength(bytes.length);
        httpServletResponse.getOutputStream().write(bytes);
    }
}
