package com.example.mobilesafe.widght;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MyWidget extends AppWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}

	/**
	 * ÿ�����µ�����С�ؼ����ɵ�ʱ�򶼻����
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
			Bundle newOptions) {
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
	}

	/**
	 * ÿ��ɾ������С�ؼ���ʱ�򶼻���õķ���
	 */
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	// ��һ�δ�����ʱ��Ż���õ�ǰ���������ڵķ���
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	/**
	 * �������������е�����С�ؼ���ɾ����ʱ��ŵ��õ�ǰ�������
	 */
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
		super.onRestored(context, oldWidgetIds, newWidgetIds);
	}

}
