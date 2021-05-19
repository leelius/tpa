package com.scyf.bean;
/** 
 * MediaList子类
 * @author WangYunfei
 * @version 1
 * 创建时间：2018年3月26日
 * 
 */
public class Item {
	/**
	 * 素材media_id
	 */
	private String media_id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 更新时间
	 */
	private String update_time;
	/**
	 * 链接
	 */
	private String url;
	/**
	 * 图文消息内容
	 */
	private News[] content;
	
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ItemContent [media_id=" + media_id + ", name=" + name + ", update_time=" + update_time + ", url=" + url
				+ "]";
	}
	public News[] getContent() {
		return content;
	}
	public void setContent(News[] content) {
		this.content = content;
	}
}
