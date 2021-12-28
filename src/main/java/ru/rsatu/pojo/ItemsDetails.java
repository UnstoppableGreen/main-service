package ru.rsatu.pojo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class ItemsDetails extends PanacheEntity {
	
    private Long itemPartID;
    private Integer qty;
    
    @ManyToOne
    @JsonIgnore
    public Items item;

	public Long getItemPartID() {
		return itemPartID;
	}

	public void setItemPartID(Long itemPartID) {
		this.itemPartID = itemPartID;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	
	
}
