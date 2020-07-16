package com.quikrete.gsondata.projectcat.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectDetail {

	@Expose
	private String status;
	@Expose
	private String id;
	@Expose
	private String title;
	@Expose
	private String group;
	@Expose
	private String image;
	@Expose
	private String content;
	@SerializedName("step_by_step")
	@Expose
	private String stepByStep;
	@SerializedName("tools_and_materials")
	@Expose
	private String toolsAndMaterials;
	@SerializedName("tech_info")
	@Expose
	private String techInfo;
	@Expose
	private Related related;

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
	* The id
	*/
	public String getId() {
	return id;
	}

	/**
	* 
	* @param id
	* The id
	*/
	public void setId(String id) {
	this.id = id;
	}

	/**
	* 
	* @return
	* The title
	*/
	public String getTitle() {
	return title;
	}

	/**
	* 
	* @param title
	* The title
	*/
	public void setTitle(String title) {
	this.title = title;
	}

	/**
	* 
	* @return
	* The group
	*/
	public String getGroup() {
	return group;
	}

	/**
	* 
	* @param group
	* The group
	*/
	public void setGroup(String group) {
	this.group = group;
	}

	/**
	* 
	* @return
	* The image
	*/
	public String getImage() {
	return image;
	}

	/**
	* 
	* @param image
	* The image
	*/
	public void setImage(String image) {
	this.image = image;
	}

	/**
	* 
	* @return
	* The content
	*/
	public String getContent() {
	return content;
	}

	/**
	* 
	* @param content
	* The content
	*/
	public void setContent(String content) {
	this.content = content;
	}

	/**
	* 
	* @return
	* The stepByStep
	*/
	public String getStepByStep() {
	return stepByStep;
	}

	/**
	* 
	* @param stepByStep
	* The step_by_step
	*/
	public void setStepByStep(String stepByStep) {
	this.stepByStep = stepByStep;
	}

	/**
	* 
	* @return
	* The toolsAndMaterials
	*/
	public String getToolsAndMaterials() {
	return toolsAndMaterials;
	}

	/**
	* 
	* @param toolsAndMaterials
	* The tools_and_materials
	*/
	public void setToolsAndMaterials(String toolsAndMaterials) {
	this.toolsAndMaterials = toolsAndMaterials;
	}

	/**
	* 
	* @return
	* The techInfo
	*/
	public String getTechInfo() {
	return techInfo;
	}

	/**
	* 
	* @param techInfo
	* The tech_info
	*/
	public void setTechInfo(String techInfo) {
	this.techInfo = techInfo;
	}

	/**
	* 
	* @return
	* The related
	*/
	public Related getRelated() {
	return related;
	}

	/**
	* 
	* @param related
	* The related
	*/
	public void setRelated(Related related) {
	this.related = related;
	}

}
