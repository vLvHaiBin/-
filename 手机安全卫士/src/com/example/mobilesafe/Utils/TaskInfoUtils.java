package com.example.mobilesafe.Utils;

import java.util.ArrayList;
import java.util.List;

import com.example.mobilesafe.R;
import com.example.mobilesafe.bean.Bean_app_process;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;
import android.text.format.Formatter;

/**
 * @author like
 *��ȡ��ǰ���н��̸�����Ϣ
 */
public class TaskInfoUtils {

	public static List<Bean_app_process> getTaskInfos(Context context) {

		PackageManager packageManager = context.getPackageManager();

		List<Bean_app_process> TaskInfos = new ArrayList<Bean_app_process>();

		// ��ȡ�����̹�����
		ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		// ��ȡ���ֻ������������еĽ���
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

		for (RunningAppProcessInfo runningAppProcessInfo : appProcesses) {

			Bean_app_process taskInfo = new Bean_app_process();

			// ��ȡ�����̵�����
			String processName = runningAppProcessInfo.processName;

			taskInfo.setPackageName(processName);

			try {
				// ��ȡ���ڴ������Ϣ
				/**
				 * �������һ��ֻ��һ������
				 */
				MemoryInfo[] memoryInfo = activityManager.getProcessMemoryInfo(new int[] { runningAppProcessInfo.pid });
				// DirtyŪ��
				// ��ȡ���ܹ�Ū������ڴ�(��ǰӦ�ó���ռ�ö����ڴ�)
				int totalPrivateDirty = memoryInfo[0].getTotalPrivateDirty() * 1024;
				String totalmemory =  String.valueOf(totalPrivateDirty);
				// System.out.println("==========="+totalPrivateDirty);

				taskInfo.setPackageSize(totalmemory);

				PackageInfo packageInfo = packageManager.getPackageInfo(processName, 0);

				// /��ȡ��ͼƬ
				Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);

				taskInfo.setPackageIcon(icon);
				// ��ȡ��Ӧ�õ�����
				String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();

				taskInfo.setPackageName(appName);

				System.out.println("-------------------");
				System.out.println("processName=" + processName);
				System.out.println("appName=" + appName);
				// ��ȡ����ǰӦ�ó���ı��
				// packageInfo.applicationInfo.flags ����д�Ĵ�
				// ApplicationInfo.FLAG_SYSTEM��ʾ��ʦ�ĸþ���
				int flags = packageInfo.applicationInfo.flags;
				// ApplicationInfo.FLAG_SYSTEM ��ʾϵͳӦ�ó���
				if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					// ϵͳӦ��
					taskInfo.setUser(false);
					;
				} else {
					// /�û�Ӧ��
					taskInfo.setUser(true);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// ϵͳ���Ŀ�������Щϵͳû��ͼ�ꡣ�����һ��Ĭ�ϵ�ͼ��

				taskInfo.setPackageName(processName);
				taskInfo.setPackageIcon(context.getResources().getDrawable(R.drawable.app));
			}

			TaskInfos.add(taskInfo);
		}

		return TaskInfos;
	}

}
