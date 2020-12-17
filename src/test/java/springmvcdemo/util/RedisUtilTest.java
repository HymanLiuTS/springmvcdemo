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
	 * �����
	 */
	@Test
	public void test1() {
		String name = "name";
		String value = "qq";
		jedis.set(name, value);
		System.out.println("׷��ǰ��" + jedis.get(name)); // ׷��ǰ��qq

		// ��ԭ��ֵ�û��������,����֮ǰû�и�key�������key
		jedis.append(name, "ww");
		System.out.println("׷�Ӻ�" + jedis.get(name)); // ׷�Ӻ�qqww

		jedis.append("id", "ee");
		System.out.println("û��key��" + jedis.get(name));
		System.out.println("get��key��" + jedis.get("id"));

	}

	/**
	 * mset �����ö��key-valueֵ ������key1,value1,key2,value2,...,keyn,valuen�� mget
	 * �ǻ�ȡ���key����Ӧ��valueֵ ������key1,key2,key3,...,keyn�� ���ص��Ǹ�list
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
		// ����һ��map
		jedis.hmset("user", map);

		// map key�ĸ���
		System.out.println("map��key�ĸ���" + jedis.hlen("user"));

		// map key
		System.out.println("map��key" + jedis.hkeys("user"));

		// map value
		System.out.println("map��value" + jedis.hvals("user"));

		// (String key, String... fields)����ֵ��һ��list
		List<String> list = jedis.hmget("user", "age", "name");
		System.out.println("redis��key�ĸ��� fieldsֵ��" + jedis.hmget("user", "age", "name") + list.size());

		// ɾ��map�е�ĳһ���� ��ֵ password
		// ��Ȼ (key, fields) Ҳ�����Ƕ��fields
		jedis.hdel("user", "age");

		System.out.println("ɾ����map��key" + jedis.hkeys("user"));

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
		System.out.println(jedis.lpop("list")); // ջ��
		jedis.del("list");
	}

	/**
	 * �Զ������ UserΪ�� id name
	 * 
	 * RedisTemplate ���� ���л��ͷ����л�
	 * �磺template.getStringSerializer().serialize("name")
	 */
	@Test
	public void test5() {
		User user = new User();
		user.setId(123);
		user.setName("fighter");

		// ����һ�� user����
		// byte[] key = "user".getBytes();
		// byte[] val = SerializationUtil.serialize(user);
		try {
			RedisUtil.init();
			Jedis jedis1 = RedisUtil.getJedis();
			jedis1.set("user".getBytes(), SerializationUtil.serialize(user));
			// ��ȡ
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
			System.out.println(String.format("�洢��ʱ��%d����", end - start));
			// ��ȡ
			for (Integer i = 1; i <= 100000; i++) {
				List<String> strs = jedis1.hmget("bo_account", i.toString());
				if (strs != null && strs.isEmpty() == false) {
					String str=strs.get(0);
					List<Map<String, Object>> list = JSONObject.parseObject(str, new TypeReference<List<Map<String, Object>>>() {});
					Map<String, Object> child=list.get(0);
					//System.out.println(child.get("id") + ":" + child.get("name"));
				}

			}
			System.out.println(String.format("��ȡ��ʱ��%d����", System.currentTimeMillis() - end));
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
		System.out.println(String.format("�洢��ʱ��%d����", end - start));
		// ��ȡ
		for (Integer i = 1; i <= 100000; i++) {
			byte[] bs = jedis1.get(i.toString().getBytes());
			User desUser = (User) SerializationUtil.deserialize(bs);
			if (desUser != null) {
				
				//System.out.println(child.get("id") + ":" + child.get("name"));
			}

		}
		System.out.println(String.format("��ȡ��ʱ��%d����", System.currentTimeMillis() - end));
		RedisUtil.returnResource(jedis1);
	}

	@After
	public void tearDown() throws Exception {
		RedisUtil.returnResource(jedis);
	}
}
