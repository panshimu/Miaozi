package com.miaozi.baselibrary.cache;

/**
 * created by panshimu
 * on 2019/10/25
 *  SP 存储
 */
public class PreferenceCacheHandler extends PreferencesHolder {
    @Override
    public void put(String key, String value) {
        super.put(key, value);
    }

    @Override
    public void put(String key, int value) {
        super.put(key, value);
    }

    @Override
    public boolean get(String key, boolean defaultValue) {
        return super.get(key, defaultValue);
    }

    @Override
    public void put(String key, float value) {
        super.put(key, value);
    }

    @Override
    public void put(String key, long value) {
        super.put(key, value);
    }

    @Override
    public void put(String key, boolean value) {
        super.put(key, value);
    }

    @Override
    public float get(String key, float defaultValue) {
        return super.get(key, defaultValue);
    }

    @Override
    public int get(String key, int defaultValue) {
        return super.get(key, defaultValue);
    }

    @Override
    public String get(String key, String defaultValue) {
        return super.get(key, defaultValue);
    }

    @Override
    public long get(String key, long defaultValue) {
        return super.get(key, defaultValue);
    }
}
