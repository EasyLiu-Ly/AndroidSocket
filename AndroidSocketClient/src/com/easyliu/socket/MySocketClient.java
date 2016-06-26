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

	// 建立连接
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
					publishProgress("@Sucess"); // 链接成功
				} catch (IOException e) {
					publishProgress("@Failure"); // 链接失败
					e.printStackTrace();
				}
				// 监听服务器发来的数据
				try {
					String line;
					while ((line = reader.readLine()) != null) {//死循环
						publishProgress(line);
					}
				} catch (IOException e) {
					Toast.makeText(MySocketClient.this, "接收数据失败！",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(String... values) {
				if (values[0].equals("@Sucess")) {
					Toast.makeText(MySocketClient.this, "建立连接成功！",
							Toast.LENGTH_SHORT).show();
					listAdapter.add("建立连接成功！");
				} else if (values[0].equals("@Failure")) {
					Toast.makeText(MySocketClient.this, "建立连接失败！",
							Toast.LENGTH_SHORT).show();
					listAdapter.add("建立连接失败！");
				} else {
					Toast.makeText(MySocketClient.this, "收到数据！",
							Toast.LENGTH_SHORT).show();
					listAdapter.add("别人说：" + values[0]);
				}
			}
		}.execute();
	}

	public void send() {
		try {
			String sendData = send.getText().toString();
			writer.write(sendData + "\n");//必须加上换行
			writer.flush();
			listAdapter.add("我说：" + sendData);
			send.setText("");
		} catch (IOException e) {
			Toast.makeText(MySocketClient.this, "发送失败！", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}
}
