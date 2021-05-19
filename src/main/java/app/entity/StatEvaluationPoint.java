package app.entity;

import lombok.Data;

/***
 * 
 * @author Denny
 *
 */
@Data
public class StatEvaluationPoint {
	private Integer subject_id;
	private String subject_name;
	private Integer type;
	private String evaluation_type_name;
	private Integer num;

}
