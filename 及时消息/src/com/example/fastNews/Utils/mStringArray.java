package com.example.fastNews.Utils;

import java.util.ArrayList;

public class mStringArray {
	public static String[] title = new String[] { "ʵʱ�ȵ�", "Ц��", "����", "��Ӱ", "����" };

	public static ArrayList<String> arrayList = new ArrayList<String>();
	private static mStringArray string;

	public static mStringArray getInstance() {
		if (arrayList.size() == 0) {
			for (String tt : title) {
				arrayList.add(tt);
			}
		}
		if (string == null) {
			string = new mStringArray();
			return string;
		}
		return null;
	}

	public void getString(String[] ll) {
		if (ll != null) {
			title = null;
			title = ll;
		}

	}

}
