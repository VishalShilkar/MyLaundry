package com.example.mylaundry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.commons.CommonUtil;
import com.example.commons.ServiceAPI;
import com.example.login.AddressDTO;
import com.example.login.UserDTO;

import static android.content.ContentValues.TAG;

public class Signin extends Activity{
	Intent i=null;
	ImageView im=null;
	EditText tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
	CheckBox tv9;
	boolean flag=false;
	//SQLiteDatabase db=null;
	ServiceAPI serviceAPI = new ServiceAPI();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		im=(ImageView)findViewById(R.id.show_hide);
		tv1=(EditText)findViewById(R.id.firstNameText);
		tv2=(EditText)findViewById(R.id.lastNameText);
		tv3=(EditText)findViewById(R.id.mobileNumberText);
		tv4=(EditText)findViewById(R.id.emailIdText);
		tv5=(EditText)findViewById(R.id.address1Text);
		tv6=(EditText)findViewById(R.id.address2Text);
		tv7=(EditText)findViewById(R.id.pinCodeText);
		tv8=(EditText)findViewById(R.id.passwordText);
		tv9=(CheckBox)findViewById(R.id.shopOwnerCheck);
		//db=openOrCreateDatabase("mydb", MODE_PRIVATE, null);
		//db.execSQL("create table if not exists login(firstName varchar,mobile_no varchar,email_id varchar,password varchar,address varchar,flag varchar)");

		//db.execSQL("create table if not exists USER(USER_ID INTEGER NOT NULL PRIMARY KEY, FIRST_NAME varchar, LAST_NAME varchar, EMAIL_ID varchar, PHONE_NO varchar, USER_TYPE varchar, CREATE_DATE varchar, PASSWORD varchar)");
		//db.execSQL("create table if not exists ADDRESS(USER_ID INTEGER NOT NULL, ADDRESS_1 varchar, ADDRESS_2 varchar, ADDRESS_3 varchar, PIN_CODE varchar)");

		/** Create basic data structure on app startup **/
		serviceAPI.onStartUpActivity();
		
		im.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
			
				if(flag==false)
				{
					im.setImageResource(R.drawable.hide);
					tv4.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
					flag=true;
				}
				else
				{
					im.setImageResource(R.drawable.show);
					tv4.setInputType(129);
					flag=false;
					
				}
			}
		});
	}
	
	public void action(View v){
		switch(v.getId()){
			case R.id.log_in:
				i=new Intent(this,Login.class);
				startActivityForResult(i, 500);
				overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
				finish();
				break;
			case R.id.loginSubmit:
				String FIRST_NAME = tv1.getText().toString();
				String LAST_NAME = tv2.getText().toString();
				String MOBILE_NO = tv3.getText().toString();
				String EMAIL_ID = tv4.getText().toString();
				String ADDRESS1 = tv5.getText().toString();
				String ADDRESS2 = tv6.getText().toString();
				String PIN_CODE = tv7.getText().toString();
				String PASSWORD = tv8.getText().toString();
				Character USERTYPE = null;
				if(tv9.isChecked()) {
					USERTYPE = 'S';
				}else {
					USERTYPE = 'U';
				}

				if(CommonUtil.isNullOrEmpty(FIRST_NAME) || FIRST_NAME.length() < 3){
					show("Please Enter Correct First Name.");
				} else if(CommonUtil.isNullOrEmpty(LAST_NAME) || LAST_NAME.length() < 3){
					show("Please Enter Correct Last Name.");
				} else if(CommonUtil.isNullOrEmpty(MOBILE_NO) || MOBILE_NO.length() < 10){
					show("Please Enter Correct Mobile Number.");
				} else if(CommonUtil.isNullOrEmpty(PIN_CODE) || PIN_CODE.length() < 6){
					show("Please Enter Correct Pin Code.");
				} else if(CommonUtil.isNullOrEmpty(PASSWORD) || PASSWORD.length()<6){
					show("Please Enter Valid Password.");
				} else {
					Log.i("Start", "Form Validation Completed");
					//db.execSQL("insert into login values('"+FIRST_NAME+"','"+MOBILE_NO+"','"+LAST_NAME+"','"+EMAIL_ID+"',"+ "'"+ADDRESS1+"','nothing')");
					Integer userId = serviceAPI.getMaxUserId();
					if(CommonUtil.isNull(userId)){
						userId = 1;
					}
					AddressDTO addressDTO = new AddressDTO(userId, ADDRESS1, ADDRESS2, Integer.valueOf(PIN_CODE));
					UserDTO userDTO = new UserDTO(userId, FIRST_NAME, LAST_NAME, EMAIL_ID, Integer.valueOf(MOBILE_NO), USERTYPE, PASSWORD, addressDTO);

					boolean isSuccess = serviceAPI.persistUserTable(userDTO);
					if(isSuccess) {
						Log.i("View", "Record saved successfully for userId : " + userDTO.getUserId() + " and Name: " + userDTO.getFirstName());
					}else {
						Log.i(TAG, "action: Error while persistin User details.");
					}

					i=new Intent(this,UserHome.class);
					i.putExtra("username",FIRST_NAME);

					storeSharedPreferences(userDTO);

					startActivityForResult(i, 500);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					finish();
				}
				break;
		}
  }

  	private void storeSharedPreferences(UserDTO userDTO){
		SharedPreferences sharedPref = getSharedPreferences("loginSession",MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = sharedPref.edit();
		prefEditor.putString("userName", userDTO.getFirstName());
		prefEditor.putString("phoneNumber", userDTO.getPhoneNumber().toString());
		prefEditor.putInt("userId", userDTO.getUserId());
		prefEditor.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	} 
	
	public void show(String str)
	{
	Toast.makeText(this, str, Toast.LENGTH_LONG).show();	
	}


}
