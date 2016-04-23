package com.aircheck.model;

import java.util.Date;

public class SicknessDetail {
	
	String symptom;
	String source;
	Date date;
	int severity;
	Coordinates coordinates;

	public String getSymptom() {
		return symptom;
	}


	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}


	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}


	public Coordinates getCoordinates() {
		return coordinates;
	}


	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
}
