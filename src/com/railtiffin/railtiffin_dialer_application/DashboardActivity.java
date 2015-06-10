package com.railtiffin.railtiffin_dialer_application;

import me.pushy.sdk.Pushy;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class DashboardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_dashboard);

		Pushy.listen(this);

		TextView regId = (TextView) findViewById(R.id.tvRegID);
		SharedPreferences sharedPref = getSharedPreferences("pushyRegID",
				Context.MODE_PRIVATE);
		String devID = sharedPref.getString("pushyRegID", "");

		if (devID.contentEquals("")) {
			startActivity(new Intent(DashboardActivity.this, MainActivity.class));
			finish();
		} else {
			regId.setText(devID);
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		Pushy.listen(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Pushy.listen(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Pushy.listen(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		Pushy.listen(this);
	}

}
