package springmvcdemo.my;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

public class MyGlobalInitializerClass1 implements ApplicationContextInitializer,Ordered{

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		System.out.println("MyGlobalInitializerClass1");
		
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
