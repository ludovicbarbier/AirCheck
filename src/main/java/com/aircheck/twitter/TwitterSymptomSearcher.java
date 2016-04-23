package com.aircheck.twitter;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.aircheck.google.geocode.GoogleGeocodeResult;
import com.aircheck.google.geocode.Result;
import com.aircheck.model.Coordinates;
import com.aircheck.model.Geometry;
import com.aircheck.model.InfoSource;
import com.aircheck.model.SicknessDetail;
import com.aircheck.twitter.result.Status;
import com.aircheck.twitter.result.TwitterSearchResult;

public class TwitterSymptomSearcher {
	
	private static List<String> symptoms = Arrays.asList("sore throat", "headache", "runny nose", "irritation", "sneezing", "chest pain", "asthma", "cough", "burning eyes", "wheez", "itchy eyes");

	public static List<SicknessDetail> searchSicknessDetails()
	{
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("q",prepareQuery());
		parameters.set("lang","en");
		parameters.set("include_entities","false");
		parameters.set("count","100");
		URI url =  URIBuilder.fromUri("https://api.twitter.com/1.1/search/tweets.json").queryParams(parameters).build();
		List<SicknessDetail> details = new ArrayList<>();
		
		TwitterSearchResult results = TwitterConnector.connect().restOperations().getForObject(url, TwitterSearchResult.class);
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		for (Status tweet : results.getStatuses()) {
			SicknessDetail detail = new SicknessDetail();
			if (tweet.getUser().getLocation() != null) {
				
				detail.setGeometry(getCoordinates(tweet.getUser().getLocation()));
//				detail.setLocation(tweet.getUser().getLocation());
			}
//			detail.setSymptom("cough");
//			detail.setSource(InfoSource.Twitter);
//			detail.setSeverity(5);
//		    try {
//				detail.setDate(df.parse(tweet.getCreatedAt()));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			} 
			details.add(detail);
			StringBuilder stringBuilder = new StringBuilder();
			boolean first = true;
			for (String s : symptoms) {
				if (tweet.getText().contains(s)) {
					if (!first)
						stringBuilder.append(", ");
					stringBuilder.append(s);
					first = false;
				}					
			}
			detail.setSymptom(stringBuilder.toString());
			System.err.println(tweet.getText());
		}
		return details;
	}
	
	private static Geometry getCoordinates(String location)
	{
		Geometry coordinates = new Geometry();
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> vars = new HashMap<String, String>();
		 
		vars.put("address", location);
		vars.put("sensor", "false");
		GoogleGeocodeResult results = restTemplate.getForObject("http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor={sensor}",GoogleGeocodeResult.class, vars);
		if (results.getResults() != null && results.getResults().size() > 0)
		{
			Result result = results.getResults().get(0);
			if (result.getGeometry() != null && result.getGeometry().getLocation() != null)
			{
				double lat = result.getGeometry().getLocation().getLat();
				double lon = result.getGeometry().getLocation().getLng();
				coordinates.setCoordinates(new double[] {lat, lon});
			}
		}
		return coordinates;
	}
	
	private static String prepareQuery() {
		StringBuilder stringBuilder = new StringBuilder();
		boolean first = true;
		for (String s : symptoms) {
			if (!first)
				stringBuilder.append("+OR+");
			stringBuilder.append(s);
			first = false;
		}
		
		return stringBuilder.toString().replace(" ", "+");
	}
}
