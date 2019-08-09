
package com.berthoud.p7.webserviceapp.model.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
public class Customer extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private char sex;

    @Column(nullable = false)
    private LocalDate dateExpirationMembership;
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private Address address;

    @OrderBy("dateEnd ASC ")
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Loan> loans;

    public Customer(String firstName, String surname, char sex, LocalDate dateExpirationMembership, String phone, String email, String password, Address address, Set<Loan> loans) {
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

    public Customer() {

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

    public Set<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }
}
