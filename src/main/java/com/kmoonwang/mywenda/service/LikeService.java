package com.kmoonwang.mywenda.service;

import com.kmoonwang.mywenda.util.JedisAdapter;
import com.kmoonwang.mywenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public long getlikecount(int entityType,int entityId){
        String likekey = RedisKeyUtil.getLikeKey(entityType,entityId);
        return jedisAdapter.scard(likekey);//有多少个userId在集合里面
    }

    public int getLikeStatus(int userId,int entityType,int entityId){
        String likekey = RedisKeyUtil.getLikeKey(entityType,entityId);
        if(jedisAdapter.sismember(likekey,String.valueOf(userId))){
            return 1;
        }
        String dislikekey = RedisKeyUtil.getDislikeKey(entityType,entityId);
        return jedisAdapter.sismember(dislikekey,String.valueOf(userId))? -1:0;
    }

    public long like(int userId,int entityType,int entityId){
        String likekey = RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.sadd(likekey,String.valueOf(userId));

        //不可能同时有赞有踩，去踩里把它删掉
        String dislikekey = RedisKeyUtil.getDislikeKey(entityType,entityId);
        jedisAdapter.srem(dislikekey,String.valueOf(userId));

        //返回现在有多少人点赞
        return jedisAdapter.scard(likekey);
    }

    public long dislike(int userId,int entityType,int entityId){
        String dislikekey = RedisKeyUtil.getDislikeKey(entityType,entityId);
        jedisAdapter.sadd(dislikekey,String.valueOf(userId));

        //不可能同时有赞有踩，去踩里把它删掉
        String likekey = RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.srem(likekey,String.valueOf(userId));

        //返回现在有多少人cai
        return jedisAdapter.scard(likekey);
    }
}
