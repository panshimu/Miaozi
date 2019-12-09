package com.miaozi.baselibrary.cache;

/**
 * created by panshimu
 * on 2019/10/25
 */
public abstract class PreferencesHolder implements CacheHandler {
    @Override
    public void put(String key, String value) {
        PreferencesUtils.getInstance().put(key,value);
    }

    @Override
    public void put(String key, int value) {
        PreferencesUtils.getInstance().put(key,value);
    }

    @Override
    public void put(String key, double value) {
    }

    @Override
    public void put(String key, float value) {
        PreferencesUtils.getInstance().put(key,value);
    }

    @Override
    public void put(String key, long value) {
        PreferencesUtils.getInstance().put(key,value);
    }

    @Override
    public void put(String key, boolean value) {
        PreferencesUtils.getInstance().put(key,value);
    }

    @Override
    public void put(String key, Object value) {
    }

    @Override
    public String get(String key, String defaultValue) {
        return PreferencesUtils.getInstance().get(key,defaultValue);
    }

    @Override
    public int get(String key, int defaultValue) {
        return PreferencesUtils.getInstance().get(key,defaultValue);
    }

    @Override
    public double get(String key, double defaultValue) {
        return 0;
    }

    @Override
    public float get(String key, float defaultValue) {
        return PreferencesUtils.getInstance().get(key,defaultValue);
    }

    @Override
    public long get(String key, long defaultValue) {
        return 0;
    }

    @Override
    public boolean get(String key, boolean defaultValue) {
        return PreferencesUtils.getInstance().get(key,defaultValue);
    }

    @Override
    public Object get(String key, Object defaultValue) {
        return null;
    }
}
