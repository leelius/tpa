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
@TableName("`permit`")
public class Permit extends SuperEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Integer id;//key
    @TableField(value = "`target`")
    private String target;//许可表名:user
    @TableField(value = "`operation`")
    private String operation;//许可操作:insert
    @TableField(value = "`name`")
    private String name;//许可名称:更新用户
    @TableField(value = "`code`")
    private String code;//许可代码(user:insert)库名:操作
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
    public static final String TARGET = "target";
    public static final String OPERATION = "operation";
    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String CREATE_USERID = "create_userid";
    public static final String UPDATE_USERID = "update_userid";
    public static final String CREATE_GMT = "create_gmt";
    public static final String UPDATE_GMT = "update_gmt";

    public Permit (Integer id, String target, String operation, String name, String code, Integer create_userid, Integer update_userid) {
        this.id = id;
        this.target = target;
        this.operation = operation;
        this.name = name;
        this.code = code;
        this.create_userid = create_userid;
        this.update_userid = update_userid;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    public Permit (String target,String operation,String name,String code,Integer create_userid,Integer update_userid) {
        this.target = target;
        this.operation = operation;
        this.name = name;
        this.code = code;
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
