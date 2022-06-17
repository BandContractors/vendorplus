/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.GeneralUtilities;
import api_tax.efris.innerclasses.Taxpayer;
import beans.Parameter_listBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import java.io.Serializable;
import org.json.JSONObject;
import java.security.PrivateKey;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import utilities.Security;
import utilities.SecurityPKI;

/**
 *
 * @author bajuna
 */
@ManagedBean
@SessionScoped
public class TaxpayerBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(TaxpayerBean.class.getName());

    public Taxpayer getTaxpayerDetailFromTaxOffline(String aTIN) {
        Taxpayer tp = null;
        String output = "";
        try {
            String json = "{\n"
                    + "	\"tin\":\"" + aTIN + "\",\n"
                    + "	\"ninBrn\":\"\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T119", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
            //System.out.println(output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));

            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject obj = parentbasicInformationjsonObject.getJSONObject("taxpayer");
            Gson g = new Gson();
            tp = new Taxpayer();
            tp = g.fromJson(obj.toString(), Taxpayer.class);
        } catch (Exception e) {
            tp = null;
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return tp;
    }

    public Taxpayer getTaxpayerDetailFromTaxOnline(String aTIN) {
        Taxpayer tp = null;
        String output = "";
        try {
            String json = "{\n"
                    + "	\"tin\":\"" + aTIN + "\",\n"
                    + "	\"ninBrn\":\"\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            //String AESpublickeystring = SecurityPKI.decrypt(new SecurityPKI().AESPublicKey(CompanySetting.getTaxIdentity(), new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value()), key);
            String AESpublickeystring = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            /**
             * Encrypt Content
             */
            String encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T119", CompanySetting.getTaxIdentity());
            //System.out.println(PostData);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
            //System.out.println(output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            String DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
            //String DecryptedContent = new String(Base64.decodeBase64(content));

            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject obj = parentbasicInformationjsonObject.getJSONObject("taxpayer");
            Gson g = new Gson();
            tp = new Taxpayer();
            tp = g.fromJson(obj.toString(), Taxpayer.class);
        } catch (Exception e) {
            tp = null;
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return tp;
    }

    public int checkDeemedExemptTaxpayer(String aApiTaxMode, String aTIN, String aCommodityCatCode) {
        int TpCatCode = 0;
        int CommCatCode = 0;//0 Error, 1 Normal, 2 Exempt, 3 Deemed
        Taxpayer tp = null;
        String output = "";
        try {
            String json = "{"
                    + "\"tin\": \"" + aTIN + "\","
                    + "\"commodityCategoryCode\": \"" + aCommodityCatCode + "\","
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = null;
            if (aApiTaxMode.equals("ONLINE")) {
                webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_ONLINE").getParameter_value());
            } else if (aApiTaxMode.equals("OFFLINE")) {
                webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            }
            PrivateKey key = null;
            String AESpublickeystring = "";
            String encryptedcontent = "";
            String signedcontent = "";
            /**
             * Read Private Key
             */
            if (aApiTaxMode.equals("ONLINE")) {
                key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
                AESpublickeystring = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            } else if (aApiTaxMode.equals("OFFLINE")) {
            }
            /**
             * Encrypt Content
             */
            if (aApiTaxMode.equals("ONLINE")) {
                encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
                signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            } else if (aApiTaxMode.equals("OFFLINE")) {
            }
            String PostData = "";
            /**
             * Post Data
             */
            if (aApiTaxMode.equals("ONLINE")) {
                PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T137", CompanySetting.getTaxIdentity());
            } else if (aApiTaxMode.equals("OFFLINE")) {
                PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T137", CompanySetting.getTaxIdentity());
            }
            //System.out.println(PostData);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
            //System.out.println(output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            String DecryptedContent = "";
            if (aApiTaxMode.equals("ONLINE")) {
                DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
            } else if (aApiTaxMode.equals("OFFLINE")) {
                DecryptedContent = new String(Base64.decodeBase64(content));
            }
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArrayCom = parentbasicInformationjsonObject.getJSONArray("commodityCategory");
            String CommTaxPayerType = "";
            for (int i = 0, size = jSONArrayCom.length(); i < size; i++) {
                JSONObject objectInArray = jSONArrayCom.getJSONObject(i);
                CommTaxPayerType = objectInArray.get("commodityCategoryTaxpayerType").toString();
            }
            if (CommTaxPayerType.equals("101")) {
                CommCatCode = 1;
            } else if (CommTaxPayerType.equals("102")) {
                CommCatCode = 2;
            } else if (CommTaxPayerType.equals("103")) {
                CommCatCode = 3;
            } else if (CommTaxPayerType.equals("104")) {
                CommCatCode = 4;
            } else {
                CommCatCode = 0;
            }
        } catch (Exception e) {
            tp = null;
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return CommCatCode;
    }

}
