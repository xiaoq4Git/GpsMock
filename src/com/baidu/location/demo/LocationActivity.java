package com.baidu.location.demo;

import com.baidu.baidulocationdemo.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.location.service.LocationService;
import com.baidu.location.service.SaveSDCardService;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/***
 * ���㶨λʾ��������չʾ�����Ķ�λ�����������LocationService.java��
 * Ĭ������Ҳ������LocationService���޸�
 * 
 * @author baidu
 *
 */
public class LocationActivity extends Activity {
	private LocationService locationService;
	private SaveSDCardService saveSDCardService;
	private TextView LocationResult;
	private Button startLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// -----------demo view config ------------
		setContentView(R.layout.location);
		LocationResult = (TextView) findViewById(R.id.textView1);
		LocationResult.setMovementMethod(ScrollingMovementMethod.getInstance());
		startLocation = (Button) findViewById(R.id.addfence);

	}
	
	
	/**
	 * ��ʾ�����ַ���
	 * 
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			if (LocationResult != null)
				LocationResult.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/***
	 * Stop location service
	 */
	@Override
	protected void onStop() {
		locationService.unregisterListener(mListener); //ע��������
		locationService.stop(); //ֹͣ��λ����
		super.onStop();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// -----------location config ------------
		locationService = ((LocationApplication) getApplication()).locationService;
		//��ȡlocationserviceʵ��������Ӧ����ֻ��ʼ��1��locationʵ����Ȼ��ʹ�ã����Բο�����ʾ����activity������ͨ�����ַ�ʽ��ȡlocationserviceʵ����
		locationService.registerListener(mListener);
		//ע�����
		saveSDCardService = ((LocationApplication) getApplication()).saveSDCardService;
		
		int type = getIntent().getIntExtra("from", 0);
		if (type == 0) {
			locationService.setLocationOption(locationService.getDefaultLocationClientOption());
		} else if (type == 1) {
			locationService.setLocationOption(locationService.getOption());
		}
		startLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (startLocation.getText().toString().equals(getString(R.string.startlocation))) {
					locationService.start();// ��λSDK
											// start֮���Ĭ�Ϸ���һ�ζ�λ���󣬿����������ж�isstart����������request
					startLocation.setText(getString(R.string.stoplocation));
				} else {
					locationService.stop();
					startLocation.setText(getString(R.string.startlocation));
				}
			}
		});
	}

	
	/*****
	 * @see copy funtion to you project
	 * ��λ����ص�����дonReceiveLocation����������ֱ�ӿ������´��뵽�Լ��������޸�
	 *
	 */
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				StringBuffer sb = new StringBuffer(256);
				StringBuffer sb1 = new StringBuffer(128);
				sb.append("time : ");
				/**
				 * ʱ��Ҳ����ʹ��systemClock.elapsedRealtime()���� ��ȡ�����Դӿ���������ÿ�λص���ʱ�䣻
				 * location.getTime() ��ָ����˳����ν����ʱ�䣬���λ�ò������仯����ʱ�䲻��
				 */
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());
				sb.append("\nCountryCode : ");
				sb.append(location.getCountryCode());
				sb.append("\nCountry : ");
				sb.append(location.getCountry());
				sb.append("\ncitycode : ");
				sb.append(location.getCityCode());
				sb.append("\ncity : ");
				sb.append(location.getCity());
				sb.append("\nDistrict : ");
				sb.append(location.getDistrict());
				sb.append("\nStreet : ");
				sb.append(location.getStreet());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\nDescribe: ");
				sb.append(location.getLocationDescribe());
				sb.append("\nDirection(not all devices have value): ");
				sb.append(location.getDirection());
				sb.append("\nPoi: ");
				if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
					for (int i = 0; i < location.getPoiList().size(); i++) {
						Poi poi = (Poi) location.getPoiList().get(i);
						sb.append(poi.getName() + ";");
					}
				}
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS��λ���
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// ��λ��km/h
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// ��λ����
					sb.append("\ndescribe : ");
					sb.append("gps��λ�ɹ�");
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ���綨λ���
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("���綨λ�ɹ�");
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
					sb.append("\ndescribe : ");
					sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					sb.append("��������綨λʧ��");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
				}
				logMsg(sb.toString());
				
				if(location.getLocType() == BDLocation.TypeServerError && location.getLocType() == BDLocation.TypeNetWorkException && location.getLocType() == BDLocation.TypeCriteriaException){
					return ;
				}
				sb1.append("\t<wpt lat=\"30.63625\" lon=\"104.0736843\">\n");
				sb1.append("\t\t<ele>495.2</ele>\n");
				sb1.append("\t</wpt>\n");
				
				saveSDCardService.write(sb1.toString());
			}
		}

	};
}
