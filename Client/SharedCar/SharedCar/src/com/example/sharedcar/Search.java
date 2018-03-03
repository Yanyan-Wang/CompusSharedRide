package com.example.sharedcar;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

import com.JPush.R;

public class Search extends TabActivity{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent1,intent2;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
       
        Intent intent = new Intent();	
    	intent.setClass(Search.this, SearchOne.class);
    	Search.this.startActivity(intent);

        // Initialize a TabSpec for each tab and add it to the TabHost
//        spec = tabHost.newTabSpec("1").setIndicator("¸ÅÂÔËÑË÷",
//                          res.getDrawable(R.drawable.ic_tab_artists))
//                      .setContent(intent1);
//        tabHost.addTab(spec);

//        // Do the same for the other tabs
//        intent2 = new Intent().setClass(this, SearchTwo.class);
//        spec = tabHost.newTabSpec("2").setIndicator("¾«È·ËÑË÷",
//                          res.getDrawable(R.drawable.ic_tab_artists))
//                      .setContent(intent2);
//        tabHost.addTab(spec);

       
  //      tabHost.setCurrentTab(2);
    }
}
 

 