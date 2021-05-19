package com.scyf.weixintool;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.scyf.bean.ImageMessageBean;
import com.scyf.bean.LinkMessageBean;
import com.scyf.bean.LocationMessageBean;
import com.scyf.bean.TextMessageBean;
import com.scyf.bean.VideoMessageBean;
import com.scyf.bean.VoiceMessageBean;

/**
 * 解析用户直接发送给微信公众号的消息的xml。处理公众号返回给用户的包含消息的xml
 * @author 昊琦
 * @version 1.0
 * @创建时间 2018年3月22日14:19:54
 */
public class WeiXinMessageResolver {
	
	/**文本的消息**/
	public final static String TEXT = "text";
	/**图片消息**/
	public final static String IMAGE = "image";
	/**语音消息**/
	public final static String VOICE = "voice";
	/**视频消息**/
	public final static String VIDEO = "video";
	/**短视频消息**/
	public final static String SHORTVIDEO = "shortvideo";
	/**地理位置消息**/
	public final static String LOCATION = "location";
	/**链接消息**/
	public final static String LINK = "link";
	
	
	/**
	 * 根据微信发送的xml数据获取用户发送的消息类型
	 * @param xml 微信封装的包含用户信息的xml字符串
	 * @return
	 */
	public static String getMsgType(String xml){
		

		int beginIndex = xml.indexOf("<MsgType><![CDATA[");
		beginIndex += 18;
		int endIndex = xml.indexOf("]]></MsgType>");
		
		return xml.substring(beginIndex, endIndex);
		
	}
	
	
	/**
	 * 解析微信发到服务器的包含了用户发送到公众号文本消息的xml
	 * @param xml
	 * @return
	 */
	public static TextMessageBean resolveTextMessage(String xml){
		
		TextMessageBean textMessageBean = new TextMessageBean() ;
		

		//解析xml
		Map<String, Object> result = resolveXml(stringToBuffered(xml));
		
		//给对应bean赋值
		if (result.containsKey("ToUserName")) {
			textMessageBean.setToUserName(result.get("ToUserName").toString());
		}
		if (result.containsKey("FromUserName")) {
			textMessageBean.setFromUserName(result.get("FromUserName").toString());
		}
		if (result.containsKey("CreateTime")) {
			textMessageBean.setCreateTime(result.get("CreateTime").toString());
		}
		if (result.containsKey("MsgType")) {
			textMessageBean.setMsgType(result.get("MsgType").toString());
		}
		if (result.containsKey("Content")) {
			textMessageBean.setContent(result.get("Content").toString());
		}
		if (result.containsKey("MsgId")) {
			textMessageBean.setMsgId(result.get("MsgId").toString());
		}
		
		
		return textMessageBean;
	}
	
	
	/**
	 * 解析用户直接发送给微信公众号的图片信息
	 * @param xml
	 * @return
	 */
	public static ImageMessageBean resolveImageMessage(String xml){
		
		
		ImageMessageBean imageMessageBean = new ImageMessageBean();
		
		//解析xml
		Map<String, Object> map = resolveXml(stringToBuffered(xml));
		
		//给对应bean赋值
		if (map.containsKey("ToUserName")) {
			imageMessageBean.setToUserName(map.get("ToUserName").toString());
		}
		if (map.containsKey("FromUserName")) {
			imageMessageBean.setFromUserName(map.get("FromUserName").toString());
		}
		if (map.containsKey("CreateTime")) {
			imageMessageBean.setCreateTime(map.get("CreateTime").toString());
		}
		if (map.containsKey("MsgType")) {
			imageMessageBean.setMsgType(map.get("MsgType").toString());
		}
		if (map.containsKey("PicUrl")) {
			imageMessageBean.setPicUrl(map.get("PicUrl").toString());
		}
		if (map.containsKey("MediaId")) {
			imageMessageBean.setMediaId(map.get("MediaId").toString());
		}
		if (map.containsKey("MsgId")) {
			imageMessageBean.setMsgId(map.get("MsgId").toString());
		}
		
		return imageMessageBean;
	}
	
