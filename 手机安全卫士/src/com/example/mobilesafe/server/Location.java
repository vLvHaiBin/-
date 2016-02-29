package com.example.mobilesafe.server;

import com.example.mobilesafe.Utils.Utils;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * @author like
 *启动位置服务
 */
public class Location extends Service {

	private LocationManager lc;
	private ll lll;
	private SharedPreferences sharedPreferences;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override             	
	public void onCreate() {
		super.onCreate();
		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		location();
	}
	/**
	 * 获取位置信息
	 * 
	 */
	public void location() {
		
		lc = (LocationManager) getSystemService(LOCATION_SERVICE);
		lll = new ll();
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(true);//设置是否可以用网络
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//设置帅选条件，选high没用
		String bestProvider = lc.getBestProvider(criteria, true);//自动选择定位的方法
		lc.requestLocationUpdates(bestProvider, 0, 0, lll);
	}

	class ll implements LocationListener{

		@Override
		public void onLocationChanged(android.location.Location location) {
			double j = location.getLongitude();//经度
			double w = location.getLatitude();//纬度
			float accuracy = location.getAccuracy();//精确度
			double altitude = location.getAltitude();//海拔高度
			sharedPreferences.edit().putString("location","经度:"+ j + "," + "纬度:" + w).commit();
			stopSelf();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
	}
	
		
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		lc.removeUpdates(lll);
	}
}
