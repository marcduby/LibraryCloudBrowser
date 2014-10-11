package com.winterdev.librarycloud;

import com.winterdev.librarycloud.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener {


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
    
    /**
     * sets the menu on the activity
     */
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.getMenuInflater().inflate(R.menu.menu_main, menu);
    	return true;
    }
    
    @Override
    /**
     * handle the menu item selection
     */
    public boolean onOptionsItemSelected(MenuItem item) {
    	// handle the menu item selection
    	switch (item.getItemId()) {
    	case R.id.menu_saved_searches:
    		// TODO
    		// send to the saved searches activity
    		return true;
    	case R.id.menu_preferences:
    		// TODO
    		// send to the preferences setting activity
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
}
