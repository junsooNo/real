package com.level.toon.dto;

public class LibraryDTO extends ObjectDTO{
	private int member_num;
	private int toon_num;
	private int ep_num;
	private String watch_date;
	
	public int getMember_num() {
		return member_num;
	}
	public void setMember_num(int member_num) {
		this.member_num = member_num;
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
	public String getWatch_date() {
		return watch_date;
	}
	public void setWatch_date(String watch_date) {
		this.watch_date = watch_date;
	}
}