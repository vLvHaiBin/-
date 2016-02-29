package com.example.fastNews.activity;

import com.example.fastNews.R;
import com.example.fastNews.Utils.toastUtils;
import com.example.fastNews.fragment.contentFragment;
import com.example.fastNews.fragment.slidingFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.umeng.common.message.Log;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.umeng.socialize.PlatformConfig;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

public class MainActivity extends SlidingFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
		// 微信 appid appsecret
		PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
		// 新浪微博 appkey appsecret
		PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
		// QQ和Qzone appid appkey
		PlatformConfig.setAlipay("2015111700822536");
		// 支付宝 appid
		PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
		// 易信 appkey

		PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
		// Twitter appid appkey
		PlatformConfig.setPinterest("1439206");
		// Pinterest appid
		PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
		// 来往 appid appkey
		PushAgent mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		PushAgent.getInstance(this).onAppStart();
		/*String device_token = UmengRegistrar.getRegistrationId(this);*/
		/*mPushAgent.enable(new IUmengRegisterCallback() {

			@Override
			public void onRegistered(String registrationId) {
				// onRegistered方法的参数registrationId即是device_token
				Log.d("device_token", registrationId);
				toastUtils.toast(MainActivity.this, registrationId);
			}
		});*/
		setContentView(R.layout.activity_content_layout);
		setBehindContentView(R.layout.sliding_menu);
		SlidingMenu menu = getSlidingMenu();
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffset(200);

		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

		FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
		beginTransaction.replace(R.id.fl_content, new contentFragment());// 使用fragment
		beginTransaction.replace(R.id.Sliding_menu, new slidingFragment());
		// 来替换activity
		beginTransaction.commit();
	}

}
