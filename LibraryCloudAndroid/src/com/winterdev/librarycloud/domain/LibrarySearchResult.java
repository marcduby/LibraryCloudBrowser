package com.winterdev.librarycloud.domain;

import java.util.ArrayList;
import java.util.List;

public class LibrarySearchResult {
	
	private int numberResults;
	private int pageStartNumber;
	private int pageEndNumber;
	private List<LibraryItem> itemList;
	
	/**
	 * add a library item to the list
	 * 
	 * @param item
	 */
	public void addLibraryItem(LibraryItem item) {
		if (this.itemList == null) {
			this.itemList = new ArrayList<LibraryItem>();
		}
		
		this.itemList.add(item);
	}
	
	// auto generated methods
	
	public int getNumberResults() {
		return numberResults;
	}
	public void setNumberResults(int numberResults) {
		this.numberResults = numberResults;
	}
	public int getPageStartNumber() {
		return pageStartNumber;
	}
	public void setPageStartNumber(int pageStartNumber) {
		this.pageStartNumber = pageStartNumber;
	}
	public int getPageEndNumber() {
		return pageEndNumber;
	}
	public void setPageEndNumber(int pageEndNumber) {
		this.pageEndNumber = pageEndNumber;
	}
	public List<LibraryItem> getItemList() {
		return itemList;
	}
	
	
	

}
