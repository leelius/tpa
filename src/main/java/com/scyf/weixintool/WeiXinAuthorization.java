package com.scyf.weixintool;

import com.scyf.bean.ErrcodeBean;
import com.scyf.bean.Oauth2AccessToken;
import com.scyf.bean.SnsUserInfo;
import com.scyf.httptool.HttpRequest;

import net.sf.json.JSONObject;

/**
 * 微信网页授权工具类
 * @author 昊琦
 * @version 1.0
 * @创建时间：2018年3月14日20:38:42
 */
public class WeiXinAuthorization {

	/**微信授权参数：静默授权**/
	public final static String  SNSAPI_BASE ="snsapi_base";
	/** 微信授权参数：需手动同意**/
	public final static String SNSAPI_USERINFO ="snsapi_userinfo";
	
	/**语言版本参数：简体中文**/
	public final static String ZH_CN ="zh_CN";
	/**语言版本参数：繁体中文**/
	public final static String ZH_TW ="zh_TW";
	/**语言版本参数：英文**/
	public final static String EN ="en";
	
	/**
	 * 构造用户同意授权，获取code的链接
	 * @API https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	 * @param appId 微信公众号appId
	 * @param redirectUri 重定向的链接
	 * @param scope WeiXinAuthorization.SNSAPI_BASE,WeiXinAuthorization.SNSAPI_USERINFO
	 * @return
	 */
	public static String getCodeUrl(String appId,String redirectUri,String scope ){
		
		 String code_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID"
				+ "&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		
		code_url = code_url.replace("APPID", appId);
		
		code_url = code_url.replace("REDIRECT_URI", redirectUri);
		
		code_url = code_url.replace("SCOPE", scope);
		
		/**要加一个state的随机数**/
		
		return code_url;
	}
	
	/**
	 * 通过code换取网页授权access_token 
	 * @API https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	 * @方法 GET
	 * @param appId 微信公众号appId
	 * @param appSecret 微信公众号开发者密码
	 * @param code
	 * @return
	 */
	public static Oauth2AccessToken getOauth2AccessToken(String appId,String appSecret,String code){
		
		String access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		
		//替换url中的参数值
		access_token_url = access_token_url.replace("APPID", appId);
		access_token_url = access_token_url.replace("SECRET", appSecret);
		access_token_url = access_token_url.replace("CODE", code);
		
		//发送http请求
		JSONObject jsonObject = HttpRequest.sendGet(access_token_url);
		
		if (jsonObject.containsKey("errcode")) {
			//如果错误则返回null
			return null ; 
		}else if (jsonObject.containsKey("access_token")) {
			//如果请求后接收到的数据符合要求
			Oauth2AccessToken oauth2AccessToken = (Oauth2AccessToken)JSONObject.toBean(jsonObject, Oauth2AccessToken.class);
			
			return oauth2AccessToken;
		}else {
			return null;
		}
			
	}
		
	
	/**
	 * 刷新access_token（如果需要）
	 * @API https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
	 * @方法 GET
	 * @param appId
	 * @param refreshToken
	 * @return
	 */
	public static Oauth2AccessToken refreshOauth2AccessToken(String appId,String refreshToken){
		
		String refresh_token_url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		
		//替换url中的参数值
		refresh_token_url = refresh_token_url.replace("APPID", appId);
		refresh_token_url = refresh_token_url.replace("REFRESH_TOKEN", refreshToken);
		//发送http请求
		JSONObject jsonObject = HttpRequest.sendGet(refresh_token_url);
		
		if (jsonObject.containsKey("errcode")) {
			//如果错误则返回null
			return null ; 
		}else if (jsonObject.containsKey("access_token")) {
			//如果请求后接收到的数据符合要求
			Oauth2AccessToken oauth2AccessToken = (Oauth2AccessToken)JSONObject.toBean(jsonObject, Oauth2AccessToken.class);
			
			return oauth2AccessToken;
		}else {
			return null;
		}
	}
	
	/**
	 * 调用网页授权后接口拉取用户信息(默认中文)
	 * @API https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	 * @方法 GET
	 * @param accessToken 	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * @param openId 用户的唯一标识
	 * @param lang  返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语, null 时默认为中文简体
	 * @return
	 */
	public static SnsUserInfo getSnsUserInfo(String accessToken,String openId,String lang){
		
		String snsapi_userinfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		
		snsapi_userinfo_url = snsapi_userinfo_url.replace("ACCESS_TOKEN", accessToken);
		snsapi_userinfo_url = snsapi_userinfo_url.replace("OPENID", openId);
		if (lang!=null) {
			snsapi_userinfo_url = snsapi_userinfo_url.replace("zh_CN", lang);
		}
		
		
		//发送http请求
		JSONObject jsonObject = HttpRequest.sendGet(snsapi_userinfo_url);
		
		if (jsonObject.containsKey("errcode")) {
			//如果错误则返回null
			return null ; 
		}else if (jsonObject.containsKey("nickname")) {
			//如果请求后接收到的数据符合要求
			SnsUserInfo snsUserInfo = (SnsUserInfo)JSONObject.toBean(jsonObject, SnsUserInfo.class);
			
			return snsUserInfo;
		}else {
			return null;
		}		
		
	} 
	
	
	/**
	 * 调用网页授权后接口拉取用户信息（中文）
	 * @API https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	 * @方法 GET
	 * @param accessToken 	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * @param openId 用户的唯一标识
	 * @return
	 */
	public static SnsUserInfo getSnsUserInfo(String accessToken,String openId){
		
		String snsapi_userinfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		
		snsapi_userinfo_url = snsapi_userinfo_url.replace("ACCESS_TOKEN", accessToken);
		snsapi_userinfo_url = snsapi_userinfo_url.replace("OPENID", openId);
		
		//发送http请求
		JSONObject jsonObject = HttpRequest.sendGet(snsapi_userinfo_url);
		
		if (jsonObject.containsKey("errcode")) {
			//如果错误则返回null
			return null ; 
		}else if (jsonObject.containsKey("nickname")) {
			//如果请求后接收到的数据符合要求
			SnsUserInfo snsUserInfo = (SnsUserInfo)JSONObject.toBean(jsonObject, SnsUserInfo.class);
			
			return snsUserInfo;
		}else {
			return null;
		}		
		
	}
	
	/**
	 * 检验授权凭证（access_token）是否有效
	 * @API https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
	 * @方法 GET
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static boolean checkOauth2AccessToken(String accessToken,String openId){
		
		String check_auth_url = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
		
		//替换url中的参数值
		check_auth_url = check_auth_url.replace("ACCESS_TOKEN", accessToken);
		check_auth_url = check_auth_url.replace("OPENID", openId);
		
		//http请求
		JSONObject jsonObject = HttpRequest.sendGet(check_auth_url);
		
		
		if (jsonObject.containsKey("errcode")) {
			//转换成bean
			ErrcodeBean errcodeBean = (ErrcodeBean)JSONObject.toBean(jsonObject, ErrcodeBean.class);
			
			if (errcodeBean==null) {
				return false ;
			}else if (errcodeBean.getErrcode()==0) {
				return true;
			}else {
				return false;
			}
		}else{
			return false;
		}
		
	}
	
}
