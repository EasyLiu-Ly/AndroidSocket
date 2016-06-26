import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ChatSocket extends Thread {
	Socket socket;
	BufferedWriter bw;
	BufferedReader br;

	public ChatSocket(Socket s) {
		this.socket = s;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(), "utf-8"));
			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    //发送数据
	public void out(String out) {
		try {
			bw.write(out + "\n");// 必须要加换行符号,不然数据发不出去
			bw.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("客户端发来数据："+line);
				//把数据发给其余的客户端
				ChatManager.getChatManager().publish(this, line);
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
