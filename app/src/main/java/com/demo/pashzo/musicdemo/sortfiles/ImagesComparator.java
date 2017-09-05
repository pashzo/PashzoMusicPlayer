package com.demo.pashzo.musicdemo.sortfiles;

import java.util.Comparator;

public class ImagesComparator implements Comparator<String> {

	@Override
	public int compare(String lhs, String rhs) {
		if (lhs==null||rhs==null) {
			return -1;
		}else {
			if (lhs.equals("")||rhs.equals("")) {
				return -1;
			}else {
				return lhs.compareTo(rhs);
			}
		}
		
	}
	

	
	
}
