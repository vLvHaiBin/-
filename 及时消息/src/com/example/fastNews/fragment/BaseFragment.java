package com.example.fastNews.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基础fragment界面
 * 
 */
public abstract class BaseFragment extends Fragment {

	/**
	 * @param savedInstanceState
	 *            初始化数据
	 */
	
	public Activity mActivity;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	/**
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return 初始化布局
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initVeiw();

	}

	public abstract View initVeiw();

	public  void initData() {

	}

}
