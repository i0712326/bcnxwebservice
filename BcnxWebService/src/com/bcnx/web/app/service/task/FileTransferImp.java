package com.bcnx.web.app.service.task;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bcnx.web.app.utility.UtilityService;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FileTransferImp implements FileTransfer {
	private static final Logger logger = Logger.getLogger(FileTransferImp.class);
	private String hostname;
	private int port;
	private String user;
	private String passwd;
	private String remotePath;
	private String localPath;
	private String expression;
	public FileTransferImp(String hostname, String user, String passwd, String remotePath, String localPath, String sport){
		this.hostname = hostname;
		this.port = Integer.parseInt(sport);
		this.user = user;
		this.passwd = passwd;
		this.remotePath = remotePath;
		this.localPath = localPath;
	}
	@Override
	public void setExpression(String expression){
		this.expression = expression;
	}
	@Override
	public List<String> download(String date) throws IOException, JSchException, SftpException{
		
		JSch jsch = new JSch();
        Session session = null;
        session = jsch.getSession(user, hostname, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(passwd);
        session.connect();
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        @SuppressWarnings("unchecked")
		List<ChannelSftp.LsEntry> list = sftpChannel.ls(remotePath+"/"+expression);
        List<String> fileNames = new ArrayList<String>();
        for(ChannelSftp.LsEntry entry : list) {
        	String fileName = entry.getFilename();
        	 int len = fileName.length();
        	 String fileDate = fileName.substring(len-6);
        	 Date currentDate = UtilityService.getCurrentDate();
    		 String str = UtilityService.date2Str(currentDate);
        	 boolean check = Integer.parseInt(fileDate)>Integer.parseInt(date);
        	 check = check&&(Integer.parseInt(fileDate)<Integer.parseInt(str));
        	 logger.debug("checking file : "+fileName + "-> result :"+check);
        	 if(check){
				String localFile = localPath + "/" + fileName;
				String remoteFile = remotePath + "/" + fileName;
				logger.debug("transfer file from " + remoteFile + " to "
						+ localFile);
				logger.debug("downloading ............");
				sftpChannel.get(remoteFile, localFile);
				fileNames.add(fileName);
				logger.debug("dowload completed.");
        	 }
        }
        sftpChannel.exit();
        session.disconnect();
        return fileNames;
	}
}
