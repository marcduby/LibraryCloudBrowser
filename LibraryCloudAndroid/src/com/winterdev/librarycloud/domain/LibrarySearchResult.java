package com.winterdev.librarycloud.domain;

import java.util.ArrayList;
import java.util.List;

public class LibrarySearchResult {
	
	private int numberResults;
	private int startNumber;
	private int endNumber;
	private int limitNumber;
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
	public int getLimitNumber() {
		return limitNumber;
	}
	public void setLimitNumber(int limit) {
		this.limitNumber = limit;
	}
	public int getNumberResults() {
		return numberResults;
	}
	public void setNumberResults(int numberResults) {
		this.numberResults = numberResults;
	}
	public int getStartNumber() {
		return startNumber;
	}
	public void setStartNumber(int pageStartNumber) {
		this.startNumber = pageStartNumber;
	}
	public int getEndNumber() {
		return this.endNumber;
	}
	public void setEndNumber(int pageEndNumber) {
		this.endNumber = pageEndNumber;
	}
	public List<LibraryItem> getItemList() {
		return itemList;
	}
	
	
	

}
