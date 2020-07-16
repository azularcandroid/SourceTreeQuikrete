package com.quikrete.data;

public class ProductData {

	String product_id;
	String product_name;
	String product_star;
	String product_text;

	public ProductData(String product_id, String product_name,
			String product_star, String product_text) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_star = product_star;
		this.product_text = product_text;
	}

	@Override
	public String toString() {
		return "ProductData [product_id=" + product_id + ", product_name="
				+ product_name + ", product_star=" + product_star
				+ ", product_text=" + product_text + "]";
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

	public String getProduct_text() {
		return product_text;
	}

	public void setProduct_text(String product_text) {
		this.product_text = product_text;
	}

}
