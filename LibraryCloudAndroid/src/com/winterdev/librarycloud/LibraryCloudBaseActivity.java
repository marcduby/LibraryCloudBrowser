package com.winterdev.librarycloud;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public abstract class LibraryCloudBaseActivity extends ActionBarActivity {

	@Override
	/**
	 * inflate the common menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_results, menu);
		
		// return
		return true;
	}

	@Override
	/**
	 * handle the menu clicks
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
    	Intent intent;
    	switch (item.getItemId()) {
    		case R.id.action_settings:
    			intent = new Intent(this, AboutActivity.class);
    			this.startActivity(intent);
    			break;
    		case R.id.action_saved_items:
    			intent = new Intent(this, SavedItemsListActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    			this.startActivity(intent);
    			break;
    		case R.id.action_saved_searches:
    			intent = new Intent(this, SavedSearchesListActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    			this.startActivity(intent);
    			break;
    		case R.id.action_search:
    			intent = new Intent(this, SearchInputActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    			this.startActivity(intent);
    			break;
    		case R.id.action_help:
    			intent = new Intent(this, HelpActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    			this.startActivity(intent);
    			break;
    	}
		return super.onOptionsItemSelected(item);
	}
}
