package com.quikrete.gsondata.productcat.list;

import com.google.gson.annotations.Expose;

public class TechInfo {

	@Expose
	private String title;
	@Expose
	private String file;

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
	 * @return The file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * 
	 * @param file
	 *            The file
	 */
	public void setFile(String file) {
		this.file = file;
	}
}
