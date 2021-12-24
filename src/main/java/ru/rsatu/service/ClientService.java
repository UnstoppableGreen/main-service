package ru.rsatu.service;

import ru.rsatu.pojo.Clients;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@ApplicationScoped
public class ClientService {
    @Inject
    EntityManager em;

    @Transactional
    public Clients createClient(String name, String contacts, Date date) {
        Clients cl = new Clients();
        cl.setName(name);
        cl.setContacts(contacts);
        cl.setDate(date);
        return cl;
    }

    //вставка данных
    @Transactional
    public Clients insertClient(Clients cl) {
       // em.persist(cl);
        em.merge(cl);
        em.flush();
        return cl;
    }

    //обновление данных
    @Transactional
    public Clients updateClient(Clients cl) {
        em.merge(cl);
        em.flush();
        return cl;
    }

    //удаление данных
    @Transactional
    public void deleteClient(Long id) {
    	Clients c = this.getClientById(id);
       // Clients c = getClientById(clients.getclientID());
        em.remove(c);
        em.flush();
    }

    public List<Clients> getClients() {
        return em.createQuery(" select c from Clients c ", Clients.class).getResultList();
    }

    public Clients getClientById(Long id) {
        Clients cl = em.find(Clients.class, id);
        return cl;
    }
}
