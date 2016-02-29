package com.example.mobilesafe.receiver;


import java.util.ArrayList;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Dao.Dao_blacklist;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.bean.Bean_blacklist;
import com.example.mobilesafe.server.Location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class Smsreceiver extends BroadcastReceiver {

	/* (non-Javadoc)
	 * 短息接收广播处理
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Dao_blacklist blacklist = new Dao_blacklist(context);
		ArrayList<Bean_blacklist> doqueryAll2 = blacklist.doqueryAll2(context);
		
		Object[] object = (Object[]) intent.getExtras().get("pdus");
		for(Object objects:object){
			@SuppressWarnings("deprecation")
			SmsMessage createFromPdu = SmsMessage.createFromPdu((byte[]) objects);
			String originatingAddress = createFromPdu.getOriginatingAddress();//获取短信号码
			String messageBody = createFromPdu.getMessageBody();//获取短信内容
			for(Bean_blacklist bb:doqueryAll2){
				String number = bb.getNumber();
				if(originatingAddress.equals(number)){
					Utils.logError(context, "lllll");
					Utils.toast(context, "拦截成功");
					abortBroadcast();
					
				}
			}
			if("报警音乐".equals(messageBody)){
				MediaPlayer create = MediaPlayer.create(context, R.raw.ylzs);
				create.setLooping(true);//循环
				create.setVolume(0.5f, 0.5f);//设置播放音量
				create.start();
				
				abortBroadcast();
			}
			else if("位置信息".equals(messageBody)){
				context.startService(new Intent(context,Location.class));
				String location = Utils.sharprefrence_get(context, "location","获取失败");
				SmsManager smsManager = SmsManager.getDefault();
				String phone = Utils.sharprefrence_get(context, "phone", null);
				smsManager.sendTextMessage("15609035035", null, location, null, null);
				Utils.toast(context, "位置信息");
				abortBroadcast();
			}
		}
	}

}
