package com.bcnx.web.app.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

public class EncryptographyImp implements Encryptography {
	private static final Logger logger = Logger.getLogger(EncryptographyImp.class);
	private static final String encryptionKey="1705CA96";
	@Override
	public String encrypt(String text) {
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(), "Blowfish");
		try {
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedBytes = cipher.doFinal(text.getBytes("UTF-8"));
			char[] encryptedData = Hex.encodeHex(encryptedBytes);
			return new String(encryptedData);
		} catch (NoSuchAlgorithmException e) {
			logger.debug("Exception occured due to wrong algorithm", e);
			return null;
		} catch (NoSuchPaddingException e) {
			logger.debug("Excepton occured due to can not padding",e);
			return null;
		} catch (InvalidKeyException e) {
			logger.debug("Exception occured due to invalid key",e);
			return null;
		} catch (IllegalBlockSizeException e) {
			logger.debug("Exception occured due to illegal block size",e);
			return null;
		} catch (BadPaddingException e) {
			logger.debug("Exception occured due to bad padding",e);
			return null;
		} catch (UnsupportedEncodingException e) {
			logger.debug("Exception occured due to unsupport encoding",e);
			return null;
		}
	}

}
