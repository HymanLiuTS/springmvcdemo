package springmvcdemo.activiti.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import springmvcdemo.authentication.model.User;

public class MyTask1Listener implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		String eventName = delegateTask.getEventName();
		if ("start".equals(eventName)) {
			System.out.println("≤Ω÷Ë1 start=========");
		} else if ("end".equals(eventName)) {
			System.out.println("≤Ω÷Ë1 end=========");
		}

	}

}
