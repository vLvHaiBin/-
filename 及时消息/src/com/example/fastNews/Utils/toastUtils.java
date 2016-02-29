package com.example.fastNews.Utils;

import android.content.Context;
import android.widget.Toast;

public class toastUtils {
	
	public static void toast(Context context,String text){
		Toast.makeText(context, text, 0).show();
	}
}
