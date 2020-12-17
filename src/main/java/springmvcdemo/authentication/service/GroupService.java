package springmvcdemo.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springmvcdemo.authentication.model.Group;
import springmvcdemo.authentication.repository.GroupRepository;



@Transactional("authTransactionManager")
@Service("groupService")
public class GroupService {

	@Autowired
	@Qualifier("groupRepository")
	private GroupRepository groupRepository;
	
	@Transactional
	public Group save(Group group){
		return groupRepository.save(group);
	}
}
