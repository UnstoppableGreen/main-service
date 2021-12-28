package ru.rsatu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Items extends PanacheEntity {	
    private String name;
    
    private Long defaultSupplierId;
    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER) 
    public List<ItemsDetails> itemDetails;
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDefaultSupplierId() {
		return defaultSupplierId;
	}
	public void setDefaultSupplierId(Long defaultSupplierId) {
		this.defaultSupplierId = defaultSupplierId;
	}
	
	
}
