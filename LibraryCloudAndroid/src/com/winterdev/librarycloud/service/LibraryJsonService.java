package com.winterdev.librarycloud.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.winterdev.librarycloud.domain.LibraryException;
import com.winterdev.librarycloud.domain.LibraryItem;
import com.winterdev.librarycloud.domain.LibraryItemLocation;

public class LibraryJsonService {
	// cached list of last search
	private List<LibraryItem> lastSearchList = null;
	
	// static singleton library json service
	private static LibraryJsonService libraryJsonService;
	
	/**
	 * singleton method to return link to static library json service
	 * 
	 * @return
	 */
	public static LibraryJsonService getLibraryJsonService() {
		if (libraryJsonService == null) {
			libraryJsonService = new LibraryJsonService();
		}
		
		return libraryJsonService;
	}

	/**
	 * method to build a list of library items from json response to library json service call
	 * 
	 * @param jsonString						the json response of the call
	 * @return									a list of library items
	 * @throws LibraryException					if there is an error
	 */
	public List<LibraryItem> getItems(String jsonString) throws LibraryException {
		List<LibraryItem> itemList = new ArrayList<LibraryItem>();
		JSONObject rootJson = null;
		JSONObject itemObject = null;
		LibraryItem item = null;
		try {
			
			if (jsonString == null) {
				throw new LibraryException("got null json return string");
			}
			
			rootJson = new JSONObject(jsonString);
			
			// get the item arrays
			JSONArray itemArray = rootJson.optJSONArray(LibraryItem.TAG_ITEMS);
			if (itemArray == null) {
				itemObject = rootJson.optJSONObject(LibraryItem.TAG_ITEMS);
				
				// if no items found, then both the array and the object search will be null, so list of items stays empty
				if (itemObject != null) {
					try {
						// create the item and parse it
						item = createLibraryItemFromJson(itemObject);
						
						// add to the list
						itemList.add(item);
						
					} catch (LibraryException exception) {
						String errorMessage = "got library item parse error: " + exception.getMessage();
						Log.e(LibraryJsonService.class.getName(), errorMessage);
						throw new LibraryException(errorMessage);
					}
				}
				
			} else {
				// for each item, create a new object and populate properties
				for (int i = 0; i < itemArray.length(); i++) { 
					itemObject = itemArray.getJSONObject(i);
					
					try {
						// create the item and parse it
						item = createLibraryItemFromJson(itemObject);
						
						// add to the list
						itemList.add(item);
						
					} catch (LibraryException exception) {
						String errorMessage = "got library item parse error: " + exception.getMessage();
						Log.v(LibraryJsonService.class.getName(), errorMessage);
						throw new LibraryException(errorMessage);
					}
				}
			}
			
		} catch (JSONException exception) {
			throw new LibraryException("Got JSON exception: " + exception.getMessage());
			
		}
		
		// set the cached list
		lastSearchList = itemList;
		
		// return the list
		return itemList;
	}

	/**
	 * method to retrieve the library item in the cached item list
	 * 
	 * @param position
	 * @return
	 */
	public LibraryItem getLastSearchListItem(int position) {
		// local variables
		LibraryItem item = null;
		
		if ((lastSearchList != null) && ((lastSearchList.size()) > position)) {
			item = lastSearchList.get(position);
			
		} else {
			item = new LibraryItem();
		}
		
		return item;
	}
	
