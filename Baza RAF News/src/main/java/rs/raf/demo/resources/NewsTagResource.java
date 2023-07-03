package rs.raf.demo.resources;


import rs.raf.demo.entities.NewsTag;
import rs.raf.demo.services.NewsTagService;
import rs.raf.demo.services.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/news_tags")
public class NewsTagResource {

    @Inject
    private NewsTagService tagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.tagService.allNewsTags()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public NewsTag create(@Valid NewsTag tag) {
        return this.tagService.addNewsTag(tag);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public NewsTag find(@PathParam("id") Integer id) {
        return this.tagService.findNewsTag(id);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.tagService.deleteNewsTag(id);
    }

}
