package springmvcdemo.datasource.druid;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import springmvcdemo.common.domain.WebAppContextBaseTest;

public class DruidDatasourceTest extends WebAppContextBaseTest {

	public class PrintRunner implements Runnable {

		public void run() {
			EntityManager em = emf.createEntityManager();
			int size = em.createNativeQuery("select * from users").getResultList().size();
			System.out.println(String.format("%s print:%d", Thread.currentThread().getName(), 0));

		}
	}

	@Autowired
	@Qualifier("authEntityManagerFactory")
	private EntityManagerFactory emf;

	@Test
	public void ConnectionTest() {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
		ses.scheduleAtFixedRate(new PrintRunner(), 0, 1, TimeUnit.SECONDS);
		for (int i = 0; i < 2; i++) {
			try {
				Thread.sleep(1000);
				EntityManager em = emf.createEntityManager();
				em.createNativeQuery("select * from users").getResultList();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
