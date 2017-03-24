package Controllers;

import Models.Tweet;
import Models.User;
import Services.TweetService;
import Services.UserService;

import javax.ejb.AfterBegin;
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
@Path("/tweet/{apikey}")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class TweetController extends Controller {


    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;




    @GET
    @Path("gettweets")
    public Response getTweets(@PathParam("apikey") String apiKey
            , @QueryParam("subject") String subject
            , @QueryParam("mentions") String mentions){
        User user = checkBegin(userService,apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        List<Tweet> tweets = null;
        if (subject!=null)
            tweets = tweetService.getTweetDAO().getTweetsBySubject(subject);
        GenericEntity<List<Tweet>> generic = new GenericEntity<List<Tweet>>(tweets){};
        return Response.ok().entity(generic).build();
    }
}
