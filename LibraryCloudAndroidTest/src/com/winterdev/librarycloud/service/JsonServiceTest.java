package com.winterdev.librarycloud.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.winterdev.librarycloud.test.R;

import android.test.AndroidTestCase;
import android.util.Log;

import com.winterdev.librarycloud.domain.*;

public class JsonServiceTest extends AndroidTestCase {
	// instance variables
	String testJsonStrting = null;
	
	// constants
	private final String SINGLE_ITEM_FILE_PATH = "testFiles/singleJsonElement.json";
	private final String MULTI_ITEM_FILE_PATH = "testFiles/multiJsonElement.json";
	private final String PROBLEM_ITEM_FILE_PATH = "testFiles/problemJsonElement.json";

	/**
	 * test for a single item
	 */
	public void testGetSingleItem() {
		String jsonString = null;
		List<LibraryItem> itemList = null;
		
		// load the json test string
		try {
			jsonString = this.getUniqueItemTestString();
			
			// log
			Log.i(this.getClass().getName(), "got json test string: " + jsonString);
			
			// parse the string and get the library item
			try {
				itemList = LibraryJsonService.getItems(jsonString);
				
				assertNotNull(itemList);
				assertTrue(itemList.size() > 0);
				assertEquals(itemList.size(), 1);
				
				for (int i = 0; i < itemList.size(); i++) {
					Log.i(this.getClass().getName(), "got library item with title: " + itemList.get(i).getTitle());
				}
				
			} catch (LibraryException exception) {
				fail("got error parsing json string: " + exception.getMessage());
			}
			
			
			
		} catch (IOException exception) {
			fail("got error loading test string: " + exception.getMessage());
		}
	}

	/**
	 * test for multiple items
	 */
	public void testGetMultipleItem() {
		String jsonString = null;
		List<LibraryItem> itemList = null;
		
		// load the json test string
		try {
			jsonString = this.getMultiItemTestString();
			
			// log
			Log.i(this.getClass().getName(), "got json test string: " + jsonString);
			
			// parse the string and get the library item
			try {
				itemList = LibraryJsonService.getItems(jsonString);
				
				assertNotNull(itemList);
				assertTrue(itemList.size() > 0);
				assertEquals(itemList.size(), 10);
				
				for (int i = 0; i < itemList.size(); i++) {
					Log.i(this.getClass().getName(), "got library item with title: " + itemList.get(i).getTitle() + " and author: " + itemList.get(i).getAuthorNameString());
					Log.i(this.getClass().getName(), "library item has locations: " + itemList.get(i).getLocationList());
				}
				
			} catch (LibraryException exception) {
				fail("got error parsing json string: " + exception.getMessage());
			}
			
			
			
		} catch (IOException exception) {
			fail("got error loading test string: " + exception.getMessage());
		}
	}

	/**
	 * test for multiple items
	 */
	public void testGetMultipleItemProblem() {
		String jsonString = null;
		List<LibraryItem> itemList = null;
		
		// load the json test string
		try {
			jsonString = this.getProbleItemTestString();
			
			// log
			Log.i(this.getClass().getName(), "got json test string: " + jsonString);
			
			// parse the string and get the library item
			try {
				itemList = LibraryJsonService.getItems(jsonString);
				
				assertNotNull(itemList);
				assertTrue(itemList.size() > 0);
				assertEquals(itemList.size(), 10);
				
				for (int i = 0; i < itemList.size(); i++) {
					Log.i(this.getClass().getName(), "got library item with title: " + itemList.get(i).getTitle() + " and author: " + itemList.get(i).getAuthorNameString());
				}
				
			} catch (LibraryException exception) {
				fail("got error parsing json string: " + exception.getMessage());
			}
			
			
			
		} catch (IOException exception) {
			fail("got error loading test string: " + exception.getMessage());
		}
	}

	/**
	 * returns the single item json string
	 * @return
	 * @throws IOException
	 */
	protected String getUniqueItemTestString()  throws IOException {
		return this.readStringFromFilePath(this.SINGLE_ITEM_FILE_PATH);
	}
	
	/**
	 * returns the multi item json string
	 * @return
	 * @throws IOException
	 */
	protected String getMultiItemTestString()  throws IOException {
		return this.readStringFromFilePath(this.MULTI_ITEM_FILE_PATH);
	}
	
	protected String getProbleItemTestString()  throws IOException {
		return this.readStringFromFilePath(this.PROBLEM_ITEM_FILE_PATH);
	}
	
	/**
	 * read the test string
	 * 
	 * @return
	 * @throws IOException
	 */
	protected String readStringFromFilePath(String filePath) throws IOException {
		// set up the streams
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
		InputStreamReader streamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(streamReader);
		
		// get the string builder
		StringBuilder builder = new StringBuilder();
		
		// get the lines
		String line = null;
		while((line = reader.readLine()) != null) {
			builder.append(line);
		}
		
		// return string
		return builder.toString();
	}
}
