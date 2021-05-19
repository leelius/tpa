package com.scyf.bean;
/**
 * 不需要使用网页授权，调取接口获取到的用户信息
 * @author wangkan
 * @version 1.0
 * @创建时间 2018年3月22日16:20:32
 *
 */

import java.util.Arrays;
import java.util.List;

public class BasicUserInfo {
	/**
	 * 用户的唯一标识
	 */
	private String openid;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	private String sex;
	/**
	 * 用户个人资料填写的省份
	 */
	private String province;
	/**
	 * 普通用户个人资料填写的城市
	 */
	private String city;
	/**
	 * 国家，如中国为CN
	 */
	private String country;
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */
	private String headimgurl;
	/**
	 * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	 */
	private String[] privilege;
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	 */
	private String unionid;
	/**
	 * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 */
	private String remark;
	/**
	 * 用户所在的分组ID（兼容旧的用户分组接口）
	 */
	private String groupid;
	/**
	 * 用户被打上的标签ID列表
	 */
	private List tagid_list;
	/**
	 * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION
	 * 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE
	 * LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID
	 * 支付后关注，ADD_SCENE_OTHERS 其他
	 */
	private String subscribe_scene;
	/**
	 * 二维码扫码场景（开发者自定义）
	 */
	private String qr_scene;
	/**
	 * 二维码扫码场景描述（开发者自定义）
	 */
	private String qr_scene_str;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String[] getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String[] privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public List getTagid_list() {
		return tagid_list;
	}

	public void setTagid_list(List tagid_list) {
		this.tagid_list = tagid_list;
	}

	public String getSubscribe_scene() {
		return subscribe_scene;
	}

	public void setSubscribe_scene(String subscribe_scene) {
		this.subscribe_scene = subscribe_scene;
	}

	public String getQr_scene() {
		return qr_scene;
	}

	public void setQr_scene(String qr_scene) {
		this.qr_scene = qr_scene;
	}

	public String getQr_scene_str() {
		return qr_scene_str;
	}

	public void setQr_scene_str(String qr_scene_str) {
		this.qr_scene_str = qr_scene_str;
	}

	@Override
	public String toString() {
		return "BasicUserInfo [openid=" + openid + ", nickname=" + nickname + ", sex=" + sex + ", province=" + province
				+ ", city=" + city + ", country=" + country + ", headimgurl=" + headimgurl + ", privilege="
				+ Arrays.toString(privilege) + ", unionid=" + unionid + ", remark=" + remark + ", groupid=" + groupid
				+ ", tagid_list=" + tagid_list + ", subscribe_scene=" + subscribe_scene + ", qr_scene=" + qr_scene
				+ ", qr_scene_str=" + qr_scene_str + "]";
	}

}
