package com.example.login;


import com.example.commons.CommonUtil;

/**
 * Created by shilk on 1/27/2018.
 */

public class UserDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private Integer phoneNumber;
    private Character userType;
    private String password;
    private AddressDTO addressDTO;
    public UserDTO(){}
    public UserDTO(Integer userId, String firstName, String lastName, String emailId, Integer phoneNumber, Character userType, String password, AddressDTO addressDTO) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.password = password;
        this.addressDTO = addressDTO;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Character getUserType() {
        return userType;
    }

    public void setUserType(Character userType) {
        this.userType = userType;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }


    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return CommonUtil.toString(this);
    }
}
