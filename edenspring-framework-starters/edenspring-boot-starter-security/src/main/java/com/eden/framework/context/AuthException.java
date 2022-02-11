package com.eden.framework.context;

import com.edenspring.framework.common.error.BusinessException;
import com.edenspring.framework.common.error.ErrorCode;


/**
 * @author eden
 * @since 2020/8/15
 */
public class AuthException extends BusinessException {



    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(ErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }
}
