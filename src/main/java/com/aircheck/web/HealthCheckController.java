package com.aircheck.web;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aircheck.model.Geometry;
import com.aircheck.model.InfoSource;
import com.aircheck.model.SicknessDetail;
import com.aircheck.twitter.TwitterSymptomSearcher;

@RestController
public class HealthCheckController {

	private List<SicknessDetail> localDetails = new ArrayList<SicknessDetail>();
	
	@RequestMapping("/getHealthInfo")
	public List<SicknessDetail> getHealthInfo() {
		List<SicknessDetail> details = TwitterSymptomSearcher.searchSicknessDetails();
		details.addAll(localDetails);
		return details;
	}

	@RequestMapping("/addHealthInfo")
    public SicknessDetail addHealthInfo(@RequestParam(value="symptom", required=true) String symptom,
    		@RequestParam(value="latitude", required=true) Double latitude,
    		@RequestParam(value="longitude", required=true) Double longitude,
    		@RequestParam(value="severity", required=true) String severity) {
		SicknessDetail detail = new SicknessDetail();
		detail.setProperties(new Properties());
		detail.getProperties().put(SicknessDetail.PROPERTY_SOURCE, InfoSource.User_input.toString());
		detail.getProperties().put(SicknessDetail.PROPERTY_SEVERITY, severity);
		Date date = new Date();
		detail.getProperties().put(SicknessDetail.PROPERTY_DATE, "" + date.getTime());
		detail.getProperties().put(SicknessDetail.PROPERTY_SYMPTOMS, symptom);
		Geometry coordinates = new Geometry();
		coordinates.setCoordinates(new double[] {latitude, longitude});
		detail.setGeometry(coordinates);
		localDetails.add(detail);
        return detail;
    }

	
}
