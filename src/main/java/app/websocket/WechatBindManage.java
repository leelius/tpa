package app.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.entity.SocketSessionTime;
import app.utils.Common;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * 管理登录的长链接
 * 
 * @author Denny
 *
 */
@ServerEndpoint("/wbm/{jsessionid}/{sid}")
public class WechatBindManage {

	private static ObjectMapper mapper = new ObjectMapper();
	private static List<SocketSessionTime> kvList = new ArrayList<>();// key:ws_session;value:jsessionid

	public static void displayList() {

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println("wbm，当前共有" + kvList.size() + "条记录");
//		for (int i = 0; i < kvList.size(); i++) {
//			if (kvList.get(i).getWs() != null) {
//				System.out.print("第" + (i + 1) + "条记录：ws.id=" + ((Session) kvList.get(i).getWs()).getId() + ",username="
//						+ kvList.get(i).getUsername() + ",Jsid=" + kvList.get(i).getJsid() + ",time="
//						+ kvList.get(i).getTime() + "(" + sdf.format(new Date(kvList.get(i).getTime())) + ")"
//						+ ",openid=" + kvList.get(i).getOpenid());
//			} else {
//				System.out.print("第" + (i + 1) + "条记录：ws.id=null" + ",username=" + kvList.get(i).getUsername()
//						+ ",Jsid=" + kvList.get(i).getJsid() + ",time=" + kvList.get(i).getTime() + "("
//						+ sdf.format(new Date(kvList.get(i).getTime())) + ")" + ",openid=" + kvList.get(i).getOpenid());
//			}
//			System.out.println("时间差：" + (new Date().getTime() - kvList.get(i).getTime()));
//		}
	}

	/**
	 * 将新建的session加入列表
	 * 
	 * @param session
	 * @param jsessionid
	 */
	public synchronized static void insertWsSession(String jsessionid, String username) {

		//System.out.println("WechatBindManage.insertWsSession(-------------------before-------------------)");
		displayList();

		for (int i = 0; i < kvList.size(); i++) {
			if (kvList.get(i).getJsid().equals(jsessionid)) {
				kvList.remove(i);
			}
		}
		for (int i = 0; i < kvList.size(); i++) {
			if (new Date().getTime() - kvList.get(i).getTime() > Common.periodOfValidity) {// Common.periodOfValidity
				kvList.remove(i);
			}
		}

		kvList.add(new SocketSessionTime(jsessionid, username));
		//System.out.println("WechatBindManage.insertWsSession(-------------------after-------------------)");
		displayList();
	}

	public synchronized void updateWsSession(String jsessionid, Session session) {

		//System.out.println("WechatBindManage.updateWsSession(-------------------before-------------------)");
		displayList();

		for (int i = 0; i < kvList.size(); i++) {
			if (kvList.get(i).getJsid().equals(jsessionid)) {
				kvList.get(i).setWs(session);
			}
		}

		//System.out.println("WechatBindManage.updateWsSession(-------------------after-------------------)");
		displayList();
	}

	public synchronized static void updateWsSession(String jsessionid, String openid) {

		//System.out.println("WechatBindManage.updateWsSession(-------------------before-------------------)");
		displayList();

		for (int i = 0; i < kvList.size(); i++) {
			if (kvList.get(i).getJsid().equals(jsessionid)) {
				kvList.get(i).setOpenid(openid);
			}
		}

		//System.out.println("WechatBindManage.updateWsSession(-------------------after-------------------)");
		displayList();
	}

	/***
	 * 
	 * @param session
	 * @param gameid
	 * @param username
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("jsessionid") String jsessionid, @PathParam("sid") String sid) {

		System.out.println("新连接WechatBindManage.onOpen()jsessionid=" + jsessionid + ",sid=" + sid + ",sessionid="
				+ session.getId());

		if (sid.equals("sid")) {
			// 电脑pc端链接后，更新Session信息（之前Session为空）
			updateWsSession(jsessionid, session);

		} else {

		}

	}

	@OnMessage
	public void onMessage(Session session, String message, @PathParam("jsessionid") String jsessionid,
			@PathParam("sid") String sid) {

		System.out.println(
				"WechatBindManage.onMessage(),message=" + message + ",jsessionid=" + jsessionid + ",sid=" + sid);

		// 移动端发出的msg

		for (int i = 0; i < kvList.size(); i++) {
			if (kvList.get(i).getJsid().equals(sid)) {

				sendJsonMessage((Session) kvList.get(i).getWs(), "bindwechatyesdoit");

				//System.out.println("WechatBindManage.onMessage(-------------------after-------------------)");
				displayList();
				return;
			}
		}

	}

	/***
	 * 页面跳转也会触发此操作
	 * 
	 * @param session
	 * @param jsessionid
	 * @param sid
	 */
	@OnClose
	public void onClose(Session session, @PathParam("jsessionid") String jsessionid, @PathParam("sid") String sid) {
		System.out.println("WechatBindManage.onClose()jsessionid=" + jsessionid + ",sid=" + sid);
//		System.out.println("WechatBindManage.onClose(-------------------before-------------------)");
//		displayList();
//		for (int i = 0; i < kvList.size(); i++) {
//			if (kvList.get(i).getJsid().equals(jsessionid)) {
//				kvList.remove(i);
//				System.out.println("WechatBindManage.onClose()已经移除jsessionid=" + jsessionid);
//			}
//		}
//		System.out.println("WechatBindManage.onClose(-------------------after-------------------)");
//		displayList();
	}

	private void sendJsonMessage(Session session, String msg) {
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			this.handleException(e);
		}
	}

	private void handleException(Throwable t) {
		t.printStackTrace();
	}

	public static List<SocketSessionTime> getKvList() {
		return kvList;
	}

}
