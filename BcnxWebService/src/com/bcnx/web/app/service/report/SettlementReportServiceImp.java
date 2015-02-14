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
import com.bcnx.web.app.service.auditor.UtilityService;
import com.bcnx.web.app.service.entity.BcnxSettle;

public class SettlementReportServiceImp implements SettlementReportService {
	private BcnxSettleService bcnxSettleService;
	private DisputeTxnService disputeTxnService;
	private BcnxSettleFee bcnxSettleFee;
	private double net;
	public void setBcnxSettleService(BcnxSettleService bcnxSettleService){
		this.bcnxSettleService = bcnxSettleService;
	}
	public void setDisputeTxnService(DisputeTxnService disputeTxnService){
		this.disputeTxnService = disputeTxnService;
	}
	public void setBcnxSettleFee(BcnxSettleFee bcnxSettleFee){
		this.bcnxSettleFee = bcnxSettleFee;
	}
	private void setNet(double net) {
		this.net = net;
	}
	@Override
	public void publishSettlement(String path, Date date, String id)
			throws IOException {
		String fileName = path+"/"+date+"-"+id+".txt";
		File file = new File(fileName);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		pw.println("Settlement Report Date : "+date);
		pw.println("MEMBER ID : "+ id);
		pw.println("============ transaction reports =============");
		// publish normal transaction reports ( acquire - issuer ) and publish issuer report
		List<BcnxSettle> issTxn = bcnxSettleService.getBcnxFinIss(date,id);
		issTxn = bcnxSettleFee.setUpIssFee(issTxn);
		printReport(pw,"ISSUER :",issTxn);
		
		// publish acquire report
		List<BcnxSettle> acqTxn = bcnxSettleService.getBcnxFinAcq(date,id);
		issTxn = bcnxSettleFee.setUpAcqFee(acqTxn);
		printReport(pw,"ACQUIRER :",acqTxn);
		
		// publish reversal report
		List<BcnxSettle> revTxn = bcnxSettleService.getBcnxRev(date, id);
		revTxn = bcnxSettleFee.setUpIssFee(revTxn);
		printReport(pw,"REVERSAL :",revTxn);
		
		// publish error report
		List<BcnxSettle> errTxn = bcnxSettleService.getBcnxErr(date,id);
		errTxn = bcnxSettleFee.setUpIssFee(errTxn);
		printReport(pw,"ERROR :",errTxn);
		
		// publish charge back ( issuer - acquire )
		List<BcnxSettle> issChb = disputeTxnService.issChb(date, id);
		issChb = bcnxSettleFee.setUpIssFee(issChb);
		printReport(pw,"ISSUER CHARGE BACK :",issChb);
		
		List<BcnxSettle> acqChb = disputeTxnService.acqChb(date, id);
		acqChb = bcnxSettleFee.setUpAcqFee(acqChb);
		printReport(pw,"ACQUIRER CHARGE BACK :",acqChb);
		
		// publish adjustment ( issuer - acquire )
		List<BcnxSettle> issAdj = disputeTxnService.issAdj(date, id);
		issAdj = bcnxSettleFee.setUpIssFee(issAdj);
		printReport(pw,"ISSUER ADJUSTMENT :",issAdj);
		
		List<BcnxSettle> acqAdj = disputeTxnService.acqAdj(date, id);
		acqAdj = bcnxSettleFee.setUpAcqFee(acqAdj);
		printReport(pw,"ACQUIRER ADJUSTMENT :",acqAdj);
		
		// net settlement report
		printNetSettlement(pw,
				revTxn.size(), getTotal(revTxn), getFee(revTxn),
				errTxn.size(), getTotal(errTxn), getFee(errTxn),
				issTxn.size(), getTotal(issTxn), getFee(issTxn),
				issChb.size(), getTotal(issChb), getFee(issChb),
				issAdj.size(), getTotal(issAdj), getFee(issAdj),
				acqTxn.size(), getTotal(acqTxn), getFee(acqTxn),
				acqChb.size(), getTotal(acqChb), getFee(acqChb),
				acqAdj.size(), getTotal(acqAdj), getFee(acqAdj)
				);
		// reconciliation report
		pw.println("\r\n\r\nRECONCILIATION :");
		
		int seq = printReconReports(pw,issTxn,1);
		seq = printReconReports(pw,acqTxn,seq);
		seq = printReconReports(pw,issChb,seq);
		seq = printReconReports(pw,issAdj,seq);
		seq = printReconReports(pw,acqChb,seq);
		seq = printReconReports(pw,acqAdj,seq);
		seq = printReconReports(pw,revTxn,seq);
		printReconReports(pw,errTxn,seq);
		
		pw.close();

	}
	@Override
	public double getNet() {
		return net;
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
					bcon.getCard(), bcon.getProc(), bcon.getRrn(), bcon.getStan(), bcon.getRes(), decimalFormat.format(bcon.getAmount()),
					decimalFormat.format(bcon.getFee()), bcon.getTermId(),bcon.getIss(),bcon.getAcq());
			total += bcon.getAmount();
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
			int issNum, double iss, double issFee,
			int isCbNum, double isCh, double isChf,
			int isAdNum, double isAd, double isAdf,
			int acqNum, double acq, double acqFee,
			int acCbNum, double acCh, double acChf,
			int acAdNum, double acAd, double acAdf){
		String pattern = "#,##0.00";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		pw.println("\r\n============= NET SETTLEMENT SUMMARY =============\r\n");
		pw.println("NON-FINANCAIL TRANSACTION SUMMARY");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.println("|         type         |    number     |        amount        |         fee        |");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","REVERSAL",revNum,decimalFormat.format(rev),decimalFormat.format(revFee));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ERROR",errNum,decimalFormat.format(err),decimalFormat.format(errFee));
		
		pw.println("\r\nFINANCAIL TRANSACTION SUMMARY");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.println("|         type         |    number     |        amount        |         fee        |");
		pw.println("+----------------------+---------------+----------------------+--------------------+");
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ISSUER",issNum,decimalFormat.format(-1*iss),decimalFormat.format(-1*issFee));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ISSUER CHARGE BACK",isCbNum,decimalFormat.format(isCh),decimalFormat.format(isChf));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ISSUER ADJUSTMENT",isAdNum,decimalFormat.format(isAd),decimalFormat.format(isAdf));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ACQUIRER",acqNum,decimalFormat.format(acq),decimalFormat.format(acqFee));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ACQUIRER CHARGE BACK",acCbNum,decimalFormat.format(-1*acCh),decimalFormat.format(-1*acChf));
		pw.printf("%23s\t%13d\t%20s\t%17s\r\n","ACQUIRER ADJUSTMENT",acAdNum,decimalFormat.format(-1*acAd),decimalFormat.format(-1*acAdf));
		
		pw.println("------------------------------------------------------------------------------------");
		double total = ( acq+acqFee) - (iss + issFee ) 
				+ (isCh + isChf) - (acCh + acChf) 
				+ (isAd + isAdf) - (acAd + acAdf);
		pw.printf("%20s %20s (LAK)","Net Settlement : ",decimalFormat.format(total));
		setNet(total);
	}
	private int printReconReports(PrintWriter pw,List<BcnxSettle> list, int count){
		for(BcnxSettle bcon : list){
			pw.printf("%4d%6s%6s%19s%6s%12s%6s%2s%012d%010d%8s%6s%6s%4s\r\n", count, UtilityService.date2Str(bcon.getDate()), bcon.getTime().replace(":", ""),
					bcon.getCard(), bcon.getProc(), bcon.getRrn(), bcon.getStan(), bcon.getRes(), (int)bcon.getAmount()*100,
					(int)bcon.getFee(), bcon.getTermId(),bcon.getIss(),bcon.getAcq()==null?"------":bcon.getAcq(),bcon.getMti());
			count++;
		}
		return count;
	}
	private double getTotal(List<BcnxSettle> list){
		double total = 0.0;
		for (BcnxSettle bcnxSettle : list) {
			total += bcnxSettle.getAmount();
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
}
