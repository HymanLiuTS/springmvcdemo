package springmvcdemo.activiti.configuration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cmd.AcquireJobsCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.jobexecutor.AcquiredJobs;
import org.activiti.engine.impl.jobexecutor.JobExecutor;
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.MessageEntity;

import com.alibaba.fastjson.JSON;

public class MyAcquireJobsCmd implements Command<AcquiredJobs> {

	private final JobExecutor jobExecutor;

	private final Integer MAX_SET_SIZE = 1;

	private final List<String> executedJobs = new ArrayList<String>();

	public MyAcquireJobsCmd(JobExecutor jobExecutor) {
		this.jobExecutor = jobExecutor;
	}

	private Boolean firstExecut = true;

	public AcquiredJobs execute(CommandContext commandContext) {

		String lockOwner = jobExecutor.getLockOwner();
		int lockTimeInMillis = jobExecutor.getLockTimeInMillis();
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(commandContext.getProcessEngineConfiguration().getClock().getCurrentTime());
		gregorianCalendar.add(Calendar.MILLISECOND, lockTimeInMillis);
		Date locktime = gregorianCalendar.getTime();

		int maxNonExclusiveJobsPerAcquisition = jobExecutor.getMaxJobsPerAcquisition();

		maxNonExclusiveJobsPerAcquisition = 10;

		AcquiredJobs acquiredJobs = new AcquiredJobs();
		List<JobEntity> jobs = commandContext.getJobEntityManager()
				.findNextJobsToExecute(new Page(0, maxNonExclusiveJobsPerAcquisition));

		for (JobEntity job : jobs) {
			if ((job.getLockOwner() != null && job.getLockExpirationTime() != null)) {
				String temp = String.format("%s-%d", job.getId(), job.getLockExpirationTime().getTime() / 1000);
				if (executedJobs.contains(temp)) {
					System.out.println(String.format("将要执行的job id:%s已经在运行中，不再执行。", job.getId()));
					continue;
				} else {
					System.out.println(String.format("%s:%s", temp, JSON.toJSONString(executedJobs)));
				}
			}
			List<String> jobIds = new ArrayList<String>();
			if (job != null && !acquiredJobs.contains(job.getId())) {
				if (job instanceof MessageEntity && job.isExclusive() && job.getProcessInstanceId() != null) {
					// wait to get exclusive jobs within 100ms
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}

					// acquire all exclusive jobs in the same process instance
					// (includes the current job)
					List<JobEntity> exclusiveJobs = commandContext.getJobEntityManager()
							.findExclusiveJobsToExecute(job.getProcessInstanceId());
					for (JobEntity exclusiveJob : exclusiveJobs) {
						if (exclusiveJob != null) {
							lockJob(commandContext, exclusiveJob, lockOwner, locktime);
							jobIds.add(exclusiveJob.getId());
						}
					}
				} else {
					lockJob(commandContext, job, lockOwner, locktime);
					jobIds.add(job.getId());
				}
			}
			acquiredJobs.addJobIdBatch(jobIds);
			String key = String.format("%s-%d", job.getId(), locktime.getTime() / 1000);
			if (executedJobs.contains(key) == false) {
				executedJobs.add(key);
			}
		}
		if (executedJobs.size() > MAX_SET_SIZE) {
			Iterator it = executedJobs.iterator();
			Integer index = 0;
			Integer maxIndex = executedJobs.size() - MAX_SET_SIZE;
			while (it.hasNext()) {
				String key = it.next().toString();
				if (index < maxIndex) {
					it.remove();
					System.out.println(String.format("remove:%s", key));
				} else {
					break;
				}
				index++;
			}
			System.out.println(JSON.toJSONString(executedJobs));
		}
		return acquiredJobs;
	}

	protected void lockJob(CommandContext commandContext, JobEntity job, String lockOwner, Date locktime) {
		job.setLockOwner(lockOwner);
		job.setLockExpirationTime(locktime);
	}

}
