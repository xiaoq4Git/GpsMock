package com.baidu.location.demo;

import java.util.ArrayList;
import java.util.List;

import com.baidu.baidulocationdemo.R;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/***
 * �������ͬ��λҵ�����޹أ�������ʵ�б�
 * 
 * @author baidu
 *
 */
public class MainActivity extends Activity {
	private final int SDK_PERMISSION_REQUEST = 127;
	private ListView FunctionList;
	private String permissionInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.function_list);
		FunctionList = (ListView) findViewById(R.id.functionList);
		FunctionList
				.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		FunctionList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Class<?> TargetClass = null;
				switch (arg2) {
				case 0:
					TargetClass = LocationActivity.class;
					break;
				case 1:
					TargetClass = LocationOption.class;
					break;
				case 2:
					TargetClass = LocationAutoNotify.class;
					break;
				case 3:
					TargetClass = LocationFilter.class;
					break;
				case 4:
					TargetClass = NotifyActivity.class;
					break;
				case 5:
					TargetClass = QuestActivity.class;
					break;
				default:
					break;
				}
				if (TargetClass != null) {
					Intent intent = new Intent(MainActivity.this, TargetClass);
					intent.putExtra("from", 0);
					startActivity(intent);
				}
			}
		});
	}

	private List<String> getData() {

		List<String> data = new ArrayList<String>();
		data.add("������λ����");
		data.add("���ö�λ����");
		data.add("�Զ���ص�ʾ��");
		data.add("������λʾ��");
		data.add("λ����Ϣ����");
		data.add("��������˵��");

		return data;
	}
}