	/**
	 * 解析用户直接发送给微信公众号的语音消息
	 * @param xml
	 * @return
	 */
	public static VoiceMessageBean resolveVoiceMessage(String xml){
		
		VoiceMessageBean voiceMessageBean = new VoiceMessageBean();
		
		//解析xml
		Map<String, Object> map = resolveXml(stringToBuffered(xml));
		
		// 给对应bean赋值
		if (map.containsKey("ToUserName")) {
			voiceMessageBean.setToUserName(map.get("ToUserName").toString());
		}
		if (map.containsKey("FromUserName")) {
			voiceMessageBean.setFromUserName(map.get("FromUserName").toString());
		}
		if (map.containsKey("CreateTime")) {
			voiceMessageBean.setCreateTime(map.get("CreateTime").toString());
		}
		if (map.containsKey("MsgType")) {
			voiceMessageBean.setMsgType(map.get("MsgType").toString());
		}
		if (map.containsKey("MediaId")) {
			voiceMessageBean.setMediaId(map.get("MediaId").toString());
		}
		if (map.containsKey("Format")) {
			voiceMessageBean.setFormat(map.get("Format").toString());
		}
		if (map.containsKey("MsgId")) {
			voiceMessageBean.setMsgId(map.get("MsgId").toString());
		}
		
		
		return voiceMessageBean;
	}
	
	
	/**
	 * 解析用户直接发送给微信公众号的视频消息或者微信小视频消息
	 * @param xml
	 * @return
	 */
	public static VideoMessageBean resolveVideoMessage(String xml){
		
		VideoMessageBean videoMessageBean = new VideoMessageBean();
		
		//解析xml
		Map<String, Object> map = resolveXml(stringToBuffered(xml));
		
		//给对应bean赋值
		
		if (map.containsKey("ToUserName")) {
			videoMessageBean.setToUserName(map.get("ToUserName").toString());
		}
		if (map.containsKey("FromUserName")) {
			videoMessageBean.setFromUserName(map.get("FromUserName").toString());
		}
		if (map.containsKey("CreateTime")) {
			videoMessageBean.setCreateTime(map.get("CreateTime").toString());
		}
		if (map.containsKey("MsgType")) {
			videoMessageBean.setMsgType(map.get("MsgType").toString());
		}
		if (map.containsKey("MediaId")) {
			videoMessageBean.setMediaId(map.get("MediaId").toString());
		}
		if (map.containsKey("ThumbMediaId")) {
			videoMessageBean.setThumbMediaId(map.get("ThumbMediaId").toString());
		}
		if (map.containsKey("MsgId")) {
			videoMessageBean.setMsgId(map.get("MsgId").toString());
		}
		
		
		return videoMessageBean;
	}
	
	
	
	/**
	 * 解析用户直接发给微信公众号的地理位置信息的xml
	 * @param xml
	 * @return
	 */
	public static LocationMessageBean resolveLocationMessage(String xml){
		
		LocationMessageBean locationMessageBean = new LocationMessageBean();
		//解析xml
		Map<String, Object> map = resolveXml(stringToBuffered(xml));
		//给对应的bean赋值
		if (map.containsKey("ToUserName")) {
			locationMessageBean.setToUserName(map.get("ToUserName").toString());
		}
		if (map.containsKey("FromUserName")) {
			locationMessageBean.setFromUserName(map.get("FromUserName").toString());
		}
		if (map.containsKey("CreateTime")) {
			locationMessageBean.setCreateTime(map.get("CreateTime").toString());
		}
		if (map.containsKey("MsgType")) {
			locationMessageBean.setMsgType(map.get("MsgType").toString());
		}
		if (map.containsKey("Location_X")) {
			locationMessageBean.setLocation_X(map.get("Location_X").toString());
		}
		if (map.containsKey("Location_Y")) {
			locationMessageBean.setLocation_Y(map.get("Location_Y").toString());
		}
		if (map.containsKey("Scale")) {
			locationMessageBean.setScale(map.get("Scale").toString());
		}
		if (map.containsKey("Label")) {
			locationMessageBean.setLabel(map.get("Label").toString());
		}
		if (map.containsKey("MsgId")) {
			locationMessageBean.setMsgId(map.get("MsgId").toString());
		}
		
		return locationMessageBean;
	}
	
