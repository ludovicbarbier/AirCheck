package com.aircheck.model;

import java.util.List;

public class Geometry {
	
	//{	  "type": "Feature", 
	//"geometry": {	    "type": "Point",	
	//"coordinates": [125.6, 10.1]	  },
	//"properties": {    "name": "Dinagat Islands"}	}


	final String type = "Point";
	
	private double[] coordinates = new double[2];

	
	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}

	public String getType() {
		return type;
	}
	
	
}
