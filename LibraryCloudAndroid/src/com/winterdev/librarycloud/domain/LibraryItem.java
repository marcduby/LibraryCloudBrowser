package com.winterdev.librarycloud.domain;

import java.util.ArrayList;
import java.util.List;

public class LibraryItem {
	
	// constants for the JSON tags
	public static final String TAG_PAGINATION 	= "pagination";
	public static final String TAG_ITEMS 		= "items";
	public static final String TAG_MODS 		= "mods";
	public static final String TAG_TITLE_INFO	= "titleInfo";
	public static final String TAG_TITLE 		= "title";
	public static final String TAG_SUBTITLE		= "subTitle";
	public static final String TAG_NAME 		= "name";
	public static final String TAG_NAME_PART	= "namePart";
	public static final String TAG_RECORD_INFO	= "recordInfo";
	public static final String TAG_LOCATION		= "location";
	public static final String TAG_BUILDING		= "physicalLocation";
	public static final String TAG_CATALOG		= "shelfLocator";
	
	// constants for passing intent data
	public static final String SESSION_TITLE_SEARCH 			= "TITLE_SEARCH_STRING";
	public static final String SESSION_ITEM_LIST_INDEX 			= "ITEM_LIST_INDEX";
	
	private String title;
	private String subTitle;
	private List<String> authorNameList = new ArrayList<String>();
	private List<String> locationList = new ArrayList<String>();
	private List<LibraryItemLocation> itemLocationList = new ArrayList<LibraryItemLocation>();
	private String authorNameString = null;
	private String userNotes;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public void addAuthorName(String name) {
		this.authorNameList.add(name);
	}
	
	public List<String> getLocationList() {
		return locationList;
	}
	
	/**
	 * add a location string to the locations list
	 * 
	 * @param locationString
	 */
	public void addLocation(String locationString) {
		// add string location
		if (this.locationList == null) {
			this.locationList = new ArrayList<String>();
		}
		
		this.locationList.add(locationString);
	}

	/**
	 * add a location object to the location object list
	 * 
	 * @param locationObject
	 */
	public void addLocationObject(LibraryItemLocation locationObject) {
		// add object location
		if (this.itemLocationList == null) {
			this.itemLocationList = new ArrayList<LibraryItemLocation>();
		}
		
		this.itemLocationList.add(locationObject);
	}
	
	/**
	 * returns strong with formatted line breaks
	 * 
	 * @return
	 */
	public String getLocationsFormattedString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.locationList.size(); i++) {
			if (i > 0) {
				builder.append("\n");
			}
			builder.append(this.locationList.get(i));
		}
		
		return builder.toString();
	}
	
	/**
	 * returns location objects string with formatted line breaks
	 * 
	 * @return
	 */
	public String getLocationObjectsFormattedString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.itemLocationList.size(); i++) {
			if (i > 0) {
				builder.append("\n===========\n");
			}
			builder.append(this.itemLocationList.get(i).getLocationName() + "\n" + this.itemLocationList.get(i).getCallNumber());
		}
		
		return builder.toString();
	}

	/**
	 * returns the author name as a string
	 * 
	 * @return
	 */
	public String getAuthorNameString() {
		if (this.authorNameString == null) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < this.authorNameList.size(); i++) {
				builder.append(this.authorNameList.get(i));
			}
			this.authorNameString = builder.toString();
		}
		
		return this.authorNameString;
	}
	
	public String getUserNotes() {
		return userNotes;
	}
	public void setUserNotes(String userNotes) {
		this.userNotes = userNotes;
	}
}
