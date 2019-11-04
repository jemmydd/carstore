package com.luoyanjie.mechanical.temp;

import com.google.common.collect.Lists;
import com.luoyanjie.mechanical.bean.dto.banner.BannerDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-09-04 15:54
 * Coding happily every day!
 */
public class SimpleTest {
    public static void main(String[] args) {
        List<BannerDTO> ints = Lists.newArrayList(null, BannerDTO.builder().build(), null);
        System.out.println(ints);

        ints = ints.stream().filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println(ints);
    }
}
