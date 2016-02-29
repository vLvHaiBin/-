package com.example.mobilesafe.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.util.Log;

/**
 * @author like ��ȡjson����
 */
public class HttpURLConnectionUtils {

	public static String getJson(String url) {

		HttpURLConnection conn = null;

		URL murl = null;
		try {
			murl = new URL(url);
		} catch (MalformedURLException e4) {
			e4.printStackTrace();
			Log.e("URL����", "MalformedURLException:���URL");
		}
		try {
			conn = (HttpURLConnection) murl.openConnection();
		} catch (IOException e4) {
			e4.printStackTrace();
		}
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e4) {
			Log.e("ProtocolException", "ProtocolException:Э�����");
			e4.printStackTrace();

		} // �������󷽷�
		conn.setConnectTimeout(5000);// �������ӳ�ʱ
		conn.setReadTimeout(5000);// ������Ӧ��ʱ, ��������,���������ٳٲ�����Ӧ
		try {
			conn.connect();
		} catch (IOException e4) {
			e4.printStackTrace();
		} // ���ӷ�����

		int responseCode = 0;
		try {
			responseCode = conn.getResponseCode();
		} catch (IOException e3) {
			e3.printStackTrace();
		} // ��ȡ��Ӧ��
		if (responseCode == 200) {
			InputStream inputStream = null;
			try {
				inputStream = conn.getInputStream();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];

			try {
				while ((len = inputStream.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			String result = out.toString();
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}
}