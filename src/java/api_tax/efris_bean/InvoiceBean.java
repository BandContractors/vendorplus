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
import api_tax.efris.innerclasses.T108;
import api_tax.efris.innerclasses.T111;
import api_tax.efris.innerclasses.TaxDetails;
import beans.AccCurrencyBean;
import beans.Api_tax_error_logBean;
import beans.CreditDebitNoteBean;
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
import entities.Api_tax_error_log;
import entities.CompanySetting;
import entities.Item;
import entities.Item_tax_map;
import entities.Trans;
import entities.TransItem;
import entities.Transaction_tax_map;
import entities.Transactor;
import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.GzipUtils;
import utilities.UtilityBean;
import utilities.Security;
import utilities.SecurityPKI;

/**
 *
 * @author bajuna
 */
@ManagedBean
@SessionScoped
public class InvoiceBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(InvoiceBean.class.getName());

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
    private String ReferenceNo = "";

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
        ReferenceNo = "";
    }

    public void submitTaxInvoice(long aTransId) {
        try {
            this.clear_all();
            this.prepareInvoice(aTransId);
            if (goodsDetails.size() > 0) {
                String APIMode = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value();
                String ErrMsg = "";
                if (APIMode.equals("OFFLINE")) {
                    ErrMsg = this.submit_invoice_offline();
                } else {
                    ErrMsg = this.submit_invoice_online();
                }
                if (ErrMsg.length() > 0) {
                    Api_tax_error_log lg = new Api_tax_error_log();
                    lg.setTransaction_type_id(2);
                    lg.setTransaction_reason_id(0);
                    lg.setError_desc(ErrMsg);
                    lg.setName_table("transaction");
                    lg.setId_table(aTransId);
                    lg.setError_date(new CompanySetting().getCURRENT_SERVER_DATE());
                    int x = new Api_tax_error_logBean().insertApi_tax_error_log(lg);
                }
            }
            //update home db
            if (InvoiceNo.length() > 0) {
                new Transaction_tax_mapBean().saveTransaction_tax_map(aTransId, InvoiceNo, VerificationCode, QrCode);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void submitTaxInvoiceThread(long aTransId) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    submitTaxInvoice(aTransId);
                }
            };
            //Thread t = new Thread(task, "MY_THREAD");
            //t.start();
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            //System.err.println("submitTaxInvoiceOfflineThread:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void prepareInvoice(long aTransId) {
        try {
            AccCurrencyBean acb = new AccCurrencyBean();
            UtilityBean ub = new UtilityBean();
            Trans trans = new Trans();
            List<TransItem> transitems = new ArrayList<>();
            Transactor transactor = new Transactor();
            try {
                trans = new TransBean().getTrans(aTransId);
            } catch (Exception e) {
                //
            }
            try {
                //transitems = new TransItemBean().getTransItemsByTransactionId(aTransId);
                transitems = new TransItemBean().getTransItemsByTransIdNoLookup(aTransId);
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
            sellerDetails.setAddress(CompanySetting.getPhysicalAddress());
            sellerDetails.setReferenceNo(trans.getTransactionNumber());

            //basicInformation 
            basicInformation.setCurrency(trans.getCurrencyCode());
            basicInformation.setDeviceNo(new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value());
            //basicInformation.setInvoiceNo(trans.getTransactionNumber());
            //Leave empty if raising an invoice/receipt. For debit notes, populate the invoiceId that was returned against the original invoice/receipt.
            basicInformation.setInvoiceType(Integer.toString(1));//1:invoice 4: debit note
            basicInformation.setInvoiceKind(Integer.toString(1));//1:invoice 2: receipt
            String APIMode = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value();
            //101:EFD, 102:Windows Client APP, 103:WebService API, 104:Mis, 105:Webportal, 106:Offline Mode Enabler
            if (APIMode.equals("OFFLINE")) {
                basicInformation.setDataSource(Integer.toString(106));
            } else {
                basicInformation.setDataSource(Integer.toString(103));
            }
            //basicInformation.setIssuedDate(new UtilityBean().formatDateTimeServer(trans.getAddDate()));
            basicInformation.setIssuedDate(new UtilityBean().formatDateTimeServer(new CompanySetting().getCURRENT_SERVER_DATE()));
            try {
                basicInformation.setOperator(new UserDetailBean().getUserDetail(trans.getTransactionUserDetailId()).getUserName());
            } catch (Exception e) {
                basicInformation.setOperator("System");
            }

            //buyerDetails
            String BuyerType = "B2C";//0: B2B or B2G 1: B2C
            if (null == transactor) {
                //do nothing
            } else {
                if (transactor.getTransactorId() > 0) {
                    try {
                        if (transactor.getCategory().equals("Business")) {
                            BuyerType = "B2B";
                        } else if (transactor.getCategory().equals("Government")) {
                            BuyerType = "B2G";
                        } else {
                            BuyerType = "B2C";
                        }
                    } catch (Exception e) {
                        BuyerType = "B2C";
                    }
                } else {
                    BuyerType = "B2C";
                }
            }
            if (BuyerType.equals("B2B") || BuyerType.equals("B2G")) {
                buyerDetails.setBuyerType(Integer.toString(0));
            } else {
                buyerDetails.setBuyerType(Integer.toString(1));
            }
            if (null == transactor) {
                if (null != trans.getTransactor_rep() && trans.getTransactor_rep().length() > 0) {
                    buyerDetails.setBuyerLegalName(trans.getTransactor_rep());
                } else {
                    buyerDetails.setBuyerLegalName("Walk-In Customer");
                }
            } else {
                if (transactor.getTransactorId() > 0) {
                    if (transactor.getTransactorNames().length() > 0) {
                        buyerDetails.setBuyerLegalName(transactor.getTransactorNames());
                    }
                    if (transactor.getTaxIdentity().length() > 0) {
                        buyerDetails.setBuyerTin(transactor.getTaxIdentity());
                    }
                    if (transactor.getPhone().length() > 0) {
                        buyerDetails.setBuyerMobilePhone(transactor.getPhone());
                        buyerDetails.setBuyerLinePhone(transactor.getPhone());
                    }
                    if (transactor.getIdNumber().length() > 0) {
                        buyerDetails.setBuyerNinBrn(transactor.getIdNumber());
                    }
                    if (new UtilityBean().getEmptyIfNull(transactor.getTrade_name()).length() > 0) {
                        buyerDetails.setBuyerBusinessName(transactor.getTrade_name());
                    }
                    if (new UtilityBean().getEmptyIfNull(trans.getTransactor_rep()).length() > 0) {
                        if (new UtilityBean().getEmptyIfNull(buyerDetails.getBuyerBusinessName()).length() > 0) {
                            buyerDetails.setBuyerBusinessName(buyerDetails.getBuyerBusinessName() + " (" + trans.getTransactor_rep() + ")");
                        } else {
                            buyerDetails.setBuyerBusinessName(trans.getTransactor_rep());
                        }
                    }
                } else {
                    if (null != trans.getTransactor_rep() && trans.getTransactor_rep().length() > 0) {
                        buyerDetails.setBuyerLegalName(trans.getTransactor_rep());
                    } else {
                        buyerDetails.setBuyerLegalName("Walk-In Customer");
                    }
                }
            }

            //extend
            //goodsDetails
            Item itm = null;
            Item_tax_map im = null;
            int OrderNo = 0;
            Double TotalVatA = 0.0;
            Double TotalVatD = 0.0;
            Double TotalAmountIncVatA = 0.0;
            Double TotalAmountIncVatD = 0.0;
            Double TotalAmountExempt = 0.0;
            Double TotalAmountZero = 0.0;
            Double CashLoyaltyDisc = trans.getCashDiscount() + trans.getSpendPointsAmount();
            for (int i = 0; i < transitems.size(); i++) {
                String VatRated = transitems.get(i).getVatRated();
                itm = new ItemBean().getItem(transitems.get(i).getItemId());
                im = new Item_tax_mapBean().getItem_tax_mapSynced(transitems.get(i).getItemId());
                if (null != itm && null != im) {
                    GoodsDetails gd = new GoodsDetails();
                    if (VatRated.equals("DEEMED")) {
                        gd.setItem(itm.getDescription() + " " + "(Deemed)");
                    } else {
                        gd.setItem(itm.getDescription());
                    }
                    gd.setItemCode(im.getItem_id_tax());
                    gd.setQty(ub.formatDoubleToStringPlain(transitems.get(i).getItemQty(), 8));
                    try {
                        String UnitSymbolTax = new UnitBean().getUnit(transitems.get(i).getUnit_id()).getUnit_symbol_tax();
                        if (null == UnitSymbolTax) {
                            gd.setUnitOfMeasure("PCE");
                        } else {
                            gd.setUnitOfMeasure(UnitSymbolTax);
                        }
                    } catch (Exception e) {
                        gd.setUnitOfMeasure("PCE");
                    }
                    Double vatPerc = 0.0;
                    if (VatRated.equals("DEEMED")) {
                        vatPerc = trans.getVatPerc();
                    } else {
                        vatPerc = transitems.get(i).getVatPerc();
                    }
                    Double tr = vatPerc / 100;
                    if (VatRated.equals("STANDARD") || VatRated.equals("DEEMED")) {
                        //gd.setTaxRate(ub.formatDoublePlain2DP(tr));
                        gd.setTaxRate(ub.formatDoubleToStringPlain(tr, 2));
                    } else if (VatRated.equals("EXEMPT")) {
                        gd.setTaxRate("-");
                    } else if (VatRated.equals("ZERO")) {
                        gd.setTaxRate("0");
                    } else {
                        gd.setTaxRate("0");
                    }
                    //start - new calc
                    //start-for cash and loyalty discount, re-calculate
                    Double ItemCashLoyaltyDisc = 0.0;
                    if (CashLoyaltyDisc > 0) {
                        ItemCashLoyaltyDisc = CashLoyaltyDisc * (transitems.get(i).getAmountExcVat() / (trans.getSubTotal() - trans.getTotalTradeDiscount()));
                        if (VatRated.equals("STANDARD")) {
                            double vatamt = (transitems.get(i).getAmountExcVat() - ItemCashLoyaltyDisc) * tr;
                            transitems.get(i).setAmountIncVat((transitems.get(i).getAmountExcVat() - ItemCashLoyaltyDisc) + vatamt);
                        } else if (VatRated.equals("DEEMED")) {
                            double vatamt = (transitems.get(i).getAmountExcVat() - ItemCashLoyaltyDisc) * tr;
                            transitems.get(i).setAmountIncVat((transitems.get(i).getAmountExcVat() - ItemCashLoyaltyDisc) + vatamt);
                        } else {
                            transitems.get(i).setAmountIncVat(transitems.get(i).getAmountExcVat() - ItemCashLoyaltyDisc);
                        }
                    }
                    //end-for cash and loyalty discount, re-calculate
                    Double Qty = transitems.get(i).getItemQty();
                    //formulae UnitPriceIncVat=AmountIncVat/Qty
                    //first put back the VAT that was removed from SM
                    if (VatRated.equals("DEEMED")) {
                        double ExcludedVat = (vatPerc / 100) * transitems.get(i).getAmountIncVat();
                        transitems.get(i).setAmountIncVat(transitems.get(i).getAmountIncVat() + ExcludedVat);
                    }
                    Double UnitPriceIncVat = transitems.get(i).getAmountIncVat() / Qty;
                    Double UnitPriceIncVatRd = acb.roundDoubleToXDps(UnitPriceIncVat, 8);
                    Double AmountIncVat = UnitPriceIncVatRd * Qty;
                    Double AmountIncVatRd = acb.roundDoubleToXDps(AmountIncVat, 2);
                    //formulae 
                    Double UnitPriceExcVat = UnitPriceIncVatRd / (1 + tr);
                    Double UnitVat = UnitPriceIncVatRd - UnitPriceExcVat;
                    Double TaxAmount = UnitVat * Qty;
                    Double TaxAmountRd = acb.roundDoubleToXDps(TaxAmount, 2);
                    //assign
                    gd.setTotal(ub.formatDoubleToStringPlain(AmountIncVatRd, 2));
                    gd.setUnitPrice(ub.formatDoubleToStringPlain(UnitPriceIncVatRd, 2));
                    gd.setTax(ub.formatDoubleToStringPlain(TaxAmountRd, 2));
                    if (VatRated.equals("STANDARD")) {
                        TotalVatA = TotalVatA + TaxAmountRd;
                        TotalAmountIncVatA = TotalAmountIncVatA + AmountIncVatRd;
                    } else if (VatRated.equals("DEEMED")) {
                        TotalVatD = TotalVatD + TaxAmountRd;
                        TotalAmountIncVatD = TotalAmountIncVatD + AmountIncVatRd;
                    } else if (VatRated.equals("EXEMPT")) {
                        TotalAmountExempt = TotalAmountExempt + AmountIncVatRd;
                    } else if (VatRated.equals("ZERO")) {
                        TotalAmountZero = TotalAmountZero + AmountIncVatRd;
                    }
                    //end - new calc
                    gd.setDiscountFlag(Integer.toString(2));//0=Discount amount,1=Discounted goods,2=None
                    gd.setExciseFlag(Integer.toString(2));
                    if (VatRated.equals("DEEMED")) {
                        gd.setDeemedFlag(Integer.toString(1));
                    } else {
                        gd.setDeemedFlag(Integer.toString(2));
                    }
                    gd.setOrderNumber(Integer.toString(OrderNo));
                    gd.setCategoryId("");
                    gd.setCategoryName("");
                    gd.setGoodsCategoryId(im.getItem_code_tax());//code for Cement
                    gd.setGoodsCategoryName("");
                    goodsDetails.add(gd);
                    OrderNo = OrderNo + 1;
                }
            }
            Double TotalVatAR = acb.roundDoubleToXDps(TotalVatA, 2);
            Double TotalVatDR = acb.roundDoubleToXDps(TotalVatD, 2);
            Double TotalAmountIncVatAR = acb.roundDoubleToXDps(TotalAmountIncVatA, 2);
            Double TotalAmountIncVatDR = acb.roundDoubleToXDps(TotalAmountIncVatD, 2);
            Double TotalAmountExemptR = acb.roundDoubleToXDps(TotalAmountExempt, 2);
            Double TotalAmountZeroR = acb.roundDoubleToXDps(TotalAmountZero, 2);
            /*
             TaxDetails
             Tax Category: Excise Duty, Standard, Deemed, Zero, Exempt
             A– Standard (18%). B-Zero (0%). C- Exempt (-). D-Deemed (18%). E-Excise Duty (as per excise duty rates).
             */
            TaxDetails td = null;
            //Standard
            if (TotalVatA > 0) {
                td = new TaxDetails();
                //td.setGrossAmount(ub.formatDoublePlain2DP(TotalAmountIncVatAR));
                td.setGrossAmount(ub.formatDoubleToStringPlain(TotalAmountIncVatAR, 2));
                td.setTaxCategoryCode("01");
                td.setTaxCategory("Standard");
                td.setTaxRateName("VAT-Standard");//free entry
                Double vatPerc = trans.getVatPerc();
                Double tr = vatPerc / 100;
                td.setTaxRate(ub.formatDoubleToStringPlain(tr, 2));
                td.setTaxAmount(ub.formatDoubleToStringPlain(TotalVatAR, 2));
                Double NetAmountAR = acb.roundDoubleToXDps((TotalAmountIncVatAR - TotalVatAR), 2);
                td.setNetAmount(ub.formatDoubleToStringPlain(NetAmountAR, 2));
                taxDetails.add(td);
            }
            //Deemed
            if (TotalVatD > 0) {
                td = new TaxDetails();
                td.setGrossAmount(ub.formatDoubleToStringPlain(TotalAmountIncVatDR, 2));
                td.setTaxCategoryCode("04");
                td.setTaxCategory("Deemed");
                td.setTaxRateName("VAT-Deemed");//free entry
                Double vatPerc = trans.getVatPerc();
                Double tr = vatPerc / 100;
                td.setTaxRate(ub.formatDoubleToStringPlain(tr, 2));
                td.setTaxAmount(ub.formatDoubleToStringPlain(TotalVatDR, 2));
                Double NetAmountDR = acb.roundDoubleToXDps((TotalAmountIncVatDR - TotalVatDR), 2);
                td.setNetAmount(ub.formatDoubleToStringPlain(NetAmountDR, 2));
                taxDetails.add(td);
            }
            //Exempt
            if (TotalAmountExempt > 0) {
                td = new TaxDetails();
                td.setGrossAmount(ub.formatDoubleToStringPlain(TotalAmountExemptR, 2));
                td.setTaxCategoryCode("03");
                td.setTaxCategory("Exempt");
                td.setTaxRateName("VAT-Exempt");//free entry
                td.setTaxRate("-");
                td.setTaxAmount("0");
                td.setNetAmount(ub.formatDoubleToStringPlain(TotalAmountExemptR, 2));
                taxDetails.add(td);
            }
            //Zero
            if (TotalAmountZero > 0) {
                td = new TaxDetails();
                td.setGrossAmount(ub.formatDoubleToStringPlain(TotalAmountZeroR, 2));
                td.setTaxCategoryCode("02");
                td.setTaxCategory("Zero");
                td.setTaxRateName("VAT-Zero");//free entry
                td.setTaxRate("0");
                td.setTaxAmount("0");
                td.setNetAmount(ub.formatDoubleToStringPlain(TotalAmountZeroR, 2));
                taxDetails.add(td);
            }
            //summary
            Double GrossAmountSummaryR = (TotalAmountIncVatAR + TotalAmountIncVatDR + TotalAmountExemptR + TotalAmountZeroR) - TotalVatDR;
            summary.setGrossAmount(ub.formatDoubleToStringPlain(acb.roundDoubleToXDps(GrossAmountSummaryR, 2), 2));
            summary.setTaxAmount(ub.formatDoubleToStringPlain(acb.roundDoubleToXDps(TotalVatAR, 2), 2));
            Double NetAmountSummary = GrossAmountSummaryR - TotalVatAR;
            summary.setNetAmount(ub.formatDoubleToStringPlain(acb.roundDoubleToXDps(NetAmountSummary, 2), 2));
            summary.setItemCount(Integer.toString(goodsDetails.size()));
            summary.setModeCode("1");
            if (null != trans.getTermsConditions()) {
                if (trans.getTermsConditions().length() > 0) {
                    summary.setRemarks(trans.getTermsConditions());
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public String submit_invoice_offline() {
        String ErrorMessage = "";
        AntifakeCode = "";
        InvoiceNo = "";
        returnCode = "";
        returnMessage = "";
        String output = "";
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
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
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
            //System.out.println("Invoice: " + InvoiceNo);
            //-System.out.println("-------------------------------------");
        } catch (Exception e) {
            ErrorMessage = returnCode + ":" + returnMessage;
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return ErrorMessage;
    }

    public String submit_invoice_online() {
        String ErrorMessage = "";
        AntifakeCode = "";
        InvoiceNo = "";
        returnCode = "";
        returnMessage = "";
        String output = "";
        try {
            EFRISInvoice eFRISInvoice = new EFRISInvoice(sellerDetails, basicInformation, buyerDetails, summary, extend);
            eFRISInvoice.setPayWay(payWay);
            eFRISInvoice.setGoodsDetails(goodsDetails);
            eFRISInvoice.setTaxDetails(taxDetails);
            Gson gson = new Gson();
            String json = gson.toJson(eFRISInvoice);
            //System.out.println("json:" + json);

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
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", basicInformation.getDeviceNo(), "T109", sellerDetails.getTin());
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
            //System.out.println("PostData:" + PostData);
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
            /**
             * Decrypt Response
             */
            //String DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
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
            //System.out.println("Invoice: " + InvoiceNo);
            //-System.out.println("-------------------------------------");
        } catch (Exception e) {
            ErrorMessage = returnCode + ":" + returnMessage;
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return ErrorMessage;
    }

    public void submitCreditNoteThread(long aTransId, int aTransTypeId) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    submitCreditNote(aTransId, aTransTypeId);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            //System.err.println("submitCreditNoteThread:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void submitCreditNote(long aTransId, int aTransTypeId) {
        try {
            this.clear_all();
            Trans CreditNoteTrans = new CreditDebitNoteBean().getTrans_cr_dr_note(aTransId);
            Transaction_tax_map InvoiceTaxMap = new Transaction_tax_mapBean().getTransaction_tax_map(CreditNoteTrans.getTransactionRef(), 2);
            if (null != InvoiceTaxMap) {
                String TaxInvoice = InvoiceTaxMap.getTransaction_number_tax();
                String SellerTin = CompanySetting.getTaxIdentity();
                String DeviceNo = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value();
                if (TaxInvoice.length() > 0) {
                    //1. Submit to URA
                    String ReturnMessage = "";
                    String APIMode = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value();
                    if (APIMode.equals("OFFLINE")) {
                        ReturnMessage = this.submit_credit_note_offline(aTransId, TaxInvoice, DeviceNo, SellerTin);
                    } else {
                        ReturnMessage = this.submit_credit_note_online(aTransId, TaxInvoice, DeviceNo, SellerTin);
                    }
                    //2. Insert Tax Map for credit note
                    if (ReturnMessage.equals("SUCCESS")) {
                        new Transaction_tax_mapBean().saveTransaction_tax_map_cr_dr_note(CreditNoteTrans, InvoiceNo, VerificationCode, QrCode, ReferenceNo);
                        this.updateCreditNote(ReferenceNo, DeviceNo, SellerTin);
                    } else {
                        Api_tax_error_log lg = new Api_tax_error_log();
                        lg.setTransaction_type_id(aTransTypeId);
                        lg.setTransaction_reason_id(0);
                        lg.setError_desc(ReturnMessage);
                        lg.setName_table("transaction_cr_dr_note");
                        lg.setId_table(aTransId);
                        lg.setError_date(new CompanySetting().getCURRENT_SERVER_DATE());
                        int x = new Api_tax_error_logBean().insertApi_tax_error_log(lg);
                    }
                }
            }
        } catch (Exception e) {
            //System.err.println("submitCreditNote:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void updateCreditNote(String aReferenceNo, String aDeviceNo, String aSellerTIN) {
        String uInvoiceNo = "";
        try {
            String APIMode = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value();
            //get Status
            if (aReferenceNo.length() > 0) {
                if (APIMode.equals("OFFLINE")) {
                    uInvoiceNo = this.getApprovalStatusOffline(aReferenceNo, aDeviceNo, aSellerTIN);
                } else {
                    uInvoiceNo = this.getApprovalStatusOnline(aReferenceNo, aDeviceNo, aSellerTIN);
                }
            }
            //get invoice detail
            if (uInvoiceNo.length() > 0) {
                String DecryptedContent = "";
                if (APIMode.equals("OFFLINE")) {
                    DecryptedContent = this.getTaxInvoiceDecryptedContentOffline(uInvoiceNo, aDeviceNo, aSellerTIN);
                } else {
                    DecryptedContent = this.getTaxInvoiceDecryptedContentOnline(uInvoiceNo, aDeviceNo, aSellerTIN);
                }
                if (DecryptedContent.length() > 0) {
                    T108 uInvoiceDtl = this.getInvoiceDetail(DecryptedContent);
                    if (uInvoiceDtl.getInvoiceNo().length() > 0) {
                        //update local db with approved credit note detail
                        new Transaction_tax_mapBean().updateCreditNoteByRefNo(uInvoiceDtl.getInvoiceNo(), uInvoiceDtl.getVerificationCode(), uInvoiceDtl.getQrCode(), aReferenceNo);
                    }
                }
            }
        } catch (Exception e) {
            //System.out.println("updateCreditNote:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
    }

    public String submit_credit_note_offline(long aTransId, String aTaxInvoiceNumber, String aDeviceNo, String aSellerTIN) {
        String RetMsg = "";
        String output = "";
        try {
            Trans trans = new Trans();
            List<TransItem> transitems = new ArrayList<>();
            try {
                trans = new CreditDebitNoteBean().getTrans_cr_dr_note(aTransId);
            } catch (Exception e) {
                //
            }
            try {
                transitems = new CreditDebitNoteBean().getTransItemsByTransactionId_cr_dr_note(aTransId);
            } catch (Exception e) {
                //
            }
            String json;
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData;
            ClientResponse response;
            //String output;
            JSONObject parentjsonObject;
            JSONObject dataobject;
            JSONObject dataobjectcontent;
            String content;
            JSONObject dataDescription;
            String zipCode;
            String DecryptedContent;
            //get previoud submitted invoice
            DecryptedContent = this.getTaxInvoiceDecryptedContentOffline(aTaxInvoiceNumber, aDeviceNo, aSellerTIN);
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray_GoodsDetials = parentbasicInformationjsonObject.getJSONArray("goodsDetails");
            JSONArray jSONArray_TaxDetails = parentbasicInformationjsonObject.getJSONArray("taxDetails");
            JSONObject dataobject_Summary = parentbasicInformationjsonObject.getJSONObject("summary");
            JSONArray jSONArray_Payway = parentbasicInformationjsonObject.getJSONArray("payWay");
            JSONObject dataobjectbasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            JSONObject dataobjectsellerDetails = parentbasicInformationjsonObject.getJSONObject("sellerDetails");
            JSONArray jSONArray_GoodsDetialsNew = new JSONArray();
            JSONArray jSONArray_TaxDetailsNew = new JSONArray();
            //prepare current note to submit
            this.prepare_credit_note(trans, transitems, jSONArray_GoodsDetials, jSONArray_GoodsDetialsNew, jSONArray_TaxDetails, jSONArray_TaxDetailsNew, dataobject_Summary);
            //prepare jason
            json = "{\n"
                    + "	\"oriInvoiceId\": \"" + dataobjectbasicInformation.getString("invoiceId") + "\",\n"
                    + "	\"oriInvoiceNo\": \"" + dataobjectbasicInformation.getString("invoiceNo") + "\",\n"
                    + "	\"reasonCode\": \"101\",\n"
                    + "	\"reason\": \"refundreason\",\n"
                    + "	\"applicationTime\": \"" + new UtilityBean().formatDateTimeServer(new CompanySetting().getCURRENT_SERVER_DATE()) + "\",\n"
                    + "	\"invoiceApplyCategoryCode\": \"101\",\n"
                    + "	\"currency\": \"" + dataobjectbasicInformation.getString("currency") + "\",\n"
                    + "	\"contactName\": \"1\",\n"
                    + "	\"contactMobileNum\": \"1\",\n"
                    + "	\"contactEmail\": \"\",\n"
                    + "	\"source\": \"104\",\n"
                    + "	\"remark\": \"Remarks\",\n"
                    + "	\"sellersReferenceNo\": \"" + "" + "\",\n"
                    + "	\"goodsDetails\": " + jSONArray_GoodsDetialsNew.toString() + ",\n"
                    + "	\"taxDetails\": " + jSONArray_TaxDetailsNew.toString() + ",\n"
                    + "	\"summary\":" + dataobject_Summary.toString() + " ,\n"
                    + "	\"payWay\":" + jSONArray_Payway.toString() + "\n"
                    + "}";
            //System.out.println("json:" + json);
            PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", aDeviceNo, "T110", aSellerTIN);
            //System.out.println("PostData:" + PostData);
            response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
            //System.out.println("output:" + output);

            parentjsonObject = new JSONObject(output);
            dataobject = parentjsonObject.getJSONObject("returnStateInfo");

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
            parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            AntifakeCode = "";
            VerificationCode = AntifakeCode;
            InvoiceNo = "";
            QrCode = "";
            ReferenceNo = parentbasicInformationjsonObject.getString("referenceNo");
            RetMsg = dataobject.getString("returnMessage");
            //System.out.println("returnMessage:" + RetMsg);
        } catch (Exception e) {
            //Logger.getLogger(InvoiceBean.class.getName()).log(Level.SEVERE, null, e);
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return RetMsg;
    }

    public String submit_credit_note_online(long aTransId, String aTaxInvoiceNumber, String aDeviceNo, String aSellerTIN) {
        String RetMsg = "";
        String output = "";
        try {
            Trans trans = new Trans();
            List<TransItem> transitems = new ArrayList<>();
            try {
                trans = new CreditDebitNoteBean().getTrans_cr_dr_note(aTransId);
            } catch (Exception e) {
                //
            }
            try {
                transitems = new CreditDebitNoteBean().getTransItemsByTransactionId_cr_dr_note(aTransId);
            } catch (Exception e) {
                //
            }
            String json;
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_ONLINE").getParameter_value());
            String PostData;
            ClientResponse response;
            //String output;
            JSONObject parentjsonObject;
            JSONObject dataobject;
            JSONObject dataobjectcontent;
            String content;
            JSONObject dataDescription;
            String zipCode;
            String DecryptedContent;
            //get previoud submitted invoice
            DecryptedContent = this.getTaxInvoiceDecryptedContentOnline(aTaxInvoiceNumber, aDeviceNo, aSellerTIN);
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray_GoodsDetials = parentbasicInformationjsonObject.getJSONArray("goodsDetails");
            JSONArray jSONArray_TaxDetails = parentbasicInformationjsonObject.getJSONArray("taxDetails");
            JSONObject dataobject_Summary = parentbasicInformationjsonObject.getJSONObject("summary");
            JSONArray jSONArray_Payway = parentbasicInformationjsonObject.getJSONArray("payWay");
            JSONObject dataobjectbasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            JSONObject dataobjectsellerDetails = parentbasicInformationjsonObject.getJSONObject("sellerDetails");
            JSONArray jSONArray_GoodsDetialsNew = new JSONArray();
            JSONArray jSONArray_TaxDetailsNew = new JSONArray();
            //prepare current note to submit
            this.prepare_credit_note(trans, transitems, jSONArray_GoodsDetials, jSONArray_GoodsDetialsNew, jSONArray_TaxDetails, jSONArray_TaxDetailsNew, dataobject_Summary);
            //prepare jason
            json = "{\n"
                    + "	\"oriInvoiceId\": \"" + dataobjectbasicInformation.getString("invoiceId") + "\",\n"
                    + "	\"oriInvoiceNo\": \"" + dataobjectbasicInformation.getString("invoiceNo") + "\",\n"
                    + "	\"reasonCode\": \"101\",\n"
                    + "	\"reason\": \"refundreason\",\n"
                    + "	\"applicationTime\": \"" + new UtilityBean().formatDateTimeServer(trans.getAddDate()) + "\",\n"
                    + "	\"invoiceApplyCategoryCode\": \"101\",\n"
                    + "	\"currency\": \"" + dataobjectbasicInformation.getString("currency") + "\",\n"
                    + "	\"contactName\": \"1\",\n"
                    + "	\"contactMobileNum\": \"1\",\n"
                    + "	\"contactEmail\": \"\",\n"
                    + "	\"source\": \"104\",\n"
                    + "	\"remark\": \"Remarks\",\n"
                    + "	\"sellersReferenceNo\": \"" + "" + "\",\n"
                    + "	\"goodsDetails\": " + jSONArray_GoodsDetialsNew.toString() + ",\n"
                    + "	\"taxDetails\": " + jSONArray_TaxDetailsNew.toString() + ",\n"
                    + "	\"summary\":" + dataobject_Summary.toString() + " ,\n"
                    + "	\"payWay\":" + jSONArray_Payway.toString() + "\n"
                    + "}";
            //System.out.println("json2:" + json);
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
            PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", aDeviceNo, "T110", aSellerTIN);
            //System.out.println("PostData:" + PostData);
            response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
            //System.out.println("output:" + output);

            parentjsonObject = new JSONObject(output);
            dataobject = parentjsonObject.getJSONObject("returnStateInfo");

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
            //System.out.println(DecryptedContent);
            parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            AntifakeCode = "";
            VerificationCode = AntifakeCode;
            InvoiceNo = "";
            QrCode = "";
            ReferenceNo = parentbasicInformationjsonObject.getString("referenceNo");
            RetMsg = dataobject.getString("returnMessage");
            //System.out.println("returnMessage:" + RetMsg);
        } catch (Exception e) {
            //Logger.getLogger(InvoiceBean.class.getName()).log(Level.SEVERE, null, e);
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return RetMsg;
    }

    public void prepare_credit_note(Trans aTrans, List<TransItem> aTransItems, JSONArray jSONArray_GoodsDetials, JSONArray jSONArray_GoodsDetialsNew, JSONArray jSONArray_TaxDetails, JSONArray jSONArray_TaxDetailsNew, JSONObject dataobject_Summary) {
        try {
            AccCurrencyBean acb = new AccCurrencyBean();
            UtilityBean ub = new UtilityBean();
            JSONObject jsonObj;
            int itemcount = 0;
            Double TotalVatA = 0.0;
            Double TotalVatD = 0.0;
            Double TotalAmountIncVatA = 0.0;
            Double TotalAmountIncVatD = 0.0;
            Double TotalAmountExempt = 0.0;
            Double TotalAmountZero = 0.0;
            Double CashLoyaltyDisc = aTrans.getCashDiscount() + aTrans.getSpendPointsAmount();
            for (int i = 0; i < jSONArray_GoodsDetials.length(); i++) {
                jsonObj = (JSONObject) jSONArray_GoodsDetials.get(i);
                TransItem ti = this.getTransItemFromList(jsonObj.get("itemCode").toString(), aTransItems);
                Double ChangedQty = ti.getItemQty();
                String VatRated = ti.getVatRated();
                Double vatPerc = 0.0;
                if (VatRated.equals("DEEMED")) {
                    vatPerc = aTrans.getVatPerc();
                } else {
                    vatPerc = ti.getVatPerc();
                }
                Double tr = vatPerc / 100;
                //start-for cash and loyalty discount, re-calculate
                Double ItemCashLoyaltyDisc = 0.0;
                if (CashLoyaltyDisc != 0) {
                    ItemCashLoyaltyDisc = CashLoyaltyDisc * (ti.getAmountExcVat() / (aTrans.getSubTotal() - aTrans.getTotalTradeDiscount()));
                    if (VatRated.equals("STANDARD")) {
                        double vatamt = (ti.getAmountExcVat() - ItemCashLoyaltyDisc) * tr;
                        ti.setAmountIncVat((ti.getAmountExcVat() - ItemCashLoyaltyDisc) + vatamt);
                    } else if (VatRated.equals("DEEMED")) {
                        double vatamt = (ti.getAmountExcVat() - ItemCashLoyaltyDisc) * tr;
                        ti.setAmountIncVat((ti.getAmountExcVat() - ItemCashLoyaltyDisc) + vatamt);
                    } else {
                        ti.setAmountIncVat(ti.getAmountExcVat() - ItemCashLoyaltyDisc);
                    }
                }
                //end-for cash and loyalty discount, re-calculate
                //first put back the VAT that was removed from SM
                if (VatRated.equals("DEEMED")) {
                    double ExcludedVat = (vatPerc / 100) * ti.getAmountIncVat();
                    ti.setAmountIncVat(ti.getAmountIncVat() + ExcludedVat);
                }
                Double ChangedAmt = ti.getAmountIncVat();
                if (ChangedQty < 0) {
                    //make the negative postive for calculation purposes
                    ChangedQty = (-1 * ChangedQty);
                    ChangedAmt = (-1 * ChangedAmt);
                    //start - new calc
                    Double Qty = ChangedQty;
                    Double UnitPriceIncVat = ChangedAmt / Qty;
                    //Double UnitPriceIncVatRd = acb.roundAmountMinTwoDps(aTrans.getCurrencyCode(), UnitPriceIncVat);
                    Double UnitPriceIncVatRd = acb.roundDoubleToXDps(UnitPriceIncVat, 8);
                    Double AmountIncVat = UnitPriceIncVatRd * Qty;
                    //Double AmountIncVatRd = acb.roundAmountMinTwoDps(aTrans.getCurrencyCode(), AmountIncVat);
                    Double AmountIncVatRd = acb.roundDoubleToXDps(AmountIncVat, 2);
                    //formulae 
                    Double UnitPriceExcVat = UnitPriceIncVatRd / (1 + tr);
                    Double UnitVat = UnitPriceIncVatRd - UnitPriceExcVat;
                    Double TaxAmount = UnitVat * Qty;
                    //Double TaxAmountRd = acb.roundAmountMinTwoDps(aTrans.getCurrencyCode(), TaxAmount);
                    Double TaxAmountRd = acb.roundDoubleToXDps(TaxAmount, 2);
                    //assign
                    //gd.setUnitPrice(ub.formatDoublePlain2DP(UnitPriceIncVatRd));
                    if (VatRated.equals("STANDARD")) {
                        TotalVatA = TotalVatA + TaxAmountRd;
                        TotalAmountIncVatA = TotalAmountIncVatA + AmountIncVatRd;
                        //put the negative back at crediting
                        jsonObj.put("qty", "" + ub.formatDoubleToStringPlain((-1 * Qty), 8) + "");
                        jsonObj.put("total", "" + ub.formatDoubleToStringPlain((-1 * AmountIncVatRd), 2));
                        jsonObj.put("tax", "" + ub.formatDoubleToStringPlain((-1 * TaxAmountRd), 2));
                    } else if (VatRated.equals("DEEMED")) {
                        TotalVatD = TotalVatD + TaxAmountRd;
                        TotalAmountIncVatD = TotalAmountIncVatD + AmountIncVatRd;
                        //put the negative back at crediting
                        jsonObj.put("qty", "" + (-1 * Qty) + "");
                        jsonObj.put("total", "" + ub.formatDoubleToStringPlain((-1 * AmountIncVatRd), 2));
                        jsonObj.put("tax", "" + ub.formatDoubleToStringPlain((-1 * TaxAmountRd), 2));
                    } else if (VatRated.equals("EXEMPT")) {
                        TotalAmountExempt = TotalAmountExempt + AmountIncVatRd;
                        //put the negative back at crediting
                        jsonObj.put("qty", "" + (-1 * Qty) + "");
                        jsonObj.put("total", "" + ub.formatDoubleToStringPlain((-1 * AmountIncVatRd), 2));
                        jsonObj.put("tax", "0");
                    } else if (VatRated.equals("ZERO")) {
                        TotalAmountZero = TotalAmountZero + AmountIncVatRd;
                        jsonObj.put("qty", "" + (-1 * Qty) + "");
                        jsonObj.put("total", "" + ub.formatDoubleToStringPlain((-1 * AmountIncVatRd), 2));
                        jsonObj.put("tax", "0");
                    }
                    //end - new calc
                    jSONArray_GoodsDetialsNew.put(jsonObj);
                    itemcount = itemcount + 1;
                }
            }
            /*
             Double TotalVatAR = acb.roundAmountMinTwoDps(aTrans.getCurrencyCode(), TotalVatA);
             Double TotalVatDR = acb.roundAmountMinTwoDps(aTrans.getCurrencyCode(), TotalVatD);
             Double TotalAmountIncVatAR = acb.roundAmountMinTwoDps(aTrans.getCurrencyCode(), TotalAmountIncVatA);
             Double TotalAmountIncVatDR = acb.roundAmountMinTwoDps(aTrans.getCurrencyCode(), TotalAmountIncVatD);
             Double TotalAmountExemptR = acb.roundAmountMinTwoDps(aTrans.getCurrencyCode(), TotalAmountExempt);
             Double TotalAmountZeroR = acb.roundAmountMinTwoDps(aTrans.getCurrencyCode(), TotalAmountZero);
             */
            Double TotalVatAR = acb.roundDoubleToXDps(TotalVatA, 2);
            Double TotalVatDR = acb.roundDoubleToXDps(TotalVatD, 2);
            Double TotalAmountIncVatAR = acb.roundDoubleToXDps(TotalAmountIncVatA, 2);
            Double TotalAmountIncVatDR = acb.roundDoubleToXDps(TotalAmountIncVatD, 2);
            Double TotalAmountExemptR = acb.roundDoubleToXDps(TotalAmountExempt, 2);
            Double TotalAmountZeroR = acb.roundDoubleToXDps(TotalAmountZero, 2);
            //tax details
            for (int i = 0; i < jSONArray_TaxDetails.length(); i++) {
                jsonObj = (JSONObject) jSONArray_TaxDetails.get(i);
                if (TotalVatA > 0 && (jsonObj.get("taxCategoryCode").toString().equals("01") || jsonObj.get("taxCategory").toString().toUpperCase().contains("STANDARD") || Double.parseDouble(jsonObj.get("taxAmount").toString()) > 0)) {
                    Double NetAmountAR = acb.roundDoubleToXDps((TotalAmountIncVatAR - TotalVatAR), 2);
                    jsonObj.put("netAmount", "" + ub.formatDoubleToStringPlain((-1 * NetAmountAR), 2));
                    jsonObj.put("taxAmount", "" + ub.formatDoubleToStringPlain((-1 * TotalVatAR), 2));
                    jsonObj.put("grossAmount", "" + ub.formatDoubleToStringPlain((-1 * TotalAmountIncVatAR), 2));
                    jSONArray_TaxDetailsNew.put(jsonObj);
                } else if (TotalVatD > 0 && (jsonObj.get("taxCategoryCode").toString().equals("04") || jsonObj.get("taxCategory").toString().toUpperCase().contains("DEEMED") || Double.parseDouble(jsonObj.get("taxAmount").toString()) > 0)) {
                    Double NetAmountDR = acb.roundDoubleToXDps((TotalAmountIncVatDR - TotalVatDR), 2);
                    jsonObj.put("netAmount", "" + ub.formatDoubleToStringPlain((-1 * NetAmountDR), 2));
                    jsonObj.put("taxAmount", "" + ub.formatDoubleToStringPlain((-1 * TotalVatDR), 2));
                    jsonObj.put("grossAmount", "" + ub.formatDoubleToStringPlain((-1 * TotalAmountIncVatDR), 2));
                    jSONArray_TaxDetailsNew.put(jsonObj);
                } else if (TotalAmountExempt > 0 && (jsonObj.get("taxCategoryCode").toString().equals("03") || jsonObj.get("taxCategory").toString().toUpperCase().contains("EXEMPT"))) {
                    jsonObj.put("netAmount", "" + ub.formatDoubleToStringPlain((-1 * TotalAmountExemptR), 2));
                    jsonObj.put("taxAmount", "0");
                    jsonObj.put("grossAmount", "" + ub.formatDoubleToStringPlain((-1 * TotalAmountExemptR), 2));
                    jSONArray_TaxDetailsNew.put(jsonObj);
                } else if (TotalAmountZero > 0 && (jsonObj.get("taxCategoryCode").toString().equals("02") || jsonObj.get("taxCategory").toString().toUpperCase().contains("ZERO"))) {
                    jsonObj.put("netAmount", "" + ub.formatDoubleToStringPlain((-1 * TotalAmountZeroR), 2));
                    jsonObj.put("taxAmount", "0");
                    jsonObj.put("grossAmount", "" + ub.formatDoubleToStringPlain((-1 * TotalAmountZeroR), 2));
                    jSONArray_TaxDetailsNew.put(jsonObj);
                }
            }
            Double GrossAmountSummaryR = (TotalAmountIncVatAR + TotalAmountIncVatDR + TotalAmountExemptR + TotalAmountZeroR) - TotalVatDR;
            Double NetAmountSummary = GrossAmountSummaryR - TotalVatAR;
            dataobject_Summary.put("grossAmount", "" + ub.formatDoubleToStringPlain((-1 * GrossAmountSummaryR), 2));
            dataobject_Summary.put("netAmount", "" + ub.formatDoubleToStringPlain((-1 * NetAmountSummary), 2));
            dataobject_Summary.put("taxAmount", "" + ub.formatDoubleToStringPlain((-1 * TotalVatAR), 2));
            dataobject_Summary.put("itemCount", "" + itemcount + "");
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void submitDebitNote(long aTransId, int aTransTypeId) {
        try {
            this.clear_all();
            Trans DebitNoteTrans = new CreditDebitNoteBean().getTrans_cr_dr_note(aTransId);
            Transaction_tax_map InvoiceTaxMap = new Transaction_tax_mapBean().getTransaction_tax_map(DebitNoteTrans.getTransactionRef(), 2);
            if (null != InvoiceTaxMap) {
                String TaxInvoice = InvoiceTaxMap.getTransaction_number_tax();
                String SellerTin = CompanySetting.getTaxIdentity();
                String DeviceNo = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value();
                if (TaxInvoice.length() > 0) {
                    //0. Preparations
                    String APIMode = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value();
                    this.prepareDebit_note(aTransId, TaxInvoice, DeviceNo, SellerTin);
                    //2. Submit to Tax Body
                    if (APIMode.equals("OFFLINE")) {
                        this.submit_debit_note_offline();
                    } else {
                        this.submit_debit_note_online();
                    }
                    String ReturnMsg = this.returnMessage;
                    //3. Insert Tax Map for debit note
                    if (ReturnMsg.equals("SUCCESS")) {
                        new Transaction_tax_mapBean().saveTransaction_tax_map_cr_dr_note(DebitNoteTrans, InvoiceNo, VerificationCode, QrCode, ReferenceNo);
                    } else {
                        Api_tax_error_log lg = new Api_tax_error_log();
                        lg.setTransaction_type_id(aTransTypeId);
                        lg.setTransaction_reason_id(0);
                        lg.setError_desc(ReturnMsg);
                        lg.setName_table("transaction_cr_dr_note");
                        lg.setId_table(aTransId);
                        lg.setError_date(new CompanySetting().getCURRENT_SERVER_DATE());
                        int x = new Api_tax_error_logBean().insertApi_tax_error_log(lg);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void submitDebitNoteThread(long aTransId, int aTransTypeId) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    submitDebitNote(aTransId, aTransTypeId);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            //System.err.println("submitDebitNoteThread:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
    }

    public String getTaxInvoiceDecryptedContentOffline(String aTaxInvoiceNumber, String aDeviceNo, String aSellerTIN) {
        String DecryptedContent = "";
        String output = "";
        try {
            String json = "{\n"
                    + "	\"invoiceNo\": \"" + aTaxInvoiceNumber + "\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
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
            //System.out.println("getTaxInvoiceDetailOffline:" + e.getMessage());
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return DecryptedContent;
    }

    public String getTaxInvoiceDecryptedContentOnline(String aTaxInvoiceNumber, String aDeviceNo, String aSellerTIN) {
        String DecryptedContent = "";
        String output = "";
        try {
            String json = "{\n"
                    + "	\"invoiceNo\": \"" + aTaxInvoiceNumber + "\"\n"
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
            //System.out.println("getTaxInvoiceDetailOffline:" + e.getMessage());
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return DecryptedContent;
    }

    public void prepareDebit_note(long aTransId, String aTaxInvoiceNumber, String aDeviceNo, String aSellerTIN) {
        try {
            Trans trans = new Trans();
            List<TransItem> transitems = new ArrayList<>();
            Transactor transactor = new Transactor();
            AccCurrencyBean acb = new AccCurrencyBean();
            UtilityBean ub = new UtilityBean();
            try {
                trans = new CreditDebitNoteBean().getTrans_cr_dr_note(aTransId);
            } catch (Exception e) {
                //
            }
            try {
                transitems = new CreditDebitNoteBean().getTransItemsByTransactionId_cr_dr_note(aTransId);
            } catch (Exception e) {
                //
            }
            String DecryptedContent = "";
            String APIMode = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value();
            if (APIMode.equals("OFFLINE")) {
                DecryptedContent = this.getTaxInvoiceDecryptedContentOffline(aTaxInvoiceNumber, aDeviceNo, aSellerTIN);
            } else {
                DecryptedContent = this.getTaxInvoiceDecryptedContentOnline(aTaxInvoiceNumber, aDeviceNo, aSellerTIN);
            }
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
            sellerDetails.setReferenceNo(trans.getTransactionNumber());

            //basicInformation
            basicInformation.setOriInvoiceId(dataobjectbasicInformation.getString("invoiceId"));
            basicInformation.setCurrency(trans.getCurrencyCode());//dataobjectbasicInformation.get("currency").toString()
            basicInformation.setDeviceNo(aDeviceNo);
            //basicInformation.setInvoiceNo(trans.getTransactionNumber()); //Leave empty if raising an invoice/receipt. For debit notes, populate the invoiceId that was returned against the original invoice/receipt.
            basicInformation.setInvoiceType(Integer.toString(4));//1:invoice 4: debit note
            basicInformation.setInvoiceKind(Integer.toString(1));//1:invoice 2: receipt
            //101:EFD, 102:Windows Client APP, 103:WebService API, 104:Mis, 105:Webportal, 106:Offline Mode Enabler
            if (APIMode.equals("OFFLINE")) {
                basicInformation.setDataSource(Integer.toString(106));
            } else {
                basicInformation.setDataSource(Integer.toString(103));
            }
            //basicInformation.setIssuedDate(new UtilityBean().formatDateTimeServer(trans.getAddDate()));
            basicInformation.setIssuedDate(new UtilityBean().formatDateTimeServer(new CompanySetting().getCURRENT_SERVER_DATE()));
            try {
                basicInformation.setOperator(new UserDetailBean().getUserDetail(trans.getTransactionUserDetailId()).getUserName());
            } catch (Exception e) {
                basicInformation.setOperator("System");
            }

            //buyerDetails
            try {
                buyerDetails.setBuyerTin(dataobjectbuyerDetails.get("buyerTin").toString());
            } catch (Exception e) {
                //do nothing
            }
            try {
                buyerDetails.setBuyerLegalName(dataobjectbuyerDetails.get("buyerLegalName").toString());
            } catch (Exception e) {
                //do nothing
            }
            buyerDetails.setBuyerType(dataobjectbuyerDetails.get("buyerType").toString());
            try {
                buyerDetails.setBuyerMobilePhone(dataobjectbuyerDetails.get("buyerMobilePhone").toString());
            } catch (Exception e) {
                //do nothing
            }
            try {
                buyerDetails.setBuyerLinePhone(dataobjectbuyerDetails.get("buyerLinePhone").toString());
            } catch (Exception e) {
                //do nothing
            }
            try {
                buyerDetails.setBuyerNinBrn(dataobjectbuyerDetails.get("buyerNinBrn").toString());
            } catch (Exception e) {
                //do nothing
            }
            //extend
            //goodsDetails
            int OrderNo = 0;
            Double TotalVatA = 0.0;
            Double TotalVatD = 0.0;
            Double TotalAmountIncVatA = 0.0;
            Double TotalAmountIncVatD = 0.0;
            Double TotalAmountExempt = 0.0;
            Double TotalAmountZero = 0.0;
            JSONObject jsonObj;
            Double CashLoyaltyDisc = trans.getCashDiscount() + trans.getSpendPointsAmount();
            for (int i = 0; i < jSONArray_GoodsDetials.length(); i++) {
                jsonObj = (JSONObject) jSONArray_GoodsDetials.get(i);
                TransItem ti = this.getTransItemFromList(jsonObj.get("itemCode").toString(), transitems);
                String VatRated = ti.getVatRated();
                //Double vatPerc = ti.getVatPerc();
                Double vatPerc = 0.0;
                if (VatRated.equals("DEEMED")) {
                    vatPerc = trans.getVatPerc();
                } else {
                    vatPerc = ti.getVatPerc();
                }
                Double tr = vatPerc / 100;
                Double ChangedQty = ti.getItemQty();
                //start-for cash and loyalty discount, re-calculate
                Double ItemCashLoyaltyDisc = 0.0;
                if (CashLoyaltyDisc != 0) {
                    //ItemCashLoyaltyDisc = CashLoyaltyDisc * (ti.getAmountExcVat() / trans.getSubTotal());
                    ItemCashLoyaltyDisc = CashLoyaltyDisc * (ti.getAmountExcVat() / (trans.getSubTotal() - trans.getTotalTradeDiscount()));
                    if (VatRated.equals("STANDARD")) {
                        double vatamt = 0;
                        vatamt = (ti.getAmountExcVat() - ItemCashLoyaltyDisc) * tr;
                        ti.setAmountIncVat((ti.getAmountExcVat() - ItemCashLoyaltyDisc) + vatamt);
                    } else if (VatRated.equals("DEEMED")) {
                        double vatamt = 0;
                        vatamt = (ti.getAmountExcVat() - ItemCashLoyaltyDisc) * tr;
                        ti.setAmountIncVat((ti.getAmountExcVat() - ItemCashLoyaltyDisc) + vatamt);
                    } else {
                        ti.setAmountIncVat(ti.getAmountExcVat() - ItemCashLoyaltyDisc);
                    }
                }
                //end-for cash and loyalty discount, re-calculate
                //first put back the VAT that was removed from SM
                if (VatRated.equals("DEEMED")) {
                    double ExcludedVat = (vatPerc / 100) * ti.getAmountIncVat();
                    ti.setAmountIncVat(ti.getAmountIncVat() + ExcludedVat);
                }
                Double ChangedAmt = ti.getAmountIncVat();
                if (ChangedQty > 0) {
                    GoodsDetails gd = new GoodsDetails();
                    gd.setItem(jsonObj.get("item").toString());//Desciption:Hima Cement
                    gd.setItemCode(jsonObj.get("itemCode").toString());//ItemIdTax:147
                    gd.setQty(ub.formatDoubleToStringPlain(ChangedQty, 8));
                    gd.setUnitOfMeasure(jsonObj.get("unitOfMeasure").toString());
                    //gd.setTotal(ub.formatDoublePlain2DP(ChangedAmt));
                    if (VatRated.equals("STANDARD") || VatRated.equals("DEEMED")) {
                        gd.setTaxRate(ub.formatDoubleToStringPlain(tr, 2));
                    } else if (VatRated.equals("EXEMPT")) {
                        gd.setTaxRate("-");
                    } else if (VatRated.equals("ZERO")) {
                        gd.setTaxRate("0");
                    } else {
                        gd.setTaxRate("0");
                    }
                    //start - new calc
                    Double Qty = ChangedQty;
                    //formulae UnitPriceIncVat=AmountIncVat/Qty
                    Double UnitPriceIncVat = ChangedAmt / Qty;
                    Double UnitPriceIncVatRd = acb.roundDoubleToXDps(UnitPriceIncVat, 8);
                    Double AmountIncVat = UnitPriceIncVatRd * Qty;
                    Double AmountIncVatRd = acb.roundDoubleToXDps(AmountIncVat, 2);
                    //formulae 
                    Double UnitPriceExcVat = UnitPriceIncVatRd / (1 + tr);
                    Double UnitVat = UnitPriceIncVatRd - UnitPriceExcVat;
                    Double TaxAmount = UnitVat * Qty;
                    Double TaxAmountRd = acb.roundDoubleToXDps(TaxAmount, 2);
                    //assign
                    gd.setTotal(ub.formatDoubleToStringPlain(AmountIncVatRd, 2));
                    gd.setUnitPrice(ub.formatDoubleToStringPlain(UnitPriceIncVatRd, 8));
                    gd.setTax(ub.formatDoubleToStringPlain(TaxAmountRd, 2));
                    if (VatRated.equals("STANDARD")) {
                        TotalVatA = TotalVatA + TaxAmountRd;
                        TotalAmountIncVatA = TotalAmountIncVatA + AmountIncVatRd;
                    } else if (VatRated.equals("DEEMED")) {
                        TotalVatD = TotalVatD + TaxAmountRd;
                        TotalAmountIncVatD = TotalAmountIncVatD + AmountIncVatRd;
                    } else if (VatRated.equals("EXEMPT")) {
                        TotalAmountExempt = TotalAmountExempt + AmountIncVatRd;
                    } else if (VatRated.equals("ZERO")) {
                        TotalAmountZero = TotalAmountZero + AmountIncVatRd;
                    }
                    //end - new calc
                    gd.setDiscountFlag(Integer.toString(2));//0=Discount amount,1=Discounted goods,2=None
                    gd.setExciseFlag(Integer.toString(2));
                    if (VatRated.equals("DEEMED")) {
                        gd.setDeemedFlag(Integer.toString(1));
                    } else {
                        gd.setDeemedFlag(Integer.toString(2));
                    }
                    gd.setOrderNumber(Integer.toString(OrderNo));
                    gd.setCategoryId("");
                    gd.setCategoryName("");
                    gd.setGoodsCategoryId(jsonObj.get("goodsCategoryId").toString());//UNSPC for Cement
                    gd.setGoodsCategoryName("");
                    goodsDetails.add(gd);
                    OrderNo = OrderNo + 1;
                }
            }
            Double TotalVatAR = acb.roundDoubleToXDps(TotalVatA, 2);
            Double TotalVatDR = acb.roundDoubleToXDps(TotalVatD, 2);
            Double TotalAmountIncVatAR = acb.roundDoubleToXDps(TotalAmountIncVatA, 2);
            Double TotalAmountIncVatDR = acb.roundDoubleToXDps(TotalAmountIncVatD, 2);
            Double TotalAmountExemptR = acb.roundDoubleToXDps(TotalAmountExempt, 2);
            Double TotalAmountZeroR = acb.roundDoubleToXDps(TotalAmountZero, 2);
            /*
             TaxDetails
             Tax Category: Excise Duty, Standard, Deemed, Zero, Exempt
             A– Standard (18%). B-Zero (0%). C- Exempt (-). D-Deemed (18%). E-Excise Duty (as per excise duty rates).
             */
            TaxDetails td = null;
            //Standard
            if (TotalVatA > 0) {
                td = new TaxDetails();
                td.setGrossAmount(ub.formatDoubleToStringPlain(TotalAmountIncVatAR, 2));
                td.setTaxCategoryCode("01");
                td.setTaxCategory("Standard");
                td.setTaxRateName("VAT-Standard");//free entry
                Double vatPerc = trans.getVatPerc();
                Double tr = vatPerc / 100;
                td.setTaxRate(ub.formatDoublePlain2DP(tr));
                td.setTaxAmount(ub.formatDoubleToStringPlain(TotalVatAR, 2));
                Double NetAmountAR = acb.roundDoubleToXDps((TotalAmountIncVatAR - TotalVatAR), 2);
                td.setNetAmount(ub.formatDoubleToStringPlain(NetAmountAR, 2));
                taxDetails.add(td);
            }
            //Deemed
            if (TotalVatD > 0) {
                td = new TaxDetails();
                td.setGrossAmount(ub.formatDoubleToStringPlain(TotalAmountIncVatDR, 2));
                td.setTaxCategoryCode("04");
                td.setTaxCategory("Deemed");
                td.setTaxRateName("VAT-Deemed");//free entry
                Double vatPerc = trans.getVatPerc();
                Double tr = vatPerc / 100;
                td.setTaxRate(ub.formatDoubleToStringPlain(tr, 2));
                td.setTaxAmount(ub.formatDoubleToStringPlain(TotalVatDR, 2));
                Double NetAmountDR = acb.roundDoubleToXDps((TotalAmountIncVatDR - TotalVatDR), 2);
                td.setNetAmount(ub.formatDoubleToStringPlain(NetAmountDR, 2));
                taxDetails.add(td);
            }
            //Exempt
            if (TotalAmountExempt > 0) {
                td = new TaxDetails();
                td.setGrossAmount(ub.formatDoubleToStringPlain(TotalAmountExemptR, 2));
                td.setTaxCategoryCode("03");
                td.setTaxCategory("Exempt");
                td.setTaxRateName("VAT-Exempt");//free entry
                td.setTaxRate("-");
                td.setTaxAmount("0");
                td.setNetAmount(ub.formatDoubleToStringPlain(TotalAmountExemptR, 2));
                taxDetails.add(td);
            }
            //Zero
            if (TotalAmountZero > 0) {
                td = new TaxDetails();
                td.setGrossAmount(ub.formatDoubleToStringPlain(TotalAmountZeroR, 2));
                td.setTaxCategoryCode("02");
                td.setTaxCategory("Zero");
                td.setTaxRateName("VAT-Zero");//free entry
                td.setTaxRate("0");
                td.setTaxAmount("0");
                td.setNetAmount(ub.formatDoubleToStringPlain(TotalAmountZeroR, 2));
                taxDetails.add(td);
            }
            //summary
            Double GrossAmountSummaryR = (TotalAmountIncVatAR + TotalAmountIncVatDR + TotalAmountExemptR + TotalAmountZeroR) - TotalVatDR;
            summary.setGrossAmount(ub.formatDoubleToStringPlain(GrossAmountSummaryR, 2));
            summary.setTaxAmount(ub.formatDoubleToStringPlain(TotalVatAR, 2));
            Double NetAmountSummary = GrossAmountSummaryR - TotalVatAR;
            summary.setNetAmount(ub.formatDoubleToStringPlain(NetAmountSummary, 2));
            summary.setItemCount(Integer.toString(goodsDetails.size()));
            summary.setModeCode("1");
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void submit_debit_note_offline() {
        String output = "";
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
            //System.out.println("PostData_Online:" + PostData_Online);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
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
            //System.out.println(DecryptedContent);
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject databasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            try {
                AntifakeCode = databasicInformation.getString("antifakeCode");
            } catch (Exception e) {
                AntifakeCode = "";
            }
            VerificationCode = AntifakeCode;
            try {
                InvoiceNo = databasicInformation.getString("invoiceNo");
            } catch (Exception e) {
                InvoiceNo = "";
            }
            try {
                JSONObject sellerDetails = parentbasicInformationjsonObject.getJSONObject("sellerDetails");
                ReferenceNo = sellerDetails.getString("referenceNo");
            } catch (Exception e) {
                ReferenceNo = "";
            }
            try {
                JSONObject summary = parentbasicInformationjsonObject.getJSONObject("summary");
                QrCode = summary.getString("qrCode");
            } catch (Exception e) {
                QrCode = "";
            }
        } catch (Exception e) {
            //Logger.getLogger(InvoiceBean.class.getName()).log(Level.SEVERE, null, e);
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void submit_debit_note_online() {
        String output = "";
        try {
            EFRISInvoice eFRISInvoice = new EFRISInvoice(sellerDetails, basicInformation, buyerDetails, summary, extend);
            eFRISInvoice.setPayWay(payWay);
            eFRISInvoice.setGoodsDetails(goodsDetails);
            eFRISInvoice.setTaxDetails(taxDetails);
            Gson gson = new Gson();
            String json = gson.toJson(eFRISInvoice);
            //System.out.println("json:" + json);

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
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", basicInformation.getDeviceNo(), "T109", sellerDetails.getTin());
            //System.out.println("PostData:" + PostData);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
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

            //System.out.println(DecryptedContent);
            //-System.out.println("-------------------------------------");
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject databasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            try {
                AntifakeCode = databasicInformation.getString("antifakeCode");
            } catch (Exception e) {
                AntifakeCode = "";
            }
            VerificationCode = AntifakeCode;
            try {
                InvoiceNo = databasicInformation.getString("invoiceNo");
            } catch (Exception e) {
                InvoiceNo = "";
            }
            try {
                JSONObject sellerDetails = parentbasicInformationjsonObject.getJSONObject("sellerDetails");
                ReferenceNo = sellerDetails.getString("referenceNo");
            } catch (Exception e) {
                ReferenceNo = "";
            }
            try {
                JSONObject summary = parentbasicInformationjsonObject.getJSONObject("summary");
                QrCode = summary.getString("qrCode");
            } catch (Exception e) {
                QrCode = "";
            }
        } catch (Exception e) {
            //Logger.getLogger(InvoiceBean.class.getName()).log(Level.SEVERE, null, e);
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
    }

    public TransItem getTransItemFromList(String aItemCode, List<TransItem> aTransItems) {
        TransItem ti = new TransItem();
        for (int i = 0; i < aTransItems.size(); i++) {
            long ItemId = 0;
            try {
                ItemId = new Item_tax_mapBean().getItem_tax_mapByIdTax(aItemCode).getItem_id();
            } catch (Exception e) {
            }
            if (ItemId == aTransItems.get(i).getItemId() && aTransItems.get(i).getItem_no() == 0) {
                ti.setItemQty(aTransItems.get(i).getItemQty());
                ti.setAmountIncVat(aTransItems.get(i).getAmountIncVat());
                ti.setAmountExcVat(aTransItems.get(i).getAmountExcVat());
                ti.setUnitVat(aTransItems.get(i).getUnitVat());
                ti.setVatPerc(aTransItems.get(i).getVatPerc());
                ti.setVatRated(aTransItems.get(i).getVatRated());
                aTransItems.get(i).setItem_no(1);
                break;
            }
        }
        return ti;
    }

    public List<TransItem> convertJsonGoodsArrayToList_Del(JSONArray ajSONArray) {
        List<TransItem> tis = new ArrayList<>();
        TransItem ti = null;
        for (int i = 0; i < ajSONArray.length(); i++) {
            ti = new TransItem();
            //ti.setItem_no(Integer.parseInt(ajSONArray.getJSONObject(i).get("orderNumber").toString()));
            ti.setItem_no(0);
            //needs to be set from code
            //ti.setItemId(Long.parseLong(ajSONArray.getJSONObject(i).get("itemCode").toString()));
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

    public String getApprovalStatusOffline(String aReferenceNo, String aDeviceNo, String aSellerTIN) {
        String InvoiceNo = "";
        String DecryptedContent = "";
        String output = "";
        try {
            String json = "{\n"
                    + " \"referenceNo\": \"" + aReferenceNo + "\",\n"
                    + " \"oriInvoiceNo\": \"\",\n"
                    + " \"invoiceNo\": \"\",\n"
                    + " \"combineKeywords\": \"\",\n"
                    + " \"approveStatus\": \"\",\n"
                    + " \"queryType\": \"1\",\n"
                    + " \"invoiceApplyCategoryCode\": \"\",\n"
                    + " \"startDate\": \"\",\n"
                    + " \"endDate\": \"\",\n"
                    + " \"pageNo\": \"1\",\n"
                    + " \"pageSize\": \"10\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", aDeviceNo, "T111", aSellerTIN);
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
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray = parentbasicInformationjsonObject.getJSONArray("records");
            List<T111> itemslist = new ArrayList<>();
            for (int i = 0, size = jSONArray.length(); i < size; i++) {
                JSONObject objectInArray = jSONArray.getJSONObject(i);
                Gson g = new Gson();
                T111 t111 = g.fromJson(objectInArray.toString(), T111.class);
                itemslist.add(t111);
            }
            if (itemslist.isEmpty()) {
                InvoiceNo = "";
            } else {
                InvoiceNo = itemslist.get(0).getInvoiceNo();
            }
            if (null == InvoiceNo) {
                InvoiceNo = "";
            }
        } catch (Exception e) {
            InvoiceNo = "";
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return InvoiceNo;
    }

    public String getApprovalStatusOnline(String aReferenceNo, String aDeviceNo, String aSellerTIN) {
        String InvoiceNo = "";
        String DecryptedContent = "";
        String output = "";
        try {
            String json = "{\n"
                    + " \"referenceNo\": \"" + aReferenceNo + "\",\n"
                    + " \"oriInvoiceNo\": \"\",\n"
                    + " \"invoiceNo\": \"\",\n"
                    + " \"combineKeywords\": \"\",\n"
                    + " \"approveStatus\": \"\",\n"
                    + " \"queryType\": \"1\",\n"
                    + " \"invoiceApplyCategoryCode\": \"\",\n"
                    + " \"startDate\": \"\",\n"
                    + " \"endDate\": \"\",\n"
                    + " \"pageNo\": \"1\",\n"
                    + " \"pageSize\": \"10\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            String AESpublickeystring = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            /**
             * Encrypt Content
             */
            String encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", aDeviceNo, "T111", aSellerTIN);
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
            List<T111> itemslist = new ArrayList<>();
            for (int i = 0, size = jSONArray.length(); i < size; i++) {
                JSONObject objectInArray = jSONArray.getJSONObject(i);
                Gson g = new Gson();
                T111 t111 = g.fromJson(objectInArray.toString(), T111.class);
                itemslist.add(t111);
            }
            if (itemslist.isEmpty()) {
                //do nothing
            } else {
                InvoiceNo = itemslist.get(0).getInvoiceNo();
            }
            if (null == InvoiceNo) {
                InvoiceNo = "";
            }
        } catch (Exception e) {
            InvoiceNo = "";
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return InvoiceNo;
    }

    public T108 getInvoiceDetail(String DecryptedContent) {
        T108 InvoiceDtl = new T108();
        try {
            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject databasicInformation = parentbasicInformationjsonObject.getJSONObject("basicInformation");
            String afCode = databasicInformation.getString("antifakeCode");
            String invNo = databasicInformation.getString("invoiceNo");
            JSONObject summary = parentbasicInformationjsonObject.getJSONObject("summary");
            String qCode = summary.getString("qrCode");
            InvoiceDtl.setAntifakeCode(afCode);
            InvoiceDtl.setVerificationCode(afCode);
            InvoiceDtl.setInvoiceNo(invNo);
            InvoiceDtl.setQrCode(qCode);
        } catch (Exception e) {
            //System.out.println("getInvoiceDetail:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
        return InvoiceDtl;
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

    /**
     * @return the ReferenceNo
     */
    public String getReferenceNo() {
        return ReferenceNo;
    }

    /**
     * @param ReferenceNo the ReferenceNo to set
     */
    public void setReferenceNo(String ReferenceNo) {
        this.ReferenceNo = ReferenceNo;
    }

}
