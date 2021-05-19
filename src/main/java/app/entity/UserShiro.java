package app.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import tpa.entity.*;
import lombok.Data;

@Data
public class UserShiro {

	private Integer id;// 用户在数据库中的主键
	private Integer userType;// 用户类型：1中心用户和培训教师，2空缺，3送培学校，4学员
	private Integer tyear;
	private String login_name;// 登录账号
	private boolean isSuperAdmin;
	private String alias;// 昵称
	private String digest;// 唯一串号
	private String realName;// 真实姓名
	private String qq;// QQ
	private String email;// 电子邮件
	private String mobile;// 手机
	private boolean isActived;// 用户是否已经被冻结
	private String wechatOpenid;// 微信id
	private String wechatNickname;// 微信头像
	private String wechatHeadimgurl;// 微信头像

	private String authorizedType;// 授权类型：User管理员/Customer顾客

	private List<Role> rolelist;
	private List<Permit> permitlist;// 当前所有许可列表

	private List<String> rolename;
	private List<String> permitname;// 当前所有许可列表

	public UserShiro() {

	}

	public UserShiro(User it, Boolean isSuperAdmin, List<Role> rolelist, List<Permit> permitlist) {
		this.id = it.getId();

		this.login_name = it.getLogin_name();
		this.isSuperAdmin = isSuperAdmin;
		this.realName = it.getName();
		this.qq = it.getQq();
		this.email = it.getEmail();
		this.isActived = it.getIs_actived();
		this.wechatOpenid = it.getWechat_openid();
		this.wechatNickname = it.getWechat_nickname();
		this.wechatHeadimgurl = it.getWechat_headimgurl();

		this.authorizedType = "User";// 管理员

		this.rolelist = rolelist;
		this.permitlist = permitlist;

		rolename = new ArrayList<String>();
		for (Role role : rolelist) {
			rolename.add(role.getName());
		}

		permitname = new ArrayList<String>();
		for (Permit permit : permitlist) {
			permitname.add(permit.getCode());
		}

	}

	@Override
	public String toString() {
		return "UserShiro [id=" + id + ", userType=" + userType + ", tyear=" + tyear + ", login_name=" + login_name
				+ ", isSuperAdmin=" + isSuperAdmin + ", alias=" + alias + ", digest=" + digest + ", realName="
				+ realName + ", qq=" + qq + ", email=" + email + ", mobile=" + mobile + ", isActived=" + isActived
				+ ", wechatOpenid=" + wechatOpenid + ", wechatNickname=" + wechatNickname + ", wechatHeadimgurl="
				+ wechatHeadimgurl + ", authorizedType=" + authorizedType + ", rolelist=" + rolelist + ", permitlist="
				+ permitlist + ", rolename=" + rolename + ", permitname=" + permitname + "]";
	}

}
