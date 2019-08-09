
package p7.webapp.model.beans;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class Customer{

    private int id;

    private String firstName;

    private String surname;

    private char sex;

    private LocalDate dateExpirationMembership;
    private String phone;
    private String email;

    private String password;

    private Address address;

    private List<Loan> loans;

    public Customer(String firstName, String surname, char sex, LocalDate dateExpirationMembership, String phone, String email, String password, Address address, List<Loan> loans) {
        this.firstName = firstName;
        this.surname = surname;
        this.sex = sex;
        this.dateExpirationMembership = dateExpirationMembership;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.loans = loans;
    }

    public Customer(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public LocalDate getDateExpirationMembership() {
        return dateExpirationMembership;
    }

    public void setDateExpirationMembership(LocalDate dateExpirationMembership) {
        this.dateExpirationMembership = dateExpirationMembership;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, surname, sex, dateExpirationMembership, phone, email, password, address, loans);
    }
}
