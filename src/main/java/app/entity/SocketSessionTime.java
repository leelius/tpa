package app.entity;

import java.util.Date;

import lombok.Data;

@Data
public class SocketSessionTime {
	private Object ws;
	private String jsid;
	private Long time;
	private String openid;
	private String username;

	public SocketSessionTime(Object ws, String jsid) {
		super();
		this.ws = ws;
		this.jsid = jsid;
		this.time = new Date().getTime();
		this.openid = "";
		this.username = "";
	}

	public SocketSessionTime(String jsid, String username) {
		super();
		this.ws = null;
		this.jsid = jsid;
		this.time = new Date().getTime();
		this.openid = "";
		this.username = username;
	}

	@Override
	public String toString() {
		return "SocketSessionTime [ws=" + ws + ", jsid=" + jsid + ", time=" + time + ", openid=" + openid + "]";
	}

}
