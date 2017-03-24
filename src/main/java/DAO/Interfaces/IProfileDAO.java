package DAO.Interfaces;

import DAO.IKwetterDAO;
import Models.Profile;

/**
 * Created by Daan on 22-Mar-17.
 */
public interface IProfileDAO extends IKwetterDAO<Profile> {

    public Profile getProfileByProfileName(String profilename);
    public Profile getProfileById(Long id);
}
