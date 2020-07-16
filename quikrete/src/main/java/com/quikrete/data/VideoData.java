package com.quikrete.data;

public class VideoData {

	String product_id;
	String product_name;
	String product_star;

	public VideoData(String product_id, String product_name,
			String product_star) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_star = product_star;
	}

	@Override
	public String toString() {
		return "ProductData [product_id=" + product_id + ", product_name="
				+ product_name + ", product_star=" + product_star + "]";
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_star() {
		return product_star;
	}

	public void setProduct_star(String product_star) {
		this.product_star = product_star;
	}

}
