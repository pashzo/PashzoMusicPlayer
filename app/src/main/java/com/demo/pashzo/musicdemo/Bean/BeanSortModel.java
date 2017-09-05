package com.demo.pashzo.musicdemo.Bean;

public class BeanSortModel {

    private String title;
    private String path;
    private String sortLetters;
    private int size;
    private long folderSize;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getFolderSize() {
        return folderSize;
    }

    public void setFolderSize(long folderSize) {
        this.folderSize = folderSize;
    }
}
