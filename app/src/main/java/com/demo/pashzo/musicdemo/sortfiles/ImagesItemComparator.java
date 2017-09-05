package com.demo.pashzo.musicdemo.sortfiles;

import com.demo.pashzo.musicdemo.Bean.BeanLocalImagesMusic;

import java.util.Comparator;


public class ImagesItemComparator implements Comparator<BeanLocalImagesMusic> {


	@Override
	public int compare(BeanLocalImagesMusic lhs, BeanLocalImagesMusic rhs) {
		if (lhs==null||rhs==null) {
			return -1;
		}else {
			if (lhs.getPath().equals("")||rhs.getPath().equals("")) {
				return -1;
			}else {
				return lhs.getPath().compareTo(rhs.getPath());
			}
		}
	}
	

	
	
}
