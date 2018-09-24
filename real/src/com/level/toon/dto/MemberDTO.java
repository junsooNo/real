package com.level.toon.dto;

public class MemberDTO extends ObjectDTO{
	private int member_num;
	private String email;
	private String password;
	private int coin;
	private String join_date;
	private String lotto_date;
	
	public String getLotto_date() {
		return lotto_date;
	}
	public void setLotto_date(String lotto_date) {
		this.lotto_date = lotto_date;
	}
	public int getMember_num() {
		return member_num;
	}
	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	
}
