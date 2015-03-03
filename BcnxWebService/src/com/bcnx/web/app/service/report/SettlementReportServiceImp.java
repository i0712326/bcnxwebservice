package com.bcnx.web.app.service.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;

import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.DisputeTxnService;
import com.bcnx.web.app.service.MemberService;
import com.bcnx.web.app.service.SettleBcnxService;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.SettleBcnx;
import com.bcnx.web.app.utility.UtilityService;

public class SettlementReportServiceImp implements SettlementReportService {
	private BcnxSettleService bcnxSettleService;
	private DisputeTxnService disputeTxnService;
	private SettleBcnxService settleBcnxService;
	private MemberService memberService;
	private BcnxSettleFee bcnxSettleFee;
	
	private SettleBcnx settleBcnx;
	private List<BcnxSettle> issTxn;
	private List<BcnxSettle> acqTxn;
	private List<BcnxSettle> revTxn;
	private List<BcnxSettle> errTxn;
	private List<DisputeTxn> oCp;
	private List<DisputeTxn> iCp;
	private List<DisputeTxn> iCrs;
	private List<DisputeTxn> oCrs;
	private List<DisputeTxn> oChb;
	private List<DisputeTxn> iChb;
	private List<DisputeTxn> iAdj;
	private List<DisputeTxn> oAdj;
	private List<DisputeTxn> oRpm;
	private List<DisputeTxn> iRpm;
	public void setBcnxSettleService(BcnxSettleService bcnxSettleService){
		this.bcnxSettleService = bcnxSettleService;
	}
	public void setDisputeTxnService(DisputeTxnService disputeTxnService){
		this.disputeTxnService = disputeTxnService;
	}
	public void setSettleBcnxService(SettleBcnxService settleBcnxService){
		this.settleBcnxService = settleBcnxService;
	}
	public void setMemberService(MemberService memberService){
		this.memberService = memberService;
	}
	public void setBcnxSettleFee(BcnxSettleFee bcnxSettleFee){
		this.bcnxSettleFee = bcnxSettleFee;
	}
	@Override
	public SettleBcnx getSettleBcnx() {
		return settleBcnx;
	}
	@Override
	public void publishSettlement(String path, Date date, String id)
			throws IOException {
		issTxn = bcnxSettleService.getBcnxFinIss(date,id);
		acqTxn = bcnxSettleService.getBcnxFinAcq(date,id);
		revTxn = bcnxSettleService.getBcnxRev(date,id);
		errTxn = bcnxSettleService.getBcnxErr(date,id);
		
		oCp = disputeTxnService.outgoingCp(date,id);
		iCp = disputeTxnService.incomingCp(date,id);
		iCrs = disputeTxnService.incomingCrs(date,id);
		oCrs = disputeTxnService.outgoingCrs(date,id);
		oChb = disputeTxnService.outgoingChb(date,id);
		iChb = disputeTxnService.incomingChb(date,id);
		iAdj = disputeTxnService.incomingAdj(date,id);
		oAdj = disputeTxnService.outgoingAdj(date,id);
		oRpm = disputeTxnService.outgoingRpm(date,id);
		iRpm = disputeTxnService.incomingRpm(date,id);
		
		issTxn = bcnxSettleFee.setIssFee(issTxn);
		acqTxn = bcnxSettleFee.setAcqFee(acqTxn);
		revTxn = bcnxSettleFee.setIssFee(revTxn);
		errTxn = bcnxSettleFee.setIssFee(errTxn);
		
		oCp = bcnxSettleFee.setDispIssFee(oCp);
		iCp = bcnxSettleFee.setDispIssFee(iCp);
		iCrs = bcnxSettleFee.setDispIssFee(iCrs);
		oCrs = bcnxSettleFee.setDispIssFee(oCrs);
		oChb = bcnxSettleFee.setDispIssFee(oChb);
		iChb = bcnxSettleFee.setDispAcqFee(iChb);
		iAdj = bcnxSettleFee.setDispIssFee(iAdj);
		oAdj = bcnxSettleFee.setDispAcqFee(oAdj);
		oRpm = bcnxSettleFee.setDispAcqFee(oRpm);
		iRpm = bcnxSettleFee.setDispIssFee(iRpm);
		
		// check data folder
		String setlName = "SETL"+UtilityService.date2Str(date)+id+".txt";
		String setlFile =  path+"/"+setlName;
		printSetlReport(date, id, issTxn, acqTxn, revTxn, errTxn, oCp, iCp, iCrs,
				oCrs, oChb, iChb, iAdj, oAdj, oRpm, iRpm, setlFile);
		//printRecnReport()
		String recnName = "RECN"+UtilityService.date2Str(date)+id+".txt";
		String recnFile = path+"/"+recnName;
		printRecnReport(issTxn, acqTxn, revTxn, errTxn, oCp, iCp, iCrs, oCrs,
				oChb, iChb, iAdj, oAdj, oRpm, iRpm, recnFile);
		saveSettleBcnx(date, id, issTxn, acqTxn, revTxn, errTxn, iCp, oCp,
				iCrs, oCrs, iChb, oChb, iAdj, oAdj, iRpm, oRpm, setlName, recnName);
	}
	
