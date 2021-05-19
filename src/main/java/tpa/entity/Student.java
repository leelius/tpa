//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- entity: entity
//-- Date Generated: 2020-10-09 10:48:00
//-----------------------------

package tpa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import app.entity.SuperEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.util.*;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@TableName("`student`")
public class Student extends SuperEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Integer id;//流水号
    @TableField(value = "`sno`")
    private String sno;//学号
    @TableField(value = "`login_name`")
    private String login_name;//登录账号
    @TableField(value = "`login_name_digest`")
    private String login_name_digest;//登录名摘要
    @TableField(value = "`login_password`")
    private String login_password;//密码
    @TableField(value = "`login_password_digest`")
    private String login_password_digest;//密码摘要
    @TableField(value = "`name`")
    private String name;//用户名称
    @TableField(value = "`email`")
    private String email;//电子邮件
    @TableField(value = "`is_actived`")
    private Boolean is_actived;//冻结账号
    @TableField(value = "`mobile`")
    private String mobile;//手机号码
    @TableField(value = "`qq`")
    private String qq;//QQ号码
    @TableField(value = "`wechat_openid`")
    private String wechat_openid;//微信openid
    @TableField(value = "`wechat_nickname`")
    private String wechat_nickname;//微信名称
    @TableField(value = "`wechat_headimgurl`")
    private String wechat_headimgurl;//微信头像url
    @TableField(value = "`login_method`")
    private Integer login_method;//状态：用户名密码登录0,微信登录1,两者同时可用2
    @TableField(value = "`classcode`")
    private String classcode;//学校的班级号，不是系统内的班级号
    @TableField(value = "`create_gmt`")
    private Long create_gmt;//创建时间
    @TableField(value = "`update_gmt`")
    private Long update_gmt;//修改时间
    //加入所有字段

    public static final String ID = "id";
    public static final String SNO = "sno";
    public static final String LOGIN_NAME = "login_name";
    public static final String LOGIN_NAME_DIGEST = "login_name_digest";
    public static final String LOGIN_PASSWORD = "login_password";
    public static final String LOGIN_PASSWORD_DIGEST = "login_password_digest";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String IS_ACTIVED = "is_actived";
    public static final String MOBILE = "mobile";
    public static final String QQ = "qq";
    public static final String WECHAT_OPENID = "wechat_openid";
    public static final String WECHAT_NICKNAME = "wechat_nickname";
    public static final String WECHAT_HEADIMGURL = "wechat_headimgurl";
    public static final String LOGIN_METHOD = "login_method";
    public static final String CLASSCODE = "classcode";
    public static final String CREATE_GMT = "create_gmt";
    public static final String UPDATE_GMT = "update_gmt";


    public Student (Integer id, String sno, String login_name, String login_name_digest, String login_password, String login_password_digest, String name, String email, Boolean is_actived, String mobile, String qq, String wechat_openid, String wechat_nickname, String wechat_headimgurl, Integer login_method, String classcode) {
        this.id = id;
        this.sno = sno;
        this.login_name = login_name;
        this.login_name_digest = login_name_digest;
        this.login_password = login_password;
        this.login_password_digest = login_password_digest;
        this.name = name;
        this.email = email;
        this.is_actived = is_actived;
        this.mobile = mobile;
        this.qq = qq;
        this.wechat_openid = wechat_openid;
        this.wechat_nickname = wechat_nickname;
        this.wechat_headimgurl = wechat_headimgurl;
        this.login_method = login_method;
        this.classcode = classcode;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    public Student (String sno,String login_name,String login_name_digest,String login_password,String login_password_digest,String name,String email,Boolean is_actived,String mobile,String qq,String wechat_openid,String wechat_nickname,String wechat_headimgurl,Integer login_method,String classcode) {
        this.sno = sno;
        this.login_name = login_name;
        this.login_name_digest = login_name_digest;
        this.login_password = login_password;
        this.login_password_digest = login_password_digest;
        this.name = name;
        this.email = email;
        this.is_actived = is_actived;
        this.mobile = mobile;
        this.qq = qq;
        this.wechat_openid = wechat_openid;
        this.wechat_nickname = wechat_nickname;
        this.wechat_headimgurl = wechat_headimgurl;
        this.login_method = login_method;
        this.classcode = classcode;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    //加入公用属性
    /**
    * 创建时间
    */
    public Date getCreate_gmt_date(){
        return new Date(create_gmt); 
    }
    /**
    * 修改时间
    */
    public Date getUpdate_gmt_date(){
        return new Date(update_gmt); 
    }
}
