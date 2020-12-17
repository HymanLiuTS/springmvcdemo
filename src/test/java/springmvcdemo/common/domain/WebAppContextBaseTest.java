package springmvcdemo.common.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
// @ContextConfiguration(classes = {WebMvcConfig.class, MockDataConfig.class})
@ContextConfiguration(locations = { "classpath:springmvc-servlet.xml", "classpath:applicationContext.xml",
		"classpath:hibernate/datasource.xml", "classpath:hibernate/authhibernate.xml",
		"classpath:activiti/activiti.config.xml", "classpath:hibernate/bueinesshibernate.xml",
		"classpath:activiti/datasource.xml" })
public class WebAppContextBaseTest {

	@Autowired
	public WebApplicationContext context;

	protected MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void test() {

	}
}
