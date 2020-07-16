package com.quikrete.gsondata.productcat.list;

import com.google.gson.annotations.Expose;

public class Alphabetical {
	@Expose
	private String id;
	@Expose
	private String title;
	@Expose
	private String group;
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
	 * @return The group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * 
	 * @param group
	 *            The group
	 */
	public void setGroup(String group) {
		this.group = group;
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

	@Override
	public String toString() {
		return "Alphabetical [id=" + id + ", title=" + title + ", group="
				+ group + ", image=" + image + "]";
	}
	
	

}
