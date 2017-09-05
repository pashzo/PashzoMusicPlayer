package com.demo.pashzo.musicdemo.music;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.demo.pashzo.musicdemo.utils.LogCat;

import java.util.ArrayList;


public class MusicService extends Service {

	public static final String tag = "musicService";
	MusiceSeviceBind musiceSeviceBind = new MusiceSeviceBind();
	private MusicPlayer musicPlayer;

	@Override
	public void onCreate() {
		musicPlayer = new MusicPlayer();
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return musiceSeviceBind;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		musicPlayer.releaseMedia();
		super.onDestroy();
	}

	public class MusiceSeviceBind extends Binder implements MusicControl {

		@Override
		public void play() {
			musicPlayer.play();
		}

		@Override
		public void start(int index) {
			musicPlayer.start(index);
		}

		@Override
		public void forward() {
			musicPlayer.forward();
		}

		@Override
		public void back() {
			musicPlayer.back();
		}

		@Override
		public void repeat() {
			musicPlayer.repeat();
		}

		@Override
		public long getCurrentPosion() {
			return musicPlayer.getMusicCurrent();
		}

		@Override
		public long getMusicDuration() {
			return musicPlayer.getMusicDuration();
		}

		@Override
		public void setMusicList(ArrayList<String> musicList) {
			musicPlayer.setMusicList(musicList);

		}

		@Override
		public boolean isPlay() {
			return musicPlayer.isPlay();
		}

		@Override
		public void setLyricPath(String lyricPath) {
			musicPlayer.setLyricPath(lyricPath);
		}

		@Override
		public String getLyricPath() {
			return musicPlayer.getLyricPath();
		}

		@Override
		public boolean isLyricChange() {
			return musicPlayer.isLyricChange();
		}

		@Override
		public void setLyricChange(boolean isLyricChange) {
			musicPlayer.setLyricChange(isLyricChange);

		}

		@Override
		public void releaseMedia() {
			musicPlayer.releaseMedia();

		}

		@Override
		public void getStopCallBack(MusicPlayer.StopCallBack stopCallBack) {
			LogCat.e("   -- getStopCallBack == ");
			musicPlayer.getStopCallBack(stopCallBack);

		}

		@Override
		public String getMusicName() {
			String path = musicPlayer.getPath();
			StringBuffer str = new StringBuffer(path);
			String filename = str.substring(str.lastIndexOf("/") + 1);
			String musicname = filename.substring(0, filename.lastIndexOf("."));
			return musicname;
		}

		@Override
		public void setCurrentMusicProgress(int progress) {
			musicPlayer.setCurrentMusicProgress(progress);
		}

	}

}
