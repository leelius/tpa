package com.scyf.bean;
/**
 * 接收普通文本消息的bean
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月22日14:57:35
 */
public class TextMessageBean {

	/**开发者微信号**/
	private String toUserName;
	/**发送方帐号（一个OpenID）**/
	private String fromUserName;
	/**消息创建时间 **/
	private String createTime;
	/**text**/
	private String msgType;
	/**文本消息内容**/
	private String content;
	/**消息id**/
	private String msgId;
	
	
	/**
	 * 无参构造器
	 */
	public TextMessageBean(){}
	
	/**
	 * 带参构造器
	 * @param toUserName
	 * @param fromUserName
	 * @param createTime
	 * @param msgType
	 * @param content
	 * @param msgId
	 */
	public TextMessageBean(String toUserName, String fromUserName, String createTime, String msgType, String content,
			String msgId) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.content = content;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}