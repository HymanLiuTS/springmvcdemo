package springmvcdemo.courseselection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvcdemo.authentication.model.User;
import springmvcdemo.authentication.repository.UserRepository;
import springmvcdemo.business.model.Student;
import springmvcdemo.business.model.Teacher;
import springmvcdemo.business.repository.StudentRepository;
import springmvcdemo.business.repository.TeacherRepository;
import springmvcdemo.common.domain.Result;

@Transactional("businessTransactionManager")
@Service("courseSelectionService")
public class CourseSelectionService {

	@Autowired
	@Qualifier("studentRepository")
	private StudentRepository studentRepository;
	
	@Autowired
	@Qualifier("teacherRepository")
	private TeacherRepository teacherRepository;
	
	@CacheEvict(cacheNames={"defaultCache"},key="#student.getId()")
	public Student saveStudent(Student student) {
		//student = this.studentRepository.save(student);
		return this.studentRepository.save(student);
	}

	@Cacheable(cacheNames={"defaultCache"})
	public Student findById(Long id) {
		return studentRepository.findById(id).get();
	}
	
	@CacheEvict(cacheNames={"defaultCache"})
	public void deleteAllStudents() {
		this.studentRepository.deleteAll();
	}
	
	@CacheEvict(cacheNames={"defaultCache"},key="#teacher.getId()")
	public Teacher saveTeacher(Teacher teacher) {
		//student = this.studentRepository.save(student);
		return this.teacherRepository.save(teacher);
	}
	
	@CacheEvict(cacheNames={"defaultCache"})
	public void deleteAllTeachers() {
		this.teacherRepository.deleteAll();
	}
}
