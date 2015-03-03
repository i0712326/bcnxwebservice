package com.bcnx.web.app.service.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.bcnx.web.app.service.MemberService;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.SettleBcnx;
import com.bcnx.web.app.utility.UtilityService;

public class NetSettlementReportImp implements NetSettlementReport {
	private static final Logger logger = Logger.getLogger(NetSettlementReportImp.class);
	private String path;
	private SettlementReportService settlementReportService;
	private MemberService memberService;
	public void setPath(String path){
		this.path = path;
	}
	public void setSettlementReportService(SettlementReportService settlementReportService){
		this.settlementReportService = settlementReportService;
	}
	public void setMemberService(MemberService memberService){
		this.memberService = memberService;
	}
	@Override
	public void printNetSettlementReport() {
		List<Member> members = memberService.getMembers();
		Date backDate = UtilityService.getBackDate();
		String folderName = UtilityService.date2Str(backDate);
		String dest = "";
		for(Member mem : members){
			try {
				String id = mem.getIin();
				dest = path+"/"+id;
				File iinFolder = new File(dest);
				if(!iinFolder.exists())
					iinFolder.mkdir();
				dest = dest+"/"+folderName;
				File dir = new File(dest);
				if(!dir.exists())
					dir.mkdir();
				settlementReportService.publishSettlement(dest, backDate, id);
				SettleBcnx settle = settlementReportService.getSettleBcnx();
				mem.setNetAmount(settle.getNetAmt());
			} catch (IOException e) {
				logger.debug("Exception occured while try to export member daily reports",e);
			}
		}
		printSummaryReport(dest,backDate,members);
	}
	private void printSummaryReport(String dest,Date backDate,List<Member> members) {
		String name = dest+"/"+"bconnex-"+backDate+".txt";
		File file = new File(name);
		String pattern = "#,##0.00";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			pw.println("BCONNEX SETTLEMEM SUMMARY REPORT : "+backDate);
			pw.println("+-------------+-------------------+-----------------+");
			pw.println("|   member 	  |       amount      |    currency     |");
			pw.println("+-------------+-------------------+-----------------+");
			double differ = 0.0;
			for(Member mem : members){
				pw.printf("%13s\t%17s\t%8s\r\n", mem.getIin(), decimalFormat.format(mem.getNetAmount()), "(LAK)");
				differ-=mem.getNetAmount();
			}
			pw.println("-----------------------------------------------------");
			pw.printf("%13s\t%17s\t%8s\r\n", "BCONNEX FEE",decimalFormat.format(differ),"(LAK)");
			pw.close();
		} catch (IOException e) {
			logger.debug("Exception occur while try to write settlement file",e);
		}
	}

}
