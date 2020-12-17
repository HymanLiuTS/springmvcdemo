package springmvcdemo.activiti.controller;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvcdemo.authentication.model.User;
import springmvcdemo.authentication.service.UserService;
import springmvcdemo.common.domain.Result;
import springmvcdemo.courseselection.service.CourseSelectionService;

@Controller
public class activitiController {
	
	public activitiController(){
		System.out.print("activitiController");
	}

	@Autowired
	ProcessEngine processEngine;

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	@Qualifier("courseSelectionService")
	private CourseSelectionService courseSelectionService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@RequestMapping(value = "/activiti/start", method = RequestMethod.GET)
	@ResponseBody
	public Result startProcess() {
		Result result = new Result(true, "", null);
		try {
			// ProcessInstance pi =
			// runtimeService.startProcessInstanceByKey("myProcess");
			Thread t = new Thread("myThread") {
				@Override
				public void run() {
					while (true) {
						try {
							
							List<User> users = userService.findAllUsers();
							if (users != null && users.isEmpty() == false)
								System.out.println(String.format("User: %s", users.get(0).getUsername()));
							else
								System.out.println("获取不到User。");
							Thread.sleep(10000);
						} catch (Exception ex) {
							System.out.println("获取User异常。");
						}
						
					}

				}
			};
			t.start();
			result.setData(t.getName());
		} catch (Exception ex) {
			result.setSucceed(false);
			result.setMsg(ex.getMessage());
		}
		return result;
	}

}
