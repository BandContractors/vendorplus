/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_invoice_detail;
import api_tax.efris.GeneralUtilities;
import api_tax.efris.innerclasses.GoodsDetails;
import api_tax.efris.innerclasses.T106;
import api_tax.efris.innerclasses.T111;
import beans.Parameter_listBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import java.io.Serializable;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.GzipUtils;
import utilities.Security;
import utilities.SecurityPKI;

/**
 *
 * @author emuwonge
 */
@ManagedBean
@SessionScoped
public class T106Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(T106Bean.class.getName());

    public void synchInvoices() {
        //String uInvoiceNo = "";
        try {
            String APIMode = new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_MODE").getParameter_value();
            //from transBean
            if (new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value().length() > 0) {
                String ReferenceNo = "";
                String SellerTIN = CompanySetting.getTaxIdentity();
                String DeviceNo = new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value();
                //get Status
                if (ReferenceNo.length() == 0) {
                    if (APIMode.equals("OFFLINE")) {
                        this.getInvoicesUploadedOffline(ReferenceNo, DeviceNo, SellerTIN);
                    } else {
                        this.getInvoicesUploadedOnline(ReferenceNo, DeviceNo, SellerTIN);
                    }
                }

                //save imported invoices
                new EFRIS_invoice_detailBean().saveImportedEFRISInvoice();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getInvoicesUploadedOffline(String aReferenceNo, String aDeviceNo, String aSellerTIN) {
        String DecryptedContent;
        String output = "";
        String startDate;
        try {
            List<EFRIS_invoice_detail> aEFRIS_invoice_detail = new EFRIS_invoice_detailBean().getEFRIS_invoice_detail_All();
            String ParameterListSartDate = new Parameter_listBean().getParameter_listByContextName("API", "API_EFRIS_SYNC_JOB_FROM_DATE").getParameter_value();
            //check if the table is empty
            if (aEFRIS_invoice_detail.size() > 0) {
                //get last add date
                int size = aEFRIS_invoice_detail.size();
                Date lastAddDate = aEFRIS_invoice_detail.get(size - 1).getAdd_date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                startDate = sdf.format(lastAddDate);
            } else if (ParameterListSartDate.length() > 0) {
                try {
                    Date date;
                    //parse the string to date to check its validity else an exception will be thrown
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    date = sdf.parse(ParameterListSartDate);
                    startDate = ParameterListSartDate;
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, e);
                    Date date = new CompanySetting().getCURRENT_SERVER_DATE();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    startDate = sdf.format(date);
                }
            } else {
                Date date = new CompanySetting().getCURRENT_SERVER_DATE();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                startDate = sdf.format(date);
            }
            String json = "{\n"
                    + " \"oriInvoiceNo\": \"\",\n"
                    + " \"invoiceNo\": \"\",\n"
                    + " \"deviceNo\": \"\",\n"
                    + " \"buyerTin\": \"\",\n"
                    + " \"buyerNinBrn\": \"\",\n"
                    + " \"buyerLegalName\": \"\",\n"
                    + " \"combineKeywords\": \"\",\n"
                    + " \"invoiceType\": \"\",\n"
                    + " \"invoiceKind\": \"1\",\n"
                    + " \"isInvalid\": \"\",\n"
                    + " \"isRefund\": \"\",\n"
                    + " \"startDate\": \"" + startDate + "\",\n"
                    + " \"endDate\": \"\",\n"
                    + " \"pageNo\": \"1\",\n"
                    + " \"pageSize\": \"10\",\n"
                    + " \"referenceNo\": \"" + aReferenceNo + "\",\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_OFFLINE").getParameter_value());
            /**
             * Post Data
             */
            //String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", aDeviceNo, "T111", aSellerTIN);
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", aDeviceNo, "T106", aSellerTIN);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            JSONObject dataDescription = dataobjectcontent.getJSONObject("dataDescription");
            String zipCode = "0";
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
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray = parentbasicInformationjsonObject.getJSONArray("records");
            JSONObject page = parentbasicInformationjsonObject.getJSONObject("page");
            int pageCount = page.getInt("pageCount");
            int pageNo = page.getInt("pageNo");
            int pageSize = page.getInt("pageSize");
            int totalSize = page.getInt("totalSize");

            if (pageCount > 1) {
                //iterate the pages in reverse order
                for (int i = pageCount; i > 0; i--) {
                    this.getInvoiceUploadedOfflineByPage(aReferenceNo, aDeviceNo, aSellerTIN, i, pageSize, startDate);
                }
            } else if (pageCount == 1) {
                for (int i = 0, size = jSONArray.length(); i < size; i++) {
                    JSONObject objectInArray = jSONArray.getJSONObject(i);
                    Gson g = new Gson();
                    T106 t106 = g.fromJson(objectInArray.toString(), T106.class);
                    //101:EFD, 102:Windows Client APP, 103:WebService API, 104:Mis, 105:Webportal, 106:Offline Mode Enabler
                    //get 101:EFD and 105:Webportal
                    if (t106.getDataSource().equals("101") || t106.getDataSource().equals("105")) {
                        String invoiceDetail = this.getTaxInvoiceDecryptedContentOffline(t106.getInvoiceNo(), aDeviceNo, aSellerTIN);
                        //qrCode and antifakeCode
                        this.setInvoiceQrAntifakeCodes(t106, invoiceDetail);
                        //get goodsDetails
                        List<GoodsDetails> goodsDetails = this.getInvoiceGoodsDetail(invoiceDetail);

                        //save invoice Details
                        int savedInvoice = new EFRIS_invoice_detailBean().insertEFRIS_invoice_detail(t106);
                        if (savedInvoice == 1) {
                            //save goodsDetails
                            int saved = new EFRIS_good_detailBean().saveEFRIS_good_detail(goodsDetails, t106.getInvoiceNo(), t106.getReferenceNo());
                        }
                    }
                }
            } else {
                //do nothing when pageCount is 0
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getInvoicesUploadedOnline(String aReferenceNo, String aDeviceNo, String aSellerTIN) {
        String DecryptedContent = "";
        String output = "";
        String startDate = "";
        try {
            List<EFRIS_invoice_detail> aEFRIS_invoice_detail = new EFRIS_invoice_detailBean().getEFRIS_invoice_detail_All();
            String ParameterListSartDate = new Parameter_listBean().getParameter_listByContextName("API", "API_EFRIS_SYNC_JOB_FROM_DATE").getParameter_value();
            //check if the table is empty
            if (aEFRIS_invoice_detail.size() > 0) {
                //get last add date
                int size = aEFRIS_invoice_detail.size();
                Date lastAddDate = aEFRIS_invoice_detail.get(size - 1).getAdd_date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                startDate = sdf.format(lastAddDate);
            } else if (ParameterListSartDate.length() > 0) {
                try {
                    Date date;
                    //parse the string to date to check its validity else an exception will be thrown
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    date = sdf.parse(ParameterListSartDate);
                    startDate = ParameterListSartDate;
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, e);
                    Date date = new CompanySetting().getCURRENT_SERVER_DATE();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    startDate = sdf.format(date);
                }
            } else {
                Date date = new CompanySetting().getCURRENT_SERVER_DATE();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                startDate = sdf.format(date);
            }
            //System.out.println("StartDate: " + startDate);
            String json = "{\n"
                    + " \"oriInvoiceNo\": \"\",\n"
                    + " \"invoiceNo\": \"\",\n"
                    + " \"deviceNo\": \"\",\n"
                    + " \"buyerTin\": \"\",\n"
                    + " \"buyerNinBrn\": \"\",\n"
                    + " \"buyerLegalName\": \"\",\n"
                    + " \"combineKeywords\": \"\",\n"
                    + " \"invoiceType\": \"\",\n"
                    //+ " \"invoiceType\": \"1\",\n"
                    + " \"invoiceKind\": \"1\",\n"
                    + " \"isInvalid\": \"\",\n"
                    + " \"isRefund\": \"\",\n"
                    + " \"startDate\": \"" + startDate + "\",\n"
                    //+ " \"startDate\": \"\",\n"
                    + " \"endDate\": \"\",\n"
                    + " \"pageNo\": \"1\",\n"
                    + " \"pageSize\": \"10\",\n"
                    + " \"referenceNo\": \"" + aReferenceNo + "\",\n"
                    //+ " \"branchName\": \"10\"\n"
                    + "}";
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
            String encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", aDeviceNo, "T106", aSellerTIN);
            //String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "", "", "9230489223014123", "123", aDeviceNo, "T106", aSellerTIN);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
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
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray = parentbasicInformationjsonObject.getJSONArray("records");
            JSONObject page = parentbasicInformationjsonObject.getJSONObject("page");
            int pageCount = page.getInt("pageCount");
            int pageNo = page.getInt("pageNo");
            int pageSize = page.getInt("pageSize");
            int totalSize = page.getInt("totalSize");

            if (pageCount > 1) {
                //iterate the pages in reverse order
                for (int i = pageCount; i > 0; i--) {
                    this.getInvoiceUploadedOnlineByPage(aReferenceNo, aDeviceNo, aSellerTIN, i, pageSize, startDate);
                }
            } else if (pageCount == 1) {
                //just get the that page
                //print invoices
                //System.out.println("DecryptedContent:" + DecryptedContent);
                //System.out.println("Invoices:" + jSONArray);
                //System.out.println("Invoice Length:" + jSONArray.length());
                //List<T106> itemslist = new ArrayList<>();
                for (int i = 0, size = jSONArray.length(); i < size; i++) {
                    JSONObject objectInArray = jSONArray.getJSONObject(i);
                    Gson g = new Gson();
                    T106 t106 = g.fromJson(objectInArray.toString(), T106.class);
                    //101:EFD, 102:Windows Client APP, 103:WebService API, 104:Mis, 105:Webportal, 106:Offline Mode Enabler
                    //get 101:EFD and 105:Webportal
                    if (t106.getDataSource().equals("101") || t106.getDataSource().equals("105")) {
                        String invoiceDetail = this.getTaxInvoiceDecryptedContentOnline(t106.getInvoiceNo(), aDeviceNo, aSellerTIN);
                        //qrCode and antifakeCode
                        this.setInvoiceQrAntifakeCodes(t106, invoiceDetail);
                        //get goodsDetails
                        List<GoodsDetails> goodsDetails = this.getInvoiceGoodsDetail(invoiceDetail);

                        //save invoice Details
                        //int savedInvoice = 0;
                        int savedInvoice = new EFRIS_invoice_detailBean().insertEFRIS_invoice_detail(t106);
                        if (savedInvoice == 1) {
                            //save goodsDetails
                            int saved = new EFRIS_good_detailBean().saveEFRIS_good_detail(goodsDetails, t106.getInvoiceNo(), t106.getReferenceNo());
                        }
                        //itemslist.add(t106);
                    }
                }
            } else {
                //do nothing when pageCount is 0
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getInvoiceUploadedOfflineByPage(String aReferenceNo, String aDeviceNo, String aSellerTIN, int pageNo, int pageSize, String startDate) {
        String DecryptedContent;
        String output = "";
        try {
            String json = "{\n"
                    + " \"oriInvoiceNo\": \"\",\n"
                    + " \"invoiceNo\": \"\",\n"
                    + " \"deviceNo\": \"\",\n"
                    + " \"buyerTin\": \"\",\n"
                    + " \"buyerNinBrn\": \"\",\n"
                    + " \"buyerLegalName\": \"\",\n"
                    + " \"combineKeywords\": \"\",\n"
                    + " \"invoiceType\": \"\",\n"
                    + " \"invoiceKind\": \"1\",\n"
                    + " \"isInvalid\": \"\",\n"
                    + " \"isRefund\": \"\",\n"
                    + " \"startDate\": \"" + startDate + "\",\n"
                    + " \"endDate\": \"\",\n"
                    + " \"pageNo\": \"" + pageNo + "\",\n"
                    + " \"pageSize\": \"" + pageSize + "\",\n"
                    + " \"referenceNo\": \"" + aReferenceNo + "\",\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_OFFLINE").getParameter_value());
            /**
             * Post Data
             */
            //String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", aDeviceNo, "T111", aSellerTIN);
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", aDeviceNo, "T106", aSellerTIN);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            JSONObject dataDescription = dataobjectcontent.getJSONObject("dataDescription");
            String zipCode = "0";
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
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray = parentbasicInformationjsonObject.getJSONArray("records");
            JSONObject page = parentbasicInformationjsonObject.getJSONObject("page");

            for (int i = 0, size = jSONArray.length(); i < size; i++) {
                JSONObject objectInArray = jSONArray.getJSONObject(i);
                Gson g = new Gson();
                T106 t106 = g.fromJson(objectInArray.toString(), T106.class);
                //101:EFD, 102:Windows Client APP, 103:WebService API, 104:Mis, 105:Webportal, 106:Offline Mode Enabler
                //get 101:EFD and 105:Webportal
                if (t106.getDataSource().equals("101") || t106.getDataSource().equals("105")) {
                    String invoiceDetail = this.getTaxInvoiceDecryptedContentOffline(t106.getInvoiceNo(), aDeviceNo, aSellerTIN);
                    //qrCode and antifakeCode
                    this.setInvoiceQrAntifakeCodes(t106, invoiceDetail);
                    //get goodsDetails
                    List<GoodsDetails> goodsDetails = this.getInvoiceGoodsDetail(invoiceDetail);

                    //save invoice Details
                    int savedInvoice = new EFRIS_invoice_detailBean().insertEFRIS_invoice_detail(t106);
                    if (savedInvoice == 1) {
                        //save goodsDetails
                        int saved = new EFRIS_good_detailBean().saveEFRIS_good_detail(goodsDetails, t106.getInvoiceNo(), t106.getReferenceNo());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getInvoiceUploadedOnlineByPage(String aReferenceNo, String aDeviceNo, String aSellerTIN, int pageNo, int pageSize, String startDate) {
        String DecryptedContent = "";
        String output = "";
        //String startDate = "";
        try {
            String json = "{\n"
                    + " \"oriInvoiceNo\": \"\",\n"
                    + " \"invoiceNo\": \"\",\n"
                    + " \"deviceNo\": \"\",\n"
                    + " \"buyerTin\": \"\",\n"
                    + " \"buyerNinBrn\": \"\",\n"
                    + " \"buyerLegalName\": \"\",\n"
                    + " \"combineKeywords\": \"\",\n"
                    + " \"invoiceType\": \"\",\n"
                    //+ " \"invoiceType\": \"1\",\n"
                    + " \"invoiceKind\": \"1\",\n"
                    + " \"isInvalid\": \"\",\n"
                    + " \"isRefund\": \"\",\n"
                    + " \"startDate\": \"" + startDate + "\",\n"
                    //+ " \"startDate\": \"\",\n"
                    + " \"endDate\": \"\",\n"
                    + " \"pageNo\": \"" + pageNo + "\",\n"
                    + " \"pageSize\": \"" + pageSize + "\",\n"
                    + " \"referenceNo\": \"" + aReferenceNo + "\",\n"
                    //+ " \"branchName\": \"10\"\n"
                    + "}";
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
            String encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", aDeviceNo, "T106", aSellerTIN);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            JSONObject dataDescription = dataobjectcontent.getJSONObject("dataDescription");
            String zipCode = "0";
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
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray = parentbasicInformationjsonObject.getJSONArray("records");
            //System.out.println("Invoices:" + jSONArray);
            JSONObject page = parentbasicInformationjsonObject.getJSONObject("page");

            for (int i = 0, size = jSONArray.length(); i < size; i++) {
                JSONObject objectInArray = jSONArray.getJSONObject(i);
                Gson g = new Gson();
                T106 t106 = g.fromJson(objectInArray.toString(), T106.class);
                //101:EFD, 102:Windows Client APP, 103:WebService API, 104:Mis, 105:Webportal, 106:Offline Mode Enabler
                //get 101:EFD and 105:Webportal
                if (t106.getDataSource().equals("101") || t106.getDataSource().equals("105")) {
                    String invoiceDetail = this.getTaxInvoiceDecryptedContentOnline(t106.getInvoiceNo(), aDeviceNo, aSellerTIN);
                    //qrCode and antifakeCode
                    this.setInvoiceQrAntifakeCodes(t106, invoiceDetail);
                    //get goodsDetails
                    List<GoodsDetails> goodsDetails = this.getInvoiceGoodsDetail(invoiceDetail);

                    //save invoice Details
                    int savedInvoice = new EFRIS_invoice_detailBean().insertEFRIS_invoice_detail(t106);
                    if (savedInvoice == 1) {
                        //save goodsDetails
                        int saved = new EFRIS_good_detailBean().saveEFRIS_good_detail(goodsDetails, t106.getInvoiceNo(), t106.getReferenceNo());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }
    
    //Get invoice details
    public String getTaxInvoiceDecryptedContentOffline(String aTaxInvoiceNumber, String aDeviceNo, String aSellerTIN) {
        String DecryptedContent = "";
        String output = "";
        try {
            String json = "{\n"
                    + "	\"invoiceNo\": \"" + aTaxInvoiceNumber + "\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", aDeviceNo, "T108", aSellerTIN);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            JSONObject dataDescription = dataobjectcontent.getJSONObject("dataDescription");
            String zipCode = "0";

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
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return DecryptedContent;
    }

    //Get invoice details
    public String getTaxInvoiceDecryptedContentOnline(String aTaxInvoiceNumber, String aDeviceNo, String aSellerTIN) {
        String DecryptedContent = "";
        String output = "";
        try {
            String json = "{\n"
                    + "	\"invoiceNo\": \"" + aTaxInvoiceNumber + "\"\n"
                    + "}";
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
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", aDeviceNo, "T108", aSellerTIN);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            JSONObject dataDescription = dataobjectcontent.getJSONObject("dataDescription");
            String zipCode = "0";
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
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return DecryptedContent;
    }

    public void setInvoiceQrAntifakeCodes(T106 aT106, String DecryptedContent) {
        try {
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject databasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            String afCode = databasicInformation.getString("antifakeCode");
            String invNo = databasicInformation.getString("invoiceNo");
            JSONObject summary = parentbasicInformationjsonObject.getJSONObject("summary");
            String qrCode = summary.getString("qrCode");

            aT106.setAntifakeCode(afCode);
            aT106.setQrCode(qrCode);

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<GoodsDetails> getInvoiceGoodsDetail(String DecryptedContent) {
        List<GoodsDetails> goodsDetails = new ArrayList<>();
        try {
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject databasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            String afCode = databasicInformation.getString("antifakeCode");
            String invNo = databasicInformation.getString("invoiceNo");
            JSONObject summary = parentbasicInformationjsonObject.getJSONObject("summary");
            String qCode = summary.getString("qrCode");

            JSONArray goodsDetailsjSONArray = parentbasicInformationjsonObject.getJSONArray("goodsDetails");

            for (int i = 0, size = goodsDetailsjSONArray.length(); i < size; i++) {
                JSONObject objectInArray = goodsDetailsjSONArray.getJSONObject(i);
                Gson g = new Gson();
                GoodsDetails gd = g.fromJson(objectInArray.toString(), GoodsDetails.class);
                goodsDetails.add(gd);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return goodsDetails;
    }

}
