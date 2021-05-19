package com.scyf.bean;
/** 
 * 添加临时素材 返回结果
 * @author WangYunfei
 * @version 
 * 创建时间：2018年3月22日 下午5:13:12 
 * 
 */
public class UploadTempMaterial {

/**
 * {"type":"image","media_id":"tnP9-GLs-l4KFgjI1Tl6gBwqs4LS1uj8vbx74ie_kPrZ7VzzLmMx9D050Zp-I2SS","created_at":1521623156}
 */
	/**
	 * 类型
	 */
	private String type;
	/**
	 * media_id
	 */
	private String media_id;
	/**
	 * 
	 */
	private String created_at;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "UploadTempMaterial [type=" + type + ", media_id=" + media_id + ", created_at=" + created_at + "]";
	}
	
	
	
}
