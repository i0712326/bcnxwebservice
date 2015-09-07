package com.bcnx.web.app.service.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bcnx.web.app.service.MemberService;
import com.bcnx.web.app.service.SettleBcnxService;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.SettleBcnx;
import com.bcnx.web.app.utility.UtilityService;

public class NetSettlementReportImp implements NetSettlementReport {
	private static final Logger logger = Logger.getLogger(NetSettlementReportImp.class);
	private static List<SettleBcnx> settleBcnxs = new ArrayList<SettleBcnx>();
	private String path;
	private SettlementReportService settlementReportService;
	private MemberService memberService;
	private SettleBcnxService settleBcnxService;
	public void setPath(String path){
		this.path = path;
	}
	public void setSettlementReportService(SettlementReportService settlementReportService){
		this.settlementReportService = settlementReportService;
	}
	public void setMemberService(MemberService memberService){
		this.memberService = memberService;
	}
	public void setSettleBcnxService(SettleBcnxService settleBcnxService){
		this.settleBcnxService = settleBcnxService;
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
				settleBcnxs.add(settle);
			} catch (IOException e) {
				logger.debug("Exception occured while try to export member daily reports",e);
			}
		}
		Member owner = memberService.getOwner();
		String admin = owner.getIin();
		File admDir = new File(path+"/"+admin);
		if(!admDir.exists())
			admDir.mkdir();
		String dir = UtilityService.date2Str(backDate);
		File admDateDir = new File(path+"/"+admin+"/"+dir);
		if(!admDateDir.exists())
			admDateDir.mkdir();
		String destName = "BCNX"+dir+admin+".txt";
		String destFile = path+"/"+admin+"/"+dir+"/"+destName;
		double net = printSummaryReport(destFile, backDate, members);
		SettleBcnx settleBcnx = new SettleBcnx();
		settleBcnx.setDate(backDate);
		settleBcnx.setNetAmt(net);
		settleBcnx.setRecFile(destName);
		settleBcnx.setSetlFile(destName);
		settleBcnx.setMember(owner);
		if(!isExist(settleBcnx))
			settleBcnxs.add(settleBcnx);
		settleBcnxService.saveAll(settleBcnxs);
	}
	
	private boolean isExist(SettleBcnx settleBcnx) {
		Date date = settleBcnx.getDate();
		String id = settleBcnx.getMember().getIin();
		SettleBcnx sb = settleBcnxService.getSettleBcnx(date, id);
		return sb!=null;
	}
	
	private double printSummaryReport(String destFile, Date backDate,List<Member> members) {
		File file = new File(destFile);
		String pattern = "#,##0.00";
		double differ = 0.0;
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			pw.println("BCONNEX SETTLEMEM SUMMARY REPORT : "+backDate);
			pw.println("+-------------+-------------------+-----------------+");
			pw.println("|   member 	  |       amount      |    currency     |");
			pw.println("+-------------+-------------------+-----------------+");
			for(Member mem : members){
				if(mem.getFlag().equals("Y")){
					pw.printf("%13s\t%17s\t%8s\r\n", mem.getIin(), decimalFormat.format(mem.getNetAmount()), "(LAK)");
					differ-=mem.getNetAmount();
				}
			}
			pw.println("-----------------------------------------------------");
			pw.printf("%13s\t%17s\t%8s\r\n", "BCONNEX FEE",decimalFormat.format(differ),"(LAK)");
			pw.close();
			return differ;
		} catch (IOException e) {
			logger.debug("Exception occur while try to write settlement file",e);
			return 0;
		}
	}

}
