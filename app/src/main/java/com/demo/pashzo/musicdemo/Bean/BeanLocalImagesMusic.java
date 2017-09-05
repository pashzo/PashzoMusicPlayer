package com.demo.pashzo.musicdemo.Bean;

import android.graphics.Bitmap;

public class BeanLocalImagesMusic {

	private int id;

	private String path;

	private String title = "";

	private long size;
	/**
	 * 文件夹名
	 */
	private String folderName;

	private String parentFile;

	private int counts;

	private String duration;

	private String artist;

	private String TopImagePath;

	private String groupMusic;

	private String type;

	private Bitmap bitmap;

	private boolean islie;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getParentFile() {
		return parentFile;
	}

	public void setParentFile(String parentFile) {
		this.parentFile = parentFile;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTopImagePath() {
		return TopImagePath;
	}

	public void setTopImagePath(String topImagePath) {
		TopImagePath = topImagePath;
	}

	public String getGroupMusic() {
		return groupMusic;
	}

	public void setGroupMusic(String groupMusic) {
		this.groupMusic = groupMusic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public boolean isIslie() {
		return islie;
	}

	public void setIslie(boolean islie) {
		this.islie = islie;
	}

	public BeanLocalImagesMusic() {
		super();
	}

	public BeanLocalImagesMusic(boolean islie) {
		super();
		this.islie = islie;
	}

	public BeanLocalImagesMusic(int id, String path, String title, long size, String folderName, String parentFile,
			int counts, String duration, String artist, String topImagePath, String groupMusic, String type,
			Bitmap bitmap) {
		super();
		this.id = id;
		this.path = path;
		this.title = title;
		this.size = size;
		this.folderName = folderName;
		this.parentFile = parentFile;
		this.counts = counts;
		this.duration = duration;
		this.artist = artist;
		TopImagePath = topImagePath;
		this.groupMusic = groupMusic;
		this.type = type;
		this.bitmap = bitmap;
	}

	public static final String mImagesPath = "/3Dworld/3D图片/";
	public static final String mMusicPath = "/3Dworld/音乐/";
	public static final String _itemList = "itemList";
	public static final String _indext = "indext";
	public static final String _type = "type";
	public static final String _parent = "parent";
	public static final String IMAGES = "images";
	public static final String MUSIC = "music";

}
