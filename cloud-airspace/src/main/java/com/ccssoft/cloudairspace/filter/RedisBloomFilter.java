package com.ccssoft.cloudairspace.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.util.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.hash;


/**
 * @author moriarty
 * @date 2020/6/11 12:55
 */
@ConfigurationProperties("bloom.filter")
@Component
@Data
public class RedisBloomFilter {
    /**
     * 预计插入量
     */
    private long expectedInsertions;
    /**
     * 误判率
     */
    private double fpp;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * bit数组长度
     */
    private long numBits;

    /**
     * hash函数数量
     */
    private int numHashFunctions;

    @PostConstruct//初始化类的时候会回调这个方法
    public void init () {
        this.numBits = optomalNumOfBits(expectedInsertions, fpp);
        this.numHashFunctions = optimalNumOfHashFunctions(expectedInsertions,numBits);
    }

    private long optomalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (long)(-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    private int optimalNumOfHashFunctions (long n, long m) {
        return Math.max(1,(int) Math.round((double) m/n * Math.log(2)));
    }


    public void put (String key) {
        long[] indexs = getIndexs(key);
        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Nullable
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                for (long index : indexs) {
                    redisConnection.setBit(("bf:"+key).getBytes(),index,true);
                }
                redisConnection.close();
                return null;
            }
        });
    }

    public boolean isExist (String key) {
        long[] indexs = getIndexs(key);
        List list = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                for (long index :indexs) {
                    redisConnection.getBit(("bf:"+key).getBytes(),index);
                }
                redisConnection.close();
                return null;
            }
        });
        return !list.contains(false);
    }

    private long[] getIndexs(String key) {
        long hash1 = hash(key);
        long hash2 = hash1 >>> 16;
        long[] result = new long[numHashFunctions];

        for (int i = 0;i<numHashFunctions; i++) {
            long combinedHash = hash1 + i*hash2;
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            result[i] = combinedHash % numBits;
        }
        return result;
    }
}
