package ru.rsatu.resources;

import ru.rsatu.pojo.Suppliers;
import ru.rsatu.service.SuppliersService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/supplier")
public class SuppliersResources {
    @Inject
    SuppliersService sr;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSuppliers")
    public Response getSuppliers(){
        return Response.ok(sr.getSuppliers()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSupplierById")
    public Response getSupplierById(@QueryParam("id") Long id){
        return Response.ok(sr.getSupplierById(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertSupplier")
    public Response insertSupplier(Suppliers s){
        return Response.ok(sr.insertSupplier(s)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateSupplier")
    public Response updateSupplier(Suppliers s){
        return Response.ok(sr.updateSupplier(s)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteSupplier")
    public Response deleteSupplier(Suppliers s){
        sr.deleteSupplier(s);
        return Response.ok().build();
    }
}
