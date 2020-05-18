package rip.vexus.lobby.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerManager {

	public static boolean isOnline(ServerTypes type) {
		try {
			Socket s = new Socket("127.0.0.1", type.getPort());
			s.close();
			return true;
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	
	public static boolean isWhitelisted(ServerTypes type) {
		return false;
	}
}
