package ru.rsatu.resources;

import io.vertx.core.json.JsonObject;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import ru.rsatu.pojo.Items;
import ru.rsatu.pojo.ItemsDetails;
import ru.rsatu.pojo.OrdersDetails;
import ru.rsatu.service.ItemService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/items")
public class ItemResources {

    @Inject
    ItemService os;

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

    @PUT
    @Path("/createItem")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Items createItem(Items item) {
        System.out.println("Попытка добавить деталь: \n"+item.toString());
        return os.insertItem(item);
    }

    @PUT
    @Path("/createStructure")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void createStructure(ItemsDetails details) {
        System.out.println("Попытка добавить структуру: \n"+details.toString());
        /*Items parent = os.insertItem(details.parent);
        details.parent = parent;*/
        List<Items> ch = new ArrayList<>();
        for (Items item : details.children) {
            System.out.println("Внутри цикла: \n"+item.toString());
            //Items child = os.insertItem(item);
            //ch.add(child);
        }
        //details.children = ch;
        System.out.println("Попытка добавить структуру2: \n"+details.toString());
        os.createStructure(details);
    }
}
