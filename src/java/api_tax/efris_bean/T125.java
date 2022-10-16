/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_excise_duty_list;
import api_tax.efris.GeneralUtilities;
import api_tax.efris.innerclasses.GoodsCommodity;
import beans.Parameter_listBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    public static int totalUNSPC;
    public static int downloadedUNSPC;

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
            //reset total number of goods commodities to be downloaded
            totalUNSPC = 0;
            //reset total number of goods commodities already downloaded
            downloadedUNSPC = 0;
            //Runnable task = new Runnable() {
            Runnable task = () -> {
                try {
                    T125 t124 = new T125();
                    String APIMode = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_MODE").getParameter_value();
                    if (APIMode.equals("OFFLINE")) {
                        t124.getExciseDutyOffline();
                    } else {
                        t124.getExciseDutyOnline();
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
            String json = "{\n"
                    + "	\"pageNo\": \"1\",\n"
                    + "	\"pageSize\": \"99\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T124", CompanySetting.getTaxIdentity());

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
            if (zipCode.equals("0")) {
                DecryptedContent = new String(Base64.decodeBase64(content));
            } else {
                byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                DecryptedContent = new String(str);
            }

            /**
             * Get total page size
             */
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject page = parentbasicInformationjsonObject.getJSONObject("page");
            int pageCount = page.getInt("pageCount");
            int pageSize = page.getInt("pageSize");
            int totalSize = page.getInt("totalSize");

            //set total number of goods commodities to be downloaded
            totalUNSPC = totalSize;
            //reset total number of goods commodities already downloaded
            downloadedUNSPC = 0;
            /**
             * End Get total page size
             */

            for (int x = 1; x <= pageCount; x++) {
                json = "{\n"
                        + "	\"pageNo\": \"" + x + "\",\n"
                        + "	\"pageSize\": \"" + pageSize + "\"\n"
                        + "}";
                /**
                 * Post Data
                 */
                PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T124", CompanySetting.getTaxIdentity());
                response = webResource.type("application/json").post(ClientResponse.class, PostData);
                output = response.getEntity(String.class);

                parentjsonObject = new JSONObject(output);
                dataobjectcontent = parentjsonObject.getJSONObject("data");
                content = dataobjectcontent.getString("content");
                dataDescription = dataobjectcontent.getJSONObject("dataDescription");

                zipCode = "0";
                DecryptedContent = "";
                try {
                    zipCode = dataDescription.getString("zipCode");
                } catch (Exception e) {
                    //do nothing
                }
                if (zipCode.equals("0")) {
                    DecryptedContent = new String(Base64.decodeBase64(content));
                } else {
                    byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                    DecryptedContent = new String(str);
                }
                JSONObject jSONObject = new JSONObject(DecryptedContent);
                JSONArray array = jSONObject.getJSONArray("records");
                List<GoodsCommodity> arList = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    Gson g = new Gson();
                    GoodsCommodity temp = g.fromJson(array.get(i).toString(), GoodsCommodity.class);
                    arList.add(temp);
                }
                //save goods commodity                
                int savedGoodsCommodity = new EFRIS_goods_commodityBean().saveEFRIS_goods_commodity(arList);

                if (savedGoodsCommodity == 1) {
                    //set total number of goods commodities already downloaded
                    downloadedUNSPC = downloadedUNSPC + arList.size();
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getExciseDutyOnline() {
        String output = "";
        try {
            String json2 = "{\n"
                    + "	\"pageNo\": \"1\",\n"
                    + "	\"pageSize\": \"99\"\n"
                    + "}";
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

            //Decode and or decompress
            if (zipCode.equals("0")) {
                DecryptedContent = new String(Base64.decodeBase64(content));
            } else {
                try {
                    byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                    DecryptedContent = new String(str);
                    //System.out.println("DecryptedContent: " + DecryptedContent);
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
                arList.add(temp);
            }
            //save goods commodity
            int savedExciseDutyList = new EFRIS_excise_duty_listBean().saveEFRIS_excise_duty_list(arList);
            //int savedGoodsCommodity = 1;

            if (savedExciseDutyList == 1) {
                //do nothing for now
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }
}
