package app.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.entity.Book;
import app.entity.KeyAndValue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint("/demowebsocket/{jsessionid}")
public class DemoWebSocket {

	private static ObjectMapper mapper = new ObjectMapper();
	private static List<KeyAndValue> kvList = new ArrayList<>();// key:ws_session;value:jsessionid

	private void insertWsSession(Session session, String jsessionid) {
		// boolean isExists = false;
		for (int i = 0; i < kvList.size(); i++) {
			if (kvList.get(i).getValue().equals(jsessionid)) {
				// isExists = true;
				kvList.remove(i);
			}
		}
		// if (!isExists) {
		kvList.add(new KeyAndValue(session, jsessionid));
		// }
	}

	/***
	 * 
	 * @param session
	 * @param gameid
	 * @param username
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("jsessionid") String jsessionid) {

		System.out
				.println("页面刷新后重新连接DemoWebSocket.onOpen()jsessionid=" + jsessionid + ",session.id=" + session.getId());
		// System.out.println("DemoWebSocket.onOpen()session.MaxBinaryMessageBufferSize="
		// + session.getMaxBinaryMessageBufferSize());
		// System.out.println("DemoWebSocket.onOpen()session.MaxIdleTimeout=" +
		// session.getMaxIdleTimeout());
		// System.out.println("DemoWebSocket.onOpen()session.MaxTextMessageBufferSize="
		// + session.getMaxTextMessageBufferSize());
		// System.out.println("DemoWebSocket.onOpen()session.ProtocolVersion=" +
		// session.getProtocolVersion());
		// System.out.println("DemoWebSocket.onOpen()session.QueryString=" +
		// session.getQueryString());
		// System.out.println("DemoWebSocket.onOpen()session.PathParameters=" +
		// session.getPathParameters());

		insertWsSession(session, jsessionid);
	}

	@OnMessage
	public void onMessage(Session session, String message, @PathParam("jsessionid") String jsessionid) {

		System.out.println("DemoWebSocket.onMessage(),message=" + message + ",jsessionid=" + jsessionid);

		String name = "";
		String msg = "";

		// atg.taglib.json.util.JSONObject jObject;

		JSONObject jObject;
		try {
			// jObject = new atg.taglib.json.util.JSONObject(message);
			jObject = JSONObject.parseObject(message);

			// System.out.println(jObject.get("name"));
			name = (String) jObject.get("name");
			msg = (String) jObject.get("msg");

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			// sendJsonMessage(session, new Book(1, "人类简史", "尤瓦尔·赫拉利"));
			// sendJsonMessage(session, new Book(2, "未来简史", "尤瓦尔·赫拉利"));

			for (KeyAndValue kv : kvList) {
				Session ksession = (Session) kv.getKey();
				// System.out.println("DemoWebSocket.onMessage()ksession.id=" +
				// ksession.getId());
				// System.out.println("DemoWebSocket.onMessage()session.id=" + session.getId());

				if (session.getId().equals(ksession.getId())) {
					// System.out.println("equals");
					//自己
					session.getBasicRemote().sendText("you said:" + msg);
				} else {
					// System.out.println("not equals");
					//其他人
					try {
						ksession.getBasicRemote().sendText(name + " said:" + msg);
					} catch (Exception e) {

					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session, @PathParam("username") String username) {

		System.out.println("DemoWebSocket.onClose()");

	}

	private void sendJsonMessage(Session session, Book book) {
		try {
			session.getBasicRemote().sendText(mapper.writeValueAsString(book));
		} catch (IOException e) {
			this.handleException(e);
		}
	}

	private void handleException(Throwable t) {
		t.printStackTrace();
	}

	public static List<KeyAndValue> getKvList() {
		return kvList;
	}

}
