package springmvcdemo.my;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

public class MyGlobalInitializerClass2 implements ApplicationContextInitializer,Ordered{

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		System.out.println("MyGlobalInitializerClass2");
		
	}

}
