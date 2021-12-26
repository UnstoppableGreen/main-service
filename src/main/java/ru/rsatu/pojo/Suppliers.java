package ru.rsatu.pojo;

import javax.persistence.*;

@Entity
@Table(name="suppliers")
public class Suppliers {
    @Id
    @SequenceGenerator(name = "suppliersSeq", sequenceName = "suppliers_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "suppliersSeq")
    private Long id;
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
