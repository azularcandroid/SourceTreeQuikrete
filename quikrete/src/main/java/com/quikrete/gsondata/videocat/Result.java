package com.quikrete.gsondata.videocat;

import com.google.gson.annotations.Expose;

public class Result {

	// @Expose
	// private List<Popularity> popularity = new ArrayList<Popularity>();
	// @Expose
	// private List<Alphabetical> alphabetical = new ArrayList<Alphabetical>();
	//
	// /**
	// *
	// * @return The popularity
	// */
	// public List<Popularity> getPopularity() {
	// return popularity;
	// }
	//
	// /**
	// *
	// * @param popularity
	// * The popularity
	// */
	// public void setPopularity(List<Popularity> popularity) {
	// this.popularity = popularity;
	// }
	//
	// /**
	// *
	// * @return The alphabetical
	// */
	// public List<Alphabetical> getAlphabetical() {
	// return alphabetical;
	// }
	//
	// /**
	// *
	// * @param alphabetical
	// * The alphabetical
	// */
	// public void setAlphabetical(List<Alphabetical> alphabetical) {
	// this.alphabetical = alphabetical;
	// }

	@Expose
	private String id;
	@Expose
	private String title;
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
}
