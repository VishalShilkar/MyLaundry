package com.example.mylaundry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

import com.example.commons.CommonUtil;

public class MainActivity extends Activity {

	Intent i=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences sharedPref = getSharedPreferences("loginSession",MODE_PRIVATE);
		int number = sharedPref.getInt("isLogged", 0);
		String userName = sharedPref.getString("userName", null);
		String phoneNumber = sharedPref.getString("phoneNumber", null);
		int userId = sharedPref.getInt("userId", 0);
		if(number == 0 && CommonUtil.isNullOrEmpty(userName) && CommonUtil.isNullOrEmpty(phoneNumber)) {
			//Open the login activity and set this so that next it value is 1 then this condition will be false.
			SharedPreferences.Editor prefEditor = sharedPref.edit();
			prefEditor.putInt("isLogged",1);
			prefEditor.commit();

			setContentView(R.layout.activity_main);
		} else {
			//Open this Home activity
			i=new Intent(this,UserHome.class);

			Bundle bundle = new Bundle();
			bundle.putString("firstName", userName);
			bundle.putString("phoneNo", phoneNumber);
			bundle.putInt("userId", userId);

			i.putExtras(bundle);

			startActivityForResult(i, 500);
		}


	}
	
	public void login_sigin(View v)
	{
		switch(v.getId())
		{
		case R.id.log_in:
			i=new Intent(this,Login.class);
			startActivityForResult(i, 500);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); 
			break;
		case R.id.sign_in:
			i=new Intent(this,Signin.class);
			startActivityForResult(i, 500);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
			break;	
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}