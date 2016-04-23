package com.aircheck.web;



import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aircheck.model.SicknessDetail;

@RestController
public class HealthCheckController {

	
	@RequestMapping("/getHealthInfo")
	public List<SicknessDetail> getHealthInfo() {
		List<SicknessDetail> details = new ArrayList<>();
		
		return null;
	}
	
//	public SicknessDetail detail = new SicknessDetail();
	
}
