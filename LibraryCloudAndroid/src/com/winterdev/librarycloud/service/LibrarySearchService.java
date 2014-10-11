package com.winterdev.librarycloud.service;

import java.util.Iterator;

import android.content.Intent;

import com.winterdev.librarycloud.domain.LibrarySearch;

/**
 * Singleton class to manage search beans
 * 
 * @author duby
 *
 */
public class LibrarySearchService {
	// instance variables
	LibrarySearch librarySearch = null;		// cached search bean that is managed by the service
	
	// static service
	private static LibrarySearchService librarySearchService;

	/**
	 * static singleton method to get the library search service
	 * 
	 * @return
	 */
	public static LibrarySearchService getLibrarySearchService() {
		if (librarySearchService == null) {
			librarySearchService = new LibrarySearchService();
		}
		
		return librarySearchService;
	}
	
	/**
	 * return the cached library search object if it exists; if not, create empty one
	 * @return
	 */
	public LibrarySearch getLibrarySearch() {
		if (this.librarySearch == null) {
			this.librarySearch = new LibrarySearch();
		}
		
		return this.librarySearch;
	}
	
    /**
     * create the library search bean
     * 
     * @param searchIntent						the intent passed into the activity from the search input
     * @return
     */
    public LibrarySearch createLibrarySearchBean(Intent searchIntent) {
    	// instance variables
    	LibrarySearch bean = new LibrarySearch();
    	
    	// get the intent terms and add them to the bean
    	for (Iterator<String> intentIterator = searchIntent.getExtras().keySet().iterator(); intentIterator.hasNext();) {
    		String key = intentIterator.next();
    		bean.setSearchTerm(key, searchIntent.getExtras().getString(key));
    	}
    	
    	// return the bean
    	return bean;
    }

	
}
