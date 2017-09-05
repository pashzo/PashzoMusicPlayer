package com.demo.pashzo.musicdemo.Bean;

import android.graphics.Bitmap;

/**
 * Created by kylin on 1/20/15.
 */
public class BeanVideo {
    private int id;
    private String title; //名称
    private String path;//路径
    private double length;//文件大小
    private String time;//时长
    private String resolution;//分辨率
    private int lrmode;//左右格式
    private double folderSize;
    private Bitmap bitmap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getLrmode() {
        return lrmode;
    }

    public void setLrmode(int lrmode) {
        this.lrmode = lrmode;
    }

    public double getFolderSize() {
        return folderSize;
    }

    public void setFolderSize(double folderSize) {
        this.folderSize = folderSize;
    }

    public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}




	public static final String _id = "_id";
    public static final String _title = "_title";
    public static final String _path = "_path";
    public static final String _length = "_length";
    public static final String _time = "_time";
    public static final String _resolution = "_resolution";
    public static final String _lrmode = "_lrmode";

}
