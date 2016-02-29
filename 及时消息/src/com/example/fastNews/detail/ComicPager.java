package com.example.fastNews.detail;

import com.example.fastNews.R;
import com.example.fastNews.activity.ComicActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ComicPager extends BaseDetailpager implements OnClickListener {

	public ComicPager(Activity activity) {
		super(activity);
	}

	@Override
	protected void initView() {
		v = View.inflate(activity, R.layout.comicfirst, null);
		v.findViewById(R.id.iv_shaonian).setOnClickListener(this);
		v.findViewById(R.id.iv_shaonv).setOnClickListener(this);
		v.findViewById(R.id.iv_danmei).setOnClickListener(this);
		v.findViewById(R.id.iv_qingnian).setOnClickListener(this);

	}

	@Override
	public void initdata() {

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(activity, ComicActivity.class);
		switch (v.getId()) {
		case R.id.iv_shaonian:// ��������
			intent.putExtra("name", "��������");
			activity.startActivity(intent);
			break;
		case R.id.iv_qingnian:// ��������
			intent.putExtra("name", "��������");
			activity.startActivity(intent);
			break;
		case R.id.iv_shaonv:// ��Ů����
			intent.putExtra("name", "��Ů����");
			activity.startActivity(intent);
			break;
		case R.id.iv_danmei:// ��������
			intent.putExtra("name", "��������");
			activity.startActivity(intent);
			break;
		}
	}
}
