package ru.rsatu.pojo;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
public class Suppliers extends PanacheEntity {

    private String name;
    private String contacts;
    private String address;

    @OneToMany(mappedBy = "suppliers", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // order из файла OrdersDetails
    //@NotFound(action = NotFoundAction.IGNORE)
    //@OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    public List<Items> items;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
