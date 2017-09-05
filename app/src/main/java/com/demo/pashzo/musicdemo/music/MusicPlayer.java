package com.demo.pashzo.musicdemo.music;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;

import com.demo.pashzo.musicdemo.utils.LogCat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


@SuppressLint("NewApi")
public class MusicPlayer implements OnPreparedListener, OnCompletionListener, OnErrorListener {
	private static final String tag = MusicPlayer.class.getSimpleName();
	private MediaPlayer mediaPlayer;
	private ArrayList<String> musicList;
	private LrcProcess lrcProcess;
	private String lyricPath;
	private boolean isMusicPlaying;// 音乐是否正在播放
	private boolean isMusicLoop;// 音乐是否在循环播放
	private int index;
	private boolean isLyricChange;
	private StopCallBack stopCallBack;
	private String path = "";
	private int errorNum = 0;//记录播放出错次数
	// 获取当前路径
	public String getPath() {
		return path;
	}

	public MusicPlayer() {
		lrcProcess = new LrcProcess();
		musicList = new ArrayList<>();
	}

	public void setMusicList(ArrayList<String> musicList) {
		this.musicList = musicList;
		LogCat.e("-----musicList == " + this.musicList.size());
	}

	public void prepare(String path) {
		try {
			releaseMedia();
			mediaPlayer = new MediaPlayer();
			mediaPlayer.reset(); // 重置
			// TODO
			mediaPlayer.setDataSource(path);
			LogCat.e(" music -- path == " + path);
			mediaPlayer.prepare();// 准备
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setLooping(isMusicLoop);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnErrorListener(this);
			isMusicPlaying = true;
			setLyricPath(musicList.get(index));
			getLyricPath();

		} catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}

	}

	public void play() {
		if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		} else if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	public void start(int index) {
		this.index = index;
		LogCat.e("---musicList===="+musicList.size()+"-index-==="+index);
		path = musicList.get(index);
		if (path == null || path.equals("") || !new File(path).exists()) {
			LogCat.e("---musicList2222===="+musicList.size());
			forward();
		} else {
			LogCat.e("---musicList333===="+musicList.size());
			prepare(path);
			stopCallBack.completionMusicPlayer();
		}
	}

	public void forward() {
		index++;
		index = revise(index);

		path = musicList.get(index);
		if (path == null || path.equals("") || !new File(path).exists()) {
			forward();
		}

		prepare(path);
		stopCallBack.completionMusicPlayer();
	}

	public void back() {
		index--;
		index = revise(index);
		path = musicList.get(index);
		if (path == null || path.equals("") || !new File(path).exists()) {
			forward();
		}
		prepare(path);
		stopCallBack.completionMusicPlayer();
	}

	public void getStopCallBack(StopCallBack stopCallBack) {
		LogCat.e("   -- getStopCallBack == ");
		this.stopCallBack = stopCallBack;
	}

	public void repeat() {
		if (mediaPlayer != null) {
			if (mediaPlayer.isLooping()) {
				isMusicLoop = false;
			} else {
				isMusicLoop = true;
			}
			mediaPlayer.setLooping(isMusicLoop);
		}
	}

	public boolean isPlay() {

		if (mediaPlayer != null) {
			return mediaPlayer.isPlaying();
		}
		return false;
	}

	public long getMusicCurrent() {

		if (isMusicPlaying) {
			return mediaPlayer.getCurrentPosition();
		}
		return 0;
	}

	public long getMusicDuration() {

		if (isMusicPlaying) {
			return mediaPlayer.getDuration();
		}
		return 0;
	}

	/*
	 * 释放mediaPlayer播放对象
	 */
	public void releaseMedia() {
		if (mediaPlayer != null) {
			try {
				isMusicPlaying = false;
				mediaPlayer.stop();
				mediaPlayer.release();// 释放
				mediaPlayer = null;
				System.gc();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setLyricPath(String lyricPath) {
		this.lyricPath = lyricPath;
	}

	public String getLyricPath() {

		String lrcPath = lyricPath;
		if (!lrcPath.endsWith(".lrc")) {
			int suffix = lrcPath.lastIndexOf(".");
			lrcPath = lrcPath.substring(0, suffix).trim() + ".lrc".trim();
		}
		setLyricChange(true);
		LogCat.e("   --- lrcPath == " + lrcPath);

		return lrcPath;
	}

	private int revise(int item) {
		if (item < 0) {
			index = musicList.size() - 1;
		}
		if (item >= musicList.size()) {
			index = 0;
		}

		return index;
	}

	public boolean isLyricChange() {

		return isLyricChange;
	}

	public void setLyricChange(boolean isLyricChange) {
		this.isLyricChange = isLyricChange;
		LogCat.e("--- isLyricChange == " + isLyricChange);
	}
	public void setCurrentMusicProgress(int progress){
		int targetPosition = (int) (progress * getMusicDuration() / 100);
		mediaPlayer.seekTo(targetPosition);
	}
	@Override
	public void onPrepared(MediaPlayer media) {
		media.start();

	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		LogCat.e("   -- onCompletion == " + isMusicLoop);
		if (isMusicLoop) {
			start(index);
		} else {
			forward();
		}
		LogCat.e("stopCallBack======" + stopCallBack);
		stopCallBack.completionMusicPlayer();
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		File file = new File(musicList.get(index));
		LogCat.e("  -- onError == " + arg1 + "  -- arg2 == " + arg2 + " -- file.exists() == " + file.exists());


		LogCat.d(" -- onError -- errorNum == " + errorNum);

		if (errorNum >= musicList.size() - 1) {
			releaseMedia();
			stopCallBack.stopMediaplyer();
			return true;
		}

		++errorNum ;
		forward();
		return true;
		
		/*if (arg1 == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
			releaseMedia();
			stopCallBack.stopMediaplyer();
		}*/
//		return true;
	}

	public interface StopCallBack {

		abstract void stopMediaplyer();

		abstract void completionMusicPlayer();
	}


}
