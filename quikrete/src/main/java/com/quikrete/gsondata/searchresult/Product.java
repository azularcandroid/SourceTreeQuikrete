package com.quikrete.gsondata.searchresult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
	@Expose
	private String id;
	@Expose
	private String title;
	@SerializedName("product_code")
	@Expose
	private String productCode;
	@Expose
	private String image;

	/**
	 * 
	 * @return The id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 *            The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return The productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 
	 * @param productCode
	 *            The product_code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 
	 * @return The image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 
	 * @param image
	 *            The image
	 */
	public void setImage(String image) {
		this.image = image;
	}

}
