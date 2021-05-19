package app.entity;

import java.util.List;

import lombok.Data;
import tpa.entity.Evaluation_type;

@Data
public class MajorQuestions {

	private Evaluation_type type;
	private Integer number;
	private Integer score;
	private List<Pevpoint> pevplist;

	public MajorQuestions(Evaluation_type type, Integer number, Integer score, List<Pevpoint> pevplist) {
		this.type = type;
		this.number = number;
		this.score = score;
		this.pevplist = pevplist;
	}

}
