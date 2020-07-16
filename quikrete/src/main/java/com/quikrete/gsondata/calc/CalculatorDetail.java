package com.quikrete.gsondata.calc;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class CalculatorDetail {
	@Expose
	private String status;
	@Expose
	private String id;
	@Expose
	private String title;
	@Expose
	private List<Option> options = new ArrayList<Option>();

	@Expose
	private List<Result> result = new ArrayList<Result>();

	@Expose
	private Related related;
	
	@Expose
	private String instructions;
	
	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Expose
	private String notes;

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
	 * @return The options
	 */
	public List<Option> getOptions() {
		return options;
	}

	/**
	 * 
	 * @param options
	 *            The options
	 */
	public void setOptions(List<Option> options) {
		this.options = options;
	}

	/**
	 * 
	 * @return The result
	 */
	public List<Result> getResult() {
		return result;
	}

	/**
	 * 
	 * @param result
	 *            The result
	 */
	public void setResult(List<Result> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "CalculatorDetail{" +
				"status='" + status + '\'' +
				", id='" + id + '\'' +
				", title='" + title + '\'' +
				", options=" + options +
				", result=" + result +
				", related=" + related +
				", instructions='" + instructions + '\'' +
				", notes='" + notes + '\'' +
				'}';
	}
}
