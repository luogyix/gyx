package com.agree.abt.model.confManager;

import java.io.Serializable;

public class BtTsfAdvert implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4155203680411371309L;
	private String advert_id;
	private String branch;
	private String play_time;
	private String play_interval;
	private String video_interval;
	private String photos_path;
	private String photos_md5;
	public String getPhotos_path() {
		return photos_path;
	}
	public void setPhotos_path(String photos_path) {
		this.photos_path = photos_path;
	}
	public String getPhotos_md5() {
		return photos_md5;
	}
	public void setPhotos_md5(String photos_md5) {
		this.photos_md5 = photos_md5;
	}
	public String getAdvert_id() {
		return advert_id;
	}
	public void setAdvert_id(String advert_id) {
		this.advert_id = advert_id;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getPlay_time() {
		return play_time;
	}
	public void setPlay_time(String play_time) {
		this.play_time = play_time;
	}
	public String getPlay_interval() {
		return play_interval;
	}
	public void setPlay_interval(String play_interval) {
		this.play_interval = play_interval;
	}
	public BtTsfAdvert() {
		super();
	}
	public String getVideo_interval() {
		return video_interval;
	}
	public void setVideo_interval(String video_interval) {
		this.video_interval = video_interval;
	}
	
	
}
