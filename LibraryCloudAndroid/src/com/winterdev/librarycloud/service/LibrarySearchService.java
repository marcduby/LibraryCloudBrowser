package com.winterdev.librarycloud.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

	// static library map - possibly put this in cache object
	private Map<String, String> libraryLookupMap;
	
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
	 * returns the library name from the library code given; will return the code given if no match found
	 * 
	 * @param code
	 * @return
	 */
	public String getLibraryName(String code) {
		// local variables
		String libraryName = code;
		
		// build map if not built
		if (libraryLookupMap == null) {
			libraryLookupMap = new HashMap<String, String>();
			
			// manually add codes for now
			libraryLookupMap.put("DIV", "Andover-Harvard Theological Library");
			libraryLookupMap.put("AJP", "Arnold Arboretum Horticultural Library");
			libraryLookupMap.put("SCH", "Arthur and Elizabeth Schlesinger Library on the History of Women in America");
			libraryLookupMap.put("BAK", "Baker Library");
			libraryLookupMap.put("BHC", "Baker Library Historical Collections");
			libraryLookupMap.put("BER", "Biblioteca Berenson");
			libraryLookupMap.put("BIR", "Birkhoff Mathematical Library");
			libraryLookupMap.put("BOT", "Botany Libraries");
			libraryLookupMap.put("CAB", "Cabot Science Library");
			libraryLookupMap.put("CHE", "Chemistry Library");
			libraryLookupMap.put("MED", "Countway Library of Medicine");
			libraryLookupMap.put("DEV", "Development Office Library");
			libraryLookupMap.put("DDO", "Dumbarton Oaks");
			libraryLookupMap.put("MUS", "Eda Kuhn Loeb Music Library");
			libraryLookupMap.put("MCZ", "Ernst Mayr Library");
			libraryLookupMap.put("FAL", "Fine Arts Library");
			libraryLookupMap.put("FAD", "Fine Arts Library - Digital Images and Slides Collection");
			libraryLookupMap.put("FUN", "Fung Library");
			libraryLookupMap.put("MCK", "Gordon McKay Library and Blue Hill Meteorological Observatory Library");
			libraryLookupMap.put("DOC", "Government Documents and Microforms Collection");
			libraryLookupMap.put("GRO", "Grossman Library");
			libraryLookupMap.put("ART", "Harvard Art Museums Archives & Special Collections");
			libraryLookupMap.put("HFA", "Harvard Film Archive");
			libraryLookupMap.put("KSG", "Harvard Kennedy School Library & Knowledge Services");
			libraryLookupMap.put("LAS", "Harvard Law School Historical & Special Collections");
			libraryLookupMap.put("LAW", "Harvard Law School Library");
			libraryLookupMap.put("MAP", "Harvard Map Collection");
			libraryLookupMap.put("THE", "Harvard Theatre Collection, Houghton Library");
			libraryLookupMap.put("HUA", "Harvard University Archives");
			libraryLookupMap.put("HYL", "Harvard-Yenching Library");
			libraryLookupMap.put("HOU", "Houghton Library");
			libraryLookupMap.put("LAM", "Lamont Library");
			libraryLookupMap.put("DES", "Loeb Design Library");
			libraryLookupMap.put("GUT", "Monroe C. Gutman Library");
			libraryLookupMap.put("PHY", "Physics Research Library");
			libraryLookupMap.put("HPO", "Property Information Resource Center");
			libraryLookupMap.put("PHI", "Robbins Library of Philosophy");
			libraryLookupMap.put("TOZ", "Tozzer Library");
			libraryLookupMap.put("WID", "Widener Library");
			libraryLookupMap.put("WOL", "Wolbach Library");
			libraryLookupMap.put("POE", "Woodberry Poetry Room");
		}
		
		// lookup the library name
		libraryName = libraryLookupMap.get(code);
					
		// return
		return (libraryName == null ? code : libraryName);
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
