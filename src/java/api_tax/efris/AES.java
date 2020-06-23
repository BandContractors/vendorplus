package api_tax.efris;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

public class AES {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void main(String[] args) throws IOException {

        com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
        WebResource webResource = client.resource("https://efristest.ura.go.ug/efrisws/ws/taapp/getInformation");
        String PostData = "{\n"
                + "    \"data\": {\n"
                + "        \"content\": \"\",\n"
                + "        \"signature\": \"JKQWJK34K32JJEK2JQWJ5678\",\n"
                + "        \"dataDescription\": {\n"
                + "            \"codeType\": \"0\",\n"
                + "            \"encryptCode\": \"0\",\n"
                + "            \"zipCode\": \"0\"\n"
                + "        }\n"
                + "    },\n"
                + "    \"globalInfo\": {\n"
                + "        \"appId\": \"AP04\",\n"
                + "        \"brn\": \"\",\n"
                + "        \"dataExchangeId\": \"9230489223014123\",\n"
                + "        \"deviceMAC\": \"123\",\n"
                + "        \"deviceNo\": \"202001130955\",\n"
                + "        \"extendField\": {},\n"
                + "        \"interfaceCode\": \"T102\",\n"
                + "        \"requestCode\": \"TP\",\n"
                + "        \"requestTime\": \"2020-01-13 10:49:07\",\n"
                + "        \"responseCode\": \"TA\",\n"
                + "        \"taxpayerID\": \"1\",\n"
                + "        \"tin\": \"1015612362\",\n"
                + "        \"userName\": \"admin\",\n"
                + "        \"version\": \"1.1.20191201\",\n"
                + "        \"longitude\": \"116.397128\",\n"
                + "        \"latitude\": \"39.916527\",\n"
                + "        \"extendField\": {\n"
                + "        \"responseDateFormat\": \"dd/MM/yyyy\",\n"
                + "        \"responseTimeFormat\": \"dd/MM/yyyy HH:mm:ss\"}\"\n"
                + "        },\n"
                + "    \"returnStateInfo\": {\n"
                + "        \"returnCode\": \"\",\n"
                + "        \"returnMessage\": \"\"\n"
                + "    }\n"
                + "}";
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);

        String output = response.getEntity(String.class);
        System.out.println(output);

        String jsonString = output;
        JSONObject parentjsonObject = new JSONObject(jsonString);
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
        final String secretKey2 = "1171897062" + new SimpleDateFormat("ddMMyy").format(new Date());

        String decryptedString = AES.decrypt(clientPriKey, secretKey2);

        System.out.println(clientPriKey);
        //System.out.println(encryptedString);
        System.out.println(decryptedString);
    }

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            SecretKey originalKey = new SecretKeySpec(secret.getBytes(), 0, secret.length(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, originalKey);
            return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
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
}
