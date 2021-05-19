package app.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.entity.Book;
import app.entity.SocketSessionTime;
import app.utils.Common;
import sld.webutils.AsymmetricEncryption;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * 管理登录的长链接
 * 
 * @author Denny
 *
 */
@ServerEndpoint("/wwsm/{jsessionid}/{sid}")
public class WechatWebSocketLinkManage {

	private static ObjectMapper mapper = new ObjectMapper();
	private static List<SocketSessionTime> kvList = new ArrayList<>();// key:ws_session;value:jsessionid
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private void displayList() {
		// System.out.println("wwsm，当前共有" + kvList.size() + "条记录");
//		for (int i = 0; i < kvList.size(); i++) {
//			System.out.print("第" + (i + 1) + "条记录：ws.id=" + ((Session) kvList.get(i).getWs()).getId() + ",Jsid="
//					+ kvList.get(i).getJsid() + ",time=" + kvList.get(i).getTime() + "("
//					+ sdf.format(new Date(kvList.get(i).getTime())) + ")" + ",openid=" + kvList.get(i).getOpenid());
//			System.out.println("时间差：" + (new Date().getTime() - kvList.get(i).getTime()));
//		}
	}

	/**
	 * 将新建的session加入列表
	 * 
	 * @param session
	 * @param jsessionid
	 */
	private synchronized void insertWsSession(Session session, String jsessionid) {
		// System.out.println("WechatWebSocketLinkManage.insertWsSession(-------------------before-------------------)");
		// displayList();

		for (int i = 0; i < kvList.size(); i++) {
			if (kvList.get(i).getJsid().equals(jsessionid)) {
				kvList.remove(i);
			}
		}
		for (int i = 0; i < kvList.size(); i++) {
			if (new Date().getTime() - kvList.get(i).getTime() > 300000) {// Common.periodOfValidity
				kvList.remove(i);
			}
		}

		kvList.add(new SocketSessionTime(session, jsessionid));
		// System.out.println("WechatWebSocketLinkManage.insertWsSession(-------------------after-------------------)");
		// displayList();
	}

//	private synchronized Session updateWsSession(String jsessionid, String openid) {
//
//		Session session = null;
//		System.out.println("WechatWebSocketLinkManage.updateWsSession(-------------------before-------------------)");
//		displayList();
//
//		for (int i = 0; i < kvList.size(); i++) {
//			if (kvList.get(i).getJsid().equals(jsessionid)) {
//				kvList.get(i).setOpenid(openid);
//				session = (Session) kvList.get(i).getWs();
//			}
//		}
//		System.out.println("WechatWebSocketLinkManage.updateWsSession(-------------------after-------------------)");
//		displayList();
//
//		return session;
//	}

