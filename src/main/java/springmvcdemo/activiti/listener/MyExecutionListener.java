package springmvcdemo.activiti.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import springmvcdemo.authentication.model.User;
import springmvcdemo.authentication.service.UserService;
import springmvcdemo.courseselection.service.CourseSelectionService;

public class MyExecutionListener implements ExecutionListener {

	@Autowired
	@Qualifier("courseSelectionService")
	private CourseSelectionService courseSelectionService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	public void notify(DelegateExecution execution) throws Exception {
		String eventName = execution.getEventName();
		// start
		if ("start".equals(eventName)) {
			System.out.println("流程start=========");

		} else if ("end".equals(eventName)) {
			System.out.println("流程end=========");
		}
	}

}
