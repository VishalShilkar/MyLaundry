package com.example.mylaundry;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by shilk on 11/28/2017.
 */

public class SchedulePickup extends Activity implements View.OnClickListener{

    ImageView datePicker,timePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText txtDate, txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedulepickup);

        List clothList = new ArrayList();
        clothList.add("Choose Options");
        clothList.add("Suit");
        clothList.add("Shirt");
        clothList.add("Saree");
        clothList.add("Pant");

        List<String> optionsList = new ArrayList<String>();
        optionsList.add("A1 Laundry, Charkop sector no 2.");
        optionsList.add("Shankar Cleaners, Sector no 5, charkop");
        //optionsList.add("Swipe Cleaners, charkop market");
        //optionsList.add("Jay Ambe Laundry, Borivali , Bhabhai naka");
        optionsList.add("Heavy Cleaners, Borivali, Near Don Bosco School");

        Spinner spinner = (Spinner) findViewById(R.id.clothList);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clothList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.optionsGroup);
        RadioButton[] rb = new RadioButton[optionsList.size()];
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < optionsList.size(); i++) {
            rb[i] = new RadioButton(this);
            radioGroup.addView(rb[i]);
            rb[i].setTextColor(Color.BLACK);
            rb[i].setText(optionsList.get(i));
        }

        //ScrollView scrollView = (ScrollView) findViewById(R.id.optionsScrollView);


        datePicker = (ImageView) findViewById(R.id.myCalender);
        datePicker.setOnClickListener(this);
        timePicker = (ImageView) findViewById(R.id.myClock);
        timePicker.setOnClickListener(this);
        txtDate=(EditText)findViewById(R.id.pickUpDateEdit);
        txtTime=(EditText)findViewById(R.id.pickUpTimeEdit);
    }

    @Override
    public void onClick(View v) {

        if (v == datePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == timePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}
