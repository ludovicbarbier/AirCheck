package com.aircheck.model;

import java.util.Date;
import java.util.Properties;

public class SicknessDetail {
	
	public final static String PROPERTY_SEVERITY = "SEVERITY";
	public final static String PROPERTY_SYMPTOMS = "SYMPTOMS";
	public final static String PROPERTY_SOURCE = "SOURCE";
	public final static String PROPERTY_DATE = "DATE";
	
	final String type = "Feature";
	
	private Geometry geometry;
	Date date;
	
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
}
