import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyServerSocket {
	public static void main(String[] args) {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress().toString();
			System.out.println(ip);//获取本机ip
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//启动服务器监听线程
		new ServerListener().start();
	}
}