	/***
	 * 
	 * @param session
	 * @param gameid
	 * @param username
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("jsessionid") String jsessionid, @PathParam("sid") String sid) {

		// System.out.println("新连接WechatWebSocketLinkManage.onOpen()jsessionid=" +
		// jsessionid + ",sid=" + sid);

		if (sid.equals("sid")) {
			// 电脑pc端

			insertWsSession(session, jsessionid);

		} else {
			// 移动端
			// 查找当前等待链接的列表中是否有对应的sid
			for (int i = 0; i < kvList.size(); i++) {
				if (kvList.get(i).getJsid().equals(sid)) {
					// System.out.println("WechatWebSocketLinkManage.onOpen(我等到花儿也谢了)");
					// System.out.print("第" + (i + 1) + "条记录：ws.id=" + ((Session)
					// kvList.get(i).getWs()).getId() + ",Jsid="
					// + kvList.get(i).getJsid() + ",time=" + kvList.get(i).getTime() + "("
					// + sdf.format(new Date(kvList.get(i).getTime())) + ")");
					// System.out.println("时间差：" + (new Date().getTime() -
					// kvList.get(i).getTime()));
					sendJsonMessage(session, "可以确认");
					sendJsonMessage((Session) kvList.get(i).getWs(), "可以确认");

					return;
				}
			}
			sendJsonMessage(session, "链接已经关闭");
		}

		// System.out.println("WechatWebSocketLinkManage.onOpen()session.MaxBinaryMessageBufferSize="
		// + session.getMaxBinaryMessageBufferSize());
		// System.out.println("WechatWebSocketLinkManage.onOpen()session.MaxIdleTimeout="
		// +
		// session.getMaxIdleTimeout());
		// System.out.println("WechatWebSocketLinkManage.onOpen()session.MaxTextMessageBufferSize="
		// + session.getMaxTextMessageBufferSize());
		// System.out.println("WechatWebSocketLinkManage.onOpen()session.ProtocolVersion="
		// +
		// session.getProtocolVersion());
		// System.out.println("WechatWebSocketLinkManage.onOpen()session.QueryString=" +
		// session.getQueryString());
		// System.out.println("WechatWebSocketLinkManage.onOpen()session.PathParameters="
		// +
		// session.getPathParameters());

	}

	@OnMessage
	public void onMessage(Session session, String message, @PathParam("jsessionid") String jsessionid,
			@PathParam("sid") String sid) {

		// System.out.println("WechatWebSocketLinkManage.onMessage(),message=" + message
		// + ",jsessionid=" + jsessionid
		// + ",sid=" + sid);

		// message=ok,709b724fdd9982f6b9bd65cee2c04ebc0a2577d24b4d8a75abc5268ce84057c257bc5639eb7fd36a62d13a74a8fd3ee2350a353fec9080f3ed6b2e2f2c797610596a24cf6b1dfd6f27313d92d9f76324ac118b5aa6d84ee7948c23977b4795a2e3b5f964616914da40243f0adc54bc7265bdca521d6e51682adb372043ededd1
		// jsessionid=B45DE42795361FBC0BC3365CB0C11A81,sid=19D1662F6715AA09F40FD7896434869B
		AsymmetricEncryption instance = new AsymmetricEncryption();
		String privateKeyString = "30820277020100300d06092a864886f70d0101010500048202613082025d02010002818100b1c081f9245bb9ef7857f7dd51b12a5012015681f447f1d0dc0674bc3dd73d5d9808b503648d3a5d082f90f91357ef28062b45b0eba8739d15e350903dcd85b66f6e66cea13c25089e26379dd9bf7b7c90bd27d3482ba07ddd9bf4c063cbd96390486c7020d9dc9a281614b608c19d6d21ecfce015b95ddee5df1294b3ff7d410203010001028180113bcade2eea3dc2bbf63b6fd7c5c7866fd8755cb024718b806997d3d06317ee051983034b326e202af501a7447ad813175606b1ba87607e306b139a88836e9d31b0c09c03f251b1cac7502c75b7838bebcead469f3793316fb0135eab6cf14e4d557193305af0865e390048afa6bb70ea95d66064341fe81ffb0803882710c9024100f674412e5fc901a0c0f302b8f1189c120300e9e43fd7f088dc6cf1e4e71d06dd0f043138028784e6e8530930c5a0e37413a1153020ae3c97ec2f9f56e49bb697024100b8a30760bca44e59951e2318673e2644a07c75cda723f7cafcc57d8ca6091f44677dcf0e763a74a7f018325f4fe1fdea4f0d419abdd37201ec6c7d35e81a7de70240026de6ee94172122fa83eab4f3fb701e65552ef9070013e8e57355771228211730bcb3d77f7ee27a7c33c5556bbd4840aea8d9d29d9fda0d0d50f2db2269be85024100912aa4d75b19f7fc18f0ebf8f8db756d099d3165f2dea5a97419b63f8a1fabf4b545c63101a5ae2887ee0e54bfd2d8378bd5c959e79bc28c5e9d61c8ab6780cb024100c3da732a651e97c1e8495b21fc7f6228a137196dc67a93c452c33b482ecc4b2903017e7b988963ccbef3b8c73909b42188ac4c84054f35d0e5d0f1f500579452";
		String openid = "";
		String operate = "cancel";
		if (!StringUtils.isBlank(message)) {
			try {
				String[] slist = message.split(",");
				operate = slist[0]; // ok
				String encodestring = slist[1];
				openid = instance.decodeByPrivatekey(privateKeyString, encodestring);// 解密
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// System.out.println("WechatWebSocketLinkManage.onMessage(解密后openid=)"+openid);

		if (!sid.equals("sid") && operate.equals("ok")) {
			// 移动端发出的msg
			// 确认，可以登录
			// System.out.println("WechatWebSocketLinkManage.onMessage(确认，可以登录)");

			// Session session2 = updateWsSession(sid, openid);
			// sendJsonMessage(session2, "gotoLoginUseWechatInfo");

			for (int i = 0; i < kvList.size(); i++) {
				if (kvList.get(i).getJsid().equals(sid)) {
					kvList.get(i).setOpenid(openid);
					sendJsonMessage((Session) kvList.get(i).getWs(), "gotoLoginUseWechatInfo");

					// System.out.println(
					// "WechatWebSocketLinkManage.onMessage(-------------------after-------------------)");
					displayList();
					return;
				}
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
		// System.out.println("WechatWebSocketLinkManage.onClose()jsessionid=" +
		// jsessionid + ",sid=" + sid);
//		System.out.println("WechatWebSocketLinkManage.onClose(-------------------before-------------------)");
//		displayList();
//		for (int i = 0; i < kvList.size(); i++) {
//			if (kvList.get(i).getJsid().equals(jsessionid)) {
//				kvList.remove(i);
//				System.out.println("WechatWebSocketLinkManage.onClose()已经移除jsessionid=" + jsessionid);
//			}
//		}
//		System.out.println("WechatWebSocketLinkManage.onClose(-------------------after-------------------)");
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
