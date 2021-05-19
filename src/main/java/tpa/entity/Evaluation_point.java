//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- entity: entity
//-- Date Generated: 2020-10-09 10:47:59
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
@TableName("`evaluation_point`")
public class Evaluation_point extends SuperEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Integer id;//主键
    @TableField(value = "`subject_id`")
    private Integer subject_id;//题目所属科目
    @TableField(value = "`is_actived`")
    private Boolean is_actived;//是否可用
    @TableField(value = "`type`")
    private Integer type;//考核类型：选择/填空/正则填空/判断/文件
    @TableField(value = "`difficulty`")
    private Integer difficulty;//难度
    @TableField(value = "`content`")
    private String content;//考核点内容
    @TableField(value = "`create_gmt`")
    private Long create_gmt;//创建时间
    @TableField(value = "`update_gmt`")
    private Long update_gmt;//修改时间
    //加入所有字段

    public static final String ID = "id";
    public static final String SUBJECT_ID = "subject_id";
    public static final String IS_ACTIVED = "is_actived";
    public static final String TYPE = "type";
    public static final String DIFFICULTY = "difficulty";
    public static final String CONTENT = "content";
    public static final String CREATE_GMT = "create_gmt";
    public static final String UPDATE_GMT = "update_gmt";

    public Evaluation_point (Integer id, Integer subject_id, Boolean is_actived, Integer type, Integer difficulty, String content) {
        this.id = id;
        this.subject_id = subject_id;
        this.is_actived = is_actived;
        this.type = type;
        this.difficulty = difficulty;
        this.content = content;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    public Evaluation_point (Integer subject_id,Boolean is_actived,Integer type,Integer difficulty,String content) {
        this.subject_id = subject_id;
        this.is_actived = is_actived;
        this.type = type;
        this.difficulty = difficulty;
        this.content = content;
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
