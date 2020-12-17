package springmvcdemo.activiti.configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.activiti.engine.impl.jobexecutor.DefaultJobExecutor;
import org.activiti.engine.impl.jobexecutor.ManagedJobExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyManagedJobExecutor extends DefaultJobExecutor {

	private static Logger log = LoggerFactory.getLogger(ManagedJobExecutor.class);
	ThreadFactory threadFactory = Executors.defaultThreadFactory();

	@Override
	protected void startExecutingJobs() {
		if (threadFactory == null) {
			log.warn("A managed thread factory was not found, falling back to self-managed threads");
			super.startExecutingJobs();
		} else {
			if (threadPoolQueue == null) {
				threadPoolQueue = new ArrayBlockingQueue<Runnable>(queueSize);
			}
			if (threadPoolExecutor == null) {
				threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime,
						TimeUnit.MILLISECONDS, threadPoolQueue, threadFactory);
				threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
			}

			startJobAcquisitionThread();
		}
	}

}
