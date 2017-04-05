package DAO.Interfaces;

import DAO.IKwetterDAO;
import Models.Profile;
import Models.Tweet;
import Models.User;

import java.util.List;

/**
 * Created by Daan on 22-Mar-17.
 */
public interface ITweetDAO extends IKwetterDAO<Tweet> {
    public List<Tweet> getTweetsBySubject(String subject);
    public List<Tweet> getTweetsByProfile(Profile profile);
    public List<Tweet> getTweetsByMentions(String mentions);
}
