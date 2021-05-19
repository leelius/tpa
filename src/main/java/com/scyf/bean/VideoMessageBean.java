package com.scyf.bean;

/**
 * 接收视频消息或者微信小视频消息的bean
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月22日17:26:42
 *
 */
public class VideoMessageBean {

	/**开发者微信号**/
	private String toUserName;
	/**发送方帐号（一个OpenID）**/
	private String fromUserName;
	/**消息创建时间 **/
	private String createTime;
	/**视频为video，小视频为shortvideo**/
	private String msgType;
	/**视频消息媒体id，可以调用多媒体文件下载接口拉取数据**/
	private String mediaId;
	/**视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据**/
	private String thumbMediaId;
	/**消息id**/
	private String msgId;
	
	/**
	 * 无参构造器
	 */
	public VideoMessageBean() {
		super();
	}

	/**
	 * 含参构造器
	 * @param toUserName
	 * @param fromUserName
	 * @param createTime
	 * @param msgType
	 * @param mediaId
	 * @param thumbMediaId
	 * @param msgId
	 */
	public VideoMessageBean(String toUserName, String fromUserName, String createTime, String msgType, String mediaId,
			String thumbMediaId, String msgId) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.mediaId = mediaId;
		this.thumbMediaId = thumbMediaId;
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

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
	
}
