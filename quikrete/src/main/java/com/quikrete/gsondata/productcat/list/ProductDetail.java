package com.quikrete.gsondata.productcat.list;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetail {

	@Expose
	private String status;
	@Expose
	private String id;
	@Expose
	private String title;
	@Expose
	private String image;
	@Expose
	private String content;
	@SerializedName("tech_info")
	@Expose
	private List<TechInfo> techInfo = new ArrayList<TechInfo>();

	@Expose
	private Related related;

	/**
	 * 
	 * @return The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *            The status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

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

	/**
	 * 
	 * @return The content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * @param content
	 *            The content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * @return The techInfo
	 */
	public List<TechInfo> getTechInfo() {
		return techInfo;
	}

	/**
	 * 
	 * @param techInfo
	 *            The tech_info
	 */
	public void setTechInfo(List<TechInfo> techInfo) {
		this.techInfo = techInfo;
	}

	/**
	 * 
	 * @return The related
	 */
	public Related getRelated() {
		return related;
	}

	/**
	 * 
	 * @param related
	 *            The related
	 */
	public void setRelated(Related related) {
		this.related = related;
	}
}
