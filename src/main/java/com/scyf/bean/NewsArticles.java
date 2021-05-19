package com.scyf.bean;

/**
 * 添加图文消息素材 添加用
 * @author WangYunfei
 * @version  1
 * 创建时间：2018年3月22日 下午1:44:05
 */
public class NewsArticles {

	/**
	 * 标题
	 */
	private String title; // 标题
	/**
	 * 图文消息的封面图片素材id（必须是永久mediaID）
	 */
	private String thumb_media_id; // 图文消息的封面图片素材id（必须是永久mediaID）
	/**
	 * 作者
	 */
	private String author; // 作者
	/**
	 * 图文消息的摘要，
	 */
	private String digest; // 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空。如果本字段为没有填写，则默认抓取正文前64个字。
	/**
	 * 是否显示封面，0为false，即不显示，1为true，即显示
	 */
	private String show_cover_pic; // 是否显示封面，0为false，即不显示，1为true，即显示
	/**
	 * 图文消息的具体内容，
	 */
	private String content; // 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS,涉及图片url必须来源"上传图文消息内的图片获取URL"
	/**
	 * 图文消息的原文地址，即点击“阅读原文”后的URL
	 */
	private String content_source_url; // 图文消息的原文地址，即点击“阅读原文”后的URL

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getShow_cover_pic() {
		return show_cover_pic;
	}

	public void setShow_cover_pic(String show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent_source_url() {
		return content_source_url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

}
