package com.demo.pashzo.musicdemo.Bean;

import android.graphics.Bitmap;

/**
 * 
 */
public class BeanFile {
	String folderPath = "";// 文件地址
	int folderNumber = 0;// 文件数量
	String folderCapacity = "";// 存储空间
	String folderUsableSpace = "";// 已用空间
	String fileTitle = "";// 文件名称
	boolean _isFile;// true 为文 false为目录
	Bitmap bitmap;

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public int getFolderNumber() {
		return folderNumber;
	}

	public void setFolderNumber(int folderNumber) {
		this.folderNumber = folderNumber;
	}

	public String getFolderCapacity() {
		return folderCapacity;
	}

	public void setFolderCapacity(String folderCapacity) {
		this.folderCapacity = folderCapacity;
	}

	public String getFolderUsableSpace() {
		return folderUsableSpace;
	}

	public void setFolderUsableSpace(String folderUsableSpace) {
		this.folderUsableSpace = folderUsableSpace;
	}

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	public boolean is_isFile() {
		return _isFile;
	}

	public void set_isFile(boolean _isFile) {
		this._isFile = _isFile;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public String toString() {
		return "BeanFile [folderPath=" + folderPath + ", folderNumber=" + folderNumber + ", folderCapacity="
				+ folderCapacity + ", folderUsableSpace=" + folderUsableSpace + ", fileTitle=" + fileTitle
				+ ", _isFile=" + _isFile + ", bitmap=" + bitmap + "]";
	}

	public static final String images = "图片";
	public static final String music = "音乐";
	public static final String videos = "视频";
	public static final String lyrics = "歌词";

	public static final String intentMemoryCardFolderPath = "MemoryCardFolderPath";

	public static final String intentVideoList = "intentVideoList";
	public static final String intentImagesList = "intentImagesList";
	public static final String intentMusicList = "intentMusicList";
	public static final String intentLyricsList = "intentMusicList";

}
