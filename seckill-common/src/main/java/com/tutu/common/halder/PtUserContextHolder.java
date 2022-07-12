package com.tutu.common.halder;


import com.tutu.common.entity.PtUser;

/**
 * @author 涂涂
 */
public class PtUserContextHolder {

    private static final ThreadLocal<PtUser> THREAD_LOCAL = new ThreadLocal<>();

    public static PtUser get() {
        return THREAD_LOCAL.get();
    }

    public static void set(PtUser user) {
        THREAD_LOCAL.set(user);
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }

}
