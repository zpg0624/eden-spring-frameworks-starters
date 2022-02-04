package com.edenspring.framework.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author eden
 * @since 2020/7/3
 */
@Getter
@RequiredArgsConstructor
public class ParamNotValidErrorCode implements ErrorCode {

    private static final String noteTemplate = "客戶端請求參數有误, %s";

    private final int code = -100_00_002;

    private final String note;

    private final String detail;

    public static ErrorCode of(String message, String detail) {
        return new ParamNotValidErrorCode(String.format(noteTemplate, message), detail);
    }


}
