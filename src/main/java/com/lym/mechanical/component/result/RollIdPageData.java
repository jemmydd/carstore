package com.lym.mechanical.component.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
@Data
@NoArgsConstructor
public class RollIdPageData<T> {
    private Boolean positive;//是否正向

    private Integer currentFlagId;//当前列表做标记的ID

    private Integer baseSize;//最底层的数据页大小

    private Boolean hasMore;//是否还有数据：1有，0没有

    private List<T> items;//结果

    private static <T> RollIdPageData<T> inner(Boolean positive, Integer currentFlagId, Integer baseSize, Boolean hasMore, List<T> items) {
        RollIdPageData<T> rollPageData = new RollIdPageData<>();

        rollPageData.setBaseSize(baseSize);
        rollPageData.setCurrentFlagId(currentFlagId);
        rollPageData.setHasMore(hasMore);
        rollPageData.setItems(items);
        rollPageData.setPositive(positive);

        return rollPageData;
    }

    public static <T> RollIdPageData<T> noData(Boolean positive, Integer baseSize) {
        return inner(positive, null, baseSize, false, null);
    }

    public static <T> RollIdPageData<T> data(Boolean positive, Integer currentFlagId, Integer baseSize, Boolean hasMore, List<T> data) {
        return inner(positive, currentFlagId, baseSize, hasMore, data);
    }
}
