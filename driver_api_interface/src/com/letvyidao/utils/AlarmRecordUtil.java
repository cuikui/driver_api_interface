package com.letvyidao.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AlarmRecordUtil {
	/**
	 * 读取alarmRecord文件key所对应的值
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static int getAlarmRecordProperties(String key) throws IOException{
		Properties p = new Properties();
		FileReader reader = new FileReader(new File("dataFiles/alarmRecord.properties"));
		p.load(reader);
		reader.close();
		return Integer.parseInt(p.getProperty(key));
	}

	/**
	 * 修改alarmRecord文件中key的值
	 * @param key  需要修改的key
	 * @param value 与key对应的value值
	 * @throws IOException
	 */
	public static void setAlarmRecordProperties(String key, Integer value) throws IOException {
		Properties p = new Properties();
		FileReader reader = new FileReader(new File("dataFiles/alarmRecord.properties"));
		p.load(reader);
		reader.close();
		FileOutputStream  fos = new FileOutputStream (new File("dataFiles/alarmRecord.properties"));
		p.setProperty(key,value+"");
	    p.store(fos ,"Update '" + key + "'+ '"+value);
	    fos.close();
	}

	@SuppressWarnings("unused")
	public static boolean alarmRecord(String key, int flag) throws IOException {
		AlarmRecordUtil alarmRecordUtil = new AlarmRecordUtil();
		switch(flag){
			case 1:
				int alarmRecordProperties = alarmRecordUtil.getAlarmRecordProperties(key);
				if(alarmRecordProperties < 5){
					alarmRecordUtil.setAlarmRecordProperties(key, alarmRecordProperties+1);
					return true;
				}else{
					alarmRecordUtil.setAlarmRecordProperties(key, alarmRecordProperties+1);
					return false;
				}
			case 2:
				int alarmRecord = alarmRecordUtil.getAlarmRecordProperties(key);
				if(alarmRecord!=0) alarmRecordUtil.setAlarmRecordProperties(key, 0);
				return false;
			default:
				return false;
		}
	}
}
