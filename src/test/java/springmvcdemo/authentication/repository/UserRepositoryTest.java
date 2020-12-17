package springmvcdemo.authentication.repository;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import springmvcdemo.authentication.model.User;
import springmvcdemo.common.domain.WebAppContextBaseTest;

public class UserRepositoryTest extends WebAppContextBaseTest {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	

	@Test
	public void findByUsernameTest() {
		User user = userRepository.findByUsername("admin");
		assertTrue(user != null);
		assertEquals(user.getUsername(), "admin");
	}

	

	
}
