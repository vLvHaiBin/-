package com.example.fastNews.activity;

import java.util.ArrayList;

import com.example.fastNews.R;
import com.example.fastNews.Utils.mStringArray;
import com.example.tuodong.DragGridView;
import com.example.tuodong.GridViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import cn.sharesdk.tencent.qq.h;
import cn.sharesdk.wechat.utils.m;

public class Activity_add_news extends Activity {
	private DragGridView add_news;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		initview();
		initData();
	}

	private void initview() {

		setContentView(R.layout.activity_news_add);
		add_news = (DragGridView) findViewById(R.id.drag_add_news);

		Intent intent = getIntent();
		final String[] stringArrayExtra = intent.getStringArrayExtra("newslist");

		new Thread() {
			@Override
			public void run() {
				super.run();
				ArrayList<String> newslist = new ArrayList<String>();
				for (String news : stringArrayExtra) {
					newslist.add(news);
				}
				Message msg = handler.obtainMessage();
				msg.obj = newslist;
				handler.sendMessage(msg);
			}
		}.start();
		;

	}

	private void initData() {

	}

	public Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			@SuppressWarnings("unchecked")
			GridViewAdapter adapter = new GridViewAdapter(Activity_add_news.this, (ArrayList<String>) msg.obj);
			@SuppressWarnings("unchecked")
			ArrayList<String> list = (ArrayList<String>) msg.obj;
			int size = list.size();
			Log.e("sss", "ddd" + size);
			String aas[] = new String[size];
			int i = 0;
			for (String ll : list) {

				aas[i] = ll;
				Log.e("sssssssssss", aas[i]);

				i++;
			}

			mStringArray.getInstance().getString(aas);
			add_news.setAdapter(adapter);
		};
	};

}
