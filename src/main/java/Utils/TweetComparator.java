package Utils;

import Models.Profile;
import Models.Tweet;

import java.util.Comparator;

/**
 * Created by Daan on 01-Apr-17.
 */
public class TweetComparator implements Comparator<Tweet> {
    public int compare(Tweet o1, Tweet o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
