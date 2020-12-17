package springmvcdemo.cache.springcache;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;

import springmvcdemo.business.model.Student;
import springmvcdemo.common.domain.WebAppContextBaseTest;
import springmvcdemo.courseselection.service.CourseSelectionService;

public class SpringCacheTest extends WebAppContextBaseTest{

	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	@Qualifier("courseSelectionService")
	private CourseSelectionService courseSelectionService;
	
	@Test
	public void ConcurrentMapCacheManagerTest(){
		System.out.println(cacheManager.getCacheNames());
	}

	@Test
	public void findByIdTest() {
		Student student=new Student();
		student.setName("Hyman");
		student.setAge(18);
		student=courseSelectionService.saveStudent(student);
		student=courseSelectionService.findById((long) 29);
		//��������������ջ��棬���Ǵ����ݿ��ѯ2��
		student=courseSelectionService.saveStudent(student);
		student=courseSelectionService.findById((long) 29);
	}

}
