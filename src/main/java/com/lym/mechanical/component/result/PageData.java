package com.lym.mechanical.component.result;

import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.lym.mechanical.sys.ExceptionAdvice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
@NoArgsConstructor
public class PageData<T> {
    @Getter
    @Setter
    private Integer currentPage;//当前页码

    @Getter
    @Setter
    private Integer pageSize;//页大小

    @Getter
    @Setter
    private Long totalCount;//总数

    @Getter
    @Setter
    private Boolean hasNextPage;//是否有下一页：1有，0没有

    @Getter
    @Setter
    private List<T> items;//结果

    private static final Integer FIRST_PAGE = 1;
    private static final Long NO_ITEMS = 0L;
    private static final Integer MAX_PS = 100;

    private static <T> PageData<T> inner(Integer currentPage, Integer pageSize, Long totalCount, List<T> items, Boolean hasNextPage) {
        PageData<T> pageData = new PageData<>();

        pageData.setCurrentPage(currentPage);
        pageData.setHasNextPage(hasNextPage);
        pageData.setItems(items);
        pageData.setPageSize(pageSize);
        pageData.setTotalCount(totalCount);

        return pageData;
    }

    public static <T> PageData<T> noData(Integer pageSize) {
        return inner(FIRST_PAGE, pageSize, NO_ITEMS, Lists.newArrayList(), false);
    }

    public static <T> PageData<T> data(List<T> data, Long totalCount, Integer cp, Integer ps) {
        return inner(cp, ps, totalCount, data, calcHasNextPage(cp, ps, totalCount));
    }

    public static <T> PageData<T> data(Page page, List<T> data) {
        return inner(page.getPageNum(), page.getPageSize(), page.getTotal(), data, calcHasNextPage(page.getPageNum(), page.getPageSize(), page.getTotal()));
    }

    public static Boolean checkCurrentPage(Integer cp) {
        return cp != null && cp > 0;
    }

    public static Boolean checkPageSize(Integer ps) {
        return ps != null && ps > 0 && ps < MAX_PS;
    }

    private static Boolean calcHasNextPage(Integer cp, Integer ps, Long totalCount) {
        return cp * ps < totalCount;
    }

    public static void checkPageParam(Integer pageNum, Integer pageSize) {
        if (pageNum == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "页码"));
        if (pageSize == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "页大小"));
        if (pageNum < 1) throw new RuntimeException(String.format(ExceptionAdvice.MUST_POS_NUM, "页码"));
        if (pageSize < 1) throw new RuntimeException(String.format(ExceptionAdvice.MUST_POS_NUM, "页大小"));
        if (pageSize > PageData.MAX_PS) throw new RuntimeException(String.format(ExceptionAdvice.NOT_OVER, "页大小", PageData.MAX_PS));
    }
}
