package com.edenspring.framework.common.slice;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author eden
 * @since 2020/8/7
 */

@NoArgsConstructor
@Data
@Valid
public class PageRequest implements Serializable {

    @Min(1)
    protected long pageNo = 1;

    protected long pageSize = 20;

    public ScrollingRequest toScrolling() {
        ScrollingRequest request = new ScrollingRequest();
        request.setOffset(String.valueOf((pageNo - 1) * pageSize));
        request.setSize(pageSize);
        return request;
    }

}
