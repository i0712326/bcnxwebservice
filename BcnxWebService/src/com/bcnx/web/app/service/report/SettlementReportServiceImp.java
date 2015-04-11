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
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.SettleBcnx;
import com.bcnx.web.app.utility.UtilityService;

public class SettlementReportServiceImp implements SettlementReportService {
	private BcnxSettleService bcnxSettleService;
	private DisputeTxnService disputeTxnService;
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
		String recnName = "RECN"+UtilityService.date2Str(date)+id+".txt";
		String recnFile = path+"/"+recnName;
		setSettleBcnx(date, id, issTxn, acqTxn, revTxn, errTxn, iCp, oCp,
				iCrs, oCrs, iChb, oChb, iAdj, oAdj, iRpm, oRpm, setlName, recnName);
		printSetlReport(date, id, issTxn, acqTxn, revTxn, errTxn, oCp, iCp, iCrs,
				oCrs, oChb, iChb, iAdj, oAdj, oRpm, iRpm, setlFile);
		printRecnReport(issTxn, acqTxn, revTxn, errTxn, oCp, iCp, iCrs, oCrs,
				oChb, iChb, iAdj, oAdj, oRpm, iRpm, recnFile);
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
		printDispReports(pw,"INCOMING FULLFILLMENT :", iCrs);
		printDispReports(pw,"OUTGOIN FULLFILLMENT :", oCrs);
		printDispReports(pw,"OUTGOING CHARGE BACK :",oChb);
		printDispReports(pw,"INCOMING CHARGE BACK :",iChb);
		printDispReports(pw,"OUTGOING ADJUSTMENT :",oAdj);
		printDispReports(pw,"INCOMING ADJUSTMENT :",iAdj);
		printDispReports(pw,"OUTGOING REPRESENTMENT :",oRpm);
		printDispReports(pw,"INCOMING REPRESENTMENT", iRpm);
		printNetSettlement(pw);
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
	
