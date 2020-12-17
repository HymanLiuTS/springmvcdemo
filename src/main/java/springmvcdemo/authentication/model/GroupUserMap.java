package springmvcdemo.authentication.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the group_user_map database table.
 * 
 */
@Entity
@Table(name = "group_user_map", uniqueConstraints = {
		@UniqueConstraint(name = "gid_uid_un", columnNames = { "group_id", "user_id" }) })
@NamedQuery(name = "GroupUserMap.findAll", query = "SELECT g FROM GroupUserMap g")
public class GroupUserMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "group_id")
	private Long groupId;

	@Column(name = "user_id")
	private Long userId;

	public GroupUserMap() {
	}

	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public GroupUserMap(Long groupId, Long userId) {
		this.userId = userId;
		this.groupId = groupId;
	}
}