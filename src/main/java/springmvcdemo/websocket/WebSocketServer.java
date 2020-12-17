package springmvcdemo.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import sun.rmi.runtime.Log;

@ServerEndpoint(value = "/websocket/{clientId}")
public class WebSocketServer {

	// private final Log log = new Log(WebSocketServer.class);
	private Session session;
	private String clientId;
	private static Map<String, WebSocketServer> clients = new ConcurrentHashMap<String, WebSocketServer>();

	// 连接时执行
	@OnOpen
	public void onOpen(@PathParam("clientId") String clientId, Session session) throws IOException {
		this.session = session;
		this.clientId = clientId;
		clients.put(clientId, this);
		System.out.println("新连接：" + clientId);
	}

	// 关闭时执行
	@OnClose
	public void onClose(@PathParam("clientId") String clientId, Session session) {
		clients.remove(clientId);
		System.out.println("连接 " + clientId + " 关闭");
	}

	// 收到消息时执行
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println("收到用户的消息: " + message);
		//this.sendMessage(session, "Server:" + message);
		WebSocketServer.sendMessageAll("JAVA Server:" + message);
		/*
		 * if("getMpDefsAndRtDatas".equals(message)){ String msg =
		 * UnityServlet.getInstance().getAllMpDefsAndRtDatas();
		 * this.sendMessage(session, msg); }
		 */
	}

	// 连接错误时执行
	@OnError
	public void onError(@PathParam("clientId") String clientId, Throwable error, Session session) {
		System.out.println("用户id为：" + clientId + "的连接发送错误");
		error.printStackTrace();
	}

	/**
	 * 发送消息给某个客户端
	 * 
	 * @param message
	 * @param To
	 * @throws IOException
	 */
	public static void sendMessageTo(String message, String To) throws IOException {
		for (WebSocketServer item : clients.values()) {
			if (item.clientId.equals(To))
				item.session.getAsyncRemote().sendText(message);
		}
	}

	/**
	 * 发送消息给某些客户端
	 * 
	 * @param message
	 * @param To
	 * @throws IOException
	 */
	public static void sendMessageToSomeone(String message, String To) throws IOException {
		for (WebSocketServer item : clients.values()) {
			if (item.clientId.startsWith(To))
				item.session.getAsyncRemote().sendText(message);
		}
	}

	/**
	 * 发送消息给所有客户端
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static void sendMessageAll(String message) throws IOException {
		for (WebSocketServer item : clients.values()) {
			item.session.getAsyncRemote().sendText(message);
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param session
	 * @param message
	 * @throws IOException
	 */
	private void sendMessage(Session session, String message) throws IOException {
		session.getBasicRemote().sendText(message);
	}

}
