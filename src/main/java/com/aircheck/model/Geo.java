package com.aircheck.model;

import java.util.Properties;

public class Geo {

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
	
	
}
