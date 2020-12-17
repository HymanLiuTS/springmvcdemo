package springmvcdemo.authentication.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import springmvcdemo.authentication.model.User;

public interface UserDao {
	public List<User> findAllUsers();
	public User findByUsername(String userName);
	public User insertUser(User user);
	public User deleteUser(String userName);
}
