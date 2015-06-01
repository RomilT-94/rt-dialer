package com.google.android.gcm.demo.app;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.EditText;


public class RegActivity extends Activity {
	
	SharedPreferences dailerprefs;

	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	        dailerprefs = getApplicationContext().getSharedPreferences("DialerPrefs", 1); 
	        
	        if(dailerprefs.getString("regphno", "notset")=="notset"){
	        
	        AlertDialog.Builder alert = new AlertDialog.Builder(this);

	        alert.setTitle("Enter your phone number");
	       

	        // Set an EditText view to get user input 
	        final EditText input = new EditText(this);
	        alert.setView(input);

	        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	           String value = input.getText().toString();
	          // Do something with value!
	           
	          
	   	       final Editor editor = dailerprefs.edit();
	   	       editor.putString("regphno", value);
	   	       editor.commit();
	           System.out.println("Input Value: "+value);
	           
	           Intent intent = new Intent(RegActivity.this, DemoActivity.class);
	           intent.putExtra("regphno", value);
	           startActivity(intent);
	           
	          }
	        });

	        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int whichButton) {
	            // Canceled.
	          }
	        });

	        alert.show();
	       }
	        else{
	        	Intent intent = new Intent(RegActivity.this, DemoActivity.class);
		        
		        startActivity(intent);
	        }

	  }
}
