/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.GeneralUtilities;
import api_tax.efris.innerclasses.GoodsCommodity;
import beans.DownloadStatusBean;
import beans.Parameter_listBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import entities.DownloadStatus;
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
public class T124 implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(T124.class.getName());
    public static int totalUNSPC;
    public static int downloadedUNSPC;

    public void generateUNSPCexcelOffline(String aFileNameWithPath) {
        String output = "";
        String returnCode = "";
        String returnMessage = "";
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
            returnCode = dataobject.getString("returnCode");
            returnMessage = dataobject.getString("returnMessage");
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
            int counter = page.getInt("pageCount");
            /**
             * End Get total page size
             */

            XSSFWorkbook workbook2 = new XSSFWorkbook();
            XSSFSheet sheet2 = workbook2.createSheet("Category");// creating a blank sheet
            int rownum = 0;

            Row row = sheet2.createRow(rownum++);
            Cell cell = row.createCell(0);
            cell.setCellValue("commodityCategoryCode");
            cell = row.createCell(1);
            cell.setCellValue("parentCode");
            cell = row.createCell(2);
            cell.setCellValue("commodityCategoryName");
            cell = row.createCell(3);
            cell.setCellValue("commodityCategoryLevel");
            cell = row.createCell(4);
            cell.setCellValue("rate");
            cell = row.createCell(5);
            cell.setCellValue("isLeafNode");
            cell = row.createCell(6);
            cell.setCellValue("serviceMark");
            cell = row.createCell(7);
            cell.setCellValue("isZeroRate");
            cell = row.createCell(8);
            cell.setCellValue("zeroRateStartDate");
            cell = row.createCell(9);
            cell.setCellValue("zeroRateEndDate");
            cell = row.createCell(10);
            cell.setCellValue("isExempt");
            cell = row.createCell(11);
            cell.setCellValue("exemptRateStartDate");
            cell = row.createCell(12);
            cell.setCellValue("exemptRateEndDate");
            cell = row.createCell(13);
            cell.setCellValue("enableStatusCode");
            cell = row.createCell(14);
            cell.setCellValue("exclusion");

            for (int x = 1; x <= counter; x++) {
                json = "{\n"
                        + "	\"pageNo\": \"" + counter + "\",\n"
                        + "	\"pageSize\": \"99\"\n"
                        + "}";
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
                for (GoodsCommodity goodsCommodity : arList) {
                    row = sheet2.createRow(rownum++);
                    cell = row.createCell(0);
                    cell.setCellValue(goodsCommodity.getCommodityCategoryCode());
                    cell = row.createCell(1);
                    cell.setCellValue(goodsCommodity.getParentCode());
                    cell = row.createCell(2);
                    cell.setCellValue(goodsCommodity.getCommodityCategoryName());
                    cell = row.createCell(3);
                    cell.setCellValue(goodsCommodity.getCommodityCategoryLevel());
                    cell = row.createCell(4);
                    cell.setCellValue(goodsCommodity.getRate());
                    cell = row.createCell(5);
                    cell.setCellValue(goodsCommodity.getIsLeafNode());
                    cell = row.createCell(6);
                    cell.setCellValue(goodsCommodity.getServiceMark());
                    cell = row.createCell(7);
                    cell.setCellValue(goodsCommodity.getIsZeroRate());
                    cell = row.createCell(8);
                    cell.setCellValue(goodsCommodity.getZeroRateStartDate());
                    cell = row.createCell(9);
                    cell.setCellValue(goodsCommodity.getZeroRateEndDate());
                    cell = row.createCell(10);
                    cell.setCellValue(goodsCommodity.getIsExempt());
                    cell = row.createCell(11);
                    cell.setCellValue(goodsCommodity.getExemptRateStartDate());
                    cell = row.createCell(13);
                    cell.setCellValue(goodsCommodity.getEnableStatusCode());
                    cell = row.createCell(14);
                    cell.setCellValue(goodsCommodity.getExclusion());
                }

            }
            FileOutputStream out = new FileOutputStream(new File(aFileNameWithPath)); // file name with path
            workbook2.write(out);
            out.close();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void generateUNSPCexcelOnline(String aFileNameWithPath) {
        String output = "";
        String returnCode = "";
        String returnMessage = "";
        try {
            String json = "{\n"
                    + "	\"pageNo\": \"1\",\n"
                    + "	\"pageSize\": \"99\"\n"
                    + "}";
            System.out.println("JSON1:" + json);
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            //String AESpublickeystring = SecurityPKI.decrypt(new SecurityPKI().AESPublicKey(CompanySetting.getTaxIdentity(), new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value()), key);
            String AESpublickeystring = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            /**
             * Encrypt Content
             */
            String encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T124", CompanySetting.getTaxIdentity());
            System.out.println("PostData1:" + PostData);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
            System.out.println("output1:" + output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");
            returnCode = dataobject.getString("returnCode");
            returnMessage = dataobject.getString("returnMessage");
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
            if (zipCode.equals("0")) {
                DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
            } else {
                byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                DecryptedContent = SecurityPKI.AESdecrypt2(str, Base64.decodeBase64(AESpublickeystring));
            }
            /**
             * Get total page size
             */
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject page = parentbasicInformationjsonObject.getJSONObject("page");
            int counter = page.getInt("pageCount");
            /**
             * End Get total page size
             */

            XSSFWorkbook workbook2 = new XSSFWorkbook();
            XSSFSheet sheet2 = workbook2.createSheet("Category");// creating a blank sheet
            int rownum = 0;

            Row row = sheet2.createRow(rownum++);
            Cell cell = row.createCell(0);
            cell.setCellValue("commodityCategoryCode");
            cell = row.createCell(1);
            cell.setCellValue("parentCode");
            cell = row.createCell(2);
            cell.setCellValue("commodityCategoryName");
            cell = row.createCell(3);
            cell.setCellValue("commodityCategoryLevel");
            cell = row.createCell(4);
            cell.setCellValue("rate");
            cell = row.createCell(5);
            cell.setCellValue("isLeafNode");
            cell = row.createCell(6);
            cell.setCellValue("serviceMark");
            cell = row.createCell(7);
            cell.setCellValue("isZeroRate");
            cell = row.createCell(8);
            cell.setCellValue("zeroRateStartDate");
            cell = row.createCell(9);
            cell.setCellValue("zeroRateEndDate");
            cell = row.createCell(10);
            cell.setCellValue("isExempt");
            cell = row.createCell(11);
            cell.setCellValue("exemptRateStartDate");
            cell = row.createCell(12);
            cell.setCellValue("exemptRateEndDate");
            cell = row.createCell(13);
            cell.setCellValue("enableStatusCode");
            cell = row.createCell(14);
            cell.setCellValue("exclusion");

            for (int x = 1; x <= counter; x++) {
                json = "{\n"
                        + "	\"pageNo\": \"" + counter + "\",\n"
                        + "	\"pageSize\": \"99\"\n"
                        + "}";
                System.out.println("JSON2:" + json);
                PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T124", CompanySetting.getTaxIdentity());
                response = webResource.type("application/json").post(ClientResponse.class, PostData);
                output = response.getEntity(String.class);

                parentjsonObject = new JSONObject(output);
                dataobjectcontent = parentjsonObject.getJSONObject("data");
                content = dataobjectcontent.getString("content");
                /**
                 * Decrypt Response
                 */
                dataDescription = dataobjectcontent.getJSONObject("dataDescription");
                zipCode = "0";
                DecryptedContent = "";
                try {
                    zipCode = dataDescription.getString("zipCode");
                } catch (Exception e) {
                    //do nothing
                }
                if (zipCode.equals("0")) {
                    DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
                } else {
                    byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                    DecryptedContent = SecurityPKI.AESdecrypt2(str, Base64.decodeBase64(AESpublickeystring));
                }
                /**
                 * Get records
                 */
                JSONObject jSONObject = new JSONObject(DecryptedContent);
                JSONArray array = jSONObject.getJSONArray("records");
                List<GoodsCommodity> arList = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    Gson g = new Gson();
                    GoodsCommodity temp = g.fromJson(array.get(i).toString(), GoodsCommodity.class);
                    arList.add(temp);
                }
                for (GoodsCommodity goodsCommodity : arList) {
                    row = sheet2.createRow(rownum++);
                    cell = row.createCell(0);
                    cell.setCellValue(goodsCommodity.getCommodityCategoryCode());
                    cell = row.createCell(1);
                    cell.setCellValue(goodsCommodity.getParentCode());
                    cell = row.createCell(2);
                    cell.setCellValue(goodsCommodity.getCommodityCategoryName());
                    cell = row.createCell(3);
                    cell.setCellValue(goodsCommodity.getCommodityCategoryLevel());
                    cell = row.createCell(4);
                    cell.setCellValue(goodsCommodity.getRate());
                    cell = row.createCell(5);
                    cell.setCellValue(goodsCommodity.getIsLeafNode());
                    cell = row.createCell(6);
                    cell.setCellValue(goodsCommodity.getServiceMark());
                    cell = row.createCell(7);
                    cell.setCellValue(goodsCommodity.getIsZeroRate());
                    cell = row.createCell(8);
                    cell.setCellValue(goodsCommodity.getZeroRateStartDate());
                    cell = row.createCell(9);
                    cell.setCellValue(goodsCommodity.getZeroRateEndDate());
                    cell = row.createCell(10);
                    cell.setCellValue(goodsCommodity.getIsExempt());
                    cell = row.createCell(11);
                    cell.setCellValue(goodsCommodity.getExemptRateStartDate());
                    cell = row.createCell(13);
                    cell.setCellValue(goodsCommodity.getEnableStatusCode());
                    cell = row.createCell(14);
                    cell.setCellValue(goodsCommodity.getExclusion());
                }
            }
            FileOutputStream out = new FileOutputStream(new File(aFileNameWithPath)); // file name with path
            workbook2.write(out);
            out.close();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void downloadGoodsCommodities() {
        try {
            String APIMode = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_MODE").getParameter_value();
            if (APIMode.equals("OFFLINE")) {
                this.generateUNSPCOffline();
            } else {
                this.generateUNSPCOnline();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void downloadGoodsCommoditiesThread() {
        try {
            //reset total number of goods commodities to be downloaded
            totalUNSPC = 0;
            //reset total number of goods commodities already downloaded
            downloadedUNSPC = 0;
            //Runnable task = new Runnable() {
            Runnable task = () -> {
                try {
                    T124 t124 = new T124();
                    String APIMode = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_MODE").getParameter_value();
                    if (APIMode.equals("OFFLINE")) {
                        t124.generateUNSPCOffline();
                    } else {
                        t124.generateUNSPCOnline();
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

    public void generateUNSPCOffline() {
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

    public void generateUNSPCOnline() {
        String output = "";
        try {
            String json = "{\n"
                    + "	\"pageNo\": \"1\",\n"
                    + "	\"pageSize\": \"99\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            /**
             * Encrypt Content
             */
            String encryptedcontent2 = Base64.encodeBase64String(json.getBytes("UTF-8"));
            String signedcontent2 = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent2, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent2, signedcontent2, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T124", CompanySetting.getTaxIdentity());

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
                 * Encrypt Content
                 */
                encryptedcontent2 = Base64.encodeBase64String(json.getBytes("UTF-8"));
                signedcontent2 = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent2, key));
                /**
                 * Post Data
                 */
                PostData = GeneralUtilities.PostData_Online(encryptedcontent2, signedcontent2, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T124", CompanySetting.getTaxIdentity());
                response = webResource.type("application/json").post(ClientResponse.class, PostData);
                output = response.getEntity(String.class);

                parentjsonObject = new JSONObject(output);
                dataobjectcontent = parentjsonObject.getJSONObject("data");
                content = dataobjectcontent.getString("content");
                /**
                 * Decrypt Response
                 */
                dataDescription = dataobjectcontent.getJSONObject("dataDescription");
                zipCode = "0";
                DecryptedContent = "";
                try {
                    zipCode = dataDescription.getString("zipCode");
                } catch (Exception e) {
                    //do nothing
                }
                //Decode and or decompress
                if (zipCode.equals("0")) {
                    DecryptedContent = new String(Base64.decodeBase64(content));
                } else {
                    byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
                    DecryptedContent = new String(str);
                }
                /**
                 * Get records
                 */
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
                //int savedGoodsCommodity = 1;

                if (savedGoodsCommodity == 1) {
                    //set total number of goods commodities already downloaded
                    downloadedUNSPC = downloadedUNSPC + arList.size();

                    //UPDATE THE DOWNLOAD STATUS 
                    DownloadStatus downloadStatus = new DownloadStatus();
                    downloadStatus.setDownload_name("GOODS COMMODITY");
                    downloadStatus.setTotal_amount(totalUNSPC);
                    downloadStatus.setTotal_downloaded(downloadedUNSPC);
                    if (totalUNSPC == downloadedUNSPC) {
                        downloadStatus.setDownload_status(1);
                        downloadStatus.setDownload_status_msg("SUCCESS");
                    } else {
                        downloadStatus.setDownload_status(2);
                        downloadStatus.setDownload_status_msg("PROGRESS/FAILED");
                    }
                    new DownloadStatusBean().updateTotalDownloaded(downloadStatus);
                }
            }
            
            //merge Goodd commodity into item_UNSPSC
            if (totalUNSPC == downloadedUNSPC){
                new EFRIS_goods_commodityBean().mergeGoodCommodity_Item_UNSPSC();
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }
}