	private void setSettleBcnx(Date date, String id, List<BcnxSettle> iss,
			List<BcnxSettle> acq, List<BcnxSettle> rev, List<BcnxSettle> err,
			List<DisputeTxn> icp, List<DisputeTxn> ocp, List<DisputeTxn> icr,
			List<DisputeTxn> ocr, List<DisputeTxn> ic, List<DisputeTxn> oc,
			List<DisputeTxn> ia, List<DisputeTxn> oa, List<DisputeTxn> ir,
			List<DisputeTxn> or, String setlFile, String recnFile) {
		settleBcnx = new SettleBcnx();
		Member member = new Member();
		member = memberService.getMemIin(id);
		
		int issNum = iss.size();
		double issAmt = -1*getTotal(iss);
		double issFee = -1*getFee(iss);
		double issTot = issAmt+issFee;
		
		int acqNum = acq.size();
		double acqAmt = getTotal(acq);
		double acqFee = getFee(acq);
		double acqTot = acqAmt+acqFee;
		
		int revNum = rev.size();
		double revAmt = getTotal(rev);
		double revFee = getFee(rev);
		double revTot = revAmt+revFee;
		
		int errNum = err.size();
		double errAmt = getTotal(err);
		double errFee = getFee(err);
		double errTot = errAmt + errFee;
		
		int icpNum = icp.size();
		int ocpNum = ocp.size();
		
		int icNum = ic.size();
		double icAmt = -1*getDispTotal(ic);
		double icFee = -1*getDispFee(ic);
		double icTot = icAmt + icFee;
		
		int ocNum = oc.size();
		double ocAmt = getDispTotal(oc);
		double ocFee = getDispFee(oc);
		double ocTot = ocAmt + ocFee;
		
		int iaNum = ia.size();
		double iaAmt = getDispTotal(ia);
		double iaFee = getDispFee(ia);
		double iaTot = iaAmt + iaFee;
		
		int oaNum = oa.size();
		double oaAmt = -1*getDispTotal(oa);
		double oaFee = -1*getDispFee(oa);
		double oaTot = oaAmt + oaFee;
		
		int irNum = ir.size();
		double irAmt = -1*getDispTotal(ir);
		double irFee = -1*getDispFee(ir);
		double irTot = irAmt + irFee;
		
		int orNum = or.size();
		double orAmt = getDispTotal(or);
		double orFee = getDispFee(or);
		double orTot = orAmt + orFee;
		
		double net = issTot + acqTot + icTot + ocTot + iaTot + oaTot + irTot + orTot;
		
		settleBcnx.setMember(member);
		settleBcnx.setDate(date);
		settleBcnx.setSetlFile(setlFile);
		settleBcnx.setRecFile(recnFile);
		
		settleBcnx.setIssNum(issNum);
		settleBcnx.setIssAmt(issAmt);
		settleBcnx.setIssFee(issFee);
		settleBcnx.setIssTot(issTot);
		
		settleBcnx.setAcqNum(acqNum);
		settleBcnx.setAcqAmt(acqAmt);
		settleBcnx.setAcqFee(acqFee);
		settleBcnx.setAcqTot(acqTot);
		
		settleBcnx.setRevNum(revNum);
		settleBcnx.setRevAmt(revAmt);
		settleBcnx.setRevFee(revFee);
		settleBcnx.setRevTot(revTot);
		
		settleBcnx.setErrNum(errNum);
		settleBcnx.setErrAmt(errAmt);
		settleBcnx.setErrFee(errFee);
		settleBcnx.setErrTot(errTot);
		
		settleBcnx.setInCprNum(icpNum);
		settleBcnx.setOuCprNum(ocpNum);
		
		settleBcnx.setInChbNum(icNum);
		settleBcnx.setInChbAmt(icAmt);
		settleBcnx.setInChbFee(icFee);
		settleBcnx.setInChbTot(icTot);
		
		settleBcnx.setOuChbNum(ocNum);
		settleBcnx.setOuChbAmt(ocAmt);
		settleBcnx.setOuChbFee(ocFee);
		settleBcnx.setOuChbTot(ocTot);
		
		settleBcnx.setInAdjNum(iaNum);
		settleBcnx.setInAdjAmt(iaAmt);
		settleBcnx.setInAdjFee(iaFee);
		settleBcnx.setInAdjTot(iaTot);
		
		settleBcnx.setOuAdjNum(oaNum);
		settleBcnx.setOuAdjAmt(oaAmt);
		settleBcnx.setOuAdjFee(oaFee);
		settleBcnx.setOuAdjTot(oaTot);
		
		settleBcnx.setInRpmNum(irNum);
		settleBcnx.setInRpmAmt(irAmt);
		settleBcnx.setInRpmFee(irFee);
		settleBcnx.setInRpmTot(irTot);
		
		settleBcnx.setOuRpmNum(orNum);
		settleBcnx.setOuRpmAmt(orAmt);
		settleBcnx.setOuRpmFee(orFee);
		settleBcnx.setOuRpmTot(orTot);
		
		settleBcnx.setNetAmt(net);
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
			pw.printf("%5d\t%10s\t%8s\t%19s\t%6s\t%12s\t%6s\t%2s\t%12s\t%10s\t%8s\t%8s\t%8s\r\n", count, disp.getDate(), disp.getTime(),
					bcon.getCard(), disp.getProcc(), bcon.getRrn(), bcon.getStan(), bcon.getRes(), decimalFormat.format(disp.getAmount()),
					decimalFormat.format(disp.getFee()), bcon.getTermId(),bcon.getIss(),bcon.getAcq());
			total += disp.getAmount();
			fee += disp.getFee();
			count++;
		}
		pw.println("\r\nSUMMARY\r\n----------------------------------\r\n");
		pw.printf("%10s\t%20s (LAK)\r\n","Total :",decimalFormat.format(total));
		pw.printf("%10s\t%20s (LAK)\r\n","Fee :",decimalFormat.format(fee));
		pw.println("\r\n\r\n");
	}

	private void printNetSettlement(PrintWriter pw) {
		String pattern = "#,##0.00";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		pw.println("\r\n============= NET SETTLEMENT SUMMARY =============\r\n");
		pw.println("NON-FINANCAIL TRANSACTION SUMMARY");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.println("|         type         |    number     |        amount        |         fee        |");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","REVERSAL",settleBcnx.getRevNum(),decimalFormat.format(settleBcnx.getRevTot()),decimalFormat.format(settleBcnx.getRevFee()));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ERROR",settleBcnx.getErrNum(),decimalFormat.format(settleBcnx.getErrAmt()),decimalFormat.format(settleBcnx.getErrFee()));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","COPY REQUEST (OUT)",settleBcnx.getOuCprNum(),null,null);
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","FULLFILLMENT (OUT)",settleBcnx.getOuCrsNum(),null,null);
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","COPY REQUEST (IN)",settleBcnx.getInCprNum(),null,null);
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","FULLFILLMENT (IN)",settleBcnx.getInCrsNum(),null,null);
		
		pw.println("\r\nFINANCAIL TRANSACTION SUMMARY");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.println("|         type         |    number     |        amount        |         fee        |");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ISSUER",settleBcnx.getIssNum(),decimalFormat.format(settleBcnx.getIssAmt()),decimalFormat.format(settleBcnx.getIssFee()));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","CHARGE BACK (OUT)",settleBcnx.getOuChbNum(),decimalFormat.format(settleBcnx.getOuChbAmt()),decimalFormat.format(settleBcnx.getOuChbFee()));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ADJUSTMENT (IN)",settleBcnx.getInAdjNum(),decimalFormat.format(settleBcnx.getInAdjAmt()),decimalFormat.format(settleBcnx.getInAdjFee()));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","REPRESENTMENT (IN)",settleBcnx.getInRpmNum(),decimalFormat.format(settleBcnx.getInRpmAmt()),decimalFormat.format(settleBcnx.getInRpmFee()));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ACQUIRER",settleBcnx.getAcqNum(),decimalFormat.format(settleBcnx.getAcqAmt()),decimalFormat.format(settleBcnx.getAcqFee()));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","CHARGE BACK (IN)",settleBcnx.getInChbNum(),decimalFormat.format(settleBcnx.getInChbAmt()),decimalFormat.format(settleBcnx.getInChbFee()));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ADJUSTMENT (OUT)",settleBcnx.getOuAdjNum(),decimalFormat.format(settleBcnx.getOuAdjAmt()),decimalFormat.format(settleBcnx.getOuAdjFee()));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","REPRESENTMENT (OUT)",settleBcnx.getOuRpmNum(),decimalFormat.format(settleBcnx.getOuRpmAmt()),decimalFormat.format(settleBcnx.getOuRpmFee()));
		pw.println("------------------------------------------------------------------------------------");
		pw.printf("%20s %20s (LAK)","Net Settlement : ",decimalFormat.format(settleBcnx.getNetAmt()));
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
