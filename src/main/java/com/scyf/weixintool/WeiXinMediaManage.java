package com.scyf.weixintool;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scyf.bean.ErrcodeBean;
import com.scyf.bean.MediaCount;
import com.scyf.bean.MediaList;
import com.scyf.bean.News;
import com.scyf.bean.NewsArticles;
import com.scyf.bean.UploadMaterial;
import com.scyf.bean.UploadTempMaterial;
import com.scyf.bean.VideoMaterial;
import com.scyf.httptool.HttpRequest;

import net.sf.json.JSONObject;

/** 
 * 微信素材管理工具
 * @author WangYunfei
 * @version 5
 * 创建时间：2018年3月16日 上午9:36:11 
 */
public class WeiXinMediaManage {

	/**
	 * 包括以下功能：
	 * (1) 临时素材管理 ① 获取、上传 
	 * (2) 永久素材管理 ① 获取、上传、删除
	 * 
	 */

	// 临时素材
	/**
	 * 临时素材
	 */
	private static String mediaUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	// 永久素材
	/**
	 * 永久素材
	 */
	private static String baseUrl = "https://api.weixin.qq.com/cgi-bin/material/OPEATER?access_token=ACCESS_TOKEN";

	/**
	 * 获取临时素材 （请先获取素材后缀名getMediaType）
	 * 请求方式: GET,https调用
	 * API https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
	 * @param access_token
	 * @param media_id
	 * @return InputStream
	 */
	public static InputStream getMedia(String access_token, String media_id) {
		String requestUrl = mediaUrl.replace("ACCESS_TOKEN", access_token).replace("MEDIA_ID", media_id);
//		String ContentType = HttpRequest.getContentType(requestUrl);
		InputStream jsonObject = HttpRequest.getFileSendGet(requestUrl);
		return jsonObject;
	}
	/**
	 * 获取临时素材 ContentType 类型
	 * @param access_token
	 * @param media_id
	 * @return ContentType
	 */
	public static String getMediaType(String access_token, String media_id) {
		String requestUrl = mediaUrl.replace("ACCESS_TOKEN", access_token).replace("MEDIA_ID", media_id);
		String ContentType = HttpRequest.getContentType(requestUrl);
		if (ContentType.split("/").length>1) {
			if (ContentType.split("/")[1].length()>5) {
				return null;
			}
			return ContentType.split("/")[1];
		}
		return null;
	}
	
	/**
	 * 添加临时素材
	 * 请求方式：POST/FORM，使用https
	 * API https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
	 * @param access_token
	 * @param file 要上传的文件类型大小与微信公众平台要求相同
	 * @param fileType image/voice/video/thumb，主要用于视频与音乐格式的缩略图
	 * @return JSONObject
	 */
	public static UploadTempMaterial uploadMedia(String access_token, File file ,String fileType) {

		String requestUrl = mediaUrl.replace("get", "upload").replace("ACCESS_TOKEN", access_token)+"&type="+fileType;
		
		Map<String, Object> param = new HashMap<>();
		param.put("fileType", fileType);
		param.put("fileName",file.getName());
		param.put("filelength", file.length());
		/**
		 * 根据文件名设置content-type
		 * 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
		 * 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
		 * 视频（video）：10MB，支持MP4格式
		 * 缩略图（thumb）：64KB，支持JPG格式
		 */
		if (file.getName().endsWith("JPEG")||file.getName().endsWith("JPG")||file.getName().endsWith("jpg")||file.getName().endsWith("jpeg")) {
			param.put("contentType", "image/jpeg");
		}else if (file.getName().endsWith("PNG")||file.getName().endsWith("png")) {
			param.put("contentType", "image/png");
		}else if(file.getName().endsWith("GIF")||file.getName().endsWith("gif")) {
			param.put("contentType", "image/gif");
		}else if(file.getName().endsWith("AMR")||file.getName().endsWith("amr")) {
			param.put("contentType", "application/octet-stream");
		}else if(file.getName().endsWith("MP3")||file.getName().endsWith("mp3")) {
			param.put("contentType", "audio/mp3");
		}else if(file.getName().endsWith("MP4")||file.getName().endsWith("mp4")) {
			param.put("contentType", "video/mpeg4");
		}else {
			return null;
		}
		
		//param.put("contentType", "image/jpeg");
		
		JSONObject jsonObject = HttpRequest.sendPostFile(requestUrl, file, param);
//		JSONObject jsonObject = HttpRequest.sendPost(requestUrl, json);
		//return {"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
		if(jsonObject.containsKey("errcode")) {
			System.out.println(" ErrorInfo -> : /n "+ jsonObject );
			return null;
		}else {
			UploadTempMaterial uploadTempMaterial = (UploadTempMaterial)JSONObject.toBean(jsonObject, UploadTempMaterial.class);
			return uploadTempMaterial;
		}
		
	}

