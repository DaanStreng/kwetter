package Controllers;

import Models.ApiKey;
import Models.Profile;
import Models.User;
import Services.ApiKeyService;
import Services.ProfileService;
import Services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Created by Daan on 22-Mar-17.
 */

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class UserController extends Controller {
    @Inject
    private UserService userService;

    @Inject private ApiKeyService apiService;
    @Inject private ProfileService profileService;

    @GET
    public Response getAll()
    {
        List<User> users;
        try
        {
            users = userService.getAll();
        } catch (Exception e)
        {
            return Response.serverError().build();
        }
        if (users == null) return Response.noContent().build();
        return Response.ok().entity(users).build();
    }

    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("username") String username,@FormParam("password") String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.create(user);
        Profile profile = new Profile(user);
        profile.setDisplayName(username);
        profileService.create(profile);
        userService.edit(user);
        return Response.ok().entity(user).build();
    }

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("username") String username,@FormParam("password") String password){
        User user = userService.getUserDAO().loginUser(username,password);
        ApiKey key = user.generateKey();
        apiService.create(key);
        return Response.ok().entity(key).build();
    }

    @GET
    @Path("/logout/{apikey}")
    public Response logout(@PathParam("apikey") String apiKey){
        User user = checkBegin(userService,apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        ApiKey key = user.removeKey(apiKey);
        userService.edit(user);
        apiService.remove(key);

        return Response.ok().entity("{\"result\":\"OK\"}").build();
    }

    @POST
    @Path("/checkkey")
    @Consumes("application/x-www-form-urlencoded")
    public Response checkApiKey(@FormParam("apikey") String apikey){

        User result = userService.getUserDAO().checkApiKey(apikey);
        if (result != null)
            return Response.ok().entity(result).build();
        else return Response.ok().entity("{\"result\":\"failed\"}").build();
    }
}
