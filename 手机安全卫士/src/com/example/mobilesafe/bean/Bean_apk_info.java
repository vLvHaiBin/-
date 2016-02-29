package com.example.mobilesafe.bean;

import android.R.bool;
import android.graphics.drawable.Drawable;

/**
 * @author like
 *apkÐÅÏ¢µÄbean
 */
public class Bean_apk_info {
	
	public  String apkname;
	public Drawable apkIcon;
	public String packageName;
	public String apksize;
	public boolean isRam;
	public boolean isUser;
	public String isuser;
	
	
	public String getIsuser() {
		return isuser;
	}
	public void setIsuser(String isuser) {
		this.isuser = isuser;
	}
	public boolean isUser() {
		return isUser;
	}
	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}
	public String getApkname() {
		return apkname;
	}
	public void setApkname(String apkname) {
		this.apkname = apkname;
	}
	public Drawable getApkIcon() {
		return apkIcon;
	}
	public void setApkIcon(Drawable apkIcon) {
		this.apkIcon = apkIcon;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getApksize() {
		return apksize;
	}
	public void setApksize(String apksize) {
		this.apksize = apksize;
	}
	public boolean isRam() {
		return isRam;
	}
	public void setRam(boolean isRam) {
		this.isRam = isRam;
	}
	
	
	
}
