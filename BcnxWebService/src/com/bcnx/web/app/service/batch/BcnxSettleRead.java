package com.bcnx.web.app.service.batch;

import java.io.File;
import java.sql.Date;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.bcnx.web.app.service.auditor.BcnxSettleAuditor;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.utility.UtilityService;

public class BcnxSettleRead extends BatchTemplate implements Tasklet {
	private String resourcePath;
	private BcnxSettleAuditor bcnxSettleAuditor;
	public void setResourcePath(String resourcePath){
		this.resourcePath = resourcePath;
	}
	public void setBcnxSettleAuditor(BcnxSettleAuditor bcnxSettleAuditor){
		this.bcnxSettleAuditor = bcnxSettleAuditor;
	}
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		File dir = new File(resourcePath);
		if(dir.isDirectory()){
			File[] files = dir.listFiles();
			for(int i=0;i<files.length;i++){
				File file = files[i];
				String name = file.getName();
				String str = name.substring(name.length()-6);
				Date date = UtilityService.str2Date2(str);
				List<BcnxSettle> bcnxSettles = bcnxSettleAuditor.toList(file, date);
				bcnxSettleService.saveAll(bcnxSettles);
			}
		}
		return RepeatStatus.CONTINUABLE;
	}

}
