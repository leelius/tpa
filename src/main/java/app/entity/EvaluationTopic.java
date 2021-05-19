package app.entity;

import lombok.Data;
import tpa.entity.Evaluation_type;

@Data
public class EvaluationTopic {
	private Evaluation_type type;
	private Integer num;
	private Integer point;
	
	
	public EvaluationTopic(Evaluation_type type, Integer num, Integer point) {
		super();
		this.type = type;
		this.num = num;
		this.point = point;
	}


}
