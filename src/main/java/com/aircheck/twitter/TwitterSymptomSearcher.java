package com.aircheck.twitter;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.aircheck.model.InfoSource;
import com.aircheck.model.SicknessDetail;
import com.aircheck.twitter.result.Status;
import com.aircheck.twitter.result.TwitterSearchResult;

public class TwitterSymptomSearcher {

	public static List<SicknessDetail> searchSicknessDetails()
	{
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("q","flu+OR+chills+OR+sore+throat+OR+headache+OR+runny+nose+OR+vomiting+OR+sneazing+OR+fever+OR+diarrhea+OR+dry+cough");
		parameters.set("lang","en");
		parameters.set("include_entities","false");
		URI url =  URIBuilder.fromUri("https://api.twitter.com/1.1/search/tweets.json").queryParams(parameters).build();
		List<SicknessDetail> details = new ArrayList<>();
		SicknessDetail detail;
		TwitterSearchResult results = TwitterConnector.connect().restOperations().getForObject(url, TwitterSearchResult.class);
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		for (Status tweet : results.getStatuses())
		{
			detail = new SicknessDetail();
			if (tweet.getUser().getLocation() != null)
			{
				detail.setCoordinates(getCoordinates(tweet.getUser().getLocation()));
				detail.setLocation(tweet.getUser().getLocation());
			}
			detail.setSymptom("cough");
			detail.setSource(InfoSource.Twitter);
			detail.setSeverity(5);
		    try {
				detail.setDate(df.parse(tweet.getCreatedAt()));
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			details.add(detail);
			System.err.println(tweet.getText());
		}
		return details;
	}
	
	private static Coordinates getCoordinates(String location)
	{
		Coordinates coordinates = new Coordinates();
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
				coordinates.setLat(result.getGeometry().getLocation().getLat());
				coordinates.setLon(result.getGeometry().getLocation().getLng());
			}
		}
		return coordinates;
	}
}
