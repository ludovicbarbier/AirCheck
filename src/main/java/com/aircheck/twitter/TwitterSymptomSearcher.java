package com.aircheck.twitter;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.aircheck.google.geocode.GoogleGeocodeResult;
import com.aircheck.google.geocode.Result;
import com.aircheck.model.Geometry;
import com.aircheck.model.InfoSource;
import com.aircheck.model.SicknessDetail;
import com.aircheck.twitter.result.Status;
import com.aircheck.twitter.result.TwitterSearchResult;

public class TwitterSymptomSearcher {
	
	private static List<String> querySymptoms = Arrays.asList("sore throat", "headache", "runny nose", "irritation", "sneezing", "chest pain", "asthma", "cough", "burning eyes", "wheez", "itchy eyes");
	private static List<String> queryWordsToIgnore = Arrays.asList("test");

	
	public static List<SicknessDetail> searchSicknessDetails(Date start, Date end)
	{
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("q",prepareQuery());
		parameters.set("include_entities","false");
		parameters.set("count","100");
		if (end != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			parameters.set("until", format.format(end));
		}
		URI url =  URIBuilder.fromUri("https://api.twitter.com/1.1/search/tweets.json").queryParams(parameters).build();
		System.err.println(url.toString());
		List<SicknessDetail> details = new ArrayList<>();
		
		System.err.println(prepareQuery());
		TwitterSearchResult results = TwitterConnector.connect().restOperations().getForObject(url, TwitterSearchResult.class);
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		for (Status tweet : results.getStatuses()) {
			SicknessDetail detail = new SicknessDetail();
			detail.setProperties(new Properties());
			try {
		    	Date date = df.parse(tweet.getCreatedAt());
		    	//if (tweet.getUser() != null)
		    	//	date.setTime(date.getTime() + (tweet.getUser().getUtcOffset() * 1000));
		    	if (start != null && date.getTime() < start.getTime())
		    		continue;
				detail.getProperties().put(SicknessDetail.PROPERTY_DATE, "" + date.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			if (tweet.getUser().getLocation() != null) {
				Geometry geometry = getCoordinates(tweet.getUser().getLocation());
				if (geometry == null)
					continue;
				detail.setGeometry(geometry);
			}
			detail.getProperties().put(SicknessDetail.PROPERTY_SOURCE, InfoSource.Twitter.toString());
			detail.getProperties().put(SicknessDetail.PROPERTY_SEVERITY, "5");
			details.add(detail);
			String tweetLower = tweet.getText().toLowerCase();
			ArrayList<String> foundSymptoms = new ArrayList<String>();
			for (String s : querySymptoms) {
				if (tweetLower.contains(s)) {
					foundSymptoms.add(s);
				}					
			}
			detail.getProperties().put(SicknessDetail.PROPERTY_SYMPTOMS, foundSymptoms.toString());
			System.err.println(tweet.getText());
		}
		return details;
	}
	
	private static Geometry getCoordinates(String location) {
		Geometry coordinates = null;
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> vars = new HashMap<String, String>();
		 
		vars.put("address", location);
		vars.put("sensor", "false");
		GoogleGeocodeResult results = restTemplate.getForObject("http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor={sensor}",GoogleGeocodeResult.class, vars);
		if (results.getResults() != null && results.getResults().size() > 0) {
			Result result = results.getResults().get(0);
			if (result.getGeometry() != null && result.getGeometry().getLocation() != null) {
				coordinates = new Geometry();
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
		int count = 0;
		for (String s : querySymptoms) {
			if (first)
				stringBuilder.append("\"");
			else if (count == querySymptoms.size())
				stringBuilder.append("\"");
			else
				stringBuilder.append("\" OR \"");
				
			stringBuilder.append(s);
			if (count == querySymptoms.size())
				stringBuilder.append("\"");
			first = false;
			count++;
		}
		for (String s : queryWordsToIgnore) {
			stringBuilder.append("-\"");				
			stringBuilder.append(s);
			stringBuilder.append("\"");
			first = false;
			count++;
		}
		return stringBuilder.toString();
	}
}
