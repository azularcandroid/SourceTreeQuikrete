package com.quikrete.gsondata.storelocator;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class StoreLocatorResult {
	@Expose
	private String status;
	@Expose
	private List<Result> result = new ArrayList<Result>();

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
	public List<Result> getResult() {
		return result;
	}

	/**
	 * 
	 * @param result
	 *            The result
	 */
	public void setResult(List<Result> result) {
		this.result = result;
	}
}
