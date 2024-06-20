package com.apis.globedr.security;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rest.core.request.body.AbsBody;
import com.rest.core.request.body.AbsSecurityBody;
import com.rest.core.response.AbsSecurityResponse;
import com.rest.core.security.ISecurity;
import io.restassured.builder.ResponseBuilder;
import io.restassured.response.Response;
import org.bouncycastle.util.encoders.Base64;
import org.testng.Assert;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class AlgorithmSecurity implements ISecurity {
    @Override
    public Object encryptBody(AbsBody body) {
        return encrypt(body.getBodyRequest().toString());
    }

    @Override
    public String getRequestBodyInfo(AbsSecurityBody request) {
        StringBuilder builder = new StringBuilder();
        builder.append("Request body:");
        builder.append("\n");
        //Show encrypt body as json

        builder.append(prettyPrint(request.getEncryptedBody()));
        builder.append("\n");
        builder.append("Request body before encrypting content:");
        builder.append("\n");
        builder.append(prettyPrint(request.getBodyRequest()));


        return builder.toString();
    }

    @Override
    public void encryptHeader() {

    }

    @Override
    public void encryptToken() {

    }

    @Override
    public Response decryptBody(Response response) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.clone(response);
        // update body with encryption data
        //JSONObject json = CommonUtil.loopJSONObject(response.body().asString());
        try {
            responseBuilder.setBody(Base64.decode(response.path("d").toString()));
        } catch (Exception exception) {
            Assert.fail(String.format("Can't decode base64 for response %s", response.getBody().asString()));
        }
        return responseBuilder.build();
    }

    @Override
    public String getResponseBodyInfo(AbsSecurityResponse rs) {
        StringBuilder builder = new StringBuilder();

        try {
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            com.google.gson.JsonParser jp = new com.google.gson.JsonParser();

            // Returned Encrypted Response from server
//            {
//                "d" :"value",
//                "k" :"value",
//                "data" :"this field is decode from field 'd', we don't need show it "
//            }

            JsonObject json = jp.parse(rs.getRs().asString()).getAsJsonObject();
            json.remove("data");
            builder.append(gson.toJson(json));
            builder.append("\n");
            builder.append("\n");
            builder.append("Response body after decrypting content");
            builder.append("\n");
            builder.append(gson.toJson(jp.parse(rs.getBody().asString())));
            builder.append("\n");
        } catch (Exception e) {
            Assert.fail("Error pretty body : " + rs.getBody().asString());
        }

        return builder.toString();
    }

    @Override
    public Response decryptHeader(Response response) {
        return response;
    }

    @Override
    public Response decryptToken(Response response) {
        return response;
    }


    private PublicKey getPublicKey() {
        // Read in the key into a String
        StringBuilder pkcs8Lines = new StringBuilder();
        BufferedReader rdr = null;
        try {
            rdr = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/resources/public-key.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

        // Base64 decode the result


        byte[] pkcs8EncodedBytes = Base64.decode(pkcs8Pem);

        // extract the private key

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String encryptData(byte[] data, Key key, String type) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(type);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(data));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> encrypt(String requestBody) {

        byte[] pass = "chuoi24kytubatky-autoapi".getBytes();

        DESedeKeySpec dks = null;
        Map<String, Object> body = new HashMap<>();

        try {
            dks = new DESedeKeySpec(pass);
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("DESede");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            //SecretKey secretKey = new SecretKeySpec(pass, 0, pass.length, "DES");
            PublicKey publicKey = getPublicKey();

            // Encrypt
            String d = encryptData(requestBody.getBytes(), secretKey, secretKey.getAlgorithm());
            String k = encryptData(pass, publicKey, "RSA/ECB/OAEPPadding");

            // Request
            body.put("d", d);
            body.put("k", k);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return body;
    }


    private String prettyPrint(Object body) {
        if (body instanceof Map) return prettyPrintMap((Map<String, Object>) body);

        return prettyPrintString(body.toString());
    }

    private String prettyPrintMap(Map<String, Object> body) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        return prettyPrintString(gson.toJson(body));
    }

    private String prettyPrintString(String body) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        com.google.gson.JsonParser jp = new com.google.gson.JsonParser();
        JsonElement je = jp.parse(body);

        return gson.toJson(je);
    }


}
