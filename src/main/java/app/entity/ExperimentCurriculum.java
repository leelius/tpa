package app.entity;

import java.util.List;

import lombok.Data;
import tpa.entity.Vcurriculum;
import tpa.entity.Vlink_experiment_curriculum_min;

/***
 * 
 * @author Denny
 *
 *         对应一位老师的一个班级的的实验课程
 *
 */
@Data
public class ExperimentCurriculum {
	private Vcurriculum curriculum;
	private List<Vlink_experiment_curriculum_min> experimentlist;

}
