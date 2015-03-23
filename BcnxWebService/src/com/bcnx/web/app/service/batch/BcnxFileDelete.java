package com.bcnx.web.app.service.batch;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class BcnxFileDelete implements Tasklet {
	private String resourcePath;
	public void setResourcePath(String resourcePath){
		this.resourcePath = resourcePath;
	}
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		File dir = new File(resourcePath);
		if(dir.isDirectory()){
			File[] files = dir.listFiles();
			for(int i=0;i<files.length;i++){
				File file = files[i];
				file.delete();
			}
		}
		return RepeatStatus.FINISHED;
	}

}
