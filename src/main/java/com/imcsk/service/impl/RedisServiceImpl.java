package com.imcsk.service.impl;

import com.imcsk.entity.RedisInfo;
import com.imcsk.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.*;

/**
 * @author csk
 * @date 2021/4/2
 */
@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static Map<String, String> map = new LinkedHashMap<>();

    static {
        map.put("redis_version", "Redis 服务器版本");
        map.put("os", "Redis 服务器的宿主操作系统");
        map.put("process_id", "服务器进程的 PID");
        map.put("tcp_port", "TCP/IP 监听端口");
        map.put("redis_mode", "运行模式，单机（standalone）或者集群（cluster）");
        map.put("executable", "server脚本目录");
        map.put("config_file", "配置文件目录");
        map.put("instantaneous_input_kbps", "redis网络入口kps");
        map.put("instantaneous_output_kbps", "redis网络出口kps");
        map.put("connected_clients", "已连接客户端的数量（不包括通过从属服务器连接的客户端）");
        map.put("uptime_in_days", "自 Redis 服务器启动以来，经过的天数");
        map.put("aof_enabled", "是否开启了aof，0-未启用，1-启用");
        map.put("role", "实例的角色，是master or slave");
    }

    private RedisConnection execute() {
        return (RedisConnection) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection;
            }
        });
    }

    @Override
    public List<RedisInfo> getRedisInfo() {
        try {
            List<RedisInfo> list = new ArrayList<>();
            Properties info = execute().info();

            for(String key : map.keySet()){
                RedisInfo redisInfo = new RedisInfo();
                redisInfo.setKey(key);
                redisInfo.setValue(info.getProperty(key));
                redisInfo.setDescription(map.get(key));
                list.add(redisInfo);
            }
            return list;
        }catch (Exception e){
            return new ArrayList<>();
        }

    }

    @Override
    public Map<String, Object> getRedisMemory() {
        return getData("memory",execute().info().get("used_memory"));
    }

    @Override
    public Map<String, Object> getRedisDbSize() {
        return getData("dbsize",execute().dbSize());
    }

    public Map<String, Object> getRedisCommand() {
        return getData("dbsize",execute().info("commandstats"));
    }

    private Map<String, Object> getData(String name, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("create_time", (new Date()).getTime());
        map.put(name, data);
        return map;
    }
}
