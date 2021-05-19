package app.entity;

import lombok.Data;

@Data
public class Idname {

	private Integer id;
	private String name;

	public Idname(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
