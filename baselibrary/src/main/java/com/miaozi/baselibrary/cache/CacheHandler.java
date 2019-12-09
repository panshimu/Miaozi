package com.miaozi.baselibrary.cache;

/**
 * created by panshimu
 * on 2019/10/25
 * 定义数据存储的规范
 */
public interface CacheHandler {

    void put(String key, String value);
    void put(String key, int value);
    void put(String key, double value);
    void put(String key, float value);
    void put(String key, long value);
    void put(String key, boolean value);
    void put(String key, Object value);

    String get(String key, String defaultValue);
    int get(String key, int defaultValue);
    double get(String key, double defaultValue);
    float get(String key, float defaultValue);
    long get(String key, long defaultValue);
    boolean get(String key, boolean defaultValue);
    Object get(String key, Object defaultValue);

}
