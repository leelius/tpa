package com.scyf.weixintool;

import java.util.ArrayList;
import java.util.List;

import com.scyf.bean.BasicUserInfo;
import com.scyf.bean.UserIdentification;
import com.scyf.httptool.HttpRequest;

import net.sf.json.JSONObject;

/** 
 * 微信用户管理
 * @author wangkan
 * @version 2.0
 * 创建时间：2018年3月22日 下午16:11:32  
 */
public class WeiXinUserInfoManage {
	/**
	 * 获取用户基本信息
	 * http请求方式: GET
	 * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	 * @param access_token
	 * @param openid
	 * @return JSON
	 */
	public static BasicUserInfo getUserInfo(String access_token,String openid) {
		String userInfo_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		userInfo_url = userInfo_url.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		//发送http请求
		JSONObject jsonObject = (JSONObject)HttpRequest.sendGet(userInfo_url);
		if (jsonObject.containsKey("errcode")) {
			//如果错误则返回null
			return null ; 
		}else if (jsonObject.containsKey("nickname")) {
			//如果请求后接收到的数据符合要求
			BasicUserInfo basicUserInfo = (BasicUserInfo)JSONObject.toBean(jsonObject, BasicUserInfo.class);	
			return basicUserInfo;
		}else {
			return null;
		}
	}
	/**
	 * 批量获取用户信息
	 * http请求方式: POST
	 * https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
	 * @param access_token
	 * @param UserIdentification
	 * @return JSON
	 */
	public static List<BasicUserInfo> getUserInfoList(String access_token,UserIdentification[] userIdentification) {
		String userInfo_url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
		userInfo_url = userInfo_url.replace("ACCESS_TOKEN", access_token);
	
		List<BasicUserInfo> list = new ArrayList<>();
		// 遍历可变长参数数组
		for (int i = 0;i < userIdentification.length;i++) {
			JSONObject jsonObject = JSONObject.fromObject(userIdentification[i]);
			String string2 = "{\"user_list\":["+jsonObject+"]}";
			JSONObject jsonObject2 = (JSONObject)HttpRequest.sendPost(userInfo_url, string2);
			String string3 = jsonObject2.toString().replaceAll("\\{\"user_info_list\":\\[","").replaceAll("\\]\\}","");
			// 转换成JSONObject
			JSONObject jsonObject3 = JSONObject.fromObject(string3);
			if (jsonObject3.containsKey("nickname")) {
				//如果请求后接收到的数据符合要求
				BasicUserInfo basicUserInfo = (BasicUserInfo)JSONObject.toBean(jsonObject3, BasicUserInfo.class);	
				list.add(basicUserInfo);
			}			
		}	
		return list;
	}
}