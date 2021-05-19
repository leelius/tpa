package com.scyf.bean;

import java.util.Arrays;

/**
 * 接收获取到的图文消息素材
 * @author WangYunfei
 * @version 1
 */
public class News {

	/**
	 * 图文消息内容
	 */
	private NewsItem[] news_item;
	/**
	 * 创建时间
	 */
	private String create_time;
	/**
	 * 更新时间
	 */
	private String update_time;
	public NewsItem[] getNews_item() {
		return news_item;
	}
	public void setNews_item(NewsItem[] news_item) {
		this.news_item = news_item;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	@Override
	public String toString() {
		return "GetNews [news_item=" + Arrays.toString(news_item) + ", create_time=" + create_time + ", update_time="
				+ update_time + "]";
	}
	
	
	
}
