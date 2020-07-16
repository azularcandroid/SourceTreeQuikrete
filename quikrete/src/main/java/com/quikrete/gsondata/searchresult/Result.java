package com.quikrete.gsondata.searchresult;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Result {

	@Expose
	private List<Product> product = new ArrayList<Product>();
	@Expose
	private List<Project> project = new ArrayList<Project>();
	@Expose
	private List<Video> video = new ArrayList<Video>();
	@Expose
	private List<Calculator> calculator = new ArrayList<Calculator>();

	/**
	 * 
	 * @return The product
	 */
	public List<Product> getProduct() {
		return product;
	}

	/**
	 * 
	 * @param product
	 *            The product
	 */
	public void setProduct(List<Product> product) {
		this.product = product;
	}

	/**
	 * 
	 * @return The project
	 */
	public List<Project> getProject() {
		return project;
	}

	/**
	 * 
	 * @param project
	 *            The project
	 */
	public void setProject(List<Project> project) {
		this.project = project;
	}

	/**
	 * 
	 * @return The video
	 */
	public List<Video> getVideo() {
		return video;
	}

	/**
	 * 
	 * @param video
	 *            The video
	 */
	public void setVideo(List<Video> video) {
		this.video = video;
	}

	/**
	 * 
	 * @return The calculator
	 */
	public List<Calculator> getCalculator() {
		return calculator;
	}

	/**
	 * 
	 * @param calculator
	 *            The calculator
	 */
	public void setCalculator(List<Calculator> calculator) {
		this.calculator = calculator;
	}

}
