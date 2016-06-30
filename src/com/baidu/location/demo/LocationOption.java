package com.baidu.location.demo;

import com.baidu.baidulocationdemo.R;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.service.LocationService;
import com.baidu.location.service.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;


/***
 * չʾ��λsdk���õ�ʾ��������ѡ�����õ�ʵ������locationActivity�Ķ�λ���ܣ����Ǹ������µ�������
 * ע�⣺��Щѡ����ڻ����ԭ��������ѡ�к���ȡ����Ȼ���ڶ�λ�������ʾ����
 * @author baidu
 *
 */
public class LocationOption extends Activity{
	private RadioGroup selectLocMode,selectcoord;
	private EditText scanSpan;
	private CheckBox geolocation,poi,describe,director;
	private LocationClientOption option;
	private Button startLoc;
	private LocationService locService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationconfig);
		selectLocMode = (RadioGroup)findViewById(R.id.selectMode);
		selectcoord = (RadioGroup)findViewById(R.id.selectCoordinates);
		scanSpan = (EditText)findViewById(R.id.frequence);
		geolocation = (CheckBox)findViewById(R.id.geolocation);
		poi = (CheckBox)findViewById(R.id.poiCheckBox);
		describe = (CheckBox)findViewById(R.id.Describe);
		director = (CheckBox)findViewById(R.id.Director);
		startLoc = (Button)findViewById(R.id.start);
		locService =  ((LocationApplication)getApplication()).locationService;
		option = new LocationClientOption();
		locService.stop();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startLoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (selectLocMode.getCheckedRadioButtonId()) {
				case R.id.radio_hight:
					option.setLocationMode(LocationMode.Hight_Accuracy);
					break;
				case R.id.radio_low:
					option.setLocationMode(LocationMode.Battery_Saving);
					break;
				case R.id.radio_device:
					option.setLocationMode(LocationMode.Device_Sensors);
					break;
				default:
					break;
				}
				switch (selectcoord.getCheckedRadioButtonId()) {
				case R.id.radio_gcj02:
					option.setCoorType(Utils.CoorType_GCJ02);
					break;
				case R.id.radio_bd09ll:
					option.setCoorType(Utils.CoorType_BD09LL);
					break;
				case R.id.radio_bd09:
					option.setCoorType(Utils.CoorType_BD09MC);
					break;
				default:
					break;
				}
				try {
					int frequence = Integer.parseInt(scanSpan.getText().toString());
					option.setScanSpan(frequence);
				} catch (Exception e) {
					// TODO: handle exception
					option.setScanSpan(3000);
				}
				/**
				 * ����λ����Ϣ
				 */
				if(geolocation.isChecked())
					option.setIsNeedAddress(true);
				else
					option.setIsNeedAddress(false);
				/**
				 * �ܱ�poi�б�
				 */
				if(poi.isChecked())
					option.setIsNeedLocationPoiList(true);
				else
					option.setIsNeedLocationPoiList(false);
				/**
				 * λ�����⻯
				 */
				if(describe.isChecked())
					option.setIsNeedLocationDescribe(true);
				else
					option.setIsNeedLocationDescribe(false);
				/**
				 * ����
				 */
				if(director.isChecked())
					option.setNeedDeviceDirect(true);
				else
					option.setNeedDeviceDirect(false);
				
				/**
				 * ����ǰ��ֹͣ��λ�������ú�������λ����ſ�����Ч
				 */
				locService.setLocationOption(option);
				
				Intent locIntent = new Intent(LocationOption.this, LocationActivity.class);
				locIntent.putExtra("from", 1);
				LocationOption.this.startActivity(locIntent);
			}
			
		});
	}
	
}
