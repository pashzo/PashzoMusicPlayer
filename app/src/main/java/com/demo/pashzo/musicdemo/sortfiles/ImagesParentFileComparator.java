package com.demo.pashzo.musicdemo.sortfiles;

import com.demo.pashzo.musicdemo.Bean.BeanLocalImagesMusic;

import java.util.Comparator;


public class ImagesParentFileComparator implements Comparator<BeanLocalImagesMusic> {

	@Override
	public int compare(BeanLocalImagesMusic lhs, BeanLocalImagesMusic rhs) {
		if (lhs==null||rhs==null) {
			return -1;
		}else {
			if (lhs.getTitle().equals("")||rhs.getTitle().equals("")) {
				return -1;
			}else {
				return lhs.getTitle().compareTo(rhs.getTitle());
			}
		}
		
	}
	

	
	
}