	private void printSetlReport(Date date, String id, List<BcnxSettle> issTxn,
			List<BcnxSettle> acqTxn, List<BcnxSettle> revTxn,
			List<BcnxSettle> errTxn, List<DisputeTxn> oCp,
			List<DisputeTxn> iCp, List<DisputeTxn> iCrs, List<DisputeTxn> oCrs,
			List<DisputeTxn> oChb, List<DisputeTxn> iChb,
			List<DisputeTxn> iAdj, List<DisputeTxn> oAdj,
			List<DisputeTxn> oRpm, List<DisputeTxn> iRpm, String fileName)
			throws IOException {
		File file = new File(fileName);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		pw.println("Settlement Report Date : "+date);
		pw.println("MEMBER ID : "+ id);
		pw.println("============ transaction reports =============");
		printReport(pw,"ISSUER :",issTxn);
		printReport(pw,"ACQUIRER :",acqTxn);
		printReport(pw,"REVERSAL :",revTxn);
		printReport(pw,"ERROR :",errTxn);
		printDispReports(pw,"OUTGONG COPY REQUEST :",oCp);
		printDispReports(pw,"INCOMING COPY REQUEST",iCp);
		printDispReports(pw,"INCOMING COPY REQUEST RESPONSE :", iCrs);
		printDispReports(pw,"OUTGOIN COPY REQUEST RESPONSE :", oCrs);
		printDispReports(pw,"OUTGOING CHARGE BACK :",oChb);
		printDispReports(pw,"INCOMING CHARGE BACK :",iChb);
		printDispReports(pw,"OUTGOING ADJUSTMENT :",oAdj);
		printDispReports(pw,"INCOMING ADJUSTMENT :",iAdj);
		printDispReports(pw,"OUTGOING REPRESENTMENT :",oRpm);
		printDispReports(pw,"INCOMING REPRESENTMENT", iRpm);
		printNetSettlement(pw,
				revTxn.size(), getTotal(revTxn), getFee(revTxn),
				errTxn.size(), getTotal(errTxn), getFee(errTxn),
				oCp.size(), getDispTotal(oCp), getDispFee(oCp),
				iCp.size(), getDispTotal(iCp), getDispFee(iCp),
				oCrs.size(), getDispTotal(oCrs), getDispFee(oCrs),
				iCrs.size(), getDispTotal(iCrs), getDispFee(iCrs),
				issTxn.size(), getTotal(issTxn), getFee(issTxn),
				oChb.size(), getDispTotal(oChb), getDispFee(oChb),
				iAdj.size(), getDispTotal(iAdj), getDispFee(iAdj),
				acqTxn.size(), getTotal(acqTxn), getFee(acqTxn),
				iChb.size(), getDispTotal(iChb), getDispFee(iChb),
				oAdj.size(), getDispTotal(oAdj), getDispFee(oAdj),
				oRpm.size(), getDispTotal(oRpm), getDispFee(oRpm),
				iRpm.size(), getDispTotal(iRpm), getDispFee(iRpm));
		pw.close();
		
	}
	private void printRecnReport(List<BcnxSettle> issTxn,
			List<BcnxSettle> acqTxn, List<BcnxSettle> revTxn,
			List<BcnxSettle> errTxn, List<DisputeTxn> oCp,
			List<DisputeTxn> iCp, List<DisputeTxn> iCrs, List<DisputeTxn> oCrs,
			List<DisputeTxn> oChb, List<DisputeTxn> iChb,
			List<DisputeTxn> iAdj, List<DisputeTxn> oAdj,
			List<DisputeTxn> oRpm, List<DisputeTxn> iRpm, String recnFile) throws IOException{
		File file = new File(recnFile);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		int seq = 1;
		seq = printReconReports(pw,issTxn,seq);
		seq = printReconReports(pw,acqTxn,seq);
		seq = printReconDisputes(pw,iAdj,seq);
		seq = printReconDisputes(pw,oAdj,seq);
		seq = printReconDisputes(pw,oChb,seq);
		seq = printReconDisputes(pw,iChb,seq);
		seq = printReconDisputes(pw,iRpm,seq);
		seq = printReconDisputes(pw,oRpm,seq);
		seq = printReconDisputes(pw,iCp,seq);
		seq = printReconDisputes(pw,oCp,seq);
		seq = printReconDisputes(pw,iCrs,seq);
		seq = printReconDisputes(pw,oCrs,seq);
		seq = printReconReports(pw,revTxn,seq);
		seq = printReconReports(pw,errTxn,seq);
		pw.close();
	}
	private void saveSettleBcnx(Date date, String id, List<BcnxSettle> iss,
			List<BcnxSettle> acq, List<BcnxSettle> rev, List<BcnxSettle> err,
			List<DisputeTxn> icp, List<DisputeTxn> ocp, List<DisputeTxn> icr,
			List<DisputeTxn> ocr, List<DisputeTxn> ic, List<DisputeTxn> oc,
			List<DisputeTxn> ia, List<DisputeTxn> oa, List<DisputeTxn> ir,
			List<DisputeTxn> or, String setlFile, String recnFile) {
		settleBcnx = new SettleBcnx();
		Member member = new Member();
		member = memberService.getMemIin(id);
		
		int issNum = iss.size();
		double issAmt = getTotal(iss);
		double issFee = getFee(iss);
		
		int acqNum = acq.size();
		double acqAmt = getTotal(acq);
		double acqFee = getFee(acq);
		
		int revNum = rev.size();
		double revAmt = getTotal(rev);
		double revFee = getFee(rev);
		
		int errNum = err.size();
		double errAmt = getTotal(err);
		double errFee = getFee(err);
		
		int icpNum = icp.size();
		int ocpNum = ocp.size();
		
		int icNum = ic.size();
		double icAmt = getDispTotal(ic);
		double icFee = getDispFee(ic);
		
		int ocNum = oc.size();
		double ocAmt = getDispTotal(oc);
		double ocFee = getDispFee(oc);
		
		int iaNum = ia.size();
		double iaAmt = getDispTotal(ia);
		double iaFee = getDispFee(ia);
		
		int oaNum = oa.size();
		double oaAmt = getDispTotal(oa);
		double oaFee = getDispFee(oa);
		
		int irNum = ir.size();
		double irAmt = getDispTotal(ir);
		double irFee = getDispFee(ir);
		
		int orNum = or.size();
		double orAmt = getDispTotal(or);
		double orFee = getDispFee(or);
		
		double net = (acqAmt +acqFee) - (issAmt + issFee) + (ocAmt + ocFee) - (icAmt + icFee) 
				+ (iaAmt + iaAmt) - (oaAmt + oaFee)	+ (orAmt + orFee) - (irAmt + irFee);
		
		settleBcnx.setMember(member);
		settleBcnx.setDate(date);
		settleBcnx.setSetlFile(setlFile);
		settleBcnx.setRecFile(recnFile);
		
		settleBcnx.setIssNum(issNum);
		settleBcnx.setIssAmt(issAmt);
		settleBcnx.setIssFee(issFee);
		
		settleBcnx.setAcqNum(acqNum);
		settleBcnx.setAcqAmt(acqAmt);
		settleBcnx.setAcqFee(acqFee);
		
		settleBcnx.setRevNum(revNum);
		settleBcnx.setRevAmt(revAmt);
		settleBcnx.setRevFee(revFee);
		
		settleBcnx.setErrNum(errNum);
		settleBcnx.setErrAmt(errAmt);
		settleBcnx.setErrFee(errFee);
		
		settleBcnx.setInCprNum(icpNum);
		settleBcnx.setOuCprNum(ocpNum);
		
		settleBcnx.setInChbNum(icNum);
		settleBcnx.setInChbAmt(icAmt);
		settleBcnx.setInChbFee(icFee);
		
		settleBcnx.setOuChbNum(ocNum);
		settleBcnx.setOuChbAmt(ocAmt);
		settleBcnx.setOuChbFee(ocFee);
		
		settleBcnx.setInAdjNum(iaNum);
		settleBcnx.setInAdjAmt(iaAmt);
		settleBcnx.setInAdjFee(iaFee);
		
		settleBcnx.setOuAdjNum(oaNum);
		settleBcnx.setOuAdjAmt(oaAmt);
		settleBcnx.setOuAdjFee(oaFee);
		
		settleBcnx.setInRpmNum(irNum);
		settleBcnx.setInRpmAmt(irAmt);
		settleBcnx.setInRpmFee(irFee);
		
		settleBcnx.setOuRpmNum(orNum);
		settleBcnx.setOuRpmAmt(orAmt);
		settleBcnx.setOuRpmFee(orFee);
		
		settleBcnx.setNetAmt(net);
		
		settleBcnxService.save(settleBcnx);
	}
	
