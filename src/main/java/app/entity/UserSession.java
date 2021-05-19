package app.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import tpa.entity.*;
import lombok.Data;

@Data
public class UserSession {

	public static final String SUPERADMIN = "superadmin";
	public static final String ADMIN = "admin";
	public static final String TEACHER = "teacher";
	public static final String STUDENT = "student";

	private Integer id;// 用户在数据库中的主键
	private String login_name;// 登录账号
	private String alias;// 昵称
	private String real_name;// 真实姓名
	private String qq;// QQ
	private String email;// 电子邮件
	private String mobile;// 手机
	private boolean isActived;// 用户是否已经被冻结
	private String wechatOpenid;// 微信id
	private String wechatNickname;// 微信头像
	private String wechatHeadimgurl;// 微信头像
	private String authorizedType;// 授权类型：Admin管理员/Teacher教师/Student学生

	private String login_name_digest;// 登录账号摘要
	private String login_password_digest;// 登录密码摘要
	private String classcode;// 学生的实际班级号

	private List<Role> rolelist;
	private List<Permit> permitlist;// 当前所有许可列表

	public UserSession() {
	}

	/***
	 * 教师/管理员类型
	 * @param it
	 * @param rolelist
	 * @param permitlist
	 */
	public UserSession(User it, String authorizedType, List<Role> rolelist, List<Permit> permitlist) {
		this.id = it.getId();
		this.login_name = it.getLogin_name();//教师登录名
		this.alias = it.getName();//姓名
		this.real_name = it.getName();//姓名
		this.qq = it.getQq();//qq号
		this.email = it.getEmail();
		this.isActived = it.getIs_actived();
		this.wechatOpenid = it.getWechat_openid();
		this.wechatNickname = it.getWechat_nickname();
		this.wechatHeadimgurl = it.getWechat_headimgurl();
		this.authorizedType = authorizedType;// 授权类型，在实例化时确定
		this.rolelist = rolelist;
		this.permitlist = permitlist;
		this.login_name_digest = it.getLogin_name_digest();
		this.login_password_digest = it.getLogin_password_digest();
	}

	/**
	 * 学生类型
	 * @param it
	 */
	public UserSession(Student it) {
		this.id = it.getId();//
		this.login_name = it.getLogin_name();
		this.alias = it.getSno();
		this.real_name = it.getName();
		this.qq = it.getQq();
		this.email = it.getEmail();
		this.isActived = it.getIs_actived();
		this.wechatOpenid = it.getWechat_openid();
		this.wechatNickname = it.getWechat_nickname();
		this.wechatHeadimgurl = it.getWechat_headimgurl();
		this.authorizedType = STUDENT;// 学生
		this.classcode = it.getClasscode();
		this.login_name_digest = it.getLogin_name_digest();
		this.login_password_digest = it.getLogin_password_digest();
	}

	@Override
	public String toString() {
		return "UserSession [id=" + id + ", login_name=" + login_name + ", alias=" + alias + ", real_name=" + real_name
				+ ", qq=" + qq + ", email=" + email + ", mobile=" + mobile + ", isActived=" + isActived
				+ ", wechatOpenid=" + wechatOpenid + ", wechatNickname=" + wechatNickname + ", wechatHeadimgurl="
				+ wechatHeadimgurl + ", authorizedType=" + authorizedType + ", login_name_digest=" + login_name_digest
				+ ", login_password_digest=" + login_password_digest + ", classcode=" + classcode + ", rolelist="
				+ rolelist + ", permitlist=" + permitlist + "]";
	}

}
