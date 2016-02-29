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
 *����λ�÷���
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
	 * ��ȡλ����Ϣ
	 * 
	 */
	public void location() {
		
		lc = (LocationManager) getSystemService(LOCATION_SERVICE);
		lll = new ll();
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(true);//�����Ƿ����������
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//����˧ѡ������ѡhighû��
		String bestProvider = lc.getBestProvider(criteria, true);//�Զ�ѡ��λ�ķ���
		lc.requestLocationUpdates(bestProvider, 0, 0, lll);
	}

	class ll implements LocationListener{

		@Override
		public void onLocationChanged(android.location.Location location) {
			double j = location.getLongitude();//����
			double w = location.getLatitude();//γ��
			float accuracy = location.getAccuracy();//��ȷ��
			double altitude = location.getAltitude();//���θ߶�
			sharedPreferences.edit().putString("location","����:"+ j + "," + "γ��:" + w).commit();
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
