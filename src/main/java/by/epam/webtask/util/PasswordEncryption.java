package by.epam.webtask.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncryption {
    public static String md5Apache(String password) {
        String md5Hex = DigestUtils.md5Hex(password);
        return md5Hex;
    }
}