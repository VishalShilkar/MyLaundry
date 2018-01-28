package com.example.mylaundry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserHome extends Activity{
	Intent i=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		String name = bundle.getString("firstName");
		setContentView(R.layout.userhome);
		TextView textView = (TextView) findViewById(R.id.w_welcome);
		textView.setText("Welcome "+name);
	}

	public void action(View v){
		switch(v.getId()){
			case R.id.schedulepickup:
				i=new Intent(this,SchedulePickup.class);
				startActivityForResult(i, 500);
				overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
				finish();
				break;

			case R.id.showMyDetails:
				Bundle bundle = getIntent().getExtras();
				i=new Intent(this,MyDetails.class);
				i.putExtras(bundle);
				startActivityForResult(i, 500);
				overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
				finish();
				break;
		}
	}
}
