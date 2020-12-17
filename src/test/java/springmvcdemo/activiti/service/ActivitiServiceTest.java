package springmvcdemo.activiti.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

import springmvcdemo.authentication.model.User;
import springmvcdemo.common.domain.Result;
import springmvcdemo.common.domain.WebAppContextBaseTest;

public class ActivitiServiceTest extends WebAppContextBaseTest {

	@Autowired
	ProcessEngine processEngine;

	@Autowired
	RepositoryService repositoryService;

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	ManagementService managementService;

	@Test
	public void processEngineTest() throws Exception {
		ProcessInstance pi = null;
		pi = runtimeService.startProcessInstanceByKey("PrintProcess1");
		//pi = runtimeService.startProcessInstanceByKey("PrintProcess2");
		System.out.print(pi.toString());
	}

}
