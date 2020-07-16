package com.quikrete.gsondata.calc;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Project {

	@Expose
	private List<Popularity> popularity = new ArrayList<Popularity>();
	@Expose
	private List<Alphabetical> alphabetical = new ArrayList<Alphabetical>();

	public List<Popularity> getPopularity() {
		return popularity;
	}

	public void setPopularity(List<Popularity> popularity) {
		this.popularity = popularity;
	}

	public List<Alphabetical> getAlphabetical() {
		return alphabetical;
	}

	public void setAlphabetical(List<Alphabetical> alphabetical) {
		this.alphabetical = alphabetical;
	}

}
