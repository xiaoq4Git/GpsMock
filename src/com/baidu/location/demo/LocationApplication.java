package com.baidu.location.demo;


import com.baidu.location.service.LocationService;
import com.baidu.location.service.SaveSDCardService;
import com.baidu.mapapi.SDKInitializer;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

/**
 * ��Application�����аٶȶ�λSDK�Ľӿ�˵����ο������ĵ���http://developer.baidu.com/map/loc_refer/index.html
 *
 * �ٶȶ�λSDK�ٷ���վ��http://developer.baidu.com/map/index.php?title=android-locsdk
 * 
 * ֱ�ӿ���com.baidu.location.service�����Լ��Ĺ����£������ü��ɻ�ȡ��λ�����Ҳ���Ը���demo�������з�װ
 */
public class LocationApplication extends Application {
	public LocationService locationService;
	public SaveSDCardService saveSDCardService;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        /***
         * ��ʼ����λsdk��������Application�д���
         */
        saveSDCardService = new SaveSDCardService();
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());  
       
    }
}
