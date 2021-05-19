package com.scyf.bean;

import java.util.Arrays;

/** 
 * 获取素材列表
 * @author WangYunfei
 * @version 1
 * 创建时间：2018年3月23日
 * 
 */
public class MediaList {

	/**
	 * Item
	 */
	private Item[] item;
	/**
	 * 总计
	 */
	private String total_count;
	/**
	 * item 数
	 */
	private String item_count;
	
	public Item[] getItem() {
		return item;
	}
	public void setItem(Item[] item) {
		this.item = item;
	}
	public String getTotal_count() {
		return total_count;
	}
	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}
	public String getItem_count() {
		return item_count;
	}
	public void setItem_count(String item_count) {
		this.item_count = item_count;
	}
	@Override
	public String toString() {
		return "MediaList [item=" + Arrays.toString(item) + ", total_count=" + total_count + ", item_count="
				+ item_count + "]";
	}
	
}

