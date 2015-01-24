package com.bcnx.web.app.service.auditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.BcnxTxnService;
import com.bcnx.web.app.service.BinService;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.BcnxTxn;
import com.bcnx.web.app.service.entity.Bin;
import com.bcnx.web.app.service.entity.CardType;

public class BcnxSettleAuditorImp implements BcnxSettleAuditor {
	private static final Logger logger = Logger.getLogger(BcnxSettleAuditorImp.class);
	private static final String IGNOR	= "(NOT FINANCIAL TRANSACTION)";
	private static final String SLOT 	= "([0-9]{1,3})";
	private static final String NUM	 	= "^[0-9]{1,}$";
	private static final String BAL	 	= "^3[1|0]";
	private static final String TIME 	= "^[0-9]{2}:[0-9]{2}:[0-9]{2}$";
	private Pattern ignorPattern;
	private Pattern slotPattern;
	private Pattern numPattern;
	private Pattern balPattern;
	private Pattern timePattern;
	private Matcher matcher;
	private static List<BcnxSettle> bcnxSettles = new ArrayList<BcnxSettle>();
	private BinService binService;
	private BcnxSettleService bcnxSettleService;
	private BcnxTxnService bcnxTxnService;
	public void setBinService(BinService binService){
		this.binService = binService;
	}
	public void setBcnxSettleService(BcnxSettleService bcnxSettleService){
		this.bcnxSettleService = bcnxSettleService;
	}
	public void setBcnxTxnService(BcnxTxnService bcnxTxnService){
		this.bcnxTxnService = bcnxTxnService;
	}
	@SuppressWarnings("resource")
	@Override
	public List<BcnxSettle> doWork(File file, Date date) throws IOException {
		BufferedReader bufferReader = new BufferedReader(new FileReader(file));
		ignorPattern = Pattern.compile(IGNOR);
		slotPattern = Pattern.compile(SLOT);
		numPattern = Pattern.compile(NUM);
		balPattern = Pattern.compile(BAL);
		timePattern = Pattern.compile(TIME);
		String previous = null;
		String line = null;
		while((line = bufferReader.readLine())!=null){
			matcher = ignorPattern.matcher(line);
			String[] token = line.split(" ");
			if(!matcher.find()&&(token.length>1)){
				matcher = slotPattern.matcher(token[0]);
				String slot = null;
				if(matcher.find())
					slot = matcher.group().trim();
				String type = token[1].trim();
				String proc = token[2].trim();
				String card	= token[3].trim();
				String res	= token[5].trim();
				String stan = token[6].trim();
				String time	= previous.trim();
				String amt	= token[7].trim();
				String atmId= token[8].substring(12,20).trim();
				String refer= token[9].trim();
				String acqId= token[11].trim();
				
				matcher = numPattern.matcher(amt.trim());
				if(!matcher.find())
					continue;
				
				matcher = balPattern.matcher(proc);
				if(matcher.find())
					amt = "0";
				String bin = card.substring(0, 6);
				Bin bb = new Bin();
				bb.setBin(bin);
				bb = binService.getBin(bb);
				String issId = "621354";
				String ct = "CUPX";
				CardType cardType = new CardType();
				cardType.setType(ct);
				cardType.setRemark("OFF US CUP CARD");
				if(bb!=null){
					issId=bb.getMember().getIin();
					cardType = bb.getType();
				}
				BcnxSettle bcnxSettle = new BcnxSettle();
				bcnxSettle.setSlot(slot);
				bcnxSettle.setMti(type);
				bcnxSettle.setRrn(refer);
				bcnxSettle.setStan(stan);
				bcnxSettle.setDate(date);
				bcnxSettle.setProc(proc);
				bcnxSettle.setCard(card);
				bcnxSettle.setRes(res);
				bcnxSettle.setTime(time);
				bcnxSettle.setAmount(Double.parseDouble(amt)/100);
				bcnxSettle.setTermId(atmId);
				bcnxSettle.setAcq(acqId);
				bcnxSettle.setIss(issId);
				bcnxSettle.setCardType(cardType);
				
				BcnxTxn bcnxTxn = new BcnxTxn();
				bcnxTxn.setSlot(slot);
				bcnxTxn.setMti(type);
				bcnxTxn.setRrn(refer);
				bcnxTxn.setStan(stan);
				bcnxTxn = bcnxTxnService.getBcnxTxn(bcnxTxn);
				bcnxSettle.setBcnxTxn(bcnxTxn);
				
				if(checkData(bcnxSettle)){
					logger.debug(bcnxSettle.toString());
					bcnxSettleService.save(bcnxSettle);
					bcnxSettles.add(bcnxSettle);
				}
			}
			else{
				matcher = timePattern.matcher(line.trim());
				if(matcher.find())
					previous = line.trim();
				else
					previous = null;
			}
		}
		return bcnxSettles;
	}
	
	private boolean checkData(BcnxSettle bcnxSettle){
		if(bcnxSettles.isEmpty())
			return true;
		BcnxSettle bcnx = bcnxSettleService.getBcnxSettle(bcnxSettle);
		if(bcnx==null)
			return true;
		return false;
			
	}
}
