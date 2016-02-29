package com.example.fastNews.activity;

import com.example.fastNews.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class HotNewsContentActivity extends Activity {

	private ImageView iv_back;
	private ImageView iv_share;
	private WebView web;
	private String mtitle;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
		setContentView(R.layout.inputactivtity);
		iv_back = (ImageView) findViewById(R.id.iv_hot_back);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		iv_share.setOnClickListener(new OnClickListener() {

			private ShareBoardlistener shareBoardlistener;
			private UMShareListener umShareListener;

			@Override
			public void onClick(View v) {
				final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] { SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
						SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.DOUBAN };
				new ShareAction(HotNewsContentActivity.this).setDisplayList(displaylist).withText("呵呵")
						.withTitle(mtitle).withTargetUrl(url)
						/*.withMedia(null)*/
						.setListenerList(umShareListener, umShareListener)
						.setShareboardclickCallback(shareBoardlistener).open();

				shareBoardlistener = new ShareBoardlistener() {

					@Override
					public void onclick(SnsPlatform arg0, SHARE_MEDIA arg1) {

					}
				};
				umShareListener = new UMShareListener() {
					@Override
					public void onResult(SHARE_MEDIA platform) {
						Toast.makeText(HotNewsContentActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(SHARE_MEDIA platform, Throwable t) {
						Toast.makeText(HotNewsContentActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
						Toast.makeText(HotNewsContentActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
					}
				};
			}

		});
		web = (WebView) findViewById(R.id.web_hotnews);
		WebSettings settings = web.getSettings();
		settings.setSupportZoom(true);
		settings.setJavaScriptEnabled(true);
		settings.setDisplayZoomControls(true);
		settings.setUseWideViewPort(true);// 设置
		settings.setBuiltInZoomControls(true);// 设置缩放

	}

	private void initData() {
		url = getIntent().getStringExtra("url");
		web.loadUrl(url);
		web.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
			}

		});

		web.setWebChromeClient(new WebChromeClient() {

		
			

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				mtitle = title;
				super.onReceivedTitle(view, title);
			}

			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				super.onReceivedIcon(view, icon);
			}

		});

	}

	private AlertDialog create;

	public void ShowDil() {
		AlertDialog.Builder builder = new AlertDialog.Builder(HotNewsContentActivity.this);
		create = builder.create();
		View v = View.inflate(HotNewsContentActivity.this, R.layout.load_framlayout, null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		create.setView(v, 0, 0, getWindowManager().getDefaultDisplay().getWidth(),
				getWindowManager().getDefaultDisplay().getHeight());
		create.show();
	}

	private String url;
}
