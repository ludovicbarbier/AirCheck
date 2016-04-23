package com.aircheck.twitter;

import org.springframework.social.twitter.api.SearchResults;

public class TwitterSymptomSearcher {

	public static SearchResults searchSymptoms()
	{
		return TwitterConnector.connect().searchOperations().search("#cough");
	}
}
