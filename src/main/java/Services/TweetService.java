package Services;

import DAO.IKwetterDAO;
import DAO.Interfaces.IProfileDAO;
import DAO.Interfaces.ISubjectDAO;
import DAO.Interfaces.ITweetDAO;
import Models.Profile;
import Models.Tweet;
import Models.User;
import Services.Facade.ServiceFacade;
import Utils.TweetComparator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Daan on 21-Mar-17.
 */
@Stateless
public class TweetService extends ServiceFacade<Tweet> {

    @Inject
    ITweetDAO dao;
    @Inject
    ISubjectDAO subjectDAO;
    @Inject
    IProfileDAO profileDAO;

    protected IKwetterDAO getDAO() {
        return dao;
    }

    public ITweetDAO getTweetDAO(){
        return (ITweetDAO) getDAO();
    }

    public Tweet createTweet(Profile profile, String message){
        Tweet tweet = new Tweet(profile, new Date(),message);
        this.create(tweet);

        return tweet;
    }
    public List<Tweet> getTweetsBySubject(String subject){
        return dao.getTweetsBySubject(subject);
    }
    public List<Tweet> getTweetsForUser(Profile user){
        ArrayList<Tweet> ret = new ArrayList<Tweet>();
        List<Tweet> base = dao.getTweetsByProfile(user);
        ret.addAll(base);
        if (user.getFollowedBy()!=null)
        for(Profile p : user.getFollows()){
            ret.addAll(dao.getTweetsByProfile(p));
        }
        List<Tweet> addTweets = dao.getTweetsByMentions(user.getDisplayName());
        ret.addAll(addTweets);
        Collections.sort(ret,new TweetComparator());
        return ret;
    }


}
