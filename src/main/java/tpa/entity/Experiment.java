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
@TableName("`experiment`")
public class Experiment extends SuperEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Integer id;//
    @TableField(value = "`subject_id`")
    private Integer subject_id;//
    @TableField(value = "`curriculum_id`")
    private Integer curriculum_id;//课程id，如果未指定课程，即为该科目共有实验
    @TableField(value = "`is_actived`")
    private Boolean is_actived;//是否可用
    @TableField(value = "`intro`")
    private String intro;//
    @TableField(value = "`title`")
    private String title;//实验标题
    @TableField(value = "`content`")
    private String content;//实验内容
    @TableField(value = "`purposes_requirement`")
    private String purposes_requirement;//实验目的及要求
    @TableField(value = "`filesize`")
    private Integer filesize;//文件大小
    @TableField(value = "`create_gmt`")
    private Long create_gmt;//创建时间
    @TableField(value = "`update_gmt`")
    private Long update_gmt;//修改时间
    //加入所有字段

    public static final String ID = "id";
    public static final String SUBJECT_ID = "subject_id";
    public static final String CURRICULUM_ID = "curriculum_id";
    public static final String IS_ACTIVED = "is_actived";
    public static final String INTRO = "intro";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String PURPOSES_REQUIREMENT = "purposes_requirement";
    public static final String FILESIZE = "filesize";
    public static final String CREATE_GMT = "create_gmt";
    public static final String UPDATE_GMT = "update_gmt";

    public Experiment (Integer id, Integer subject_id, Integer curriculum_id, Boolean is_actived, String intro, String title, String content, String purposes_requirement, Integer filesize) {
        this.id = id;
        this.subject_id = subject_id;
        this.curriculum_id = curriculum_id;
        this.is_actived = is_actived;
        this.intro = intro;
        this.title = title;
        this.content = content;
        this.purposes_requirement = purposes_requirement;
        this.filesize = filesize;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    public Experiment (Integer subject_id,Integer curriculum_id,Boolean is_actived,String intro,String title,String content,String purposes_requirement,Integer filesize) {
        this.subject_id = subject_id;
        this.curriculum_id = curriculum_id;
        this.is_actived = is_actived;
        this.intro = intro;
        this.title = title;
        this.content = content;
        this.purposes_requirement = purposes_requirement;
        this.filesize = filesize;
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
