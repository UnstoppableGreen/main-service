package ru.rsatu.resources;

import io.vertx.core.json.JsonObject;
import ru.rsatu.service.OrderService;
import ru.rsatu.pojo.Clients;
import ru.rsatu.pojo.Orders;

import java.sql.Date;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getClientById(@QueryParam("orderID") Long orderID){
        return Response.ok(os.getOrderById(orderID)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertOrder")
    public Response insertOrder(@QueryParam("clientID") Long clientID){
        return Response.ok(os.insertOrder(os.createOrder(clientID))).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertOrder")
    
    public Response insertOrder(Orders order){
        return Response.ok(os.insertOrder(order)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateClient")
    public Response updateClient(Orders order){
        return Response.ok(os.updateOrder(order)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteOrder")
    public Response deleteOrder(@QueryParam("orderID") Long orderID){
        os.deleteOrder(orderID);
        return Response.ok().build();
    }
}
