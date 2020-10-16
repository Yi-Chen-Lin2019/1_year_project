package gui;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import db.DataAccessException;

public class ManagePassword {


    public String generateHash(String input) throws NoSuchAlgorithmException {

        String hashtext = null;
        try {
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        // digest() method is called
        // to calculate message digest of the input string
        // returned as array of byte
        byte[] messageDigest = md.digest(input.getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        hashtext = no.toString(16);

        // Add preceding 0s to make it 32 bit
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }
        // return the HashText
        return hashtext;
    }

    public boolean compare(String java_pass, String db_pass) throws DataAccessException {
        return db_pass.equals(java_pass);
    }

}