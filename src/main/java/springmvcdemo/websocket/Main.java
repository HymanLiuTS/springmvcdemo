package springmvcdemo.websocket;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class Main {
	//private static String uri = "ws://localhost:8080/springmvcdemo/websocket/broadcast";
	private static String uri = "ws://localhost:8888/Laputa";
	public static Session session;

	public void start() {
		WebSocketContainer container = null;
		try {
			container = ContainerProvider.getWebSocketContainer();
		} catch (Exception ex) {
			System.out.println("error" + ex);
		}
		try {
			URI r = URI.create(uri);
			session = container.connectToServer(MyWebSocketClient.class, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
