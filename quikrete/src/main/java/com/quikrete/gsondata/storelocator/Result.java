package com.quikrete.gsondata.storelocator;

import com.google.gson.annotations.Expose;

public class Result {
	@Expose
	private String name;
	@Expose
	private String address;
	@Expose
	private String lat;
	@Expose
	private String lon;
	@Expose
	private String km;
	@Expose
	private String m;

	/**
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 *            The address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 
	 * @return The lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * 
	 * @param lat
	 *            The lat
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * 
	 * @return The lon
	 */
	public String getLon() {
		return lon;
	}

	/**
	 * 
	 * @param lon
	 *            The lon
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}

	/**
	 * 
	 * @return The km
	 */
	public String getKm() {
		return km;
	}

	/**
	 * 
	 * @param km
	 *            The km
	 */
	public void setKm(String km) {
		this.km = km;
	}

	/**
	 * 
	 * @return The m
	 */
	public String getM() {
		return m;
	}

	/**
	 * 
	 * @param m
	 *            The m
	 */
	public void setM(String m) {
		this.m = m;
	}

}
