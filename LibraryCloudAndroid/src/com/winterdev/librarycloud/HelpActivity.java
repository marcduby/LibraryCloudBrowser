package com.winterdev.librarycloud;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class HelpActivity extends LibraryCloudBaseActivity {
	
    @Override
    /**
     * create activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

	@Override
	/**
	 * override creating options menu to set the selected icon on the search menu item
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// call parent to inflate menu
		super.onCreateOptionsMenu(menu);
		
		// set the different icons based on what activity it is
		MenuItem menuItem = (MenuItem)menu.getItem(3);
//		MenuItem menuItem = (MenuItem)this.findViewById(R.id.action_search);
		menuItem.setIcon(this.getResources().getDrawable(R.drawable.wd_action_question_selected));
		Log.i(this.getClass().getName(), "got selected search icon");
			
		// return
		return true;
	}

}
