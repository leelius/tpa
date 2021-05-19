package app.pdf;

import java.util.Date;

public class PdfInfo {
	private Integer expOrderId;// 实验序号
	private String subjTitle;// 科目名称
	private Date submitdate;// 提交日期
	private String ugname;// 班名
	private String expTitle;// 实验名称
	private Integer erscore;// 实验成绩
	private String erscorescript;// 实验成绩
	private String userAlias;// 学生名字
	private String username;// 学号
	private String teacherAlias;// 教师名字
	private String expPurposesRequirements;// 实验目的及要求
	private String expContent;// 实验内容
	private String erMainflow;// 实验流程
	private String erResult;// 实验结果

	public Integer getExpOrderId() {
		return expOrderId;
	}

	public void setExpOrderId(Integer expOrderId) {
		this.expOrderId = expOrderId;
	}

	public String getSubjTitle() {
		return subjTitle;
	}

	public void setSubjTitle(String subjTitle) {
		this.subjTitle = subjTitle;
	}

	public Date getSubmitdate() {
		return submitdate;
	}

	public void setSubmitdate(Date submitdate) {
		this.submitdate = submitdate;
	}

	public String getUgname() {
		return ugname;
	}

	public void setUgname(String ugname) {
		this.ugname = ugname;
	}

	public String getExpTitle() {
		return expTitle;
	}

	public void setExpTitle(String expTitle) {
		this.expTitle = expTitle;
	}

	public Integer getErscore() {
		return erscore;
	}

	public void setErscore(Integer erscore) {
		this.erscore = erscore;
	}

	public String getUserAlias() {
		return userAlias;
	}

	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTeacherAlias() {
		return teacherAlias;
	}

	public void setTeacherAlias(String teacherAlias) {
		this.teacherAlias = teacherAlias;
	}

	public String getExpPurposesRequirements() {
		return expPurposesRequirements;
	}

	public void setExpPurposesRequirements(String expPurposesRequirements) {
		this.expPurposesRequirements = expPurposesRequirements;
	}

	public String getExpContent() {
		return expContent;
	}

	public void setExpContent(String expContent) {
		this.expContent = expContent;
	}

	public String getErMainflow() {
		return erMainflow;
	}

	public void setErMainflow(String erMainflow) {
		this.erMainflow = erMainflow;
	}

	public String getErResult() {
		return erResult;
	}

	public void setErResult(String erResult) {
		this.erResult = erResult;
	}

	public String getErscorescript() {
		return erscorescript;
	}

	public void setErscorescript(String erscorescript) {
		this.erscorescript = erscorescript;
	}

}
