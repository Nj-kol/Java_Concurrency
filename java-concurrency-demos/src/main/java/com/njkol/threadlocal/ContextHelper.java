package com.njkol.threadlocal;

/**
 * Helper to manage AppContext per request thread
 */
public class ContextHelper {
	
    private static final ThreadLocal<AppContext> CONTEXT_THREAD_LOCAL = ThreadLocal.withInitial(() -> new AppContext("", ""));

    public static void setContext(AppContext ac) {
        CONTEXT_THREAD_LOCAL.set(ac);
    }

    public static AppContext getContext() {
        return CONTEXT_THREAD_LOCAL.get();
    }

    public static void unsetContext() {
        CONTEXT_THREAD_LOCAL.remove();
    }
}