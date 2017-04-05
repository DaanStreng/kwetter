package Utils;

import java.security.MessageDigest;

/**
 * Created by Daan on 29-Mar-17.
 */
public class StringUtils {

    public static String Hash(String stringToEncrypt){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(stringToEncrypt.getBytes());
            String encryptedString = new String(messageDigest.digest());
            return encryptedString;
        }
        catch(Exception ex){
            return stringToEncrypt;
        }
    }
}
