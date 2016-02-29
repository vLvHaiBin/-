package com.example.mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Contact extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_activity);
		final List<HashMap<String, String>> list = readContact();
		ListView lv_contact = (ListView) findViewById(R.id.lv_contact);
		lv_contact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				HashMap<String, String> hashMap = list.get(position);
				intent.putExtra("phone", hashMap.get("phone"));
				setResult(1, intent);
				
				intent.putExtra("phone", hashMap.get("phone"));
				intent.putExtra("name", hashMap.get("name"));
				setResult(2, intent);
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		lv_contact.setDivider(null);
		lv_contact.setAdapter(new SimpleAdapter(this, list, R.layout.contact_list_item,
				new String[] { "name", "phone" }, new int[] { R.id.tv_contact, R.id.tv_phone_number }));
		iniData();

	}

	private void iniData() {

	}

	/**
	 * ��ȡ��ϵ����Ϣ
	 * @return
	 */
	private ArrayList<HashMap<String, String>> readContact() {
		
		// ����,��raw_contacts�ж�ȡ��ϵ�˵�id("contact_id")
		// ���, ����contact_id��data���в�ѯ����Ӧ�ĵ绰�������ϵ������
		// Ȼ��,����mimetype�������ĸ�����ϵ��,�ĸ��ǵ绰����
		Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		// ��raw_contacts�ж�ȡ��ϵ�˵�id("contact_id")
		Cursor rawContactsCursor = getContentResolver().query(rawContactsUri, new String[] { "contact_id" }, null, null,
				null);
		if (rawContactsCursor != null) {
			while (rawContactsCursor.moveToNext()) {
				String contactId = rawContactsCursor.getString(0);
				// System.out.println(contactId);

				// ����contact_id��data���в�ѯ����Ӧ�ĵ绰�������ϵ������, ʵ���ϲ�ѯ������ͼview_data
				Cursor dataCursor = getContentResolver().query(dataUri, new String[] { "data1", "mimetype" },
						"contact_id=?", new String[] { contactId }, null);

				if (dataCursor != null) {
					HashMap<String, String> map = new HashMap<String, String>();
					while (dataCursor.moveToNext()) {
						String data1 = dataCursor.getString(0);
						String mimetype = dataCursor.getString(1);
						// System.out.println(contactId + ";" + data1 + ";"
						// + mimetype);
						if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
							map.put("phone", data1);
						} else if ("vnd.android.cursor.item/name".equals(mimetype)) {
							map.put("name", data1);
						}
					}

					list.add(map);
					dataCursor.close();
				}
			}

			rawContactsCursor.close();
		}

		return list;
	}
}
