package com.scyf.bean;

/**
 * 永久素材总数
 * @author WangYunfei
 * @version 1
 * 创建时间：2018年3月23日 下午5:24:03
 */
public class MediaCount {
	/**
	 * 视频总数
	 */
	private String voice_count;
	/**
	 * 音频总数
	 */
	private String video_count;
	/**
	 * 图像总数
	 */
	private String image_count;
	/**
	 * 图文消息总数
	 */
	private String news_count;
	public String getVoice_count() {
		return voice_count;
	}
	public void setVoice_count(String voice_count) {
		this.voice_count = voice_count;
	}
	public String getVideo_count() {
		return video_count;
	}
	public void setVideo_count(String video_count) {
		this.video_count = video_count;
	}
	public String getImage_count() {
		return image_count;
	}
	public void setImage_count(String image_count) {
		this.image_count = image_count;
	}
	public String getNews_count() {
		return news_count;
	}
	public void setNews_count(String news_count) {
		this.news_count = news_count;
	}
	@Override
	public String toString() {
		return "MediaCount [voice_count=" + voice_count + ", video_count=" + video_count + ", image_count="
				+ image_count + ", news_count=" + news_count + "]";
	}
	
	
}