	/**
	 * 获取永久素材 
	 * http请求方式: POST,https协议
	 * https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN
	 * {"media_id":MEDIA_ID}
	 * 
	 * @param access_token
	 * @param media_id
	 * @param mediaType 文件类型 视频1 / 图文素材 2 /其他 3
 	 * @return Map 1:videoMaterial 2:News 3 inputStream
	 */
	public static Map<String, Object> getFMedia(String access_token, String media_id ,int mediaType) {
		Map<String, Object> map = new HashMap<>();
		String url = baseUrl.replace("OPEATER", "get_material").replace("ACCESS_TOKEN", access_token);
		String json = "{\"media_id\":\""+media_id+"\"}";
		if (mediaType == 2) {
			
			JSONObject jsonObject = HttpRequest.sendPost(url, json);
			
			if(jsonObject.containsKey("errcode")) {
				return null;
			}
			
			News getNews = (News)JSONObject.toBean(jsonObject, News.class);
			
			map.put("News", getNews);
		}else if(mediaType == 1) {
			JSONObject jsonObject = HttpRequest.sendPost(url, json);
			if(jsonObject.containsKey("errcode")) {
				return null;
			}
			VideoMaterial videoMaterial = (VideoMaterial)JSONObject.toBean(jsonObject, VideoMaterial.class);
			map.put("videoMaterial", videoMaterial);
		}
		else {
			InputStream inputStream = HttpRequest.getFileSendPost(url, json);
			if(inputStream==null) {
				return null;
			}
			map.put("inputStream", inputStream);
		}
		
		return map;
	}

	/**
	 * 新增图文消息素材
	 * http请求方式: POST，https协议
	 * https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN
	 * @param access_token
	 * @param List包含图文素材NewsArticles
	 * @return JSONObject
	 */
	public static UploadMaterial uploadNewsMedia(String access_token, List<NewsArticles> list) {
		
		String url = baseUrl.replace("OPEATER", "add_news").replace("ACCESS_TOKEN", access_token);

		String jsonurl = "{\r\n" + 
				"\"articles\": [";
		
		if (list!=null) {
			for(NewsArticles nn : list) {
				jsonurl = jsonurl + "{\r\n" + 
						"\"title\": \""+nn.getTitle()+"\",\r\n" + 
						"\"thumb_media_id\": \""+nn.getThumb_media_id()+"\",\r\n" + 
						"\"author\": \""+nn.getAuthor()+"\",\r\n" + 
						"\"digest\": \""+nn.getDigest()+"\",\r\n" + 
						"\"show_cover_pic\": \""+nn.getShow_cover_pic()+"\",\r\n" + 
						"\"content\": \""+nn.getContent()+"\",\r\n" + 
						"\"content_source_url\": \""+nn.getContent_source_url()+"\" \r\n" + 
						"},";
			}
		}else {
			return null;
		}
		
		jsonurl = jsonurl + "]\r\n" + 
				"}";
		JSONObject jsonObject = HttpRequest.sendPost(url, jsonurl);
		//return {"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
		if(jsonObject.containsKey("errcode")) {
			System.out.println(" ErrorInfo -> : /n "+ jsonObject );
			return null;
		}else {
			UploadMaterial uploadTempMaterial = (UploadMaterial)JSONObject.toBean(jsonObject, UploadMaterial.class);
			return uploadTempMaterial;
		}

	}
	
