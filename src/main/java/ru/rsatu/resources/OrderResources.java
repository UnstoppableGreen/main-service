package ru.rsatu.resources;

import io.vertx.core.json.JsonObject;
import ru.rsatu.service.OrderService;
import ru.rsatu.pojo.Clients;
import ru.rsatu.pojo.Orders;
import ru.rsatu.pojo.OrdersDetails;

import java.sql.Date;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
    public Response getOrderById(@QueryParam("orderID") Long orderID){
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @PUT
    @Path("addDetail")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addDetail(@QueryParam("orderID") Long orderID, @QueryParam("itemID") Long itemID, @QueryParam("qty") Integer qty, @QueryParam("comments") String comments) {
        Orders order = os.getOrderById(orderID);
        if (order == null) {
            return;
        }

        OrdersDetails OrderDetail = new OrdersDetails();
        OrderDetail.setItemID(itemID);
        OrderDetail.setQty(qty);
        OrderDetail.setComments(comments);
        
        os.insertDetail(OrderDetail);
        OrderDetail.persist(); //????
        
        order.orderDetails.add(OrderDetail);       
        order.persist(); //????
        
    }

 /*   @DELETE
    @Path("book/{id}")
    @Transactional
    public void deleteBook(@PathParam Long id) {
        Book book = Book.findById(id);
        if (book != null) {
            book.author.books.remove(book);
            book.delete();
        }
    }

    @PUT
    @Path("author")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addAuthor(@FormParam String firstName, @FormParam String lastName) {
        Author author = new Author();
        author.firstName = firstName;
        author.lastName = lastName;
        author.persist();
    }

    @POST
    @Path("author/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateAuthor(@PathParam Long id, @FormParam String firstName, @FormParam String lastName) {
        Author author = Author.findById(id);
        if (author == null) {
            return;
        }
        author.firstName = firstName;
        author.lastName = lastName;
        author.persist();
    }

    @DELETE
    @Path("author/{id}")
    @Transactional
    public void deleteAuthor(@PathParam Long id) {
        Author author = Author.findById(id);
        if (author != null) {
            author.delete();
        }
    }
    
    
    
    
    
    
    
    
    
    
    */
}
