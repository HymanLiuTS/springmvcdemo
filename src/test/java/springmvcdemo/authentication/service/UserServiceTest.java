package springmvcdemo.authentication.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import springmvcdemo.authentication.model.User;
import springmvcdemo.common.domain.WebAppContextBaseTest;

public class UserServiceTest extends WebAppContextBaseTest{
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	

	@Test
	public void findAllUsersTest() {
		List<User> users=userService.findAllUsers();
		assertTrue(users!=null);
		assertFalse(users.isEmpty());
	}
	
	
}
