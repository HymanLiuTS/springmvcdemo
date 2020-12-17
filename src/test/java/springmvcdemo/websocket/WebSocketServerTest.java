package springmvcdemo.websocket;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import org.junit.Test;

public class WebSocketServerTest {

	@Test
	public void client() {
		try {
			Main myClient = new Main();
			myClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
