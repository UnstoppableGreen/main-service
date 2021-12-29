package ru.rsatu.service;

import ru.rsatu.pojo.Clients;
import ru.rsatu.pojo.Items;
import ru.rsatu.pojo.ItemsDetails;
import ru.rsatu.pojo.Orders;
import ru.rsatu.pojo.OrdersDetails;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ItemService {
    @Inject
    EntityManager em;
	private List<Items> atomicItems = new ArrayList<>();

    public List<Items> getItems(int page) {
        Query query = em.createQuery(" select c from Items c ");
        query.setFirstResult((page-1)*10);
        query.setMaxResults(10);
        List<Items> listItems = query.getResultList();
        return listItems;
    }

    public int countItems() {
        Number ordersQTY = (Number) em.createQuery(" select count(Items) from Items ").getResultList().get(0);
        return ordersQTY.intValue() ;
    }

    //вставка данных
    @Transactional
    public Items insertItem(Items item) {
        em.merge(item);
        em.flush();
        em.clear();
        return item;
    }
    public Items getItemById(Long itemID) {
        Items item = em.find(Items.class, itemID);
        return item;
    }

    @Transactional
    public ItemsDetails createStructure(ItemsDetails details) {
        em.merge(details);
        em.flush();
        em.clear();
        return details;
    }
    public List<Items> getAtomicsFromItem (Items parentItem){
    	atomicItems.clear();
    	getAllAtomicItems (parentItem,1);
    	
     return atomicItems; 
    }
    @Transactional
    public List<Items> getAllAtomicItems (Items parentItem, int savedQTY){
    	System.out.println("Проверяемый родитель:"+parentItem.getName());
    	if (!parentItem.itemDetails.isEmpty()) { 
	    	List<ItemsDetails> childItemDetails = new ArrayList<>();    	
		    	for (ItemsDetails itemDetail : parentItem.itemDetails) {
		    		System.out.println("Проверяем потомков:"+itemDetail.toString());
		    		System.out.println("ID потомка: "+itemDetail.getItemID());
		    		Items item = em.find(Items.class, itemDetail.getItemID());
		    		int qty=itemDetail.getQty();
		    		System.out.println("Проверяемый потомок из ITEMS:"+item.toString());
		    		if (item.itemDetails.isEmpty()) {		    			
		    			//atomicItems.add(parentItem);
		    			for(int i = 1; i <= qty*savedQTY; i++) {
		    				System.out.println("Добавляем атомарного в список::"+item.toString());
		    				atomicItems.add(item);
		    				}
		    		}	    		
		    		else {
			    		Query query = em.createQuery(" select c from ItemsDetails c where itemID="+item.id);
			    		childItemDetails = query.getResultList();
			    		List<Items> childItems = new ArrayList<>();			    		
				    		for (ItemsDetails childItem  : childItemDetails) {
				    			System.out.println("Иначе смотрим его детей:"+childItem.toString()+", сохранённое количество: " +childItem.getQty());
				    			getAllAtomicItems(em.find(Items.class, childItem.getItemID()),childItem.getQty());		    			
				    		}		    	 
		    		}
		    	} 
    	}
    	else { 
			for(int i = 1; i <= savedQTY; i++) {
				System.out.println("Добавляем атомарного в список::"+parentItem.toString());
				atomicItems.add(parentItem);				
				}
			}
    return atomicItems;
    }
    
    
}