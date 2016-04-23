package com.aircheck.model;

import java.util.Date;
import java.util.Properties;

public class SicknessDetail {
	
	final String type = "Feature";
	
	private Geometry geometry;
	
	private Properties properties;

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getType() {
		return type;
	}
	
	
	
//	String symptom;
//	Date date;
//	int severity;
//	Coordinates coordinates;
//	InfoSource source;
//	String location;
//
//	public String getSymptom() {
//		return symptom;
//	}
//
//
//	public void setSymptom(String symptom) {
//		this.symptom = symptom;
//	}
//
//
//	public Date getDate() {
//		return date;
//	}
//
//	public void setDate(Date date) {
//		this.date = date;
//	}
//
//	public int getSeverity() {
//		return severity;
//	}
//
//	public void setSeverity(int severity) {
//		this.severity = severity;
//	}
//
//
//	public Coordinates getCoordinates() {
//		return coordinates;
//	}
//
//
//	public void setCoordinates(Coordinates coordinates) {
//		this.coordinates = coordinates;
//	}
//
//
//	public InfoSource getSource() {
//		return source;
//	}
//
//
//	public void setSource(InfoSource source) {
//		this.source = source;
//	}
//
//
//	public String getLocation() {
//		return location;
//	}
//
//
//	public void setLocation(String location) {
//		this.location = location;
//	}

	
}
