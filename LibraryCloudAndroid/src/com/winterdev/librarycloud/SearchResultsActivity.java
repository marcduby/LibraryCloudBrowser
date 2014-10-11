package com.winterdev.librarycloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import com.winterdev.librarycloud.domain.LibraryException;
import com.winterdev.librarycloud.domain.LibraryItem;
import com.winterdev.librarycloud.domain.LibrarySearch;
import com.winterdev.librarycloud.service.LibraryJsonService;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class SearchResultsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);

		SearchResultsClass searchInnerClass = new SearchResultsClass();
		searchInnerClass.execute("http://api.lib.harvard.edu/v2/items.json?title=peanuts&identifier=29026500");
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
	
	private class SearchResultsClass extends AsyncTask<String, Void, String> {
		String successString = null;
		
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
		
		protected void onPostExecute(String result) {
			// assume JSON string, so parse
	
			try {
				List<LibraryItem> itemList = LibraryJsonService.getItems(result);
//					JSONObject json = new JSONObject(result);
//					String title = json.getString("title");
//					resultsView.setText(title);
//					resultsView.setText(strings[0]);
				
//					setSearchResultsText(title);
				setSearchResultsText("Title: " + itemList.get(0).getTitle());
				
				setSearchSuccessText(this.successString);
				
			} catch (LibraryException exception) {
				Log.e(this.getClass().getName(), exception.getMessage());
				setSearchSuccessText("Ooops, we had an issue...");
			}
				
		}

	}
	
	protected void setSearchSuccessText(String textString) {
		TextView searchSuccessText = (TextView)this.findViewById(R.id.search_success_text);
		searchSuccessText.setText(textString);
	}

	protected void setSearchResultsText(String...strings) {
		// set the results on the text view
		TextView resultsView = (TextView)this.findViewById(R.id.search_results_text);
		resultsView.setText(strings[0]);
	}
}
