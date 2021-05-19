package app.wechat.pojo;

/***
 * 读取spring中的实体 <br/>
 * 包含微信的登录信息
 * 
 * @author Denny
 * 
 */
public class WeChat {

	private String appId;
	private String appSecret;
	private Boolean enable;

	@Override
	public String toString() {
		return "WeChat:" + ",  appId=" + appId + ",  appSecret=" + appSecret
				+ ",  enable=" + enable;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

}
