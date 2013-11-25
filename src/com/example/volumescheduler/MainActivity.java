package com.example.volumescheduler;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;

public class MainActivity extends Activity {
    
	//DBHelper dbHelper;
 	final String LOG_TAG = "myLogs";
 
 	private ListView list;
 	private ImageButton btn_add;
 	private TextView emptyView;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        //list  = (ListView) findViewById(R.id.list_view);
        btn_add = (ImageButton) findViewById(R.id.btn_add);
        //emptyView = (TextView) findViewById(android.R.id.empty);
       
        //btn_add.setOnClickListener(this);
        //list.setOnItemClickListener(this);
        //list.setEmptyView(emptyView);
    }
    /*
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_add:
          dlg.show(getFragmentManager(), "dlg1");
          break;
        default:
          break;
        }
        }
      */

	public void onClick(View v) {
		
		switch (v.getId()) {
        case R.id.btn_add:
        {
        	Intent intent = new Intent(v.getContext(), AddRule.class);
            startActivity(intent);
        }
          break;
        default:
          break;
        }
		
	}
    
    
    
    
    /*
    public void onClickStart(View v) {
    	TimeTable tt = new TimeTable();
    	tt.id=0;
    	tt.hour=1;
    	tt.min=8;		  
    	tt.day=3;
    	tt.state=2;
    	tt.rule=1;
    	tt.enable=1;
  	  
    	DBHelper db = new DBHelper(this);
    	
    	Log.d(LOG_TAG, "DB init");
    	
    	db.Save(tt); 
    	
    	Log.d(LOG_TAG, "DB save");
    	
    	db.Save(tt); 
    	
    	Log.d(LOG_TAG, "DB compl");
    	
    	startService(new Intent(this, MainService.class));      
    }
    
    public void onClickStop(View v) {
    	stopService(new Intent(this, MainService.class));
    }
	*/
}
