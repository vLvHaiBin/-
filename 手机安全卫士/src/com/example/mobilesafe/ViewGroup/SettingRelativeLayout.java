package com.example.mobilesafe.ViewGroup;


import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.ViewGroup.ToggleButton.ToggleStateChangeListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode.Mode;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingRelativeLayout extends RelativeLayout {

	

	private static final String NAMESPACE ="http://schemas.android.com/apk/res/com.example.mobilesafe";
	public  ToggleButton toggleButton;
	public TextView tv_title_setting;
	public TextView tv_state_setting;
	public String title;
	private String desc_on;
	private String desc_off;
	private String name;
	
	


	public SettingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SettingRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		//通过自定义属性的名字来获取相应的值
		title = attrs.getAttributeValue(NAMESPACE,"title");
		desc_on = attrs.getAttributeValue(NAMESPACE,"desc_on");
		desc_off = attrs.getAttributeValue(NAMESPACE,"desc_off");
		name = attrs.getAttributeValue(NAMESPACE,"name");
		initView();
	}

	public SettingRelativeLayout(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		
	
		View v = View.inflate(getContext(), R.layout.setting_zidingyi, this);
		toggleButton = (ToggleButton) v.findViewById(R.id.tl_switch);
		tv_title_setting = (TextView) v.findViewById(R.id.tv_title_setting);
		tv_state_setting = (TextView) v.findViewById(R.id.tv_state_setting);
		tv_state_setting.setText(desc_off);
		RelativeLayout rl_item = (RelativeLayout) v.findViewById(R.id.rl_item);
		String first = Utils.sharprefrence_get(getContext(), name, "-1");
		if(TextUtils.equals(first, "-1")){
			Utils.sharprefrence_save(getContext(), name, "0");
		}
		rl_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = Utils.sharprefrence_get(getContext(), name, "-1");
				if(!TextUtils.equals(text,"0")) {
					Utils.sharprefrence_save(getContext(), name, "0");
					toggleButton.setSlideBackgroundResource(
							R.drawable.mz_switch_thumb_activated_disabled_color_limegreen);
					tv_state_setting.setText(desc_off);
					toggleButton.setToggleState(0);    
					
				}else if (!TextUtils.equals(text,"1")) {
					tv_state_setting.setText(desc_on);
					toggleButton.setToggleState(1);
					Utils.sharprefrence_save(getContext(), name, "1");
					toggleButton.setSlideBackgroundResource(
							R.drawable.mz_switch_thumb_activated_color_limegreen);
					
				}
			}
		});
		tv_title_setting.setText(title);
		statechangevent();
		String state = Utils.sharprefrence_get(getContext(), name, "-1");
		 onStateChange(state);
		saveState();
	}

	/*private void getstate() {
		String state = Utils.sharprefrence_get(getContext(), name, "-1");
		changeLister.onStateChange(state);
	}*/

	/**
	 * switch开关改变的处理
	 */
	private void statechangevent() {
		
		toggleButton.setToggleState(0);
		toggleButton.setSlideBackgroundResource(//滑动块的背景图片
				R.drawable.mz_switch_thumb_activated_disabled_color_limegreen);
		toggleButton.setSwitchBackgroundResource(
				R.drawable.mc_switch_bg_default);
		toggleButton.setOnToaggleStateChange(new ToggleStateChangeListener() {
			
			@Override
			public void onToggleStateChange(int state) {
				if(state == 0) {
					
					Utils.sharprefrence_save(getContext(),name,"0");
					toggleButton.setSlideBackgroundResource(
							R.drawable.mz_switch_thumb_activated_disabled_color_limegreen);
					tv_state_setting.setText(desc_off);
				}else {
					Utils.toast(getContext(), "1");
				Utils.sharprefrence_save(getContext(),name, "1");
					tv_state_setting.setText(desc_on);
					toggleButton.setSlideBackgroundResource(
							R.drawable.mz_switch_thumb_activated_color_limegreen);
				}
			}
		});
	}

	private void saveState() {
		String state = Utils.sharprefrence_get(getContext(), name, "-1");
		if(TextUtils.equals(state,"0")) {
			Utils.toast(getContext(), "0");
			toggleButton.setSlideBackgroundResource(
					R.drawable.mz_switch_thumb_activated_disabled_color_limegreen);
			tv_state_setting.setText(desc_off);
			toggleButton.setToggleState(0);    
			
		}else if (TextUtils.equals(state,"1")) {
			Utils.toast(getContext(), "1");
			tv_state_setting.setText(desc_on);
			toggleButton.setToggleState(1);
			toggleButton.setSlideBackgroundResource(
					R.drawable.mz_switch_thumb_activated_color_limegreen);
			
		}else {
			return;
		}
	}
	
	
	

	public void onStateChange(String state){
		
	}
}
	
/*
	public interface onStateChangeLister{
		void onStateChange(String state);
		}
	}
*/
	