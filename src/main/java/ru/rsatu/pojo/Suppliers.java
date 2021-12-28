package ru.rsatu.pojo;

import javax.persistence.*;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Suppliers extends PanacheEntity {

    private String name;
    private String contacts;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
