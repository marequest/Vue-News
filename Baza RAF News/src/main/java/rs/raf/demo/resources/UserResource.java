package rs.raf.demo.resources;

import rs.raf.demo.entities.User;
import rs.raf.demo.entities.helper.UserHelper;
import rs.raf.demo.requests.LoginRequest;
import rs.raf.demo.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.userService.allUsers()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User create(@Valid User user) {
        return this.userService.addUser(user);
    }

    @PUT
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public void put(@Valid UserHelper user) {
        this.userService.putUser(user);
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@PathParam("username") String username) {
        return this.userService.findUser(username);
    }

    @DELETE
    @Path("/{username}")
    public void delete(@PathParam("username") String username) {
        this.userService.deleteUser(username);
    }



    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }
}
