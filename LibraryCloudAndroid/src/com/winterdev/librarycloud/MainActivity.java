package com.winterdev.librarycloud;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends LibraryCloudBaseActivity implements OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        
        // add the listeners for the buttons
        View countButton = this.findViewById(R.id.button_browse);
        countButton.setOnClickListener(this);
        
        View deluxeListButton = this.findViewById(R.id.button_browse_deluxe);
        deluxeListButton.setOnClickListener(this);
*/
        
        View listButton = this.findViewById(R.id.button_about);
        listButton.setOnClickListener(this);
        
        View searchInputButton = this.findViewById(R.id.button_search_title);
        searchInputButton.setOnClickListener(this);
        
    }

    /**
     * listen for button clicks
     * 
     */
    public void onClick(View view) {
    	Intent intent;
    	switch (view.getId()) {
/*
    		case R.id.button_browse:
    			intent = new Intent(this, SearchResultsActivity.class);
    			this.startActivity(intent);
    			break;
    		case R.id.button_browse_deluxe:
    			intent = new Intent(this, DeluxeSearchResultsActivity.class);
    			this.startActivity(intent);
    			break;
*/
    		case R.id.button_about:
    			intent = new Intent(this, AboutActivity.class);
    			this.startActivity(intent);
    			break;
    		case R.id.button_search_title:
    			intent = new Intent(this, SearchInputActivity.class);
    			this.startActivity(intent);
    			break;
    	}
    }
}
