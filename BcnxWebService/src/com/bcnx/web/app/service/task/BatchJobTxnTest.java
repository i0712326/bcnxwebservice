package com.bcnx.web.app.service.task;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchJobTxnTest {
	@SuppressWarnings("resource")
	@Test
	public void testDoWork() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BatchJob job = (BatchJob) context.getBean("batchJobTxn");
		job.doWork();
		assertTrue(true);
	}

}
