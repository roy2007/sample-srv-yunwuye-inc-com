package com.yunwuye.sample.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Roy
 *
 */
public class LocalCache {
    @SuppressWarnings("rawtypes")
    private static Map<String, CacheData> CACHE_DATA = new ConcurrentHashMap<>();

    public static <T> T getData(String key, Load<T> load, int expire) {
        T data = getData(key);
        if (data == null && load != null) {
            data = load.load();
            if (data != null) {
                setData(key, data, expire);
            }
        }
        return data;
    }

    public static <T> T getData(String key) {
        @SuppressWarnings("unchecked")
        CacheData<T> data = CACHE_DATA.get(key);
        if (data != null && (data.getExpire() <= 0 || data.getSifeTime() >= System.currentTimeMillis())) {
            return data.getData();
        }
        return null;
    }

    public static <T> void setData(String key, T data, int expire) {
        CACHE_DATA.put(key, new CacheData<T>(data, expire));
    }

    public static void remove(String key) {
        CACHE_DATA.remove(key);
    }

    public static void clearAll() {
        CACHE_DATA.clear();
    }

    public interface Load<T> {
        T load();
    }

    private static class CacheData<T> {
        CacheData(T t, int expire) {
            this.data = t;
            this.expire = expire <= 0 ? 0 : expire * 1000;
            this.sifeTime = System.currentTimeMillis() + this.expire;
        }

        private T data;
        private long sifeTime;
        private long expire;

        /**
         * @return the data
         */
        public T getData() {
            return data;
        }

        /**
         * @return the sifeTime
         */
        public long getSifeTime() {
            return sifeTime;
        }

        /**
         * @return the expire
         */
        public long getExpire() {
            return expire;
        }
    }

    /**
     * 每隔10秒获取一次
     * 
     * @param operatorInfo
     * @param parameterMap
     * @return
     */
    @SuppressWarnings("unused")
    private String getUserCreationGroupsCount(String key, Map<String, Object> parameterMap) {
        String mymakes = LocalCache.getData(key, new LocalCache.Load<String>() {
            @Override
            public String load() {
                String mymakes = "";// XXXXService.queryCountUsers(parameterMap);
                return mymakes;
            }
        }, 10);
        return mymakes;
    }
}
