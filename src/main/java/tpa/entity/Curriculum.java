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
@TableName("`curriculum`")
public class Curriculum extends SuperEntity {

	private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Integer id;//课程id
    @TableField(value = "`subject_id`")
    private Integer subject_id;//学科
    @TableField(value = "`student_group_id`")
    private Integer student_group_id;//班级
    @TableField(value = "`user_id`")
    private Integer user_id;//教师
    @TableField(value = "`is_actived`")
    private Boolean is_actived;//
    @TableField(value = "`semester`")
    private String semester;//学期编号：2018-2019-1
    @TableField(value = "`create_gmt`")
    private Long create_gmt;//创建时间
    @TableField(value = "`update_gmt`")
    private Long update_gmt;//修改时间
    //加入所有字段

    public static final String ID = "id";
    public static final String SUBJECT_ID = "subject_id";
    public static final String STUDENT_GROUP_ID = "student_group_id";
    public static final String USER_ID = "user_id";
    public static final String IS_ACTIVED = "is_actived";
    public static final String SEMESTER = "semester";
    public static final String CREATE_GMT = "create_gmt";
    public static final String UPDATE_GMT = "update_gmt";

    public Curriculum (Integer id, Integer subject_id, Integer student_group_id, Integer user_id, Boolean is_actived, String semester) {
        this.id = id;
        this.subject_id = subject_id;
        this.student_group_id = student_group_id;
        this.user_id = user_id;
        this.is_actived = is_actived;
        this.semester = semester;
        this.create_gmt = new  Date().getTime();
        this.update_gmt = 0l;
    }

    public Curriculum (Integer subject_id,Integer student_group_id,Integer user_id,Boolean is_actived,String semester) {
        this.subject_id = subject_id;
        this.student_group_id = student_group_id;
        this.user_id = user_id;
        this.is_actived = is_actived;
        this.semester = semester;
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
