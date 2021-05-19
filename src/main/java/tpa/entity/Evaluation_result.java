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
@TableName("`evaluation_result`")
public class Evaluation_result extends SuperEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Integer id;//主键
    @TableField(value = "`student_id`")
    private Integer student_id;//
    @TableField(value = "`evaluation_id`")
    private Integer evaluation_id;//考核id
    @TableField(value = "`sno`")
    private String sno;//学号
    @TableField(value = "`comment`")
    private String comment;//评阅注释
    @TableField(value = "`evaluation_record`")
    private String evaluation_record;//
    @TableField(value = "`score`")
    private Integer score;//考核成绩
    @TableField(value = "`submit_gmt`")
    private Long submit_gmt;//提交日期
    @TableField(value = "`is_reviewed`")
    private Boolean is_reviewed;//评阅完成
    @TableField(value = "`create_gmt`")
    private Long create_gmt;//创建时间
    @TableField(value = "`update_gmt`")
    private Long update_gmt;//修改时间
    //加入所有字段

    public static final String ID = "id";
    public static final String STUDENT_ID = "student_id";
    public static final String EVALUATION_ID = "evaluation_id";
    public static final String SNO = "sno";
    public static final String COMMENT = "comment";
    public static final String EVALUATION_RECORD = "evaluation_record";
    public static final String SCORE = "score";
    public static final String SUBMIT_GMT = "submit_gmt";
    public static final String IS_REVIEWED = "is_reviewed";
    public static final String CREATE_GMT = "create_gmt";
    public static final String UPDATE_GMT = "update_gmt";

    public Evaluation_result (Integer id, Integer student_id, Integer evaluation_id, String sno, String comment, String evaluation_record, Integer score, Long submit_gmt, Boolean is_reviewed) {
        this.id = id;
        this.student_id = student_id;
        this.evaluation_id = evaluation_id;
        this.sno = sno;
        this.comment = comment;
        this.evaluation_record = evaluation_record;
        this.score = score;
        this.submit_gmt = submit_gmt;
        this.is_reviewed = is_reviewed;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    public Evaluation_result (Integer student_id,Integer evaluation_id,String sno,String comment,String evaluation_record,Integer score,Long submit_gmt,Boolean is_reviewed) {
        this.student_id = student_id;
        this.evaluation_id = evaluation_id;
        this.sno = sno;
        this.comment = comment;
        this.evaluation_record = evaluation_record;
        this.score = score;
        this.submit_gmt = submit_gmt;
        this.is_reviewed = is_reviewed;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    //加入公用属性
    /**
    * 提交日期
    */
    public Date getSubmit_gmt_date(){
        return new Date(submit_gmt); 
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