	/**
	 * 上传图文消息内的图片
	 * 获取URL本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。
	 * 图片仅支持jpg/png格式大小必须在1MB以下。
	 * 请求方式: POST，https协议
	 * API https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN
	 * @param access_token
	 * @param file
	 * @return DownloadUrl
	 */
	public static String uploadImg(String access_token , File file ,String filetype) {
		String basurl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
		String url = basurl.replace("ACCESS_TOKEN", access_token);
		Map<String, Object> param = new HashMap<>();
		param.put("fileType", "image");
		param.put("fileName",file.getName());
		param.put("filelength", file.length());
//		param.put("contentType", "image/jpeg");
		if (file.getName().endsWith("JPEG")||file.getName().endsWith("JPG")||file.getName().endsWith("jpg")||file.getName().endsWith("jpeg")) {
			param.put("contentType", "image/jpeg");
		}else if (file.getName().endsWith("PNG")||file.getName().endsWith("png")) {
			param.put("contentType", "image/png");
		}
		JSONObject jsonObject = HttpRequest.sendPostFile(url, file, param);
		
		if(jsonObject.containsKey("errcode")) {
			System.out.println(" ErrorInfo -> : /n "+ jsonObject );
			return null;
		}else {
			//System.out.println(jsonObject.get("url").toString());
		}
		return jsonObject.get("url").toString();

	}
	/**
	 * 新增其他永久素材
	 * 通过POST表单来调用接口
	 * http请求方式: POST，需使用https
	 * Api https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE
	 * 表单id为media，包含需要上传的素材内容，有filename、filelength、content-type等信息。请注意：图片素材将进入公众平台官网素材管理模块中的默认分组。
	 * @param access_token
	 * @param fileType
	 * @param Map
	 * @return JSONObject
	 */
	public static UploadMaterial uploadMediaOther(String access_token, File file, String fileType, Map<String, Object> param) {
	
		/**
		 * 根据文件名设置content-type
		 * 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
		 * 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
		 * 视频（video）：10MB，支持MP4格式
		 * 缩略图（thumb）：64KB，支持JPG格式
		 */
		if (param==null) {
			param = new HashMap<String,Object>();
		}
		param.put("fileType", fileType);
		param.put("fileName",file.getName());
		param.put("filelength", file.length());
		
		if (file.getName().endsWith("JPEG")||file.getName().endsWith("JPG")||file.getName().endsWith("jpg")||file.getName().endsWith("jpeg")) {
			param.put("contentType", "image/jpeg");
		}else if (file.getName().endsWith("PNG")||file.getName().endsWith("png")) {
			param.put("contentType", "image/png");
		}else if(file.getName().endsWith("GIF")||file.getName().endsWith("gif")) {
			param.put("contentType", "image/gif");
		}else if(file.getName().endsWith("AMR")||file.getName().endsWith("amr")) {
			param.put("contentType", "application/octet-stream");
		}else if(file.getName().endsWith("MP3")||file.getName().endsWith("mp3")) {
			param.put("contentType", "audio/mp3");
		}else if(file.getName().endsWith("MP4")||file.getName().endsWith("mp4")) {
			param.put("contentType", "video/mpeg4");
		}else {
			return null;
		}
		
		String url = baseUrl.replace("OPEATER", "add_material").replace("ACCESS_TOKEN", access_token)+"&type="+fileType;
		JSONObject jsonObject = HttpRequest.sendPostFile(url, file, param);
		if(jsonObject.containsKey("errcode")) {
			System.out.println(" ErrorInfo -> : /n "+ jsonObject );
			return null;
		}else {
			UploadMaterial uploadTempMaterial = (UploadMaterial)JSONObject.toBean(jsonObject, UploadMaterial.class);
			return uploadTempMaterial;
		}
	}

	/**
	 * 删除永久素材 
	 * https请求方式: POST
	 * API: https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN
	 * @param access_token
	 * @param media_id
	 * @return ErrcodeBean
	 */
	public static ErrcodeBean rmFMedia(String access_token, String media_id) {
		
		String url = baseUrl.replace("OPEATER", "del_material").replace("ACCESS_TOKEN", access_token);
		String json = "{\"media_id\":\""+media_id+"\"}";
		JSONObject jsonObject = HttpRequest.sendPost(url, json);

		return (ErrcodeBean)JSONObject.toBean(jsonObject, ErrcodeBean.class);

	}
	
