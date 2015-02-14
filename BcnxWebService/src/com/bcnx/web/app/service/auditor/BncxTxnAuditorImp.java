package com.bcnx.web.app.service.auditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bcnx.web.app.service.BcnxTxnService;
import com.bcnx.web.app.service.BinService;
import com.bcnx.web.app.service.entity.BcnxTxn;
import com.bcnx.web.app.service.entity.Bin;

public class BncxTxnAuditorImp implements BcnxTxnAuditor {
	private static Logger logger = Logger.getLogger(BncxTxnAuditorImp.class);
	private static final String breaker = "waiting on router queue for slot....";
	private BcnxTxnService bcnxTxnService;
	private BinService binService;
	private static List<BcnxTxn> list = new ArrayList<BcnxTxn>();
	private static List<String> slot = new ArrayList<String>();
	public void setBcnxTxnService(BcnxTxnService bcnxTxnService){
		this.bcnxTxnService = bcnxTxnService;
	}
	public void setBinService(BinService binService){
		this.binService = binService;
	}
	@SuppressWarnings("resource")
	@Override
	public void toBcnxTxn(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while((line = br.readLine())!=null){
			if(line.equals(breaker))
			{
				if(!slot.isEmpty())
					doWork(slot);
				slot.clear();
			}
			else
				slot.add(line);
		}
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
	private void doWork(List<String> slot){
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
		String mti = bcnxTxn.getMti();
		if(mti==null){
			logger.info(bcnxTxn.toString());
			return ;
		}
		boolean check = mti.equals("0200")||mti.equals("0210")||mti.equals("0420")||mti.equals("0430");
		if(check){
			logger.info(bcnxTxn.toString());
			Bin bin = new Bin();
			bin.setBin(bcnxTxn.getCard().substring(0, 6));
			bin = binService.getBin(bin);
			String iin = "621354";
			if(bin!=null)
				iin = bin.getMember().getIin();
			bcnxTxn.setIss(iin);
			list.add(bcnxTxn);
		}
	}
	@Override
	public void refine(){
		for(BcnxTxn item : list){
			String mti = item.getMti();
			boolean check = mti.equals("0200")||mti.equals("0420");
			if(check){
				BcnxTxn chk = bcnxTxnService.getBcnxTxn(item);
				if(chk==null){
					logger.debug(item.toString());
					bcnxTxnService.save(item);
				}
			}
			else{
				logger.debug(item.toString());
				bcnxTxnService.update(item);
			}
		}
	}
}
