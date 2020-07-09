package by.nareiko.fr.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordHasher {
    public PasswordHasher(){}
    public static String hashPassword(String password){
        String md5HexPassword = DigestUtils.md5Hex(password);
        return md5HexPassword;
    }
}
