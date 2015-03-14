package com.bcnx.web.app.service.test;

import static org.junit.Assert.*;

import java.sql.Date;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.junit.Test;

import com.bcnx.web.app.utility.UtilityService;

public class TimeTest {

	@Test
	public void test() {
		Date org = UtilityService.str2Date("2015-03-01");
		Date date = UtilityService.getCurrentDate();
		LocalDate start = new LocalDate(org);
		LocalDate end = new LocalDate(date);
		int valid = Days.daysBetween(start, end).getDays();
		System.out.println(valid);
		assertTrue(valid==1);
	}

}
