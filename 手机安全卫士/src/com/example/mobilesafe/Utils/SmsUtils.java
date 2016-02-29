package com.example.mobilesafe.Utils;

import java.io.File;
import java.io.FileOutputStream;

import org.xmlpull.v1.XmlSerializer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Xml;

public class SmsUtils {

	/**
	 * ���ݶ��ŵĽӿ�
	 * 
	 * @author Administrator
	 *
	 */
	public interface BackUpCallBackSms {

		public void befor(int count);

		public void onBackUpSms(int process);

	}

	public static boolean backUp(Context context, BackUpCallBackSms callback) {
		/**
		 * Ŀ�� �� ���ݶ��ţ�
		 * 
		 * 1 �жϵ�ǰ�û����ֻ������Ƿ���sd�� 2 Ȩ�� --- ʹ�����ݹ۲��� 3 д����(д��sd��)
		 * 
		 * 
		 */

		// �жϵ�ǰsd����״̬
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			// ����ܽ�����˵���û���SD��
			ContentResolver resolver = context.getContentResolver();
			// ��ȡ���ŵ�·��
			Uri uri = Uri.parse("content://sms/");
			// type = 1 ���ն���
			// type = 2 ���Ͷ���
			// cursor ��ʾ�α����˼
			Cursor cursor = resolver.query(uri, new String[] { "address", "date", "type", "body" }, null, null, null);
			// ��ȡ��ǰһ���ж���������
			int count = cursor.getCount();
			// ����pd�����ֵ
			// pd.setMax(count);
			// progressBar1.setMax(count);

			callback.befor(count);

			// ������Ĭ����0
			int process = 0;

			// д�ļ�
			try {
				// �Ѷ��ű��ݵ�sd�� �ڶ���������ʾ����
				File file = new File(Environment.getExternalStorageDirectory() + "/backupmessaggs");
				if (!file.exists()) {
					file.mkdir();
				}

				File file2 = new File(Environment.getExternalStorageDirectory() + "/backupmessaggs", "message.xml");
				FileOutputStream os = new FileOutputStream(file2);
				// �õ����л���
				// ��androidϵͳ���������й�xml�Ľ�������pull����
				XmlSerializer serializer = Xml.newSerializer();
				// �Ѷ������л���sd��Ȼ�����ñ����ʽ
				serializer.setOutput(os, "utf-8");
				// standalone��ʾ��ǰ��xml�Ƿ��Ƕ����ļ� ture��ʾ�ļ�������yes
				serializer.startDocument("utf-8", true);
				// ���ÿ�ʼ�Ľڵ� ��һ�������������ռ䡣�ڶ��������ǽڵ������
				serializer.startTag(null, "smss");
				// ����smss�ڵ����������ֵ �ڶ������������֡�������������ֵ
				serializer.attribute(null, "size", String.valueOf(count));
				// �α�����������ƶ�
				while (cursor.moveToNext()) {
					System.err.println("----------------------------");
					System.out.println("address = " + cursor.getString(0));
					System.out.println("date = " + cursor.getString(1));
					System.out.println("type = " + cursor.getString(2));
					System.out.println("body = " + cursor.getString(3));

					serializer.startTag(null, "sms");

					serializer.startTag(null, "address");
					// �����ı�������
					String address = cursor.getString(0);
					if(TextUtils.isEmpty(address)){//�����ж�
						serializer.text("");
					}else {
						serializer.text(address);
					}
					serializer.endTag(null, "address");

					serializer.startTag(null, "date");
					
					String date = cursor.getString(1);
					if(TextUtils.isEmpty(address)){
						serializer.text("");
					}else {
						serializer.text(date);
					}
					

					serializer.endTag(null, "date");

					serializer.startTag(null, "type");

					String type = cursor.getString(2);
					if(TextUtils.isEmpty(address)){
						serializer.text("");
					}else {
						serializer.text(type);
					}
					
					serializer.endTag(null, "type");

					serializer.startTag(null, "body");

					// ��ȡ���ŵ�����
					/**
					 * ���ܣ���һ��������ʾ��������(��Կ) �ڶ���������ʾ���ܵ�����
					 */
					String body = cursor.getString(3);
					if(TextUtils.isEmpty(address)){
						serializer.text("");
					}else {
						serializer.text(body);
					}
					

					serializer.endTag(null, "body");

					serializer.endTag(null, "sms");
					// ���л���һ������֮�����Ҫ++
					process++;

					// pd.setProgress(process);
					//
					// progressBar1.setProgress(process);

					callback.onBackUpSms(process);

					SystemClock.sleep(200);

				}
				if (cursor.moveToLast()) {
					cursor.close();
					serializer.endDocument();
					os.flush();
					os.close();
				}

				serializer.endTag(null, "smss");

				return true;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return false;
	}

}
