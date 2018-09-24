package com.level.toon.dto;

public class CoinDTO {

	private int coin_num;
	private int quantity;
	private int price;
	private int sale;
	private String coin_content;
	private String event;
	private int sale_price;	//db no
	private String coin_image;
	
	public String getCoin_image() {
		return coin_image;
	}
	public void setCoin_image(String coin_image) {
		this.coin_image = coin_image;
	}
	public int getSale_price() {
		return sale_price;
	}
	public void setSale_price(int sale_price) {
		this.sale_price = sale_price;
	}
	public int getCoin_num() {
		return coin_num;
	}
	public void setCoin_num(int coin_num) {
		this.coin_num = coin_num;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getSale() {
		return sale;
	}
	public void setSale(int sale) {
		this.sale = sale;
	}
	public String getCoin_content() {
		return coin_content;
	}
	public void setCoin_content(String coin_content) {
		this.coin_content = coin_content;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
}
