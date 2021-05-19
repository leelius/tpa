package app.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import tpa.entity.Subject;;

@Data
public class Menu {

	private Integer id;// 序号
	private String intro;// 简介
	private String name;// 名称
	private String alias;// 简称
	private String outlink;// 外部链接url
	private Boolean is_actived;// 是否可用

	private List<Menu> submenu;

	public Menu() {

	}

	public Menu(Subject col) {

		this.id = col.getId();
		this.intro = col.getIntro();
		this.name = col.getName();
		this.alias = col.getIllustration();
		this.outlink = col.getName();
		this.is_actived = col.getIs_actived();

		this.submenu = new ArrayList<>();

	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", intro=" + intro + ", name=" + name + ", alias=" + alias + ", outlink=" + outlink
				+ ", is_actived=" + is_actived + ", submenu=" + submenu + "]";
	}

}
