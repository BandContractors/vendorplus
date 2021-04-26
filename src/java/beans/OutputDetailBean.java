package beans;

import connections.DBConnection;
import entities.Pay;
import entities.PayTrans;
import sessions.GeneralUserSetting;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import utilities.ConvertNumToWordBean;

@ManagedBean
@SessionScoped
public class OutputDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public OutputDetailBean() {
    }

    public void refreshOutput(String aLevel, String aSource) {
        try {
            TransBean tb = new TransBean();
            OutputDetail aOutputDetail = new OutputDetail();
            switch (aLevel) {
                case "PARENT":
                    try {
                        aOutputDetail.setTrans(new TransBean().getTrans(new GeneralUserSetting().getCurrentTransactionId()));
                    } catch (Exception e) {
                    }
                    try {
                        aOutputDetail.setPay(new PayBean().getPay(new GeneralUserSetting().getCurrentPayId()));
                    } catch (Exception e) {
                        aOutputDetail.setPay(new Pay());
                    }
                    break;
                case "CHILD":
                    try {
                        aOutputDetail.setTrans(new TransBean().getTrans(new GeneralUserSetting().getCurrentTransactionIdChild()));
                    } catch (Exception e) {
                    }
                    try {
                        aOutputDetail.setPay(new PayBean().getPay(new GeneralUserSetting().getCurrentPayIdChild()));
                    } catch (Exception e) {
                        aOutputDetail.setPay(new Pay());
                    }
                    break;
            }
            tb.updateLookup(aOutputDetail.getTrans());

            try {
                aOutputDetail.setPoints_card(new PointsCardBean().getPointsCardByCardNumber(aOutputDetail.getTrans().getCardNumber()));
            } catch (Exception e) {
            }
            try {
                if (aSource.equals("SOURCE-PAY")) {
                    aOutputDetail.setAdd_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getPay().getAddUserDetailId()));
                } else {
                    aOutputDetail.setAdd_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTrans().getAddUserDetailId()));
                }
            } catch (Exception e) {
            }
            try {
                if (aSource.equals("SOURCE-PAY")) {
                    aOutputDetail.setEdit_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getPay().getEditUserDetailId()));
                } else {
                    aOutputDetail.setEdit_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTrans().getEditUserDetailId()));
                }
            } catch (Exception e) {
            }
            try {
                if (aSource.equals("SOURCE-PAY")) {
                    aOutputDetail.setTrans_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getPay().getAddUserDetailId()));
                } else {
                    aOutputDetail.setTrans_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTrans().getTransactionUserDetailId()));
                }
            } catch (Exception e) {
            }
            try {
                if (aSource.equals("SOURCE-PAY")) {
                    aOutputDetail.setAuthorised_by_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getPay().getAddUserDetailId()));
                } else {
                    aOutputDetail.setAuthorised_by_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTrans().getAuthorisedByUserDetailId()));
                }
            } catch (Exception e) {
            }
            try {
                if (aSource.equals("SOURCE-PAY")) {
                    aOutputDetail.setTransactor(new TransactorBean().getTransactor(aOutputDetail.getPay().getBillTransactorId()));
                } else {
                    aOutputDetail.setTransactor(new TransactorBean().getTransactor(aOutputDetail.getTrans().getTransactorId()));
                }
            } catch (Exception e) {
            }
            try {
                if (aSource.equals("SOURCE-PAY")) {
                    aOutputDetail.setBill_transactor(new TransactorBean().getTransactor(aOutputDetail.getPay().getBillTransactorId()));
                } else {
                    aOutputDetail.setBill_transactor(new TransactorBean().getTransactor(aOutputDetail.getTrans().getBillTransactorId()));
                }
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setScheme_transactor(new TransactorBean().getTransactor(aOutputDetail.getTrans().getSchemeTransactorId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTrans_items(new TransItemBean().getTransItemsByTransactionIdCEC(aOutputDetail.getTrans().getTransactionId()));
            } catch (Exception e) {
            }
            try {
                if (aSource.equals("SOURCE-PAY")) {
                    aOutputDetail.setStore(new StoreBean().getStore(aOutputDetail.getPay().getStoreId()));
                } else {
                    aOutputDetail.setStore(new StoreBean().getStore(aOutputDetail.getTrans().getStoreId()));
                }
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setStore2(new StoreBean().getStore(aOutputDetail.getTrans().getStore2Id()));
            } catch (Exception e) {
            }
            try {
                if (aSource.equals("SOURCE-PAY")) {
                    aOutputDetail.setTransaction_type(new TransactionTypeBean().getTransactionType(aOutputDetail.getPay().getPayTypeId()));
                } else {
                    aOutputDetail.setTransaction_type(new TransactionTypeBean().getTransactionType(aOutputDetail.getTrans().getTransactionTypeId()));
                }
            } catch (Exception e) {
            }
            try {
                if (aSource.equals("SOURCE-PAY")) {
                    aOutputDetail.setTransaction_reason(new TransactionReasonBean().getTransactionReason(aOutputDetail.getPay().getPayReasonId()));
                } else {
                    aOutputDetail.setTransaction_reason(new TransactionReasonBean().getTransactionReason(aOutputDetail.getTrans().getTransactionReasonId()));
                }
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setPay_method(new PayMethodBean().getPayMethod(aOutputDetail.getPay().getPayMethodId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTotal_items(aOutputDetail.getTrans_items().size());
                //aOutputDetail.setTotal_items_list(new ArrayList<>());
                List<Integer> itmlist = new ArrayList<>();
                for (int x = 1; x <= (10 - aOutputDetail.getTotal_items()); x++) {//changed from 15 to 10 to avoid 2 pages
                    itmlist.add(x);
                }
                aOutputDetail.setTotal_items_list(itmlist);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            try {
                if (aSource.equals("SOURCE-PAY")) {
                    aOutputDetail.setAcc_child_account(new AccChildAccountBean().getAccChildAccById(aOutputDetail.getPay().getAccChildAccountId()));
                } else {
                    aOutputDetail.setAcc_child_account(new AccChildAccountBean().getAccChildAccById(aOutputDetail.getTrans().getAccChildAccountId()));
                }
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setAcc_child_account2(new AccChildAccountBean().getAccChildAccById(aOutputDetail.getPay().getAccChildAccountId2()));
            } catch (Exception e) {
            }
            try {
                if (aOutputDetail.getTrans().getTransactionTypeId() == 66) {
                    aOutputDetail.getTrans().setTotal_weight(new TransItemBean().calcTransTotalWeight(aOutputDetail.getTrans_items()));
                }
            } catch (Exception e) {
            }
            //refresh amoun tin words
            try {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "OUTPUT_SHOW_AMOUNT_IN_WORDS").getParameter_value().equals("1")) {
                    aOutputDetail.setTransAmountInWords(new ConvertNumToWordBean().convertNumToWord(aOutputDetail.getTrans().getGrandTotal(), aOutputDetail.getTrans().getCurrencyCode()));
                } else {
                    aOutputDetail.setTransAmountInWords("");
                }
            } catch (Exception e) {
                aOutputDetail.setTransAmountInWords("");
            }
            try {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "OUTPUT_SHOW_AMOUNT_IN_WORDS").getParameter_value().equals("1")) {
                    aOutputDetail.setPayAmountInWords(new ConvertNumToWordBean().convertNumToWord(aOutputDetail.getPay().getPaidAmount(), aOutputDetail.getPay().getCurrencyCode()));
                } else {
                    aOutputDetail.setPayAmountInWords("");
                }
            } catch (Exception e) {
                aOutputDetail.setPayAmountInWords("");
            }
            //refresh pay reason
            aOutputDetail.setPay_reason("");
            aOutputDetail.setPay_reason2("");
            try {
                if (null != aOutputDetail.getPay()) {
                    if (aOutputDetail.getPay().getPayReasonId() == 21 || aOutputDetail.getPay().getPayReasonId() == 22) {
                        //aOutputDetail.setPay_reason("Goods/Services Ref No:");
                        aOutputDetail.setPay_reason(this.getGoodOrService(aOutputDetail.getPay().getPayId()) + " Ref No:");
                        String RefIdString = "";
                        String RefIdString2 = "";
                        try {
                            List<PayTrans> PTs = new PayTransBean().getPayTranssByPayId(aOutputDetail.getPay().getPayId());
                            for (int i = 0; i < PTs.size(); i++) {
                                if (RefIdString.length() <= 45) {
                                    if (RefIdString.length() == 0) {
                                        RefIdString = PTs.get(i).getTransactionNumber() + " ";
                                    } else {
                                        RefIdString = RefIdString + "," + PTs.get(i).getTransactionNumber() + " ";
                                    }
                                } else {
                                    if (RefIdString2.length() <= 60) {
                                        RefIdString2 = RefIdString2 + "," + PTs.get(i).getTransactionNumber() + " ";
                                    } else {
                                        RefIdString2 = ",etc.";
                                        break;
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }
                        aOutputDetail.setPay_reason(aOutputDetail.getPay_reason() + RefIdString);
                        aOutputDetail.setPay_reason2(RefIdString2);
                    } else if (aOutputDetail.getPay().getPayReasonId() == 23) {
                        aOutputDetail.setPay_reason("Share Capital Contribution");
                    } else if (aOutputDetail.getPay().getPayReasonId() == 24) {
                        aOutputDetail.setPay_reason("Current/Long-Term Loan");
                    } else if (aOutputDetail.getPay().getPayReasonId() == 90) {
                        aOutputDetail.setPay_reason("Customer Deposit/Prepayment");
                    } else if (aOutputDetail.getPay().getPayReasonId() == 115) {
                        aOutputDetail.setPay_reason("Other Revenue");
                    }
                }
                //for hire return
                try {
                    if (aOutputDetail.getTrans().getTransactionTypeId() == 67) {
                        new TransBean().setHireReturnTotalsAndBalances(aOutputDetail.getTrans().getTransactionRef(), aOutputDetail.getTrans_items());
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
            }
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            switch (aLevel) {
                case "PARENT":
                    httpSession.setAttribute("OUTPUT_DETAIL_PARENT", aOutputDetail);
                    break;
                case "CHILD":
                    httpSession.setAttribute("OUTPUT_DETAIL_CHILD", aOutputDetail);
                    break;
            }
        } catch (Exception e) {
            System.err.println("refreshOutput:" + e.getMessage());
        }
    }

    public void refreshOutputCrDr(String aLevel, String aSource) {
        try {
            TransBean tb = new TransBean();
            OutputDetail aOutputDetail = new OutputDetail();
            switch (aLevel) {
                case "PARENT":
                    try {
                        aOutputDetail.setTrans(new CreditDebitNoteBean().getTrans_cr_dr_note(new GeneralUserSetting().getCurrentTransactionId()));
                    } catch (Exception e) {
                    }
                    break;
                case "CHILD":
                    try {
                        aOutputDetail.setTrans(new CreditDebitNoteBean().getTrans_cr_dr_note(new GeneralUserSetting().getCurrentTransactionIdChild()));
                    } catch (Exception e) {
                    }
                    break;
            }
            tb.updateLookup(aOutputDetail.getTrans());
            try {
                aOutputDetail.setAdd_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTrans().getAddUserDetailId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setEdit_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTrans().getEditUserDetailId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTrans_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTrans().getTransactionUserDetailId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setAuthorised_by_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTrans().getAuthorisedByUserDetailId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTransactor(new TransactorBean().getTransactor(aOutputDetail.getTrans().getTransactorId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setBill_transactor(new TransactorBean().getTransactor(aOutputDetail.getTrans().getBillTransactorId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTrans_items(new CreditDebitNoteBean().getTransItemsByTransactionId_cr_dr_note(aOutputDetail.getTrans().getTransactionId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setStore(new StoreBean().getStore(aOutputDetail.getTrans().getStoreId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTransaction_type(new TransactionTypeBean().getTransactionType(aOutputDetail.getTrans().getTransactionTypeId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTransaction_reason(new TransactionReasonBean().getTransactionReason(aOutputDetail.getTrans().getTransactionReasonId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTotal_items(aOutputDetail.getTrans_items().size());
                //aOutputDetail.setTotal_items_list(new ArrayList<>());
                List<Integer> itmlist = new ArrayList<>();
                for (int x = 1; x <= (15 - aOutputDetail.getTotal_items()); x++) {
                    itmlist.add(x);
                }
                aOutputDetail.setTotal_items_list(itmlist);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            //refresh amount in words
            try {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "OUTPUT_SHOW_AMOUNT_IN_WORDS").getParameter_value().equals("1")) {
                    aOutputDetail.setTransAmountInWords(new ConvertNumToWordBean().convertNumToWord(aOutputDetail.getTrans().getGrandTotal(), aOutputDetail.getTrans().getCurrencyCode()));
                } else {
                    aOutputDetail.setTransAmountInWords("");
                }
            } catch (Exception e) {
                aOutputDetail.setTransAmountInWords("");
            }
            try {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "OUTPUT_SHOW_AMOUNT_IN_WORDS").getParameter_value().equals("1")) {
                    aOutputDetail.setPayAmountInWords(new ConvertNumToWordBean().convertNumToWord(aOutputDetail.getPay().getPaidAmount(), aOutputDetail.getPay().getCurrencyCode()));
                } else {
                    aOutputDetail.setPayAmountInWords("");
                }
            } catch (Exception e) {
                aOutputDetail.setPayAmountInWords("");
            }
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            switch (aLevel) {
                case "PARENT":
                    httpSession.setAttribute("OUTPUT_DETAIL_PARENT", aOutputDetail);
                    break;
                case "CHILD":
                    httpSession.setAttribute("OUTPUT_DETAIL_CHILD", aOutputDetail);
                    break;
            }
        } catch (Exception e) {
            System.err.println("refreshOutputCrDr:" + e.getMessage());
        }
    }

    public String getGoodOrService(long aPayId) {
        String good_or_service = "";
        int good_found = 0;
        int service_found = 0;
        String sql = "SELECT distinct i.item_type FROM transaction_item ti "
                + "INNER JOIN item i ON ti.item_id=i.item_id "
                + "WHERE ti.transaction_id IN (select pt.transaction_id from pay_trans pt where pt.pay_id=" + aPayId + ")";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                if (good_found == 0 && rs.getString("item_type").equals("PRODUCT")) {
                    good_found = 1;
                } else if (service_found == 0 && rs.getString("item_type").equals("SERVICE")) {
                    service_found = 1;
                } else {
                    break;
                }
            }
            if (good_found == 1 && service_found == 0) {
                good_or_service = "Goods Sold";
            } else if (good_found == 0 && service_found == 1) {
                good_or_service = "Services Rendered";
            } else if (good_found == 1 && service_found == 1) {
                good_or_service = "Goods and Services";
            } else {
                good_or_service = "Goods/Services";
            }
        } catch (Exception e) {
            System.err.println("getGoodOrService:" + e.getMessage());
        }
        return good_or_service;
    }

    public void refreshOutputProduction(String aLevel, String aSource) {
        try {
            TransProductionBean tb = new TransProductionBean();
            OutputDetail aOutputDetail = new OutputDetail();
            switch (aLevel) {
                case "PARENT":
                    try {
                        aOutputDetail.setTransProduction(new TransProductionBean().getTransProductionById(new GeneralUserSetting().getCurrentTransactionId()));
                    } catch (Exception e) {
                    }
                    break;
                case "CHILD":
                    try {
                        aOutputDetail.setTransProduction(new TransProductionBean().getTransProductionById(new GeneralUserSetting().getCurrentTransactionIdChild()));
                    } catch (Exception e) {
                    }
                    break;
            }
            tb.updateLookup(aOutputDetail.getTransProduction());
            try {
                aOutputDetail.setAdd_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTransProduction().getAddUserDetailId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setEdit_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTrans().getEditUserDetailId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTrans_user_detail(new UserDetailBean().getUserDetail(aOutputDetail.getTransProduction().getTransactionUserDetailId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTrans_prod_items(new TransProductionItemBean().getTransProductionItemsByTransProductionId(aOutputDetail.getTransProduction().getTransactionId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setStore(new StoreBean().getStore(aOutputDetail.getTransProduction().getStoreId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setStore2(new StoreBean().getStore(aOutputDetail.getTransProduction().getStore2Id()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTransaction_type(new TransactionTypeBean().getTransactionType(aOutputDetail.getTransProduction().getTransactionTypeId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTransaction_reason(new TransactionReasonBean().getTransactionReason(aOutputDetail.getTransProduction().getTransactionReasonId()));
            } catch (Exception e) {
            }
            try {
                aOutputDetail.setTotal_items(aOutputDetail.getTrans_prod_items().size());
                List<Integer> itmlist = new ArrayList<>();
                for (int x = 1; x <= (15 - aOutputDetail.getTotal_items()); x++) {
                    itmlist.add(x);
                }
                aOutputDetail.setTotal_items_list(itmlist);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            switch (aLevel) {
                case "PARENT":
                    httpSession.setAttribute("OUTPUT_DETAIL_PARENT", aOutputDetail);
                    break;
                case "CHILD":
                    httpSession.setAttribute("OUTPUT_DETAIL_CHILD", aOutputDetail);
                    break;
            }
        } catch (Exception e) {
            System.err.println("refreshOutputProduction:" + e.getMessage());
        }
    }

    public void clearOutputDetail(String aLevel) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        switch (aLevel) {
            case "PARENT":
                httpSession.setAttribute("OUTPUT_DETAIL_PARENT", new OutputDetail());
                break;
            case "CHILD":
                httpSession.setAttribute("OUTPUT_DETAIL_CHILD", new OutputDetail());
                break;
        }
    }

    public String getVatRatedCode(String Vatrated) {
        switch (Vatrated) {
            case "STANDARD":
                return "S";
            case "ZERO":
                return "Z";
            case "EXEMPT":
                return "E";
            default:
                return "";
        }
    }

}
