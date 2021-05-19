package app.entity;

import lombok.Data;

/***
 * 按学生id统计题目数，难度系数和总分
 * 
 * @author Denny
 *
 */
@Data
public class EvprStatInfo {
	private Integer id;
	private Integer num;
	private Integer sum_difficulty;
	private Integer sum_score_wish;

	@Override
	public String toString() {
		return "StatInfo1 [id=" + id + ", num=" + num + ", sum_difficulty=" + sum_difficulty + ", sum_score_wish="
				+ sum_score_wish + "]";
	}

}
