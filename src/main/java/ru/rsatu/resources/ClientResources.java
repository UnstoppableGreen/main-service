package ru.rsatu.resources;

import io.vertx.core.json.JsonObject;
import ru.rsatu.service.ClientService;
import ru.rsatu.pojo.Clients;
import java.sql.Date;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/client")
public class ClientResources {
    @Inject
    ClientService sr;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getClientsPage")
    public Response getClientsPage(@QueryParam("page") int page){
        JsonObject json = new JsonObject();
        json.put("page", page);
        json.put("per_page", 4);
        int c = sr.getCountClients();
        json.put("total", c);
        json.put("total_pages", (int)Math.ceil(c / 4.0));
        json.put("data", sr.getClientsPage(page));
        return Response.ok(json).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getClients")
    public Response getClients(){
        return Response.ok(sr.getClients()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getClientById")
    public Response getClientById(@QueryParam("clientID") Long clientID){
        return Response.ok(sr.getClientById(clientID)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertClient1")
    public Response insertClient1(@QueryParam("name") String name,@QueryParam("contacts") String contacts,@QueryParam("address") String address,@QueryParam("date") Date date){
        return Response.ok(sr.insertClient1(sr.createClient(name, contacts, address,date))).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertClient")
    public Response insertClient(Clients cl){
        return Response.ok(sr.insertClient(cl)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateClient")
    public Response updateClient(Clients cl){
        System.out.println("clint " + cl.getName());
        return Response.ok(sr.updateClient(cl)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteClient")
    public Response deleteClient(@QueryParam("clientID") Long id){
        sr.deleteClient(id);
        return Response.ok().build();
    }
}
