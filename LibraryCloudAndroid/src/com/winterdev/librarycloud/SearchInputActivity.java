package com.winterdev.librarycloud;

import com.winterdev.librarycloud.domain.LibraryItem;
import com.winterdev.librarycloud.domain.LibrarySearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class SearchInputActivity extends LibraryCloudBaseActivity implements OnClickListener {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_input);
        
        // add the listeners for the buttons
        View countButton = this.findViewById(R.id.button_search_title);
        countButton.setOnClickListener(this);
        
        View listButton = this.findViewById(R.id.button_reset_title);
        listButton.setOnClickListener(this);
    }

    /**
     * listen for button clicks
     * 
     */
    public void onClick(View view) {
    	Intent intent;
		EditText titleSearch = (EditText)this.findViewById(R.id.edit_search_title);
    	switch (view.getId()) {
    		case R.id.button_search_title:
    			// get the search edit text and send to search activity
    			String titleSearchString = titleSearch.getText().toString();
    			if ((titleSearchString == null) || (titleSearchString.length() < 1)) {
    				Toast.makeText(getApplicationContext(),  "Please enter a search term",  Toast.LENGTH_LONG).show();
    				
    			} else {
        			intent = new Intent(this, DeluxeSearchResultsActivity.class);
        			intent.putExtra(LibrarySearch.SEARCH_KEYWORD_TITLE, titleSearchString);
        			this.startActivity(intent);
    			}
    			break;
    		case R.id.button_reset_title:
    			// get the search edit view and reset it
    			titleSearch = (EditText)this.findViewById(R.id.edit_search_title);
    			titleSearch.setText("");
    			break;
    	}
    }

	@Override
	/**
	 * override creating options menu to set the selected icon on the search menu item
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// call parent to inflate menu
		super.onCreateOptionsMenu(menu);
		
		// set the different icons based on what activity it is
		MenuItem menuItem = (MenuItem)menu.getItem(0);
//		MenuItem menuItem = (MenuItem)this.findViewById(R.id.action_search);
		menuItem.setIcon(this.getResources().getDrawable(R.drawable.wd_action_search_selected));
		Log.i(this.getClass().getName(), "got selected search icon");
			
		// return
		return true;
	}
}
