package com.eden.framework.context;

import lombok.NonNull;

/**
 * @author eden
 * @since 2020/8/8
 */
public interface ResourceAccessProvider {

    ResourceAccess provide(@NonNull String userId);

}
