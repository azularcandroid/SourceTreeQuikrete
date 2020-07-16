package com.quikrete.gsondata.appsweepstakes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppSweepstakesData {

	@Expose
	private String status;
	@Expose
	private String to;
	@Expose
	private String from;
	@SerializedName("page_url")
	@Expose
	private String pageUrl;

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
	 * @return The to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * 
	 * @param to
	 *            The to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * 
	 * @return The from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * 
	 * @param from
	 *            The from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * 
	 * @return The pageUrl
	 */
	public String getPageUrl() {
		return pageUrl;
	}

	/**
	 * 
	 * @param pageUrl
	 *            The page_url
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}
