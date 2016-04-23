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
	
//	public SicknessDetail detail = new SicknessDetail();
	
}
