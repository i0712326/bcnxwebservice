package com.bcnx.web.app.service;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class TripleDES implements Encryptography{
	private static final String secretKey = "1D92D077EF105BE95D27F81884ECF780";
	public String encrypt(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = message.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			String base64EncryptedString = new String(base64Bytes);

			return base64EncryptedString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
