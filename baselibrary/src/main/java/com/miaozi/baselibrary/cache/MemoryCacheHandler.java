package com.miaozi.baselibrary.cache;

/**
 * created by panshimu
 * on 2019/10/25
 * 内存存储
 */
public class MemoryCacheHandler implements CacheHandler {

    @Override
    public void put(String key, String value) {

    }

    @Override
    public void put(String key, int value) {

    }

    @Override
    public void put(String key, double value) {

    }

    @Override
    public void put(String key, float value) {

    }

    @Override
    public void put(String key, long value) {

    }

    @Override
    public void put(String key, boolean value) {

    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public String get(String key, String defaultValue) {
        return null;
    }

    @Override
    public int get(String key, int defaultValue) {
        return 0;
    }

    @Override
    public double get(String key, double defaultValue) {
        return 0;
    }

    @Override
    public float get(String key, float defaultValue) {
        return 0;
    }

    @Override
    public long get(String key, long defaultValue) {
        return 0;
    }

    @Override
    public boolean get(String key, boolean defaultValue) {
        return false;
    }

    @Override
    public Object get(String key, Object defaultValue) {
        return null;
    }
}
