package com.aircheck.model;

import java.util.List;

public class SicknessDetails {

	
	final String type = "FeatureCollection";
	
	List<SicknessDetail> features;

	public List<SicknessDetail> getFeatures() {
		return features;
	}

	public void setFeatures(List<SicknessDetail> features) {
		this.features = features;
	}
	
	public String getType() {
		return type;
	}
}
