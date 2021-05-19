package app.entity;

import lombok.Data;
import tpa.entity.User;

@Data
public class PuserEv {
	private Integer id;// userid not username
	private Integer score;// 成绩
	private Integer evpNumber;// 总题量
	private Integer difficulty;// 总难度
	private Integer scoreWish;// 成绩

	private String sno;
	private String alias;
	private String classcode;
	private String mobile;

	@Override
	public String toString() {
		return "PuserEv [id=" + id + ", score=" + score + ", evpNumber=" + evpNumber + ", difficulty=" + difficulty
				+ ", scoreWish=" + scoreWish + ", sno=" + sno + ", alias=" + alias + ", classcode=" + classcode
				+ ", mobile=" + mobile + "]";
	}

	public PuserEv(Integer id, Integer score, Integer evpNumber, Integer difficulty, Integer scoreWish, String sno,
			String alias, String classcode, String mobile) {
		this.id = id;
		this.score = score;
		this.evpNumber = evpNumber;
		this.difficulty = difficulty;
		this.scoreWish = scoreWish;
		this.sno = sno;
		this.alias = alias;
		this.classcode = classcode;
		this.mobile = mobile;
	}

}
