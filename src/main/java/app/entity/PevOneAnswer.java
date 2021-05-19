package app.entity;

import lombok.Data;

@Data
public class PevOneAnswer {
	// <para name="answer" value="d" check="on"/>
	private String key;
	private String answer;
	private Boolean correct;
	private Boolean reply;

	@Override
	public String toString() {

		return "answer:" + "key=" + key + ";answer=" + answer + ";correct=" + correct + ";reply=" + reply;
	}

}
