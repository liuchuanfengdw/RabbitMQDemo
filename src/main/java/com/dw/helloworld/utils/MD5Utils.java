package com.dw.helloworld.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * MD5加密工具
 * @Author: DING WEI
 * @Date: 2019-05-02 11:03
 * @Version: 1.0
 */
public class MD5Utils {

	/**
	 * @Description: 对字符串进行md5加密 
	 */
	public static String getMD5Str(String strValue) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
		return newstr;
	}

	public static void main(String[] args) {
		try {
			String md5 = getMD5Str("imooc");
			System.out.println(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
