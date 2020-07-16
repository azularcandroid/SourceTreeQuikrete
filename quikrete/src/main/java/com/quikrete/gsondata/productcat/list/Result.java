package com.quikrete.gsondata.productcat.list;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Result {
	@Expose
	private List<Popularity> popularity = new ArrayList<Popularity>();
	@Expose
	private List<Alphabetical> alphabetical = new ArrayList<Alphabetical>();

	/**
	 * 
	 * @return The popularity
	 */
	public List<Popularity> getPopularity() {
		return popularity;
	}

	/**
	 * 
	 * @param popularity
	 *            The popularity
	 */
	public void setPopularity(List<Popularity> popularity) {
		this.popularity = popularity;
	}

	/**
	 * 
	 * @return The alphabetical
	 */
	public List<Alphabetical> getAlphabetical() {
		return alphabetical;
	}

	/**
	 * 
	 * @param alphabetical
	 *            The alphabetical
	 */
	public void setAlphabetical(List<Alphabetical> alphabetical) {
		this.alphabetical = alphabetical;
	}
}
