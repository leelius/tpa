package com.scyf.bean;

/**
 * 存放微信公众号的开发信息
 * @author 昊琦
 * @version 1
 * @创建时间 2018年3月14日19:51:29
 */
public class WeChat {
	
	/**开发者ID**/
	private String appId;
	
	/**开发者密码**/
	private String appSecret;

	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
