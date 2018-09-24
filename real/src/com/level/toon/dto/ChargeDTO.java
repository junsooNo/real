package com.level.toon.dto;

public class ChargeDTO {
	private int charge_num;
	private int member_num;
	private String charge_method;
	private int charge_price;
	private int charge_coin;
	private String charge_date;
	
	public int getCharge_num() {
		return charge_num;
	}
	public void setCharge_num(int charge_num) {
		this.charge_num = charge_num;
	}
	public int getMember_num() {
		return member_num;
	}
	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}
	public String getCharge_method() {
		return charge_method;
	}
	public void setCharge_method(String charge_method) {
		this.charge_method = charge_method;
	}
	public int getCharge_price() {
		return charge_price;
	}
	public void setCharge_price(int charge_price) {
		this.charge_price = charge_price;
	}
	public int getCharge_coin() {
		return charge_coin;
	}
	public void setCharge_coin(int charge_coin) {
		this.charge_coin = charge_coin;
	}
	public String getCharge_date() {
		return charge_date;
	}
	public void setCharge_date(String charge_date) {
		this.charge_date = charge_date;
	}	
}
