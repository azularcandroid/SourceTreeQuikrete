package com.quikrete.gsondata.calc;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Related {

	@Expose
	private Product product;
	@Expose
	private Project project;
	@Expose
	private List<Video> video = new ArrayList<Video>();
	
	public List<Calculator> getCalculators() {
		return calculator;
	}

	public void setCalculators(List<Calculator> calculators) {
		this.calculator = calculators;
	}

	@Expose
	private List<Calculator> calculator = new ArrayList<Calculator>();

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

	public List<Video> getVideos() {
		return video;
	}

	public void setVideos(List<Video> videos) {
		this.video = videos;
	}

}
