package ru.rsatu.resources;

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
    @Path("/getClients")
    public Response getClients(){
        return Response.ok(sr.getClients()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getClientById")
    public Response getClientById(@QueryParam("clientID") Long id){
        return Response.ok(sr.getClientById(id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertClient")
    public Response insertClient(@QueryParam("name") String name,@QueryParam("contacts") String contacts,@QueryParam("date") Date date){      	    	
        return Response.ok(sr.insertClient(sr.createClient(name, contacts, date))).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateClient")
    public Response updateClient(Clients cl){
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
