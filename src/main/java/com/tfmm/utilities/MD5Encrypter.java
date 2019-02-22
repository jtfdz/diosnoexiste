package com.tfmm.utilities;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encrypter {
    private static MD5Encrypter encrypter = null;
    private MessageDigest md = null;

    public MD5Encrypter(){
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }



    public String hashString(String password){
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        md.reset();
        return hash;
    }


}
