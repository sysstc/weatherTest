package com.test.weathertest.activity;

import com.test.weathertest.R;
import com.test.weathertest.util.HttpCallbackListener;
import com.test.weathertest.util.HttpUtil;
import com.test.weathertest.util.Utility;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends Activity{
	private LinearLayout weatherInfoLayout;
	private TextView cityNameText;
	private TextView publishText;
	private TextView weatherDespText;
	private TextView temp1Text;
	private TextView temp2Text;
	private TextView currentDateText;
	private String spName = "SharedPreferenceWeather";
	private Button clearPrefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);
		weatherDespText = (TextView) findViewById(R.id.weather_desp);
		temp1Text = (TextView) findViewById(R.id.temp1);
		temp2Text = (TextView) findViewById(R.id.temp2);
		currentDateText = (TextView) findViewById(R.id.current_date);
		String countyCode = getIntent().getStringExtra("county_code");
		if(!TextUtils.isEmpty(countyCode)){
			publishText.setText("ͬ����......");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
		}else{
			//û���ؼ����ž�ֱ����ʾ��������
			showWeather();
		}
		clearPrefs = (Button) findViewById(R.id.clear_prefs);
		clearPrefs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
				SharedPreferences.Editor editor = prefs.edit();
				editor.clear();
				editor.commit();
			}
		});
	}

	private void showWeather() {
		// TODO �Զ����ɵķ������
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
		cityNameText.setText(prefs.getString("city_name", ""));
		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("����"+prefs.getString("publish_time", "")+"����");
		currentDateText.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
	}
	private void queryWeatherCode(String countyCode) {
		// TODO �Զ����ɵķ������
		String address = "http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
		queryFromServer(address,"countyCode");
	}

	private void queryFromServer(final String address, final String type) {
		// TODO �Զ����ɵķ������
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {
				// TODO �Զ����ɵķ������
				if("countyCode".equals(type)){
					if(!TextUtils.isEmpty(response)){
						//�ӷ�����������������
						String[] array = response.split("\\|");
						if(array!=null&&array.length==2){
							String weatherCode = array[1];
							queryWeatherInfo(weatherCode);
						}
					}
				}else if("weatherCode".equals(type)){
					Utility.handleWeatherResponse(WeatherActivity.this, response);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO �Զ����ɵķ������
							showWeather();
						}
					});
				}
			}
			@Override
			public void onError(Exception e) {
				// TODO �Զ����ɵķ������
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO �Զ����ɵķ������
						publishText.setText("ͬ��ʧ��");
					}
				});
			}
		});
	}
	private void queryWeatherInfo(String weatherCode) {
		// TODO �Զ����ɵķ������
		String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		queryFromServer(address, "weatherCode");
	}
}
