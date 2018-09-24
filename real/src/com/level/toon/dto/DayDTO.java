package com.level.toon.dto;

public class DayDTO {
	private int date_num;
	private String today;
	private String today_status; //db nono 오늘인지 확인해주는 변수
	public String getToday_status() {
		return today_status;
	}
	public void setToday_status(String today_status) {
		this.today_status = today_status;
	}
	public int getDate_num() {
		return date_num;
	}
	public void setDate_num(int date_num) {
		this.date_num = date_num;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
}
