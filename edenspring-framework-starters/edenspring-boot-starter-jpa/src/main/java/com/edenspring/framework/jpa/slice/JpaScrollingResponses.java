package com.edenspring.framework.jpa.slice;

import com.edenspring.framework.common.slice.ScrollingResponse;
import com.querydsl.core.QueryResults;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

/**
 * @author eden
 * @since 2020/8/7
 */
@UtilityClass
public class JpaScrollingResponses {

    public <O> ScrollingResponse<O> from(QueryResults<O> results) {
        return ScrollingResponse.<O>builder()
                .next(results.getTotal() > results.getLimit() + results.getOffset())
                .nextOffset(String.valueOf(results.getOffset() + results.getLimit()))
                .result(results.getResults())
                .build();
    }

    public <O> ScrollingResponse<O> from(Page<O> results) {
        return ScrollingResponse.<O>builder()
                .next(results.hasNext())
                .nextOffset(String.valueOf(results.getPageable().getOffset() + results.getPageable().getPageSize()))
                .result(results.getContent())
                .build();
    }

}
