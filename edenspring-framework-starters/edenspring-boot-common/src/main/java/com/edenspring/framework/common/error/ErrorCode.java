package com.edenspring.framework.common.error;

/**
 * ErrorCode
 *
 * @author eden
 * @since 2019-03-06 10:35
 */
public interface ErrorCode {

    int getCode();

    String getNote();

    default String getDetail() {
        return null;
    }

}
