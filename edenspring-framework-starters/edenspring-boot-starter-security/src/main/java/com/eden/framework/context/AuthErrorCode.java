package com.eden.framework.context;

import com.edenspring.framework.common.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用错误类型
 *
 * @author eden
 * @since 2019-03-06 10:37
 */
@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    NO_PERMISSION(-100_01_002, "您沒有操作權限"),
    NEED_LOGIN(-100_01_003, "需要登錄"),
    INVALID_LOGIN_MESSAGE(-100_01_004, "登錄信息有誤，請重新登錄"),
    ERROR_REFRESH_TOKEN(-100_01_005, "登錄刷新碼錯誤"),
    NO_RESOURCE_ACCESS(-100_01_006, "您沒有當前商戶或門店的操作權限"),
    INVALID_SCOPE(-100_01_008, "scope信息有誤，請聯系開发人員"),
    ;

    private final int code;

    private final String note;

}
