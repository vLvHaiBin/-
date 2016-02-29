package com.example.fastNews.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ����fragment����
 * 
 */
public abstract class BaseFragment extends Fragment {

	/**
	 * @param savedInstanceState
	 *            ��ʼ������
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
	 * @return ��ʼ������
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initVeiw();

	}

	public abstract View initVeiw();

	public  void initData() {

	}

}
