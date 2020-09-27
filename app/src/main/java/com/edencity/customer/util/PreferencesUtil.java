package com.edencity.customer.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.List;

public class PreferencesUtil {

//	private static PreferencesUtil util;
	private SharedPreferences spf;
	private static String separator ="__";
	
	public PreferencesUtil(Application app,String name) {
		spf= app.getSharedPreferences(name, Application.MODE_PRIVATE);
	}
	
//	public static PreferencesUtil getPreferences(){
//		if (util==null)
//			util=new PreferencesUtil(defaultPre);
//		return util;
//	}
	
	
	public void set(String key, boolean value) {
		if(spf==null)	return;
		Editor e = spf.edit();
		e.putBoolean(key, value);
		e.apply();
	}

	public  void set(String key, int value) {
		if(spf==null)	return;
		Editor e = spf.edit();
		e.putInt(key, value);
		e.apply();
	}

	public  void set(String key, float value) {
		if(spf==null)	return;
		Editor e = spf.edit();
		e.putFloat(key, value);
		e.apply();
	}


	public  void set(String key, String value) {
		if(spf==null)	return;
		
		Editor e = spf.edit();
		if(value==null)
			e.remove(key);
		else
			e.putString(key, value);

		e.apply();
	}
	
	public  void remove(String key){
		if(spf==null)	return;
		Editor e = spf.edit();
		e.remove(key);
		e.apply();
	}
	public  void setIntArray(String key, List<Integer> value) {
		if(spf==null ||value==null || value.size()==0)	return;
		String vs=null;
		if(value.size()==1){
			vs=String.valueOf(value.get(0).intValue());
		}else{
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<value.size();i++){
				if(i>0){
					sb.append(separator);
				}
				sb.append(String.valueOf(value.get(i).intValue()));
			}
			vs=sb.toString();
		}
		
		set(key, vs);
	}
	
	public  void setIntArray(String key, int[] value) {
		if(spf==null ||value==null || value.length==0)	return;
		String vs=null;
		if(value.length==1){
			vs=String.valueOf(value[0]);
		}else{
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<value.length;i++){
				if(i>0){
					sb.append(separator);
				}
				sb.append(String.valueOf(value[i]));
			}
			vs=sb.toString();
		}
		
		set(key,vs);
	}
	
	public  void setStringArray(String key, List<String> value) {
		if(spf==null ||value==null || value.size()==0)	return;
		String vs=null;
		if(value.size()==1){
			vs=value.get(0);
		}else{
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<value.size();i++){
				if(i>0){
					sb.append(separator);
				}
				sb.append(value.get(i));
			}
			vs=sb.toString();
		}
		set(key,vs);
	}
	
	public  List<Integer> getIntList(String key){
		String sv=getString(key);
		if(sv==null)	return null;
		String[] sva = sv.split(separator);
		List<Integer> ret=new ArrayList<Integer>();
		for(int i=0;i<sva.length;i++){
			if(sva[i].trim().length()<1)
				continue;
			
			ret.add(Integer.valueOf(sva[i]));
		}
		if(ret.size()==0)
			return null;
		else
			return ret;
	}
	
	public  int[] getIntArray(String key){
		String sv=getString(key);
		if(sv==null)	return null;
		String[] sva = sv.split(separator);
		if(sva==null || sva.length==0)
			return null;
		
		int[] ret=new int[sva.length];
		for(int i=0;i<sva.length;i++){
			ret[i]=0;
			if(sva[i].trim().length()<1)
				continue;
			ret[i]=Integer.parseInt(sva[i]);
		}
		return ret;
	}
	
	public  List<String> getStringArray(String key){
		String sv=getString(key);
		if(sv==null)	return null;
		String[] sva = sv.split(separator);
		List<String> ret=new ArrayList<String>();
		for(int i=0;i<sva.length;i++){
			if(sva[i]==null || sva[i].trim().length()<1)
				continue;
			
			ret.add(sva[i]);
		}
		if(ret.size()==0)
			return null;
		else
			return ret;
	}
	
	
	public  boolean getBoolean(String key){
		if(spf==null)	return false;
		return spf.getBoolean(key, false);
	}
	
	public  int getInt(String key){
		if(spf==null)	return 0;
		return spf.getInt(key, 0);
	}

	public  float getFloat(String key,float value){
		if(spf==null)	return value;
		return spf.getFloat(key, value);
	}


	public  int getInt(String key,int value){
		if(spf==null)	return value;
		return spf.getInt(key, value);
	}
	
	public  String getString(String key){
		if(spf==null)	return null;
		return spf.getString(key,null);
	}
	
}
