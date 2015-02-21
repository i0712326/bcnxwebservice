package com.bcnx.web.app.service.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchJobSettleTest {
	
	@SuppressWarnings("resource")
	@Test
	public void testDoWork() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BatchJob job = (BatchJob) context.getBean("batchJobSettle");
		job.doWork();
		assertTrue(true);
	}

}
