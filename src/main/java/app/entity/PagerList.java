package app.entity;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

@Data
public class PagerList<T> {
	private Pager<T> pager;
	private List<T> list;
	public PagerList(Pager<T> pager, List<T> list) {
		super();
		this.pager = pager;
		this.list = list;
	}

}
