package com.winterdev.librarycloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.winterdev.librarycloud.R;
import com.winterdev.librarycloud.adapter.LibraryItemArrayAdapter;
import com.winterdev.librarycloud.domain.LibraryException;
import com.winterdev.librarycloud.domain.LibraryItem;
import com.winterdev.librarycloud.domain.LibrarySearch;
import com.winterdev.librarycloud.service.LibraryJsonService;
import com.winterdev.librarycloud.service.LibrarySearchService;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class DeluxeSearchResultsActivity extends ActionBarActivity implements OnItemClickListener {
	// constants
	private final String searchJsonUrl 	= "http://api.lib.harvard.edu/v2/items.json";
	private final String searchUrl 		= "http://api.lib.harvard.edu/v2/items.json?title=";
	private final String searchLimit 	= "&limit=25";
	private LibrarySearchService librarySearchService = LibrarySearchService.getLibrarySearchService();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_listview);

		// build the library search bean from the intent
		LibrarySearch librarySearch = this.librarySearchService.createLibrarySearchBean(this.getIntent());

		/* refactor with the bean
		// search based on the library search 
		String titleSearchString = this.getIntent().getExtras().getString(LibraryItem.SESSION_TITLE_SEARCH);
		if (titleSearchString == null) {
			// default to dummy search
			this.searchForAndDisplayItems();
		} else {
			this.searchForAndDisplayItemsForTitleKeyword(titleSearchString);
		}
		*/
		
		// search based on the library search 
		if (librarySearch == null) {
			// default to dummy search
			this.searchForAndDisplayItems();
		} else {
			this.searchWithLibrarySearchBean(librarySearch);
		}

	}

	/**
	 * search method for the items through an async query
	 * 
	 */
	protected void searchForAndDisplayItems() {
		DeluxeSearchResultsClass searchInnerClass = new DeluxeSearchResultsClass();
//		searchInnerClass.execute("http://api.lib.harvard.edu/v2/items.json?title=peanuts&identifier=29026500");
		searchInnerClass.execute("http://api.lib.harvard.edu/v2/items.json?title=peanuts");
	}

	/**
	 * search for title keyword
	 */
	protected void searchForAndDisplayItemsForTitleKeyword(String titleKeyword) {
		String searchTitleUrl = this.searchUrl + titleKeyword + this.searchLimit;
		DeluxeSearchResultsClass searchInnerClass = new DeluxeSearchResultsClass();
//		searchInnerClass.execute("http://api.lib.harvard.edu/v2/items.json?title=peanuts&identifier=29026500");
		searchInnerClass.execute(searchTitleUrl);
	}

	/**
	 * search for title keyword
	 */
	protected void searchWithLibrarySearchBean(LibrarySearch librarySearch) {
		DeluxeSearchResultsClass searchInnerClass = new DeluxeSearchResultsClass();
		String searchUrlString = librarySearch.getSearchUrl(this.searchJsonUrl);
		Log.i(this.getClass().getName(), "searching with search bean with url: " + searchUrlString);
		searchInnerClass.execute(searchUrlString);
	}

	protected String getLibrarySearchResults(String urlString) throws Exception {
		StringBuilder builder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
//		HttpGet httpGet = new HttpGet("http://api.lib.harvard.edu/v2/items.json?title=peanuts&identifier=29026500");
		HttpGet httpGet = new HttpGet(urlString);
		
		try {
			// make the rest call
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			
			// check for good status code
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream stream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

				// get the data
				String line;
				while((line = reader.readLine()) != null) {
					builder.append(line);
				}
				
			} else {
				throw new Exception("No 200 data status code");
			}
			
		} catch (ClientProtocolException exception) {
			throw new Exception("Got protocal exception: " + exception.getMessage());
			
		} catch (IOException exception) {
			throw new Exception("Got io exception: " + exception.getMessage());
		}
		
		// return
		return builder.toString();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_results, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_search_results,
					container, false);
			return rootView;
		}
	}
	
	/**
	 * sets the text in the search success text view
	 * 
	 * @param textString
	 */
	protected void setSearchSuccessText(String textString) {
		TextView searchSuccessText = (TextView)this.findViewById(R.id.deluxe_search_success_text);
		searchSuccessText.setText(textString);
	}

	/**
	 * sets the search results and bind the array adapter to the list view
	 * 
	 * @param strings
	 */
	protected void setSearchResultsList(List<LibraryItem> itemList) {
		// get the array adapter
		final LibraryItemArrayAdapter itemAdapter = new LibraryItemArrayAdapter(this, R.layout.library_item_list, itemList);
		
		// set the adapter to the list view
		ListView itemListView = (ListView) this.findViewById(R.id.library_item_listview);
		itemListView.setAdapter(itemAdapter);
		
		// set anonymous class to listen to list view
		itemListView.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, LibraryItemDetailsActivity.class);
		intent.putExtra(LibraryItem.SESSION_ITEM_LIST_INDEX, position);
		this.startActivity(intent);

//		Toast.makeText(getApplicationContext(),  "position: " + position,  Toast.LENGTH_LONG).show();
	}
	
	// -------------------- inner class ---------------------------
	/**
	 * inner class to search for results outside of the UI thread
	 *  
	 */
	private class DeluxeSearchResultsClass extends AsyncTask<String, Void, String> {
		String successString = null;
		
		/**
		 * start method
		 */
		protected String doInBackground(String... urlString) {
			String result = null;
			
			try {
				result = getLibrarySearchResults(urlString[0]);
				
				this.successString = "Search successful";

			} catch (Exception exception) {
				String errorString = "Got inner class exception: " + exception.getMessage();
				this.successString = errorString;
			}
			
			return result;
		}
		
		/**
		 * method executed after search comes back
		 * 
		 */
		protected void onPostExecute(String result) {
			// assume JSON string, so parse
	
			try {
				List<LibraryItem> itemList = LibraryJsonService.getItems(result);
//					JSONObject json = new JSONObject(result);
//					String title = json.getString("title");
//					resultsView.setText(title);
//					resultsView.setText(strings[0]);
				
//					setSearchResultsText(title);
				setSearchResultsList(itemList);
				
				if (itemList.size() > 0) {
					setSearchSuccessText(this.successString + ", " + itemList.size() + " results.");
					
				} else {
					setSearchSuccessText(this.successString + ", no items found.");
				}
				
			} catch (LibraryException exception) {
				Log.e(this.getClass().getName(), exception.getMessage());
				setSearchSuccessText("Ooops, we had an issue...");
			}
				
		}

	}
	
}
