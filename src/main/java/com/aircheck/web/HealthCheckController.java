package com.aircheck.web;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aircheck.model.Coordinates;
import com.aircheck.model.InfoSource;
import com.aircheck.model.SicknessDetail;
import com.aircheck.twitter.TwitterSymptomSearcher;

@RestController
public class HealthCheckController {

	
	@RequestMapping("/getHealthInfo")
	public List<SicknessDetail> getHealthInfo() {
		List<SicknessDetail> details = TwitterSymptomSearcher.searchSicknessDetails();
		return details;
	}
	
	
	public SicknessDetail createSicknessDetail() {
		SicknessDetail detail = new SicknessDetail();
		detail.setDate(new Date());
		detail.setSeverity(5);
		detail.setSource(InfoSource.Twitter);
		detail.setSymptom("runny nose");
		detail.setCoordinates(createCoordinates());
		//
//	    <Placemark id="2012 Jan 19 06:48:48.75 UTC">
//        <name>M 5.9 - 2012 Jan 19, OFF W. COAST OF S. ISLAND, N.Z.</name>
//        <magnitude>5.9</magnitude>
//        <Point>
//            <coordinates>165.778,-46.686,0</coordinates>
//        </Point>
		//
		
		return detail;
	}
	
	private Coordinates createCoordinates() {
		Coordinates c = new Coordinates();
		c.setElev(0.0);
		c.setLon(165.778);
		c.setLat(-46.686);
		return c;
	}
	
}
