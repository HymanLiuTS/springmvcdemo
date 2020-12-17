package springmvcdemo.websocket;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

@ClientEndpoint
public class MyWebSocketClient {

	@OnOpen
	public void onOpen(Session session) {

	}

	@OnMessage
	public void onMessage(String message) {
		System.out.println("Client onMessage: " + message);
	}

	@OnClose
	public void onClose() {

	}
}
