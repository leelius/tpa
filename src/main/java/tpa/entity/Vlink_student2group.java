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
@TableName("`vlink_student2group`")
public class Vlink_student2group extends SuperEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Integer id;//主键
    @TableField(value = "`student_id`")
    private Integer student_id;//用户id
    @TableField(value = "`student_group_id`")
    private Integer student_group_id;//分组id
    @TableField(value = "`student_is_actived`")
    private Boolean student_is_actived;//冻结账号
    @TableField(value = "`student_group_is_actived`")
    private Boolean student_group_is_actived;//是否可以登录
    @TableField(value = "`sno`")
    private String sno;//学号
    @TableField(value = "`name`")
    private String name;//用户名称
    @TableField(value = "`email`")
    private String email;//电子邮件
    @TableField(value = "`mobile`")
    private String mobile;//手机号码
    @TableField(value = "`qq`")
    private String qq;//QQ号码
    @TableField(value = "`classcode`")
    private String classcode;//学校的班级号，不是系统内的班级号
    @TableField(value = "`login_method`")
    private Integer login_method;//状态：用户名密码登录0,微信登录1,两者同时可用2
    @TableField(value = "`student_group_name`")
    private String student_group_name;//组名
    @TableField(value = "`student_group_code`")
    private String student_group_code;//分组编号，8位字符
    //加入所有字段

    public static final String ID = "id";
    public static final String STUDENT_ID = "student_id";
    public static final String STUDENT_GROUP_ID = "student_group_id";
    public static final String STUDENT_IS_ACTIVED = "student_is_actived";
    public static final String STUDENT_GROUP_IS_ACTIVED = "student_group_is_actived";
    public static final String SNO = "sno";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String QQ = "qq";
    public static final String CLASSCODE = "classcode";
    public static final String LOGIN_METHOD = "login_method";
    public static final String STUDENT_GROUP_NAME = "student_group_name";
    public static final String STUDENT_GROUP_CODE = "student_group_code";

    public Vlink_student2group (Integer id, Integer student_id, Integer student_group_id, Boolean student_is_actived, Boolean student_group_is_actived, String sno, String name, String email, String mobile, String qq, String classcode, Integer login_method, String student_group_name, String student_group_code) {
        this.id = id;
        this.student_id = student_id;
        this.student_group_id = student_group_id;
        this.student_is_actived = student_is_actived;
        this.student_group_is_actived = student_group_is_actived;
        this.sno = sno;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.qq = qq;
        this.classcode = classcode;
        this.login_method = login_method;
        this.student_group_name = student_group_name;
        this.student_group_code = student_group_code;
    }

    public Vlink_student2group (Integer student_id,Integer student_group_id,Boolean student_is_actived,Boolean student_group_is_actived,String sno,String name,String email,String mobile,String qq,String classcode,Integer login_method,String student_group_name,String student_group_code) {
        this.student_id = student_id;
        this.student_group_id = student_group_id;
        this.student_is_actived = student_is_actived;
        this.student_group_is_actived = student_group_is_actived;
        this.sno = sno;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.qq = qq;
        this.classcode = classcode;
        this.login_method = login_method;
        this.student_group_name = student_group_name;
        this.student_group_code = student_group_code;
    }

    //加入公用属性
}
