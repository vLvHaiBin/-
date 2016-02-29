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
 *获取手机应用各种信息
 */
public class AppInfoUtils {
	
	
	public static ArrayList<Bean_apk_info> getAppInfo(Context context){
		
		ArrayList<Bean_apk_info> apkinfolist = new ArrayList<Bean_apk_info>();;
		if(apkinfolist != null){
			apkinfolist.clear();
		}
		PackageManager manager = context.getPackageManager();
		List<PackageInfo> installedPackages = manager.getInstalledPackages(0);//获取所有安装包的信息
		for(PackageInfo packages:installedPackages){
			
			Bean_apk_info apk_info = new Bean_apk_info();
			
			Drawable loadIcon = packages.applicationInfo.loadIcon(manager);//apk图标
			apk_info.setApkIcon(loadIcon);
			
			String name = packages.applicationInfo.loadLabel(manager).toString();//apk名字
			apk_info.setApkname(name);
			
			String packageName = packages.packageName;//apk的包名
			apk_info.setPackageName(packageName);
			
			String sourceDir = packages.applicationInfo.sourceDir;//安装路径
		
			File file = new File(sourceDir);//获取所在的的文件位置
			long appsize = file.length();//获取所在文件夹的大小
			String appsizeF = Formatter.formatFileSize(context, appsize);
			apk_info.setApksize(appsizeF);
			if(sourceDir.startsWith("/data")){
				apk_info.setUser(true);
			}else {
				apk_info.setUser(false);;
			}
			int flags = packages.applicationInfo.flags;
			
			/*if((flags & ApplicationInfo.FLAG_SYSTEM)== 1){
				//表示系统app
			
				apk_info.setUser(false);
			}else {
				//表示用户App
				apk_info.setUser(true);
			}*/
			
			if((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE)== 1){
				//表示在sd卡
				apk_info.setRam(true);
			}else {
				//表示在系统内存
				apk_info.setRam(false);
			}
		apkinfolist.add(apk_info);
		}
		return apkinfolist;
	}
}
