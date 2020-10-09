package com.sziov.smart.park.constant;

/**
 * @Author: willkong
 * @CreateDate: 2020/1/3 17:31
 * @Description: 常量类
 */
public class Constant {
    //glid的缓存设置
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    public static final int MAX_CACHE_MEMORY_SIZE = MAX_HEAP_SIZE / 4;
    public static final int MAX_CACHE_DISK_SIZE = 250 * 1024 * 1024;
}
