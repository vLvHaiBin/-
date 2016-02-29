package com.example.mobilesafe.server;

import com.example.mobilesafe.Dao.AddressDAO;
import com.example.mobilesafe.Utils.Utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * @author like
 *归属地服务
 */
public class AddressServer extends Service {

	private TelephonyManager telephonyManager;
	private Mylistener listener;
	private callshowAddressreceiver addressreceiver;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		telephonyManager = (TelephonyManager)
				getSystemService(TELEPHONY_SERVICE);
		listener = new Mylistener();
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		callAddress();
	
	}

	/**
	 * 打电话归属地显示
	 */
	private void callAddress() {
		addressreceiver = new callshowAddressreceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
		registerReceiver(addressreceiver, filter);//动态注册广播
	}
	class callshowAddressreceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String resultData = getResultData();
			String address = AddressDAO.getAddress(resultData);
			Utils.showToast(context, address);
		}
		
	}
	
	class Mylistener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
		String address = com.example.mobilesafe.Dao.AddressDAO.getAddress(incomingNumber);
				Utils.toast(AddressServer.this, address);
				break;

			default:
				break;
			}
		}
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
		unregisterReceiver(addressreceiver);//取消广播
	}

}
