package com.scyf.bean;
/** 
 * 用户标识,用于批量获取用户信息时存放要查找的openid 和 以何种语言返回
 * @author wangkan
 * 创建时间：2018年3月22日 下午17:22:42  
 */
public class UserIdentification {
	/**
	 * 用户唯一标识
	 */
	private String openid;
	/**
	 * 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 */
	private String lang;
	
	
	
	/**
	 * 无参构造器
	 */
	public UserIdentification() {
		super();
		
	}
	
	/**
	 * 含参构造器
	 * @param openid
	 * @param lang
	 */
	public UserIdentification(String openid, String lang) {
		super();
		this.openid = openid;
		this.lang = lang;
	}


	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openId) {
		this.openid = openId;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}	
}
