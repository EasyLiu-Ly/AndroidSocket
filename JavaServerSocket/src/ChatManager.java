import java.util.Vector;

//�൥��������
public class ChatManager {
	Vector<ChatSocket> vector = new Vector<ChatSocket>();

	private ChatManager() {
	}
   
	private static class ChatManagerHolder {
		private static final ChatManager cm = new ChatManager();
	}

	public static ChatManager getChatManager() {
		return ChatManagerHolder.cm;
	}

	public void add(ChatSocket cs) {
		vector.add(cs);
	}

	public void publish(ChatSocket cs, String out) {
		for (int i = 0; i < vector.size(); i++) {
			ChatSocket csChatSocket = vector.get(i);
			// �������Լ���ֻ�Ƿ�������
			if (!cs.equals(csChatSocket)) {
				csChatSocket.out(out);
			}
		}
	}
   //�������е���
	public void publishAll(String out) {
		for (int i = 0; i < vector.size(); i++) {
			ChatSocket csChatSocket = vector.get(i);
			csChatSocket.out(out);
		}
	}

}
