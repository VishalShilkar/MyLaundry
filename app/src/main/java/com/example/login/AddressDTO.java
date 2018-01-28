package com.example.login;

/**
 * Created by shilk on 1/27/2018.
 */

public class AddressDTO {

    private Integer userId;
    private String address1;
    private String address2;
    private Integer pinCode;
    public AddressDTO(){}
    public AddressDTO(Integer userrId, String address1, String address2, Integer pinCode) {
        this.userId = userrId;
        this.address1 = address1;
        this.address2 = address2;
        this.pinCode = pinCode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
