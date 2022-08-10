/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_good_detail;
import api_tax.efris.EFRIS_invoice_detail;
import api_tax.efris.innerclasses.T106;
import beans.AccChildAccountBean;
import beans.AccCurrencyBean;
import beans.CreditDebitNoteBean;
import beans.MenuItemBean;
import beans.OutputDetailBean;
import beans.Parameter_listBean;
import beans.PayBean;
import beans.ReportBean;
import beans.StoreBean;
import beans.TransBean;
import beans.TransExtBean;
import beans.TransItemBean;
import beans.TransactorBean;
import beans.UserDetailBean;
import connections.DBConnection;
import entities.AccChildAccount;
import entities.AccCurrency;
import entities.CompanySetting;
import entities.Store;
import entities.Trans;
import entities.TransItem;
import entities.Transaction_tax_map;
import entities.Transactor;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utilities.UtilityBean;

/**
 *
 * @author emuwonge
 */
@ManagedBean
@SessionScoped
public class EFRIS_invoice_detailBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(EFRIS_invoice_detailBean.class.getName());

    private List<EFRIS_invoice_detail> EFRIS_invoice_detailList;
    private EFRIS_invoice_detail EFRIS_invoice_detailObj;
    private List<EFRIS_good_detail> EFRIS_good_detailList;
    private Date fromDate;
    private Date toDate;
    private int storeId;
    //@ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;
    private String ActionMessage = null;

    public void saveImportedEFRISInvoice() {
        try {
            List<EFRIS_invoice_detail> invoiceList;
            //get unprocessed invoices
            invoiceList = this.getEFRIS_invoice_detail_Unprocessed();

            for (int i = 0; i < invoiceList.size(); i++) {
                EFRIS_invoice_detail invoice = invoiceList.get(i);

                //get good/items details                
                List<EFRIS_good_detail> goodList;
                goodList = new EFRIS_good_detailBean().getEFRIS_invoice_detailByInvoiceNo(invoice.getInvoiceNo());

                //validate invoice and good detail
                String validationMsg = this.validateSaleInvoice(invoice, goodList);
                if (validationMsg.equals("success")) {
                    //convert invoice to trans
                    Trans trans = new Trans();
                    this.setTransFromEFRIS_invoice_detail(trans, invoice);

                    //convert invoice to trans
                    List<TransItem> transItemList = new ArrayList<>();
                    new EFRIS_good_detailBean().setTransItemFromEFRIS_good_detail(transItemList, goodList);

                    //save in SM db
                    int store_id = this.getStoreByDeviceNo(invoice.getDeviceNo()).getStoreId();
                    //storeId=1 Busula, aTransTypeId=2 SALE INVOICE, aTransReasonId=2 RETAIL SALE INVOICE, aSaleType=RETAIL SALE INVOICE
                    Transaction_tax_map TransTaxMap = new Transaction_tax_map();
                    TransTaxMap.setVerification_code_tax(invoice.getAntifakeCode());
                    TransTaxMap.setQr_code_tax(invoice.getQrCode());
                    TransTaxMap.setTransaction_number_tax(invoice.getInvoiceNo());
                    new TransExtBean().saveSalesInvoiceImported("PARENT", store_id, 2, 2, "RETAIL SALE INVOICE", trans, transItemList, null, null, null, null, null, null, TransTaxMap);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public String validateSaleInvoice(EFRIS_invoice_detail aEFRIS_invoice_detail, List<EFRIS_good_detail> aEFRIS_good_detail) {
        String status;
        try {
            Store store;
            //currencyCode using currency
            AccCurrency currency = new AccCurrencyBean().getCurrency(aEFRIS_invoice_detail.getCurrency());
            //user using operator
            UserDetail aUserDetail = new UserDetailBean().getUserDetailByUserName(aEFRIS_invoice_detail.getOperator());
            //store using deviceNo
            String storeDeviceMapping = new Parameter_listBean().getParameter_listByContextName("MOBILE", "STORE_MOB_DEVICE_MAP").getParameter_value();
            store = this.getStoreByDeviceNo(aEFRIS_invoice_detail.getDeviceNo());
            //validate goodDetail/items
            String itemValidationMsg = new EFRIS_good_detailBean().validateSaleInvoiceItems(aEFRIS_good_detail);
            int payMethodId = 1;
            if (currency == null) {
                status = "Currency code does not exist";
            } else if (aUserDetail == null) {
                status = "Operator/User does not exist";
            } else if (storeDeviceMapping.length() == 0) {
                status = "Device Store Mapping Not Configured";
            } else if (store == null) {
                status = "Device Number not mapped to any store";
            } //else if (!new StoreBean().getStoresByUser(aUserDetail.getUserDetailId()).contains(store) && aUserDetail.getIsUserGenAdmin().equals("No")) {
            //    status = "Operator/User has no access to the store mapped to the device number";
            //} 
            else if (new AccChildAccountBean().getAccChildAccountsForCashReceipt(currency.getCurrencyCode(), payMethodId, store.getStoreId(), aUserDetail.getUserDetailId()).get(0) == null) {
                status = "Operator/User does not have a child account";
            } else if (!itemValidationMsg.equals("success")) {
                status = itemValidationMsg;
            } else {
                status = "success";
            }
        } catch (Exception e) {
            status = e.getMessage();
            LOGGER.log(Level.ERROR, e);
        }

        //update validation status
        try {
            if (status.equals("success")) {
                this.updateEFRIS_invoice_detailValidation(aEFRIS_invoice_detail.getEFRIS_invoice_detail_id(), 1, status);
            } else {
                this.updateEFRIS_invoice_detailValidation(aEFRIS_invoice_detail.getEFRIS_invoice_detail_id(), 2, status);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return status;
    }

    public Store getStoreByDeviceNo(String aDeviceNo) {
        Store store = null;
        try {
            String storeDeviceMapping = new Parameter_listBean().getParameter_listByContextName("MOBILE", "STORE_MOB_DEVICE_MAP").getParameter_value();
            if (storeDeviceMapping.length() > 0) {
                String[] storeDeviceNoArray = new UtilityBean().getStringArrayFromCommaSeperatedStr(storeDeviceMapping);
                if (storeDeviceNoArray.length > 0) {
                    for (int i = 0; i < storeDeviceNoArray.length; i++) {
                        String[] storeDeviceNo = new UtilityBean().getStringArrayFromXSeperatedStr(storeDeviceNoArray[i], ":");
                        if (storeDeviceNo[1].equals(aDeviceNo)) {
                            store = new StoreBean().getStoreByCode(storeDeviceNo[0]);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return store;
    }

    public void setEFRIS_invoice_detailFromResultset(EFRIS_invoice_detail aEFRIS_invoice_detail, ResultSet aResultSet) {
        try {
            try {
                aEFRIS_invoice_detail.setEFRIS_invoice_detail_id(aResultSet.getLong("efris_invoice_detail_id"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setEFRIS_invoice_detail_id(0);
            }
            try {
                aEFRIS_invoice_detail.setId(aResultSet.getString("id"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setId("");
            }
            try {
                aEFRIS_invoice_detail.setInvoiceNo(aResultSet.getString("invoiceNo"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setInvoiceNo("");
            }
            try {
                aEFRIS_invoice_detail.setOriInvoiceId(aResultSet.getString("oriInvoiceId"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setOriInvoiceId("");
            }
            try {
                aEFRIS_invoice_detail.setOriInvoiceNo(aResultSet.getString("oriInvoiceNo"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setOriInvoiceNo("");
            }
            try {
                aEFRIS_invoice_detail.setIssuedDate(aResultSet.getString("issuedDate"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setIssuedDate("");
            }
            try {
                aEFRIS_invoice_detail.setBuyerTin(aResultSet.getString("buyerTin"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setBuyerTin("");
            }
            try {
                aEFRIS_invoice_detail.setBuyerLegalName(aResultSet.getString("buyerLegalName"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setBuyerLegalName("");
            }
            try {
                aEFRIS_invoice_detail.setBuyerNinBrn(aResultSet.getString("buyerNinBrn"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setBuyerNinBrn("");
            }
            try {
                aEFRIS_invoice_detail.setCurrency(aResultSet.getString("currency"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setCurrency("");
            }
            try {
                aEFRIS_invoice_detail.setGrossAmount(aResultSet.getString("grossAmount"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setGrossAmount("");
            }
            try {
                aEFRIS_invoice_detail.setTaxAmount(aResultSet.getString("taxAmount"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setTaxAmount("");
            }
            try {
                aEFRIS_invoice_detail.setDataSource(aResultSet.getString("dataSource"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setDataSource("");
            }
            try {
                aEFRIS_invoice_detail.setIsInvalid(aResultSet.getString("isInvalid"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setIsInvalid("");
            }
            try {
                aEFRIS_invoice_detail.setIsRefund(aResultSet.getString("isRefund"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setIsRefund("");
            }
            try {
                aEFRIS_invoice_detail.setInvoiceType(aResultSet.getString("invoiceType"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setInvoiceType("");
            }
            try {
                aEFRIS_invoice_detail.setInvoiceKind(aResultSet.getString("invoiceKind"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setInvoiceKind("");
            }
            try {
                aEFRIS_invoice_detail.setInvoiceIndustryCode(aResultSet.getString("invoiceIndustryCode"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setInvoiceIndustryCode("");
            }
            try {
                aEFRIS_invoice_detail.setBranchName(aResultSet.getString("branchName"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setBranchName("");
            }
            try {
                aEFRIS_invoice_detail.setDeviceNo(aResultSet.getString("deviceNo"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setDeviceNo("");
            }
            try {
                aEFRIS_invoice_detail.setUploadingTime(aResultSet.getString("uploadingTime"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setUploadingTime("");
            }
            try {
                aEFRIS_invoice_detail.setReferenceNo(aResultSet.getString("referenceNo"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setReferenceNo("");
            }
            try {
                aEFRIS_invoice_detail.setOperator(aResultSet.getString("operator"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setOperator("");
            }
            try {
                aEFRIS_invoice_detail.setUserName(aResultSet.getString("userName"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setUserName("");
            }
            try {
                aEFRIS_invoice_detail.setProcess_flag(aResultSet.getInt("process_flag"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setProcess_flag(0);
            }
            try {
                aEFRIS_invoice_detail.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setAdd_date(null);
            }
            try {
                aEFRIS_invoice_detail.setProcess_date(new Date(aResultSet.getTimestamp("process_date").getTime()));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setProcess_date(null);
            }
            try {
                aEFRIS_invoice_detail.setProcess_desc(aResultSet.getString("process_desc"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setProcess_desc("");
            }
            try {
                aEFRIS_invoice_detail.setAntifakeCode(aResultSet.getString("verification_code"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setAntifakeCode("");
            }
            try {
                aEFRIS_invoice_detail.setQrCode(aResultSet.getString("qr_code"));
            } catch (Exception e) {
                aEFRIS_invoice_detail.setQrCode("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setTransFromEFRIS_invoice_detail(Trans aTrans, EFRIS_invoice_detail aEFRIS_invoice_detail) {
        try {
            try {
                String dateInString = aEFRIS_invoice_detail.getIssuedDate();

                //convert string to date
                //input "14/07/2022 18:41:23"
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                Date parsedDate = sdf.parse(dateInString);

                aTrans.setTransactionDate(parsedDate);
            } catch (Exception e) {
                aTrans.setTransactionDate(null);
                LOGGER.log(Level.ERROR, e);
            }
            try {
                if (null == aEFRIS_invoice_detail.getReferenceNo()) {
                    aTrans.setTransactionNumber("");
                } else {
                    aTrans.setTransactionNumber(aEFRIS_invoice_detail.getReferenceNo());
                }
            } catch (Exception e) {
                aTrans.setTransactionNumber("");
            }
            try {
                Transactor aTransactor = null;
                //get transactor using taxIdentity TIN
                if (null != aEFRIS_invoice_detail.getBuyerTin()) {
                    if (aEFRIS_invoice_detail.getBuyerTin().length() > 0) {
                        aTransactor = new TransactorBean().getTransactorBy_tax_identity(aEFRIS_invoice_detail.getBuyerTin());
                    }
                }
                //get transactor using legalname or transactorNames
                if (null != aEFRIS_invoice_detail.getBuyerLegalName()) {
                    if (aEFRIS_invoice_detail.getBuyerLegalName().length() > 0 && aTransactor == null) {
                        aTransactor = new TransactorBean().getTransactorBy_transactor_names_equal(aEFRIS_invoice_detail.getBuyerLegalName());
                    }
                }
                //et default customer
                if (new Parameter_listBean().getParameter_listByContextName("GENERAL", "WALK_IN_CUSTOMER_DEFAULT_REFNO").getParameter_value().length() > 0 && aTransactor == null) {
                    String refNo = new Parameter_listBean().getParameter_listByContextName("GENERAL", "WALK_IN_CUSTOMER_DEFAULT_REFNO").getParameter_value();
                    aTransactor = new TransactorBean().getTransactorBy_transactor_ref(refNo);
                    if (null != aEFRIS_invoice_detail.getBuyerLegalName()) {
                        if (aEFRIS_invoice_detail.getBuyerLegalName().length() > 0) {
                            aTrans.setTransactor_rep(aEFRIS_invoice_detail.getBuyerLegalName());
                        }
                    }
                }
                if (aTransactor != null) {
                    aTrans.setTransactorId(aTransactor.getTransactorId());
                } else {
                    aTrans.setTransactorId(0);
                }
            } catch (Exception e) {
                aTrans.setTransactorId(0);
            }
            try {
                aTrans.setTransactionReasonId(2);
            } catch (Exception e) {
                aTrans.setTransactionReasonId(0);
            }
            try {
                aTrans.setSubTotal(Integer.parseInt(aEFRIS_invoice_detail.getGrossAmount()) - Integer.parseInt(aEFRIS_invoice_detail.getTaxAmount()));
            } catch (Exception e) {
                aTrans.setSubTotal(0);
            }
            try {
                aTrans.setTotalVat(Integer.parseInt(aEFRIS_invoice_detail.getTaxAmount()));;
            } catch (Exception e) {
                aTrans.setTotalVat(0);
            }
            try {
                aTrans.setGrandTotal(Integer.parseInt(aEFRIS_invoice_detail.getGrossAmount()));;
            } catch (Exception e) {
                aTrans.setGrandTotal(0);
            }
            try {
                String receiveInvoiceCashType = new Parameter_listBean().getParameter_listByContextName("MOBILE", "RECEIVE_INVOICE_CASH_TYPE").getParameter_value();
                if (receiveInvoiceCashType.equals("1")) {
                    aTrans.setAmountTendered(Double.parseDouble(aEFRIS_invoice_detail.getGrossAmount()));
                } else {
                    aTrans.setAmountTendered(0);
                }
            } catch (Exception e) {
                aTrans.setAmountTendered(0);
            }
            try {
                aTrans.setPayMethod(1);
            } catch (Exception e) {
                aTrans.setPayMethod(0);
            }
            try {
                aTrans.setCurrencyCode(aEFRIS_invoice_detail.getCurrency());
            } catch (Exception e) {
                aTrans.setCurrencyCode("");
            }
            try {
                aTrans.setXrate(1);
            } catch (Exception e) {
                aTrans.setXrate(0);
            }
            try {
                aTrans.setStoreId(this.getStoreByDeviceNo(aEFRIS_invoice_detail.getDeviceNo()).getStoreId());
            } catch (Exception e) {
                aTrans.setStoreId(0);
            }
            try {
                UserDetail aUserDetail;
                if (aEFRIS_invoice_detail.getOperator().length() > 0) {
                    aUserDetail = new UserDetailBean().getUserDetailByUserName(aEFRIS_invoice_detail.getOperator());
                    aTrans.setAddUserDetailId(aUserDetail.getUserDetailId());
                    aTrans.setAddUserDetailName(aUserDetail.getUserName());
                    aTrans.setTransactionUserDetailId(aUserDetail.getUserDetailId());
                    aTrans.setTransactionUserDetailName(aUserDetail.getUserName());
                } else {
                    aTrans.setAddUserDetailId(0);
                    aTrans.setAddUserDetailName("");
                    aTrans.setTransactionUserDetailId(0);
                    aTrans.setTransactionUserDetailName("");
                }
            } catch (Exception e) {
                aTrans.setAddUserDetailId(0);
                aTrans.setAddUserDetailName("");
                aTrans.setTransactionUserDetailId(0);
                aTrans.setTransactionUserDetailName("");
            }
            try {
                String currencyCode = aTrans.getCurrencyCode();
                int payMethodId = aTrans.getPayMethod();
                int storeId = aTrans.getStoreId();
                int userId = aTrans.getAddUserDetailId();
                //get child account
                AccChildAccount childAccount = new AccChildAccountBean().getAccChildAccountsForCashReceipt(currencyCode, payMethodId, storeId, userId).get(0);

                aTrans.setAccChildAccountId(childAccount.getAccChildAccountId());
            } catch (Exception e) {
                aTrans.setAccChildAccountId(0);
            }
            try {
                aTrans.setVerification_code_tax(aEFRIS_invoice_detail.getAntifakeCode());
            } catch (Exception e) {
                aTrans.setVerification_code_tax("");
            }
            try {
                aTrans.setQr_code_tax(aEFRIS_invoice_detail.getQrCode());
            } catch (Exception e) {
                aTrans.setQr_code_tax("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertEFRIS_invoice_detail(EFRIS_invoice_detail aEFRIS_invoice_detail) {
        int saved = 0;
        String sql = "INSERT INTO efris_invoice_detail"
                + "(id, invoiceNo, oriInvoiceId, oriInvoiceNo, issuedDate, buyerTin, buyerLegalName, buyerNinBrn, currency, grossAmount,"
                + "taxAmount, dataSource, isInvalid, isRefund, invoiceType, invoiceKind, invoiceIndustryCode, branchName, deviceNo,"
                + "uploadingTime, referenceNo, operator, userName, process_flag, add_date, process_date, process_desc,"
                + "verification_code, qr_code)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //id, invoiceNo, oriInvoiceId, oriInvoiceNo, issuedDate, buyerTin, buyerLegalName, buyerNinBrn, currency, grossAmount
            ps.setString(1, aEFRIS_invoice_detail.getId());
            ps.setString(2, aEFRIS_invoice_detail.getInvoiceNo());
            ps.setString(3, aEFRIS_invoice_detail.getOriInvoiceId());
            ps.setString(4, aEFRIS_invoice_detail.getOriInvoiceNo());
            ps.setString(5, aEFRIS_invoice_detail.getIssuedDate());
            ps.setString(6, aEFRIS_invoice_detail.getBuyerTin());
            ps.setString(7, aEFRIS_invoice_detail.getBuyerLegalName());
            ps.setString(8, aEFRIS_invoice_detail.getBuyerNinBrn());
            ps.setString(9, aEFRIS_invoice_detail.getCurrency());
            ps.setString(10, aEFRIS_invoice_detail.getGrossAmount());
            //taxAmount, dataSource, isInvalid, isRefund, invoiceType, invoiceKind, invoiceIndustryCode, branchName, deviceNo
            ps.setString(11, aEFRIS_invoice_detail.getTaxAmount());
            ps.setString(12, aEFRIS_invoice_detail.getDataSource());
            ps.setString(13, aEFRIS_invoice_detail.getIsInvalid());
            ps.setString(14, aEFRIS_invoice_detail.getIsRefund());
            ps.setString(15, aEFRIS_invoice_detail.getInvoiceType());
            ps.setString(16, aEFRIS_invoice_detail.getInvoiceKind());
            ps.setString(17, aEFRIS_invoice_detail.getInvoiceIndustryCode());
            ps.setString(18, aEFRIS_invoice_detail.getBranchName());
            ps.setString(19, aEFRIS_invoice_detail.getDeviceNo());
            //uploadingTime, referenceNo, operator, userName, process_flag, add_date, process_date, process_desc
            ps.setString(20, aEFRIS_invoice_detail.getUploadingTime());
            if (aEFRIS_invoice_detail.getReferenceNo() == null) {
                ps.setString(21, aEFRIS_invoice_detail.getInvoiceNo());
            } else if (aEFRIS_invoice_detail.getReferenceNo().isEmpty()) {
                ps.setString(21, aEFRIS_invoice_detail.getInvoiceNo());
            } else {
                ps.setString(21, aEFRIS_invoice_detail.getReferenceNo());
            }
            ps.setString(22, aEFRIS_invoice_detail.getOperator());
            ps.setString(23, aEFRIS_invoice_detail.getUserName());
            ps.setInt(24, 0);
            ps.setTimestamp(25, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setString(26, null);
            ps.setString(27, "");
            //verification_code, qr_code
            ps.setString(28, aEFRIS_invoice_detail.getAntifakeCode());
            ps.setString(29, aEFRIS_invoice_detail.getQrCode());
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int insertEFRIS_invoice_detail(T106 aT106) {
        int saved = 0;
        String sql = "INSERT INTO efris_invoice_detail"
                + "(id, invoiceNo, oriInvoiceId, oriInvoiceNo, issuedDate, buyerTin, buyerLegalName, buyerNinBrn, currency, grossAmount,"
                + "taxAmount, dataSource, isInvalid, isRefund, invoiceType, invoiceKind, invoiceIndustryCode, branchName, deviceNo,"
                + "uploadingTime, referenceNo, operator, userName, process_flag, add_date, process_date, process_desc,"
                + "verification_code, qr_code)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //id, invoiceNo, oriInvoiceId, oriInvoiceNo, issuedDate, buyerTin, buyerLegalName, buyerNinBrn, currency, grossAmount
            ps.setString(1, aT106.getId());
            ps.setString(2, aT106.getInvoiceNo());
            ps.setString(3, aT106.getOriInvoiceId());
            ps.setString(4, aT106.getOriInvoiceNo());
            //ps.setString(3, "");
            //ps.setString(4, "");
            ps.setString(5, aT106.getIssuedDate());
            //ps.setTimestamp(5, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setString(6, aT106.getBuyerTin());
            ps.setString(7, aT106.getBuyerLegalName());
            ps.setString(8, aT106.getBuyerNinBrn());
            ps.setString(9, aT106.getCurrency());
            ps.setString(10, aT106.getGrossAmount());
            //taxAmount, dataSource, isInvalid, isRefund, invoiceType, invoiceKind, invoiceIndustryCode, branchName, deviceNo
            ps.setString(11, aT106.getTaxAmount());
            ps.setString(12, aT106.getDataSource());
            ps.setString(13, aT106.getIsInvalid());
            ps.setString(14, aT106.getIsRefund());
            ps.setString(15, aT106.getInvoiceType());
            ps.setString(16, aT106.getInvoiceKind());
            ps.setString(17, aT106.getInvoiceIndustryCode());
            ps.setString(18, aT106.getBranchName());
            ps.setString(19, aT106.getDeviceNo());
            //uploadingTime, referenceNo, operator, userName, process_flag, add_date, process_date, process_desc
            ps.setString(20, aT106.getUploadingTime());
            if (aT106.getReferenceNo() == null) {
                ps.setString(21, aT106.getInvoiceNo());
            } else if (aT106.getReferenceNo().isEmpty()) {
                ps.setString(21, aT106.getInvoiceNo());
            } else {
                ps.setString(21, aT106.getReferenceNo());
            }
            //ps.setString(21, aT106.getReferenceNo());
            ps.setString(22, aT106.getOperator());
            ps.setString(23, aT106.getUserName());
            ps.setInt(24, 0);
            ps.setTimestamp(25, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setString(26, null);
            ps.setString(27, "");
            //verification_code, qr_code
            ps.setString(28, aT106.getAntifakeCode());
            ps.setString(29, aT106.getQrCode());
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public void updateEFRIS_invoice_detailValidation(long aEFRIS_invoice_detail_id, int status, String statusMsg) {
        //int saved = 0;
        String sql = "UPDATE efris_invoice_detail SET process_flag = ?, process_desc = ?, process_date = ? WHERE efris_invoice_detail_id = ?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, status);
            ps.setString(2, statusMsg);
            ps.setTimestamp(3, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setLong(4, aEFRIS_invoice_detail_id);
            ps.executeUpdate();
            //saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        //return saved;
    }

    public EFRIS_invoice_detail getEFRIS_invoice_detail(long aEFRIS_invoice_detail_id) {
        String sql = "SELECT * FROM efris_invoice_detail WHERE efris_invoice_detail_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aEFRIS_invoice_detail_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                EFRIS_invoice_detail obj = new EFRIS_invoice_detail();
                this.setEFRIS_invoice_detailFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<EFRIS_invoice_detail> getEFRIS_invoice_detail_All() {
        String sql = "SELECT * FROM efris_invoice_detail";
        ResultSet rs;
        List<EFRIS_invoice_detail> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                EFRIS_invoice_detail obj = new EFRIS_invoice_detail();
                this.setEFRIS_invoice_detailFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }

    public List<EFRIS_invoice_detail> getEFRIS_invoice_detail_Unprocessed() {
        String sql = "SELECT * FROM efris_invoice_detail where process_flag=0";
        ResultSet rs;
        List<EFRIS_invoice_detail> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                EFRIS_invoice_detail obj = new EFRIS_invoice_detail();
                this.setEFRIS_invoice_detailFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }

    public void reSynchEFDLogsDetail(long aEFRIS_invoice_detail_id) {
        try {
            //resynch
            this.updateEFRIS_invoice_detailValidation(aEFRIS_invoice_detail_id, 0, "ReSynced");
            //update the datable
            this.reportSalesInvoiceDetail();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void reportSalesInvoiceDetail() {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        this.setActionMessage("");
        try {
            if (fromDate != null && toDate != null) {
                //okay no problem
            } else {
                msg = "Select Date Range";
            }
        } catch (Exception e) {
            //do nothing
        }
        ResultSet rs;
        this.EFRIS_invoice_detailList = new ArrayList<>();
        if (msg.length() > 0) {
            this.setActionMessage(ub.translateWordsInText(BaseName, msg));
            FacesContext.getCurrentInstance().addMessage("Report", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String sql = "SELECT * FROM efris_invoice_detail WHERE efris_invoice_detail_id > 0";
            String wheresql = "";
            String ordersql;
            if (this.getStoreId() > 0) {
                //wheresql = wheresql + " AND store_id=" + this.getStoreId();
            }
            if (this.getFromDate() != null && this.getToDate() != null) {
                wheresql = wheresql + " AND add_date BETWEEN '" + new java.sql.Timestamp(this.getFromDate().getTime()) + "' AND '" + new java.sql.Timestamp(this.getToDate().getTime()) + "'";
            }
            ordersql = " ORDER BY add_date DESC,efris_invoice_detail_id DESC";
            sql = sql + wheresql + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                //EFRIS_invoice_detail aEFRIS_invoice_detail = null;
                while (rs.next()) {
                    EFRIS_invoice_detail aEFRIS_invoice_detail = new EFRIS_invoice_detail();
                    this.setEFRIS_invoice_detailFromResultset(aEFRIS_invoice_detail, rs);
                    this.EFRIS_invoice_detailList.add(aEFRIS_invoice_detail);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    // to validate existing invoices not pushed yet
    public Boolean isEFRIS_invoice_detail_Exist(String id) {
        String sql = "SELECT * FROM efris_invoice_detail where id=?";
        ResultSet rs;
        List<EFRIS_invoice_detail> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return false;
    }

    public void initResetEFDLogsDetail() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.resetEFDLogsDetail();
        }
    }

    public void resetEFDLogsDetail() {
        this.setActionMessage("");
        try {
            //this.clearTrans(aTrans);
        } catch (NullPointerException npe) {
        }
        try {
            this.setFromDate(null);
            this.setToDate(null);
            this.setStoreId(0);
            this.EFRIS_invoice_detailList.clear();
        } catch (NullPointerException npe) {
        }
    }

    public void initEFDLogSession(long aEFRIS_invoice_detail_id) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        String msg = "";
        this.setActionMessage("");
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        this.EFRIS_invoice_detailObj = new EFRIS_invoice_detailBean().getEFRIS_invoice_detail(aEFRIS_invoice_detail_id);
        List<Trans> aList = new ArrayList<>();
//        if (this.TransObj.getTransactionTypeId() == 2) {
//            aList = new CreditDebitNoteBean().getTrans_cr_dr_notes(this.TransObj.getTransactionNumber(), 0);
//        }
//        if (aAction.equals("Edit") && !aList.isEmpty()) {
//            this.ActionType = "None";
//            msg = "Transaction Has Credit or Debit Note";
//            this.setActionMessage(ub.translateWordsInText(BaseName, msg));
//        } else {
        //first set current selection in session
//            FacesContext context = FacesContext.getCurrentInstance();
//            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//            HttpSession httpSession = request.getSession(true);
//            httpSession.setAttribute("CURRENT_TRANSACTION_ID", aTransId);
//            httpSession.setAttribute("CURRENT_TRANSACTION_ACTION", aAction);
//            httpSession.setAttribute("CURRENT_PAY_ID", 0);
//            this.ActionType = aAction;
        //this.TransObj = new TransBean().getTrans(aTransId);
//            this.updateLookup(this.TransObj);
        this.EFRIS_good_detailList = new EFRIS_good_detailBean().getEFRIS_invoice_detailByInvoiceNo(this.EFRIS_invoice_detailObj.getInvoiceNo());
        try {
//                this.PayObj = new PayBean().getTransactionFirstPayByTransNo(TransObj.getTransactionNumber());//first payment
//                httpSession.setAttribute("CURRENT_PAY_ID", this.PayObj.getPayId());
        } catch (NullPointerException npe) {
//                this.PayObj = null;
        }
        //refresh output
        new OutputDetailBean().refreshOutput("PARENT", "");
        //refresh history
//            this.TransListHist = new ReportBean().getTransHistory(aTransId);
//        }
    }

    public void setDateToToday() {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();
        this.setFromDate(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getFromDate());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setFromDate(cal.getTime());

        this.setToDate(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.getToDate());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setToDate(cal2.getTime());
    }

    public void setDateToYesterday() {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();

        this.setFromDate(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getFromDate());
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setFromDate(cal.getTime());

        this.setToDate(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.getToDate());
        cal2.add(Calendar.DATE, -1);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setToDate(cal2.getTime());
    }

    /**
     * @return the EFRIS_invoice_detailList
     */
    public List<EFRIS_invoice_detail> getEFRIS_invoice_detailList() {
        return EFRIS_invoice_detailList;
    }

    /**
     * @param EFRIS_invoice_detailList the EFRIS_invoice_detailList to set
     */
    public void setEFRIS_invoice_detailList(List<EFRIS_invoice_detail> EFRIS_invoice_detailList) {
        this.EFRIS_invoice_detailList = EFRIS_invoice_detailList;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the storeId
     */
    public int getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the ActionMessage
     */
    public String getActionMessage() {
        return ActionMessage;
    }

    /**
     * @param ActionMessage the ActionMessage to set
     */
    public void setActionMessage(String ActionMessage) {
        this.ActionMessage = ActionMessage;
    }

    /**
     * @return the EFRIS_invoice_detailObj
     */
    public EFRIS_invoice_detail getEFRIS_invoice_detailObj() {
        return EFRIS_invoice_detailObj;
    }

    /**
     * @param EFRIS_invoice_detailObj the EFRIS_invoice_detailObj to set
     */
    public void setEFRIS_invoice_detailObj(EFRIS_invoice_detail EFRIS_invoice_detailObj) {
        this.EFRIS_invoice_detailObj = EFRIS_invoice_detailObj;
    }

    /**
     * @return the EFRIS_good_detailList
     */
    public List<EFRIS_good_detail> getEFRIS_good_detailList() {
        return EFRIS_good_detailList;
    }

    /**
     * @param EFRIS_good_detailList the EFRIS_good_detailList to set
     */
    public void setEFRIS_good_detailList(List<EFRIS_good_detail> EFRIS_good_detailList) {
        this.EFRIS_good_detailList = EFRIS_good_detailList;
    }

}
