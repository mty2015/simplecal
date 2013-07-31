package cnblogs.jcli.calendar.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JRedisClientFactory {

    private static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
    
    public static Jedis getJRedisClient(){
        return pool.getResource();
        
    }
}
