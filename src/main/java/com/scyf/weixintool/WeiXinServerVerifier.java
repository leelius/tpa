package com.scyf.weixintool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 在微信公众平台配置服务器接口时的校验工具
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月20日16:36:51
 */
public class WeiXinServerVerifier {
	/**
	 * 在微信公众平台修改接口配置信息时校验request请求是否来自微信服务器,是返回true，否则返回false
	 * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	 * @param token 令牌 
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	public static boolean verifyRequest(String signature,String token,String timestamp,String nonce){
		
		//token、timestamp和nonce进行排序
		String[] arrayStr = {token,timestamp,nonce};
		
		Arrays.sort(arrayStr);
		
		//排序后的结果拼接成字符串
		StringBuffer sBuffer = new StringBuffer();
		for (String string : arrayStr) {
			sBuffer.append(string);
		}
		
		//对排序结果进行sha1加密
		MessageDigest md ;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("SHA1加密失败，");
			return false;
		}
		byte[] digestByteArray = md.digest(sBuffer.toString().getBytes());
		//把最终的结果转成16进制字符串
		StringBuffer digestStr = new StringBuffer();
		
		for (byte b : digestByteArray) {
			String str = Integer.toHexString(b&0xFF);
			if (str.length()==2) {
				digestStr.append(str);
			}else {
				digestStr.append('0').append(str);
			}
		}
		//与signature判断比较
		if (digestStr.toString().equals(signature)) {
			
			return true;
		}else{
			
			return false;
		}
	}
	

}