	private void printReport(PrintWriter pw, String title, List<BcnxSettle> list){
		pw.println(title);
		pw.println("+-----+-----------+-----------+--------------------+-------+------------+---------+---+--------------+------------+----------+----------+----------+");
		pw.println("| No. |   date    |   time    |       card         |  proc |    rrn     |  trace  |res|    amount    |     fee    | terminal |  issuer  | acquirer |");
		pw.println("+-----+-----------+-----------+--------------------+-------+------------+---------+---+--------------+------------+----------+----------+----------+");
		String pattern = "#,##0.00";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		double total = 0;
		double fee = 0;
		int count = 1;
		for (BcnxSettle bcon : list) {
			pw.printf("%5d\t%10s\t%8s\t%19s\t%6s\t%12s\t%6s\t%2s\t%12s\t%10s\t%8s\t%8s\t%8s\r\n", count, bcon.getDate(), bcon.getTime(),
					bcon.getCard(), bcon.getProc(), bcon.getRrn(), bcon.getStan(), bcon.getRes(), decimalFormat.format(bcon.getAmt()),
					decimalFormat.format(bcon.getFee()), bcon.getTermId(),bcon.getIss(),bcon.getAcq());
			total += bcon.getAmt();
			fee += bcon.getFee();
			count++;
		}
		pw.println("\r\nSUMMARY\r\n----------------------------------\r\n");
		pw.printf("%10s\t%20s (LAK)\r\n","Total :",decimalFormat.format(total));
		pw.printf("%10s\t%20s (LAK)\r\n","Fee :",decimalFormat.format(fee));
		pw.println("\r\n\r\n");
	}
	
