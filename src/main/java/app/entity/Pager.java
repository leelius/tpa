package app.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * 分页实体
 */
@Data
public class Pager<T> {
	public Pager(Page<T> page) {
		this.current = page.getCurrent();
		this.pages = page.getPages();
		if (current > pages) {
			current = pages;
		}
		this.size = page.getSize();
		this.total = page.getTotal();
		this.next = this.pre = current;
		this.hasNext = page.hasNext();
		this.hasPrevious = page.hasPrevious();
		if (hasNext) {
			this.next = current + 1;
		}
		if (hasPrevious) {
			this.pre = current - 1;
		}
		this.recordsSize = page.getRecords().size();
	}

	/***
	 * 当前页码
	 */
	private Long current;
	/***
	 * 总页数
	 */
	private Long pages;
	/***
	 * 当前页码大小
	 */
	private Long size;
	/***
	 * 项目总数
	 */
	private Long total;
	/***
	 * 前一页
	 */
	private Long pre;
	/***
	 * 后一页
	 */
	private Long next;
	/**
	 * 是否有前一页
	 */
	private Boolean hasNext;
	/***
	 * 是否有后一页
	 */
	private Boolean hasPrevious;
	/***
	 * 记录条数
	 */
	private Integer recordsSize;

}
