package com.scyf.weixintool;

import com.scyf.httptool.HttpRequest;

import net.sf.json.JSONObject;

/**
 * 微信公众号的菜单管理
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月23日18:52:53
 */
public class WeiXinMenuManage {


	/**
	 * 创建菜单
	 * @API https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
	 * @方法 POST
	 * @param token
	 * @param json
	 * @return 
	 */
	public static JSONObject createMenu(String token,String json){
		
		String menu_url ="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESSTOKEN";
		
		menu_url = menu_url.replace("ACCESSTOKEN", token);
		
		return HttpRequest.sendPost(menu_url, json);
	}
	
	
	/**
	 * 删除菜单
	 * @API https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN
	 * @方法 GET
	 * @param token
	 */
	public static JSONObject clearMenu(String token){
		
		String menu_url ="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESSTOKEN";
		
		menu_url = menu_url.replace("ACCESSTOKEN", token);
		
		return HttpRequest.sendGet(menu_url);
	}
	
}
