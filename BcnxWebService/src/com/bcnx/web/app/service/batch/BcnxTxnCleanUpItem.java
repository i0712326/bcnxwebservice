package com.bcnx.web.app.service.batch;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class BcnxTxnCleanUpItem implements Tasklet {
	private String path;
	public void setPath(String path){
		this.path = path;
	}
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		File dir = new File(path);
		String[] list = dir.list();
		for(int i=0;i<list.length;i++){
			File file = new File(list[i]);
			file.delete();
		}
		return RepeatStatus.FINISHED;
	}

}
