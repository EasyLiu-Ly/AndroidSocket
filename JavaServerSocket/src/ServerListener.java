import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerListener extends Thread {
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			// ѭ���ļ���
			while (true) {
				Socket socket = serverSocket.accept();// ����
				JOptionPane.showMessageDialog(null, "�пͻ������ӵ�������12345�˿ڣ�");
				// ��socket�����µ��߳�
				ChatSocket cs = new ChatSocket(socket);
				cs.start();
				//��socket����ChatManager
				ChatManager.getChatManager().add(cs);
				System.out.println("�������������пͻ������ӵ���������");
				ChatManager.getChatManager().publishAll("�������������пͻ������ӵ���������");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
