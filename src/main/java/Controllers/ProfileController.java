package Controllers;

import Models.Profile;
import Models.Tweet;
import Models.User;
import Services.ProfileService;
import Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.mail.iap.ByteArray;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daan on 22-Mar-17.
 */
@Path("/profile/{apikey}")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class ProfileController extends Controller {

    @Inject
    private UserService userService;
    @Inject
    private ProfileService profileService;

    @GET
    @Path("/getprofile")
    public Response getProfile(
            @PathParam("apikey") String apiKey,
            @QueryParam("displayname") String displayName
            ,@QueryParam("profileId") Long profileId){
        User user = checkBegin(userService, apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        Profile profile = null;
        if (displayName!=null)
         profile = profileService.getProfileByProfileName(displayName);
        else if (profileId!=null){
            profile = profileService.getProfileById(profileId);
        }
        else profile = user.getProfile();
        return Response.ok().entity(profile).build();
    }

    @GET
    @Path("/findprofiles")
    public Response findProfiles(@PathParam("apikey") String apiKey,
                                 @QueryParam("query") String query){
        User user = checkBegin(userService, apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        List<Profile> profs = profileService.findProfileByProfileName(query);
        ArrayList<Profile> ret = new ArrayList<Profile>();
        for(Profile p : profs){
            if (p.getId()!=user.getProfile().getId())
                ret.add(p);
        }
        ObjectMapper map = new ObjectMapper();
        try {
            return Response.ok().entity(map.writeValueAsString(ret)).build();
        }
        catch(Exception ex){
            return Response.serverError().build();
        }

    }

    @GET
    @Path("/getfollows")
    public Response getFollowing(@PathParam("apikey") String apiKey){
        User user = checkBegin(userService, apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        List<Profile> ret = user.getProfile().getFollows();
        ObjectMapper map = new ObjectMapper();
        try {
            return Response.ok().entity(map.writeValueAsString(ret)).build();
        }
        catch(Exception ex){
            return Response.serverError().build();
        }

    }

    @POST
    @Path("/updateprofile")
    @Consumes("application/x-www-form-urlencoded")
    public Response updateProfile(
            @PathParam("apikey") String apiKey,
            @QueryParam("name") String name
            ,@QueryParam("surname") String surname
            ,@QueryParam("bio") String bio
            ,@QueryParam("website") String website
            ,@QueryParam("location") String location)
    {
        User user = checkBegin(userService, apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        Profile profile = user.getProfile();
        profile.setName(name);
        profile.setSurname(surname);
        profile.setBio(bio);
        profile.setWebsite(website);
        profile.setLocation(location);
        profileService.edit(profile);
        return Response.ok().entity(profile).build();
    }

    @POST
    @Path("/setimage")
    @Consumes("application/x-www-form-urlencoded")
    public Response saveImage(@FormParam("image") String image, @PathParam("apikey") String apiKey) throws Exception{
        User user = checkBegin(userService, apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        byte[] bytes = image.getBytes();
        Byte[] byteObjects = new Byte[bytes.length];
        int i=0;
        for(byte b: bytes)
            byteObjects[i++] = b;
        user.getProfile().setImage(byteObjects);
        return Response.ok().build();
    }
    @GET
    @Produces("text/plain")
    @Path("/getimage/{profileid}")
    public Response getImage(@PathParam("apikey") String apiKey,@PathParam("profileid") Long profileId){
        User user = checkBegin(userService, apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        Profile pp = profileService.getProfileById(profileId);
        Byte[] bytes = pp.getImage();
        byte[] byteObjects = new byte[bytes.length];
        int i=0;
        for(Byte b: bytes)
            byteObjects[i++] = b;
        String res = new String(byteObjects);
        return Response.ok().entity(res).build();
    }

    @GET
    @Path("/follow")
    public Response followProfile(@PathParam("apikey") String apiKey,
                                  @QueryParam("profileid") Long profileId){
        User user = checkBegin(userService, apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        Profile myProfile = user.getProfile();
        Profile otherProfile = profileService.getProfileById(profileId);
        myProfile.followProfile(otherProfile);
        otherProfile.addFollowedBy(myProfile);
        profileService.edit(myProfile);
        profileService.edit(otherProfile);
        return Response.ok().entity(otherProfile).build();
    }

    @GET
    @Path("/getfollowedby")
    public Response getFollowedBy(@PathParam("apikey") String apiKey,
                                 @QueryParam("profileid") Long profileId){
        User user = checkBegin(userService, apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        Profile profile = user.getProfile();
        if (profileId!=null && profileId!=0){
            profile = profileService.getProfileById(profileId);
        }
        GenericEntity<List<Profile>> generic = new GenericEntity<List<Profile>>(profile.getFollowedBy()){};
        return Response.ok().entity(generic).build();
    }
}
