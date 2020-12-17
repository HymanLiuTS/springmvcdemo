package springmvcdemo.util;

import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

public class SubThread implements Runnable {
	private RuntimeService runtimeService;
	private Map<String, Object> map;
	private String processKey;

	public SubThread(Map<String, Object> map, RuntimeService runtimeService, String processKey) {
		this.map = map;
		this.runtimeService = runtimeService;
		this.processKey = processKey;
	}

	public void run() {
		try {
			ProcessInstance pi = runtimeService.startProcessInstanceByKey(processKey, map);
			String id = pi.getActivityId();
		} catch (Exception e) {
			
		}
	}


}