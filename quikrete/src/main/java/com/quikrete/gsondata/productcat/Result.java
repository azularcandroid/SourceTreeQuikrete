package com.quikrete.gsondata.productcat;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.quikrete.gsondata.projectcat.Homeowner;
import com.quikrete.gsondata.projectcat.Pro;

public class Result {
	@Expose
	private List<Homeowner> homeowner = new ArrayList<Homeowner>();
	@Expose
	private List<Pro> pro = new ArrayList<Pro>();

	/**
	 * 
	 * @return The homeowner
	 */
	public List<Homeowner> getHomeowner() {
		return homeowner;
	}

	/**
	 * 
	 * @param homeowner
	 *            The homeowner
	 */
	public void setHomeowner(List<Homeowner> homeowner) {
		this.homeowner = homeowner;
	}

	/**
	 * 
	 * @return The pro
	 */
	public List<Pro> getPro() {
		return pro;
	}

	/**
	 * 
	 * @param pro
	 *            The pro
	 */
	public void setPro(List<Pro> pro) {
		this.pro = pro;
	}
}
