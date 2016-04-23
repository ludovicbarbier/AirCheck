package com.aircheck.web;


import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aircheck.model.SicknessDetail;
import com.aircheck.twitter.TwitterSymptomSearcher;

@RestController
public class HealthCheckController {

	@RequestMapping("/getHealthInfo")
	public List<SicknessDetail> getHealthInfo() {
		List<SicknessDetail> details = TwitterSymptomSearcher.searchSicknessDetails();
		return details;
	}
	
	
	//{	  "type": "Feature",  "geometry": {	    "type": "Point",	    "coordinates": [125.6, 10.1]	  },	  "properties": {    "name": "Dinagat Islands"}	}
	// [{"symptom":"runny nose","date":1461396466475,"severity":5,"coordinates":{"lat":"-46.686","lon":"165.778","elev":"0"},"source":"Twitter"}]

	

	
}
