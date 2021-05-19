package app.entity;

import lombok.Data;

@Data
public class StatUserInfo1 {
	private Integer roleid;
	private Integer num;
	private Double sum;
	private Double avg;

	@Override
	public String toString() {
		return "StatUserInfo1:" + "roleid=" + roleid + ",num=" + num+ ",sum=" + sum+ ",avg=" + avg;
	}
}
