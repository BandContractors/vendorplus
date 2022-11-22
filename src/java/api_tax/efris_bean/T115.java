/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_currency_type;
import api_tax.efris.EFRIS_rate_unit;
import api_tax.efris.GeneralUtilities;
import beans.Parameter_listBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.GzipUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utilities.Security;
import utilities.SecurityPKI;

/**
 *
 * @author bajuna
 */
@ManagedBean
@SessionScoped
public class T115 implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(T115.class.getName());

    public void downloadRateUnit() {
        try {
            String APIMode = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_MODE").getParameter_value();
            if (APIMode.equals("OFFLINE")) {
                this.getRateUnitOffline();
            } else {
                this.getRateUnitOnline();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void downloadRateUnitThread() {
        try {
            //Runnable task = new Runnable() {
            Runnable task = () -> {
                try {
                    T115 t115 = new T115();
                    String APIMode = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_MODE").getParameter_value();
                    if (APIMode.equals("OFFLINE")) {
                        t115.getRateUnitOffline();
                    } else {
                        t115.getRateUnitOnline();
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, e);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getRateUnitOffline() {
        String output = "";
        try {
            String json = "";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_OFFLINE").getParameter_value());
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T115", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            /**
             * Check return state info
             */
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");
            String returnCode = dataobject.getString("returnCode");
            String returnMessage = dataobject.getString("returnMessage");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            JSONObject dataDescription = dataobjectcontent.getJSONObject("dataDescription");

            String zipCode = "0";
            String DecryptedContent = "";
            try {
                zipCode = dataDescription.getString("zipCode");
            } catch (Exception e) {
                //do nothing
            }

            /**
             * Decode and or decompress
             */
            if (zipCode.equals("0")) {
                DecryptedContent = new String(Base64.decodeBase64(content));
            } else {
                byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                DecryptedContent = new String(str);
            }

            /**
             * Get rateUnit
             */
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray rateUnit = parentbasicInformationjsonObject.getJSONArray("rateUnit");

            List<EFRIS_rate_unit> arList = new ArrayList<>();
            for (int i = 0; i < rateUnit.length(); i++) {
                Gson g = new Gson();
                EFRIS_rate_unit temp = g.fromJson(rateUnit.get(i).toString(), EFRIS_rate_unit.class);

                arList.add(temp);
            }
            //save Excise Duty List
            new EFRIS_rate_unitBean().saveEFRIS_rate_unit(arList);
            
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getRateUnitOnline() {
        String output = "";
        try {
            String json = "";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            String AESpublickeystring = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            /**
             * Encrypt Content
             */
            String encryptedcontent = Base64.encodeBase64String(json.getBytes("UTF-8"));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T115", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            /**
             * Check return state info
             */
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");
            //System.out.println("returnCode: " + dataobject.getString("returnCode"));
            //System.out.println("returnMessage: " + dataobject.getString("returnMessage"));

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            JSONObject dataDescription = dataobjectcontent.getJSONObject("dataDescription");
            String zipCode = "0";
            String DecryptedContent = "";
            try {
                zipCode = dataDescription.getString("zipCode");
            } catch (Exception e) {
                //do nothing
            }

            /**
             * Decode and or decompress
             */
            if (zipCode.equals("0")) {
                //DecryptedContent = new String(Base64.decodeBase64(content));
                DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
            } else {
                try {
                    //byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                    //DecryptedContent = new String(str);

                    byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                    DecryptedContent = SecurityPKI.AESdecrypt2(str, Base64.decodeBase64(AESpublickeystring));
                } catch (Throwable t) {
                    LOGGER.log(Level.ERROR, t);
                }
            }
            /**
             * Get rateUnit
             */
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray rateUnit = parentbasicInformationjsonObject.getJSONArray("rateUnit");

            List<EFRIS_rate_unit> arList = new ArrayList<>();
            for (int i = 0; i < rateUnit.length(); i++) {
                Gson g = new Gson();
                EFRIS_rate_unit temp = g.fromJson(rateUnit.get(i).toString(), EFRIS_rate_unit.class);

                arList.add(temp);
            }
            //save Excise Duty List
            new EFRIS_rate_unitBean().saveEFRIS_rate_unit(arList);

        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void downloadCurrencyType() {
        try {
            String APIMode = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_MODE").getParameter_value();
            if (APIMode.equals("OFFLINE")) {
                this.getCurrencyTypeOffline();
            } else {
                this.getCurrencyTypeOnline();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void downloadCurrencyTypeThread() {
        try {
            //Runnable task = new Runnable() {
            Runnable task = () -> {
                try {
                    T115 t115 = new T115();
                    String APIMode = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_MODE").getParameter_value();
                    if (APIMode.equals("OFFLINE")) {
                        t115.getCurrencyTypeOffline();
                    } else {
                        t115.getCurrencyTypeOnline();
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, e);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getCurrencyTypeOffline() {
        String output = "";
        try {
            String json = "";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_OFFLINE").getParameter_value());
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T115", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            /**
             * Check return state info
             */
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");
            String returnCode = dataobject.getString("returnCode");
            String returnMessage = dataobject.getString("returnMessage");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            JSONObject dataDescription = dataobjectcontent.getJSONObject("dataDescription");

            String zipCode = "0";
            String DecryptedContent = "";
            try {
                zipCode = dataDescription.getString("zipCode");
            } catch (Exception e) {
                //do nothing
            }

            /**
             * Decode and or decompress
             */
            if (zipCode.equals("0")) {
                DecryptedContent = new String(Base64.decodeBase64(content));
            } else {
                byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                DecryptedContent = new String(str);
            }

            /**
             * Get currencyType
             */
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray currencyType = parentbasicInformationjsonObject.getJSONArray("currencyType");

            List<EFRIS_currency_type> arList = new ArrayList<>();
            for (int i = 0; i < currencyType.length(); i++) {
                Gson g = new Gson();
                EFRIS_currency_type temp = g.fromJson(currencyType.get(i).toString(), EFRIS_currency_type.class);

                arList.add(temp);
            }
            //save Excise Duty List
            new EFRIS_currency_typeBean().saveEFRIS_currency_type(arList);
            
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getCurrencyTypeOnline() {
        String output = "";
        try {
            String json = "";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            String AESpublickeystring = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            /**
             * Encrypt Content
             */
            String encryptedcontent = Base64.encodeBase64String(json.getBytes("UTF-8"));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T115", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            /**
             * Check return state info
             */
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");
            System.out.println("returnCode: " + dataobject.getString("returnCode"));
            System.out.println("returnMessage: " + dataobject.getString("returnMessage"));

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            JSONObject dataDescription = dataobjectcontent.getJSONObject("dataDescription");
            String zipCode = "0";
            String DecryptedContent = "";
            try {
                zipCode = dataDescription.getString("zipCode");
            } catch (Exception e) {
                //do nothing
            }

            /**
             * Decode and or decompress
             */
            if (zipCode.equals("0")) {
                //DecryptedContent = new String(Base64.decodeBase64(content));
                DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
            } else {
                try {
                    //byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                    //DecryptedContent = new String(str);

                    byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                    DecryptedContent = SecurityPKI.AESdecrypt2(str, Base64.decodeBase64(AESpublickeystring));
                } catch (Throwable t) {
                    LOGGER.log(Level.ERROR, t);
                }
            }
            /**
             * Get currencyType
             */
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray currencyType = parentbasicInformationjsonObject.getJSONArray("currencyType");

            List<EFRIS_currency_type> arList = new ArrayList<>();
            for (int i = 0; i < currencyType.length(); i++) {
                Gson g = new Gson();
                EFRIS_currency_type temp = g.fromJson(currencyType.get(i).toString(), EFRIS_currency_type.class);

                arList.add(temp);
            }
            //save Excise Duty List
            new EFRIS_currency_typeBean().saveEFRIS_currency_type(arList);

        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }
}
