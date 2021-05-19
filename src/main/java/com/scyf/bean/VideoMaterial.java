package com.scyf.bean;

/**
 * 接收获取永久视频素材
 * @author WangYunfei
 * @version 1
 */
public class VideoMaterial {

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 下载链接
	 */
	private String down_url;
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
	public String getDown_url() {
		return down_url;
	}
	public void setDown_url(String down_url) {
		this.down_url = down_url;
	}
	@Override
	public String toString() {
		return "VideoMaterial [title=" + title + ", description=" + description + ", down_url=" + down_url + "]";
	}
	
	
}
