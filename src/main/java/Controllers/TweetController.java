package Controllers;

import Models.Tweet;
import Models.User;
import Services.TweetService;
import Services.UserService;

import javax.annotation.Resource;
import javax.ejb.AfterBegin;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.persistence.criteria.Order;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.fasterxml.jackson.databind.*;

/**
 * Created by Daan on 22-Mar-17.
 */

import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Path("/tweet/{apikey}")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class TweetController extends Controller {


    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;

    @Resource
    ManagedExecutorService executorService;



    @GET
    @Path("/gettweets")
    public Response getTweets(@PathParam("apikey") String apiKey
            , @QueryParam("subject") String subject
            , @QueryParam("mentions") String mentions){
        User user = checkBegin(userService,apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        List<Tweet> tweets = null;
        if (subject!=null)
            tweets = tweetService.getTweetsBySubject(subject);
        else
            tweets = tweetService.getTweetsForUser(user.getProfile());
   //     GenericEntity<List<Tweet>> generic = new GenericEntity<List<Tweet>>(tweets){};
        if (tweets.size()>101){
            tweets = tweets.subList(tweets.size()-1, tweets.size()-100);
        }
        ObjectMapper map = new ObjectMapper();

        try {
            return Response.ok().entity(map.writeValueAsString(tweets)).build();
        }
        catch(Exception ex){
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        }
    }

    @GET
    @Path("/tweetstream")
    public void getTweetStream(@Suspended final AsyncResponse ar, @PathParam("apikey") final String apiKey){
        final TweetController me = this;
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                User user = checkBegin(userService,apiKey);
                if (user == null)
                    ar.resume(Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build());
                List<Tweet> tweets = tweetService.getTweetsForUser(user.getProfile());
                int size = tweets.size();
                while(tweetService.getTweetsForUser(user.getProfile()).size()<=size){
                    try {
                        Thread.sleep(500);
                    }
                    catch(Exception ex){}
                }
                Response response = me.getTweets(apiKey,null,null);
                ar.resume(response);
            }
        });
    }

    @POST
    @Path("/addtweet")
    public Response addTweet(@PathParam("apikey") String apiKey,@FormParam("message") String message){
        User user = checkBegin(userService,apiKey);
        if (user == null)
            return Response.ok().entity("{\"result\":\"BAD_API_KEY\"}").build();
        Tweet t = new Tweet(user.getProfile(),new Date(),message);
        tweetService.create(t);
        return Response.ok().entity(t).build();
    }
}
