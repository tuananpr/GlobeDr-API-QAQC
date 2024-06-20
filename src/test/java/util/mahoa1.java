package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class mahoa1 {
    private static final String priKey = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCz3GPFbaQN5mxxSv+mhu6JUmS0\n" +
            "Su+7x8ubLGiYzfROXur2iHFOUXEALE1v7qlov8N4E9eThM0W1pZVmJFFajvkV+4dZNHkwpq0PV4M\n" +
            "LAaleuULPs8LoJKWK5fGOL8HXAsMtHxvrfbMkPyfWFDu8dYRg0ajEFKqE12zLqRkkDCdGYPnCiN/\n" +
            "zz20avkYxNUanwPwvGQHT99JFWbhNpbg3ycRpacgrsjzIQi3YggwjQjVx5hEYeFbfCRrCW7n4ZSh\n" +
            "KZYWwaiNsfEAEDmkk0xDUvUFOkZOSDZxGYFNbTtOdYMPkMtU2/lJrng3sNtnrHd6MMivjVzg7tk3\n" +
            "e7EkPIV0ewRjAgMBAAECggEAVJlzbGe35LnfhCKMwV1yUbEpoQwmWyMNB/4JbOZi5YIL7s4azYXN\n" +
            "KxGrktylXpnkbVSX79FlTjq9F6HdotLnPO6HF9hWSc2nSuQamosSzT9TAMkYRhXPHUd/6kLCx8lp\n" +
            "gR5b8YPYNDx2NtUprFqgIDipPwXsfAcD6cYkmRChbSLLPT5LlI0IBBPqoqvASU8UpYvITsK7Slvu\n" +
            "1sk3b14emPyFd4ROW99S64LeE6fA5RYzilOFntvlAz3hEkFXBaDQyodV48C823J0imuQ2REIxudB\n" +
            "gjcVKm0K6GXivWW+ihoWUfIKw9nnDRNGgL/Kz0GU4Ub0L/A1hov8AiopttoCkQKBgQDfMwWMv/ap\n" +
            "hh2g9FcyOEPeqjP28g2HFSH/WlrlF6F2yWPi1GJTTm5cZmjdPD+VvZ5wvaDTMSkPvtI3PV7pYng0\n" +
            "GfoVtQTNsmFU2XSawdOGuCIg2LEm0IZUPvph9jA2860PsdTAX+K2XzfcEjpBY6UNAggHL8o6BYCx\n" +
            "9JUZtq23jQKBgQDOSu80rkSq9a5vygU0jlnmeGhBR5fun/OG/tw9ocwlt8NIZFWUQL7XWB36QFdx\n" +
            "oDLqXclghyiDFRAqj2aJ5uFuHiFPfIW8RiozoXq3HVPmJ5+TWen65yE1SGAe7WbuiHnxyHZzA4Bt\n" +
            "ITgKrYGpYaqs2upj9/ZJRM2MjBEoMwh3rwKBgQC+WtBP4uPmXphkTh8TmILhov0gjf1ViINvOUst\n" +
            "+QJewZErwgB9lfVvXnPuwtRjy80Vz9QaaeHgKNk1/WQ13SIxJTpxSunm2ExERhU5azcQvXd11fj3\n" +
            "5n252eBGreUe6KFfMkYFL4G2Ee0dBnqii+4W8vWP04VACLNikfqbbH4pfQKBgAPnNw9wHKx/aYWD\n" +
            "9QVBjEHoFVDm4eEJ9bmgKNqhrRMpnvBHtbbQYswARtDtVro3pb75d030QLf6NgwYGoCRdcJ3n3m5\n" +
            "iW4+hZ3keEXusB+ysDpBgUPWC7LOY8MyCriIrIX+nP8gGdX2wvCwx7tEtc3/dl/d2TMZsinunIR+\n" +
            "MrLRAoGBALLGRMrO3eC7U0g4A7v62EUfkhdk5fN7KKza/vSFvMsp8XX74lXPqtxCC9Y2Q4N7HOHB\n" +
            "56++os3UQxm+e1KFyUt/EqN/+5XdLhRyd/NpzizBeIQMfkdvXPAqYTq9oL4UVEEwAbUMs21WUim0\n" +
            "sU+nsxZAgaM1CCiomqUgdlGm9wyd\n" +
            "-----END PRIVATE KEY-----";



    public static PrivateKey getPrimaryKeyFromStr(String primaryKeyStr){
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
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+","");

        // Base64 decode the result


        byte [] pkcs8EncodedBytes = org.bouncycastle.util.encoders.Base64.decode(pkcs8Pem);

        // extract the private key

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static PublicKey getPublicKeyFromStr(String primaryKeyStr){
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
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+","");

        // Base64 decode the result


        byte [] pkcs8EncodedBytes = org.bouncycastle.util.encoders.Base64.decode(pkcs8Pem);

        // extract the private key

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec (pkcs8EncodedBytes);
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws Exception {

        //PrivateKey privateKey = getPrimaryKeyFromStr(priKey);
        PublicKey publicKey = getPublicKeyFromStr(ConstantsKey.pubKey);

        String msg = "{\"utcTime0\":\"2020-12-08T04:31:41.354\",\"success\":true,\"message\":\"Thành công\",\"data\":{\"userId\":6,\"userSignature\":\"6E7A4277384F49387A755858695342384C736A6D32513D3D\",\"userType\":27,\"gdrLogin\":\"84767892632\",\"displayName\":\"Duy\",\"title\":\"mr\",\"phone\":\"84303305400\",\"gender\":1,\"dob\":\"2020-11-16T00:00:00.000\",\"avatar\":\"https://files-test.globedr.com/Upload/UserAvatar/167/909/1c5/6\",\"measurementUnit\":2,\"country\":\"VN\",\"visitingCountry\":\"CD\",\"language\":1,\"isOrgAdmin\":true,\"isVerified\":true,\"tokenType\":\"Bearer\",\"tokenExpires\":null,\"refreshToken\":\"/UXiG1jQVUmchnxjOE8bzCDNToRq70qLL/cV3Ka+Sn4=\",\"accessToken\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJEZXZpY2VJZCI6IjNtNGloYzl5OFRxWGYrZHVINk5VVnV1dERMWEpNMlVhMVZRWGR2WlpvN1V0Y1lzdlY2RGEwVEMyaENscVJSYkUiLCJBcHBUeXBlIjoiMSIsIlVzZXJJZCI6IjYiLCJVc2VyVHlwZSI6IjI3IiwiTGFuZ3VhZ2UiOiIxIiwianRpIjoiZGJjNWMwOTItZTM5YS00ZDhkLWJhZmMtMTViZWUxOTIyYjdjIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjpbIlByb3ZpZGVyIiwiUGF0aWVudCIsIkNvb3JkaW5hdG9yIiwiQXBwcm92ZXIiXSwiQ3JlYXRlZERhdGUiOiIxMi8wOC8yMDIwIDA0OjMxOjQxIiwiaXNzIjoiZ2xvYmVkciIsImF1ZCI6Imdsb2JlZHIifQ.a71BUXYDojqP7_3IlFS1XA67xdHOuMPJuUlbytdu4zQ\"},\"errors\":null}";

        String d = "eyJ1dGNUaW1lMCI6IjIwMjAtMTItMTVUMDM6MjI6NDcuMDEyIiwic3VjY2VzcyI6dHJ1ZSwibWVzc2FnZSI6IlRow6BuaCBjw7RuZyIsImRhdGEiOnsidXNlcklkIjo2LCJ1c2VyU2lnbmF0dXJlIjoiNkI0NTY2NTQzNjQxNkM3NzU3NjM3MjU5NzU3QTdBNEE2NzRCN0E3Njc1NzczRDNEIiwidXNlclR5cGUiOjI3LCJnZHJMb2dpbiI6Ijg0NzY3ODkyNjMyIiwiZGlzcGxheU5hbWUiOiJEdXkiLCJ0aXRsZSI6Im1yIiwicGhvbmUiOiI4NDMwMzMwNTQwMCIsImdlbmRlciI6MSwiZG9iIjoiMjAyMC0xMS0xNlQwMDowMDowMC4wMDAiLCJhdmF0YXIiOiJodHRwczovL2ZpbGVzLXRlc3QuZ2xvYmVkci5jb20vVXBsb2FkL1VzZXJBdmF0YXIvMTY3LzkwOS8xYzUvNiIsIm1lYXN1cmVtZW50VW5pdCI6MiwiY291bnRyeSI6IlZOIiwidmlzaXRpbmdDb3VudHJ5IjoiQ0QiLCJsYW5ndWFnZSI6MSwiaXNPcmdBZG1pbiI6dHJ1ZSwiaXNWZXJpZmllZCI6dHJ1ZSwidG9rZW5UeXBlIjoiQmVhcmVyIiwidG9rZW5FeHBpcmVzIjpudWxsLCJyZWZyZXNoVG9rZW4iOiJ6NEVWTTYvRGI0ZUI2ZjVBMHpqMHppV1QyUE9UWExnZkh4S2wxaFpnbWR3PSIsImFjY2Vzc1Rva2VuIjoiZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SkVaWFpwWTJWSlpDSTZJako1YldkVlR6RlVjMmxyV0RaeUsyMXRNakZ4WmxsVE1WYzVXVTV2UVN0R1dURnVjSGRKTXpOd1RGQlRUVTkyUmxadldIQk9MMk01Y0U4MFdWWlJLMFlpTENKQmNIQlVlWEJsSWpvaU15SXNJbFZ6WlhKSlpDSTZJallpTENKVmMyVnlWSGx3WlNJNklqSTNJaXdpVEdGdVozVmhaMlVpT2lJeElpd2lhblJwSWpvaVpETTNZVFl3WkRZdFpqQTJNaTAwWmpkaUxUZzBZakl0WlRnME5qRmhaV014TlRNMklpd2lhSFIwY0RvdkwzTmphR1Z0WVhNdWJXbGpjbTl6YjJaMExtTnZiUzkzY3k4eU1EQTRMekEyTDJsa1pXNTBhWFI1TDJOc1lXbHRjeTl5YjJ4bElqcGJJbEJ5YjNacFpHVnlJaXdpVUdGMGFXVnVkQ0lzSWtOdmIzSmthVzVoZEc5eUlpd2lRWEJ3Y205MlpYSWlYU3dpUTNKbFlYUmxaRVJoZEdVaU9pSXhNaTh4TlM4eU1ESXdJREF6T2pJeU9qUTJJaXdpYVhOeklqb2laMnh2WW1Wa2NpSXNJbUYxWkNJNkltZHNiMkpsWkhJaWZRLkxBMjl2LVJ0WVBQYVdBeUZEUTE1ZW1IWnpwX2ljc0pIcFpmZThXWGNmY3cifSwiZXJyb3JzIjpudWxsfQ==";
        String s = "EkKEDhDcUZ5IIbYXswhkaUVyBhkThVz+DHeVL39gA7166pLTJ1TTtldE4OQzzW1MI6Tpj8k3PvxH4MJjI6o5DMltRnC0wi9Q0vYfZ1Rg2EAqILYSHR/eaQ4KnrDGhLL2BKGqrwkkvDELqj2Cq1vrramus668UoMOB04HMAKB9H1m9Pa3AgFDA2AgRNBGBNqcW/rWi/ZMJqcHkluMKghilW3ND1sbVZ5MXJHC1b8A8U0Q4WKpE/aGeoqmU8z1c8efSORXZSJLDNn4j6D6n1e12S8nRHEeTCZRL/mfcHr6fI6yvzJ0tZJXTMaUHAB2hsvSmnxV7aLXQnnYWANujEBqIQ==";



        Signature signClient = Signature.getInstance("SHA256WithRSA");
        signClient.initVerify(publicKey);
        signClient.update(Base64.getDecoder().decode(d));
        System.out.println("!!! " + signClient.verify(Base64.getDecoder().decode(s)));
    }
}
