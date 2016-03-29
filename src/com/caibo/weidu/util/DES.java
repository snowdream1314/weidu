package com.caibo.weidu.util;


import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.util.Base64;

public class DES {

	private static final String DESKey = "weixinhui_encrypt_FEAFEAFEQFEQF";

	private static byte[] iv1 = { (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };

	/**
	 * ���ܱ��뺯��
	 * 
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public byte[] desEncrypt(byte[] plainText) throws Exception {

		IvParameterSpec iv = new IvParameterSpec(iv1);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		DESKeySpec dks = new DESKeySpec(DESKey.getBytes());
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	/**
	 * ���ܱ��뺯��
	 * 
	 * @param encryptText
	 * @return
	 * @throws Exception
	 */
	public byte[] desDecrypt(byte[] encryptText) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// key�ĳ��Ȳ��ܹ�С��8λ�ֽ�
		DESKeySpec dks = new DESKeySpec(DESKey.getBytes("UTF-8"));
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(iv1);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		return cipher.doFinal(encryptText);

	}

	/**
	 * ʵ�ֵ��ü���
	 * 
	 * @param input
	 * @return
	 */
	public String encrypt(String input) {
		String result = "input";
		try {
			result = base64Encode(desEncrypt(input.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ���ý���
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String input) throws Exception {

		byte[] result = base64Decode(input);

		return new String(desDecrypt(result),Charset.forName("UTF8"));

	}

	public String base64Encode(byte[] s) {
		if (s == null)
			return null;
		return Base64.encodeToString(s, Base64.DEFAULT);

	}

	public byte[] base64Decode(String s) throws IOException {

		if (s == null)

			return null;

		return Base64.decode(s, Base64.DEFAULT);
	}
}