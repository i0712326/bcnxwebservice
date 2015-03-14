package com.bcnx.web.app.service.batch;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class CleanUpTasklet implements Tasklet {
	private static Logger logger = Logger.getLogger(CleanUpTasklet.class);
	private String dirPath;
	public void setDirPath(String dirPath){
		this.dirPath = dirPath;
	}
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		File dir = new File(dirPath);
		if(dir.exists()){
			File[] files = dir.listFiles();
			for(int i=0;i<files.length;i++){
				File file = files[i];
				file.delete();
				logger.debug("Deleting file "+file.getName()+" :............. Done.");
			}
		}
		logger.debug("Clean up working directory completed.!");
		return RepeatStatus.FINISHED;
	}

}
