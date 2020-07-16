package com.quikrete.gsondata.calc;

import com.google.gson.annotations.Expose;

public class Result {
	@Expose
	private String id;
	@Expose
	private String title;

	@Expose
	private String key;
	@Expose
	private String value;

	/**
	 * 
	 * @return The key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 
	 * @param key
	 *            The key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 
	 * @return The value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 *            The value
	 */
	public void setValue(String value) {
		this.value = value;
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

	@Override
	public String toString() {
		return "Result [id=" + id + ", title=" + title + ", key=" + key
				+ ", value=" + value + "]";
	}

}
