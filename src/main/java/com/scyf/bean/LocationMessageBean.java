package com.scyf.bean;
/**
 * 接收地理位置消息的bean
 * @author 昊琦
 * @version 1.0
 * @创建时间：2018年3月22日17:31:33
 */
public class LocationMessageBean {

	/**开发者微信号**/
	private String toUserName;
	/**发送方帐号（一个OpenID）**/
	private String fromUserName;
	/**消息创建时间**/
	private String createTime;
	/**location**/
	private String msgType;
	/**地理位置维度**/
	private String location_X;
	/**地理位置经度**/
	private String location_Y;
	/**地图缩放大小**/
	private String scale;
	/**地理位置信息**/
	private String label;
	/**消息id**/
	private String msgId;
	
	
	/**
	 * 无参构造器
	 */
	public LocationMessageBean() {}


	/**
	 * 含参构造器
	 * @param toUserName
	 * @param fromUserName
	 * @param createTime
	 * @param msgType
	 * @param location_X
	 * @param location_Y
	 * @param scale
	 * @param label
	 * @param msgId
	 */
	public LocationMessageBean(String toUserName, String fromUserName, String createTime, String msgType,
			String location_X, String location_Y, String scale, String label, String msgId) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.location_X = location_X;
		this.location_Y = location_Y;
		this.scale = scale;
		this.label = label;
		this.msgId = msgId;
	}


	public String getToUserName() {
		return toUserName;
	}


	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}


	public String getFromUserName() {
		return fromUserName;
	}


	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getMsgType() {
		return msgType;
	}


	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}


	public String getLocation_X() {
		return location_X;
	}


	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}


	public String getLocation_Y() {
		return location_Y;
	}


	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}


	public String getScale() {
		return scale;
	}


	public void setScale(String scale) {
		this.scale = scale;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getMsgId() {
		return msgId;
	}


	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
	
}
