package com.scyf.bean;

/**
 * 接收语音消息的bean
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月22日17:00:54
 */
public class VoiceMessageBean {

	/**开发者微信号**/
	private String toUserName;
	/**发送方帐号（一个OpenID）**/
	private String fromUserName;
	/**消息创建时间**/
	private String createTime;
	/**语音为voice**/
	private String msgType;
	/**语音消息媒体id，可以调用多媒体文件下载接口拉取数据**/
	private String mediaId;
	/**	语音格式，如amr，speex等**/
	private String format;
	/**消息id**/
	private String msgId;
	
	/**
	 * 无参构造器
	 */
	public VoiceMessageBean(){}

	/**
	 * 含参构造器
	 * @param toUserName
	 * @param fromUserName
	 * @param createTime
	 * @param msgType
	 * @param mediaId
	 * @param format
	 * @param msgId
	 */
	public VoiceMessageBean(String toUserName, String fromUserName, String createTime, String msgType, String mediaId,
			String format, String msgId) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.mediaId = mediaId;
		this.format = format;
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

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
}
