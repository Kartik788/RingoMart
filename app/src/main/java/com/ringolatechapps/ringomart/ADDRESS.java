package com.ringolatechapps.ringomart;

public class ADDRESS {
    private String AddressLine_1;
    private String AddressLine_2;
    private String City;
    private String FullName;
    private String Landmark;
    private String Number;
    private String PinCode;
    private String State;

    public ADDRESS(String addressLine_1, String addressLine_2, String city, String fullName, String landmark, String number, String pinCode, String state) {
        AddressLine_1 = addressLine_1;
        AddressLine_2 = addressLine_2;
        City = city;
        FullName = fullName;
        Landmark = landmark;
        Number = number;
        PinCode = pinCode;
        State = state;
    }

    public String getAddressLine_1() {
        return AddressLine_1;
    }

    public String getAddressLine_2() {
        return AddressLine_2;
    }

    public String getCity() {
        return City;
    }

    public String getFullName() {
        return FullName;
    }

    public String getLandmark() {
        return Landmark;
    }

    public String getNumber() {
        return Number;
    }

    public String getPinCode() {
        return PinCode;
    }

    public String getState() {
        return State;
    }
}
