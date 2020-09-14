package com.zzxx.travel.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class testDemo {
    @Test
    public void jedisPoolTest() {
        // 1. 创建连接池配置对象，设置配置项
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 1.1 最⼤连接数
        poolConfig.setMaxTotal(30);
        // 1.2 最⼤空闲连接数
        poolConfig.setMaxIdle(10);

        // 2. 获得连接池
        JedisPool pool = new JedisPool(poolConfig , "192.168.56.101", 6379);

        // 3.获得核⼼对象
        Jedis jedis = pool.getResource();
        // 4.设置数据
        jedis.set("name", "mafan");
        // 5.获得数据
        String name = jedis.get("name");
        System.out.println(name);

        // 6.释放资源
        jedis.close();
        // 虚拟机关闭时，释放pool资源
        pool.close();
    }
}
