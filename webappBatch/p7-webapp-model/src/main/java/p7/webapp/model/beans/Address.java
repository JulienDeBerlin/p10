package p7.webapp.model.beans;

import java.util.Set;

public class Address {

    private int id;

    private String additionalAddressField1;
    private String additionalAddressField2;

    private String road;

    private int houseNumber;

    private String zipCode;

    private String city;

    private Set<Customer> customers;

    private Librairy librairy;

    public Address(String additionalAddressField1, String additionalAddressField2, String road, int houseNumber, String zipCode, String city, Set<Customer> customers, Librairy librairy) {
        this.additionalAddressField1 = additionalAddressField1;
        this.additionalAddressField2 = additionalAddressField2;
        this.road = road;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.customers = customers;
        this.librairy = librairy;
    }

    public Address(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdditionalAddressField1() {
        return additionalAddressField1;
    }

    public void setAdditionalAddressField1(String additionalAddressField1) {
        this.additionalAddressField1 = additionalAddressField1;
    }

    public String getAdditionalAddressField2() {
        return additionalAddressField2;
    }

    public void setAdditionalAddressField2(String additionalAddressField2) {
        this.additionalAddressField2 = additionalAddressField2;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Librairy getLibrairy() {
        return librairy;
    }

    public void setLibrairy(Librairy librairy) {
        this.librairy = librairy;
    }
}
