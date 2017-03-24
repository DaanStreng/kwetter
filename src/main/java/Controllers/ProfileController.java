package Controllers;

import Models.Profile;
import Models.Tweet;
import Models.User;
import Services.ProfileService;
import Services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Path("getprofile")
    public Response getProfile(
            @PathParam("apikey") String apiKey,
            @QueryParam("displayname") String displayName
            ,@QueryParam("profileId") Long profileId){
        User user = checkBegin(userService, apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        Profile profile = null;
        if (displayName!=null)
         profile = profileService.getProfileDAO().getProfileByProfileName(displayName);
        else if (profileId!=null){
            profile = profileService.getProfileDAO().getProfileById(profileId);
        }
        else profile = user.getProfile();
        return Response.ok().entity(profile).build();
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

    @GET
    @Path("/follow")
    public Response followProfile(@PathParam("apikey") String apiKey,
                                  @QueryParam("profileid") Long profileId){
        User user = checkBegin(userService, apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        Profile myProfile = user.getProfile();
        Profile otherProfile = profileService.getProfileDAO().getProfileById(profileId);
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
            profile = profileService.getProfileDAO().getProfileById(profileId);
        }
        GenericEntity<List<Profile>> generic = new GenericEntity<List<Profile>>(profile.getFollowedBy()){};
        return Response.ok().entity(generic).build();
    }
}
