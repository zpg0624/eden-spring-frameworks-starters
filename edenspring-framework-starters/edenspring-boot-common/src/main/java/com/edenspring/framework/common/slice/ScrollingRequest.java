package com.edenspring.framework.common.slice;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author eden
 * @since 2020/7/20
 */
@NoArgsConstructor
@Data
public class ScrollingRequest implements Serializable {

    protected String offset = "0";

    protected long size = 20;

    public long offsetAsLong() {
        return Long.parseLong(offset);
    }

}
