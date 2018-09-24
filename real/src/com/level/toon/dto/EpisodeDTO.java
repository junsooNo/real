package com.level.toon.dto;

public class EpisodeDTO {
	private int toon_num;
	private int ep_num;
	private String ep_title;
	private String ep_main_image;
	private String ep_toon_image;
	private String ep_upload_date;
	private float ep_star_rating;
	private int view_count;
	private String first_status;
	private String saw_status;
	private String free;
	private String free_date;
	private int view_coin;
	private int d_day;
	
	public int getD_day() {
		return d_day;
	}
	public void setD_day(int d_day) {
		this.d_day = d_day;
	}
	public int getView_coin() {
		return view_coin;
	}
	public void setView_coin(int view_coin) {
		this.view_coin = view_coin;
	}
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	public String getFree_date() {
		return free_date;
	}
	public void setFree_date(String free_date) {
		this.free_date = free_date;
	}
	public String getSaw_status() {
		return saw_status;
	}
	public void setSaw_status(String saw_status) {
		this.saw_status = saw_status;
	}
	public String getFirst_status() {
	   return first_status;
	}
	public void setFirst_status(String first_status) {
	   this.first_status = first_status;
	}
	public int getToon_num() {
		return toon_num;
	}
	public void setToon_num(int toon_num) {
		this.toon_num = toon_num;
	}
	public int getEp_num() {
		return ep_num;
	}
	public void setEp_num(int ep_num) {
		this.ep_num = ep_num;
	}
	public String getEp_title() {
		return ep_title;
	}
	public void setEp_title(String ep_title) {
		this.ep_title = ep_title;
	}
	public String getEp_main_image() {
		return ep_main_image;
	}
	public void setEp_main_image(String ep_main_image) {
		this.ep_main_image = ep_main_image;
	}
	public String getEp_toon_image() {
		return ep_toon_image;
	}
	public void setEp_toon_image(String ep_toon_image) {
		this.ep_toon_image = ep_toon_image;
	}
	public String getEp_upload_date() {
		return ep_upload_date;
	}
	public void setEp_upload_date(String ep_upload_date) {
		this.ep_upload_date = ep_upload_date;
	}
	public float getEp_star_rating() {
		return ep_star_rating;
	}
	public void setEp_star_rating(float ep_star_rating) {
		this.ep_star_rating = ep_star_rating;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}	
}
