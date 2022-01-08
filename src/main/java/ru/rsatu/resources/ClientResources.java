package ru.rsatu.resources;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.security.Authenticated;
import io.vertx.core.json.JsonObject;
import ru.rsatu.pojo.Clients;
import ru.rsatu.service.ClientService;

@Authenticated

@Path("/clients")
public class ClientResources {
    @Inject
    ClientService sr;
    
    @RolesAllowed({"departmentAdmin","chief","logist","manager"})
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
    @RolesAllowed({"departmentAdmin","chief","logist","manager"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getClients")
    public Response getClients(){
        return Response.ok(sr.getClients()).build();
    }
    @RolesAllowed({"departmentAdmin","chief","logist","manager"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getClientById")
    public Response getClientById(@QueryParam("clientID") Long clientID){
        return Response.ok(sr.getClientById(clientID)).build();
    }
    @RolesAllowed({"departmentAdmin","chief","manager"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertClient")
    public Response insertClient(Clients cl){
        return Response.ok(sr.insertClient(cl)).build();
    }
    @RolesAllowed({"departmentAdmin","chief","manager"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateClient")
    public Response updateClient(Clients cl){
        System.out.println("client " + cl.getName());
        return Response.ok(sr.updateClient(cl)).build();
    }
    @RolesAllowed({"departmentAdmin","chief"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteClient")
    public Response deleteClient(@QueryParam("clientID") Long id){
        sr.deleteClient(id);
        return Response.ok().build();
    }
}
