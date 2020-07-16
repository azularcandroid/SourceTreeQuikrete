package com.quikrete.gsondata.videocat;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Related {

	@Expose
	private Product product;
	@Expose
	private Project project;
	@Expose
	private List<Calculator> calculator = new ArrayList<Calculator>();

	public List<Video> getVideo() {
		return video;
	}

	public void setVideo(List<Video> video) {
		this.video = video;
	}

	@Expose
	private List<Video> video = new ArrayList<Video>();

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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
