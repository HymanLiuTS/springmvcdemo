package springmvcdemo.my;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.PriorityOrdered;

import com.alibaba.fastjson.JSON;

public class MyGlobalInitializerClass implements ApplicationContextInitializer ,PriorityOrdered{

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		System.out.println("MyGlobalInitializerClass");
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
