package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.testng.Assert;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class DecryptRequest {


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

    public static String prettyBody(String jsonString) {

        StringBuilder builder = new StringBuilder();

        try {
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            com.google.gson.JsonParser jp = new com.google.gson.JsonParser();
            JsonObject json = jp.parse(jsonString).getAsJsonObject();
            builder.append("Body after decrypting content");
            builder.append("\n");
            builder.append(gson.toJson(jp.parse(jsonString)));
            builder.append("\n");
        } catch (Exception e) {
            Assert.fail("Error pretty body : " + jsonString);
        }

        return builder.toString();
    }

    public static String decryptRequest(String body) {
        PrivateKey priKey = getPrimaryKeyFromStr(ConstantsKey.priKey);

        try {

            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String d = json.get("d").getAsString().replaceAll("\\\\", "");
            String k = json.get("k").getAsString().replaceAll("\\\\", "");

            byte[] key = decrypt(Base64.getDecoder().decode(k), priKey, "RSA/ECB/OAEPPadding");
            DESedeKeySpec dks = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("DESede");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            byte[] data = decrypt(Base64.getDecoder().decode(d), secretKey, secretKey.getAlgorithm());
            return prettyBody(new String(data, StandardCharsets.UTF_8));

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

        String d = "SgZcQy4yaTp+0kZKgFn\\/QU+HxrLJ5RWAOIM9jUz5Xw8sfKwvSAEuR75wQqU2iW6rR5PDfXOt1E61Le\\/Z7IXqSocd1+246oAyYjitlW+XgXpfsgEMqIUtCtOwOhZqYmDGWCuRtGpp9czoMhyzMgAkExe6ilZdtF407ckPMXk64NlqEiMTCRoXo3q+fZKo9an\\/F40GcD3VfJum\\/SxOMFNmTCs\\/aQRu4F6Tcs+R9ntNLYVmvtbEUr1qRVSOCh4vKHjPonedLObFFDh\\/4nwXRu2i8cpZqchqG++gi4BuLL25A5Byz5H2e00thVE\\/3gKOhS3iYfxMF5AhYFZQ247Yc09l4yumjFzQ4EP3hNXgRyZIrx0LnfKKfzXmCEYehcK0a9jx";
        String k = "iyOhUGveJl9R4vcVjxFR90pMzzYxRTay8EhW3Qsdbtd\\/Yr+10EiFJgjCLPjjvYsvFIZP4\\/q\\/96BwoWJzJai9lQWawfjkukoQiFoXHNC88yQsNgRRyMht\\/EIPbTTtdHXhTYdbDJnIA8kwcw6vmspcBOau4f9YG\\/W\\/762kdn74ijPEMSY260WLzaVIXQbVOkrlCJEfVaxVfpsj6PibcBeUkaB9N3XDOjmOxi9xOUOPGARFjaY0CKF8XoJDiF7M2dirpI96HogsdS0jTO8mbYCAU3aI1kyLfeNStCH9\\/H50NBJJfV5eR6TiCCTu5Sp5OPIQVpSwMUBkIPUeyjkJKuGPkQ==";

        d = d.replaceAll("\\\\", "");
        k = k.replaceAll("\\\\", "");


        PrivateKey priKey = getPrimaryKeyFromStr(ConstantsKey.priKey);
        byte[] key = decrypt(Base64.getDecoder().decode(k), priKey, "RSA/ECB/OAEPPadding");

        DESedeKeySpec dks = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory
                .getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(dks);

        byte[] data = decrypt(Base64.getDecoder().decode(d), secretKey, secretKey.getAlgorithm());

        System.out.println("!!! body request \n" + prettyBody(new String(data, StandardCharsets.UTF_8)));
    }

}
