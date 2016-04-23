package com.aircheck.model;

public enum InfoSource {

	Twitter("twitter"),
	User_input("User input");
	
	final String name;

	private InfoSource(String name) {
		this.name = name;
	}
}
