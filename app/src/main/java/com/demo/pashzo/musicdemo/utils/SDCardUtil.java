/**
 * 
 */
package com.demo.pashzo.musicdemo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

/**
 * Description�?sd卡操作工具类<br/>
 * Copyright (c) ,2015 , janson <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:SDCardUtil.java <br/>
 * Date: 2015-9-21
 * 
 * @author 徐文�?
 * @version : 1.0
 */
public class SDCardUtil {
	/**
	 * 判断sd卡是否挂�?
	 * 
	 * @param contenxt
	 * @return true：挂载；false：卸�?
	 */
	public static boolean isMountSDCard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * 获取sd卡的根目�?
	 * 
	 * @return
	 */
	public static File getSDCardRootDir() {
		if (isMountSDCard()) {
			return Environment.getExternalStorageDirectory();
		}
		return null;
	}

	/**
	 * 获取sd卡全部存储空间的大小(单位M)
	 * 
	 * @return
	 */
	public static String getSDCardAllSpace() {
		// Retrieve overall information about the space on a filesystem. This is
		// a wrapper for Unix statvfs().
		
		// StatFs:封装了获取文件系统上�?��空间的一些方�?
		StatFs state = new StatFs(getSDCardRootDir().toString());
		long blockSize = state.getBlockSize();// 获得sd卡上每一个数据存储单元的大小（单位：字节�?
		long count = state.getBlockCount();// 获得sd卡上总共有多少个存储单元�?
		
		// long totalSpace= state.getTotalBytes();//Call requires API level 18
		// (current min is 8): android.os.StatFs#getTotalBytes
		
		DecimalFormat format = new DecimalFormat(".00");
		
		return format.format(blockSize * count / 1024d / 1024d);
	}
	
	/**
	 * 获取sd卡剩余存储空间的大小(单位M)
	 * 
	 * @return
	 */
	public static String getSDCardAvailableSpace() {

		// StatFs:封装了获取文件系统上�?��空间的一些方�?
		StatFs state = new StatFs(getSDCardRootDir().toString());
		long blockSize = state.getBlockSize();// 获得sd卡上每一个数据存储单元的大小（单位：字节�?
		long count = state.getAvailableBlocks();// 获得sd卡上剩余有多少个存储单元�?


		DecimalFormat format = new DecimalFormat(".00");

		return format.format(blockSize * count / 1024d / 1024d);
	}

	/**
	 * 向sd卡上指定的目录指定的文件中写入数�?
	 * 
	 * @param dir
	 * @param fileName
	 * @param datas
	 */
	public static boolean writeFileToSDCard(String dir, String fileName,
			byte[] datas) {
		File fileDir = new File(getSDCardRootDir(), dir);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		File operateFile = new File(fileDir, fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(operateFile);
			fos.write(datas);
			fos.flush();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 从sd卡中读取数据
	 * 
	 * @param dir
	 * @param fileName
	 */
	public static String readFileFromSDCard(String dir, String fileName,
			Context context) {
		File fileDir = new File(getSDCardRootDir(), dir);
		if (!fileDir.exists()) {
			Toast.makeText(context, "目录�? + dir + ", Toast.LENGTH_LONG)
					.show();
			return null;
		}

		File operateFile = new File(fileDir, fileName);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(operateFile);

			StringBuffer buffer = new StringBuffer();
			int len = 0;
			byte[] buf = new byte[8 * 1024];
			while ((len = fis.read(buf)) != -1) {
				buffer.append(new String(buf, 0, len));
			}

			return buffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
