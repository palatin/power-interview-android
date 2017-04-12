package example.com.powerinterview.utils;

/**
 * Created by Игорь on 12.04.2017.
 */

import android.util.Log;

import com.loopj.android.http.Base64;


import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import example.com.powerinterview.exceptions.EncryptionException;


public class Encrypt {




    public static String publicServerKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAswtsGHh1wvtUeYX8BIHy" +
            "BOGtqLYUOOw6YI/8aWGwU6fLXBj6+fxZRQX/j3G9XdrgvnFe/DzXSglQ+RofX8t8" +
            "cxwKkGATnfHC91x69V8KCWMJS2r9zish8FdKwmY/H+p6YTxvaoQoCgLj7S3XCvC0" +
            "HD6fYrnzuHSqofIOoyiDgkW3OP57mQfCZu+rtZdrD1IisVPs9DYZ4GGBElIwhjed" +
            "CvO9qwJxmCypMs5Xmx0nG+CxNgkHQPj8cOE9UG3qwnI/ubvqVOpvs6HHHe8unF7z" +
            "gBDWQ/fb+ZLSxBwbT+F0LJK2rVTWBEIHUjKzCU6wFu1raaDKs8fqsD0Qr0wzwHEL" +
            "GwIDAQAB";


    public static String encryptByRSA(String key, String input) throws EncryptionException {



        PublicKey publicRSAKey = null;

        try {

            byte[] publicBytes = Base64.decode(key, Base64.DEFAULT);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicRSAKey = keyFactory.generatePublic(spec);


            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicRSAKey);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes("UTF-8"));
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new EncryptionException(ex.getMessage());
        }
    }

    public static String encryptMD5(String input) throws EncryptionException {
        try {
            MessageDigest mdEnc = null;
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(input.getBytes());
            String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
            while ( md5.length() < 32 ) {
                md5 = "0"+md5;
            }
            return md5;
        }
        catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
            throw new EncryptionException(ex.getMessage());
        }
    }
}

