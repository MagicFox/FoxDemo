package com.example.fox.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class FileUtils {

	public static final String CACHE_PATH= Environment.getExternalStorageDirectory()+ File.separator+"GongPei";
	public static String imageFolderPath = CACHE_PATH + File.separator + "image" + File.separator;
	public static String logPath = CACHE_PATH + File.separator + "log" + File.separator;
	/**
	 * 是否有外存卡
	 * 
	 * @return
	 */
	public static boolean isExistExternalStore() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建文件夹
	 * @param path
	 */
	public static void createFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	
	/**
	 * 获取手机自身内存路径
	 * 
	 */
	public static String getPhoneCardPath() {
		return Environment.getDataDirectory().getPath();
	}

	/**
	 * 获取sd卡路径 双sd卡时，根据”设置“里面的数据存储位置选择，获得的是内置sd卡或外置sd卡
	 * 
	 * @return
	 */
	public static String getNormalSDCardPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	/**
	 * 创建.nomedia文件，禁止图库扫描
	 */
	private static void createNoMedia(){
		File nomedia = new File(CACHE_PATH + ".nomedia" );
		if (! nomedia.exists())
			try {
				nomedia.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

	public static void forceMkdir(File directory) throws IOException {
		String message;
		if(directory.exists()) {
			if(!directory.isDirectory()) {
				message = "File " + directory + " exists and is " + "not a directory. Unable to create directory.";
				throw new IOException(message);
			}
		} else if(!directory.mkdirs() && !directory.isDirectory()) {
			message = "Unable to create directory " + directory;
			throw new IOException(message);
		}

	}

	/**
	 * 创建目录
	 * @param directory
	 * @return
     */
	public static boolean mkdirs(File directory) {
		try {
			forceMkdir(directory);
			return true;
		} catch (IOException var2) {
			return false;
		}
	}
}
