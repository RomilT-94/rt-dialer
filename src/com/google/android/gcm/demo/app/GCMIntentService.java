/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gcm.demo.app;

import static com.google.android.gcm.demo.app.CommonUtilities.SENDER_ID;
import static com.google.android.gcm.demo.app.CommonUtilities.displayMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {
	SharedPreferences dailerprefs;
    @SuppressWarnings("hiding")
    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
    	
    	dailerprefs = getApplicationContext().getSharedPreferences("DialerPrefs", 1);
    	
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, getString(R.string.gcm_registered,
                registrationId));
        ServerUtilities.register(context, registrationId);
        
        if(dailerprefs.getString("regphno", "notset")!="notset")
     		sendPostRequest(dailerprefs.getString("regphno", "notset"),registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
    	
    	 if(intent.getExtras().getString("message")==null){
    	
    		Log.i(TAG, "Received message. Extras: " + intent.getExtras().getString("message"));
    		
            String message = getString(R.string.gcm_message);
            displayMessage(context, message);
            
         // notifies user
            generateNotification(context, message);
    	 }
    	 
    	 
        if(intent.getExtras().getString("message")!=null){
        	
        	String phno = intent.getExtras().getString("message");
        	Uri number = Uri.parse("tel:" + phno);
        	Intent dial = new Intent(Intent.ACTION_CALL, number);
        	dial.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(dial);
        }
        
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_stat_gcm;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, DemoActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }
    
    
    private void sendPostRequest(String phno, String devid) {
		class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{
		@Override
		protected String doInBackground(String... params) {
		  String paramPHNO = params[0];
	      String paramDEVID = params[1];
		  
		  System.out.println("*** doInBackground ** paramPHNO " + paramPHNO + " paramDEVID :" + paramDEVID);
		  HttpClient httpClient = new DefaultHttpClient();
		  
		  // In a POST request, we don't pass the values in the URL.
		  //Therefore we use only the web page URL as the parameter of the HttpPost argument
		  HttpPost httpPost = new HttpPost("http://railtiffin.com/dailer/register.php");
		  // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
		  //uniquely separate by the other end.
		  //To achieve that we use BasicNameValuePair	
		  //Things we need to pass with the POST request
		  BasicNameValuePair phnoBasicNameValuePair = new BasicNameValuePair("paramPHNO", paramPHNO);
		  BasicNameValuePair devidBasicNameValuePAir = new BasicNameValuePair("paramDEVID", paramDEVID);
		 
		
		  // We add the content that we want to pass with the POST request to as name-value pairs
		  //Now we put those sending details to an ArrayList with type safe of NameValuePair
		  List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		  nameValuePairList.add(phnoBasicNameValuePair);
		  nameValuePairList.add(devidBasicNameValuePAir);
		  
		try {
		  // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs. 
		  //This is typically useful while sending an HTTP POST request. 
		  UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
		  // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
		  httpPost.setEntity(urlEncodedFormEntity);
		  try {
		    // HttpResponse is an interface just like HttpPost.
		    //Therefore we can't initialize them
		    HttpResponse httpResponse = httpClient.execute(httpPost);
		    // According to the JAVA API, InputStream constructor do nothing. 
		    //So we can't initialize InputStream although it is not an interface
		    InputStream inputStream = httpResponse.getEntity().getContent();
		    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		    StringBuilder stringBuilder = new StringBuilder();
		    String bufferedStrChunk = null;
		    while((bufferedStrChunk = bufferedReader.readLine()) != null){
		    stringBuilder.append(bufferedStrChunk);
	    	}
		   return stringBuilder.toString();
		    } catch (ClientProtocolException cpe) {
		        System.out.println("First Exception caz of HttpResponese :" + cpe);
		         cpe.printStackTrace();
		     } catch (IOException ioe) {
		     System.out.println("Second Exception caz of HttpResponse :" + ioe);
		     ioe.printStackTrace();
		    }
		} catch (UnsupportedEncodingException uee) {
		System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
		uee.printStackTrace();
		}
		return null;
		}
		@Override
		protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(result.equals("working")){
			Toast.makeText(getApplicationContext(), "Thanks for registering", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getApplicationContext(), "Oops! Something went wrong!", Toast.LENGTH_LONG).show();
		}
		}	
		}
		SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
		sendPostReqAsyncTask.execute(phno, devid);	
		}

}
