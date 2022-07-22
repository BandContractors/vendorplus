package utilities;

import api_tax.efris.GeneralUtilities;
import beans.Parameter_listBean;
import java.io.Serializable;
import javax.faces.bean.*;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import entities.Parameter_list;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
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
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class SecurityPKI implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(SecurityPKI.class.getName());
    private String ErrMsg = "";

    public PrivateKey getPrivate(String aFilename, String aPassword, String aAlias) throws Exception {
        InputStream ins = new FileInputStream(aFilename);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(ins, aPassword.toCharArray());
        KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection(aPassword.toCharArray());
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(aAlias, keyPassword);
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();
        return privateKey;
    }

    public String AESPublicKey(String TIN, String DeviceNumber) {
        String publickey = "";
        //com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
        com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create(ClientHelper.configureClient());
        WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_ONLINE").getParameter_value());
        String PostData = GeneralUtilities.PostData_Online("", "", "AP04", "", "9230489223014123", "123", DeviceNumber, "T104", TIN);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
        String output = response.getEntity(String.class);

        String jsonString = output;
        this.ErrMsg = jsonString;
        JSONObject parentjsonObject = new JSONObject(jsonString);
        JSONObject dataobject = parentjsonObject.getJSONObject("data");
        String content = dataobject.getString("content");
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
            return new String(cipher.doFinal(byteArray));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
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
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
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
            LOGGER.log(Level.ERROR, e);
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
            return new String(cipher.doFinal(byteArray));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return null;
    }

    public static String AESdecrypt2(byte[] strToDecrypt, byte[] secret) {
        try {
            SecretKeySpec originalKey = new SecretKeySpec(secret, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            return new String(cipher.doFinal(strToDecrypt));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return null;
    }

    private static SecretKeySpec secretKey;

    private static String getSecretKey(String tin) {
        String key = tin;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());
        String padding = "0000000000000000";
        //if(!StringUtil.isEmpty(tin)) {
        int tinLen = tin.length();
        if (tinLen < 16) {
            key = (tin + date + padding).substring(0, 16);
        }
        return key;
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            SecretKey originalKey = new SecretKeySpec(secret.getBytes(), 0, secret.length(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, originalKey);
            return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return null;
    }

    public static String decrypt2(String strToDecrypt, String secret) {
        try {
            //setKey(secret);
            SecretKey originalKey = new SecretKeySpec(secret.getBytes(), 0, secret.length(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] byteArray = Base64.decodeBase64(strToDecrypt); //Base64.getDecoder().decode(strToDecrypt);
            return new String(cipher.doFinal(byteArray));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return null;
    }

    public String getNewAesPublicKey() {
        String AesPublicKey = "";
        try {
            PrivateKey key = this.getPrivate(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            AesPublicKey = SecurityPKI.decrypt(this.AESPublicKey(CompanySetting.getTaxIdentity(), new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value()), key);
        } catch (Exception e) {
            AesPublicKey = "";
            LOGGER.log(Level.ERROR, e);
            LOGGER.log(Level.ERROR, this.ErrMsg);
        }
        return AesPublicKey;
    }

    public int saveNewAesPublicKey() {
        int saved = 0;
        try {
            String AesPublicKey = "";
            AesPublicKey = this.getNewAesPublicKey();
            if (AesPublicKey.length() > 0) {
                Parameter_list pl = new Parameter_listBean().getParameter_listById(70);
                pl.setParameter_value(AesPublicKey);
                new Parameter_listBean().saveParameter_list(pl);
                new Parameter_listBean().saveParameter_listByIdMemory(70, AesPublicKey);
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    /**
     * @return the ErrMsg
     */
    public String getErrMsg() {
        return ErrMsg;
    }

    /**
     * @param ErrMsg the ErrMsg to set
     */
    public void setErrMsg(String ErrMsg) {
        this.ErrMsg = ErrMsg;
    }
}
