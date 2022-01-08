package ru.rsatu.resources;

import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import io.quarkus.security.Authenticated;
import io.vertx.core.json.JsonObject;
import ru.rsatu.pojo.Items;
import ru.rsatu.pojo.ItemsDetails;
import ru.rsatu.service.ItemService;

@Authenticated

@Path("/items")
public class ItemResources {

    @Inject
    ItemService os;
    
    @RolesAllowed({"watchAll"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getItems")
    public Response getItems(@QueryParam("page") int page){
        JsonObject json = new JsonObject();
        json.put("page", page);
        json.put("per_page", 10);
        int c = os.countItems();
        json.put("total", c);
        json.put("total_pages", (int)Math.ceil(c / 10.0));
        json.put("data", os.getItems(page));
        return Response.ok(json).build();
    }
    @RolesAllowed({"watchAll"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllItems")
    public Response getItems(){
        return Response.ok(os.getItems()).build();
    }
    @RolesAllowed({"editItem"})
    @PUT
    @Path("/newItem")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void newItem(Items item) {   	 
    	System.out.println("Попытка добавить итем: \n"+item.toString());
    	for (ItemsDetails detail : item.itemDetails) {                    
            System.out.println("Внутри цикла: \n"+detail.toString());
            detail.item = item; 
    	} 
    os.insertItem(item);
    }
    @RolesAllowed({"watchAll"})
    @GET
    @Path("/getAtomics")
    @Transactional
   // @Consumes(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)	
    public Map<Items, Integer> getAtomics(@QueryParam("itemID") Long id ) {
       // System.out.println("Попытка добавить деталь: \n"+item.toString()); 
        return os.getAtomicsFromItem(os.getItemById(id),1);
    	
    }
    @RolesAllowed({"watchAll"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getItemById")
    public Response getItemById(@QueryParam("itemID") Long itemID){
        return Response.ok(os.getItemById(itemID)).build();
    }
    @RolesAllowed({"watchAll"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStructure")
    public Response getStructure(@QueryParam("itemID") Long id){
        return Response.ok(os.getStructure(os.getItemById(id), 1)).build();
    }
    @RolesAllowed({"editItems"})
    @POST
    @Path("/updateItem")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateItem(Items item) { 
    	System.out.println("Попытка обновить заказ: \n"+item.toString());
    	for (ItemsDetails detail : item.itemDetails) {
            System.out.println("Внутри цикла: \n"+detail.toString());
            detail.item = item;
            if (detail.id==0) {            	
            	addDetail(detail, item.id);
            } else {
        os.updateDetail(detail);  }               
    	} 
    	os.updateItem(item);
    }
    
    @RolesAllowed({"editItems"})
    @PUT
    @Path("/addDetail/{itemID}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void addDetail(ItemsDetails detail, @PathParam Long itemID) {
        Items item = os.getItemById(itemID);
        if (item == null) {
            return;
        }
        ItemsDetails detail1 = new ItemsDetails();
        detail1.setItemID(detail.getItemID());
        detail1.setQty(detail.getQty());
        
        detail1.item = item;        
        detail1.persist(); //????
        
        item.itemDetails.add(detail1);       
        item.persist(); //????
        
    }
    
    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getItemById")
    public Response getClientById(@QueryParam("clientID") Long clientID){
        return Response.ok(sr.getClientById(clientID)).build();
    }*/

//    @PUT
//    @Path("/createStructure")
//    @Transactional
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void createStructure(ItemsDetails details) {
//        System.out.println("Попытка добавить структуру: \n"+details.toString());
//        /*Items parent = os.insertItem(details.parent);
//        details.parent = parent;*/
//        List<Items> ch = new ArrayList<>();
//        for (Items item : details.children) {
//            System.out.println("Внутри цикла: \n"+item.toString());
//            //Items child = os.insertItem(item);
//            //ch.add(child);
//        }
//        //details.children = ch;
//        System.out.println("Попытка добавить структуру2: \n"+details.toString());
//        os.createStructure(details);
//    }
}