package com.railtiffin.railtiffin_dialer_application;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

public class PushReceiver extends BroadcastReceiver {

	Context c;
	String notificationDesc;
	String id, number;

	@Override
	public void onReceive(Context context, Intent intent) {

		c = context;

		if (intent.getStringExtra("message") != null) {
			notificationDesc = intent.getStringExtra("message");
			String[] parts = notificationDesc.split("-");
			id = parts[0];
			number = parts[1];

		}

		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + "0" + number));
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		c.startActivity(callIntent);

		// notificationTest(notificationDesc);

		new PushCallback().execute();

	}

	class PushCallback extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			pushReceivedCallback();

			return null;
		}

	}

	public void pushReceivedCallback() {

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(
				"http://railtiffin.com/dialer_pushy/logger.php?rcvd_time=now()"
						+ "&id=" + id);

		try {
			HttpResponse response = httpClient.execute(httpPost, localContext);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notificationTest(String message) {

		// -----------------------------
		// Create a test notification
		//
		// (Use deprecated notification
		// API for demonstration purposes,
		// to avoid having to import
		// the Android Support Library)
		// -----------------------------

		String notificationTitle = "Pushy";
		String notificationDesc = message;

		// -----------------------------
		// Create a test notification
		// -----------------------------

		Notification notification = new Notification(
				android.R.drawable.ic_dialog_info, notificationDesc,
				System.currentTimeMillis());

		// -----------------------------
		// Sound + vibrate + light
		// -----------------------------

		notification.defaults = Notification.DEFAULT_ALL;

		// -----------------------------
		// Dismisses when pressed
		// -----------------------------

		notification.flags = Notification.FLAG_AUTO_CANCEL;

		// -----------------------------
		// Create pending intent
		// without a real intent
		// -----------------------------

		notification.setLatestEventInfo(c, notificationTitle, notificationDesc,
				null);

		// -----------------------------
		// Get notification manager
		// -----------------------------

		NotificationManager mNotificationManager = (NotificationManager) c
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// -----------------------------
		// Show the notification
		// -----------------------------

		mNotificationManager.notify(0, notification);
	}

}