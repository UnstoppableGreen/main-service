package ru.rsatu.resources;

import io.quarkus.security.Authenticated;
import io.vertx.core.json.JsonObject;
import ru.rsatu.pojo.Suppliers;
import ru.rsatu.service.SuppliersService;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Authenticated
@Path("/suppliers")
public class SuppliersResources {
    @Inject
    SuppliersService ss;
    
    @RolesAllowed({"departmentAdmin","chief","logist","manager"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSuppliers")
    public Response getSuppliers(@QueryParam("page") int page){
        JsonObject json = new JsonObject();
        json.put("page", page);
        json.put("per_page", 10);
        int c = ss.countSuppliers();
        json.put("total", c);
        json.put("total_pages", (int)Math.ceil(c / 10.0));
        json.put("data", ss.getSuppliers(page));
        return Response.ok(json).build();
    }
    @RolesAllowed({"departmentAdmin","chief","logist","manager"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllSuppliers")
    public Response getAllSuppliers(){
        return Response.ok(ss.getAllSuppliers()).build();
    }
    @RolesAllowed({"departmentAdmin","chief","logist","manager"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSupplierById")
    public Response getSupplierById(@QueryParam("supplierID") Long id){
        return Response.ok(ss.getSupplierById(id)).build();
    }
    @RolesAllowed({"departmentAdmin","chief","logist"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createSupplier")
    public Response insertSupplier(Suppliers s){
        return Response.ok(ss.insertSupplier(s)).build();
    }
    @RolesAllowed({"departmentAdmin","chief","logist"})
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateSupplier")
    public Response updateSupplier(Suppliers s){
        return Response.ok(ss.updateSupplier(s)).build();
    }
    @RolesAllowed({"departmentAdmin","chief","logist"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteSupplier")
    public Response deleteSupplier(@QueryParam("supplierID") Long id){
        ss.deleteSupplier(ss.getSupplierById(id));
        return Response.ok().build();
    }
}
