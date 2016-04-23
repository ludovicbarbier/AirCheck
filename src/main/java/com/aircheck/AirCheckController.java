package com.aircheck;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aircheck.twitter.TwitterSymptomSearcher;

@RestController
public class AirCheckController {

	@RequestMapping("/")
    public String getSymptoms() {
        return TwitterSymptomSearcher.searchSymptoms().toString();
    }

}