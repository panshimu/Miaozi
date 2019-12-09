package com.miaozi.baselibrary.cache;

/**
 * created by panshimu
 * on 2019/10/25
 *  数据存储工厂类
 */
public class CacheFactory {
    private static volatile CacheFactory mCacheFactory;
    private CacheHandler mPreferencesCacheHolder,mMemoryCacheHolder,mDiskCacheHolder;
    private CacheFactory(){
    }
    public static CacheFactory getInstance(){
        if(mCacheFactory == null){
            synchronized (CacheFactory.class){
                if(mCacheFactory == null){
                    mCacheFactory = new CacheFactory();
                }
            }
        }
        return mCacheFactory;
    }
    public CacheHandler createCacheHandler(Class<? extends CacheHandler> cacheHandlerClass){
        if(cacheHandlerClass != null){
            try {
                return cacheHandlerClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public CacheHandler getPreferencesCacheHolder() {
        if(mPreferencesCacheHolder == null){
            mPreferencesCacheHolder = createCacheHandler(PreferenceCacheHandler.class);
        }
        return mPreferencesCacheHolder;
    }
    public CacheHandler getMemoryCacheHolder() {
        if(mMemoryCacheHolder == null){
            mMemoryCacheHolder = createCacheHandler(MemoryCacheHandler.class);
        }
        return mMemoryCacheHolder;
    }
    public CacheHandler getDiskCacheHolder() {
        if(mDiskCacheHolder == null){
            mDiskCacheHolder = createCacheHandler(DiskCacheHandler.class);
        }
        return mDiskCacheHolder;
    }

    /**
     * 默认
     * @return
     */
    public CacheHandler getDefaultCacheHolder(){
        return getPreferencesCacheHolder();
    }
}
