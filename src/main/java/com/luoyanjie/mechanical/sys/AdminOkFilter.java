package com.luoyanjie.mechanical.sys;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.luoyanjie.mechanical.bean.common.Constant;
import com.luoyanjie.mechanical.bean.entity.UserDO;
import com.luoyanjie.mechanical.component.result.ResultCode;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.dao.mapper.UserDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

/**
 * @author luoyanjie
 * @date 2019-09-20 23:56
 * Coding happily every day!
 */
@Slf4j
//@Component
//@Profile({"prod", "test"})
public class AdminOkFilter extends OncePerRequestFilter {
    private static final List<String> WHITE_LIST = Lists.newArrayList(
            "/adminLogin/login.admin"
    );

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = httpServletRequest.getRequestURI();
        log.info("requestUri: " + requestUri);
        if (WHITE_LIST.contains(requestUri)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            boolean flag = false;
            String token = httpServletRequest.getHeader(Constant.PP_TOKEN);

            if (!StringUtils.isEmpty(token)) {
                UserDO userDO = userDOMapper.selectByPrimaryKey(Constant.ADMIN_USER_ID);
                if (Objects.equals(token, userDO.getToken())) {
                    flag = true;
                }
            }

            log.info("flag: " + flag);
            if (flag) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                error(httpServletRequest, httpServletResponse);
            }
        }
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
