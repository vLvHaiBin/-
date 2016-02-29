package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;

import com.example.mobilesafe.Utils.ModifyOffset;

import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.adapter.MyMenuGridViewAdapter;
import com.example.mobilesafe.server.AddressServer;
import com.example.mobilesafe.server.WatchDogService;
import com.example.mobilesafe.server.rocketserver;
import com.lidroid.xutils.HttpUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class menu_activity extends Activity {

	private TextView tv_score;
	private int width;
	private int height;

	public int[] image = new int[] { R.drawable.rubbish_clean, R.drawable.phone_accelarate, R.drawable.flow_management,
			R.drawable.harass_intercept, R.drawable.permision_management,
			R.drawable.rightsmanagement_security_permission, R.drawable.phone_clean, R.drawable.virus_cleaning };

	public String[] text = new String[] { "��������", "���̹���", "��������", "ɧ������", "�������", "�ֻ�����", "�߼�����", "������ɱ" };

	private GridView gv_menu_content;

	private ImageView iv_menu;

	private PopupWindow mPopupWindow;

	private View v;
	private EditText et_password;
	private EditText et_password_confirm;
	private Button btn_confirm;
	private Button btn_cancle;
	private AlertDialog create;
	private String password;
	private String confirm_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_activity);
		initView();
		initData();
		// startService(new Intent(this, rocketserver.class));
		startService(new Intent(this, WatchDogService.class));
	}

	public void initView() {
		String isUpdate = Utils.sharprefrence_get(this, "auto_update", "-1");
		if (isUpdate.equals("1")) {
			Utils.getVersionCode(this);// �����ȡ�İ汾�źͷ�������ȡ��С���͸��£�
			HttpUtils httpUtils = new HttpUtils();
			Utils.toast(this, "���ӷ�����ʧ�ܣ�����ʧ��");
			/* httpUtils.download("���ص�url", "�����ļ���λ��", callback); */
		}
		iv_menu = (ImageView) findViewById(R.id.iv_menu);
		v = View.inflate(menu_activity.this, R.layout.popwindows, null);
		TextView tv_setting = (TextView) v.findViewById(R.id.tv_1);

		onclickevent(tv_setting);// ����¼�
		tv_score = (TextView) findViewById(R.id.tv_score);
		gv_menu_content = (GridView) findViewById(R.id.gv_content_gridview);
		gv_menu_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:// ��������
					startActivity(new Intent(menu_activity.this, Activity_Clean.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
					break;
				case 1:// ���̹���
					startActivity(new Intent(menu_activity.this, Activity_app_process.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
					break;
				case 2:// ��������

					break;
				case 3:// ɧ������
					startActivity(new Intent(menu_activity.this, harassintercept.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);

					break;
				case 4:// �������
					startActivity(new Intent(menu_activity.this, Activity_sw_manage.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
					break;
				case 5:// �ֻ�����
					disposepassword();

					break;
				case 6:// �߼�����
					startActivity(new Intent(menu_activity.this, hightools.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);

					break;
				case 7:// ������ɱ
					startActivity(new Intent(menu_activity.this, Activity_virus_find.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);

					break;
				}
			}

			/**
			 * �Է���ҳ������������¼�����
			 */
			private void disposepassword() {

				String getpassword = Utils.sharprefrence_get(menu_activity.this, "password", "-1");
				if (TextUtils.equals(getpassword, "-1")) {
					showsetpassworddialog();
					passwordsetting();
				} else {
					showentersettingdialog();
				}
			}
		});

	}

	/**
	 * �����������
	 * 
	 */
	protected void showentersettingdialog() {

		AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
		AlertDialog mcreate = mbuilder.create();
		View mv = View.inflate(menu_activity.this, R.layout.dialog_guard_enter_password, null);
		EditText et_mpassword = (EditText) mv.findViewById(R.id.et_mpassword);
		Button btn_mconfirm = (Button) mv.findViewById(R.id.btn_maffirm);
		Button btn_mcancle = (Button) mv.findViewById(R.id.btn_mcancle);
		mcreate.setView(mv);
		mcreate.show();

		onclick(et_mpassword, btn_mconfirm, btn_mcancle, mcreate);
	}

	/**
	 * �Խ����������ĵ���¼�����
	 * 
	 * @param et_mpassword
	 * @param btn_mconfirm
	 * @param btn_mcancle
	 * @param mcreate
	 */
	private void onclick(final EditText et_mpassword, Button btn_mconfirm, Button btn_mcancle,
			final AlertDialog mcreate) {
		btn_mcancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mcreate.dismiss();
			}
		});

		btn_mconfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String getpassword = Utils.MD5_encode(et_mpassword.getText().toString());
				String savepassword = Utils.sharprefrence_get(menu_activity.this, "password", "-1");
				if (TextUtils.equals(getpassword, savepassword)) {
					mcreate.dismiss();
					startActivity(new Intent(menu_activity.this, guard_activity.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);

				} else {
					Utils.toast(menu_activity.this, "���벻��ȷ������������");
				}
			}
		});
	}

	/**
	 * ���������ȷ���¼�
	 */
	protected void passwordsetting() {

		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				create.dismiss();
			}
		});

		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				password = et_password.getText().toString();
				confirm_password = et_password_confirm.getText().toString();
				if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirm_password)) {
					if (TextUtils.equals(password, confirm_password)) {
						Utils.sharprefrence_save(menu_activity.this, "password", Utils.MD5_encode(confirm_password));
						startActivity(new Intent(menu_activity.this, guard_activity.class));

						Utils.toast(menu_activity.this, "��ȷ");
						create.dismiss();
					} else {
						Utils.toast(menu_activity.this, "�������벻һ�£�����������");
					}
				} else {
					Utils.toast(menu_activity.this, "���벻��Ϊ��");
				}
			}
		});
	}

	/**
	 * �ֻ������״ν���ҳ����������
	 */
	protected void showsetpassworddialog() {// �����Ӱ����ʾ
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		create = builder.create();
		View v = View.inflate(menu_activity.this, R.layout.dialog_guard_password, null);
		et_password = (EditText) v.findViewById(R.id.et_password);
		et_password_confirm = (EditText) v.findViewById(R.id.et_password_affirm);
		btn_confirm = (Button) v.findViewById(R.id.btn_affirm);
		btn_cancle = (Button) v.findViewById(R.id.btn_cancle);
		create.setView(v);
		create.show();
	}

	/**
	 * @param tv_setting
	 *            pop����¼�
	 */
	private void onclickevent(TextView tv_setting) {
		tv_setting.setOnClickListener(new OnClickListener() {// popwindows�ĵ���¼�

			@Override
			public void onClick(View v) {
				startActivity(new Intent(menu_activity.this, settingActivity.class));
				mPopupWindow.dismiss();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		// ͼ��ĵ���¼�
		iv_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopWindows();

			}
		});
	}

	/**
	 * popwindows
	 */
	@SuppressWarnings("deprecation")
	protected void showPopWindows() {

		if (mPopupWindow == null) {
			mPopupWindow = new PopupWindow(v, v.getLayoutParams().WRAP_CONTENT, v.getLayoutParams().WRAP_CONTENT);
		}
		mPopupWindow.setFocusable(true);// pop����Ŀؼ���û�н���ģ���Ҫ���ã�
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.mz_card_bg_light_normal));
		mPopupWindow.showAsDropDown(iv_menu, -200, 0);

	}

	public void initData() {

		MyMenuGridViewAdapter adapter = new MyMenuGridViewAdapter(image, text, this);
		gv_menu_content.setAdapter(adapter);
		// �ж��Զ������Ƿ���
		String state = Utils.sharprefrence_get(this, "�Զ���������", "-1");
		if (TextUtils.equals(state, "1")) {

		}
		guard();// ����ҳ�洦��
		place_show();// ����������
	}

	/**
	 * ����ҳ�洦��
	 * 
	 */
	private void guard() {

		String sim = Utils.sharprefrence_get(menu_activity.this, "sim", null);
		if (TextUtils.isEmpty(sim)) {

			TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String simSerialNumber = manager.getSimSerialNumber();
			Utils.sharprefrence_save(menu_activity.this, "sim", simSerialNumber);
		} else {
			Utils.toast(menu_activity.this, "sim���Ѱ�");
		}

		/*
		 * String guardstate = Utils.sharprefrence_get(this, "guard", "-1");
		 * if(guardstate.equals("1")){
		 * 
		 * 
		 * }
		 */
	}

	/**
	 * ������ҳ�洦��
	 * 
	 */
	private void place_show() {

		String place_setting_state = Utils.sharprefrence_get(menu_activity.this, "place_show", "-1");
		if (place_setting_state.equals("1")) {
			startService(new Intent(this, AddressServer.class));
			Utils.toast(menu_activity.this, "�����ط�����");
		} else {

		}

	}

}