	private void printDispReports(PrintWriter pw, String title, List<DisputeTxn> list){
		pw.println(title);
		pw.println("+-----+-----------+-----------+--------------------+-------+------------+---------+---+--------------+------------+----------+----------+----------+");
		pw.println("| No. |   date    |   time    |       card         |  proc |    rrn     |  trace  |res|    amount    |     fee    | terminal |  issuer  | acquirer |");
		pw.println("+-----+-----------+-----------+--------------------+-------+------------+---------+---+--------------+------------+----------+----------+----------+");
		String pattern = "#,##0.00";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		double total = 0;
		double fee = 0;
		int count = 1;
		for (DisputeTxn disp : list) {
			BcnxSettle bcon = disp.getBcnxSettle();
			pw.printf("%5d\t%10s\t%8s\t%19s\t%6s\t%12s\t%6s\t%2s\t%12s\t%10s\t%8s\t%8s\t%8s\r\n", count, bcon.getDate(), bcon.getTime(),
					bcon.getCard(), bcon.getProc(), bcon.getRrn(), bcon.getStan(), bcon.getRes(), decimalFormat.format(bcon.getAmt()),
					decimalFormat.format(bcon.getFee()), bcon.getTermId(),bcon.getIss(),bcon.getAcq());
			total += bcon.getAmt();
			fee += bcon.getFee();
			count++;
		}
		pw.println("\r\nSUMMARY\r\n----------------------------------\r\n");
		pw.printf("%10s\t%20s (LAK)\r\n","Total :",decimalFormat.format(total));
		pw.printf("%10s\t%20s (LAK)\r\n","Fee :",decimalFormat.format(fee));
		pw.println("\r\n\r\n");
	}
	private void printNetSettlement(PrintWriter pw, 
			int revNum,	double rev, double revFee,
			int errNum, double err, double errFee,
			int oCpNum, double oCp, double oCpFee,
			int iCpNum, double iCp, double iCpFee,
			int oCrsNum, double oCrs, double oCrsFee,
			int iCrsNum, double iCrs, double iCrsFee,
			int issNum, double iss, double issFee,
			int isCbNum, double isCh, double isChf,
			int isAdNum, double isAd, double isAdf,
			int acqNum, double acq, double acqFee,
			int acCbNum, double acCh, double acChf,
			int acAdNum, double acAd, double acAdf,
			int oRpmNum, double oRpm, double oRpmf,
			int iRpmNUm, double iRpm, double iRpmf){
		String pattern = "#,##0.00";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		pw.println("\r\n============= NET SETTLEMENT SUMMARY =============\r\n");
		pw.println("NON-FINANCAIL TRANSACTION SUMMARY");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.println("|         type         |    number     |        amount        |         fee        |");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","REVERSAL",revNum,decimalFormat.format(rev),decimalFormat.format(revFee));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ERROR",errNum,decimalFormat.format(err),decimalFormat.format(errFee));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","COPY REQUEST (OUT)",errNum,decimalFormat.format(err),decimalFormat.format(errFee));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","COPY REQUEST RES.(OUT)",errNum,decimalFormat.format(err),decimalFormat.format(errFee));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","COPY REQUEST (IN)",errNum,decimalFormat.format(err),decimalFormat.format(errFee));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","COPY REQUEST RES.(IN)",errNum,decimalFormat.format(err),decimalFormat.format(errFee));
		
		pw.println("\r\nFINANCAIL TRANSACTION SUMMARY");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.println("|         type         |    number     |        amount        |         fee        |");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ISSUER",issNum,decimalFormat.format(-1*iss),decimalFormat.format(-1*issFee));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","OUTGOING CHARGE BACK",isCbNum,decimalFormat.format(isCh),decimalFormat.format(isChf));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","INCOMING ADJUSTMENT",isAdNum,decimalFormat.format(isAd),decimalFormat.format(isAdf));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","INCOMING REPRESENTMENT",acAdNum,decimalFormat.format(-1*acAd),decimalFormat.format(-1*acAdf));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ACQUIRER",acqNum,decimalFormat.format(acq),decimalFormat.format(acqFee));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","INCOMING CHARGE BACK",acCbNum,decimalFormat.format(-1*acCh),decimalFormat.format(-1*acChf));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","OUTGOING ADJUSTMENT",acAdNum,decimalFormat.format(-1*acAd),decimalFormat.format(-1*acAdf));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","OUTGOING REPRESENTMENT",acAdNum,decimalFormat.format(-1*acAd),decimalFormat.format(-1*acAdf));
		pw.println("------------------------------------------------------------------------------------");
		double total = ( acq+acqFee) - (iss + issFee ) + (isCh + isChf) - (acCh + acChf) 
				+ (isAd + isAdf) - (acAd + acAdf) + (oRpm + oRpmf) - (iRpm + iRpmf);
		pw.printf("%20s %20s (LAK)","Net Settlement : ",decimalFormat.format(total));
	}
	public int printReconReports(PrintWriter pw,List<BcnxSettle> list, int count){
		for(BcnxSettle bcon : list){
			pw.printf("%4d%6s%6s%19s%6s%12s%6s%2s%012d%010d%8s%6s%6s%4s\r\n", count, UtilityService.date2Str(bcon.getDate()), bcon.getTime().replace(":", ""),
					bcon.getCard(), bcon.getProc(), bcon.getRrn(), bcon.getStan(), bcon.getRes(), (int)bcon.getAmt()*100,
					(int)bcon.getFee(), bcon.getTermId(),bcon.getIss(),bcon.getAcq()==null?"------":bcon.getAcq(),bcon.getMti());
			count++;
		}
		return count;
	}
	public int printReconDisputes(PrintWriter pw,List<DisputeTxn> list, int count){
		for(DisputeTxn disp : list){
			BcnxSettle bcon = disp.getBcnxSettle();
			pw.printf("%4d%6s%6s%19s%6s%12s%6s%2s%012d%010d%8s%6s%6s%4s\r\n", count, UtilityService.date2Str(disp.getDate()), disp.getTime().replace(":", ""),
					bcon.getCard(), disp.getProcc(), bcon.getRrn(), bcon.getStan(), bcon.getRes(), (int)disp.getAmount()*100,
					(int)disp.getFee(), bcon.getTermId(),bcon.getIss(),bcon.getAcq(),disp.getMti());
			count++;
		}
		return count;
	}
	private double getTotal(List<BcnxSettle> list){
		double total = 0.0;
		for (BcnxSettle bcnxSettle : list) {
			total += bcnxSettle.getAmt();
		}
		return total;
	}
	private double getDispTotal(List<DisputeTxn> list){
		double total = 0.0;
		for (DisputeTxn disp : list) {
			total += disp.getAmount();
		}
		return total;
	}
	private double getFee(List<BcnxSettle> list){
		double fee = 0.0;
		for (BcnxSettle bcnxSettle : list) {
			fee += bcnxSettle.getFee();
		}
		return fee;
	}
	private double getDispFee(List<DisputeTxn> list){
		double fee = 0.0;
		for (DisputeTxn disp : list) {
			fee += disp.getFee();
		}
		return fee;
	}
}
