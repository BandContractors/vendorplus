/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRISInvoice;
import api_tax.efris.GeneralUtilities;
import api_tax.efris.innerclasses.BasicInformation;
import api_tax.efris.innerclasses.BuyerDetails;
import api_tax.efris.innerclasses.Extend;
import api_tax.efris.innerclasses.GoodsDetails;
import api_tax.efris.innerclasses.PayWay;
import api_tax.efris.innerclasses.SellerDetails;
import api_tax.efris.innerclasses.Summary;
import api_tax.efris.innerclasses.TaxDetails;
import beans.ItemBean;
import beans.Item_tax_mapBean;
import beans.Parameter_listBean;
import beans.TransBean;
import beans.TransItemBean;
import beans.Transaction_tax_mapBean;
import beans.TransactorBean;
import beans.UnitBean;
import beans.UserDetailBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import entities.Item;
import entities.Item_tax_map;
import entities.Trans;
import entities.TransItem;
import entities.Transaction_tax_map;
import entities.Transactor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.UtilityBean;

/**
 *
 * @author bajuna
 */
@ManagedBean
@SessionScoped
public class InvoiceOfflineBean {

    public void init() {
        if (sellerDetails == null) {
            sellerDetails = new SellerDetails();
        }
        if (basicInformation == null) {
            basicInformation = new BasicInformation();
        }
        if (buyerDetails == null) {
            buyerDetails = new BuyerDetails();
        }
        if (goodsDetails == null) {
            goodsDetails = new ArrayList<>();
        }
        if (taxDetails == null) {
            taxDetails = new ArrayList<>();
        }
        if (summary == null) {
            summary = new Summary();
        }
        if (payWay == null) {
            payWay = new ArrayList<>();
        }
        if (extend == null) {
            extend = new Extend();
        }
        if (item == null) {
            item = new GoodsDetails();
        }
        if (paymentmode == null) {
            paymentmode = new PayWay();
        }
        if (taxdetail == null) {
            taxdetail = new TaxDetails();
        }
    }
    private SellerDetails sellerDetails;
    private BasicInformation basicInformation;
    private BuyerDetails buyerDetails;
    private List<GoodsDetails> goodsDetails = new ArrayList<>();
    private List<TaxDetails> taxDetails = new ArrayList();
    private Summary summary;
    private List<PayWay> payWay = new ArrayList();
    private Extend extend;

    private GoodsDetails item;
    private TaxDetails taxdetail;
    private PayWay paymentmode;

    private String returnCode = "";
    private String returnMessage = "";
    private String AntifakeCode = "";
    private String InvoiceNo = "";
    private String VerificationCode = "";
    private String QrCode = "";

    public void clear_all() {
        sellerDetails = new SellerDetails();
        basicInformation = new BasicInformation();
        buyerDetails = new BuyerDetails();
        goodsDetails = new ArrayList<>();
        taxDetails = new ArrayList<>();
        summary = new Summary();
        payWay = new ArrayList<>();
        extend = new Extend();
        extend.setReason("");
        extend.setReasonCode("");
        item = new GoodsDetails();
        paymentmode = new PayWay();
        taxdetail = new TaxDetails();
        returnCode = "";
        returnMessage = "";
        AntifakeCode = "";
        InvoiceNo = "";
        VerificationCode = "";
        QrCode = "";
    }

    public void submitTaxInvoiceOffline(long aTransId) {
        try {
            this.clear_all();
            this.prepareInvoice(aTransId);
            if (goodsDetails.size() > 0) {
                this.submit_invoice();
            }
            //System.out.println("InvoiceNo:" + InvoiceNo);
            //update home db
            if (InvoiceNo.length() > 0) {
                new Transaction_tax_mapBean().saveTransaction_tax_map(aTransId, InvoiceNo, VerificationCode, QrCode);
            }
        } catch (Exception e) {
            System.err.println("submitTaxInvoiceOffline:" + e.getMessage());
        }
    }

