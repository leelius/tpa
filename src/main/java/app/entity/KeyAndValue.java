package app.entity;

import lombok.Data;

@Data
public class KeyAndValue {
	private Object key;
	private String value;
	public KeyAndValue(Object key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
}
