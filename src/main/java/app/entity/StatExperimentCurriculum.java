package app.entity;

import lombok.Data;

/***
 * 
 * @author Denny
 *
 */
@Data
public class StatExperimentCurriculum {
	private Integer curriculum_id;
	private Integer student_group_id;
	private String student_group_name;
	private String semester;
	private Integer subject_id;
	private String subject_name;
	private Boolean experiment_curriculum_is_actived;
	private Integer num;
}
