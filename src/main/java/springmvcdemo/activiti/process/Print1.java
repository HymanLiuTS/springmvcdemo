package springmvcdemo.activiti.process;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import springmvcdemo.business.model.Teacher;
import springmvcdemo.courseselection.service.CourseSelectionService;
import springmvcdemo.util.ThreadPool;

@Service("print1")
public class Print1 implements JavaDelegate {

	@Autowired
	@Qualifier("courseSelectionService")
	private CourseSelectionService courseSelectionService;

	public void execute(DelegateExecution execution) throws Exception {
		try {
			Long mi=System.currentTimeMillis();
			System.out.println(String.format("%d:%d print1 begin", mi,Thread.currentThread().getId()));
			Thread.sleep(120*1000);
			mi=System.currentTimeMillis();
			System.out.println(String.format("%d:%d print1 end", mi,Thread.currentThread().getId()));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}

	}

}
