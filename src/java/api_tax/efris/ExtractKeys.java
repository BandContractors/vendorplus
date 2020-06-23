/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

/**
 *
 * @author bajuna
 */
public class ExtractKeys {

    public String PrivateKey(String TIN, String DeviceNumber, String code, String codeDesc) {
        String privatekey = "";
        com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
        WebResource webResource = client.resource("https://efristest.ura.go.ug/efrisws/ws/taapp/getInformation");
        String PostData = GeneralUtilities.PostData("", "", "AP04", "", "9230489223014123", "123", DeviceNumber, "T102", TIN);
//        String PostData = "{\n"
//                + "    \"data\": {\n"
//                + "        \"content\": \"\",\n"
//                + "        \"signature\": \"JKQWJK34K32JJEK2JQWJ5678\",\n"
//                + "        \"dataDescription\": {\n"
//                + "            \"codeType\": \"0\",\n"
//                + "            \"encryptCode\": \"0\",\n"
//                + "            \"zipCode\": \"0\"\n"
//                + "        }\n"
//                + "    },\n"
//                + "    \"globalInfo\": {\n"
//                + "        \"appId\": \"AP04\",\n"
//                + "        \"brn\": \"\",\n"
//                + "        \"dataExchangeId\": \"9230489223014123\",\n"
//                + "        \"deviceMAC\": \"123\",\n"
//                + "        \"deviceNo\": \"202001130955\",\n"
//                + "        \"extendField\": {},\n"
//                + "        \"interfaceCode\": \"T102\",\n"
//                + "        \"requestCode\": \"TP\",\n"
//                + "        \"requestTime\": \"2020-01-13 10:49:07\",\n"
//                + "        \"responseCode\": \"TA\",\n"
//                + "        \"taxpayerID\": \"1\",\n"
//                + "        \"tin\": \"1015612362\",\n"
//                + "        \"userName\": \"admin\",\n"
//                + "        \"version\": \"1.1.20191201\",\n"
//                + "        \"longitude\": \"116.397128\",\n"
//                + "        \"latitude\": \"39.916527\",\n"
//                + "        \"extendField\": {\n"
//                + "        \"responseDateFormat\": \"dd/MM/yyyy\",\n"
//                + "        \"responseTimeFormat\": \"dd/MM/yyyy HH:mm:ss\"}\"\n"
//                + "        },\n"
//                + "    \"returnStateInfo\": {\n"
//                + "        \"returnCode\": \"\",\n"
//                + "        \"returnMessage\": \"\"\n"
//                + "    }\n"
//                + "}";
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);

        String output = response.getEntity(String.class);
        System.out.println(output);

        String jsonString = output;
        JSONObject parentjsonObject = new JSONObject(jsonString);
        /**
         * For checking any error
         */
        JSONObject checkerror = parentjsonObject.getJSONObject("returnStateInfo");
        code = checkerror.getString("returnCode");
        codeDesc = checkerror.getString("returnMessage");

        if (!"00".equals(code)) {
            return "ERROR:," + code + "," + codeDesc;
        }

        JSONObject dataobject = parentjsonObject.getJSONObject("data");
        String content = dataobject.getString("content");
        System.out.println(content);

        byte[] byteArray = Base64.decodeBase64(content);

        // Print the decoded array
        //System.out.println(Arrays.toString(byteArray));
        // Print the decoded string 
        String decodedString = new String(byteArray);
        JSONObject parentjsonObject2 = new JSONObject(decodedString);
        String clientPriKey = parentjsonObject2.getString("clientPriKey");
        final String secretKey2 = "1396190549" + new SimpleDateFormat("ddMMyy").format(new Date());
                                   //139619054992380623
        
        String decryptedString = AES.decrypt(clientPriKey, secretKey2);

        System.out.println(clientPriKey);
        //System.out.println(encryptedString);
        System.out.println(decryptedString);
        privatekey = decryptedString;
        return privatekey;
    }

    public String AESPublicKey(String TIN, String DeviceNumber) {
        String publickey = "";
        com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
        WebResource webResource = client.resource("https://efristest.ura.go.ug/efrisws/ws/taapp/getInformation");
        String PostData = GeneralUtilities.PostData("", "", "AP04", "", "9230489223014123", "123", DeviceNumber, "T104", TIN);
        System.out.println(PostData);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
        String output = response.getEntity(String.class);
        System.out.println(output);

        String jsonString = output;
        JSONObject parentjsonObject = new JSONObject(jsonString);
        JSONObject dataobject = parentjsonObject.getJSONObject("data");
        String content = dataobject.getString("content");
        System.out.println(content);
        byte[] byteArray = Base64.decodeBase64(content); //Base64.getDecoder().decode(strToDecrypt);
        String AESJson = new String(byteArray);
        JSONObject AESjsonObject = new JSONObject(AESJson);
        String passowrdDes = AESjsonObject.getString("passowrdDes");
        publickey = passowrdDes;
        return publickey;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            //setKey(secret);
            SecretKey originalKey = new SecretKeySpec(secret.getBytes(), 0, secret.length(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] byteArray = Base64.decodeBase64(strToDecrypt); //Base64.getDecoder().decode(strToDecrypt);
            //System.out.println(byteArray.length);
            //return new String(cipher.doFinal(strToDecrypt.getBytes()));
            return new String(cipher.doFinal(byteArray));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static PrivateKey loadPrivateKey(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] buffer = Base64.decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

    }

    public static PublicKey getKey(String key) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        byte[] byteKey = Base64.decodeBase64(key);
        //byteKey=Base64.decodeBase64(new String(byteKey));
        //BASE64Decoder decoder=new BASE64Decoder();
        //byte[] byteKey=decoder.decodeBuffer(key);
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        //RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(X509publicKey);
        PublicKey publicKey = kf.generatePublic(X509publicKey);

        return publicKey;
    }

    //The list consists of the message and the signature.
    //The method that signs the data using the private key that is stored in keyFile path
    public byte[] sign(String data, PrivateKey privateKey) throws InvalidKeyException, Exception {
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(privateKey);
        rsa.update(data.getBytes("UTF-8"));
        return rsa.sign();
    }

    /**
     * Encrypts the text with the public key (RSA)
     *
     * @param rawText Text to be encrypted
     * @param publicKey
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static String encrypt(String rawText, PublicKey publicKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        //return Base64.encodeBase64String(cipher.doFinal(rawText.getBytes()));
        //BASE64Encoder encoder = new BASE64Encoder();
        //return encoder.encode(cipher.doFinal(rawText.getBytes("UTF-8")));
        byte[] byt = cipher.doFinal(rawText.getBytes("UTF-8"));
        return Base64.encodeBase64String(byt);
    }

    /**
     * Decrypts the text with the private key (RSA)
     *
     * @param cipherText Text to be decrypted
     * @param privateKey
     * @return Decrypted text (Base64 encoded)
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static String decrypt(String cipherText, PrivateKey privateKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), "UTF-8");
    }

    public static String AESencrypt(String strToEncrypt, byte[] secret) {
        try {
            SecretKey originalKey = new SecretKeySpec(secret, 0, 16, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, originalKey);
            return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String AESdecrypt(String strToDecrypt, byte[] secret) {
        try {
            //setKey(secret);
            SecretKey originalKey = new SecretKeySpec(secret, 0, 16, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] byteArray = Base64.decodeBase64(strToDecrypt); //Base64.getDecoder().decode(strToDecrypt);
            //System.out.println(byteArray.length);
            //return new String(cipher.doFinal(strToDecrypt.getBytes()));
            return new String(cipher.doFinal(byteArray));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
