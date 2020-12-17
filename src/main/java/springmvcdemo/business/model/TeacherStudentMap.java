package springmvcdemo.business.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the teacher_student_map database table.
 * 
 */
@Entity
@Table(name = "teacher_student_map")
@NamedQuery(name = "TeacherStudentMap.findAll", query = "SELECT t FROM TeacherStudentMap t")
public class TeacherStudentMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "student_id")
	private Long studentId;

	@Column(name = "teacher_id")
	private Long teacherId;

	public TeacherStudentMap() {
	}

	public Long getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

}