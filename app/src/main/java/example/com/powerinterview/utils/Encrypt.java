package example.com.powerinterview.utils;

/**
 * Created by Игорь on 12.04.2017.
 */

import android.util.Log;

import com.loopj.android.http.Base64;
import com.scottyab.aescrypt.AESCrypt;


import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import example.com.powerinterview.exceptions.EncryptionException;


public class Encrypt {




    public static String publicServerKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAswtsGHh1wvtUeYX8BIHy" +
            "BOGtqLYUOOw6YI/8aWGwU6fLXBj6+fxZRQX/j3G9XdrgvnFe/DzXSglQ+RofX8t8" +
            "cxwKkGATnfHC91x69V8KCWMJS2r9zish8FdKwmY/H+p6YTxvaoQoCgLj7S3XCvC0" +
            "HD6fYrnzuHSqofIOoyiDgkW3OP57mQfCZu+rtZdrD1IisVPs9DYZ4GGBElIwhjed" +
            "CvO9qwJxmCypMs5Xmx0nG+CxNgkHQPj8cOE9UG3qwnI/ubvqVOpvs6HHHe8unF7z" +
            "gBDWQ/fb+ZLSxBwbT+F0LJK2rVTWBEIHUjKzCU6wFu1raaDKs8fqsD0Qr0wzwHEL" +
            "GwIDAQAB";


    public static String encryptByAES(String input, String key) throws Exception {


        try {
            return AESCrypt.encrypt(key, input);
        }
        catch (Exception ex) {
            throw new Exception();
        }
    }

    public static String generateRandomAESKey() throws EncryptionException {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new EncryptionException(e.getMessage());
        }
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();

        String aes = android.util.Base64.encodeToString(secretKey.getEncoded(), android.util.Base64.DEFAULT);
        return aes;
    }

    public static String encryptAES(String value, String key) throws Exception {
        try {
            byte[] bytes = "0111872818520578".getBytes("UTF-8");
            IvParameterSpec iv = new IvParameterSpec(bytes);
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.decode(key,Base64.DEFAULT), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }

    }

    public static String decryptAES(String value, String key) throws Exception {
        try {
            byte[] bytes = "0111872818520578".getBytes("UTF-8");
            IvParameterSpec iv = new IvParameterSpec(bytes);
            SecretKeySpec skeySpec = new SecretKeySpec(android.util.Base64.decode(key, Base64.DEFAULT), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] decrypted = cipher.doFinal(android.util.Base64.decode(value, android.util.Base64.DEFAULT));

            return new String(decrypted, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }

    }

    public static String decryptByAES(String input, String key) throws Exception {

        try {
            return AESCrypt.decrypt(key, input);
        }
        catch (Exception ex) {
            throw new Exception(ex);
        }
    }



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