	/**
	 * 解析用户直接发送给微信公众号的链接消息
	 * @param xml
	 * @return
	 */
	public static LinkMessageBean resolveLinkMessage(String xml){
		
		LinkMessageBean linkMessageBean = new LinkMessageBean();
		
		//解析xml
		Map<String, Object> map = resolveXml(stringToBuffered(xml));
		
		//给对应bean赋值
		if (map.containsKey("ToUserName")) {
			linkMessageBean.setToUserName(map.get("ToUserName").toString());
		}
		if (map.containsKey("FromUserName")) {
			linkMessageBean.setFromUserName(map.get("FromUserName").toString());
		}
		if (map.containsKey("CreateTime")) {
			linkMessageBean.setCreateTime(map.get("CreateTime").toString());
		}
		if (map.containsKey("MsgType")) {
			linkMessageBean.setMsgType(map.get("MsgType").toString());
		}
		if (map.containsKey("Title")) {
			linkMessageBean.setTitle(map.get("Title").toString());
		}
		if (map.containsKey("Description")) {
			linkMessageBean.setDescription(map.get("Description").toString());
		}
		if (map.containsKey("Url")) {
			linkMessageBean.setUrl(map.get("Url").toString());
		}
		if (map.containsKey("MsgId")) {
			linkMessageBean.setMsgId(map.get("MsgId").toString());
		}
		
		return linkMessageBean;
		
	}
	
	
	/**
	 * 解析用户直接发送给微信公众号的消息的xml
	 * @param xml
	 * @return
	 */
	private static Map<String, Object> resolveXml(BufferedReader xml){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {

			//获取xml文档
			SAXReader reader = new SAXReader();
			Document document = reader.read(xml);
			//获取根节点
			Element rootElement = document.getRootElement();
			//迭代器遍历
			Iterator<Element> iterator = rootElement.elementIterator();
			
			while(iterator.hasNext()){

				//获取二级节点，并去除其中的值
				Element element = iterator.next();
				
				
				
				map.put(element.getName(), element.getStringValue());
				//System.out.println(element.getName()+"|"+element.getStringValue());
			}
			
			xml.close();
			
		} catch (Exception e) {
			try {
				xml.close();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		
		
		return map;
		
	}
	
	
	/**
	 * string 字符串转成输入流
	 * @param str
	 * @return
	 */
	private  static BufferedReader stringToBuffered(String str){
		
		try {
			return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes()), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	/******************构建微信公众号返回给用户的消息*********************/
	
	/**
	 * 构造微信回复用户消息的文本消息xml
	 * @param toUser 接收方帐号（收到的OpenID）
	 * @param fromUser 开发者微信号
	 * @param content 回复的消息内容
	 * @return
	 */
	public static String makeTextResponseXmlString(String toUser,String fromUser,String content ){
		
		String message ="<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>time</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[msgContent]]></Content></xml>";
		
		message = message.replace("toUser", toUser);
		message = message.replace("fromUser", fromUser);
		message = message.replace("time", String.valueOf(System.currentTimeMillis()));
		message = message.replace("msgContent", content);
		
		return message;
	}
	
	
	/**
	 * 构造微信回复用户消息的图片消息xml
	 * @param toUser 接收方帐号
	 * @param fromUser 开发者微信号
	 * @param mediaId 通过素材管理中的接口上传多媒体文件id
	 * @return
	 */
	public static String makeImageResponseXmlString(String toUser,String fromUser,String mediaId){
		
		String message="<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>time</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[media_id]]></MediaId></Image></xml>";
		message= message.replace("toUser", toUser);
		message = message.replace("fromUser", fromUser);
		message = message.replace("time", String.valueOf(System.currentTimeMillis()));
		message = message.replace("media_id", mediaId);
		
		return message;
	}
	
	/**
	 * 构造微信回复用户消息的语音消息xml
	 * @param toUser 接收方帐号
	 * @param fromUser 开发者微信号
	 * @param mediaId 通过素材管理中的接口上传多媒体文件id
	 * @return
	 */
	public static String makeVoiceResponseXmlString(String toUser,String fromUser,String mediaId){
		String message="<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>time</CreateTime><MsgType><![CDATA[voice]]></MsgType><Voice><MediaId><![CDATA[media_id]]></MediaId></Voice></xml>";
		message= message.replace("toUser", toUser);
		message = message.replace("fromUser", fromUser);
		message = message.replace("time", String.valueOf(System.currentTimeMillis()));
		message = message.replace("media_id", mediaId);
		
		return message;
		
	}
	
	/**
	 * 构造微信回复用户消息的视频消息xml
	 * @param toUser 接收方帐号（收到的OpenID）
	 * @param fromUser 开发者微信号
	 * @param mediaId  通过素材管理中的接口上传多媒体文件，得到的id
	 * @param title 视频消息的标题
	 * @param description 视频消息的描述
	 * @return
	 */
	public static String makeVideoResponseXmlString(String toUser,String fromUser,String mediaId,String title,String description){
		
		String message ="<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>time</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[media_id]]></MediaId><Title><![CDATA[title]]></Title><Description><![CDATA[description]]></Description></Video></xml>";
		
		message = message.replace("toUser", toUser);
		message = message.replace("fromUser", fromUser);
		message = message.replace("time", String.valueOf(System.currentTimeMillis()));
		message = message.replace("media_id", mediaId);
		message = message.replace("title", title);
		message = message.replace("description", description);
		
		return message;
	}
	
	/**
	 * 构造微信回复用户消息的音乐消息xml
	 * @param toUser 	接收方帐号（收到的OpenID）
	 * @param fromUser 	开发者微信号
	 * @param title 	音乐标题
	 * @param description 音乐描述
	 * @param musicURL 音乐链接
	 * @param HQmusicUrl 	高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 * @param thumbMediaId 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
	 * @return
	 */
	public static String makeMusicResponseXmlString(String toUser,String fromUser,String title,String description,String musicURL,String HQmusicUrl,String thumbMediaId){
		
		String message ="<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>time</CreateTime><MsgType><![CDATA[music]]></MsgType><Music><Title><![CDATA[title]]></Title><Description><![CDATA[description]]></Description><MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl><HQMusicUrl><![CDATA[HQMUSICUrl]]></HQMusicUrl><ThumbMediaId><![CDATA[media_id]]></ThumbMediaId></Music></xml>";
		
		message = message.replace("toUser", toUser);
		message = message.replace("fromUser", fromUser);
		message = message.replace("time", String.valueOf(System.currentTimeMillis()));
		message = message.replace("title", title);
		message = message.replace("description", description);
		message = message.replace("MUSIC_Url", musicURL);
		message = message.replace("HQMUSICUrl", HQmusicUrl);
		message = message.replace("media_id", thumbMediaId);
		
		return message;
	}
	
	
}
