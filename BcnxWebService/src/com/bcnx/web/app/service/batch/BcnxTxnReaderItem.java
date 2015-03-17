package com.bcnx.web.app.service.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.bcnx.web.app.service.auditor.BncxTxnAuditorImp;
import com.bcnx.web.app.service.entity.BcnxTxn;

public class BcnxTxnReaderItem implements ItemReader<BcnxTxn> {
	private static Logger logger = Logger.getLogger(BncxTxnAuditorImp.class);
	private static final String breaker = "waiting on router queue for slot....";
	//private static List<BcnxTxn> list = new ArrayList<BcnxTxn>();
	private static List<String> slot = new ArrayList<String>();
	private static File file;
	private static BufferedReader br;
	private static String line;
	static{
		try {
			file = new File("sample/router.audit.150313");
			br = new BufferedReader(new FileReader(file));
			line = null;
		} catch (FileNotFoundException e) {
			logger.debug("Exception occured while try to read file", e);
		}
		
	}
	@Override
	public BcnxTxn read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		while((line = br.readLine())!=null){
			if(line.equals(breaker))
			{
				if(!slot.isEmpty()){
					BcnxTxn bcnx = doWork(slot);
					slot.clear();
					return bcnx;
				}
			}
			else
				slot.add(line);
		}
		return null;
	}
	private static String MTI  = "1";
	private static String CARD = "2";
	private static String PROC = "3";
	private static String AMT  = "4";
	private static String TRAC = "11";
	private static String TIME = "12";
	private static String DATE = "13";
	private static String EXP  = "14";
	private static String MCC  = "18";
	private static String CTC  = "19";
	private static String PMOD = "22";
	private static String CONC = "25";
	private static String FEE  = "28";
	private static String ACQ  = "32";
	private static String RRN  = "37";
	private static String APPR = "38";
	private static String RES  = "39";
	private static String TRID = "41";
	private static String LOC  = "43";
	private static String TXNC = "49";
	
	private static String dataPattern = "\\d{2}:\\d{2}:\\d{2} \\[\\s+(\\d{1,3})\\]  \\[\\s+\\d{1,3}\\]  \\[(.+)\\]";
	private static String slotPattern = "Slot Id\\s+:\\s+<(\\d+)>";
	private static Pattern pattern = Pattern.compile(dataPattern);
	private static Pattern patternSlot = Pattern.compile(slotPattern);
	private Matcher matcher;
	private BcnxTxn doWork(List<String> slot){
		BcnxTxn bcnxTxn = new BcnxTxn();
		for(String item : slot){
			matcher = patternSlot.matcher(item);
			if(matcher.find()){
				String s = matcher.group(1).trim();
				bcnxTxn.setSlot(s);
			}
			matcher = pattern.matcher(item);
			if(matcher.find()){
				String de 	= matcher.group(1).trim();
				String data = matcher.group(2).trim();
				if(de.equals(MTI)){
					bcnxTxn.setMti(data);
					if(data.equals("0800")||data.equals("0810"))
						continue;
				}
				if(de.equals(CARD))
					bcnxTxn.setCard(data);
				if(de.equals(PROC))
					bcnxTxn.setProc(data);
				if(de.equals(AMT))
					bcnxTxn.setAmount(Double.parseDouble(data)/100);
				if(de.equals(TRAC))
					bcnxTxn.setStan(data);
				if(de.equals(TIME))
					bcnxTxn.setTime(data);
				if(de.equals(DATE))
					bcnxTxn.setDate(data);
				if(de.equals(EXP))
					bcnxTxn.setExp(data);
				if(de.equals(MCC))
					bcnxTxn.setMcc(data);
				if(de.equals(CTC))
					bcnxTxn.setCountry(data);
				if(de.equals(PMOD))
					bcnxTxn.setPos(data);
				if(de.equals(CONC))
					bcnxTxn.setCondCode(data);
				if(de.equals(FEE))
					bcnxTxn.setFee(Double.parseDouble(data.replaceAll("D", "").replaceAll("C", "")));
				if(de.equals(ACQ))
					bcnxTxn.setAcq(data);
				if(de.equals(RRN))
					bcnxTxn.setRrn(data);
				if(de.equals(APPR))
					bcnxTxn.setAppr(data);
				if(de.equals(RES))
					bcnxTxn.setRes(data);
				if(de.equals(TRID))
					bcnxTxn.setTermId(data);
				if(de.equals(LOC))
					bcnxTxn.setLocation(data);
				if(de.equals(TXNC))
					bcnxTxn.setCurr(data);
			}
		}
		return bcnxTxn;
	}
}