	/**
	 * 修改永久图文素材
	 * https请求方式: POST
	 * API https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=ACCESS_TOKEN
	 * @param access_token
	 * @param media_id
	 * @param index 要更新的文章在图文消息中的位置从0开始
	 * @param NewsArticles
	 * @return ErrcodeBean
	 */
	public static ErrcodeBean updateNewsMedia(String access_token,String media_id,String index,NewsArticles nn) {
		String strUrl = baseUrl.replace("OPEATER", "update_news").replace("ACCESS_TOKEN", access_token);
	
		String jsonurl = "{\r\n" + 
				"  \"media_id\": \""+media_id+"\",\r\n" + 
				"  \"index\": \""+index+"\",\r\n" + 
				"  \"articles\":";
		
		if (nn!=null) {
			
				jsonurl = jsonurl + "{\r\n" + 
						"\"title\": \""+nn.getTitle()+"\",\r\n" + 
						"\"thumb_media_id\": \""+nn.getThumb_media_id()+"\",\r\n" + 
						"\"author\": \""+nn.getAuthor()+"\",\r\n" + 
						"\"digest\": \""+nn.getDigest()+"\",\r\n" + 
						"\"show_cover_pic\": \""+nn.getShow_cover_pic()+"\",\r\n" + 
						"\"content\": \""+nn.getContent()+"\",\r\n" + 
						"\"content_source_url\": \""+nn.getContent_source_url()+"\" \r\n" + 
						"}";
			
		}else {
			return null;
		}
		
		jsonurl = jsonurl + "}";
		
		//System.out.println("request url  "+jsonurl);
		JSONObject jsonObject = HttpRequest.sendPost(strUrl, jsonurl);
		//{"errcode":0,"errmsg":"ok"}
		return (ErrcodeBean)JSONObject.toBean(jsonObject, ErrcodeBean.class);
	}

	/**
	 * 获取永久素材列表
	 * 1、获取永久素材的列表，也包含公众号在公众平台官网素材管理模块中新建的图文消息、语音、视频等素材
	 * 2、临时素材无法通过本接口获取 
	 * 3、调用该接口需https协议 http请求方式: POST
	 * https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token= ACCESS_TOKEN
	 * { "type":TYPE, "offset":OFFSET, "count":COUNT }
	 * @param access_token
	 * @param type 素材类型
	 * @param offset 
	 * @param count 
	 * @return MediaList
	 */
	public static MediaList getMediaList(String access_token, String type, String offset, String count) {

		String requestUrl = baseUrl.replace("OPEATER", "batchget_material").replace("ACCESS_TOKEN", access_token);
		
		String jsonObj = "{ \"type\":\""+type+"\", \"offset\":\""+offset+"\", \"count\":\""+count+"\" }";
		JSONObject res = HttpRequest.sendPost(requestUrl, jsonObj);
		if (res.containsKey("errcode")) {
			System.err.println(" error: "+res);
			return null;
		} else {
			MediaList mediaCount = (MediaList)JSONObject.toBean(res,MediaList.class);
			return mediaCount;
		}
	}

	/**
	 * 获取永久素材的总数 
	 * 1永久素材的总数，也会计算公众平台官网素材管理中的素材
	 * 2.图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000 
	 * 3.调用该接口需https协议 获取素材总数 
	 * 请求方式  get
	 * https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN
	 * 
	 * @param access_token
	 * @return MediaCount
	 */
	public static MediaCount getMediaCount(String access_token) {
		String requestUrl = baseUrl.replace("OPEATER", "get_materialcount").replace("ACCESS_TOKEN", access_token);
		JSONObject res = HttpRequest.sendGet(requestUrl);
		if (res.containsKey("errcode")) {
			System.err.println(" error: "+res);
			return null;
		} else {
			MediaCount mediaCount = (MediaCount)JSONObject.toBean(res,MediaCount.class);
			return mediaCount;
		}
	}
}
