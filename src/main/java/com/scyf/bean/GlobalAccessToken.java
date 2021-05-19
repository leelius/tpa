package com.scyf.bean;


/**
 * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
 * 注意去微信网页授权的access_token区分
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月18日15:21:55
 */
public class GlobalAccessToken {

	/**获取到的凭证**/
	private String access_token;
	/**凭证有效时间，单位：秒**/
	private String expires_in;
	
	
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
	
}
