package by.nareiko.fr.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The type Password hasher.
 */
public class PasswordHasher {
    /**
     * Instantiates a new Password hasher.
     */
    public PasswordHasher() {
    }

    /**
     * Hash password string.
     *
     * @param password the password
     * @return the string
     */
    public static String hashPassword(String password) {
        String md5HexPassword = DigestUtils.md5Hex(password);
        return md5HexPassword;
    }
}
