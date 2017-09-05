package com.demo.pashzo.musicdemo.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;

import com.demo.pashzo.musicdemo.Bean.BeanFile;
import com.demo.pashzo.musicdemo.Bean.BeanLocalImagesMusic;
import com.demo.pashzo.musicdemo.Bean.BeanSortModel;
import com.demo.pashzo.musicdemo.Bean.BeanVideo;
import com.demo.pashzo.musicdemo.sortfiles.CharacterParser;
import com.demo.pashzo.musicdemo.sortfiles.ImagesItemComparator;
import com.demo.pashzo.musicdemo.sortfiles.ImagesParentFileComparator;
import com.demo.pashzo.musicdemo.sortfiles.PinyinComparator;
import com.demo.pashzo.musicdemo.sortfiles.VideosComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kylin on 1/20/15.
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class MediaList {
	private static final String tag = MediaList.class.toString();
	public static String hz_3dworld = "/3Dworld/";
	public static String music = "music/";

	/**
	 * 图片文件，如果是同一个文件夹下的，归为一类。
	 * 
	 * @return
	 */
	public static HashMap<String, ArrayList<BeanLocalImagesMusic>> getImagesHashMap(Context context) {
		HashMap<String, ArrayList<BeanLocalImagesMusic>> imagesGruopMap = new HashMap<>();

		String myImages = "%" + BeanLocalImagesMusic.mImagesPath + "%";
		Cursor imgCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
				MediaStore.Files.FileColumns.DATA + " like ? ", new String[] { myImages },
				MediaStore.Images.Media.DEFAULT_SORT_ORDER);
		try {
			if (imgCursor.getCount() > 0) {
				while (imgCursor.moveToNext()) {
					String url = imgCursor.getString(imgCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
					String paretFileTitle = imgCursor
							.getString(imgCursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
					// LogCat.d(" -- paretFileTitle == " + paretFileTitle + " --
					// url == "+url);
					String title = imgCursor.getString(imgCursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
					// LogCat.d(" -- title == " + title + " -- url == "+url);
					BeanLocalImagesMusic beanLocalImages = new BeanLocalImagesMusic();
					beanLocalImages.setPath(url);
					beanLocalImages.setType(beanLocalImages.IMAGES);
					beanLocalImages.setTitle(title);
					if (imagesGruopMap.containsKey(paretFileTitle)) {
						imagesGruopMap.get(paretFileTitle).add(beanLocalImages);
					} else {
						ArrayList<BeanLocalImagesMusic> imageList = new ArrayList<>();
						imageList.add(beanLocalImages);
						imagesGruopMap.put(paretFileTitle, imageList);
						// LogCat.d(" -- imageList.size() ==
						// "+imageList.size());
					}
				}
			}
		} catch (Exception e) {

		} finally {
			try {
				if (imgCursor != null) {
					imgCursor.close();
				}
			} catch (Exception e) {

				System.out.println("--cursorM==" + e);
			}
		}
		return imagesGruopMap;

	};

	/**
	 * 图片列表。
	 * 
	 * @return
	 */
	public static ArrayList<String> getImagesList(Context context) {
		ArrayList<String> list = new ArrayList<>();

		String myImages = "%" + BeanLocalImagesMusic.mImagesPath + "%";
		Cursor imgCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
				MediaStore.Files.FileColumns.DATA + " like ? ", new String[] { myImages },
				MediaStore.Images.Media.DEFAULT_SORT_ORDER);
		try {
			if (imgCursor.getCount() > 0) {
				while (imgCursor.moveToNext()) {
					String url = imgCursor.getString(imgCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
					list.add(url);
				}
			}
		} catch (Exception e) {

		} finally {
			try {
				if (imgCursor != null) {

					imgCursor.close();
				}
			} catch (Exception e) {

				System.out.println("--cursorM==" + e);
			}
		}
		return list;

	};

	/**
	 * 获取文件夹的名称放到listView中，用于显示文件名以及文件夹下第一张图片
	 * 
	 * @param listHashMap
	 * @return
	 */

	public static ArrayList<BeanLocalImagesMusic> getParentList(
			HashMap<String, ArrayList<BeanLocalImagesMusic>> listHashMap, String type) {
		ArrayList<BeanLocalImagesMusic> list = new ArrayList<>();
		// LogCat.d(" -- listHashMap.size() == "+listHashMap.size());

		Iterator<Map.Entry<String, ArrayList<BeanLocalImagesMusic>>> iterator = listHashMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, ArrayList<BeanLocalImagesMusic>> entry = iterator.next();
			ArrayList<BeanLocalImagesMusic> imagesList = entry.getValue();
			// LogCat.d(" -- imagesList.size() == "+imagesList.size());
			ImagesItemComparator ImagesComparator = new ImagesItemComparator();
			Collections.sort(imagesList, (Comparator<? super BeanLocalImagesMusic>) ImagesComparator);
			if (imagesList.size() > 0) {
				String key = entry.getKey();
				LogCat.d("   -- key == " + key);
				String imagePath = imagesList.get(0).getPath();
				BeanLocalImagesMusic inforBean = new BeanLocalImagesMusic();
				inforBean.setTitle(key);
				inforBean.setPath(imagePath);
				inforBean.setType(type);
				inforBean.setCounts(imagesList.size());
				list.add(inforBean);
			}
		}
		// if (list.size() == 1) {
		// list.add(new BeanLocalImagesMusic());
		// }

		ImagesParentFileComparator imagesParentFile = new ImagesParentFileComparator();
		Collections.sort(list, (Comparator<? super BeanLocalImagesMusic>) imagesParentFile);
		LogCat.d("   -- list.size() == " + list.size());
		return list;
	}

	/**
	 * 音乐列表
	 * 
	 * @param context
	 * @return
	 */
	public static HashMap<String, ArrayList<BeanLocalImagesMusic>> getMusicHashMap(Context context) {
		HashMap<String, ArrayList<BeanLocalImagesMusic>> mGroupMap1 = new HashMap<>();
//		LogCat.d("---- 111(mGroupMap1 == null) == " + (mGroupMap1 == null));
		String musicPath = "%" + BeanLocalImagesMusic.mMusicPath + "%";
		String music = "1";
		String selection = MediaStore.Files.FileColumns.DATA + " like ? " + " AND " + AudioColumns.IS_MUSIC + " = ? ";
		Cursor audioCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
				selection, new String[] { musicPath, music }, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		try {
			if (audioCursor.getCount() > 0) {
				while (audioCursor.moveToNext()) {

					int _id = audioCursor.getInt(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));

					String _title = audioCursor
							.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));

					String _artist = audioCursor
							.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));

					String _path = audioCursor
							.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

					long duration = audioCursor
							.getInt(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

					String _duration = FormatTime.formatTime(duration / 1000);
//					LogCat.d("在这个数据库里的字段为============:" + _path);

					long _size = audioCursor.getLong(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
					// LogCat.d(" __title == "+_title);
					BeanLocalImagesMusic beanLocalMusic = new BeanLocalImagesMusic();

					beanLocalMusic.setId(_id);
					beanLocalMusic.setTitle(_title);
					beanLocalMusic.setArtist(_artist);
					beanLocalMusic.setPath(_path);
					beanLocalMusic.setDuration(_duration);
					beanLocalMusic.setSize(_size);
					beanLocalMusic.setType(beanLocalMusic.MUSIC);
					if (_artist.equals("<unknown>") || beanLocalMusic.getArtist() == null) {
						beanLocalMusic.setArtist("unknown");
					}
					if (_path != null && !_path.equals("") && !_path.endsWith(".ogg")) {
						String parentTitle = new File(_path).getParentFile().getName();
//						LogCat.d("---- parentTitle == " + parentTitle);
//						LogCat.d("---- 222(mGroupMap1 == null) == " + (mGroupMap1 == null));
						if (mGroupMap1 == null)
							return null;
						if (mGroupMap1.containsKey(parentTitle)) {
							mGroupMap1.get(parentTitle).add(beanLocalMusic);
						} else {

							ArrayList<BeanLocalImagesMusic> list = new ArrayList<>();
							list.add(beanLocalMusic);
							mGroupMap1.put(parentTitle, list);
						}
					}
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				if (audioCursor != null) {

					audioCursor.close();
				}
			} catch (Exception e) {

				System.out.println("--cursorM==" + e);
			}
		}
		return mGroupMap1;

	}

	public static ArrayList<String> getMusicList(Context context) {
		ArrayList<String> list = new ArrayList<>();
		String music = "1";
		String selection = MediaStore.Files.FileColumns.DATA + " like ? " + " AND " + AudioColumns.IS_MUSIC + " = ? ";
		Cursor audioCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
				AudioColumns.IS_MUSIC + " = ? ", new String[] { "1" }, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		LogCat.d("   -- getCount == " + audioCursor.getCount());
		try {
			if (audioCursor.getCount() > 0) {
				while (audioCursor.moveToNext()) {
					String _path = audioCursor
							.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
					LogCat.d("   -- _path == " + _path);
					list.add(_path);
				}
			}
		} catch (Exception e) {

		} finally {
			try {
				if (audioCursor != null) {
					audioCursor.close();
				}
			} catch (Exception e) {

			}
		}

		return list;
	}

	/**
	 * 获取视频列表
	 *
	 * @param context
	 * @return
	 */

	public static ArrayList<BeanVideo> getVideoList(Context context) {
		ContentValues gCValues = new ContentValues();
		String tag = "getVideoList";
		int LRmode = 3;

		ArrayList<BeanVideo> mList = new ArrayList<>();

		BeanVideo mVideoBean;

		long startTime = 0;
		long endTime = 0;

		double folderSize = 0;
		String duration;
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		// 首先检索SDcard上所有的video
		ContentResolver cr = context.getContentResolver();
		Cursor cursorM = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
				MediaStore.Files.FileColumns.DATA + " NOT  like ? ", new String[] { "%.dat" }, null);
		try {
			if (cursorM != null && cursorM.getCount() != 0) {

				while (cursorM.moveToNext()) {
					// 视频大小
					Double medoSize = cursorM.getDouble(cursorM.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
					if (medoSize > 0) {

						mVideoBean = new BeanVideo();
						mVideoBean.setId(cursorM.getInt(cursorM.getColumnIndexOrThrow(MediaStore.Video.Media._ID)));
						// 路径
						mVideoBean
								.setPath(cursorM.getString(cursorM.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
						// 视频类型
						// String mimeType =
						// cursorM.getString(cursorM.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
						// 视频名称
						mVideoBean.setTitle(
								cursorM.getString(cursorM.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
						// 视频时长
						Double duration1 = cursorM
								.getDouble(cursorM.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
						// 视频分辨率
						mVideoBean.setResolution(
								cursorM.getString(cursorM.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)));
						//
						// iso格式的视频或报错
						duration = FormatTime.formatTime(duration1 / 1000);

						mVideoBean.setTime(duration);

						mVideoBean.setLength(medoSize);
						mVideoBean.setLrmode(LRmode);

						folderSize += medoSize;
						mVideoBean.setFolderSize(folderSize);
						mList.add(mVideoBean);
					}
				}

			}
		} catch (Exception e) {

		} finally {
			try {
				if (cursorM != null) {

					cursorM.close();
				}
			} catch (Exception e) {

				System.out.println("--cursorM==" + e);
			}
		}

		VideosComparator videosComparator = new VideosComparator();
		Collections.sort(mList, (Comparator<? super BeanVideo>) videosComparator);

		return mList;

	}

	public static ArrayList<BeanFile> getLocalFiles(String path) {
		ArrayList<BeanFile> list = new ArrayList<>();
		File file = new File(path);
		if (file != null && file.exists() && file.canWrite() && file.isDirectory()) {
			File[] files = file.listFiles();
			for (File itemFile : files) {
				BeanFile beanFile = new BeanFile();
				beanFile.setFileTitle(itemFile.getName());
				beanFile.setFolderPath(itemFile.getPath());
				list.add(beanFile);
			}
		}
		return list;
	}

	/**
	 * 获取歌词列表
	 *
	 * @param context
	 * @return
	 */
	public static ArrayList<BeanSortModel> getLyricList(Context context) {
		String myImages = "%.lrc";
		long folderSize = 0;
		ArrayList<BeanSortModel> list = new ArrayList<>();
		CharacterParser characterParser = CharacterParser.getInstance();
		PinyinComparator pinyinComparator = new PinyinComparator();
		// System.out.println("--------------path=="+SDCardPath);
		Cursor lyricCursor = context.getContentResolver().query(Uri.parse("content://media/external/file"), null,
				MediaStore.Files.FileColumns.DATA + " like ? ", new String[] { myImages },
				MediaStore.Images.Media.DEFAULT_SORT_ORDER);
		try {
			if (lyricCursor.getCount() > 0) {
				while (lyricCursor.moveToNext()) {
					String url = lyricCursor.getString(lyricCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
					String title = lyricCursor
							.getString(lyricCursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));

					BeanSortModel sortModel = new BeanSortModel();
					// LogCat.d(" ----- "," --- url == "+ url);
					title = title.concat("    --    " + url);
					sortModel.setTitle(title);
					sortModel.setPath(url);
					int sortSize = lyricCursor.getInt(lyricCursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
					sortModel.setSize(sortSize);
					// 汉字转换成拼音
					String pinyin = characterParser.getSelling(title);
					String sortString = pinyin.substring(0, 1).toUpperCase();

					// 正则表达式，判断首字母是否是英文字母
					if (sortString.matches("[A-Z]")) {
						sortModel.setSortLetters(sortString.toUpperCase());
					} else {
						sortModel.setSortLetters("#");
					}

					folderSize += sortSize;
					sortModel.setFolderSize(folderSize);

					list.add(sortModel);
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				if (lyricCursor != null) {

					lyricCursor.close();

				}
			} catch (Exception e) {

				System.out.println("--lyricCursor==" + e);
			}
		}
		// 根据a-z进行排序源数据
		Collections.sort(list, (Comparator<? super BeanSortModel>) pinyinComparator);
		return list;

	}

	/**
	 * 获取文件列表
	 *
	 * @param url
	 * @return
	 */

	public static void getFileList(Context context, String url, ArrayList<BeanFile> list,
			FileListCallBack fileListCallBack) {

		BeanFile fileListBean = null;

		File file = new File(url);
		if (file.exists()) {

			if (file.isDirectory()) {
				try {
					if (file.listFiles().length == 0) {
						fileListCallBack.notifyAdapter();
					} else {
						for (File f : file.listFiles()) {
							if (!f.getName().startsWith(".")) {
								fileListBean = new BeanFile();
								// LogCat.d(tag, " title = " + f.getName());

								if (f.isDirectory()) {
									try {

										fileListBean.setFolderNumber(f.list().length);
									} catch (Exception e) {
									}

									fileListBean.set_isFile(false);
								} else {
									try {

										fileListBean.setFolderUsableSpace(FileSize.FormetFileSize((double) f.length()));
									} catch (Exception e) {
									}

									fileListBean.set_isFile(true);
								}

								fileListBean.setFileTitle(f.getName());
								fileListBean.setFolderPath(f.getPath());
								list.add(fileListBean);
								fileListCallBack.notifyAdapter();
							}
						}
					}
				} catch (Exception e) {
				}

			}

		}

	}

	public interface FileListCallBack {

		public abstract void notifyAdapter();
	}

}
