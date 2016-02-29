package com.example.mobilesafe.receiver;

import com.example.mobilesafe.Utils.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class Bootcompletereceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		TelephonyManager manager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		String simSerialNumber = manager.getSimSerialNumber();
		String sim = Utils.sharprefrence_get(context, "sim", null);

		if (!TextUtils.isEmpty(sim)) {
			if (!sim.equals(simSerialNumber)) {
				String phone = Utils.sharprefrence_get(context, "phone", null);
				String state = Utils.sharprefrence_get(context, "guard", null);
				if (state.equals("1")) {
					if (!TextUtils.isEmpty(phone)) {
						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage(phone, null, "手机sim卡已变更，请检查手机是否安全", null, null);
					}

				}

			}
		}

	}

}
