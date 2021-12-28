package ru.rsatu.resources;

import io.vertx.core.json.JsonObject;
import ru.rsatu.service.OrderService;
import ru.rsatu.pojo.Clients;
import ru.rsatu.pojo.Orders;
import ru.rsatu.pojo.OrdersDetails;

import java.sql.Date;
import javax.inject.Inject;
import javax.persistence.PostUpdate;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
@Path("/orders")
public class OrderResources {
    @Inject
    OrderService os;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getOrders")
    public Response getOrders(@QueryParam("page") int page){
        JsonObject json = new JsonObject();
        json.put("page", page);
        json.put("per_page", 10);
        int c = os.countOrders();
        json.put("total", c);
        json.put("total_pages", (int)Math.ceil(c / 4.0));
        json.put("data", os.getOrders(page));
        return Response.ok(json).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getOrders")
    public Response getOrders(){
        return Response.ok(os.getOrders()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getOrderById")
    public Response getOrderById(@QueryParam("orderID") Long orderID){
        return Response.ok(os.getOrderById(orderID)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteOrder")
    public Response deleteOrder(@QueryParam("orderID") Long orderID){
        os.deleteOrder(orderID);
        return Response.ok().build();
    }
        
    @PUT
    @Path("/newOrder")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void newOrder(Orders order) {
    	System.out.println("Попытка добавить заказ: \n"+order.toString());
    	    	 
    	for (OrdersDetails detail : order.orderDetails) {                    
            System.out.println("Внутри цикла: \n"+detail.toString());
            detail.order = order; 
    	} 
    os.insertOrder(order);
    }
   
    @PUT
    @Path("/addDetail/{orderID}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void addDetail(OrdersDetails detail, @PathParam Long orderID) {
        Orders order = os.getOrderById(orderID);
        if (order == null) {
            return;
        }
        OrdersDetails OrderDetail = new OrdersDetails();
        OrderDetail.setItemID(detail.getItemID());
        OrderDetail.setQty(detail.getQty());
        OrderDetail.setComments(detail.getComments());
        
        OrderDetail.order = order;        
        OrderDetail.persist(); //????
        
        order.orderDetails.add(OrderDetail);       
        order.persist(); //????
        
    }

    @DELETE
    @Path("/deleteDetail")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void deleteDetail(@QueryParam("detailID") Long detailID) {
    	OrdersDetails detail = os.getDetailById(detailID);
    	System.out.println("Удаляемый детэйл: \n"+detail.toString());
        if (detail != null) {
        	detail.order.orderDetails.remove(detail);
        	detail.delete();
        }
    }

    
    @PUT
    @Path("/updateOrder")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateOrder1(Orders order) { 
    	System.out.println("Попытка обновить заказ: \n"+order.toString());
    	for (OrdersDetails detail : order.orderDetails) {
            System.out.println("Внутри цикла: \n"+detail.toString());
            detail.order = order;
            if (detail.id==0) {            	
            	addDetail(detail, order.id);
            } else {
        os.updateDetail(detail);  }               
    	} 
    	os.updateOrder(order);
    }
}
