package springmvcdemo.my;

import javax.annotation.Priority;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@Priority(2)
public class MyGlobalInitializerClass5 implements ApplicationContextInitializer{

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		// TODO Auto-generated method stub
		
	}

}
