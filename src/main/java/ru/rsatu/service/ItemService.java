package ru.rsatu.service;

import ru.rsatu.pojo.Items;
import ru.rsatu.pojo.ItemsDetails;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ItemService {
    @Inject
    EntityManager em;

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

    @Transactional
    public ItemsDetails createStructure(ItemsDetails details) {
        em.merge(details);
        em.flush();
        em.clear();
        return details;
    }
}
