package com.quikrete.gsondata.calc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {
	@Expose
	private String label;
	@SerializedName("input_title")
	@Expose
	private String inputTitle;

	/**
	 * 
	 * @return The label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 *            The label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @return The inputTitle
	 */
	public String getInputTitle() {
		return inputTitle;
	}

	/**
	 * 
	 * @param inputTitle
	 *            The input_title
	 */
	public void setInputTitle(String inputTitle) {
		this.inputTitle = inputTitle;
	}

	@Override
	public String toString() {
		return "Option{" +
				"label='" + label + '\'' +
				", inputTitle='" + inputTitle + '\'' +
				'}';
	}
}
