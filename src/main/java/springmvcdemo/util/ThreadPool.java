package springmvcdemo.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	
	public ThreadPool(int threadNum)
	{
		try{
			pool = Executors.newFixedThreadPool(threadNum);
		}catch (Exception e) {
			//PublicFunc.LogOutException(e, "New FixedThreadPool");
		}
	}
	
	private ExecutorService pool;
	
	public void Submit(Runnable thread)
	{
		try {
			pool.submit(thread);
		}catch(Exception e) {
			//PublicFunc.LogOutException(e, "ThreadPool Submit");
		}
	}

	public void  WaitTerminate()
	{
		try {
			// 关闭启动线程
			pool.shutdown();
	        // 等待子线程结束，再继续执行下面的代码
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (Exception e) {
			//PublicFunc.LogOutException(e, "ThreadPool AwaitTermination");
		}
	}

}
