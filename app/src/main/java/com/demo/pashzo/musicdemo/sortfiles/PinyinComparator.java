package com.demo.pashzo.musicdemo.sortfiles;

import com.demo.pashzo.musicdemo.Bean.BeanSortModel;

import java.util.Comparator;



/**
 * 
 * @author 
 *
 */
public class PinyinComparator implements Comparator<BeanSortModel> {

	public int compare(BeanSortModel o1, BeanSortModel o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
