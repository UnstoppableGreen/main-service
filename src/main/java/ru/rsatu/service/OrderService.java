package ru.rsatu.service;

import ru.rsatu.pojo.Clients;
import ru.rsatu.pojo.Orders;
import ru.rsatu.pojo.OrdersDetails;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class OrderService {
    @Inject
    EntityManager em;

    @Transactional
    public Orders createOrder(Long clientID) {
    	Orders order = new Orders();
    	order.setClientID(clientID);
    	order.setStatusID(1); //надо ставить дефолтный статус
    	
        Date dateNow = new Date();      
    	order.setCreationDate(dateNow);
    	order.setLastUpdateOn(dateNow);
        return order;
    }

    //вставка данных
    @Transactional
    public Orders insertOrder(Orders order) {
    	em.merge(order);
        em.flush();
        em.clear();
        return order;
    }
    
    //вставка данных
   /* @Transactional
    public OrdersDetails insertDetail(OrdersDetails orderDetail) {
        em.merge(orderDetail);
        em.flush();
        return orderDetail;
    }*/
    

    //обновление данных
    @Transactional
    public Orders updateOrder(Orders order) {
    	Date dateNow = new Date();
    	order.setLastUpdateOn(dateNow);
        em.merge(order);
        em.flush();
        return order;
    }
    //обновление данных
    @Transactional
    public OrdersDetails updateDetail(OrdersDetails detail) {
        em.merge(detail);
        em.flush();
        return detail;
    }

    //удаление данных
    @Transactional
    public void deleteOrder(Long orderID) {
       Orders order = this.getOrderById(orderID);
	   Date dateNow = new Date();
	   order.setLastUpdateOn(dateNow);
	   order.setStatusID(100);
        //em.remove(order); //реально удалять наверно не надо, выставим статус "удалён"
       // em.flush(); 
    }

    public List<Orders> getOrders() {
        return em.createQuery(" select c from Orders c ", Orders.class).getResultList();
    }

    public int countOrders() {
        Number ordersQTY = (Number) em.createQuery(" select count(orderID) from Orders ").getResultList().get(0);
        return ordersQTY.intValue() ;
    }

    public List<Orders> getOrders(int page) {
        Query query = em.createQuery(" select c from Orders c where statusID != 100 ");
        query.setFirstResult((page-1)*4);
        query.setMaxResults(4);
        List<Orders> listOrders = query.getResultList();
        return listOrders;
    }
    
    public List<Orders> getDeletedOrders(int page) {
        Query query = em.createQuery(" select c from Orders c where statusID = 100 ");
        query.setFirstResult((page-1)*4);
        query.setMaxResults(4);
        List<Orders> listOrders = query.getResultList();
        return listOrders;
    }
    
    public Orders getOrderById(Long orderID) {
        Orders order = em.find(Orders.class, orderID);
        return order;
    }
    
    public OrdersDetails getDetailById(Long detailID) {
        OrdersDetails detail = em.find(OrdersDetails.class, detailID);
        return detail;
    }
}
