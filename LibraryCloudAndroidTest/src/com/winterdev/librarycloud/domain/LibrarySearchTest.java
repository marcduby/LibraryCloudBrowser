package com.winterdev.librarycloud.domain;

import junit.framework.TestCase;

public class LibrarySearchTest extends TestCase {
	// instance constants
	private final String rootUrl = "http://yadiyada.com";

	/**
	 * test getting a title search
	 */
	public void testGetSearchUrl() {
		// local variables
		String titleString = "Jurassic";
			
		// create bean and add in search term
		LibrarySearch searchBean = new LibrarySearch();
		searchBean.setSearchTerm(LibrarySearch.SEARCH_KEYWORD_TITLE, titleString);
		
		// check the search string
		String searchString = searchBean.getSearchUrl(this.rootUrl);
		String expectedString = this.rootUrl + "?" + LibrarySearch.SEARCH_KEYWORD_TITLE + "=" + titleString;
		this.assertNotNull(searchString);
		this.assertEquals(expectedString, searchString);
	}

}
