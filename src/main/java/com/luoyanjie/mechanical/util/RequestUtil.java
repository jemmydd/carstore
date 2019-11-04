package com.luoyanjie.mechanical.util;

import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author luoyanjie
 * @date 2019-07-29 18:01
 * Coding happily every day!
 */
@Slf4j
public class RequestUtil {
    /**
     * 根据请求获取所有请求参数
     *
     * @param httpServletRequest 请求
     * @return 请求参数map
     */
    public static Map<String, String> getParamMapFromRequest(HttpServletRequest httpServletRequest) {
        return getParamMapFromRequest(httpServletRequest, null);
    }

    /**
     * 根据请求获取所有请求参数
     *
     * @param httpServletRequest 请求
     * @param charset            字符编码
     * @return 请求参数map
     * @throws UnsupportedEncodingException 异常
     */
    public static Map<String, String> getParamMapFromRequest(HttpServletRequest httpServletRequest, String charset) {
        if (Objects.isNull(httpServletRequest)) {
            return new HashMap<>();
        }

        boolean charsetIsEmpty = StringUtils.isBlank(charset);


        Map<String, String[]> requestParams = httpServletRequest.getParameterMap();
        Map<String, String> params = new HashMap<>();

        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = requestParams.get(name);
            String valueStr = "";

            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }

            try {
                params.put(name, charsetIsEmpty ? valueStr : new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), charset));
            } catch (UnsupportedEncodingException e) {
                log.error("getParamMapFromRequest error");
                e.printStackTrace();
            }
        }

        return params;
    }

    /**
     * 请求转XML
     *
     * @param httpServletRequest 请求
     * @return xml Map
     * @throws IOException       IOException
     * @throws DocumentException DocumentException
     */
    public static Map<String, String> requestToXml(HttpServletRequest httpServletRequest) throws IOException, DocumentException {
        Map<String, String> result = new HashMap<>();

        ServletInputStream servletInputStream = httpServletRequest.getInputStream();
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(servletInputStream);

        //得到xml根元素
        Element root = document.getRootElement();

        // 得到根元素的所有子节点
        List elements = root.elements();

        // 遍历所有子节点
        for (Object object : elements) {
            Element element = (Element) object;
            result.put(element.getName(), element.getText());
        }

        // 释放资源
        servletInputStream.close();

        return result;
    }

    public static String paramMapToString(Map<String, String> params) throws UnsupportedEncodingException {
        return paramMapToString(params, "UTF-8");
    }

    public static String paramMapToString(Map<String, String> params, String encodeCharset) throws UnsupportedEncodingException {
        int index = 0;
        StringBuilder content = new StringBuilder();

        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(URLEncoder.encode(value, StringUtil.isEmpty(encodeCharset) ? "UTF-8" : encodeCharset));
                ++index;
            }
        }

        return content.toString();
    }

}
