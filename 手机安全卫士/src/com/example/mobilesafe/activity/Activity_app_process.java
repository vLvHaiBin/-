package com.example.mobilesafe.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.SystemInfoUtils;
import com.example.mobilesafe.Utils.TaskInfoUtils;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.ViewGroup.Titlebar;
import com.example.mobilesafe.bean.Bean_app_process;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author like 进程管理
 */
public class Activity_app_process extends Activity {

	private TextView tv_process_number;
	private Bean_app_process bean_apk_info;
	private TextView tv_process_surplus;
	private TextView tv_process_show;
	private ListView lv_process;
	private ArrayList<Bean_app_process> systemApp;
	private ArrayList<Bean_app_process> userApp;
	private FrameLayout fl_process_loading;
	private boolean isAll = false;
	private Button btn_process_all_chioce;
	private Button btn_kill_process;
	ActivityManager activityManager;
	private long availMem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		initView();
		initData();
	}

	private void initView() {
		activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		setContentView(R.layout.activity_app_process);
		tv_process_number = (TextView) findViewById(R.id.tv_process_number);
		tv_process_surplus = (TextView) findViewById(R.id.tv_process_surplus);
		tv_process_show = (TextView) findViewById(R.id.tv_process_show);
		lv_process = (ListView) findViewById(R.id.lv_process);
		fl_process_loading = (FrameLayout) findViewById(R.id.fl_process_jiazai);
		btn_process_all_chioce = (Button) findViewById(R.id.btn_process_all_chioce);
		btn_kill_process = (Button) findViewById(R.id.btn_kill_process);
		Titlebar tl = (Titlebar) findViewById(R.id.title_process_manager);
		tl.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});

	}

	/**
	 * 
	 */
	private void initData() {

		totalMem = SystemInfoUtils.getProcessCount(this);
		availMem = SystemInfoUtils.getAvailMem(this);
		tv_process_number.setText("当前运行程序数:" + totalMem);
		tv_process_surplus.setText("剩余运行内存:" + Formatter.formatFileSize(this, availMem));

		getBean_app_process();
	}

	/**
	 * 获取process bean
	 */
	private void getBean_app_process() {
		new Thread(new Runnable() {
			public void run() {
				List<Bean_app_process> taskInfos = TaskInfoUtils.getTaskInfos(Activity_app_process.this);
				Message msg = handler.obtainMessage();
				msg.obj = taskInfos;
				handler.sendMessage(msg);
			}
		}).start();
	}

	Handler handler = new Handler() {
		private ArrayList<Bean_app_process> app;
		Myadapter myadapter;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			app = null;

			fl_process_loading.setVisibility(View.GONE);
			app = (ArrayList<Bean_app_process>) msg.obj;
			myadapter = new Myadapter();
			userApp = new ArrayList<Bean_app_process>();
			systemApp = new ArrayList<Bean_app_process>();

			// 分别用户和系统app
			for (Bean_app_process bean_apk_info : app) {
				if (bean_apk_info.isUser) {// 将用户和系统应用装入不同的集合
					userApp.add(bean_apk_info);
				} else {
					systemApp.add(bean_apk_info);
				}
			}
			lv_process.setAdapter(myadapter);

			// 全选
			selectAll();
			//

			listonclik();
			listonscroll();
			// 杀死进程
			btn_kill_process.setOnClickListener(new OnClickListener() {
				int total = 0;
				int totalsize = 0;

				@SuppressLint("ServiceCast")
				@Override
				public void onClick(View v) {

					ArrayList<Bean_app_process> systemKill = new ArrayList<Bean_app_process>();
					ArrayList<Bean_app_process> userKill = new ArrayList<Bean_app_process>();

					for (Bean_app_process bp : systemApp) {
						if (bp.isIscheck()) {
							systemKill.add(bp);
							total++;
							totalsize += Integer.valueOf(bp.getPackageSize());
						}
					}

					for (Bean_app_process bp : userApp) {
						if (bp.isIscheck()) {
							total++;
							userKill.add(bp);
							totalsize += Integer.valueOf(bp.getPackageSize());
						}
					}

					for (Bean_app_process bp : systemKill) {
						systemApp.remove(bp);

						activityManager.killBackgroundProcesses(bp.getPackageName());
					}

					for (Bean_app_process bp : userKill) {
						userApp.remove(bp);
						activityManager.killBackgroundProcesses(bp.getPackageName());
					}

					if (userKill == null && systemKill == null) {
						return;
					}
					if (isAll) {
						btn_process_all_chioce.setText("全选");
					}

					String ts = Formatter.formatFileSize(Activity_app_process.this, totalsize);

					Utils.toast(Activity_app_process.this, "杀死" + total + "个进程，" + "释放了" + ts + "内存");
					tv_process_number.setText("当前运行程序数:" + (totalMem - total));
					tv_process_surplus.setText(
							"剩余运行内存:" + Formatter.formatFileSize(Activity_app_process.this, availMem + totalsize));
					myadapter.notifyDataSetChanged();
				}
			});
		}

		/**
		 * 
		 */
		private void selectAll() {
			btn_process_all_chioce.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (isAll) {
						for (Bean_app_process ba : app) {
							ba.setIscheck(false);
							btn_process_all_chioce.setText("全选");
						}

					} else {

						for (Bean_app_process ba : app) {
							ba.setIscheck(true);
							btn_process_all_chioce.setText("取消");
						}
					}
					isAll = !isAll;

					myadapter.notifyDataSetChanged();
				}

			});
		}
	};
	private int totalMem;

	protected void listonclik() {
		lv_process.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Object itemAtPosition = lv_process.getItemAtPosition(position);
				if (itemAtPosition != null) {
					Bean_app_process bap = (Bean_app_process) itemAtPosition;
					ViewHolder viewHolder = (ViewHolder) view.getTag();
					boolean is = bap.isIscheck();
					if (is) {
						bap.setIscheck(false);
						viewHolder.iv_process_is_check.setEnabled(false);
					} else {
						bap.setIscheck(true);
						viewHolder.iv_process_is_check.setEnabled(true);
					}

				}
			}
		});
	}

	private void listonscroll() {
		lv_process.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				if (firstVisibleItem > userApp.size()) {
					/* tv_process_show.setVisibility(View.VISIBLE); */
					tv_process_show.setText("系统应用(" + systemApp.size() + ")");
				} else {
					/* tv_process_show.setVisibility(View.VISIBLE); */
					tv_process_show.setText("用户应用(" + userApp.size() + ")");
				}
			}
		});
	}

	class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			return userApp.size() + systemApp.size() + 1;
		}

		@Override
		public Object getItem(int position) {
			Bean_app_process mbean_apk_info = null;
			if (position == userApp.size()) {
				return null;
			} else if (position < userApp.size()) {
				mbean_apk_info = userApp.get(position);
			} else if (position > userApp.size()) {
				mbean_apk_info = systemApp.get(position - (userApp.size() + 1));
			}
			return mbean_apk_info;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewholder;
			if (position == (userApp.size())) {

				TextView tv_system = new TextView(Activity_app_process.this);
				tv_system.setText("系统应用(" + systemApp.size() + ")");
				tv_system.setBackgroundColor(Color.GRAY);
				return tv_system;

			} else if (position < userApp.size()) {
				bean_apk_info = userApp.get(position);
			} else if (position > userApp.size()) {
				bean_apk_info = systemApp.get(position - (userApp.size() + 1));
			}

			if (convertView != null && convertView instanceof RelativeLayout) {
				viewholder = (ViewHolder) convertView.getTag();
			} else {
				viewholder = new ViewHolder();
				convertView = View.inflate(Activity_app_process.this, R.layout.adapter_process_manager, null);
				viewholder.iv_apk_icon = (ImageView) convertView.findViewById(R.id.iv_process_apk_icon);
				viewholder.tv_apk_name = (TextView) convertView.findViewById(R.id.tv_process_apk_name);
				viewholder.tv_apk_size = (TextView) convertView.findViewById(R.id.tv_process_apk_size);
				viewholder.iv_process_is_check = (ImageView) convertView.findViewById(R.id.iv_process_is_check);
				viewholder.rl_process_adapter = (RelativeLayout) convertView.findViewById(R.id.rl_process_adapter);

				convertView.setTag(viewholder);

			}

			boolean ischeck = bean_apk_info.isIscheck();

			if (ischeck) {
				viewholder.iv_process_is_check.setEnabled(true);
			} else {
				viewholder.iv_process_is_check.setEnabled(false);
			}
			viewholder.iv_apk_icon.setBackground(bean_apk_info.getPackageIcon());
			viewholder.tv_apk_name.setText(bean_apk_info.getPackageName().toString());
			viewholder.tv_apk_size.setText("占用内存:" + Formatter.formatFileSize(Activity_app_process.this,
					Integer.valueOf(bean_apk_info.getPackageSize())));
			return convertView;
		}

	}

	class ViewHolder {
		public RelativeLayout rl_process_adapter;
		public ImageView iv_process_is_check;
		public ImageView iv_apk_icon;
		public TextView tv_apk_name;
		public TextView tv_apk_size;

	}

}
