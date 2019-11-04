package com.luoyanjie.mechanical.sys;

import com.luoyanjie.mechanical.bean.enumBean.EnvEnum;
import com.luoyanjie.mechanical.util.GsonUtil;
import com.luoyanjie.mechanical.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author luoyanjie
 * @date 2019-07-29 17:53
 * Coding happily every day!
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    @Value("${spring.profiles.active}")
    private String active;

    //控制层切入点
    @Pointcut("execution(* com.luoyanjie.mechanical.web..* (..))") //web下的和子包下的
    public void webLogPointcut() {
    }

    //控制层请求打印
    @Around("webLogPointcut()")
    public Object controllerLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String signature = proceedingJoinPoint.getSignature().toString();
        String args = Arrays.toString(proceedingJoinPoint.getArgs());
        String uri = httpServletRequest.getRequestURI();

        log.info("uri = [ {} ], method = [ {} ]", uri, httpServletRequest.getMethod());
        if ("GET".equalsIgnoreCase(httpServletRequest.getMethod())) {
            String prefix;
            switch (EnvEnum.valueOf(active.toUpperCase())) {
                case LOCAL:
                    prefix = "https://127.0.0.1:443";
                    break;
                case TEST:
                    prefix = "https://116.62.19.123:443";
                    break;
                case PROD:
                    prefix = "https://prod.lanchengyun.com";
                    break;
                default:
                    prefix = "";
                    break;
            }

            log.info("uri = [ {} ], 模拟出客户端的请求路径 url = [ {} ]", uri, prefix + uri + "?" + RequestUtil.paramMapToString(RequestUtil.getParamMapFromRequest(httpServletRequest)));
        }
        log.info("uri = [ {} ], 请求参数 = [ {} ]", uri, GsonUtil.GSON.toJson(httpServletRequest.getParameterMap()));
        log.info("uri = [ {} ], 内部方法 = [ {} ]", uri, signature);
        log.info("uri = [ {} ], 方法参数 = [ {} ]", uri, args);

        Object object = proceedingJoinPoint.proceed();

        log.info("uri = [ {} ], 请求返回值(最多只打印50字符) = [ {} ]", uri, GsonUtil.GSON.toJson(object));

        return object;
    }
}
