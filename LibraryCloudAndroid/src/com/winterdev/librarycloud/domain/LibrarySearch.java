package com.winterdev.librarycloud.domain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import android.util.Log;

public class LibrarySearch {
	// constants related to the LC api
	public static final String SEARCH_KEYWORD_TITLE 	= "title";
	public static final String SEARCH_KEYWORD_ANY 		= "q";
	public static final String SEARCH_KEYWORD_AUTHOR 	= "name";
	public static final String SEARCH_PAGE_START	 	= "start";
	public static final String SEARCH_ITEM_LIMIT	 	= "limit";
	public static final String SEARCH_KEYWORD_PUBLISHER	= "publisher";
	public static final String SEARCH_KEYWORD_SUBJECT	= "subject";

	// constant keys for passing object
	public static final String SEARCH_ITEM_KEY			= "searchItem";
	
	// instance variables
	private String name;
	private int startPageNumber;
	private int limit = 25;							// default search limit of 25
	private Map<String, String> searchMap;

	/**
	 * method to set a search term and key combination for a web service search
	 * 
	 * @param searchKey
	 * @param searchValue
	 */
	public void setSearchTerm(String searchKey, String searchValue) {
		if (this.searchMap == null) {
			this.searchMap = new HashMap<String, String>();
		}
		
		// only add non null objects
		if (searchValue != null) {
			this.searchMap.put(searchKey,  searchValue);
		}
	}
	
	/**
	 * return the search string URL
	 * 
	 * @param rootUrl						the root url for the search
	 * @return								the search url
	 */
	public String getSearchUrl(String rootUrl) {
		// local variables
		StringBuilder stringBuilder = new StringBuilder();
		String urlString;
		
		// add in the url
		stringBuilder.append(rootUrl);

		// add in limit
		stringBuilder.append("?");
		stringBuilder.append(SEARCH_ITEM_LIMIT);
		stringBuilder.append("=");
		stringBuilder.append(this.limit);
		
		// for each key, add the search item to the url
		for (Iterator<String> keyIterator = this.searchMap.keySet().iterator(); keyIterator.hasNext();) {
			// get the key and value pair
			String key = keyIterator.next();
			String value = this.searchMap.get(key);
			
			// tokenize the value string of necessary
			// TODO - test for space first for better speed
			StringTokenizer tokenizer = new StringTokenizer(value); 
			while(tokenizer.hasMoreElements()) {
				stringBuilder.append("&");
				stringBuilder.append(key);
				stringBuilder.append("=");
				stringBuilder.append(tokenizer.nextElement());
			}
		}

		// get the final string
		urlString = stringBuilder.toString();
		
		// log the url called
		Log.i(this.getName(), "search for url: " + urlString);
		
		// return the url
		return urlString;
	}
	
	// auto generated methods
	
	public int getStartPageNumber() {
		return startPageNumber;
	}
	public void setStartPageNumber(int startPageNumber) {
		this.startPageNumber = startPageNumber;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
