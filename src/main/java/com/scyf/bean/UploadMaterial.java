package com.scyf.bean;
/** 
 * 添加永久素材返回结果
 * 包括 video 图文消息 图片（image）、语音（voice）和缩略图（thumb） 
 * @author WangYunfei
 * @version 
 * 创建时间：2018年3月22日 下午5:16:33 
 * ..
 */
public class UploadMaterial {

	/**
	 * 永久素材media_id
	 */
	private String media_id;
	/**
	 * 链接 其他类型（除视频与图文消息）
	 */
	private String url;
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "UploadMaterial [media_id=" + media_id + ", url=" + url + "]";
	}
	
	
}
