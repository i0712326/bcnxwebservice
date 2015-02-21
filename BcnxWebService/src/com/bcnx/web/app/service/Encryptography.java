package com.bcnx.web.app.service;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class Encryptography {
	private static final StrongPasswordEncryptor strongPasswordEncryptor = new StrongPasswordEncryptor();
	public static String encrypt(String text) {
		return strongPasswordEncryptor.encryptPassword(text);
	}
	public static boolean checkPasswd(String passwd, String encrypted){
		return strongPasswordEncryptor.checkPassword(passwd, encrypted);
	}
}
