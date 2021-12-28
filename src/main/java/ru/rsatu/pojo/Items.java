package ru.rsatu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "items")
public class Items implements Serializable {

	@Id
	@SequenceGenerator(name = "itemSeq", sequenceName = "item_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "itemSeq")
	private Long itemID;
	private String name;
    private Long defaultSupplierId;

	/*@OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<ItemsDetails> itemDetails;*/

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<ItemsDetails> parent = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "children")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	public List<ItemsDetails> children;
    
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

	@Override
	public String toString() {
		return "Item{" +
				", name='" + name + '\'' +
				", defaultSupplierId=" + defaultSupplierId +
				'}';
	}
}
