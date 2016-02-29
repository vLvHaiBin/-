package com.example.mobilesafe.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.mobilesafe.bean.Bean_apk_info;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.format.Formatter;


/**
 * @author like
 *��ȡ�ֻ�Ӧ�ø�����Ϣ
 */
public class AppInfoUtils {
	
	
	public static ArrayList<Bean_apk_info> getAppInfo(Context context){
		
		ArrayList<Bean_apk_info> apkinfolist = new ArrayList<Bean_apk_info>();;
		if(apkinfolist != null){
			apkinfolist.clear();
		}
		PackageManager manager = context.getPackageManager();
		List<PackageInfo> installedPackages = manager.getInstalledPackages(0);//��ȡ���а�װ������Ϣ
		for(PackageInfo packages:installedPackages){
			
			Bean_apk_info apk_info = new Bean_apk_info();
			
			Drawable loadIcon = packages.applicationInfo.loadIcon(manager);//apkͼ��
			apk_info.setApkIcon(loadIcon);
			
			String name = packages.applicationInfo.loadLabel(manager).toString();//apk����
			apk_info.setApkname(name);
			
			String packageName = packages.packageName;//apk�İ���
			apk_info.setPackageName(packageName);
			
			String sourceDir = packages.applicationInfo.sourceDir;//��װ·��
		
			File file = new File(sourceDir);//��ȡ���ڵĵ��ļ�λ��
			long appsize = file.length();//��ȡ�����ļ��еĴ�С
			String appsizeF = Formatter.formatFileSize(context, appsize);
			apk_info.setApksize(appsizeF);
			if(sourceDir.startsWith("/data")){
				apk_info.setUser(true);
			}else {
				apk_info.setUser(false);;
			}
			int flags = packages.applicationInfo.flags;
			
			/*if((flags & ApplicationInfo.FLAG_SYSTEM)== 1){
				//��ʾϵͳapp
			
				apk_info.setUser(false);
			}else {
				//��ʾ�û�App
				apk_info.setUser(true);
			}*/
			
			if((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE)== 1){
				//��ʾ��sd��
				apk_info.setRam(true);
			}else {
				//��ʾ��ϵͳ�ڴ�
				apk_info.setRam(false);
			}
		apkinfolist.add(apk_info);
		}
		return apkinfolist;
	}
}
