package com.railtiffin.railtiffin_dialer_application;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import me.pushy.sdk.Pushy;
import me.pushy.sdk.exceptions.PushyException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView mRegistrationID;
	EditText phoneNumber;
	String deviceID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ------------------------------
		// Load activity_main.xml layout
		// ------------------------------

		setContentView(R.layout.activity_main);
		phoneNumber = (EditText) findViewById(R.id.etPhoneNumber);

		Button register = (Button) findViewById(R.id.bRegister);
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new RegisterForPushNotifications().execute();

			}
		});

		// ------------------------------
		// Cache TextView object
		// ------------------------------

		mRegistrationID = (TextView) findViewById(R.id.registrationID);

		// ------------------------------
		// Restart the socket service,
		// in case the user force-closed
		// ------------------------------

		Pushy.listen(this);

		// ------------------------------
		// Register up for push notifications
		// (will return existing token
		// if already registered before)
		// ------------------------------

	}

	private class RegisterForPushNotifications extends
			AsyncTask<String, Void, String> {
		ProgressDialog mLoading;

		public RegisterForPushNotifications() {
			// ------------------------------
			// Create progress dialog
			// and set it up
			// ------------------------------

			mLoading = new ProgressDialog(MainActivity.this);
			mLoading.setMessage("Please Wait....");
			mLoading.setCancelable(false);

			// ------------------------------
			// Show it
			// ------------------------------

			mLoading.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// ------------------------------
			// Temporary string containing
			// the registration ID result
			// ------------------------------

			String result = "";

			// ------------------------------
			// Get registration ID via Pushy
			// ------------------------------

			try {
				result = Pushy.register(MainActivity.this);
			} catch (PushyException exc) {
				// ------------------------------
				// Show error instead
				// ------------------------------

				result = exc.getMessage();
			}

			// ------------------------------
			// Write to log
			// ------------------------------

			Log.d("Pushy", "Registration result: " + result);

			// ------------------------------
			// Return result
			// ------------------------------

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// ------------------------------
			// Activity died?
			// ------------------------------

			if (isFinishing()) {
				return;
			}
			deviceID = result;
			// ------------------------------
			// Hide progress bar
			// ------------------------------

			mLoading.dismiss();

			// ------------------------------
			// Display it
			// ------------------------------

			mRegistrationID.setText(result);

			SharedPreferences sharedPref = getSharedPreferences("pushyRegID",
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("pushyRegID", result);
			editor.commit();
			
			new RegistrationTask().execute();

		}
	}

	class RegistrationTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(
					"http://railtiffin.com/dialer_pushy/register.php?phn="
							+ phoneNumber.getText().toString() + "&devid="
							+ deviceID);

			try {
				HttpResponse response = httpClient.execute(httpPost,
						localContext);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			startActivity(new Intent(MainActivity.this, DashboardActivity.class));
			finish();

		}

	}
}
