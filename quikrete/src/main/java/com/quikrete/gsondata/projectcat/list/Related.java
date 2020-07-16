package com.quikrete.gsondata.projectcat.list;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Related {

	@Expose
	private Product product;

	@Expose
	private Project project;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Expose
	private List<Video> video = new ArrayList<Video>();
	@Expose
	private List<Calculator> calculator = new ArrayList<Calculator>();

	/**
	 * /**
	 * 
	 * @return The video
	 */
	public List<Video> getVideo() {
		return video;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
