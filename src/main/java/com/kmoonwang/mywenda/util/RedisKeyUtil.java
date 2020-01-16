package com.kmoonwang.mywenda.util;

public class RedisKeyUtil {
    /**
     * 保证生成的Redis Key 不重复，如何不重复，那就是在不同的业务上加不同的前缀
     */
    private static String SPLIT = ":";//分隔符
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";

    public static String getLikeKey(int entityType,int entityId){
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDislikeKey(int entityType,int entityId){
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }
}
