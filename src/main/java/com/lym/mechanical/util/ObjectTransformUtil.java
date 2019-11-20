package com.lym.mechanical.util;

import com.google.common.collect.Maps;
import com.lym.mechanical.bean.common.ClassAttribute;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

public class ObjectTransformUtil {
    private static Logger log = Logger.getLogger(ObjectTransformUtil.class.getName());

    /**
     * 将javabean实体类转为map类型，然后返回一个map类型的值
     *
     * @param bean 需要转换的对象
     * @return 转换后的map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> result = new HashMap<>();

        //对象为空，直接返回空
        if (Objects.isNull(bean)) {
            return result;
        }

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            result.putAll(setAttributeMap(bean, propertyDescriptors));

            // 过滤敏感信息: class属性
            result.remove(ClassAttribute.CLASS);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("beanToMap, 对象转换出错: " + bean);
        }

        return result;
    }

    /**
     * 将javabean实体类转为map类型，然后返回一个map类型的值
     *
     * @param bean       需要转换的对象
     * @param attributes 不包括这些属性
     * @return 转换后的map
     */
    public static Map<String, Object> beanToMap(Object bean, Set<String> attributes) {
        Map<String, Object> result = new HashMap<>();

        //对象为空，直接返回空
        if (Objects.isNull(bean)) {
            return result;
        }

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor temp : propertyDescriptors) {
                String key = temp.getName();
                //排除属性
                if (CollectionUtils.isEmpty(attributes) || !attributes.contains(key)) {
                    Method getter = temp.getReadMethod();
                    Object value = getter.invoke(bean);
                    result.put(key, value);
                }
            }
            // 过滤敏感信息: class属性
            result.remove(ClassAttribute.CLASS);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("beanToMap, 对象转换出错: " + bean);
        }

        return result;
    }

    public static Map<String, String> beanToMapReturnString(Object bean) {
        Map<String, String> result = Maps.newHashMap();

        Map<String, Object> data = beanToMap(bean);
        Set<String> keySet = data.keySet();

        if (!ObjectUtils.isEmpty(keySet)) {
            for (String key : keySet) {
                String val = String.valueOf(data.get(key));

                if (!ObjectUtils.isEmpty(val)) {
                    result.put(key, val);
                }
            }
        }

        return result;
    }

    /**
     * 将javabean实体类转为map类型（键值都是字符串），然后返回一个map类型的值
     *
     * @param bean 需要转换的对象
     * @return 转换后的map
     */
    public static Map<String, String> beanToStringMap(Object bean) {
        Map<String, String> result = new HashMap<>();

        //对象为空，直接返回空
        if (Objects.isNull(bean)) {
            return result;
        }

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            result.putAll(setAttributeMap(bean, propertyDescriptors));

            // 过滤敏感信息: class属性
            result.remove(ClassAttribute.CLASS);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("beanToStringMap, 对象转换出错: " + bean);
        }
        return result;
    }

    /**
     * 将javabean实体类转为map类型（键值都是字符串），然后返回一个map类型的值
     *
     * @param bean       需要转换的对象
     * @param attributes 不包括这些属性
     * @return 转换后的map
     */
    public static Map<String, String> beanToStringMap(Object bean, Set<String> attributes) {
        Map<String, String> result = new HashMap<>();

        //对象为空，直接返回空
        if (Objects.isNull(bean)) {
            return result;
        }

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor temp : propertyDescriptors) {
                String key = temp.getName();
                //排除属性
                if (CollectionUtils.isEmpty(attributes) || !attributes.contains(key)) {
                    Method getter = temp.getReadMethod();
                    String value = String.valueOf(getter.invoke(bean));
                    result.put(key, value);
                }
            }
            // 过滤敏感信息: class属性
            result.remove(ClassAttribute.CLASS);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("beanToStringMap, 对象转换出错: " + bean);
        }

        return result;
    }

    /**
     * map转对象
     *
     * @param map map
     * @param clz 类型
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T mapToBean(Map map, Class<T> clz) {
        T obj = null;

        try {
            //获取类属性
            BeanInfo beanInfo = Introspector.getBeanInfo(clz);
            //创建 JavaBean 对象
            obj = clz.newInstance();
            //属性
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String propertyName = propertyDescriptor.getName();

                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    propertyDescriptor.getWriteMethod().invoke(obj, value);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            log.info("ObjectTransformUtil -> mapToBean");
        }

        return obj;
    }

    public static <T, K, V> List<T> mapToBean(List<Map<K, V>> mapList, Class<T> clz) {
        List<T> result = new ArrayList<>();

        for (Map<K, V> map : mapList) {
            T t = mapToBean(map, clz);
            result.add(t);
        }

        return result;
    }

    private static Map<? extends String, ? extends String> setAttributeMap(Object bean, PropertyDescriptor[] propertyDescriptors) throws InvocationTargetException, IllegalAccessException {
        Map<String, String> result = new HashMap<>();
        for (PropertyDescriptor temp : propertyDescriptors) {
            String key = temp.getName();
            Method getter = temp.getReadMethod();

            Object val = getter.invoke(bean);

            if (val != null) {
                result.put(key, String.valueOf(val));
            }
        }
        return result;
    }


}
