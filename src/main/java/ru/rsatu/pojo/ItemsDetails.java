package ru.rsatu.pojo;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "itemsDetails")
public class ItemsDetails implements Serializable {
	@Id
	@SequenceGenerator(name = "itemdetSeq", sequenceName = "itemdet_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "itemdetSeq")
	private Long itemdetID;

	@ManyToOne(targetEntity = Items.class)
	@JoinColumn(name = "parent")
	public Items parent;

	@ManyToOne(targetEntity = Items.class)
	@JoinColumn(name = "children")
	//public Items children;
	public List<Items> children;

    private Integer qty;

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "DETAIL{" +
				"parentID='" + parent + '\'' +
				"childItem='" + children + '\'' +
				", qty='" + qty + '\'' +
				'}';
	}
	
}
