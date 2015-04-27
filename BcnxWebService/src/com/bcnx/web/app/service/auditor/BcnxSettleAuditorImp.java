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

import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.BcnxTxn;
import com.bcnx.web.app.service.entity.Bin;
import com.bcnx.web.app.service.entity.CardType;

public class BcnxSettleAuditorImp extends BatchAuditJob implements BcnxSettleAuditor {
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
	
	@SuppressWarnings("resource")
	@Override
	public List<BcnxSettle> toList(File file, Date date) throws IOException {
		BufferedReader bufferReader = new BufferedReader(new FileReader(file));
		ignorPattern = Pattern.compile(IGNOR);
		slotPattern  = Pattern.compile(SLOT);
		numPattern   = Pattern.compile(NUM);
		balPattern   = Pattern.compile(BAL);
		timePattern  = Pattern.compile(TIME);
		String previous = null;
		String line = null;
		while((line = bufferReader.readLine())!=null){
			matcher = ignorPattern.matcher(line);
			String[] token = line.split(" ");
			if(!matcher.find()&&(token.length>=13)){
				matcher = slotPattern.matcher(token[0]);
				String slot = null;
				if(matcher.find())
					slot = matcher.group().trim();
				logger.debug("slot number : "+slot);
				if(token[1]==null)
					continue;
				String type = token[1].trim();
				if(token[2]==null)
					continue;
				String proc = token[2].trim();
				if(token[3]==null)
					continue;
				String card	= token[3].trim();
				if(token[5]==null)
					continue;
				String res	= token[5].trim();
				if(token[6]==null)
					continue;
				String stan = token[6].trim();
				if(previous==null)
					continue;
				String time	= previous.trim();
				if(token[7]==null)
					continue;
				String amt	= token[7].trim();
				if(token[8]==null)
					continue;
				String atmId= token[8].substring(12,20).trim();
				if(token[9]==null)
					continue;
				String refer= token[9].trim();
				if(token[11]==null)
					continue;
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
				bcnxSettle.setAmt(Double.parseDouble(amt)/100);
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
					logger.debug(bcnxSettle);
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
		boolean chk1 = (bcnx==null);
		boolean chk2 = !(bcnxSettle.getAcq()==null || bcnxSettle.getAcq().isEmpty());
		boolean chk3 = !(bcnxSettle.getIss()==null || bcnxSettle.getIss().isEmpty());
		return chk1 && chk2 && chk3;
	}
}
