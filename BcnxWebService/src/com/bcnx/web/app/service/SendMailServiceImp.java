package com.bcnx.web.app.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class SendMailServiceImp implements SendMailService {
	private static final Logger logger = Logger.getLogger(SendMailServiceImp.class);
	private String sender;
	private String passwd;
	private String host;
	private String port;
	private String title;
	public void setSender(String sender) {
		this.sender = sender;
	}
	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
	public void setHost(String host){
		this.host = host;
	}
	public void setPort(String port){
		this.port = port;
	}
	public void setTitle(String title){
		this.title = title;
	}
	@Override
	public void sendMail(String receiver, String content){
		Message message = new MimeMessage(getSession());
		// creates header
		try {
			message.setFrom(new InternetAddress(sender));

			message.setRecipient(RecipientType.TO,
					new InternetAddress(receiver));
			message.setSubject(title);
			message.setSentDate(new Date());
			// creates multi-part
			Multipart multipart = new MimeMultipart();
			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(content, "text/plain");
			multipart.addBodyPart(messageBodyPart);
			// sets the multi-part as e-mail's content
			message.setContent(multipart);

			// sends the e-mail
			Transport.send(message);
		} catch (MessagingException e) {
			logger.debug("Exception occured while try to send email", e);
		}
	}
	private Session getSession() {
		Authenticator authenticator = new Authenticator();

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", sender);
		properties.setProperty("mail.smtp.auth", "true");

		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port);

		return Session.getInstance(properties, authenticator);
	}
	
	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;
		public Authenticator() {
			authentication = new PasswordAuthentication(sender, passwd);
		}
		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
}
