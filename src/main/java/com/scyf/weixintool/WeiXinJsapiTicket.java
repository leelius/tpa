package com.scyf.weixintool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.scyf.bean.JsapiTicketBean;
import com.scyf.httptool.HttpRequest;

import net.sf.json.JSONObject;

/**
 * 管理微信公众号内调用微信的js接口时的授权信息
 * @author haoqi
 * @version 1.0
 * @创建时间：2018年5月2日16:07:11
 *
 */
public class WeiXinJsapiTicket {
	
	/**
	 * 获取微信授权接口
	 * @param global_token
	 * @return
	 */
	public static JsapiTicketBean geJsapiTicket(String global_token) {
		
		String jsapi_ticket_url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		
		jsapi_ticket_url = jsapi_ticket_url.replace("ACCESS_TOKEN", global_token);
		
		JSONObject jsonObject = HttpRequest.sendGet(jsapi_ticket_url);
		
		return (JsapiTicketBean)JSONObject.toBean(jsonObject, JsapiTicketBean.class);
	}
	
	/***
	 * 2017年5月3日取消
	 * @param content
	 * @return
	 */
	public static String getSHA1Digest(String content) {

		MessageDigest md = null;
		String hashcode = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.getBytes());
			hashcode = byteToStr(digest).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashcode;
	}
	
	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}
