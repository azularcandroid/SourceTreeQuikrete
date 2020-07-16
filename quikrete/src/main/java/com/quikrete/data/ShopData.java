package com.quikrete.data;

public class ShopData {

	String shop_id;
	String shop_name;
	String shop_lat;
	String shop_lon;
	String shop_address;

	public ShopData(String shop_id, String shop_name, String shop_lat,
			String shop_lon, String shop_address) {
		super();
		this.shop_id = shop_id;
		this.shop_name = shop_name;
		this.shop_lat = shop_lat;
		this.shop_lon = shop_lon;
		this.shop_address = shop_address;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getShop_lat() {
		return shop_lat;
	}

	public void setShop_lat(String shop_lat) {
		this.shop_lat = shop_lat;
	}

	public String getShop_lon() {
		return shop_lon;
	}

	public void setShop_lon(String shop_lon) {
		this.shop_lon = shop_lon;
	}

	public String getShop_address() {
		return shop_address;
	}

	public void setShop_address(String shop_address) {
		this.shop_address = shop_address;
	}

}
