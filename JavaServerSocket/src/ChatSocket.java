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
    //��������
	public void out(String out) {
		try {
			bw.write(out + "\n");// ����Ҫ�ӻ��з���,��Ȼ���ݷ�����ȥ
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
				System.out.println("�ͻ��˷������ݣ�"+line);
				//�����ݷ�������Ŀͻ���
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
