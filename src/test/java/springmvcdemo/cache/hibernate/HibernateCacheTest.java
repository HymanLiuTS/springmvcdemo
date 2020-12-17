package springmvcdemo.cache.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import springmvcdemo.authentication.model.User;
import springmvcdemo.authentication.repository.UserRepository;
import springmvcdemo.common.domain.WebAppContextBaseTest;

public class HibernateCacheTest extends WebAppContextBaseTest{

	// 通过PersistenceContext获取的是试题管理类对象
	@PersistenceContext(unitName = "authEntityManagerFactory")
	EntityManager em2;

	@Autowired
	@Qualifier("authEntityManagerFactory")
	private EntityManagerFactory emf;
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	/**
	 * hibernate一级缓存测试
	 * 1、一级缓存只针对同一个EntityManager有效，为session级别的缓存，一个EntityManager创建后开启一个session;
	 * 2、当应用程序调用Session的save()、update()、saveOrUpdate()、get()或load()，以及调用查询接口的 list()、iterate()(该方法会出现N+1问题，
	 *    先查id)方法时，如果在Session缓存中还不存在相应的对象，Hibernate就会把该对象加入到第一级缓存中。当清理缓存时，Hibernate会根据缓存中
	 *    对象的状态变化来同步更新数据库。 Session为应用程序提供了两个管理缓存的方法： evict(Object obj)：从缓存中清除参数指定的持久化对象。 
	 *    clear()：清空缓存中所有持久化对象,flush():使缓存与数据库同步。
	 * 3、当查询相应的字段，而不是对象时，不支持缓存。
	 * 4、通过iterator()方法来获得我们对象的时候，hibernate首先会发出1条sql去查询出所有对象的 id 值，当我们如果需要查询到某个对象的具体信息的时候，
	 *    hibernate此时会根据查询出来的 id 值再发sql语句去从数据库中查询对象的信息，这就是典型的 N+1 的问题。
	 */
	@Test
	public void findUsersByIDTest() {
		//
		EntityManager em = emf.createEntityManager();
		User user1 = em.find(User.class, (long)1);
		User user2 = em.find(User.class, (long)1);
		assertEquals(user1.getUsername(), user1.getUsername());
	}

	/**
	 * hibernate二级缓存测试
	 * 1、二级缓存跨多个EntityManager和多个session有效
	 * 2、在实体上使用javax.persistence.Cacheable注解或者org.hibernate.annotations.Cache注解
	 * 3、二级缓存缓存的仅仅是对象，如果查询出来的是对象的一些属性，则不会被加到缓存中去
	 * 4、二级缓存可以解决N+1问题，先用.list()查出所有数据并进行了缓存，再使用iterator()就不会出现N+1问题。
	 * 5、二级缓存会缓存 native sql语句查出来的结果（貌似只在原生的hibernate中有效，在jpa中无效），但是不会缓存 hql语句查出来的结果，如果缓存hql查询语句，需要配置查询缓存。
	 * 6、jpa方式下使用hibernate的二级缓存，貌似只有使用主键查询的情况下采用起作用
	 */
	@Test
	public void findUsersByID2Test() {
		Optional<User> user1 = userRepository.findById((long) 1);
		Optional<User> user2 = userRepository.findById((long) 1);
		assertTrue(user1 != null);
		assertTrue(user2 != null);
	}
	
	@Test
	public void findUsersByNameTest() {
		User user1 = userRepository.findByUsername("admin");
		User user2 = userRepository.findByUsername("admin");
		assertTrue(user1 != null);
		assertTrue(user2 != null);
	}
	
	@Test
	public void findAllTest() {
		List<User> users1 = userRepository.findAll();
		List<User> users2 = userRepository.findAll();
		assertTrue(users1 != null);
		assertTrue(users2 != null);
	}
	
	/**
	 * 1、jpa方式下使用hibernate的二级缓存，执行native sql和hql都没有进行缓存，执行hql时通过使用setHint可以进行缓存，但是执行native sql时，使用setHint报错。
	 * 2、通过setHint(QueryHints.HINT_CACHEABLE, true)开启了hibernate的查询缓存
	 * 3、查询缓存以hql作为查询的键值
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void findAll2Test() {
		//EntityManager执行hql语句
		List<User> users1 = em2.createQuery("select users_ from User users_").setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
		users1 = em2.createQuery("select users_ from User users_").setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
		List<User> users2 = em2.createQuery("from User").setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
		users2 = em2.createQuery("from User").setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
		List<User> users3 = em2.createNativeQuery("select * from users").getResultList();
		users3 = em2.createNativeQuery("select * from users").getResultList();
		assertTrue(users1 != null);
		assertTrue(users2 != null);
		assertTrue(users3 != null);
	}

}
