package springmvcdemo.authentication.service;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springmvcdemo.authentication.dao.UserDao;
import springmvcdemo.authentication.model.Group;
import springmvcdemo.authentication.model.GroupUserMap;
import springmvcdemo.authentication.model.User;
import springmvcdemo.authentication.repository.GroupRepository;
import springmvcdemo.authentication.repository.GroupUserMapRepository;
import springmvcdemo.authentication.repository.UserRepository;

@Transactional("authTransactionManager")
@Service("userService")
public class UserService {

	//static Logger logger = LogManager.getRootLogger();
	protected final Log logger = LogFactory.getLog(getClass());


	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	@Autowired
	@Qualifier("groupRepository")
	private GroupRepository groupRepository;

	@Autowired
	@Qualifier("groupUserMapRepository")
	private GroupUserMapRepository groupUserMapRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	public List<User> findAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public User findByUsername(String userName) {
		logger.info("userName:" + userName);
		return userRepository.findByUsername(userName);
	}

	public User save(User user) {
		user.setEnabled(true);
		user.setPassword(encoder.encode(user.getPassword()));
		user = userRepository.save(user);
		return user;
	}


	public GroupUserMap putUserToGroup(String userName, String groupName) throws Exception {
		User user = userRepository.findByUsername(userName);
		if (user == null)
			throw new Exception("找不到该用户");
		Group group = groupRepository.findByGroupnameEquals(groupName);
		if (group == null)
			throw new Exception("找不到该用户组");

		GroupUserMap map = new GroupUserMap(group.getId().longValue(), user.getId());
		return groupUserMapRepository.save(map);
	}

	@Transactional
	public String[] findAllAuthorities(Integer id) {
		return userRepository.findAllAuthorities(id);
	}

}
