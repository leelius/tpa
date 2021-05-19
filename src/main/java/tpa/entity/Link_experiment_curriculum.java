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
@TableName("`link_experiment_curriculum`")
public class Link_experiment_curriculum extends SuperEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Integer id;//
    @TableField(value = "`experiment_id`")
    private Integer experiment_id;//
    @TableField(value = "`curriculum_id`")
    private Integer curriculum_id;//
    @TableField(value = "`subject_id`")
    private Integer subject_id;//
    @TableField(value = "`opening_gmt`")
    private Long opening_gmt;//
    @TableField(value = "`closing_gmt`")
    private Long closing_gmt;//
    @TableField(value = "`sortid`")
    private Integer sortid;//
    @TableField(value = "`is_actived`")
    private Boolean is_actived;//
    @TableField(value = "`create_gmt`")
    private Long create_gmt;//创建时间
    @TableField(value = "`update_gmt`")
    private Long update_gmt;//修改时间
    //加入所有字段

    public static final String ID = "id";
    public static final String EXPERIMENT_ID = "experiment_id";
    public static final String CURRICULUM_ID = "curriculum_id";
    public static final String SUBJECT_ID = "subject_id";
    public static final String OPENING_GMT = "opening_gmt";
    public static final String CLOSING_GMT = "closing_gmt";
    public static final String SORTID = "sortid";
    public static final String IS_ACTIVED = "is_actived";
    public static final String CREATE_GMT = "create_gmt";
    public static final String UPDATE_GMT = "update_gmt";

    public Link_experiment_curriculum (Integer id, Integer experiment_id, Integer curriculum_id, Integer subject_id, Long opening_gmt, Long closing_gmt, Integer sortid, Boolean is_actived) {
        this.id = id;
        this.experiment_id = experiment_id;
        this.curriculum_id = curriculum_id;
        this.subject_id = subject_id;
        this.opening_gmt = opening_gmt;
        this.closing_gmt = closing_gmt;
        this.sortid = sortid;
        this.is_actived = is_actived;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    public Link_experiment_curriculum (Integer experiment_id,Integer curriculum_id,Integer subject_id,Long opening_gmt,Long closing_gmt,Integer sortid,Boolean is_actived) {
        this.experiment_id = experiment_id;
        this.curriculum_id = curriculum_id;
        this.subject_id = subject_id;
        this.opening_gmt = opening_gmt;
        this.closing_gmt = closing_gmt;
        this.sortid = sortid;
        this.is_actived = is_actived;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    //加入公用属性
    /**
    * 
    */
    public Date getOpening_gmt_date(){
        return new Date(opening_gmt); 
    }
    /**
    * 
    */
    public Date getClosing_gmt_date(){
        return new Date(closing_gmt); 
    }
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