    public void submitTaxInvoiceOfflineThread(long aTransId) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    submitTaxInvoiceOffline(aTransId);
                }
            };
            //Thread t = new Thread(task, "MY_THREAD");
            //t.start();
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            System.err.println("submitTaxInvoiceOfflineThread:" + e.getMessage());
        }
    }

    public void prepareInvoice(long aTransId) {
        try {
            Trans trans = new Trans();
            List<TransItem> transitems = new ArrayList<>();
            Transactor transactor = new Transactor();
            try {
                trans = new TransBean().getTrans(aTransId);
            } catch (Exception e) {
                //
            }
            try {
                transitems = new TransItemBean().getTransItemsByTransactionId(aTransId);
            } catch (Exception e) {
                //
            }
            try {
                if (trans.getBillTransactorId() > 0) {
                    transactor = new TransactorBean().getTransactor(trans.getBillTransactorId());
                }
            } catch (Exception e) {
                //
            }

            //sellerDetails
            sellerDetails.setTin(CompanySetting.getTaxIdentity());
            sellerDetails.setBusinessName(CompanySetting.getLICENSE_CLIENT_NAME());
            sellerDetails.setLegalName(new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "PAYEE_NAME").getParameter_value());
            sellerDetails.setEmailAddress(CompanySetting.getEmail());

            //basicInformation 
            basicInformation.setCurrency(trans.getCurrencyCode());
            basicInformation.setDeviceNo(new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value());
            basicInformation.setInvoiceNo(trans.getTransactionNumber());
            basicInformation.setInvoiceType(Integer.toString(1));
            basicInformation.setInvoiceKind(Integer.toString(1));
            basicInformation.setDataSource(Integer.toString(103));
            //basicInformation.setIssuedDate(new UtilityBean().formatDateServer(trans.getTransactionDate()));
            basicInformation.setIssuedDate(new UtilityBean().formatDateTimeServer(trans.getAddDate()));
            try {
                basicInformation.setOperator(new UserDetailBean().getUserDetail(trans.getTransactionUserDetailId()).getUserName());
            } catch (Exception e) {
                basicInformation.setOperator("System");
            }

            //buyerDetails
            String BuyerType = "B2C";
            if (null == transactor) {
                //do nothing
            } else {
                if (transactor.getTransactorId() > 0) {
                    if (transactor.getCategory().equals("Business")) {
                        BuyerType = "B2B";
                    } else if (transactor.getCategory().equals("Government")) {
                        BuyerType = "B2G";
                    } else {
                        BuyerType = "B2C";
                    }
                } else {
                    BuyerType = "B2C";
                }
            }
            if (BuyerType.equals("B2B") || BuyerType.equals("B2G")) {
                buyerDetails.setBuyerType(Integer.toString(1));
            } else {
                buyerDetails.setBuyerType(Integer.toString(2));
            }
            if (null == transactor) {
                buyerDetails.setBuyerLegalName("Walk-In Customer");
            } else {
                if (transactor.getTransactorId() > 0) {
                    if (transactor.getTransactorNames().length() > 0) {
                        buyerDetails.setBuyerLegalName(transactor.getTransactorNames());
                    }
                    if (transactor.getTaxIdentity().length() > 0) {
                        buyerDetails.setBuyerTin(transactor.getTaxIdentity());
                    }
                    if (transactor.getIdNumber().length() > 0) {
                        buyerDetails.setBuyerNinBrn(transactor.getIdNumber());
                    }
                } else {
                    buyerDetails.setBuyerLegalName("Walk-In Customer");
                }
            }

            //extend
            //goodsDetails
            Item itm = null;
            Item_tax_map im = null;
            int OrderNo = 0;
            for (int i = 0; i < transitems.size(); i++) {
                itm = new ItemBean().getItem(transitems.get(i).getItemId());
                im = new Item_tax_mapBean().getItem_tax_map(transitems.get(i).getItemId());
                if (null != itm && null != im) {
                    GoodsDetails gd = new GoodsDetails();
                    gd.setItem(itm.getDescription());//Hima Cement
                    gd.setItemCode(Long.toString(itm.getItemId()));//147
                    gd.setQty(Double.toString(transitems.get(i).getItemQty()));
                    //gd.setUnitOfMeasure(itm.getUnitSymbol());
                    try {
                        String UnitSymbolTax = new UnitBean().getUnit(itm.getUnitId()).getUnit_symbol_tax();
                        if (null == UnitSymbolTax) {
                            gd.setUnitOfMeasure("PCE");
                        } else {
                            gd.setUnitOfMeasure(UnitSymbolTax);
                        }
                    } catch (Exception e) {
                        gd.setUnitOfMeasure("PCE");
                    }
                    gd.setUnitPrice(Double.toString(transitems.get(i).getUnitPriceIncVat()));
                    //gd.setDiscountTotal(Double.toString(transitems.get(i).getUnitTradeDiscount()));
                    gd.setTax(Double.toString(transitems.get(i).getUnitVat()));
                    gd.setTotal(Double.toString(transitems.get(i).getAmountIncVat()));
                    Double vatPerc = transitems.get(i).getVatPerc();
                    Double tr = vatPerc / 100;
                    gd.setTaxRate(Double.toString(tr));
                    gd.setDiscountFlag(Integer.toString(2));//0=Discount amount,1=Discounted goods,2=None
                    gd.setExciseFlag(Integer.toString(2));
                    gd.setDeemedFlag(Integer.toString(2));
                    gd.setOrderNumber(Integer.toString(OrderNo));
                    gd.setCategoryId("");
                    gd.setCategoryName("");
                    gd.setGoodsCategoryId(im.getItem_code_tax());//code for Cement
                    gd.setGoodsCategoryName("");
                    //exciseRate;exciseRule;exciseTax;pack;stick;exciseUnit;exciseCurrency;exciseRateName;
                    goodsDetails.add(gd);
                    OrderNo = OrderNo + 1;
                }
            }

            //taxDetails
            TaxDetails td = null;
            if (trans.getTotalStdVatableAmount() > 0) {
                td = new TaxDetails();
                td.setGrossAmount(Double.toString(trans.getTotalStdVatableAmount() + trans.getTotalVat()));
                td.setTaxCategory("VAT");
                td.setTaxRateName("STANDARD");
                Double vatPerc = trans.getVatPerc();
                Double tr = vatPerc / 100;
                td.setTaxRate(Double.toString(tr));
                td.setTaxAmount(Double.toString(trans.getTotalVat()));
                td.setNetAmount(Double.toString(trans.getTotalStdVatableAmount()));
                taxDetails.add(td);
            }
            //summary
            summary.setGrossAmount(new UtilityBean().formatDoubleToStringPlain(trans.getGrandTotal(), trans.getCurrencyCode()));
            summary.setTaxAmount(new UtilityBean().formatDoubleToStringPlain(trans.getTotalVat(), trans.getCurrencyCode()));
            summary.setNetAmount(new UtilityBean().formatDoubleToStringPlain((trans.getGrandTotal() - trans.getTotalVat()), trans.getCurrencyCode()));
            summary.setItemCount(Integer.toString(goodsDetails.size()));
        } catch (Exception e) {
            System.err.println("prepareInvoice:" + e.getMessage());
        }
    }

    public void submit_invoice() {
        AntifakeCode = "";
        InvoiceNo = "";
        returnCode = "";
        returnMessage = "";
        try {
            EFRISInvoice eFRISInvoice = new EFRISInvoice(sellerDetails, basicInformation, buyerDetails, summary, extend);
            eFRISInvoice.setPayWay(payWay);
            eFRISInvoice.setGoodsDetails(goodsDetails);
            eFRISInvoice.setTaxDetails(taxDetails);
            Gson gson = new Gson();
            String json = gson.toJson(eFRISInvoice);
            //System.out.println("json:" + json);

            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", basicInformation.getDeviceNo(), "T109", sellerDetails.getTin());
            //System.out.println("PostData:" + PostData);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            String output = response.getEntity(String.class);
            //System.out.println("output:" + output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");
            returnCode = dataobject.getString("returnCode");
            returnMessage = dataobject.getString("returnMessage");
            //-System.out.println("-------------------------------------");
            //-System.out.println(returnCode);
            //-System.out.println(returnMessage);
            //-System.out.println("-------------------------------------");
            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            //System.out.println(AESpublickeystring);
            //String DecryptedContent = ExtractKeys.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
            String DecryptedContent = new String(Base64.decodeBase64(content));
            //-System.out.println(DecryptedContent);
            //-System.out.println("-------------------------------------");
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject databasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            AntifakeCode = databasicInformation.getString("antifakeCode");
            InvoiceNo = databasicInformation.getString("invoiceNo");
            VerificationCode = AntifakeCode;
            //System.out.println("verificationNo:" + VerificationCode);
            JSONObject summary = parentbasicInformationjsonObject.getJSONObject("summary");
            QrCode = summary.getString("qrCode");
            //System.out.println("QrCode:" + QrCode.length() + ":" + QrCode);
            //-System.out.println("AntiFakeCode: " + AntifakeCode);
            //-System.out.println("Invoice: " + InvoiceNo);
            //-System.out.println("-------------------------------------");
        } catch (Exception e) {
            Logger.getLogger(InvoiceOfflineBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void submitCreditNoteOfflineThread(long aTransId, int aTransTypeId) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    submitCreditNoteOffline(aTransId, aTransTypeId);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            System.err.println("submitCreditNoteOfflineThread:" + e.getMessage());
        }
    }

    public void submitCreditNoteOffline(long aTransId, int aTransTypeId) {
        try {
            Transaction_tax_map ttm = new Transaction_tax_mapBean().getTransaction_tax_map(aTransId, aTransTypeId);
            if (null != ttm) {
                String TaxInvoice = ttm.getTransaction_number_tax();
                String SellerTin = CompanySetting.getTaxIdentity();
                String DeviceNo = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value();
                if (TaxInvoice.length() > 0) {
                    //1. Update is_updated to 1, update_synced=0
                    ttm.setUpdate_type("Credit Note");
                    new Transaction_tax_mapBean().markTransaction_tax_mapUpdated(ttm);
                    //2. Submit
                    String ReturnMessage = this.submit_credit_note(aTransId, TaxInvoice, DeviceNo, SellerTin);
                    //3. Update update_synced=1 
                    if (ReturnMessage.equals("SUCCESS")) {
                        ttm.setUpdate_synced(1);
                        new Transaction_tax_mapBean().saveTransaction_tax_map(ttm);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("submitCreditNoteOffline:" + e.getMessage());
        }
    }

    public String submit_credit_note(long aTransId, String aTaxInvoiceNumber, String aDeviceNo, String aSellerTIN) {
        String RetMsg = "";
        try {
            Trans trans = new Trans();
            List<TransItem> transitems = new ArrayList<>();
            try {
                trans = new TransBean().getTrans(aTransId);
            } catch (Exception e) {
                //
            }
            try {
                transitems = new TransItemBean().getTransItemsByTransactionId(aTransId);
            } catch (Exception e) {
                //
            }
            String json = "{\n"
                    + "	\"invoiceNo\": \"" + aTaxInvoiceNumber + "\"\n"
                    + "}";
            //System.out.println("json1:" + json);
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", aDeviceNo, "T108", aSellerTIN);
            //System.out.println("PostData1:" + PostData);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            String output = response.getEntity(String.class);
            //System.out.println("output1:" + output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));

            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray_GoodsDetials = parentbasicInformationjsonObject.getJSONArray("goodsDetails");
            JSONArray jSONArray_TaxDetails = parentbasicInformationjsonObject.getJSONArray("taxDetails");
            JSONObject dataobject_Summary = parentbasicInformationjsonObject.getJSONObject("summary");
            JSONArray jSONArray_Payway = parentbasicInformationjsonObject.getJSONArray("payWay");
            JSONObject dataobjectbasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            JSONObject dataobjectsellerDetails = parentbasicInformationjsonObject.getJSONObject("sellerDetails");

            JSONObject jsonObj;
            for (int i = 0; i < jSONArray_GoodsDetials.length(); i++) {
                jsonObj = (JSONObject) jSONArray_GoodsDetials.get(i);
                TransItem ti = this.getTransItemFromList(jsonObj.get("itemCode").toString(), transitems);
                Double ChangedQty = ti.getItemQty() - Double.parseDouble(jsonObj.get("qty").toString());//can be + or -
                Double ChangedVatAmt = ti.getAmountIncVat() - Double.parseDouble(jsonObj.get("total").toString());//can be + or -
                Double ChangedTax = ti.getUnitVat() - Double.parseDouble(jsonObj.get("tax").toString());//can be + or -
                jsonObj.put("qty", "" + ChangedQty + "");
                jsonObj.put("total", "" + ChangedVatAmt + "");
                jsonObj.put("tax", "" + ChangedVatAmt + "");
            }
            for (int i = 0; i < jSONArray_TaxDetails.length(); i++) {
                jsonObj = (JSONObject) jSONArray_TaxDetails.get(i);
                Double ChangedNetAmount = trans.getTotalStdVatableAmount() - Double.parseDouble(jsonObj.get("netAmount").toString());
                Double ChangedTaxAmount = trans.getTotalVat() - Double.parseDouble(jsonObj.get("taxAmount").toString());
                Double ChangedGrossAmount = (trans.getTotalStdVatableAmount() + trans.getTotalVat()) - Double.parseDouble(jsonObj.get("grossAmount").toString());
                jsonObj.put("netAmount", "" + ChangedNetAmount + "");
                jsonObj.put("taxAmount", "" + ChangedTaxAmount + "");
                jsonObj.put("grossAmount", "" + ChangedGrossAmount + "");
            }

            Double ChangedGrossAmountSum = trans.getGrandTotal() - Double.parseDouble(dataobject_Summary.get("grossAmount").toString());
            Double ChangedNetAmountSum = (trans.getGrandTotal() - trans.getTotalVat()) - Double.parseDouble(dataobject_Summary.get("netAmount").toString());
            Double ChangedTaxAmountSum = trans.getTotalVat() - Double.parseDouble(dataobject_Summary.get("taxAmount").toString());
            dataobject_Summary.put("grossAmount", "" + new UtilityBean().formatDoubleToStringPlain(ChangedGrossAmountSum, trans.getCurrencyCode()) + "");
            dataobject_Summary.put("netAmount", "" + new UtilityBean().formatDoubleToStringPlain(ChangedNetAmountSum, trans.getCurrencyCode()) + "");
            dataobject_Summary.put("taxAmount", "" + new UtilityBean().formatDoubleToStringPlain(ChangedTaxAmountSum, trans.getCurrencyCode()) + "");

            json = "{\n"
                    + "	\"oriInvoiceId\": \"" + dataobjectbasicInformation.getString("invoiceId") + "\",\n"
                    + "	\"oriInvoiceNo\": \"" + dataobjectbasicInformation.getString("invoiceNo") + "\",\n"
                    + "	\"reasonCode\": \"101\",\n"
                    + "	\"reason\": \"refundreason\",\n"
                    + "	\"applicationTime\": \"" + new UtilityBean().formatDateTimeServer(trans.getEditDate()) + "\",\n"
                    + "	\"invoiceApplyCategoryCode\": \"101\",\n"
                    + "	\"currency\": \"" + dataobjectbasicInformation.getString("currency") + "\",\n"
                    + "	\"contactName\": \"1\",\n"
                    + "	\"contactMobileNum\": \"1\",\n"
                    + "	\"contactEmail\": \"\",\n"
                    + "	\"source\": \"104\",\n"
                    + "	\"remark\": \"Remarks\",\n"
                    + "	\"sellersReferenceNo\": \"" + "" + "\",\n"
                    + "	\"goodsDetails\": " + jSONArray_GoodsDetials.toString() + ",\n"
                    + "	\"taxDetails\": " + jSONArray_TaxDetails.toString() + ",\n"
                    + "	\"summary\":" + dataobject_Summary.toString() + " ,\n"
                    + "	\"payWay\":" + jSONArray_Payway.toString() + "\n"
                    + "}";
            //System.out.println("json2:" + json);
            PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", aDeviceNo, "T110", aSellerTIN);
            //System.out.println("PostData2:" + PostData);
            response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
            //System.out.println("output2:" + output);

            parentjsonObject = new JSONObject(output);
            dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            dataobjectcontent = parentjsonObject.getJSONObject("data");
            content = dataobjectcontent.getString("content");

            DecryptedContent = new String(Base64.decodeBase64(content));
            //System.out.println("DecryptedContent:" + DecryptedContent);

            RetMsg = dataobject.getString("returnMessage");
            //System.out.println("returnMessage:" + RetMsg);
        } catch (Exception e) {
            Logger.getLogger(InvoiceOfflineBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return RetMsg;
    }

    public void submitDebitNoteOffline(long aTransId, int aTransTypeId) {
        try {
            this.clear_all();
            Transaction_tax_map ttm = new Transaction_tax_mapBean().getTransaction_tax_map(aTransId, aTransTypeId);
            if (null != ttm) {
                String TaxInvoice = ttm.getTransaction_number_tax();
                String SellerTin = CompanySetting.getTaxIdentity();
                String DeviceNo = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value();
                if (TaxInvoice.length() > 0) {
                    //0. Preparations
                    this.prepareDebit_note(aTransId, TaxInvoice, DeviceNo, SellerTin);
                    //1. Update is_updated to 1, update_synced=0
                    ttm.setUpdate_type("Debit Note");
                    new Transaction_tax_mapBean().markTransaction_tax_mapUpdated(ttm);
                    //2. Submit
                    this.submit_debit_note();
                    String ReturnMsg = this.returnMessage;
                    //3. Update update_synced=1 
                    if (ReturnMsg.equals("SUCCESS")) {
                        ttm.setTransaction_number_tax_update(this.InvoiceNo);
                        ttm.setVerification_code_tax_update(this.VerificationCode);
                        ttm.setQr_code_tax_update(this.QrCode);
                        ttm.setUpdate_synced(1);
                        new Transaction_tax_mapBean().saveTransaction_tax_map(ttm);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("submitDebitNoteOffline:" + e.getMessage());
        }
    }

    public void submitDebitNoteOfflineThread(long aTransId, int aTransTypeId) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    submitDebitNoteOffline(aTransId, aTransTypeId);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            System.err.println("submitDebitNoteOfflineThread:" + e.getMessage());
        }
    }

    public void prepareDebit_note(long aTransId, String aTaxInvoiceNumber, String aDeviceNo, String aSellerTIN) {
        try {
            Trans trans = new Trans();
            List<TransItem> transitems = new ArrayList<>();
            Transactor transactor = new Transactor();
            try {
                trans = new TransBean().getTrans(aTransId);
            } catch (Exception e) {
                //
            }
            try {
                transitems = new TransItemBean().getTransItemsByTransactionId(aTransId);
            } catch (Exception e) {
                //
            }

            String json = "{\n"
                    + "	\"invoiceNo\": \"" + aTaxInvoiceNumber + "\"\n"
                    + "}";
            //System.out.println("json1:" + json);
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", aDeviceNo, "T108", aSellerTIN);
            //System.out.println("PostData1:" + PostData);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            String output = response.getEntity(String.class);
            //System.out.println("output1:" + output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));

            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray_GoodsDetials = parentbasicInformationjsonObject.getJSONArray("goodsDetails");
            JSONArray jSONArray_TaxDetails = parentbasicInformationjsonObject.getJSONArray("taxDetails");
            JSONObject dataobject_Summary = parentbasicInformationjsonObject.getJSONObject("summary");
            JSONArray jSONArray_Payway = parentbasicInformationjsonObject.getJSONArray("payWay");
            JSONObject dataobjectbasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            JSONObject dataobjectsellerDetails = parentbasicInformationjsonObject.getJSONObject("sellerDetails");
            JSONObject dataobjectbuyerDetails = parentbasicInformationjsonObject.getJSONObject("buyerDetails");

            //sellerDetails
            sellerDetails.setTin(CompanySetting.getTaxIdentity());//dataobjectsellerDetails.get("tin").toString()
            sellerDetails.setBusinessName(CompanySetting.getLICENSE_CLIENT_NAME());//dataobjectsellerDetails.get("businessName").toString()
            sellerDetails.setLegalName(new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "PAYEE_NAME").getParameter_value());//dataobjectsellerDetails.get("legalName").toString()
            sellerDetails.setEmailAddress(CompanySetting.getEmail());//dataobjectsellerDetails.get("emailAddress").toString()

            //basicInformation
            basicInformation.setOriInvoiceId(dataobjectbasicInformation.getString("invoiceId"));
            basicInformation.setCurrency(trans.getCurrencyCode());//dataobjectbasicInformation.get("currency").toString()
            basicInformation.setDeviceNo(aDeviceNo);
            basicInformation.setInvoiceNo(trans.getTransactionNumber());//dataobjectbasicInformation.get("invoiceNo").toString()
            basicInformation.setInvoiceType(Integer.toString(4));
            basicInformation.setInvoiceKind(Integer.toString(1));
            basicInformation.setDataSource(Integer.toString(103));
            basicInformation.setIssuedDate(new UtilityBean().formatDateTimeServer(trans.getEditDate()));
            try {
                basicInformation.setOperator(new UserDetailBean().getUserDetail(trans.getTransactionUserDetailId()).getUserName());
            } catch (Exception e) {
                basicInformation.setOperator("System");
            }

            //buyerDetails
            buyerDetails.setBuyerTin(dataobjectbuyerDetails.get("buyerTin").toString());//transactor.getTaxIdentity()
            buyerDetails.setBuyerLegalName(dataobjectbuyerDetails.get("buyerLegalName").toString());//transactor.getTransactorNames()
            buyerDetails.setBuyerType(Integer.toString(1));
            //extend

            //goodsDetails
            Item itm = null;
            Item_tax_map im = null;
            int OrderNo = 0;
            List<TransItem> prevTIs = this.convertJsonGoodsArrayToList(jSONArray_GoodsDetials);
            for (int i = 0; i < transitems.size(); i++) {
                itm = new ItemBean().getItem(transitems.get(i).getItemId());
                im = new Item_tax_mapBean().getItem_tax_map(transitems.get(i).getItemId());
                if (null != itm && null != im) {
                    //check if item has changed
                    TransItem prevTI = this.getTransItemFromList(Long.toString(transitems.get(i).getItemId()), prevTIs);
                    Double ChangedQty = transitems.get(i).getItemQty() - prevTI.getItemQty();//can be + or -
                    Double ChangedVatAmt = transitems.get(i).getAmountIncVat() - prevTI.getAmountIncVat();//can be + or -
                    if (ChangedQty > 0 || ChangedVatAmt > 0) {
                        GoodsDetails gd = new GoodsDetails();
                        gd.setItem(itm.getDescription());//Hima Cement
                        gd.setItemCode(Long.toString(itm.getItemId()));//147
                        gd.setQty(Double.toString(ChangedQty));//transitems.get(i).getItemQty()
                        try {
                            String UnitSymbolTax = new UnitBean().getUnit(itm.getUnitId()).getUnit_symbol_tax();
                            if (null == UnitSymbolTax) {
                                gd.setUnitOfMeasure("PCE");
                            } else {
                                gd.setUnitOfMeasure(UnitSymbolTax);
                            }
                        } catch (Exception e) {
                            gd.setUnitOfMeasure("PCE");
                        }
                        gd.setUnitPrice(Double.toString(transitems.get(i).getUnitPriceIncVat()));
                        gd.setTax(Double.toString(transitems.get(i).getUnitVat()));
                        gd.setTotal(Double.toString(ChangedVatAmt));//transitems.get(i).getAmountIncVat()
                        Double vatPerc = transitems.get(i).getVatPerc();
                        Double tr = vatPerc / 100;
                        gd.setTaxRate(Double.toString(tr));
                        gd.setDiscountFlag(Integer.toString(2));//0=Discount amount,1=Discounted goods,2=None
                        gd.setExciseFlag(Integer.toString(2));
                        gd.setDeemedFlag(Integer.toString(2));
                        gd.setOrderNumber(Integer.toString(OrderNo));
                        gd.setCategoryId("");
                        gd.setCategoryName("");
                        gd.setGoodsCategoryId(im.getItem_code_tax());//code for Cement
                        gd.setGoodsCategoryName("");
                        //exciseRate;exciseRule;exciseTax;pack;stick;exciseUnit;exciseCurrency;exciseRateName;
                        goodsDetails.add(gd);
                        OrderNo = OrderNo + 1;
                    }
                }
            }

            //taxDetails
            TaxDetails td = null;
            List<TaxDetails> prevTDs = this.convertJsonTaxDetailsArrayToList(jSONArray_TaxDetails);
            if (trans.getTotalStdVatableAmount() > 0) {
                td = new TaxDetails();
                Double ChangedGrossAmount = (trans.getTotalStdVatableAmount() + trans.getTotalVat()) - Double.parseDouble(prevTDs.get(0).getGrossAmount());
                Double ChangedNetAmount = trans.getTotalStdVatableAmount() - Double.parseDouble(prevTDs.get(0).getNetAmount());
                Double ChangedTaxAmount = trans.getTotalVat() - Double.parseDouble(prevTDs.get(0).getTaxAmount());
                td.setGrossAmount(Double.toString(ChangedGrossAmount));
                td.setTaxCategory("VAT");
                td.setTaxRateName("STANDARD");
                Double vatPerc = trans.getVatPerc();
                Double tr = vatPerc / 100;
                td.setTaxRate(Double.toString(tr));
                td.setTaxAmount(Double.toString(ChangedTaxAmount));
                td.setNetAmount(Double.toString(ChangedNetAmount));
                taxDetails.add(td);
            }
            //summary
            Double ChangedGAmount = trans.getGrandTotal() - Double.parseDouble(dataobject_Summary.get("grossAmount").toString());
            Double ChangedNAmount = (trans.getGrandTotal() - trans.getTotalVat()) - Double.parseDouble(dataobject_Summary.get("netAmount").toString());
            Double ChangedTAmount = trans.getTotalVat() - Double.parseDouble(dataobject_Summary.get("taxAmount").toString());
            summary.setGrossAmount(new UtilityBean().formatDoubleToStringPlain(ChangedGAmount, trans.getCurrencyCode()));
            summary.setTaxAmount(new UtilityBean().formatDoubleToStringPlain(ChangedTAmount, trans.getCurrencyCode()));
            summary.setNetAmount(new UtilityBean().formatDoubleToStringPlain(ChangedNAmount, trans.getCurrencyCode()));
            summary.setItemCount(Integer.toString(goodsDetails.size()));
        } catch (Exception e) {
            System.err.println("prepareDebit_note:" + e.getMessage());
        }
    }

    public void submit_debit_note() {
        AntifakeCode = "";
        InvoiceNo = "";
        returnCode = "";
        returnMessage = "";
        try {
            EFRISInvoice eFRISInvoice = new EFRISInvoice(sellerDetails, basicInformation, buyerDetails, summary, extend);
            eFRISInvoice.setPayWay(payWay);
            eFRISInvoice.setGoodsDetails(goodsDetails);
            eFRISInvoice.setTaxDetails(taxDetails);
            Gson gson = new Gson();
            String json = gson.toJson(eFRISInvoice);
            //System.out.println("json:" + json);

            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", basicInformation.getDeviceNo(), "T109", sellerDetails.getTin());
            //System.out.println("PostData:" + PostData);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            String output = response.getEntity(String.class);
            //System.out.println("output:" + output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");
            returnCode = dataobject.getString("returnCode");
            returnMessage = dataobject.getString("returnMessage");
            //-System.out.println("-------------------------------------");
            //-System.out.println(returnCode);
            //-System.out.println(returnMessage);
            //-System.out.println("-------------------------------------");
            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            //System.out.println(AESpublickeystring);
            String DecryptedContent = new String(Base64.decodeBase64(content));
            //-System.out.println(DecryptedContent);
            //-System.out.println("-------------------------------------");
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject databasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            AntifakeCode = databasicInformation.getString("antifakeCode");
            InvoiceNo = databasicInformation.getString("invoiceNo");
            VerificationCode = AntifakeCode;
            //System.out.println("verificationNo:" + VerificationCode);
            JSONObject summary = parentbasicInformationjsonObject.getJSONObject("summary");
            QrCode = summary.getString("qrCode");
            //System.out.println("QrCode:" + QrCode.length() + ":" + QrCode);
            //System.out.println("Invoice: " + InvoiceNo);
            //System.out.println("returnMessage: " + returnMessage);
        } catch (Exception e) {
            Logger.getLogger(InvoiceOfflineBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public TransItem getTransItemFromList(String aItemCode, List<TransItem> aTransItems) {
        TransItem ti = new TransItem();
        for (int i = 0; i < aTransItems.size(); i++) {
            if (aItemCode.equals(Long.toString(aTransItems.get(i).getItemId())) && aTransItems.get(i).getItem_no() == 0) {
                ti.setItemQty(aTransItems.get(i).getItemQty());
                ti.setAmountIncVat(aTransItems.get(i).getAmountIncVat());
                ti.setUnitVat(aTransItems.get(i).getUnitVat());
                aTransItems.get(i).setItem_no(1);
                break;
            }
        }
        return ti;
    }

    public List<TransItem> convertJsonGoodsArrayToList(JSONArray ajSONArray) {
        List<TransItem> tis = new ArrayList<>();
        TransItem ti = null;
        for (int i = 0; i < ajSONArray.length(); i++) {
            ti = new TransItem();
            ti.setItem_no(Integer.parseInt(ajSONArray.getJSONObject(i).get("orderNumber").toString()));
            ti.setItemId(Long.parseLong(ajSONArray.getJSONObject(i).get("itemCode").toString()));
            ti.setItemQty(Double.parseDouble(ajSONArray.getJSONObject(i).get("qty").toString()));
            ti.setAmountIncVat(Double.parseDouble(ajSONArray.getJSONObject(i).get("total").toString()));
            ti.setUnitVat(Double.parseDouble(ajSONArray.getJSONObject(i).get("tax").toString()));
            tis.add(ti);
        }
        return tis;
    }

    public List<TaxDetails> convertJsonTaxDetailsArrayToList(JSONArray aJsonArrayTaxDetails) {
        List<TaxDetails> tds = new ArrayList<>();
        TaxDetails td = null;
        for (int i = 0; i < aJsonArrayTaxDetails.length(); i++) {
            td = new TaxDetails();
            td.setGrossAmount(aJsonArrayTaxDetails.getJSONObject(i).get("grossAmount").toString());
            td.setNetAmount(aJsonArrayTaxDetails.getJSONObject(i).get("netAmount").toString());
            td.setTaxAmount(aJsonArrayTaxDetails.getJSONObject(i).get("taxAmount").toString());;
            tds.add(td);
        }
        return tds;
    }

    public PayWay getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(PayWay paymentmode) {
        this.paymentmode = paymentmode;
    }

    public TaxDetails getTaxdetail() {
        return taxdetail;
    }

    public void setTaxdetail(TaxDetails taxdetail) {
        this.taxdetail = taxdetail;
    }

    public GoodsDetails getItem() {
        return item;
    }

    public void setItem(GoodsDetails item) {
        this.item = item;
    }

    public void add_item() {
        goodsDetails.remove(item);
        goodsDetails.add(item);
        item = new GoodsDetails();
    }

    public void remove_item(GoodsDetails item) {
        goodsDetails.remove(item);
    }

    public void edit_item(GoodsDetails item) {
        this.item = item;
    }

    public void add_payway() {
        payWay.remove(paymentmode);
        payWay.add(paymentmode);
        paymentmode = new PayWay();
    }

    public void remove_payway(PayWay item) {
        payWay.remove(item);
    }

    public void edit_payway(PayWay item) {
        this.paymentmode = item;
    }

    public void add_taxdetail() {
        taxDetails.remove(taxdetail);
        taxDetails.add(taxdetail);
        taxdetail = new TaxDetails();
    }

    public void remove_taxdetail(TaxDetails taxdetail) {
        taxDetails.remove(taxdetail);
    }

    public void edit_taxdetail(TaxDetails item) {
        this.taxdetail = item;
    }

    public SellerDetails getSellerDetails() {
        return sellerDetails;
    }

    public void setSellerDetails(SellerDetails sellerDetails) {
        this.sellerDetails = sellerDetails;
    }

    public BasicInformation getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation) {
        this.basicInformation = basicInformation;
    }

    public BuyerDetails getBuyerDetails() {
        return buyerDetails;
    }

    public void setBuyerDetails(BuyerDetails buyerDetails) {
        this.buyerDetails = buyerDetails;
    }

    public List<GoodsDetails> getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(List<GoodsDetails> goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public List<TaxDetails> getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(List<TaxDetails> taxDetails) {
        this.taxDetails = taxDetails;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<PayWay> getPayWay() {
        return payWay;
    }

    public void setPayWay(List<PayWay> payWay) {
        this.payWay = payWay;
    }

    public Extend getExtend() {
        return extend;
    }

    public void setExtend(Extend extend) {
        this.extend = extend;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getAntifakeCode() {
        return AntifakeCode;
    }

    public void setAntifakeCode(String AntifakeCode) {
        this.AntifakeCode = AntifakeCode;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String InvoiceNo) {
        this.InvoiceNo = InvoiceNo;
    }

    /**
     * @return the VerificationCode
     */
    public String getVerificationCode() {
        return VerificationCode;
    }

    /**
     * @param VerificationCode the VerificationCode to set
     */
    public void setVerificationCode(String VerificationCode) {
        this.VerificationCode = VerificationCode;
    }

    /**
     * @return the QrCode
     */
    public String getQrCode() {
        return QrCode;
    }

    /**
     * @param QrCode the QrCode to set
     */
    public void setQrCode(String QrCode) {
        this.QrCode = QrCode;
    }

}
