/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_excise_duty_list;
import api_tax.efris.GeneralUtilities;
import api_tax.efris.innerclasses.ExciseDutyDetailsList;
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
public class T125 implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(T125.class.getName());

    public void downloadExciseDuty_list() {
        try {
            String APIMode = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_MODE").getParameter_value();
            if (APIMode.equals("OFFLINE")) {
                this.getExciseDutyOffline();
            } else {
                this.getExciseDutyOnline();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void downloadExciseDuty_listThread() {
        try {
            //Runnable task = new Runnable() {
            Runnable task = () -> {
                try {
                    T125 t125 = new T125();
                    String APIMode = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_MODE").getParameter_value();
                    if (APIMode.equals("OFFLINE")) {
                        t125.getExciseDutyOffline();
                    } else {
                        t125.getExciseDutyOnline();
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

    public void getExciseDutyOffline() {
        String output = "";
        try {
            String json = "";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T125", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
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
             * Get exciseDutyList
             */
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray exciseDutyList = parentbasicInformationjsonObject.getJSONArray("exciseDutyList");

            List<EFRIS_excise_duty_list> arList = new ArrayList<>();
            for (int i = 0; i < exciseDutyList.length(); i++) {
                Gson g = new Gson();
                EFRIS_excise_duty_list temp = g.fromJson(exciseDutyList.get(i).toString(), EFRIS_excise_duty_list.class);

                //set unit, currency, rate_perc, rate_value
                if (temp.getExciseDutyDetailsList().size() > 0) {
                    for (int e = 0; e < temp.getExciseDutyDetailsList().size(); e++) {
                        ExciseDutyDetailsList edl = temp.getExciseDutyDetailsList().get(e);
                        if (edl.getType().equals("101")) {//101 is Percentage, 102 is Unit of measurement
                            temp.setRate_perc(edl.getRate());
                            temp.setCurrency(edl.getCurrency());
                        } else {
                            temp.setRate_value(edl.getRate());
                            temp.setUnit(edl.getUnit());
                            if (edl.getCurrency() == null) {
                                temp.setCurrency(edl.getCurrency());
                            }
                        }
                    }
                } else {
                    temp.setUnit("");
                    temp.setCurrency("");
                    temp.setRate_perc("");
                    temp.setRate_value("");
                }
                arList.add(temp);
            }
            //save Excise Duty List
            int savedExciseDutyList = new EFRIS_excise_duty_listBean().saveEFRIS_excise_duty_list(arList);
            //int savedExciseDutyList = 1;

            if (savedExciseDutyList == 1) {
                //do nothing for now
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getExciseDutyOnline() {
        String output = "";
        try {
            String json = "";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            /**
             * Encrypt Content
             */
            String encryptedcontent = Base64.encodeBase64String(json.getBytes("UTF-8"));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T125", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");
            String returnCode = dataobject.getString("returnCode");
            String returnMessage = dataobject.getString("returnMessage");
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
                DecryptedContent = new String(Base64.decodeBase64(content));
            } else {
                try {
                    byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                    DecryptedContent = new String(str);
                } catch (Throwable t) {
                    LOGGER.log(Level.ERROR, t);
                }
            }
            /**
             * Get exciseDutyList
             */
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray exciseDutyList = parentbasicInformationjsonObject.getJSONArray("exciseDutyList");

            List<EFRIS_excise_duty_list> arList = new ArrayList<>();
            for (int i = 0; i < exciseDutyList.length(); i++) {
                Gson g = new Gson();
                EFRIS_excise_duty_list temp = g.fromJson(exciseDutyList.get(i).toString(), EFRIS_excise_duty_list.class);

                //set unit, currency, rate_perc, rate_value
                if (temp.getExciseDutyDetailsList().size() > 0) {
                    for (int e = 0; e < temp.getExciseDutyDetailsList().size(); e++) {
                        ExciseDutyDetailsList edl = temp.getExciseDutyDetailsList().get(e);
                        if (edl.getType().equals("101")) {//101 is Percentage, 102 is Unit of measurement
                            temp.setRate_perc(edl.getRate());
                            if (temp.getCurrency() == null || "".equals(temp.getCurrency())) {
                                temp.setCurrency(edl.getCurrency());
                            }
                        } else {
                            temp.setRate_value(edl.getRate());
                            temp.setUnit(edl.getUnit());
                            if (temp.getCurrency() == null || "".equals(temp.getCurrency())) {
                                temp.setCurrency(edl.getCurrency());
                            }
                        }
                    }
                } else {
                    temp.setUnit("");
                    temp.setCurrency("");
                    temp.setRate_perc("");
                    temp.setRate_value("");
                }
                arList.add(temp);
            }
            //save Excise Duty List
            int savedExciseDutyList = new EFRIS_excise_duty_listBean().saveEFRIS_excise_duty_list(arList);
            //int savedExciseDutyList = 1;

            if (savedExciseDutyList == 1) {
                //do nothing for now
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }
}
