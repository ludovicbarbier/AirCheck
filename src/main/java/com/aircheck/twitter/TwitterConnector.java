package com.aircheck.twitter;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

public class TwitterConnector {
	
	private static Twitter connexion = null;
	
	public static Twitter connect()
	{
		if (connexion == null)
			connexion = new TwitterTemplate("XJmznocKIHzIX8qHoWqnAGlHu", "YlKKlFDZ97aY8ElYUp9RUpVZaWrv2A5WFJJaIxv3GQZKo714hg");
		return connexion;
	}

}
