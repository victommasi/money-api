package com.money.api.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String street;
    private String number;
    private String complementary;
    private String neiborhood;
    private String zipcode;
    private String city;
    private String state;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplementary() {
        return complementary;
    }

    public void setComplementary(String complementary) {
        this.complementary = complementary;
    }

    public String getNeiborhood() {
        return neiborhood;
    }

    public void setNeiborhood(String neiborhood) {
        this.neiborhood = neiborhood;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
