package com.level.toon.dto;

public class CommentDTO {
	private int toon_num;
	private int ep_num;
	private int member_num;
	private int comment_num;
	private String comment_content;
	private String comment_date;
	private int rec_count;
	private int start_num;
	private int end_num;
	
	public int getStart_num() {
		return start_num;
	}
	public void setStart_num(int start_num) {
		this.start_num = start_num;
	}
	public int getEnd_num() {
		return end_num;
	}
	public void setEnd_num(int end_num) {
		this.end_num = end_num;
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
	public int getMember_num() {
		return member_num;
	}
	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public String getComment_date() {
		return comment_date;
	}
	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}
	public int getRec_count() {
		return rec_count;
	}
	public void setRec_count(int rec_count) {
		this.rec_count = rec_count;
	}		
}
