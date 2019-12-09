package com.miaozi.baselibrary.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * created by panshimu
 * on 2019/10/25
 */
public class PreferencesUtils{
    private static final String CACHE_TAG = "miaozi";
    private static volatile PreferencesUtils mPreferencesUtils;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private PreferencesUtils(){
    }
    public static PreferencesUtils getInstance(){
        if(mPreferencesUtils == null){
            synchronized (PreferencesUtils.class){
                if(mPreferencesUtils == null){
                    mPreferencesUtils = new PreferencesUtils();
                }
            }
        }
        return mPreferencesUtils;
    }
    public void init(Context context){
        mPreferences = context.getApplicationContext().getSharedPreferences(CACHE_TAG,Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public void put(String key, String value) {
        if(mEditor == null)
            throw new RuntimeException("请先初始化 init() ");
        mEditor.putString(key,value);
        mEditor.commit();
    }

    public void put(String key, int value) {
        if(mEditor == null)
            throw new RuntimeException("请先初始化 init() ");
        mEditor.putInt(key,value);
        mEditor.commit();
    }

    public void put(String key, float value) {
        if(mEditor == null)
            throw new RuntimeException("请先初始化 init() ");
        mEditor.putFloat(key,value);
        mEditor.commit();
    }

    public void put(String key, long value) {
        if(mEditor == null)
            throw new RuntimeException("请先初始化 init() ");
        mEditor.putLong(key,value);
        mEditor.commit();
    }

    public void put(String key, boolean value) {
        if(mEditor == null)
            throw new RuntimeException("请先初始化 init() ");
        mEditor.putBoolean(key,value);
        mEditor.commit();
    }

    public String get(String key, String defaultValue) {
        if(mPreferences == null)
            throw new RuntimeException("请先初始化 init() ");
        return mPreferences.getString(key,defaultValue);
    }

    public int get(String key, int defaultValue) {
        if(mPreferences == null)
            throw new RuntimeException("请先初始化 init() ");
        return  mPreferences.getInt(key,defaultValue);
    }

    public float get(String key, float defaultValue) {
        if(mPreferences == null)
            throw new RuntimeException("请先初始化 init() ");
        return  mPreferences.getFloat(key,defaultValue);
    }

    public long get(String key, long defaultValue) {
        if(mPreferences == null)
            throw new RuntimeException("请先初始化 init() ");
        return  mPreferences.getLong(key,defaultValue);
    }

    public boolean get(String key, boolean defaultValue) {
        if(mPreferences == null)
            throw new RuntimeException("请先初始化 init() ");
        return  mPreferences.getBoolean(key,defaultValue);
    }

}
