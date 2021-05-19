package com.scyf.weixintool;

import com.scyf.bean.GlobalAccessToken;
import com.scyf.httptool.HttpRequest;

import net.sf.json.JSONObject;

/**
 * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token.
 * 注意与微信网页授权的access_token区分
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月18日15:12:50
 */
public class WeiXinGlobalToken {

	/**
	 * 获取微信公众号的全局唯一接口调用凭据，注意与微信网页授权的access_token区分
	 * @API https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
	 * @方法 GET
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static GlobalAccessToken  getGlobalAccessToken(String appId,String appSecret){
		
		String global_access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		
		//替换参数
		global_access_token = global_access_token.replace("APPID", appId);
		global_access_token = global_access_token.replace("APPSECRET", appSecret);
		
		//发送请求
		JSONObject jsonObject = HttpRequest.sendGet(global_access_token);
		
		//处理返回数据
		if (jsonObject.containsKey("errcode")) {
			return null;
		}else{
			GlobalAccessToken globalAccessToken = (GlobalAccessToken)JSONObject.toBean(jsonObject, GlobalAccessToken.class);
			
			return globalAccessToken;
		}
	}
}
