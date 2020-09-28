package com.common.constant;

/**
 *  缓存配置
 * @author kevin
 * @date 2020/9/15 11:00
 * @since 1.0.0
 */
public final class RedisCacheConstant {

    /**
     * 仓库缓存前缀
     */
    public static final String STORE_LOCATION_PREFIX = "STORE_LOCATION_CONFIG:";

    /**
     * 转运点缓存前缀
     */
    public static final String SHIPPING_SITE_PREFIX = "SHIPPING_SITE_CONFIG:";

    /**
     * 库区缓存前缀
     */
    public static final String STORE_AREA_PREFIX = "STORE_AREA_CONFIG:";

    /**
     * 获取仓库缓存的key
     * @param storeLocationCode 仓库编码
     * @return 缓存key
     */
    public static String getStoreLocation(String storeLocationCode) {
        return STORE_LOCATION_PREFIX + storeLocationCode;
    }

    /**
     * 获取转运点缓存的key
     * @param shippingSiteCode 转运点编码
     * @return 缓存key
     */
    public static String getShippingSite(String shippingSiteCode) {
        return SHIPPING_SITE_PREFIX + shippingSiteCode;
    }

    /**
     * 获取仓库缓存的key
     * @param storeAreaCode 库区编码
     * @return 缓存key
     */
    public static String getStoreArea(String storeAreaCode) {
        return STORE_AREA_PREFIX + storeAreaCode;
    }

}
