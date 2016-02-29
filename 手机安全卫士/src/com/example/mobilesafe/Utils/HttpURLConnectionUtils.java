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
 * @author like 获取json工具
 */
public class HttpURLConnectionUtils {

	public static String getJson(String url) {

		HttpURLConnection conn = null;

		URL murl = null;
		try {
			murl = new URL(url);
		} catch (MalformedURLException e4) {
			e4.printStackTrace();
			Log.e("URL错误", "MalformedURLException:检查URL");
		}
		try {
			conn = (HttpURLConnection) murl.openConnection();
		} catch (IOException e4) {
			e4.printStackTrace();
		}
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e4) {
			Log.e("ProtocolException", "ProtocolException:协议出错");
			e4.printStackTrace();

		} // 设置请求方法
		conn.setConnectTimeout(5000);// 设置连接超时
		conn.setReadTimeout(5000);// 设置响应超时, 连接上了,但服务器迟迟不给响应
		try {
			conn.connect();
		} catch (IOException e4) {
			e4.printStackTrace();
		} // 连接服务器

		int responseCode = 0;
		try {
			responseCode = conn.getResponseCode();
		} catch (IOException e3) {
			e3.printStackTrace();
		} // 获取响应码
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