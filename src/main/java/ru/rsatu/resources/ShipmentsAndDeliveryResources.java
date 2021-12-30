package ru.rsatu.resources;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import io.vertx.core.json.JsonObject;
import ru.rsatu.pojo.Requests;
import ru.rsatu.pojo.Shipments;
import ru.rsatu.service.ItemService;
import ru.rsatu.service.OrderService;
import ru.rsatu.service.ShipmentsAndDeliveryService;

@Path("/ShipmentsAndDeliveryResources")
public class ShipmentsAndDeliveryResources {
    @Inject
    ShipmentsAndDeliveryService sads;
    @Inject
    ItemService is;
    @Inject
    OrderService os;
    
    
    @PUT
    @Path("/newShipment")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void newShipment(Shipments shipment) {
	    System.out.println("Попытка добавить отгрузку: \n"+shipment.toString());   	    	  
	    sads.insertShipment(shipment);
    }
    
    @PUT
    @Path("/updateShipment")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateShipment(Shipments shipment) { 
    	System.out.println("Попытка обновить отгрузку: \n"+shipment.toString());
    	sads.updateShipment(shipment);
    }
    
    @GET
    @Path("/getShipmentDetails")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShipmentDetails(@QueryParam("shipmentID") Long shipmentID) {   	
    	Shipments shipment = new Shipments();
    	shipment=sads.getShipmentById(shipmentID);
    	JsonObject json = new JsonObject();
    	json.put("shipmentID", shipment.id);
    	json.put("orderID", shipment.getOrderID());
    	json.put("clientID", os.getOrderById(shipment.getOrderID()).getClientID());
    	json.put("shipmentDate", shipment.getShipmentDate());
    	json.put("estimateDeliveryDate", shipment.getEstimateDeliveryDate());
    	json.put("deliveryDate", shipment.getDeliveryDate());
    	json.put("orderDetails", os.getOrderDetails(sads.getShipmentById(shipmentID).getOrderID()));
    	return Response.ok(json).build();
    }
    
    @GET
    @Path("/getShipments")
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getShipments(@QueryParam("page") int page){
        JsonObject json = new JsonObject();
        json.put("page", page);
        json.put("per_page", 10);
        int c = sads.countShipments();
        json.put("total", c);
        json.put("total_pages", (int)Math.ceil(c / 4.0));
        json.put("data", sads.getShipments(page));
        return Response.ok(json).build();
    }
    
    
    
    @PUT
    @Path("/newRequest")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void newRequest(Requests request) {
	    System.out.println("Попытка добавить запрос к поставщику: \n"+request.toString());   	    	  
	    sads.insertRequest(request);
    }
    
    @PUT
    @Path("/updateRequest")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRequest(Requests request) { 
    	System.out.println("Попытка обновить отгрузку: \n"+request.toString());
    	sads.updateRequest(request);
    }
    
    @GET
    @Path("/getRequestDetails")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequestDetails(@QueryParam("requestID") Long requestID) {   	
    	Requests request = new Requests();
    	request=sads.getRequestById(requestID);
    	JsonObject json = new JsonObject();
    	json.put("requestID", request.id);
    	json.put("orderID", request.getOrderID());
    	json.put("itemID", request.getItemID());
    	json.put("creationDate", request.getCreationDate());
    	json.put("estimateDeliveryDate", request.getEstimateDeliveryDate());
    	json.put("deliveryDate", request.getDeliveryDate());
	
    	json.put("itemDetails", is.getAtomicsFromItem(is.getItemById(request.getItemID()),request.getQty()));
    	
    	return Response.ok(json).build();
    }
    
    
}
