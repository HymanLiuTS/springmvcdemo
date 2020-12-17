package springmvcdemo.activiti.configuration;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.activiti.engine.impl.cmd.AcquireJobsCmd;
import org.activiti.engine.impl.jobexecutor.AcquireJobsRunnableImpl;
import org.activiti.engine.impl.jobexecutor.DefaultJobExecutor;
import org.activiti.engine.impl.jobexecutor.ExecuteJobsRunnable;
import org.activiti.engine.impl.jobexecutor.JobExecutor;
import org.activiti.engine.impl.jobexecutor.JobExecutorContext;
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class MyJobExecutor extends JobExecutor {

	private static Logger log = LoggerFactory.getLogger(DefaultJobExecutor.class);
	  
	  protected int queueSize = 3;
	  protected int corePoolSize = 2;
	  protected int maxPoolSize = 3;
	  protected long keepAliveTime = 0L;
	  protected int lockTimeInMillis = 1 * 60 * 1000;
	  //jobExecutor.getLockTimeInMillis();
	  public int getLockTimeInMillis() {
		    return lockTimeInMillis;
	  }
	 
	  protected BlockingQueue<Runnable> threadPoolQueue;
	  protected ThreadPoolExecutor threadPoolExecutor;
	    
	  protected void startExecutingJobs() {
	    if (threadPoolQueue==null) {
	      threadPoolQueue = new ArrayBlockingQueue<Runnable>(queueSize);
	      System.out.println("new ArrayBlockingQueue<Runnable>(queueSize)");
	    }
	    if (threadPoolExecutor==null) {
	      threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, threadPoolQueue);      
	      threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
	      System.out.println("new ThreadPoolExecutor");
	    }
	    startJobAcquisitionThread(); 
	  }
	    
	  protected void stopExecutingJobs() {
	    stopJobAcquisitionThread();
	    
	    // Ask the thread pool to finish and exit
	    threadPoolExecutor.shutdown();

	    // Waits for 1 minute to finish all currently executing jobs
	    try {
	      if(!threadPoolExecutor.awaitTermination(60L, TimeUnit.SECONDS)) {
	        log.warn("Timeout during shutdown of job executor. "
	                + "The current running jobs could not end within 60 seconds after shutdown operation.");        
	      }              
	    } catch (InterruptedException e) {
	      log.warn("Interrupted while shutting down the job executor. ", e);
	    }

	    threadPoolExecutor = null;
	  }
	  
	  protected void ensureInitialization() { 
		  	if (acquireJobsCmd == null) {
		  		acquireJobsCmd = new MyAcquireJobsCmd(this);
		  	}
		  	if (acquireJobsRunnable == null) {
		  		acquireJobsRunnable = new AcquireJobsRunnableImpl(this);
		  	}
	}
	  
	  public void executeJobs(List<String> jobIds) {
	    try {
	      System.out.println(String.format("当前线程池活动线程个数：%d,任务队列等待任务个数：%d:",threadPoolExecutor.getActiveCount(),threadPoolQueue.size()));
	      if(jobIds!=null&&jobIds.isEmpty()==false){
	    	  System.out.println(String.format("开始执行job：%s", JSON.toJSONString(jobIds)));
	      }
	      //List<String> activingJobs=((MyAcquireJobsCmd)this.acquireJobsCmd).getActiveJobs();
	      /*if(jobIds!=null){
		    	Iterator<String> it=jobIds.iterator();
		 	    while(it.hasNext()){
		 	    	String jb=(String)it.next();
		 	    	if(activingJobs.contains(jb)){
		 	    		it.remove();
		 	    		 threadPoolExecutor.execute(new Runnable(){
		 					@Override
		 					public void run() {
		 						System.out.print(String.format("已存在id为%s的job，不执行该job。",jb));
		 					}
		 		    	  });
		 	    	}
		 	    }
		  }*/
	      threadPoolExecutor.execute(new ExecuteJobsRunnable(this, jobIds));
	      if(jobIds!=null&&jobIds.isEmpty()==false){
	    	  System.out.println(String.format("job提交成功：%s", JSON.toJSONString(jobIds)));
	      }
	    } catch (RejectedExecutionException e) {
	      rejectedJobsHandler.jobsRejected(this, jobIds);
	    }
	  }
	  
	  
	  // getters and setters ////////////////////////////////////////////////////// 
	  
	  public int getQueueSize() {
	    return queueSize;
	  }
	  
	  public void setQueueSize(int queueSize) {
	    this.queueSize = queueSize;
	  }
	  
	  public int getCorePoolSize() {
	    return corePoolSize;
	  }
	  
	  public void setCorePoolSize(int corePoolSize) {
	    this.corePoolSize = corePoolSize;
	  }

	  public int getMaxPoolSize() {
	    return maxPoolSize;
	  }

	  public void setMaxPoolSize(int maxPoolSize) {
	    this.maxPoolSize = maxPoolSize;
	  }
	  
	  public long getKeepAliveTime() {
			return keepAliveTime;
		}

		public void setKeepAliveTime(long keepAliveTime) {
			this.keepAliveTime = keepAliveTime;
		}

		public BlockingQueue<Runnable> getThreadPoolQueue() {
	    return threadPoolQueue;
	  }

	  public void setThreadPoolQueue(BlockingQueue<Runnable> threadPoolQueue) {
	    this.threadPoolQueue = threadPoolQueue;
	  }

	  public ThreadPoolExecutor getThreadPoolExecutor() {
	    return threadPoolExecutor;
	  }
	  
	  public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
	    this.threadPoolExecutor = threadPoolExecutor;
	  }

}
