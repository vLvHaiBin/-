package com.example.mobilesafe.bean;

import android.graphics.drawable.Drawable;

/**
 * @author like
 *app½ø³Ì
 */
public class Bean_app_process {
	
	public String packageName;
	public Drawable packageIcon;
	public String packageSize;
	public boolean isUser;
	public boolean ischeck = false;
	
	public boolean isIscheck() {
		return ischeck;
	}
	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Drawable getPackageIcon() {
		return packageIcon;
	}
	public void setPackageIcon(Drawable packageIcon) {
		this.packageIcon = packageIcon;
	}
	public String getPackageSize() {
		return packageSize;
	}
	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}
	public boolean isUser() {
		return isUser;
	}
	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}
}
