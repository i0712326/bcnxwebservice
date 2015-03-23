package com.bcnx.web.app.service.task;

import java.io.IOException;
import java.util.List;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface FileTransfer {
	public void setLocalPath(String localPath);
	public void setExpression(String expression);
	public List<String> download(String date) throws IOException, JSchException, SftpException;
}
