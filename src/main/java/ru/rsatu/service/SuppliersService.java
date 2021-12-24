package ru.rsatu.service;

import ru.rsatu.pojo.Suppliers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SuppliersService {
    @Inject
    EntityManager em;

    @Transactional
    public void createSupplier() {
        Suppliers sup = new Suppliers();
        sup.setName("Name");
        sup.setContacts("Discription");
        em.persist(sup);
    }

    //вставка данных
    @Transactional
    public Suppliers insertSupplier(Suppliers s) {
        em.persist(s);
        em.flush();
        return s;
    }

    //обновление данных
    @Transactional
    public Suppliers updateSupplier(Suppliers s) {
        em.merge(s);
        em.flush();
        return s;
    }

    //удаление данных
    @Transactional
    public void deleteSupplier(Suppliers sup) {
        Suppliers s = getSupplierById(sup.getId());
        em.remove(s);
        em.flush();
    }

    public Suppliers getSupplierById(Long id) {
        Suppliers s = em.find(Suppliers.class, id);
        return s;
    }

    public List<Suppliers> getSuppliers() {
        return em.createQuery(" select s from Suppliers s ", Suppliers.class).getResultList();
    }
}
