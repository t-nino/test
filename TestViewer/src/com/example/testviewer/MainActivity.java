package com.example.testviewer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	AsyncHttpRequest task = new AsyncHttpRequest(this);
	ArrayList<String> imageList;
	TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageList = new ArrayList<String>();
		Button button_start = (Button) findViewById(R.id.button1);
		Button button_show = (Button) findViewById(R.id.button2);

		text = (TextView)findViewById(R.id.text_main);

		button_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				task.execute();
			}
		});

		button_show.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				try {
					imageList = task.get();
					StringBuffer sb = new StringBuffer();
					Iterator<String> ite = imageList.iterator();
					while(ite.hasNext()){
						sb.append(ite.next());
						sb.append("\n");
					}

					text.setText(sb.toString());
					//text.setText("result");
					//System.out.println("press");

				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
