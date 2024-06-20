package util;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import javax.crypto.*;
import javax.crypto.spec.*;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class mahoa2 {


    public static byte[] encrypt(byte[] data, Key key, String type) {
        Cipher cipher = null;
        try {
            System.out.println("!!! " + type);
            cipher = Cipher.getInstance(type);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static byte[] decrypt(byte[] data, Key key, String type) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(type);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static PrivateKey getPrimaryKeyFromStr(String primaryKeyStr) {
        // Read in the key into a String
        StringBuilder pkcs8Lines = new StringBuilder();
        BufferedReader rdr = new BufferedReader(new StringReader(primaryKeyStr));
        String line;


        try {
            while ((line = rdr.readLine()) != null) {
                pkcs8Lines.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the "BEGIN" and "END" lines, as well as any whitespace

        String pkcs8Pem = pkcs8Lines.toString();
        pkcs8Pem = pkcs8Pem.replace("-----BEGIN PRIVATE KEY-----", "");
        pkcs8Pem = pkcs8Pem.replace("-----END PRIVATE KEY-----", "");
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(pkcs8Pem));

        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static PublicKey getPublicKeyFromStr(String primaryKeyStr) {
        // Read in the key into a String
        StringBuilder pkcs8Lines = new StringBuilder();
        BufferedReader rdr = new BufferedReader(new StringReader(primaryKeyStr));
        String line;


        try {
            while ((line = rdr.readLine()) != null) {
                pkcs8Lines.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the "BEGIN" and "END" lines, as well as any whitespace

        String pkcs8Pem = pkcs8Lines.toString();
        pkcs8Pem = pkcs8Pem.replace("-----BEGIN PUBLIC KEY-----", "");
        pkcs8Pem = pkcs8Pem.replace("-----END PUBLIC KEY-----", "");
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");


        // extract the public key

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(pkcs8Pem));
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            return  kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) throws Exception {


        String msg = "{\"country\":\"VN\",\"deviceId\":\"2ymgUO1TsikX6r+mm21qfYS1W9YNoA+FY1npwI33pLPSMOvFVoXpN/c9pO4YVQ+F\",\"language\":1,\"password\":\"123456\",\"gdrLogin\":\"0767892632\"}";
        byte[] pass = "01234567890123456789abcd".getBytes();

        DESedeKeySpec dks = new DESedeKeySpec(pass);
        SecretKeyFactory keyFactory = SecretKeyFactory
                .getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(dks);

        //SecretKey secretKey = new SecretKeySpec(pass, 0, pass.length, "DES");
        PublicKey publicKey = getPublicKeyFromStr(ConstantsKey.pubKey);

        // Encrypt
        byte[] d = encrypt(msg.getBytes(StandardCharsets.UTF_8), secretKey, secretKey.getAlgorithm());
        byte[] k = encrypt(pass, publicKey, "RSA/ECB/OAEPPadding");


        // Request
        Map<String, Object> body = new HashMap<>();
        body.put("d", Base64.getEncoder().encodeToString(d));
        body.put("k", Base64.getEncoder().encodeToString(k));

        // Req
        Response rs = RestAssured.given()
                .header("content-type", "application/json")
                .log().all()
                .when().body(body).post("https://apis1.globedr.com/api/v1/Account/ESignIn");
        rs.then().log().body();

        String response = rs.getBody().path("d");
        System.out.println("observers " + new String(Base64.getDecoder().decode(response)));

    }


}
