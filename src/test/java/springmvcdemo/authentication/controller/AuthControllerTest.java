package springmvcdemo.authentication.controller;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import springmvcdemo.authentication.model.User;
import springmvcdemo.authentication.service.GroupService;
import springmvcdemo.authentication.service.UserService;
import springmvcdemo.common.domain.WebAppContextBaseTest;
import springmvcdemo.common.domain.Result;

import static org.hamcrest.Matcher.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends WebAppContextBaseTest {

	@Mock
	private UserService userService;

	@Mock
	private GroupService groupService;

	@InjectMocks
	private AuthController authController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void addUserTest() throws Exception {
		User user=new User(1, "hyman", "123", "ADMIN", true);
		Result result=new Result(true,"",Arrays.asList(user));
		when(userService.findAllUsers()).thenReturn(Arrays.asList(user));
		this.mockMvc.perform(get("/user"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json(JSONObject.toJSONString(result)));
	}

}
