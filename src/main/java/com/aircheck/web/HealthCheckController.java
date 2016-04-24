package com.aircheck.web;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	private static List<SicknessDetail> localDetails = new ArrayList<SicknessDetail>();
	
	@RequestMapping("/getHealthInfo")
	public List<SicknessDetail> getHealthInfo(@RequestParam(value="start", required=false) String start,
			@RequestParam(value="end", required=false) String end) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date startDate = null;
		Date endDate = null;
		try {
			if (start != null && !start.isEmpty())
				startDate = df.parse(start);
			if (end != null && !end.isEmpty())
				endDate = df.parse(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<SicknessDetail> details = TwitterSymptomSearcher.searchSicknessDetails(startDate, endDate);
		for (SicknessDetail detail : localDetails) {
			Long date = Long.parseLong(detail.getProperties().getProperty(SicknessDetail.PROPERTY_DATE));
			if (date < startDate.getTime() || date > endDate.getTime())
				continue;
			details.add(detail);
		}
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
