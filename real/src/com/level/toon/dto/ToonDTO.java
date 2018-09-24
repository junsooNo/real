package com.level.toon.dto;

public class ToonDTO extends ObjectDTO {
	private int toon_num;
	private String main_image;
	private String main_image2;
	private int cate_num;
	private String toon_title;
	private String toon_writer;
	private String toon_painter;
	private String upload_day;
	private String upload_type;
	private String toon_info;
	private String reg_date;
	private String toon_info_sub;
	public String getToon_info_sub() {
		return toon_info_sub;
	}
	public void setToon_info_sub(String toon_info_sub) {
		this.toon_info_sub = toon_info_sub;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getMain_image2() {
		return main_image2;
	}
	public void setMain_image2(String main_image2) {
		this.main_image2 = main_image2;
	}
	public String getToon_info() {
		return toon_info;
	}
	public void setToon_info(String toon_info) {
		this.toon_info = toon_info;
	}
	public int getToon_num() {
		return toon_num;
	}
	public void setToon_num(int toon_num) {
		this.toon_num = toon_num;
	}
	public String getMain_image() {
		return main_image;
	}
	public void setMain_image(String main_image) {
		this.main_image = main_image;
	}
	public int getCate_num() {
		return cate_num;
	}
	public void setCate_num(int cate_num) {
		this.cate_num = cate_num;
	}
	public String getToon_title() {
		return toon_title;
	}
	public void setToon_title(String toon_title) {
		this.toon_title = toon_title;
	}
	public String getToon_writer() {
		return toon_writer;
	}
	public void setToon_writer(String toon_writer) {
		this.toon_writer = toon_writer;
	}
	public String getToon_painter() {
		return toon_painter;
	}
	public void setToon_painter(String toon_painter) {
		this.toon_painter = toon_painter;
	}
	public String getUpload_day() {
		return upload_day;
	}
	public void setUpload_day(String upload_day) {
		this.upload_day = upload_day;
	}
	public String getUpload_type() {
		return upload_type;
	}
	public void setUpload_type(String upload_type) {
		this.upload_type = upload_type;
	}	
}
