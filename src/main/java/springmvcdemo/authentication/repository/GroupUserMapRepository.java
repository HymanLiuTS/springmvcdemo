package springmvcdemo.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springmvcdemo.authentication.model.GroupUserMap;

@Repository("groupUserMapRepository")
public interface GroupUserMapRepository extends JpaRepository<GroupUserMap, Long> {

}