	/**
	 * method to populate a library item from the JSON object passed; exceptions caught and repackaged
	 * 
	 * @param jsonObject
	 * @param item
	 * @return
	 */
	protected LibraryItem createLibraryItemFromJson(JSONObject jsonObject) throws LibraryException {
		LibraryItem item = new LibraryItem();

		try {
			// get the mods tag
			JSONObject modsJson = jsonObject.getJSONObject(LibraryItem.TAG_MODS);
			
			// get the title info
			JSONObject titleInfoJson = getFirstJsonObjectIfArray(modsJson, LibraryItem.TAG_TITLE_INFO);
			
			// get the subtitle and title
			String title = titleInfoJson.getString(LibraryItem.TAG_TITLE);
			String subTitle = titleInfoJson.optString(LibraryItem.TAG_SUBTITLE);
			
			// get the author name
			String authorName = null;
			JSONObject nameJson = getFirstJsonObjectIfArray(modsJson, LibraryItem.TAG_NAME);
			if (nameJson != null) {
				JSONArray namePartArray = nameJson.optJSONArray(LibraryItem.TAG_NAME_PART);
				if (namePartArray == null) {
					authorName = nameJson.getString(LibraryItem.TAG_NAME_PART);
				} else {
					authorName = namePartArray.getString(0);
				}
				item.addAuthorName(authorName);
			}
			
			// create add title information
			item.setTitle(title);
			item.setSubTitle(subTitle);
		
			// get the item locations
			addRecordLocationList(modsJson, item);
			
		} catch (JSONException exception) {
			throw new LibraryException("Got JSON exception: " + exception.getMessage());
		}
		
		// return if worked
		return item;
	}

	/**
	 * add locations to a item record
	 * 
	 * @param jsonObject
	 * @param item
	 * @throws LibraryException
	 */
	protected void addRecordLocationList(JSONObject jsonObject, LibraryItem item) throws LibraryException {
		// find the record info object
		
		// if if exists, look for the location array/object
		JSONArray locationArray = jsonObject.optJSONArray(LibraryItem.TAG_LOCATION);
		if (locationArray == null) {
			// look for object
			JSONObject locationObject = jsonObject.optJSONObject(LibraryItem.TAG_LOCATION);
			addRecordLocation(locationObject, item);
			
		} else {
			for (int i = 0; i < locationArray.length(); i++) {
				try {
					JSONObject locationObject = locationArray.getJSONObject(i);
					addRecordLocation(locationObject, item);
					
				} catch (JSONException exception) {
					Log.e(LibraryJsonService.class.getName(), "got json array error for location: " + exception.getMessage());
				}
			}
		}
	}

	/**
	 * add an individual item location
	 * 
	 * @param locationObject
	 * @param item
	 * @throws LibraryException
	 */
	protected void addRecordLocation(JSONObject locationObject, LibraryItem item) throws LibraryException {
		try {
			if (locationObject != null) {
				String buildingCode = locationObject.getString(LibraryItem.TAG_BUILDING);
				String  callNumber = locationObject.getString(LibraryItem.TAG_CATALOG);
				
				// set the location string on the object
				item.addLocation(buildingCode + ": " + callNumber);
				
				// add the location object on the object
				LibraryItemLocation locationItemObject = new LibraryItemLocation();
				locationItemObject.setLocationCode(buildingCode);
				locationItemObject.setCallNumber(callNumber);
				
				// get the library name
				if (buildingCode != null) {
					locationItemObject.setLocationName(LibrarySearchService.getLibrarySearchService().getLibraryName(buildingCode));
				}
	
				// add the object
				item.addLocationObject(locationItemObject);
//				Log.i(this.getClass().getName(), "added item location object: " + locationItemObject);
			}
			
		} catch (JSONException exception) {
			Log.e(LibraryJsonService.class.getName(), "got json error for location: " + exception.getMessage());
		}
	}
	
	/**
	 * returns the json object or the first instance of the object if it is an array
	 * 
	 * @param jsonObject
	 * @param tagString
	 * @return
	 * @throws JSONException
	 */
	protected JSONObject getFirstJsonObjectIfArray(JSONObject jsonObject, String tagString) throws JSONException {
		JSONArray jsonArray = jsonObject.optJSONArray(tagString);
		JSONObject returnObject = null;
		
		// if this is not an array, then return the object
		if (jsonArray == null) {
			returnObject = jsonObject.optJSONObject(tagString);
			
		// if it is an array, return the first element	
		} else {
			returnObject = jsonArray.getJSONObject(1);
		}
		
		// return result
		return returnObject;
	}

}
