package com.eden.framework.context;

import lombok.NonNull;

import java.util.Set;

/**
 * @author eden
 * @since 2020/8/8
 */
public interface PermissionProvider {

    Set<String> provide(@NonNull String userId);

}
