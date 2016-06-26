import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerListener extends Thread {
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			// 循环的监听
			while (true) {
				Socket socket = serverSocket.accept();// 阻塞
				JOptionPane.showMessageDialog(null, "有客户端连接到本机的12345端口！");
				// 将socket传给新的线程
				ChatSocket cs = new ChatSocket(socket);
				cs.start();
				//把socket加入ChatManager
				ChatManager.getChatManager().add(cs);
				System.out.println("服务器反馈：有客户端连接到服务器！");
				ChatManager.getChatManager().publishAll("服务器反馈：有客户端连接到服务器！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
