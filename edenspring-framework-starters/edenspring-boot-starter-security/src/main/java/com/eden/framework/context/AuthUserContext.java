package com.eden.framework.context;

import lombok.experimental.UtilityClass;

/**
 * @author eden
 * @since 2020/8/1
 */
@UtilityClass
public class AuthUserContext {

    private final ThreadLocal<CurrentAuthUser> currentAuthUser = new ThreadLocal<>();

    public CurrentAuthUser current() {
        return currentAuthUser.get();
    }

    public void setCurrent(CurrentAuthUser currentAuthUser) {
        AuthUserContext.currentAuthUser.set(currentAuthUser);
    }

    public void reset() {
        AuthUserContext.currentAuthUser.remove();
    }


}
