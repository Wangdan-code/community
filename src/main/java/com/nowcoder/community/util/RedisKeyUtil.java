package com.nowcoder.community.util;

public class RedisKeyUtil {
    private static final String SPLIT = ":";// 用:隔开
    private static final String PREFIX_ENTITY_LIKE = "like:entity";//赞的前缀

    // 某个实体的赞 传入实体的类型和id
    // like:entity:entityType:entityId -> set集合中装入userId
    public static String getEntityLikeKey(int entityType,int entityId){
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
}
