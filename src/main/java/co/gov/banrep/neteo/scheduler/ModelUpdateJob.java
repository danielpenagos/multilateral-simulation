package co.gov.banrep.neteo.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelUpdateJob implements Job {

	@Autowired
    private ModelUpdateService jobService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobService.executeUpdateJob();		
	}

}
