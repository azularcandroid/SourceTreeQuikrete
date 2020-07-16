package com.quikrete.data;

import com.google.gson.annotations.Expose;

public class FavResult {

	@Expose
	private String status;
	@Expose
	private String message;

	/**
	* 
	* @return
	* The status
	*/
	public String getStatus() {
	return status;
	}

	/**
	* 
	* @param status
	* The status
	*/
	public void setStatus(String status) {
	this.status = status;
	}

	/**
	* 
	* @return
	* The message
	*/
	public String getMessage() {
	return message;
	}

	/**
	* 
	* @param message
	* The message
	*/
	public void setMessage(String message) {
	this.message = message;
	}
}
