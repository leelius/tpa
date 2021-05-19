package app.entity;

import lombok.Data;

/***
 * 
 * @author Denny
 *
 */
@Data
public class StatInfo1 {
	private Integer id;
	private Integer roleid;
	private Integer num;
	private Double sum;
	private Double avg;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "StatUserInfo1:" + "id=" + id + "roleid=" + roleid + ",num=" + num + ",sum=" + sum + ",avg=" + avg;
	}

}
