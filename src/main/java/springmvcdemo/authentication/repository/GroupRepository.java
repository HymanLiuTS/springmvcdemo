package springmvcdemo.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.stereotype.Repository;

import springmvcdemo.authentication.model.Group;
import springmvcdemo.authentication.model.User;

@Repository("groupRepository")
public interface GroupRepository extends JpaRepository<Group, Long>{
	public Group findByGroupnameEquals(String groupName);
}
