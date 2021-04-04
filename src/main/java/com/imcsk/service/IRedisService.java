package com.imcsk.service;

import com.imcsk.entity.RedisInfo;

import java.util.List;
import java.util.Map;

/**
 * @author csk
 * @date 2021/4/2
 */
public interface IRedisService {

    /**
     * 获取Redis基础info列表
     *
     * @return
     */
    List<RedisInfo> getRedisInfo();

    /**
     * 获取Redis内存占用信息
     *
     * @return
     */
    Map<String, Object> getRedisMemory();

    /**
     * 获取Redis key的数量 --dbsize
     *
     * @return
     */
    Map<String, Object> getRedisDbSize();
}
