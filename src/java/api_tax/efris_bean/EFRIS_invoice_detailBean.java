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
import beans.Parameter_listBean;
import beans.StoreBean;
import beans.TransExtBean;
import beans.TransactorBean;
import beans.UserDetailBean;
import connections.DBConnection;
import entities.AccChildAccount;
import entities.AccCurrency;
import entities.CompanySetting;
import entities.Store;
import entities.Trans;
import entities.TransItem;
import entities.Transactor;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
                    new TransExtBean().saveSalesInvoiceImported("PARENT", store_id, 2, 2, "RETAIL SALE INVOICE", trans, transItemList, null, null, null, null, null, null);
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
            } else if (new AccChildAccountBean().getAccChildAccountsForCashReceipt(currency.getCurrencyCode(), payMethodId, store.getStoreId(), aUserDetail.getUserDetailId()).get(0) == null) {
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
                Transactor aTransactor;
                if (aEFRIS_invoice_detail.getBuyerTin().length() > 0) {
                    aTransactor = new TransactorBean().getTransactorBy_tax_identity(aEFRIS_invoice_detail.getBuyerTin());
                    aTrans.setTransactorId(aTransactor.getTransactorId());
                } else if (aEFRIS_invoice_detail.getBuyerLegalName().length() > 0) {
                    aTransactor = new TransactorBean().getTransactorBy_transactor_names(aEFRIS_invoice_detail.getBuyerLegalName());
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
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertEFRIS_invoice_detail(EFRIS_invoice_detail aEFRIS_invoice_detail) {
        int saved = 0;
        String sql = "INSERT INTO efris_invoice_detail"
                + "(id, invoiceNo, oriInvoiceId, oriInvoiceNo, issuedDate, buyerTin, buyerLegalName, buyerNinBrn, currency, grossAmount,"
                + "taxAmount, dataSource, isInvalid, isRefund, invoiceType, invoiceKind, invoiceIndustryCode, branchName, deviceNo,"
                + "uploadingTime, referenceNo, operator, userName, process_flag, add_date, process_date, process_desc)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            if (aEFRIS_invoice_detail.getReferenceNo().isEmpty()) {
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
                + "uploadingTime, referenceNo, operator, userName, process_flag, add_date, process_date, process_desc)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            ps.setString(21, aT106.getReferenceNo());
            ps.setString(22, aT106.getOperator());
            ps.setString(23, aT106.getUserName());
            ps.setInt(24, 0);
            ps.setTimestamp(25, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setString(26, null);
            ps.setString(27, "");
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

}
