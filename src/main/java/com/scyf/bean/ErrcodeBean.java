package com.scyf.bean;
/**
 * 调用api返回的错误信息，通常errcode为0表示正常
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月26日10:39:39
 */
public class ErrcodeBean {
	
	/**错误代码**/
	private int errcode;
	/**错误信息**/
	private String errmsg;
	
	

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	

}
