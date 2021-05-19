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
@TableName("`role`")
public class Role extends SuperEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Integer id;//角色ID
    @TableField(value = "`name`")
    private String name;//角色名称
    @TableField(value = "`alias`")
    private String alias;//角色别名
    @TableField(value = "`is_system`")
    private Boolean is_system;//系统数据 1=是,只有超级管理员能修改/0=否,拥有角色修改人员的权限能都修改
    @TableField(value = "`is_actived`")
    private Boolean is_actived;//状态 0=冻结/1=正常
    @TableField(value = "`remark`")
    private String remark;//备注信息
    @TableField(value = "`create_userid`")
    private Integer create_userid;//创建者
    @TableField(value = "`update_userid`")
    private Integer update_userid;//更新者
    @TableField(value = "`create_gmt`")
    private Long create_gmt;//创建时间
    @TableField(value = "`update_gmt`")
    private Long update_gmt;//修改时间
    //加入所有字段

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ALIAS = "alias";
    public static final String IS_SYSTEM = "is_system";
    public static final String IS_ACTIVED = "is_actived";
    public static final String REMARK = "remark";
    public static final String CREATE_USERID = "create_userid";
    public static final String UPDATE_USERID = "update_userid";
    public static final String CREATE_GMT = "create_gmt";
    public static final String UPDATE_GMT = "update_gmt";

    public Role (Integer id, String name, String alias, Boolean is_system, Boolean is_actived, String remark, Integer create_userid, Integer update_userid) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.is_system = is_system;
        this.is_actived = is_actived;
        this.remark = remark;
        this.create_userid = create_userid;
        this.update_userid = update_userid;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    public Role (String name,String alias,Boolean is_system,Boolean is_actived,String remark,Integer create_userid,Integer update_userid) {
        this.name = name;
        this.alias = alias;
        this.is_system = is_system;
        this.is_actived = is_actived;
        this.remark = remark;
        this.create_userid = create_userid;
        this.update_userid = update_userid;
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