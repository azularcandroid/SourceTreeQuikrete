package com.quikrete.gsondata.videocat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoDetailsData {

	@Expose
	private String status;
	@Expose
	private String id;
	@Expose
	private String title;
	@SerializedName("youtube_url")
	@Expose
	private String youtubeUrl;
	@SerializedName("step_by_step")
	@Expose
	private String stepByStep;
	@SerializedName("tools_and_materials")
	@Expose
	private String toolsAndMaterials;
	
	@Expose
	private Related related;

	public Related getRelated() {
		return related;
	}

	public void setRelated(Related related) {
		this.related = related;
	}

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
	 * @return The youtubeUrl
	 */
	public String getYoutubeUrl() {
		return youtubeUrl;
	}

	/**
	 * 
	 * @param youtubeUrl
	 *            The youtube_url
	 */
	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

	/**
	 * 
	 * @return The stepByStep
	 */
	public String getStepByStep() {
		return stepByStep;
	}

	/**
	 * 
	 * @param stepByStep
	 *            The step_by_step
	 */
	public void setStepByStep(String stepByStep) {
		this.stepByStep = stepByStep;
	}

	/**
	 * 
	 * @return The toolsAndMaterials
	 */
	public String getToolsAndMaterials() {
		return toolsAndMaterials;
	}

	/**
	 * 
	 * @param toolsAndMaterials
	 *            The tools_and_materials
	 */
	public void setToolsAndMaterials(String toolsAndMaterials) {
		this.toolsAndMaterials = toolsAndMaterials;
	}

}
