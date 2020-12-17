package springmvcdemo.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import springmvcdemo.business.model.Student;
import springmvcdemo.business.repository.StudentRepository;

@Service("studentService")
public class StudentService {

	@Autowired
	@Qualifier("studentRepository")
	StudentRepository studentRepository;
	
	public Student addStudent(String name,int age){
		Student stu=new Student();
		stu.setAge(age);
		stu.setName(name);
		stu = studentRepository.save(stu);
		return stu;
	}
}
