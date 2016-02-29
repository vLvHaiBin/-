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
 *获取当前运行进程各种信息
 */
public class TaskInfoUtils {

	public static List<Bean_app_process> getTaskInfos(Context context) {

		PackageManager packageManager = context.getPackageManager();

		List<Bean_app_process> TaskInfos = new ArrayList<Bean_app_process>();

		// 获取到进程管理器
		ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		// 获取到手机上面所有运行的进程
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

		for (RunningAppProcessInfo runningAppProcessInfo : appProcesses) {

			Bean_app_process taskInfo = new Bean_app_process();

			// 获取到进程的名字
			String processName = runningAppProcessInfo.processName;

			taskInfo.setPackageName(processName);

			try {
				// 获取到内存基本信息
				/**
				 * 这个里面一共只有一个数据
				 */
				MemoryInfo[] memoryInfo = activityManager.getProcessMemoryInfo(new int[] { runningAppProcessInfo.pid });
				// Dirty弄脏
				// 获取到总共弄脏多少内存(当前应用程序占用多少内存)
				int totalPrivateDirty = memoryInfo[0].getTotalPrivateDirty() * 1024;
				String totalmemory =  String.valueOf(totalPrivateDirty);
				// System.out.println("==========="+totalPrivateDirty);

				taskInfo.setPackageSize(totalmemory);

				PackageInfo packageInfo = packageManager.getPackageInfo(processName, 0);

				// /获取到图片
				Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);

				taskInfo.setPackageIcon(icon);
				// 获取到应用的名字
				String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();

				taskInfo.setPackageName(appName);

				System.out.println("-------------------");
				System.out.println("processName=" + processName);
				System.out.println("appName=" + appName);
				// 获取到当前应用程序的标记
				// packageInfo.applicationInfo.flags 我们写的答案
				// ApplicationInfo.FLAG_SYSTEM表示老师的该卷器
				int flags = packageInfo.applicationInfo.flags;
				// ApplicationInfo.FLAG_SYSTEM 表示系统应用程序
				if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					// 系统应用
					taskInfo.setUser(false);
					;
				} else {
					// /用户应用
					taskInfo.setUser(true);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// 系统核心库里面有些系统没有图标。必须给一个默认的图标

				taskInfo.setPackageName(processName);
				taskInfo.setPackageIcon(context.getResources().getDrawable(R.drawable.app));
			}

			TaskInfos.add(taskInfo);
		}

		return TaskInfos;
	}

}
