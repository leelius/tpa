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
@TableName("`experiment_record`")
public class Experiment_record extends SuperEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Integer id;//
    @TableField(value = "`student_id`")
    private Integer student_id;//
    @TableField(value = "`sno`")
    private String sno;//学号
    @TableField(value = "`experiment_id`")
    private Integer experiment_id;//实验id
    @TableField(value = "`comment`")
    private String comment;//对实验报告的评语，100字以内！
    @TableField(value = "`mainflow`")
    private String mainflow;//实验主要流程、基本操作或核心代码、算法片段
    @TableField(value = "`report`")
    private String report;//实验报告，用xml记录实验结果
    @TableField(value = "`result`")
    private String result;//实验结果的分析与评价
    @TableField(value = "`score`")
    private Integer score;//实验成绩
    @TableField(value = "`submit`")
    private Long submit;//实验提交时间
    @TableField(value = "`scorescript`")
    private String scorescript;//成绩描述：优秀/良好/中等/及格/不及格
    @TableField(value = "`reviewstate`")
    private Integer reviewstate;//评阅状态1已评,2待评
    @TableField(value = "`create_gmt`")
    private Long create_gmt;//创建时间
    @TableField(value = "`update_gmt`")
    private Long update_gmt;//修改时间
    //加入所有字段

    public static final String ID = "id";
    public static final String STUDENT_ID = "student_id";
    public static final String SNO = "sno";
    public static final String EXPERIMENT_ID = "experiment_id";
    public static final String COMMENT = "comment";
    public static final String MAINFLOW = "mainflow";
    public static final String REPORT = "report";
    public static final String RESULT = "result";
    public static final String SCORE = "score";
    public static final String SUBMIT = "submit";
    public static final String SCORESCRIPT = "scorescript";
    public static final String REVIEWSTATE = "reviewstate";
    public static final String CREATE_GMT = "create_gmt";
    public static final String UPDATE_GMT = "update_gmt";

    public Experiment_record (Integer id, Integer student_id, String sno, Integer experiment_id, String comment, String mainflow, String report, String result, Integer score, Long submit, String scorescript, Integer reviewstate) {
        this.id = id;
        this.student_id = student_id;
        this.sno = sno;
        this.experiment_id = experiment_id;
        this.comment = comment;
        this.mainflow = mainflow;
        this.report = report;
        this.result = result;
        this.score = score;
        this.submit = submit;
        this.scorescript = scorescript;
        this.reviewstate = reviewstate;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    public Experiment_record (Integer student_id,String sno,Integer experiment_id,String comment,String mainflow,String report,String result,Integer score,Long submit,String scorescript,Integer reviewstate) {
        this.student_id = student_id;
        this.sno = sno;
        this.experiment_id = experiment_id;
        this.comment = comment;
        this.mainflow = mainflow;
        this.report = report;
        this.result = result;
        this.score = score;
        this.submit = submit;
        this.scorescript = scorescript;
        this.reviewstate = reviewstate;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    //加入公用属性
    /**
    * 实验提交时间
    */
    public Date getSubmit_date(){
        return new Date(submit); 
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