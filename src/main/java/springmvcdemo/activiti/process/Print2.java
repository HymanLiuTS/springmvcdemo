package springmvcdemo.activiti.process;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import springmvcdemo.authentication.model.User;
import springmvcdemo.authentication.service.UserService;

@Service("print2")
public class Print2 implements JavaDelegate {
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	public void execute(DelegateExecution execution) throws InterruptedException {
		Long mi=System.currentTimeMillis();
		System.out.println(String.format("%d:%d print2 begin", mi,Thread.currentThread().getId()));
		Thread.sleep(60*50*1000);
		mi=System.currentTimeMillis();
		System.out.println(String.format("%d:%d print2 end", mi,Thread.currentThread().getId()));
	}
}
