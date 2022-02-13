package com.edenspring.framework.common.slice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author eden
 * @since 2020/7/20
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResponse<O> implements Serializable {

    private List<O> result;

    private long total;

}
