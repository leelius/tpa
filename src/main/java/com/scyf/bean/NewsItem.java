package com.scyf.bean;

/**
 * News_item
 * @author WangYunfei
 * @version  1
 * 创建时间：2018年3月22日 下午1:44:05
 */
public class NewsItem {

	/**
	 *  标题
	 */
	private String title; // 标题
	/**
	 * 图文消息的封面图片素材id（必须是永久mediaID）
	 */
	private String thumb_media_id; // 图文消息的封面图片素材id（必须是永久mediaID）
	/**
	 *  作者
	 */
	private String author; // 作者
	/**
	 *  图文消息的摘要 单图文消息才有摘要，多图文此处为空 默认抓取正文前64个字
	 */
	private String digest; // 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空。如果本字段为没有填写，则默认抓取正文前64个字。
	/**
	 * 是否显示封面，0为false，即不显示，1为true，即显示
	 */
	private String show_cover_pic; // 是否显示封面，0为false，即不显示，1为true，即显示
	/**
	 * 图文消息的具体内容 url必须来源 "上传图文消息内的图片获取URL"接口获取。
	 */
	private String content; 
	/**
	 * 	图文消息的原文地址，即点击“阅读原文”后的URL			
	 */
	private String content_source_url; // 图文消息的原文地址，即点击“阅读原文”后的URL
	/**
	 * 文章链接
	 */
	private String url;	
	/**
	 * 缩略图url
	 */
	private String thumb_url;
	/**
	 * 需要打开评论
	 */
	private String need_open_comment;
	/**
	 * 只有fans可评论
	 */
	private String only_fans_can_comment;

	
	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public String getNeed_open_comment() {
		return need_open_comment;
	}

	public void setNeed_open_comment(String need_open_comment) {
		this.need_open_comment = need_open_comment;
	}

	public String getOnly_fans_can_comment() {
		return only_fans_can_comment;
	}

	public void setOnly_fans_can_comment(String only_fans_can_comment) {
		this.only_fans_can_comment = only_fans_can_comment;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	@Override
	public String toString() {
		return "NewsArticles2 [title=" + title + ", thumb_media_id=" + thumb_media_id + ", author=" + author
				+ ", digest=" + digest + ", show_cover_pic=" + show_cover_pic + ", content=" + content
				+ ", content_source_url=" + content_source_url + ", url=" + url + ", thumb_url=" + thumb_url
				+ ", need_open_comment=" + need_open_comment + ", only_fans_can_comment=" + only_fans_can_comment + "]";
	}
	
}
