package com.demo.pashzo.musicdemo.sortfiles;

import com.demo.pashzo.musicdemo.Bean.BeanVideo;

import java.util.Comparator;


public class VideosComparator implements Comparator<BeanVideo> {

	@Override
	public int compare(BeanVideo lhs, BeanVideo rhs) {
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
