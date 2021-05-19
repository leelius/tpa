package com.scyf.bean;
/**
 * 存放链接消息的bean
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月22日18:46:11
 */
public class LinkMessageBean {

	/**接收方微信号**/
	private String toUserName;
	/**发送方微信号，若为普通用户，则是一个OpenID**/
	private String fromUserName;
	/**消息创建时间**/
	private String createTime;
	/**消息类型，link**/
	private String msgType;
	/**消息标题**/
	private String title;
	/**消息描述**/
	private String description;
	/**消息链接**/
	private String url;
	/**消息id**/
	private String msgId;
	
	/**
	 * 无参构造器
	 */
	public LinkMessageBean() {}

	/**
	 * 含参构造器
	 * @param toUserName
	 * @param fromUserName
	 * @param createTime
	 * @param msgType
	 * @param title
	 * @param description
	 * @param url
	 * @param msgId
	 */
	public LinkMessageBean(String toUserName, String fromUserName, String createTime, String msgType, String title,
			String description, String url, String msgId) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.title = title;
		this.description = description;
		this.url = url;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
	
	
}
