package com.scyf.bean;

/**
 * 接收图片消息的bean
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月22日16:35:51
 *
 */
public class ImageMessageBean {
	
	/**开发者微信号**/
	private String toUserName;
	/**发送方帐号（一个OpenID）**/
	private String fromUserName;
	/**消息创建时间**/
	private String createTime;
	/**image**/
	private String msgType;
	/**图片链接（由系统生成）**/
	private String picUrl;
	/**图片消息媒体id，可以调用多媒体文件下载接口拉取数据**/
	private String mediaId;
	/**消息id**/
	private String msgId;
	
	/**
	 * 无参构造器
	 */
	public ImageMessageBean(){}

	public ImageMessageBean(String toUserName, String fromUserName, String createTime, String msgType, String picUrl,
			String mediaId, String msgId) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.picUrl = picUrl;
		this.mediaId = mediaId;
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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
