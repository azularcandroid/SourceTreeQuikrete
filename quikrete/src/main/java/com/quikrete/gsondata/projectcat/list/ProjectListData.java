package com.quikrete.gsondata.projectcat.list;

import com.google.gson.annotations.Expose;

public class ProjectListData {
	@Expose
	private String status;
	@Expose
	private Result result;

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
	 * @return The result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * 
	 * @param result
	 *            The result
	 */
	public void setResult(Result result) {
		this.result = result;
	}
}