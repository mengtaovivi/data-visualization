package com.taikang.dataVis.core.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.utils]
 * 类名称:	[AESUtil]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年4月24日 下午12:05:32]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年4月24日 下午12:05:32]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
@SuppressWarnings("restriction")
public class AESUtil {
	/**
	 * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
	 */
	// private static String sKey = "P@ssw0rdP@ssw0rd";
	// private static String ivParameter = "1234567890123456";
	// 加密
	public String encrypt(String sSrc, String sKey, String ivParameter) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。
	}

	// 解密
	public String decrypt(String sSrc, String sKey, String ivParameter) throws Exception {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}

	// byte转16进制
	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length);
		String sTemp;
		for (int i = 0; i < bytes.length; i++) {
			sTemp = Integer.toHexString(0xFF & bytes[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	// 16进制转byte
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

		}
		return d;
	}

	// char转byte
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		AESUtil demo = new AESUtil();
		String str = "123456";
		System.out.println("加密前：" + str);
		String str1 = demo.encrypt(str, "P@ssw0rdP@ssw0rd", "1234567890123456");
		System.out.println("加密后，转换前：" + str1);
		String str2 = demo.byteToHexString(str1.getBytes());
		System.out.println("加密后，转换后：" + str2);
		System.out.println("解密，转换前：" + str2);
		String str3 = new String(hexStringToBytes(str2));
		System.out.println("解密，转换后：" + str3);
		String str4 = demo.decrypt(str3, "P@ssw0rdP@ssw0rd", "1234567890123456");
		System.out.println("解密后：" + str4);
	}
}
