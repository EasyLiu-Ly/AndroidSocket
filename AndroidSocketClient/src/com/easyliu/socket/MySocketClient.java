package com.easyliu.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MySocketClient extends ActionBarActivity {
	private EditText ip;
	private EditText send;
	private ListView listView;
	private ArrayAdapter<String> listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ip = (EditText) findViewById(R.id.editTextip);
		send = (EditText) findViewById(R.id.editTextSend);
		listView = (ListView) findViewById(R.id.listViewData);
		listAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
		listView.setAdapter(listAdapter);
		findViewById(R.id.connect).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				connect();
			}
		});
		findViewById(R.id.send).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				send();
			}
		});
	}

	Socket socket = null;
	BufferedWriter writer = null;
	BufferedReader reader = null;

	// ��������
	public void connect() {
		new AsyncTask<Void, String, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					socket = new Socket(ip.getText().toString(), 12345);
					writer = new BufferedWriter(new OutputStreamWriter(
							socket.getOutputStream(),"utf-8"));
					reader = new BufferedReader(new InputStreamReader(
							socket.getInputStream(),"utf-8"));
					publishProgress("@Sucess"); // ���ӳɹ�
				} catch (IOException e) {
					publishProgress("@Failure"); // ����ʧ��
					e.printStackTrace();
				}
				// ��������������������
				try {
					String line;
					while ((line = reader.readLine()) != null) {//��ѭ��
						publishProgress(line);
					}
				} catch (IOException e) {
					Toast.makeText(MySocketClient.this, "��������ʧ�ܣ�",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(String... values) {
				if (values[0].equals("@Sucess")) {
					Toast.makeText(MySocketClient.this, "�������ӳɹ���",
							Toast.LENGTH_SHORT).show();
					listAdapter.add("�������ӳɹ���");
				} else if (values[0].equals("@Failure")) {
					Toast.makeText(MySocketClient.this, "��������ʧ�ܣ�",
							Toast.LENGTH_SHORT).show();
					listAdapter.add("��������ʧ�ܣ�");
				} else {
					Toast.makeText(MySocketClient.this, "�յ����ݣ�",
							Toast.LENGTH_SHORT).show();
					listAdapter.add("����˵��" + values[0]);
				}
			}
		}.execute();
	}

	public void send() {
		try {
			String sendData = send.getText().toString();
			writer.write(sendData + "\n");//������ϻ���
			writer.flush();
			listAdapter.add("��˵��" + sendData);
			send.setText("");
		} catch (IOException e) {
			Toast.makeText(MySocketClient.this, "����ʧ�ܣ�", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}
}
