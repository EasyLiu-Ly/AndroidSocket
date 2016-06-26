import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyServerSocket {
	public static void main(String[] args) {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress().toString();
			System.out.println(ip);//��ȡ����ip
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//���������������߳�
		new ServerListener().start();
	}
}
