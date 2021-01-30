package pro.artse.dal.util;

import redis.clients.jedis.*;

/**
 * @author Marija Communicates with Redis server.
 */
public class RedisConnector {

	/**
	 * Jedis pool configuration.
	 */
	public static final JedisPoolConfig POOL_CONFIG = buildPoolConfig();
	public static final int ERROR = 0;
	public static final int SUCCESS = 1;
	public static final String OK = "OK";
	public static final int START = 0;
	public static final int END = -1;

	/**
	 * Configures Jedis pool.
	 * 
	 * @return Configured Jedis pool.
	 */
	public static JedisPool createConnection() {
		String address = ConfigurationUtil.get("redisServer");
		int port = Integer.parseInt(ConfigurationUtil.get("redisPort"));
		return new JedisPool(address, port);
	}

	/**
	 * @return Configured Jedis pool.
	 */
	private static JedisPoolConfig buildPoolConfig() {
		final JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(128);
		poolConfig.setMaxIdle(128);
		poolConfig.setMinIdle(16);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setMinEvictableIdleTimeMillis(60000);
		poolConfig.setTimeBetweenEvictionRunsMillis(30000);
		poolConfig.setNumTestsPerEvictionRun(-1);
		poolConfig.setBlockWhenExhausted(true);
		return poolConfig;
	}
}
