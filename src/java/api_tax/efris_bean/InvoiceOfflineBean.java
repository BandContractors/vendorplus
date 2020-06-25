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
import beans.UserDetailBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import entities.Item;
import entities.Item_tax_map;
import entities.Trans;
import entities.TransItem;
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
    }

    public void submitTaxInvoiceOffline(long aTransId) {
        try {
            this.clear_all();
            this.prepareInvoice(aTransId);
            if (goodsDetails.size() > 0) {
                this.submit_invoice();
            }
            System.out.println("InvoiceNo:" + InvoiceNo);
            //update home db
            if (InvoiceNo.length() > 0) {
                new Transaction_tax_mapBean().saveTransaction_tax_map(aTransId, InvoiceNo);
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
            basicInformation.setIssuedDate(new UtilityBean().formatDateServer(trans.getTransactionDate()));
            basicInformation.setIssuedDate(new UtilityBean().formatDateTimeServer(trans.getAddDate()));
            try {
                basicInformation.setOperator(new UserDetailBean().getUserDetail(trans.getTransactionUserDetailId()).getUserName());
            } catch (Exception e) {
                basicInformation.setOperator("System");
            }

            //buyerDetails
            if (transactor.getTransactorId() > 0) {
                buyerDetails.setBuyerTin(transactor.getTaxIdentity());
                buyerDetails.setBuyerLegalName(transactor.getTransactorNames());
            } else {
                buyerDetails.setBuyerTin("");
                buyerDetails.setBuyerLegalName("");
            }
            buyerDetails.setBuyerType(Integer.toString(1));
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
                    gd.setUnitOfMeasure(itm.getUnitSymbol());
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
            /*
             if (trans.getTotalExemptVatableAmount() > 0) {
             td = new TaxDetails();
             td.setGrossAmount(Double.toString(trans.getTotalExemptVatableAmount()));
             td.setTaxRateName("EXEMPT");
             td.setTaxRate("0");
             td.setTaxAmount("0");
             taxDetails.add(td);
             }
             if (trans.getTotalZeroVatableAmount() > 0) {
             td = new TaxDetails();
             td.setGrossAmount(Double.toString(trans.getTotalZeroVatableAmount()));
             td.setTaxRateName("ZERO");
             td.setTaxRate("0");
             td.setTaxAmount("0");
             taxDetails.add(td);
             }
             */

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
            //-System.out.println(json);

            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            //WebResource webResource = client.resource("https://efristest.ura.go.ug/efrisws/ws/taapp/getInformation");
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            //String privatekey = new ExtractKeys().PrivateKey(sellerDetails.getTin(), basicInformation.getDeviceNo(), returnCode, returnMessage);
            //String sub = privatekey.substring(0, 5);
            //if (sub.equals("ERROR")) {
            //      String[] split = privatekey.split(",");
            //      returnCode = split[1];
            //     returnMessage = split[2];
            //}
            //PrivateKey key = ExtractKeys.loadPrivateKey(privatekey);
            //System.out.println(key.getAlgorithm());
            // String AESpublickeystring = ExtractKeys.decrypt(new ExtractKeys().AESPublicKey(sellerDetails.getTin(), basicInformation.getDeviceNo()), key);
            //String encryptedcontent = ExtractKeys.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            //PublicKey publickey=ExtractKeys.getKey(AESpublickeystring);
            //System.out.println(encryptedcontent);
            //String signedcontent = Base64.encodeBase64String(new ExtractKeys().sign(encryptedcontent, key));
            //String PostData = GeneralUtilities.PostData(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", basicInformation.getDeviceNo(), "T109", sellerDetails.getTin());

            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", basicInformation.getDeviceNo(), "T109", sellerDetails.getTin());
            //-System.out.println(PostData);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            String output = response.getEntity(String.class);
            //-System.out.println(output);

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
            //-System.out.println("AntiFakeCode: " + AntifakeCode);
            //-System.out.println("Invoice: " + InvoiceNo);
            //-System.out.println("-------------------------------------");
        } catch (Exception ex) {
            Logger.getLogger(InvoiceOfflineBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
