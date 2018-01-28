package com.example.commons;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.login.AddressDTO;
import com.example.login.UserDTO;
import com.example.mylaundry.R;
import com.example.mylaundry.UserHome;

import static android.content.ContentValues.TAG;

/**
 * Created by shilk on 1/27/2018.
 */

public class ServiceAPI extends Activity{

    SQLiteDatabase db = null;
    public static final String SALT = "KEEPPROTECTED";

    public ServiceAPI() {
        this.db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
    }

    public void onStartUpActivity(){
        db.execSQL("create table if not exists USER(USER_ID INTEGER NOT NULL PRIMARY KEY, FIRST_NAME varchar NOT NULL, LAST_NAME varchar NOT NULL, EMAIL_ID varchar, PHONE_NO varchar NOT NULL, USER_TYPE varchar NOT NULL, CREATE_DATE varchar NOT NULL, PASSWORD varchar NOT NULL)");
        db.execSQL("create table if not exists ADDRESS(USER_ID INTEGER NOT NULL, ADDRESS_1 varchar, ADDRESS_2 varchar, ADDRESS_3 varchar, PIN_CODE varchar NOT NULL)");
    }

    public Integer getMaxUserId() {
        Integer userId = null;
        try {
            Cursor c = db.rawQuery("SELECT MAX(USER_ID)+1 FROM USER ",null);
            c.moveToFirst();
            userId = c.getInt(0);
            c.close();
            if(!CommonUtil.isNull(userId) && !(userId < 0)) {
                return userId;
            }
        } catch (Exception e){
            Log.e(TAG, "getMaxUserId: ", e);
        } finally {
            db.close();
        }
        return userId;
    }

    public Boolean persistUserTable(UserDTO userDTO){
        Boolean returnFlag = false;
        try{
            boolean errorFlag= true;
            StringBuffer sql = new StringBuffer("INSERT INTO USER VALUES(");
            StringBuffer addressSQL = new StringBuffer("INSERT INTO ADDRESS VALUES(");

            if(CommonUtil.isNull(userDTO.getUserId())){
                sql.append(userDTO.getUserId());
            }else {errorFlag = false;}

            if(CommonUtil.isNullOrEmpty(userDTO.getFirstName())){
                sql.append(",'" + userDTO.getFirstName().trim() + "'");
            }else {errorFlag = false;}

            if(CommonUtil.isNullOrEmpty(userDTO.getLastName())){
                sql.append(",'"+userDTO.getLastName().trim()+"'");
            }else {errorFlag = false;}

            if(CommonUtil.isNullOrEmpty(userDTO.getEmailId())){
                sql.append(",'"+userDTO.getEmailId().trim()+"'");
            }else{
                sql.append(", NULL");
            }

            if(CommonUtil.isNull(userDTO.getPhoneNumber())){
                sql.append(",'" + userDTO.getPhoneNumber()+"'");
            }else {errorFlag = false;}

            if(CommonUtil.isNull(userDTO.getUserType())){
                sql.append(",'" + userDTO.getUserType()+"'");
            }else {errorFlag = false;}

            sql.append(",'" + CommonUtil.getTodaysDate("yyyy-MM-dd HH:mm:SS")+"'");
            sql.append(",'" + CommonUtil.generateHashCode(SALT + userDTO.getPassword())+"'");
            sql.append(")");

            if(!CommonUtil.isNull(userDTO.getAddressDTO())){
                AddressDTO addressDTO = userDTO.getAddressDTO();
                if(CommonUtil.isNull(addressDTO.getUserId())){
                    addressSQL.append(addressDTO.getUserId());
                }else {errorFlag = false;}

                if(CommonUtil.isNullOrEmpty(addressDTO.getAddress1())){
                    addressSQL.append(",'" + addressDTO.getAddress1() + "'");
                }else {
                    addressSQL.append(", NULL");
                }

                if(CommonUtil.isNullOrEmpty(addressDTO.getAddress2())){
                    addressSQL.append(",'" + addressDTO.getAddress2() + "'");
                }else {
                    addressSQL.append(", NULL");
                }
                addressSQL.append(", NULL");

                if(CommonUtil.isNull(addressDTO.getPinCode())){
                    addressSQL.append(",'" + addressDTO.getPinCode() + "'");
                }else { errorFlag=false;}
                addressSQL.append(")");
            }else{errorFlag=false;}

            if(errorFlag){
                db.execSQL(sql.toString());
                db.execSQL(addressSQL.toString());
                returnFlag = true;
            }
        }catch (Exception e) {
            Log.e(TAG, "persistUserTable: ", e);
        } finally {
            db.close();
        }
        return returnFlag;
    }

    public UserDTO validateCredentials(String userName, String password){
        UserDTO userDTO = null;
        try{
            Cursor c=db.rawQuery("select * from USER us join ADDRESS add on (add.USER_ID = us.USER_ID)  where us.PHONE_NO='"+userName+"' and us.PASSWORD='"+CommonUtil.generateHashCode(SALT + password)+"'",null);
            c.moveToFirst();
            if(c.getCount()>0) {
                userDTO = new UserDTO();
                userDTO.setUserId(c.getInt(0));
                userDTO.setFirstName(c.getString(1));
                userDTO.setLastName(c.getString(2));
                userDTO.setEmailId(c.getString(3));
                userDTO.setPhoneNumber(c.getInt(4));
                userDTO.setUserType(c.getString(5).charAt(0));
                userDTO.setPassword(c.getString(6));

                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setUserId(c.getInt(7));
                addressDTO.setAddress1(c.getString(8));
                addressDTO.setAddress2(c.getString(9));
                addressDTO.setPinCode(c.getInt(10));

                userDTO.setAddressDTO(addressDTO);
            }
        }catch (Exception e){
            Log.e(TAG, "validateCredentials: ", e);
        } finally {
            db.close();
        }
        return userDTO;
    }
}
