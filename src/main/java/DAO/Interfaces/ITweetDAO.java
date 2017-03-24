package DAO.Interfaces;

import DAO.IKwetterDAO;
import Models.Tweet;

import java.util.List;

/**
 * Created by Daan on 22-Mar-17.
 */
public interface ITweetDAO extends IKwetterDAO<Tweet> {
    public List<Tweet> getTweetsBySubject(String subject);
}
