package com.example.fastNews.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.fastNews.R;
import com.example.fastNews.bean.WeatherBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class slidingFragment extends BaseFragment {

	private String mJosn;
	private WeatherBean mAnalysisData;

	private TextView tv_today_date, tv_today_nongli_date, tv_today_tixing, tv_today_weather, tv_today_wind,
			tv_today_temp, tv_tommorow_weather, tv_tommorow_temp, tv_tommorow_wind, tv_houtian_wind, tv_houtian_temp,
			tv_houtian_weather, tv_citiy;

	private String cityname;
	private ImageView iv_first, iv_second, iv_third;

	@Override
	public View initVeiw() {
		View v = View.inflate(mActivity, R.layout.sliding_menu_weather, null);
		tv_today_date = (TextView) v.findViewById(R.id.tv_today_date);
		tv_today_nongli_date = (TextView) v.findViewById(R.id.tv_nongli_date);
		tv_today_temp = (TextView) v.findViewById(R.id.tv_currentTemp);
		tv_today_tixing = (TextView) v.findViewById(R.id.tv_tixing);
		tv_today_weather = (TextView) v.findViewById(R.id.tv_currentWeather);
		tv_today_wind = (TextView) v.findViewById(R.id.tv_currentWind);

		tv_tommorow_temp = (TextView) v.findViewById(R.id.tv_tommrow_temp);
		tv_tommorow_weather = (TextView) v.findViewById(R.id.tv_tommrow_weather);
		tv_tommorow_wind = (TextView) v.findViewById(R.id.tv_tommrow_wind);

		tv_houtian_temp = (TextView) v.findViewById(R.id.tv_houtian_temp);
		tv_houtian_weather = (TextView) v.findViewById(R.id.tv_houtian_weather);
		tv_houtian_temp = (TextView) v.findViewById(R.id.tv_houtian_temp);
		tv_houtian_wind = (TextView) v.findViewById(R.id.tv_houtian_wind);

		tv_citiy = (TextView) v.findViewById(R.id.tv_city);

		iv_first = (ImageView) v.findViewById(R.id.weather_icon01);
		iv_second = (ImageView) v.findViewById(R.id.weather_icon02);
		iv_third = (ImageView) v.findViewById(R.id.weather_icon03);

		return v;
	}
	
	@Override
	public void initData() {
		getJson();
	}

	private void getJson() {
		cityname = "成都";
		String Url = "http://wthrcdn.etouch.cn/weather_mini?city=" + cityname;
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, Url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(mActivity, "aa", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				mJosn = (String) arg0.result;
				analysis(mJosn);
			}
		});

	}

	protected void analysis(String mJosn2) {
		Gson mGson = new Gson();
		mAnalysisData = mGson.fromJson(mJosn, WeatherBean.class);
		// 今日天气内容
		String ganmao = mAnalysisData.data.ganmao;
		String fengli = mAnalysisData.data.forecast.get(0).fengli;
		String hight = mAnalysisData.data.forecast.get(0).high;
		String low = mAnalysisData.data.forecast.get(0).low;
		String type = mAnalysisData.data.forecast.get(0).type;
		String date = mAnalysisData.data.forecast.get(0).date;

		// 明日天气
		String tommorowtemp = mAnalysisData.data.forecast.get(1).low + "/" + mAnalysisData.data.forecast.get(1).high;
		String tommorowwind = mAnalysisData.data.forecast.get(1).fengli;
		String tommorowweather = mAnalysisData.data.forecast.get(1).type;
		// 后天天气
		String lasttemp = mAnalysisData.data.forecast.get(2).low + "/" + mAnalysisData.data.forecast.get(2).high;
		String lastwind = mAnalysisData.data.forecast.get(2).fengli;
		String lastweather = mAnalysisData.data.forecast.get(2).type;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
		String format = dateFormat.format(new Date());
		String mdate = date.substring(3);

		tv_citiy.setText(cityname);
		tv_today_date.setText(format + mdate);
		tv_today_temp.setText(low + "/" + hight);
		tv_today_tixing.setText(ganmao);
		tv_today_weather.setText(type);
		tv_today_wind.setText(fengli);

		if (TextUtils.equals(type, "小雨")) {
			iv_first.setImageResource(R.drawable.weathericon_condition_08);
			iv_second.setImageResource(R.drawable.weathericon_condition_08);

		} else if (TextUtils.equals(type, "中雨")) {
			iv_first.setImageResource(R.drawable.weathericon_condition_09);
			iv_second.setImageResource(R.drawable.weathericon_condition_09);
			iv_third.setImageResource(R.drawable.weathericon_condition_09);
		} else if (type.contains("雷")) {
			iv_first.setImageResource(R.drawable.weathericon_condition_10);
			iv_second.setImageResource(R.drawable.weathericon_condition_10);
			iv_third.setImageResource(R.drawable.weathericon_condition_10);
		} else if (type.contains("阴")) {
			iv_first.setImageResource(R.drawable.weathericon_condition_04);
			iv_second.setImageResource(R.drawable.weathericon_condition_04);
			iv_third.setImageResource(R.drawable.weathericon_condition_04);
		}

		tv_tommorow_temp.setText(tommorowtemp);
		tv_tommorow_weather.setText(type);
		tv_tommorow_wind.setText(tommorowwind);

		tv_houtian_temp.setText(lasttemp);
		tv_houtian_weather.setText(lastweather);
		tv_houtian_wind.setText(lastwind);
	}

}
