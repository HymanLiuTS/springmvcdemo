package springmvcdemo.authentication.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import springmvcdemo.authentication.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User,Long>{

	public User findByUsername(String username);
	
	@Query(value = "select u.authorities,g.authorities from User u,Group g where u.id=?1 and g.id in (select groupId from GroupUserMap m where userId = ?1)", nativeQuery = false)
	//@Cacheable(value="sampleCache1")
	public String[] findAllAuthorities(Integer id);
}
