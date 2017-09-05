package com.demo.pashzo.musicdemo.music;

import java.util.ArrayList;


public interface MusicControl {
	public void setMusicList(ArrayList<String> musicList);

	public void play();

	public void start(int index);

	public void forward();

	public void back();

	public void getStopCallBack(MusicPlayer.StopCallBack stopCallBack);

	public long getCurrentPosion();

	public long getMusicDuration();

	public void repeat();

	public boolean isPlay();

	public void setLyricPath(String lyricPath);

	public String getLyricPath();

	public boolean isLyricChange();

	public void releaseMedia();

	public void setLyricChange(boolean isLyricChange);

	public String getMusicName();

	public void setCurrentMusicProgress(int progress);
}
