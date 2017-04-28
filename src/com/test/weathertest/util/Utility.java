package com.test.weathertest.util;

import android.text.TextUtils;

import com.test.weathertest.db.WeatherDB;
import com.test.weathertest.model.City;
import com.test.weathertest.model.County;
import com.test.weathertest.model.Province;

public class Utility {
	/*
	 * 解析和处理服务器返回的省级数据
	 * */
	public synchronized  static boolean handleProvincesResponse(WeatherDB weatherDB,String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
			if(allProvinces!=null&&allProvinces.length>0){
				for(String p : allProvinces){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					weatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	/*
	 * 解析和处理服务器返回的市级数据
	 * */
	public synchronized static boolean handleCitiesResponse(WeatherDB weatherDB,String response,int provinceId){
		if(!TextUtils.isEmpty(response)){
			String[] allCities =response.split(",");
			if(allCities!=null&&allCities.length>0){
				for(String c:allCities){
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					weatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	/*
	 * 解析和处理服务器返回的县级数据
	 * */
	public synchronized static boolean handleCountiesResponse(WeatherDB weatherDB,String response,int cityId){
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = response.split(",");
			if(allCounties!=null&&allCounties.length>0){
				for(String c : allCounties){
					String[] array = c.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					weatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
}
