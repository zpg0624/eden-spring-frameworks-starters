package com.edenspring.framework.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author eden
 * @since 2020/7/3
 */
@Getter
@RequiredArgsConstructor
public class HttpStatusUnOkErrorCode implements ErrorCode {

    private final int code = -100_00_000;

    private final String note;

    private final String detail;

    public static ErrorCode of(String desc, String detail) {
        return new HttpStatusUnOkErrorCode(desc, detail);
    }


}
