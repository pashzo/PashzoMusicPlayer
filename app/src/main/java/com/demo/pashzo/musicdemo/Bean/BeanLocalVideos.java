package com.demo.pashzo.musicdemo.Bean;

public class BeanLocalVideos {
	private String array;
	
	public String getArray() {
		return array;
	}
	public void setArray(String array) {
		this.array = array;
	}



	public static class BeanLocalVideoInfo{
		private String title;
		private String type;
		private String video;
		private String image;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getVideo() {
			return video;
		}
		public void setVideo(String video) {
			this.video = video;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}




	}

}
