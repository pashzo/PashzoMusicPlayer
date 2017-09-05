package com.demo.pashzo.musicdemo.Bean;

import java.io.File;

public class BeanLocalFragment {
	
	String fragmentName;
	File file;
	String path;
	int level;
	
	public String getFragmentName() {
		return fragmentName;
	}
	public void setFragmentName(String fragmentName) {
		this.fragmentName = fragmentName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}



	public static final String LOCAL_FIFLES = "LocalFilesFragment";
	public static final String LOCAL_VIDEOS = "LocalVideosFragment";
	public static final String LOCAL_IMAGES = "LocalImagesFragment";
	public static final String LOCAL_MUSIC = "LocalMusicFragment";

	@Override
	public String toString() {
		return "BeanLocalFragment [fragmentName=" + fragmentName + ", file=" + file + ", path=" + path + ", level="
				+ level + "]";
	}

}
