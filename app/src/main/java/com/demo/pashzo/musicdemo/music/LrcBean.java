package com.demo.pashzo.musicdemo.music;

import java.util.Map;

public class LrcBean {
	// 歌曲名称
	private String ti;
	// 歌手
	private String ar;
	// 专辑
	private String al;
	// 歌词制作者
	private String by;

	private long beginTime;

	private Map<Long, String> infos;

	public String getTi() {
		return ti;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	public String getAr() {
		return ar;
	}

	public void setAr(String ar) {
		this.ar = ar;
	}

	public String getAl() {
		return al;
	}

	public void setAl(String al) {
		this.al = al;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public Map<Long, String> getInfos() {
		return infos;
	}

	public void setInfos(Map<Long, String> infos) {
		this.infos = infos;
	}

	public static final String itemLRCPath = "lrcPath";
	public static final String isPlay = "isPlay";
	public static final String isSupport = "isSupport";
	public static final String isExistLRC = "isExist";
	public static final String lrcTopLeft = "lrcTopLeft";
	public static final String lrcTopRight = "lrcTopRight";
	public static final String lrcbroadcastIntent = "com.hz_3dworld.videoplayer.pictureviewer.lrcBroadcastReceiver";
}
