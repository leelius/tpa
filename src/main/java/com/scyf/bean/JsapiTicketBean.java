package com.scyf.bean;

/**
 * 存放微信js接口调用授权的令牌相关信息
 * @author haoqi
 * @version 1.0
 * @创建时间 2018年5月2日15:57:24
 */
public class JsapiTicketBean {
	/**错误码**/
	private String errcode;
	/**错误信息**/
	private String errmsg;
	/**用于js接口授权的令牌**/
	private String ticket;
	/**ticket的有效时间**/
	private String expires_in;
	

	public JsapiTicketBean() {
		super();
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
	
}
