package com.edenspring.framework.common.slice;

import com.querydsl.core.QueryResults;
import lombok.experimental.UtilityClass;

/**
 * @author eden
 * @since 2020/8/7
 */
@UtilityClass
public class JpaPageResponses {

    public <O> PageResponse<O> from(QueryResults<O> results) {
        return PageResponse.<O>builder()
                .result(results.getResults())
                .total(results.getTotal())
                .build();
    }

    public <O> PageResponse<O> from(org.springframework.data.domain.Page<O> results) {
        return PageResponse.<O>builder()
                .result(results.getContent())
                .total(results.getTotalElements())
                .build();
    }

}
