package springmvcdemo.util;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import redis.clients.jedis.Jedis;

public class RedisUtilTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3595533237761477418L;

	public class User implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1530813282496676263L;
		private Integer id;
		private String name;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	Jedis jedis = null;

	@Before
	public void before() {
		RedisUtil.init();
		Jedis jedis = RedisUtil.getJedis();
	}

	@Test
	public void print() {

		jedis.append("Account-12345678", "{\"name\":\"hyman\",\"age\":123}");
		String json = jedis.get("Account-12345678");
		jedis.del("Account-12345678");
		json = jedis.get("Account-12345678");
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("name", "fujianchao");
		map.put("password", "123");
		map.put("age", "12");
		jedis.hmset("user", map);
		jedis.del("user");
	}

	/**
	 * 简单添加
	 */
	@Test
	public void test1() {
		String name = "name";
		String value = "qq";
		jedis.set(name, value);
		System.out.println("追加前：" + jedis.get(name)); // 追加前：qq

		// 在原有值得基础上添加,如若之前没有该key，则导入该key
		jedis.append(name, "ww");
		System.out.println("追加后：" + jedis.get(name)); // 追加后：qqww

		jedis.append("id", "ee");
		System.out.println("没此key：" + jedis.get(name));
		System.out.println("get此key：" + jedis.get("id"));

	}

	/**
	 * mset 是设置多个key-value值 参数（key1,value1,key2,value2,...,keyn,valuen） mget
	 * 是获取多个key所对应的value值 参数（key1,key2,key3,...,keyn） 返回的是个list
	 */
	@Test
	public void test2() {
		jedis.mset("name1", "aa", "name2", "bb", "name3", "cc");
		System.out.println(jedis.mget("name1", "name2", "name3"));
	}

	/**
	 * map
	 */
	@Test
	public void test3() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "fujianchao");
		map.put("password", "123");
		map.put("age", "12");
		// 存入一个map
		jedis.hmset("user", map);

		// map key的个数
		System.out.println("map的key的个数" + jedis.hlen("user"));

		// map key
		System.out.println("map的key" + jedis.hkeys("user"));

		// map value
		System.out.println("map的value" + jedis.hvals("user"));

		// (String key, String... fields)返回值是一个list
		List<String> list = jedis.hmget("user", "age", "name");
		System.out.println("redis中key的各个 fields值：" + jedis.hmget("user", "age", "name") + list.size());

		// 删除map中的某一个键 的值 password
		// 当然 (key, fields) 也可以是多个fields
		jedis.hdel("user", "age");

		System.out.println("删除后map的key" + jedis.hkeys("user"));

	}

	/**
	 * list
	 */
	@Test
	public void test4() {

		jedis.lpush("list", "aa");
		jedis.lpush("list", "bb");
		jedis.lpush("list", "cc");
		System.out.println(jedis.lrange("list", 0, -1));
		System.out.println(jedis.lrange("list", 0, 1));
		System.out.println(jedis.lpop("list")); // 栈顶
		jedis.del("list");
	}

	/**
	 * 自定义对象 User为例 id name
	 * 
	 * RedisTemplate 中有 序列化和反序列化
	 * 如：template.getStringSerializer().serialize("name")
	 */
	@Test
	public void test5() {
		User user = new User();
		user.setId(123);
		user.setName("fighter");

		// 存入一个 user对象
		// byte[] key = "user".getBytes();
		// byte[] val = SerializationUtil.serialize(user);
		try {
			RedisUtil.init();
			Jedis jedis1 = RedisUtil.getJedis();
			jedis1.set("user".getBytes(), SerializationUtil.serialize(user));
			// 获取
			byte[] bs = jedis1.get("user".getBytes());
			User desUser = (User) SerializationUtil.deserialize(bs);
			System.out.println(desUser.getId() + ":" + desUser.getName());
			RedisUtil.returnResource(jedis1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test6() {
		try {
			RedisUtil.init();
			Jedis jedis1 = RedisUtil.getJedis();
			Long start = System.currentTimeMillis();
			Map<String, String> map = new ConcurrentHashMap<String, String>();
			for (Integer i = 1; i <= 100000; i++) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Map<String, Object> child = new HashMap<String, Object>();
				child.put("id", i);
				child.put("name", "student" + i);
				list.add(child);
				map.put(i.toString(), JSON.toJSONString(list));
			}
			jedis1.hmset("bo_account", map);
			Long end = System.currentTimeMillis();
			System.out.println(String.format("存储耗时：%d毫秒", end - start));
			// 获取
			for (Integer i = 1; i <= 100000; i++) {
				List<String> strs = jedis1.hmget("bo_account", i.toString());
				if (strs != null && strs.isEmpty() == false) {
					String str=strs.get(0);
					List<Map<String, Object>> list = JSONObject.parseObject(str, new TypeReference<List<Map<String, Object>>>() {});
					Map<String, Object> child=list.get(0);
					//System.out.println(child.get("id") + ":" + child.get("name"));
				}

			}
			System.out.println(String.format("读取耗时：%d毫秒", System.currentTimeMillis() - end));
			RedisUtil.returnResource(jedis1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void test7() {
		RedisUtil.init();
		Jedis jedis1 = RedisUtil.getJedis();
		Long start = System.currentTimeMillis();
		
		for (Integer i = 1; i <= 100000; i++) {
			User user = new User();
			user.setId(i);
			user.setName("fighter"+i);
			jedis1.set(i.toString().getBytes(), SerializationUtil.serialize(user));
		}
		Long end = System.currentTimeMillis();
		System.out.println(String.format("存储耗时：%d毫秒", end - start));
		// 获取
		for (Integer i = 1; i <= 100000; i++) {
			byte[] bs = jedis1.get(i.toString().getBytes());
			User desUser = (User) SerializationUtil.deserialize(bs);
			if (desUser != null) {
				
				//System.out.println(child.get("id") + ":" + child.get("name"));
			}

		}
		System.out.println(String.format("读取耗时：%d毫秒", System.currentTimeMillis() - end));
		RedisUtil.returnResource(jedis1);
	}

	@After
	public void tearDown() throws Exception {
		RedisUtil.returnResource(jedis);
	}
}
