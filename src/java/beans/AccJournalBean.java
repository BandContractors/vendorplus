package beans;

import connections.DBConnection;
import entities.AccDepSchedule;
import entities.AccJournal;
import entities.CompanySetting;
import entities.Pay;
import entities.PayTrans;
import entities.Stock;
import entities.Trans;
import entities.TransItem;
import entities.TransProduction;
import entities.Transactor;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessions.GeneralUserSetting;
import utilities.UtilityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "accJournalBean")
@SessionScoped
public class AccJournalBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<AccJournal> AccJournalList;
    private List<AccJournal> AccJournalSummary;
    private AccJournal AccJournalObj;
    private String DateType;
    private Date Date1;
    private Date Date2;

    private String ActionMessage = null;

    public void setAccJournalFromResultset(AccJournal accjournal, ResultSet aResultSet) {
        try {
            try {
                accjournal.setAccJournalId(aResultSet.getLong("acc_journal_id"));
            } catch (NullPointerException npe) {
                accjournal.setAccJournalId(0);
            }
            try {
                accjournal.setJournalDate(new Date(aResultSet.getDate("journal_date").getTime()));
            } catch (NullPointerException npe) {
                accjournal.setJournalDate(null);
            }
            try {
                accjournal.setTransactionId(aResultSet.getLong("transaction_id"));
            } catch (NullPointerException npe) {
                accjournal.setTransactionId(0);
            }
            try {
                accjournal.setTransactionTypeId(aResultSet.getInt("transaction_type_id"));
            } catch (NullPointerException npe) {
                accjournal.setTransactionTypeId(0);
            }
            try {
                accjournal.setTransactionReasonId(aResultSet.getInt("transaction_reason_id"));
            } catch (NullPointerException npe) {
                accjournal.setTransactionReasonId(0);
            }
            try {
                accjournal.setPayId(aResultSet.getLong("pay_id"));
            } catch (NullPointerException npe) {
                accjournal.setPayId(0);
            }
            try {
                accjournal.setPayTypeId(aResultSet.getInt("pay_type_id"));
            } catch (NullPointerException npe) {
                accjournal.setPayTypeId(0);
            }
            try {
                accjournal.setPayReasonId(aResultSet.getInt("pay_reason_id"));
            } catch (NullPointerException npe) {
                accjournal.setPayReasonId(0);
            }
            try {
                accjournal.setStoreId(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                accjournal.setStoreId(0);
            }
            try {
                accjournal.setBillTransactorId(aResultSet.getLong("bill_transactor_id"));
            } catch (NullPointerException npe) {
                accjournal.setBillTransactorId(0);
            }
            try {
                accjournal.setLedgerFolio(aResultSet.getString("ledger_folio"));
            } catch (NullPointerException npe) {
                accjournal.setLedgerFolio("");
            }
            try {
                accjournal.setAccCoaId(aResultSet.getInt("acc_coa_id"));
            } catch (NullPointerException npe) {
                accjournal.setAccCoaId(0);
            }
            try {
                accjournal.setAccountCode(aResultSet.getString("account_code"));
            } catch (NullPointerException npe) {
                accjournal.setAccountCode("");
            }
            try {
                accjournal.setCurrencyCode(aResultSet.getString("currency_code"));
            } catch (NullPointerException npe) {
                accjournal.setCurrencyCode("");
            }
            try {
                accjournal.setXrate(aResultSet.getDouble("xrate"));
            } catch (NullPointerException npe) {
                accjournal.setXrate(0);
            }
            try {
                accjournal.setDebitAmount(aResultSet.getDouble("debit_amount"));
            } catch (NullPointerException npe) {
                accjournal.setDebitAmount(0);
            }
            try {
                accjournal.setCreditAmount(aResultSet.getDouble("credit_amount"));
            } catch (NullPointerException npe) {
                accjournal.setCreditAmount(0);
            }
            try {
                accjournal.setNarration(aResultSet.getString("narration"));
            } catch (NullPointerException npe) {
                accjournal.setNarration("");
            }
            try {
                accjournal.setAccPeriodId(aResultSet.getInt("acc_period_id"));
            } catch (NullPointerException npe) {
                accjournal.setAccPeriodId(0);
            }
            try {
                accjournal.setAccChildAccountId(aResultSet.getInt("acc_child_account_id"));
            } catch (NullPointerException npe) {
                accjournal.setAccChildAccountId(0);
            }
            try {
                accjournal.setIsActive(aResultSet.getInt("is_active"));
            } catch (NullPointerException npe) {
                accjournal.setIsActive(0);
            }
            try {
                accjournal.setIsDeleted(aResultSet.getInt("is_deleted"));
            } catch (NullPointerException npe) {
                accjournal.setIsDeleted(0);
            }
            try {
                accjournal.setAddBy(aResultSet.getInt("add_by"));
            } catch (NullPointerException npe) {
                accjournal.setAddBy(0);
            }
            try {
                accjournal.setLastEditBy(aResultSet.getInt("last_edit_by"));
            } catch (NullPointerException npe) {
                accjournal.setLastEditBy(0);
            }
            try {
                accjournal.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                accjournal.setAddDate(null);
            }
            try {
                accjournal.setLastEditDate(new Date(aResultSet.getTimestamp("last_edit_date").getTime()));
            } catch (NullPointerException npe) {
                accjournal.setLastEditDate(null);
            }
            try {
                accjournal.setJobId(aResultSet.getLong("job_id"));
            } catch (NullPointerException npe) {
                accjournal.setJobId(0);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void saveAccJournal(AccJournal aAccJournal) {
        String sql = "";
        sql = "{call sp_insert_acc_journal(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setDate("in_journal_date", new java.sql.Date(aAccJournal.getJournalDate().getTime()));
            try {
                cs.setLong("in_transaction_id", aAccJournal.getTransactionId());
            } catch (NullPointerException npe) {
                cs.setLong("in_transaction_id", 0);
            }
            try {
                cs.setInt("in_transaction_type_id", aAccJournal.getTransactionTypeId());
            } catch (NullPointerException npe) {
                cs.setInt("in_transaction_type_id", 0);
            }
            try {
                cs.setInt("in_transaction_reason_id", aAccJournal.getTransactionReasonId());
            } catch (NullPointerException npe) {
                cs.setInt("in_transaction_reason_id", 0);
            }
            try {
                cs.setLong("in_pay_id", aAccJournal.getPayId());
            } catch (NullPointerException npe) {
                cs.setLong("in_pay_id", 0);
            }
            try {
                cs.setInt("in_pay_type_id", aAccJournal.getPayTypeId());
            } catch (NullPointerException npe) {
                cs.setInt("in_pay_type_id", 0);
            }
            try {
                cs.setInt("in_pay_reason_id", aAccJournal.getPayReasonId());
            } catch (NullPointerException npe) {
                cs.setInt("in_pay_reason_id", 0);
            }
            cs.setInt("in_store_id", aAccJournal.getStoreId());
            try {
                cs.setLong("in_bill_transactor_id", aAccJournal.getBillTransactorId());
            } catch (NullPointerException npe) {
                cs.setLong("in_bill_transactor_id", 0);
            }
            cs.setString("in_ledger_folio", aAccJournal.getLedgerFolio());
            cs.setInt("in_acc_coa_id", aAccJournal.getAccCoaId());
            cs.setString("in_account_code", aAccJournal.getAccountCode());
            cs.setDouble("in_debit_amount", aAccJournal.getDebitAmount());
            cs.setDouble("in_credit_amount", aAccJournal.getCreditAmount());
            cs.setString("in_narration", aAccJournal.getNarration());
            cs.setInt("in_acc_period_id", aAccJournal.getAccPeriodId());
            //cs.setInt("in_store_id", aAccJournal.getStoreId());
            try {
                cs.setLong("in_acc_child_account_id", aAccJournal.getAccChildAccountId());
            } catch (NullPointerException npe) {
                cs.setLong("in_acc_child_account_id", 0);
            }
            cs.setString("in_currency_code", aAccJournal.getCurrencyCode());
            cs.setDouble("in_xrate", aAccJournal.getXrate());
            cs.setInt("in_add_by", aAccJournal.getAddBy());
            try {
                cs.setLong("in_job_id", aAccJournal.getJobId());
            } catch (NullPointerException npe) {
                cs.setLong("in_job_id", 0);
            }
            cs.executeUpdate();
            //Post Jounal to Ledger
            new AccLedgerBean().postJounalToLedger(aAccJournal);
            //Post Jounal to specific table
            //this.saveAccJournalSpecify(aAccJournal);
            this.saveAccJournalSpecific(aAccJournal);
        } catch (SQLException se) {
            System.err.println("saveAccJournal:" + se.getMessage());
        }
    }

    public void saveAccJournalSpecify(AccJournal aAccJournal) {
        String sql = "";
        sql = "{call sp_insert_acc_journal_specify(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        String TableName = this.getSpecificTableName(aAccJournal.getAccountCode(), "JOURNAL");
        if (TableName.length() > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                cs.setString("in_table_name", TableName);
                cs.setDate("in_journal_date", new java.sql.Date(aAccJournal.getJournalDate().getTime()));
                try {
                    cs.setLong("in_transaction_id", aAccJournal.getTransactionId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_transaction_id", 0);
                }
                try {
                    cs.setInt("in_transaction_type_id", aAccJournal.getTransactionTypeId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_transaction_type_id", 0);
                }
                try {
                    cs.setInt("in_transaction_reason_id", aAccJournal.getTransactionReasonId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_transaction_reason_id", 0);
                }
                try {
                    cs.setLong("in_pay_id", aAccJournal.getPayId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_pay_id", 0);
                }
                try {
                    cs.setInt("in_pay_type_id", aAccJournal.getPayTypeId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_pay_type_id", 0);
                }
                try {
                    cs.setInt("in_pay_reason_id", aAccJournal.getPayReasonId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_pay_reason_id", 0);
                }
                cs.setInt("in_store_id", aAccJournal.getStoreId());
                try {
                    cs.setLong("in_bill_transactor_id", aAccJournal.getBillTransactorId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_bill_transactor_id", 0);
                }
                cs.setString("in_ledger_folio", aAccJournal.getLedgerFolio());
                cs.setInt("in_acc_coa_id", aAccJournal.getAccCoaId());
                cs.setString("in_account_code", aAccJournal.getAccountCode());
                cs.setDouble("in_debit_amount", aAccJournal.getDebitAmount());
                cs.setDouble("in_credit_amount", aAccJournal.getCreditAmount());
                cs.setString("in_narration", aAccJournal.getNarration());
                cs.setInt("in_acc_period_id", aAccJournal.getAccPeriodId());
                //cs.setInt("in_store_id", aAccJournal.getStoreId());
                try {
                    cs.setLong("in_acc_child_account_id", aAccJournal.getAccChildAccountId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_acc_child_account_id", 0);
                }
                cs.setString("in_currency_code", aAccJournal.getCurrencyCode());
                cs.setDouble("in_xrate", aAccJournal.getXrate());
                cs.setInt("in_add_by", aAccJournal.getAddBy());
                try {
                    cs.setLong("in_job_id", aAccJournal.getJobId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_job_id", 0);
                }
                cs.executeUpdate();
                //Post JounalSpecify to LedgerSpecify
                new AccLedgerBean().postJounalToLedgerSpecify(aAccJournal);
            } catch (SQLException se) {
                System.err.println("saveAccJournalSpecify:" + se.getMessage());
            }
        }
    }

    public void saveAccJournalSpecific(AccJournal aAccJournal) {
        String sql = "";
        String TableName = this.getSpecificTableName(aAccJournal.getAccountCode(), "JOURNAL");
        sql = "{call sp_insert_" + TableName + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        if (TableName.length() > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                cs.setDate("in_journal_date", new java.sql.Date(aAccJournal.getJournalDate().getTime()));
                try {
                    cs.setLong("in_transaction_id", aAccJournal.getTransactionId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_transaction_id", 0);
                }
                try {
                    cs.setInt("in_transaction_type_id", aAccJournal.getTransactionTypeId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_transaction_type_id", 0);
                }
                try {
                    cs.setInt("in_transaction_reason_id", aAccJournal.getTransactionReasonId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_transaction_reason_id", 0);
                }
                try {
                    cs.setLong("in_pay_id", aAccJournal.getPayId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_pay_id", 0);
                }
                try {
                    cs.setInt("in_pay_type_id", aAccJournal.getPayTypeId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_pay_type_id", 0);
                }
                try {
                    cs.setInt("in_pay_reason_id", aAccJournal.getPayReasonId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_pay_reason_id", 0);
                }
                cs.setInt("in_store_id", aAccJournal.getStoreId());
                try {
                    cs.setLong("in_bill_transactor_id", aAccJournal.getBillTransactorId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_bill_transactor_id", 0);
                }
                cs.setString("in_ledger_folio", aAccJournal.getLedgerFolio());
                cs.setInt("in_acc_coa_id", aAccJournal.getAccCoaId());
                cs.setString("in_account_code", aAccJournal.getAccountCode());
                cs.setDouble("in_debit_amount", aAccJournal.getDebitAmount());
                cs.setDouble("in_credit_amount", aAccJournal.getCreditAmount());
                cs.setString("in_narration", aAccJournal.getNarration());
                cs.setInt("in_acc_period_id", aAccJournal.getAccPeriodId());
                //cs.setInt("in_store_id", aAccJournal.getStoreId());
                try {
                    cs.setLong("in_acc_child_account_id", aAccJournal.getAccChildAccountId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_acc_child_account_id", 0);
                }
                cs.setString("in_currency_code", aAccJournal.getCurrencyCode());
                cs.setDouble("in_xrate", aAccJournal.getXrate());
                cs.setInt("in_add_by", aAccJournal.getAddBy());
                try {
                    cs.setLong("in_job_id", aAccJournal.getJobId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_job_id", 0);
                }
                cs.executeUpdate();
                //Post JounalSpecify to LedgerSpecify
                //new AccLedgerBean().postJounalToLedgerSpecify(aAccJournal);
                new AccLedgerBean().postJounalToLedgerSpecific(aAccJournal);
            } catch (Exception e) {
                System.err.println("saveAccJournalSpecific:" + e.getMessage());
            }
        }
    }

    public String getSpecificTableName(String aAccountCode, String aReturnTable) {
        String TableNameJournal = "";
        String TableNameLedger = "";
        String TableName = "";
        try {
            if (aAccountCode.length() > 0 && aAccountCode.startsWith("1-00-010-010")) {// AR Trade
                TableNameJournal = "acc_journal_receivable";
                TableNameLedger = "acc_ledger_receivable";
            } else if (aAccountCode.length() > 0 && aAccountCode.startsWith("2-00-000-010")) {//AP Trade
                TableNameJournal = "acc_journal_payable";
                TableNameLedger = "acc_ledger_payable";
            } else if (aAccountCode.length() > 0 && aAccountCode.startsWith("2-00-000-070")) {//Prepaid Income/Cust Dep
                TableNameJournal = "acc_journal_prepaid";
                TableNameLedger = "acc_ledger_prepaid";
            } else if (aAccountCode.length() > 0 && aAccountCode.startsWith("1-00-030-050")) {//Prepaid Expense/Sup Dep
                TableNameJournal = "acc_journal_prepaid";
                TableNameLedger = "acc_ledger_prepaid";
            }

            //set return
            if (aReturnTable.equals("JOURNAL")) {
                TableName = TableNameJournal;
            } else if (aReturnTable.equals("LEDGER")) {
                TableName = TableNameLedger;
            }
        } catch (Exception e) {

        }
        return TableName;
    }

    public void postJournalSaleInvoice(Trans aTrans, List<TransItem> aTransItems, Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        try {
            //Cash account money paid to
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aTrans.getAccChildAccountId());
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
                CashAccountCode = "";
            }
            //Customer deposit acc
            int DepositAccountId = 0;
            String DepositAccountCode = "2-00-000-070";//Customer Advances and Deposits Payable
            try {
                DepositAccountId = new AccCoaBean().getAccCoaByCodeOrId(DepositAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                DepositAccountId = 0;
            }
            //loyalty acc
            int LoyaltyAccountId = 0;
            String LoyaltyAccountCode = "5-20-430-010";//Expense Loyalty
            try {
                LoyaltyAccountId = new AccCoaBean().getAccCoaByCodeOrId(LoyaltyAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                LoyaltyAccountId = 0;
            }
            int ARAccountId = 0;
            String ARAccountCode = "1-00-010-010";//AR Trade
            try {
                ARAccountId = new AccCoaBean().getAccCoaByCodeOrId(ARAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                ARAccountId = 0;
            }
            int SalesVatOutputTaxAccountId = 0;
            String SalesVatOutputTaxAccountCode = "2-00-000-090";//Sales VAT Output Payable
            try {
                SalesVatOutputTaxAccountId = new AccCoaBean().getAccCoaByCodeOrId(SalesVatOutputTaxAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                SalesVatOutputTaxAccountId = 0;
            }
            int SalesDiscAccountId = 0;
            String SalesDiscAccountCode = "4-10-000-030";//SALES Discounts (Contra-Revenue Acc)
            try {
                SalesDiscAccountId = new AccCoaBean().getAccCoaByCodeOrId(SalesDiscAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                SalesDiscAccountId = 0;
            }
            double GrossSalesAmount = 0;
            double NetSalesAmount = 0;
            double PaidCashAmount = 0;
            double PaidLoyaltyAmount = 0;
            double ReceivableAmount = 0;
            double VatOutputTaxAmount = 0;
            double CashDiscountAmount = 0;

            GrossSalesAmount = aTrans.getGrandTotal();
            PaidLoyaltyAmount = aTrans.getSpendPointsAmount();
            if ((aTrans.getChangeAmount() > 0)) {
                PaidCashAmount = aTrans.getGrandTotal() - aTrans.getSpendPointsAmount();
            } else {
                PaidCashAmount = aTrans.getAmountTendered();
            }
            ReceivableAmount = aTrans.getGrandTotal() - (PaidCashAmount + PaidLoyaltyAmount);
            VatOutputTaxAmount = aTrans.getTotalVat();
            CashDiscountAmount = aTrans.getCashDiscount();
            NetSalesAmount = GrossSalesAmount - VatOutputTaxAmount;//-CashDiscountAmount;

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aTrans.getCurrencyCode());
            accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(aTrans.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aTrans.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //CASH RECEIVED
            if (PaidCashAmount > 0) {
                if (aPay.getPayMethodId() == 6 && aPay.getPayReasonId() == 90) {//For prepaid income
                    //Debit Deposit account
                    accjournal.setAccChildAccountId(0);
                    if (aBillTransactor != null) {
                        accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                    }
                    accjournal.setAccCoaId(DepositAccountId);
                    accjournal.setAccountCode(DepositAccountCode);
                    accjournal.setDebitAmount(PaidCashAmount);
                    accjournal.setCreditAmount(0);
                    accjournal.setNarration("CUSTOMER DEPOSIT CONSUMED");
                    this.saveAccJournal(accjournal);
                } else {//for cash
                    //Debit cash account
                    accjournal.setAccChildAccountId(aTrans.getAccChildAccountId());
                    if (aBillTransactor != null) {
                        accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                    }
                    accjournal.setAccCoaId(CashAccountId);
                    accjournal.setAccountCode(CashAccountCode);
                    accjournal.setDebitAmount(PaidCashAmount);
                    accjournal.setCreditAmount(0);
                    accjournal.setNarration("PAYMENT RECEIVED FROM CLIENT");
                    this.saveAccJournal(accjournal);
                }
            }
            //CREDIT MADE
            if (ReceivableAmount > 0) {
                //Debit cash account
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(ARAccountId);
                accjournal.setAccountCode(ARAccountCode);
                accjournal.setDebitAmount(ReceivableAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CREDIT RECEIVABLE FROM CLIENT");
                this.saveAccJournal(accjournal);
            }
            //Sales tax (VAT)
            if (VatOutputTaxAmount > 0) {
                accjournal.setAccChildAccountId(aTrans.getAccChildAccountId());
                accjournal.setBillTransactorId(0);
                accjournal.setAccCoaId(SalesVatOutputTaxAccountId);
                accjournal.setAccountCode(SalesVatOutputTaxAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(VatOutputTaxAmount);
                accjournal.setNarration("VAT OUTPUT PAYABLE FROM CLIENT");
                this.saveAccJournal(accjournal);
            }
            //SALES DISCOUNT (DISCOUNT ALLOWED) - Cash Discount
            if (CashDiscountAmount > 0) {
                //Debit Disc Allowed
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(SalesDiscAccountId);
                accjournal.setAccountCode(SalesDiscAccountCode);
                accjournal.setDebitAmount(CashDiscountAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("SALES DISCOUNT TO CLIENT");
                this.saveAccJournal(accjournal);
            }
            //SALES REVENUE
            //1. Sales revues per Sales Account
            List<TransItem> ati = new TransItemBean().getTransItemsSummaryByItemType(aTrans.getTransactionId());
            //2. post account sales revenue
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            String ItemSalesAccountCode = "";
            int ItemSalesAccountId = 0;
            double ItemNetSalesAmount = 0;//Amt exc VAT
            while (ListItemIndex < ListItemNo) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                if (ati.get(ListItemIndex).getItem_type().equals("PRODUCT")) {//4-10-000-010 - SALES Products
                    ItemSalesAccountCode = "4-10-000-010";
                } else if (ati.get(ListItemIndex).getItem_type().equals("SERVICE")) {//4-10-000-020 - SALES Services	
                    ItemSalesAccountCode = "4-10-000-020";
                }
                ItemNetSalesAmount = ati.get(ListItemIndex).getAmountExcVat();
                try {
                    ItemSalesAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemSalesAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemSalesAccountId = 0;
                }
                if (ItemSalesAccountId > 0 && ItemNetSalesAmount > 0) {
                    accjournal.setAccCoaId(ItemSalesAccountId);
                    accjournal.setAccountCode(ItemSalesAccountCode);
                    accjournal.setDebitAmount(0);
                    accjournal.setCreditAmount(ItemNetSalesAmount);
                    accjournal.setNarration("ITEM SALES REVENUE");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex = ListItemIndex + 1;
            }

            //CREDIT COS - InventoryAcc
            //1. Cost by InventoryAcc
            List<TransItem> ati2 = new TransItemBean().getInventoryCostByTrans(aTrans.getTransactionId());
            //2. Credit inventory account
            int ListItemIndex2 = 0;
            int ListItemNo2 = ati2.size();
            String ItemInventoryAccountCode = "";
            int ItemInventoryAccountId = 0;
            double ItemInventoryCostAmount = 0;
            while (ListItemIndex2 < ListItemNo2) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(0);
                }
                ItemInventoryAccountCode = ati2.get(ListItemIndex2).getAccountCode();
                ItemInventoryCostAmount = ati2.get(ListItemIndex2).getUnitCostPrice();
                try {
                    ItemInventoryAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemInventoryAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemInventoryAccountId = 0;
                }
                if (ItemInventoryAccountId > 0 && ItemInventoryCostAmount > 0) {
                    accjournal.setAccCoaId(ItemInventoryAccountId);
                    accjournal.setAccountCode(ItemInventoryAccountCode);
                    accjournal.setDebitAmount(0);
                    accjournal.setCreditAmount(ItemInventoryCostAmount);
                    accjournal.setNarration("INVENTORY COST OF SALE");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex2 = ListItemIndex2 + 1;
            }

            //DEBIT COS - COS ACC
            //1. Cost by ItemType
            List<TransItem> ati3 = new TransItemBean().getInventoryItemTypeCostByTrans(aTrans.getTransactionId());
            //2. Debit COS
            int ListItemIndex3 = 0;
            int ListItemNo3 = ati3.size();
            String ItemInventoryItemTypeAccountCode = "";
            int ItemInventoryItemTypeAccountId = 0;
            double ItemInventoryItemTypeCostAmount = 0;
            while (ListItemIndex3 < ListItemNo3) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(0);
                }
                if (ati3.get(ListItemIndex3).getItem_type().equals("PRODUCT")) {//Cost of Purchase - Products	5-10-000-010
                    ItemInventoryItemTypeAccountCode = "5-10-000-010";
                } else if (ati3.get(ListItemIndex3).getItem_type().equals("SERVICE")) {//Cost of Purchase - Services	5-10-000-020	
                    ItemInventoryItemTypeAccountCode = "5-10-000-020";
                }
                ItemInventoryItemTypeCostAmount = ati3.get(ListItemIndex3).getUnitCostPrice();
                try {
                    ItemInventoryItemTypeAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemInventoryItemTypeAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemInventoryItemTypeAccountId = 0;
                }
                if (ItemInventoryItemTypeAccountId > 0 && ItemInventoryItemTypeCostAmount > 0) {
                    accjournal.setAccCoaId(ItemInventoryItemTypeAccountId);
                    accjournal.setAccountCode(ItemInventoryItemTypeAccountCode);
                    accjournal.setDebitAmount(ItemInventoryItemTypeCostAmount);
                    accjournal.setCreditAmount(0);
                    accjournal.setNarration("INVENTORY COST OF SALE");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex3 = ListItemIndex3 + 1;
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public void postJournalStockConsumption(Trans aTrans, int aAccPeriodId) {
        long JobId = 0;
        try {
            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(0);
            accjournal.setPayTypeId(0);
            accjournal.setPayReasonId(0);
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aTrans.getCurrencyCode());
            accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(aTrans.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aTrans.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }

            //CREDIT COS - InventoryAcc
            //1. Cost by InventoryAcc
            List<TransItem> ati2 = new TransItemBean().getInventoryCostByTransConsume(aTrans.getTransactionId());
            //2. Credit inventory account
            int ListItemIndex2 = 0;
            int ListItemNo2 = ati2.size();
            String ItemInventoryAccountCode = "";
            int ItemInventoryAccountId = 0;
            double ItemInventoryCostAmount = 0;
            while (ListItemIndex2 < ListItemNo2) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(0);
                }
                ItemInventoryAccountCode = ati2.get(ListItemIndex2).getAccountCode();
                ItemInventoryCostAmount = ati2.get(ListItemIndex2).getAmountExcVat();
                try {
                    ItemInventoryAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemInventoryAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemInventoryAccountId = 0;
                }
                if (ItemInventoryAccountId > 0 && ItemInventoryCostAmount > 0) {
                    accjournal.setAccCoaId(ItemInventoryAccountId);
                    accjournal.setAccountCode(ItemInventoryAccountCode);
                    accjournal.setDebitAmount(0);
                    accjournal.setCreditAmount(ItemInventoryCostAmount);
                    accjournal.setNarration("CONSUMPTION INVENTORY COST");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex2 = ListItemIndex2 + 1;
            }

            //DEBIT COS - COS ACC
            //1. Cost by ItemType
            List<TransItem> ati3 = new TransItemBean().getInventoryItemTypeCostByTransConsume(aTrans.getTransactionId());
            //2. Debit COS
            int ListItemIndex3 = 0;
            int ListItemNo3 = ati3.size();
            String ItemInventoryItemTypeAccountCode = "";
            int ItemInventoryItemTypeAccountId = 0;
            double ItemInventoryItemTypeCostAmount = 0;
            while (ListItemIndex3 < ListItemNo3) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(0);
                }
                if (ati3.get(ListItemIndex3).getItem_type().equals("PRODUCT")) {//Cost of Purchase - Products	5-10-000-010
                    ItemInventoryItemTypeAccountCode = "5-10-000-010";
                } else if (ati3.get(ListItemIndex3).getItem_type().equals("SERVICE")) {//Cost of Purchase - Services	5-10-000-020	
                    ItemInventoryItemTypeAccountCode = "5-10-000-020";
                }
                ItemInventoryItemTypeCostAmount = ati3.get(ListItemIndex3).getAmountExcVat();
                try {
                    ItemInventoryItemTypeAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemInventoryItemTypeAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemInventoryItemTypeAccountId = 0;
                }
                if (ItemInventoryItemTypeAccountId > 0 && ItemInventoryItemTypeCostAmount > 0) {
                    accjournal.setAccCoaId(ItemInventoryItemTypeAccountId);
                    accjournal.setAccountCode(ItemInventoryItemTypeAccountCode);
                    accjournal.setDebitAmount(ItemInventoryItemTypeCostAmount);
                    accjournal.setCreditAmount(0);
                    accjournal.setNarration("CONSUMPTION INVENTORY COST");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex3 = ListItemIndex3 + 1;
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public void postJournalProduction(long aTransId) {
        long JobId = 0;
        try {
            TransProduction aTrans = new TransProductionBean().getTransProductionById(aTransId);
            int aAccPeriodId = new AccPeriodBean().getAccPeriod(aTrans.getTransactionDate()).getAccPeriodId();
            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(0);
            accjournal.setPayTypeId(0);
            accjournal.setPayReasonId(0);
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(new ItemBean().getItem(aTrans.getOutputItemId()).getCurrencyCode());
            accjournal.setXrate(1);//aTrans.getXrate()
            accjournal.setAddBy(aTrans.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aTrans.getTransactor_id());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }

            //1. CREDIT Input->InventoryAcc
            List<TransItem> ati2 = new TransItemBean().getInventoryCostByTransInput(aTrans.getTransactionId());
            int ListItemIndex2 = 0;
            int ListItemNo2 = ati2.size();
            String ItemInventoryAccountCode = "";
            int ItemInventoryAccountId = 0;
            double ItemInventoryCostAmount = 0;
            while (ListItemIndex2 < ListItemNo2) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(0);
                }
                ItemInventoryAccountCode = ati2.get(ListItemIndex2).getAccountCode();
                ItemInventoryCostAmount = ati2.get(ListItemIndex2).getAmount();
                try {
                    ItemInventoryAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemInventoryAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemInventoryAccountId = 0;
                }
                if (ItemInventoryAccountId > 0 && ItemInventoryCostAmount > 0) {
                    accjournal.setAccCoaId(ItemInventoryAccountId);
                    accjournal.setAccountCode(ItemInventoryAccountCode);
                    accjournal.setDebitAmount(0);
                    accjournal.setCreditAmount(ItemInventoryCostAmount);
                    accjournal.setNarration("PRODUCTION INPUT INVENTORY COST");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex2 = ListItemIndex2 + 1;
            }

            //1. DEBIT Output->InventoryAcc
            List<TransItem> ati3 = new TransItemBean().getInventoryCostByTransOutput(aTrans.getTransactionId());
            int ListItemIndex3 = 0;
            int ListItemNo3 = ati3.size();
            String ItemInventoryItemTypeAccountCode = "";
            int ItemInventoryItemTypeAccountId = 0;
            double ItemInventoryItemTypeCostAmount = 0;
            while (ListItemIndex3 < ListItemNo3) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(0);
                }
                ItemInventoryItemTypeAccountCode = ati3.get(ListItemIndex3).getAccountCode();
                ItemInventoryItemTypeCostAmount = ati3.get(ListItemIndex3).getAmount();
                try {
                    ItemInventoryItemTypeAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemInventoryItemTypeAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemInventoryItemTypeAccountId = 0;
                }
                if (ItemInventoryItemTypeAccountId > 0 && ItemInventoryItemTypeCostAmount > 0) {
                    accjournal.setAccCoaId(ItemInventoryItemTypeAccountId);
                    accjournal.setAccountCode(ItemInventoryItemTypeAccountCode);
                    accjournal.setDebitAmount(ItemInventoryItemTypeCostAmount);
                    accjournal.setCreditAmount(0);
                    accjournal.setNarration("PRODUCTION OUTPUT INVENTORY COST");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex3 = ListItemIndex3 + 1;
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public int postJournalReverse(Trans aTrans, Pay aPay) {
        int x = 0;
        int aTransTypeId = 0;
        int aTransReasId = 0;
        long aTransId = 0;
        int aPayTypeId = 0;
        int aPayReasId = 0;
        long aPayId = 0;
        try {
            try {
                aTransTypeId = aTrans.getTransactionTypeId();
            } catch (NullPointerException npe) {
                aTransTypeId = 0;
            }
            try {
                aTransReasId = aTrans.getTransactionReasonId();
            } catch (NullPointerException npe) {
                aTransReasId = 0;
            }
            try {
                aTransId = aTrans.getTransactionId();
            } catch (NullPointerException npe) {
                aTransId = 0;
            }
            try {
                aPayTypeId = aPay.getPayTypeId();
            } catch (NullPointerException npe) {
                aPayTypeId = 0;
            }
            try {
                aPayReasId = aPay.getPayReasonId();
            } catch (NullPointerException npe) {
                aPayReasId = 0;
            }
            try {
                aPayId = aPay.getPayId();
            } catch (NullPointerException npe) {
                aPayId = 0;
            }
            //get job Id
            long JobId = 0;
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }

            List<AccJournal> objs = new AccJournalBean().getAccJournalListByTrans(aTransTypeId, aTransReasId, aTransId, aPayTypeId, aPayReasId, aPayId);
            AccJournal obj = null;
            int ListItemIndex = 0;
            int ListItemNo = objs.size();
            double OldDrAmount = 0;
            double OldCrAmount = 0;
            while (ListItemIndex < ListItemNo) {
                obj = new AccJournal();
                obj = objs.get(ListItemIndex);
                obj.setJobId(JobId);
                obj.setNarration("REVERSE-" + obj.getNarration());
                obj.setJournalDate(new CompanySetting().getCURRENT_SERVER_DATE());
                OldDrAmount = obj.getDebitAmount();
                OldCrAmount = obj.getCreditAmount();
                if (OldDrAmount > 0) {
                    obj.setCreditAmount(obj.getDebitAmount());
                    obj.setDebitAmount(0);
                    this.saveAccJournal(obj);
                } else if (OldCrAmount > 0) {
                    obj.setDebitAmount(obj.getCreditAmount());
                    obj.setCreditAmount(0);
                    this.saveAccJournal(obj);
                }
                ListItemIndex = ListItemIndex + 1;
            }
            x = 1;
        } catch (Exception e) {
            x = 0;
            e.printStackTrace();
        }
        return x;
    }

    public int postJournalCancelCashReceipt(Pay aPay) {
        int x = 0;
        int aTransTypeId = 0;
        int aTransReasId = 0;
        long aTransId = 0;
        int aPayTypeId = 0;
        int aPayReasId = 0;
        long aPayId = 0;
        try {
            try {
                aPayTypeId = aPay.getPayTypeId();
            } catch (NullPointerException npe) {
                aPayTypeId = 0;
            }
            try {
                aPayReasId = aPay.getPayReasonId();
            } catch (NullPointerException npe) {
                aPayReasId = 0;
            }
            try {
                aPayId = aPay.getPayId();
            } catch (NullPointerException npe) {
                aPayId = 0;
            }
            //get job Id
            long JobId = 0;
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }

            List<AccJournal> objs = new AccJournalBean().getAccJournalListByTrans(aTransTypeId, aTransReasId, aTransId, aPayTypeId, aPayReasId, aPayId);
            AccJournal obj = null;
            int ListItemIndex = 0;
            int ListItemNo = objs.size();
            double OldDrAmount = 0;
            double OldCrAmount = 0;
            while (ListItemIndex < ListItemNo) {
                obj = new AccJournal();
                obj = objs.get(ListItemIndex);
                obj.setJobId(JobId);
                obj.setNarration("REVERSE-" + obj.getNarration());
                obj.setJournalDate(new CompanySetting().getCURRENT_SERVER_DATE());
                OldDrAmount = obj.getDebitAmount();
                OldCrAmount = obj.getCreditAmount();
                if (OldDrAmount > 0) {
                    obj.setCreditAmount(obj.getDebitAmount());
                    obj.setDebitAmount(0);
                    this.saveAccJournal(obj);
                } else if (OldCrAmount > 0) {
                    obj.setDebitAmount(obj.getCreditAmount());
                    obj.setCreditAmount(0);
                    this.saveAccJournal(obj);
                }
                ListItemIndex = ListItemIndex + 1;
            }
            x = 1;
        } catch (Exception e) {
            x = 0;
            e.printStackTrace();
        }
        return x;
    }

    public void postJournalSaleInvoiceReverse(Trans aTrans, List<TransItem> aTransItems, Pay aPay, int aAccPeriodId) {
        try {
            //Cash account money paid to
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aTrans.getAccChildAccountId());
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
                CashAccountCode = "";
            }
            int LoyaltyAccountId = 0;
            String LoyaltyAccountCode = "5-20-430-010";//Expense Loyalty
            try {
                LoyaltyAccountId = new AccCoaBean().getAccCoaByCodeOrId(LoyaltyAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                LoyaltyAccountId = 0;
            }
            int ARAccountId = 0;
            String ARAccountCode = "1-00-010-010";//AR Trade
            try {
                ARAccountId = new AccCoaBean().getAccCoaByCodeOrId(ARAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                ARAccountId = 0;
            }
            int SalesVatOutputTaxAccountId = 0;
            String SalesVatOutputTaxAccountCode = "2-00-000-090";//Sales VAT Output Payable
            try {
                SalesVatOutputTaxAccountId = new AccCoaBean().getAccCoaByCodeOrId(SalesVatOutputTaxAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                SalesVatOutputTaxAccountId = 0;
            }
            int SalesDiscAccountId = 0;
            String SalesDiscAccountCode = "5-10-000-060";//Cost of Purchase - Discounts
            try {
                SalesDiscAccountId = new AccCoaBean().getAccCoaByCodeOrId(SalesDiscAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                SalesDiscAccountId = 0;
            }
            double GrossSalesAmount = 0;
            double NetSalesAmount = 0;
            double PaidCashAmount = 0;
            double PaidLoyaltyAmount = 0;
            double ReceivableAmount = 0;
            double VatOutputTaxAmount = 0;
            double CashDiscountAmount = 0;

            GrossSalesAmount = aTrans.getGrandTotal();
            PaidLoyaltyAmount = aTrans.getSpendPointsAmount();
            if ((aTrans.getChangeAmount() > 0)) {
                PaidCashAmount = aTrans.getGrandTotal() - aTrans.getSpendPointsAmount();
            } else {
                PaidCashAmount = aTrans.getAmountTendered();
            }
            ReceivableAmount = aTrans.getGrandTotal() - (PaidCashAmount + PaidLoyaltyAmount);
            VatOutputTaxAmount = aTrans.getTotalVat();
            CashDiscountAmount = aTrans.getCashDiscount();
            NetSalesAmount = GrossSalesAmount - VatOutputTaxAmount;//-CashDiscountAmount;

            AccJournal accjournal = new AccJournal();
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aTrans.getCurrencyCode());
            accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aTrans.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //CASH RECEIVED
            if (PaidCashAmount > 0) {
                //Debit cash account
                accjournal.setAccChildAccountId(aTrans.getAccChildAccountId());
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("PAYMENT RECEIVED FROM CLIENT");
                this.saveAccJournal(accjournal);
            }
            //CREDIT MADE
            if (ReceivableAmount > 0) {
                //Debit cash account
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(ARAccountId);
                accjournal.setAccountCode(ARAccountCode);
                accjournal.setDebitAmount(ReceivableAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CREDIT RECEIVABLE FROM CLIENT");
                this.saveAccJournal(accjournal);
            }
            //Sales tax (VAT)
            if (VatOutputTaxAmount > 0) {
                accjournal.setAccChildAccountId(aTrans.getAccChildAccountId());
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(SalesVatOutputTaxAccountId);
                accjournal.setAccountCode(SalesVatOutputTaxAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(VatOutputTaxAmount);
                accjournal.setNarration("VAT OUTPUT PAYABLE FROM CLIENT");
                this.saveAccJournal(accjournal);
            }
            //Sales Revenue
            //1. Sales revues per Sales Account
            List<TransItem> ati = new TransItemBean().getTransItemsSummaryByAccount(aTrans.getTransactionId());
            //2. post account sales revenue
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            String ItemSalesAccountCode = "";
            int ItemSalesAccountId = 0;
            double ItemNetSalesAmount = 0;//Amt exc VAT
            while (ListItemIndex < ListItemNo) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                ItemSalesAccountCode = ati.get(ListItemIndex).getAccountCode();
                ItemNetSalesAmount = ati.get(ListItemIndex).getAmountExcVat();
                try {
                    ItemSalesAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemSalesAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemSalesAccountId = 0;
                }
                if (ItemSalesAccountId > 0 && ItemNetSalesAmount > 0) {
                    accjournal.setAccCoaId(ItemSalesAccountId);
                    accjournal.setAccountCode(ItemSalesAccountCode);
                    accjournal.setDebitAmount(0);
                    accjournal.setCreditAmount(ItemNetSalesAmount);
                    accjournal.setNarration("ITEM SALES REVENUE");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public void postJournalDisposeStock(Trans aTrans, int aAccPeriodId) {
        long JobId = 0;
        try {
            //Loss Inv Expense Acc(LIE)
            int LIEAccountId = 0;
            String LIEAccountCode = "5-10-000-090";//Loss on inventory write off
            try {
                LIEAccountId = new AccCoaBean().getAccCoaByCodeOrId(LIEAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                LIEAccountId = 0;
            }
            //Invetory Stores Acccount(Inventory)
            int InventoryAccountId = 0;
            String InventoryAccountCode = "1-00-020-010";
            try {
                InventoryAccountId = new AccCoaBean().getAccCoaByCodeOrId(InventoryAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                InventoryAccountId = 0;
            }
            double TotalDisposeAmountt = 0;
            TotalDisposeAmountt = aTrans.getGrandTotal();
            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(0);
            accjournal.setPayTypeId(0);
            accjournal.setPayReasonId(0);
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setAccChildAccountId(aTrans.getAccChildAccountId());
            accjournal.setCurrencyCode(aTrans.getCurrencyCode());
            accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(aTrans.getAddUserDetailId());

            //Debit LIE
            if (TotalDisposeAmountt > 0) {
                accjournal.setAccCoaId(LIEAccountId);
                accjournal.setAccountCode(LIEAccountCode);
                accjournal.setDebitAmount(TotalDisposeAmountt);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("STOCK DISPOSED LOSS");
                this.saveAccJournal(accjournal);
            }
            //Credit Invetory/Cost Account
            //Cost by Inventory/Cost Acc
            List<TransItem> ati2 = new TransItemBean().getInventoryCostByTransDispose(aTrans.getTransactionId());
            int ListItemIndex2 = 0;
            int ListItemNo2 = ati2.size();
            String ItemInventoryAccountCode = "";
            int ItemInventoryAccountId = 0;
            double ItemInventoryCostAmount = 0;
            while (ListItemIndex2 < ListItemNo2) {
                accjournal.setAccChildAccountId(0);
                accjournal.setBillTransactorId(0);
                ItemInventoryAccountCode = ati2.get(ListItemIndex2).getAccountCode();
                ItemInventoryCostAmount = ati2.get(ListItemIndex2).getAmountExcVat();
                try {
                    ItemInventoryAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemInventoryAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemInventoryAccountId = 0;
                }
                if (ItemInventoryAccountId > 0 && ItemInventoryCostAmount > 0) {
                    accjournal.setAccCoaId(ItemInventoryAccountId);
                    accjournal.setAccountCode(ItemInventoryAccountCode);
                    accjournal.setDebitAmount(0);
                    accjournal.setCreditAmount(ItemInventoryCostAmount);
                    accjournal.setNarration("STOCK DISPOSED LOSS");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex2 = ListItemIndex2 + 1;
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public void postJournalJournalEntry(Trans aTrans, List<TransItem> aActiveTransItems, int aAccPeriodId) {
        long JobId = 0;
        try {
            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(0);
            accjournal.setPayTypeId(0);
            accjournal.setPayReasonId(0);
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aTrans.getCurrencyCode());
            accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            accjournal.setBillTransactorId(aTrans.getBillTransactorId());
            //1. post account journal entries
            List<TransItem> ati = aActiveTransItems;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            String ItemAccountCode = "";
            int ItemAccountId = 0;
            String ItemChildAccountCode = "";
            int ItemChildAccountId = 0;
            double DebitAmount = 0;
            double CreditAmount = 0;
            String Narration = "";
            while (ListItemIndex < ListItemNo) {
                ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                ItemChildAccountCode = ati.get(ListItemIndex).getCodeSpecific();
                DebitAmount = ati.get(ListItemIndex).getAmountExcVat();
                CreditAmount = ati.get(ListItemIndex).getAmountIncVat();
                Narration = ati.get(ListItemIndex).getNarration();
                try {
                    ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemAccountId = 0;
                }
                try {
                    ItemChildAccountId = new AccChildAccountBean().getAccChildAccByCode(ItemChildAccountCode).getAccChildAccountId();
                } catch (NullPointerException npe) {
                    ItemChildAccountId = 0;
                }
                accjournal.setNarration(Narration);
                accjournal.setAccCoaId(ItemAccountId);
                accjournal.setAccountCode(ItemAccountCode);
                accjournal.setAccChildAccountId(ItemChildAccountId);
                accjournal.setDebitAmount(DebitAmount);
                accjournal.setCreditAmount(CreditAmount);
                this.saveAccJournal(accjournal);
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public void postJournalOpenBalance(Trans aTrans, List<TransItem> aActiveTransItems, int aAccPeriodId) {
        long JobId = 0;
        try {
            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(0);
            accjournal.setPayTypeId(0);
            accjournal.setPayReasonId(0);
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aTrans.getCurrencyCode());
            accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            accjournal.setBillTransactorId(0);
            //1. post account journal entries
            List<TransItem> ati = aActiveTransItems;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            String ItemAccountCode = "";
            int ItemAccountId = 0;
            String ItemChildAccountCode = "";
            int ItemChildAccountId = 0;
            double DebitAmount = 0;
            double CreditAmount = 0;
            String Narration = "";
            while (ListItemIndex < ListItemNo) {
                //customer receivable
                if (aTrans.getTransactionReasonId() == 117) {
                    accjournal.setBillTransactorId(aTrans.getBillTransactorId());
                    ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                    DebitAmount = ati.get(ListItemIndex).getAmount();
                    CreditAmount = 0;
                    Narration = "Customer Opening Balance";
                    try {
                        ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                    } catch (NullPointerException npe) {
                        ItemAccountId = 0;
                    }
                    ItemChildAccountId = 0;
                    accjournal.setNarration(Narration);
                    accjournal.setAccCoaId(ItemAccountId);
                    accjournal.setAccountCode(ItemAccountCode);
                    accjournal.setAccChildAccountId(ItemChildAccountId);
                    accjournal.setDebitAmount(DebitAmount);
                    accjournal.setCreditAmount(CreditAmount);
                    this.saveAccJournal(accjournal);
                }
                //supplier payable
                if (aTrans.getTransactionReasonId() == 118) {
                    accjournal.setBillTransactorId(aTrans.getBillTransactorId());
                    ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                    DebitAmount = 0;
                    CreditAmount = ati.get(ListItemIndex).getAmount();
                    Narration = "Supplier Opening Balance";
                    try {
                        ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                    } catch (NullPointerException npe) {
                        ItemAccountId = 0;
                    }
                    ItemChildAccountId = 0;
                    accjournal.setNarration(Narration);
                    accjournal.setAccCoaId(ItemAccountId);
                    accjournal.setAccountCode(ItemAccountCode);
                    accjournal.setAccChildAccountId(ItemChildAccountId);
                    accjournal.setDebitAmount(DebitAmount);
                    accjournal.setCreditAmount(CreditAmount);
                    this.saveAccJournal(accjournal);
                }
                //cash account balance
                if (aTrans.getTransactionReasonId() == 119) {
                    accjournal.setBillTransactorId(0);
                    ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                    ItemChildAccountCode = ati.get(ListItemIndex).getCodeSpecific();
                    DebitAmount = ati.get(ListItemIndex).getAmount();
                    CreditAmount = 0;
                    Narration = "Cash Account Opening Balance";
                    try {
                        ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                    } catch (NullPointerException npe) {
                        ItemAccountId = 0;
                    }
                    try {
                        ItemChildAccountId = new AccChildAccountBean().getAccChildAccByCode(ItemChildAccountCode).getAccChildAccountId();
                    } catch (NullPointerException npe) {
                        ItemChildAccountId = 0;
                    }
                    accjournal.setNarration(Narration);
                    accjournal.setAccCoaId(ItemAccountId);
                    accjournal.setAccountCode(ItemAccountCode);
                    accjournal.setAccChildAccountId(ItemChildAccountId);
                    accjournal.setDebitAmount(DebitAmount);
                    accjournal.setCreditAmount(CreditAmount);
                    this.saveAccJournal(accjournal);
                }
                //other account balance
                if (aTrans.getTransactionReasonId() == 120) {
                    accjournal.setBillTransactorId(aTrans.getBillTransactorId());
                    ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                    ItemChildAccountCode = ati.get(ListItemIndex).getCodeSpecific();
                    DebitAmount = ati.get(ListItemIndex).getAmountExcVat();
                    CreditAmount = ati.get(ListItemIndex).getAmountIncVat();
                    Narration = "Account Opening Balance";
                    try {
                        ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                    } catch (NullPointerException npe) {
                        ItemAccountId = 0;
                    }
                    try {
                        ItemChildAccountId = new AccChildAccountBean().getAccChildAccByCode(ItemChildAccountCode).getAccChildAccountId();
                    } catch (NullPointerException npe) {
                        ItemChildAccountId = 0;
                    }
                    accjournal.setNarration(Narration);
                    accjournal.setAccCoaId(ItemAccountId);
                    accjournal.setAccountCode(ItemAccountCode);
                    accjournal.setAccChildAccountId(ItemChildAccountId);
                    accjournal.setDebitAmount(DebitAmount);
                    accjournal.setCreditAmount(CreditAmount);
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception exc) {
            System.err.println("postJournalOpenBalance:" + exc.getMessage());
        }
    }

    public void postJournalOpenBalanceCANCEL(Trans aTrans, List<TransItem> aActiveTransItems, int aAccPeriodId) {
        long JobId = 0;
        try {
            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(0);
            accjournal.setPayTypeId(0);
            accjournal.setPayReasonId(0);
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aTrans.getCurrencyCode());
            accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            accjournal.setBillTransactorId(0);
            //1. REVERSE - post account journal entries
            List<TransItem> ati = aActiveTransItems;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            String ItemAccountCode = "";
            int ItemAccountId = 0;
            String ItemChildAccountCode = "";
            int ItemChildAccountId = 0;
            double DebitAmount = 0;
            double CreditAmount = 0;
            String Narration = "";
            while (ListItemIndex < ListItemNo) {
                //customer receivable
                if (aTrans.getTransactionReasonId() == 117) {
                    accjournal.setBillTransactorId(aTrans.getBillTransactorId());
                    ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                    DebitAmount = ati.get(ListItemIndex).getAmount();
                    CreditAmount = 0;
                    Narration = "REVERSE-Customer Opening Balance";
                    try {
                        ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                    } catch (NullPointerException npe) {
                        ItemAccountId = 0;
                    }
                    ItemChildAccountId = 0;
                    accjournal.setNarration(Narration);
                    accjournal.setAccCoaId(ItemAccountId);
                    accjournal.setAccountCode(ItemAccountCode);
                    accjournal.setAccChildAccountId(ItemChildAccountId);
                    accjournal.setDebitAmount(CreditAmount);//REVERSE
                    accjournal.setCreditAmount(DebitAmount);//REVERSE
                    this.saveAccJournal(accjournal);
                }
                //REVERSE-supplier payable
                if (aTrans.getTransactionReasonId() == 118) {
                    accjournal.setBillTransactorId(aTrans.getBillTransactorId());
                    ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                    DebitAmount = 0;
                    CreditAmount = ati.get(ListItemIndex).getAmount();
                    Narration = "REVERSE-Supplier Opening Balance";
                    try {
                        ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                    } catch (NullPointerException npe) {
                        ItemAccountId = 0;
                    }
                    ItemChildAccountId = 0;
                    accjournal.setNarration(Narration);
                    accjournal.setAccCoaId(ItemAccountId);
                    accjournal.setAccountCode(ItemAccountCode);
                    accjournal.setAccChildAccountId(ItemChildAccountId);
                    accjournal.setDebitAmount(CreditAmount);//REVERSE
                    accjournal.setCreditAmount(DebitAmount);//REVERSE
                    this.saveAccJournal(accjournal);
                }
                //REVERSE-cash account balance
                if (aTrans.getTransactionReasonId() == 119) {
                    accjournal.setBillTransactorId(0);
                    ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                    ItemChildAccountCode = ati.get(ListItemIndex).getCodeSpecific();
                    DebitAmount = ati.get(ListItemIndex).getAmount();
                    CreditAmount = 0;
                    Narration = "REVERSE-Cash Account Opening Balance";
                    try {
                        ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                    } catch (NullPointerException npe) {
                        ItemAccountId = 0;
                    }
                    try {
                        ItemChildAccountId = new AccChildAccountBean().getAccChildAccByCode(ItemChildAccountCode).getAccChildAccountId();
                    } catch (NullPointerException npe) {
                        ItemChildAccountId = 0;
                    }
                    accjournal.setNarration(Narration);
                    accjournal.setAccCoaId(ItemAccountId);
                    accjournal.setAccountCode(ItemAccountCode);
                    accjournal.setAccChildAccountId(ItemChildAccountId);
                    accjournal.setDebitAmount(CreditAmount);//REVERSE
                    accjournal.setCreditAmount(DebitAmount);//REVERSE
                    this.saveAccJournal(accjournal);
                }
                //REVERSE-other account balance
                if (aTrans.getTransactionReasonId() == 120) {
                    accjournal.setBillTransactorId(aTrans.getBillTransactorId());
                    ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                    ItemChildAccountCode = ati.get(ListItemIndex).getCodeSpecific();
                    DebitAmount = ati.get(ListItemIndex).getAmountExcVat();
                    CreditAmount = ati.get(ListItemIndex).getAmountIncVat();
                    Narration = "REVERSE-Account Opening Balance";
                    try {
                        ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                    } catch (NullPointerException npe) {
                        ItemAccountId = 0;
                    }
                    try {
                        ItemChildAccountId = new AccChildAccountBean().getAccChildAccByCode(ItemChildAccountCode).getAccChildAccountId();
                    } catch (NullPointerException npe) {
                        ItemChildAccountId = 0;
                    }
                    accjournal.setNarration(Narration);
                    accjournal.setAccCoaId(ItemAccountId);
                    accjournal.setAccountCode(ItemAccountCode);
                    accjournal.setAccChildAccountId(ItemChildAccountId);
                    accjournal.setDebitAmount(CreditAmount);//REVERSE
                    accjournal.setCreditAmount(DebitAmount);//REVERSE
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception exc) {
            System.err.println("postJournalOpenBalanceCANCEL:" + exc.getMessage());
        }
    }

    public void postJournalCashTransfer(Trans aTrans, List<TransItem> aActiveTransItems, int aAccPeriodId) {
        long JobId = 0;
        String LocalCurrencyCode = "";
        try {
            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            try {
                LocalCurrencyCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
            } catch (Exception e) {

            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(0);
            accjournal.setPayTypeId(0);
            accjournal.setPayReasonId(0);
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            //accjournal.setCurrencyCode(aTrans.getCurrencyCode());
            //accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            accjournal.setBillTransactorId(aTrans.getBillTransactorId());
            //1. post cash transfer entries
            List<TransItem> ati = aActiveTransItems;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            String FromChildAccountCode = "";
            String ToChildAccountCode = "";
            String FromAccountCode = "";
            String ToAccountCode = "";
            int FromAccountId = 0;
            int ToAccountId = 0;
            int FromChildAccountId = 0;
            int ToChildAccountId = 0;
            //double TransferAmount = 0;
            String Narration = "";
            String FromCurrencyCode = "";
            String ToCurrencyCode = "";
            double FromXrate = 1;
            double ToXrate = 1;
            double FromAmount = 0;
            double ToAmount = 0;
            while (ListItemIndex < ListItemNo) {
                //set general
                Narration = ati.get(ListItemIndex).getNarration();
                accjournal.setNarration(Narration);
                //set from
                FromChildAccountCode = ati.get(ListItemIndex).getAccountCode();
                try {
                    FromChildAccountId = new AccChildAccountBean().getAccChildAccByCode(FromChildAccountCode).getAccChildAccountId();
                } catch (NullPointerException npe) {
                    FromChildAccountId = 0;
                }
                try {
                    FromAccountCode = new AccChildAccountBean().getAccChildAccByCode(FromChildAccountCode).getAccCoaAccountCode();
                } catch (NullPointerException npe) {
                    FromAccountCode = "";
                }
                try {
                    FromAccountId = new AccCoaBean().getAccCoaByCodeOrId(FromAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    FromAccountId = 0;
                }
                FromCurrencyCode = ati.get(ListItemIndex).getBatchno();
                if (FromCurrencyCode.equals(LocalCurrencyCode)) {
                    FromXrate = 1;
                } else {
                    FromXrate = ati.get(ListItemIndex).getVatPerc();
                }
                FromAmount = ati.get(ListItemIndex).getAmountIncVat();
                //set to
                ToChildAccountCode = ati.get(ListItemIndex).getCodeSpecific();
                try {
                    ToChildAccountId = new AccChildAccountBean().getAccChildAccByCode(ToChildAccountCode).getAccChildAccountId();
                } catch (NullPointerException npe) {
                    ToChildAccountId = 0;
                }
                try {
                    ToAccountCode = new AccChildAccountBean().getAccChildAccByCode(ToChildAccountCode).getAccCoaAccountCode();
                } catch (NullPointerException npe) {
                    ToAccountCode = "";
                }
                try {
                    ToAccountId = new AccCoaBean().getAccCoaByCodeOrId(ToAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ToAccountId = 0;
                }
                ToCurrencyCode = ati.get(ListItemIndex).getDescSpecific();
                if (ToCurrencyCode.equals(LocalCurrencyCode)) {
                    ToXrate = 1;
                } else {
                    ToXrate = ati.get(ListItemIndex).getVatPerc();
                }
                ToAmount = ati.get(ListItemIndex).getAmountExcVat();
                //save from (credit)
                accjournal.setAccCoaId(FromAccountId);
                accjournal.setAccountCode(FromAccountCode);
                accjournal.setAccChildAccountId(FromChildAccountId);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(FromAmount);
                accjournal.setCurrencyCode(FromCurrencyCode);
                accjournal.setXrate(FromXrate);
                this.saveAccJournal(accjournal);
                //save to (debit)
                accjournal.setAccCoaId(ToAccountId);
                accjournal.setAccountCode(ToAccountCode);
                accjournal.setAccChildAccountId(ToChildAccountId);
                accjournal.setDebitAmount(ToAmount);
                accjournal.setCreditAmount(0);
                accjournal.setCurrencyCode(ToCurrencyCode);
                accjournal.setXrate(ToXrate);
                this.saveAccJournal(accjournal);

                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public void postJournalCashAdjustment(Trans aTrans, List<TransItem> aActiveTransItems, int aAccPeriodId) {
        long JobId = 0;
        String LocalCurrencyCode = "";
        try {
            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            try {
                LocalCurrencyCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
            } catch (Exception e) {

            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(0);
            accjournal.setPayTypeId(0);
            accjournal.setPayReasonId(0);
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            //accjournal.setCurrencyCode(aTrans.getCurrencyCode());
            //accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            accjournal.setBillTransactorId(aTrans.getBillTransactorId());
            //1. post cash transfer entries
            List<TransItem> ati = aActiveTransItems;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            String FromChildAccountCode = "";
            String FromAccountCode = "";
            int FromAccountId = 0;
            int FromChildAccountId = 0;
            String Narration = "";
            String FromCurrencyCode = "";
            double AdjustAmount = 0;
            double FromXrate = 1;
            while (ListItemIndex < ListItemNo) {
                //set general
                Narration = ati.get(ListItemIndex).getNarration();
                accjournal.setNarration(Narration);
                //set from
                FromChildAccountCode = ati.get(ListItemIndex).getAccountCode();
                try {
                    FromChildAccountId = new AccChildAccountBean().getAccChildAccByCode(FromChildAccountCode).getAccChildAccountId();
                } catch (NullPointerException npe) {
                    FromChildAccountId = 0;
                }
                try {
                    FromAccountCode = new AccChildAccountBean().getAccChildAccByCode(FromChildAccountCode).getAccCoaAccountCode();
                } catch (NullPointerException npe) {
                    FromAccountCode = "";
                }
                try {
                    FromAccountId = new AccCoaBean().getAccCoaByCodeOrId(FromAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    FromAccountId = 0;
                }
                FromCurrencyCode = ati.get(ListItemIndex).getBatchno();
                if (FromCurrencyCode.equals(LocalCurrencyCode)) {
                    FromXrate = 1;
                } else {
                    FromXrate = new AccXrateBean().getXrate(FromCurrencyCode, LocalCurrencyCode);
                }
                AdjustAmount = ati.get(ListItemIndex).getAmountIncVat();

                //for Add, (debit) account
                if (Narration.equals("Add")) {
                    accjournal.setAccCoaId(FromAccountId);
                    accjournal.setAccountCode(FromAccountCode);
                    accjournal.setAccChildAccountId(FromChildAccountId);
                    accjournal.setDebitAmount(AdjustAmount);
                    accjournal.setCreditAmount(0);
                    accjournal.setCurrencyCode(FromCurrencyCode);
                    accjournal.setXrate(FromXrate);
                    this.saveAccJournal(accjournal);
                }
                //for Subtract, (credit) account
                if (Narration.equals("Subtract")) {
                    accjournal.setAccCoaId(FromAccountId);
                    accjournal.setAccountCode(FromAccountCode);
                    accjournal.setAccChildAccountId(FromChildAccountId);
                    accjournal.setDebitAmount(0);
                    accjournal.setCreditAmount(AdjustAmount);
                    accjournal.setCurrencyCode(FromCurrencyCode);
                    accjournal.setXrate(FromXrate);
                    this.saveAccJournal(accjournal);
                }

                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception exc) {
            System.err.println("postJournalCashAdjustment:" + exc.getMessage());
        }
    }

    public long postJournalPurchaseInvoice(Trans aTrans, List<TransItem> aTransItems, Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        try {
            //Cash account money paid from
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aTrans.getAccChildAccountId());
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
                CashAccountCode = "";
            }
            //Prepaid Expense
            int PrepaidExpenseAccountId = 0;
            String PrepaidExpenseAccountCode = "1-00-030-050";//PREPAID Other Prepaid Expenses
            try {
                PrepaidExpenseAccountId = new AccCoaBean().getAccCoaByCodeOrId(PrepaidExpenseAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                PrepaidExpenseAccountId = 0;
            }
            //Payable Account
            int APAccountId = 0;
            String APAccountCode = "2-00-000-010";//AP-Trade
            try {
                APAccountId = new AccCoaBean().getAccCoaByCodeOrId(APAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                APAccountId = 0;
            }
            int PurchaseVatInputTaxAccountId = 0;
            String PurchaseVatInputTaxAccountCode = "1-00-010-060";//AR Sales VAT Tax
            try {
                PurchaseVatInputTaxAccountId = new AccCoaBean().getAccCoaByCodeOrId(PurchaseVatInputTaxAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                PurchaseVatInputTaxAccountId = 0;
            }
            int ReceivedDiscAccountId = 0;
            String ReceivedDiscAccountCode = "5-10-000-060";//Purchase Discounts (Contra Account)
            try {
                ReceivedDiscAccountId = new AccCoaBean().getAccCoaByCodeOrId(ReceivedDiscAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                ReceivedDiscAccountId = 0;
            }

            double GrossPurchaseAmount = 0;
            double NetPurchaseAmount = 0;
            double PaidCashAmount = 0;
            double PayableAmount = 0;
            double VatInputTaxAmount = 0;
            double ReceivedCashDiscAmount = 0;

            PaidCashAmount = aTrans.getAmountTendered();
            GrossPurchaseAmount = aTrans.getGrandTotal();
            PayableAmount = aTrans.getGrandTotal() - PaidCashAmount;
            VatInputTaxAmount = aTrans.getTotalVat();
            ReceivedCashDiscAmount = aTrans.getCashDiscount();
            NetPurchaseAmount = GrossPurchaseAmount - VatInputTaxAmount;//-ReceivedCashDiscAmount;

            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            AccJournal accjournal = new AccJournal();
            accjournal.setAccJournalId(0);
            accjournal.setJobId(JobId);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aTrans.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //CASH PAID
            if (PaidCashAmount > 0) {
                if (aPay.getPayMethodId() == 6 && aPay.getPayReasonId() == 91) {
                    //PrepaidExpense
                    accjournal.setAccChildAccountId(0);
                    if (aBillTransactor != null) {
                        accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                    }
                    accjournal.setAccCoaId(PrepaidExpenseAccountId);
                    accjournal.setAccountCode(PrepaidExpenseAccountCode);
                    accjournal.setDebitAmount(0);
                    accjournal.setCreditAmount(PaidCashAmount);
                    accjournal.setNarration("SUPPLIER ADAVANCE CONSUMED");
                    this.saveAccJournal(accjournal);
                } else {
                    //Credit cash account
                    accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                    if (aBillTransactor != null) {
                        accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                    }
                    accjournal.setAccCoaId(CashAccountId);
                    accjournal.setAccountCode(CashAccountCode);
                    accjournal.setDebitAmount(0);
                    accjournal.setCreditAmount(PaidCashAmount);
                    accjournal.setNarration("CASH PAYMENT TO SUPPLIER");
                    this.saveAccJournal(accjournal);
                }
            }
            //CREDIT PURCHASE
            if (PayableAmount > 0) {
                //Credit payable account
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(APAccountId);
                accjournal.setAccountCode(APAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PayableAmount);
                accjournal.setNarration("CREDIT PAYABLE TO SUPPLIER");
                this.saveAccJournal(accjournal);
            }
            //Sales tax (VAT) Receivable
            if (VatInputTaxAmount > 0) {
                accjournal.setAccChildAccountId(0);
                accjournal.setBillTransactorId(0);
                accjournal.setAccCoaId(PurchaseVatInputTaxAccountId);
                accjournal.setAccountCode(PurchaseVatInputTaxAccountCode);
                accjournal.setDebitAmount(VatInputTaxAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("VAT INPUT RECEIVABLE");
                this.saveAccJournal(accjournal);
            }
            //PURCHASE DISCOUNT RECEIVED
            if (ReceivedCashDiscAmount > 0) {
                //Credit Discount Received
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(ReceivedDiscAccountId);
                accjournal.setAccountCode(ReceivedDiscAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(ReceivedCashDiscAmount);
                accjournal.setNarration("DISCOUNT RECEIVED FROM SUPPLIER");
                this.saveAccJournal(accjournal);
            }
            //Purchase Invoice Item Accounts
            //1. get account codes with summary of amounts invoiced
            List<TransItem> ati = new TransItemBean().getTransItemsSummaryByAccount(aTrans.getTransactionId());
            //1. post account invoice amounts
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            String ItemAccountCode = "";
            int ItemAccountId = 0;
            double ItemAmountExcVat = 0;
            while (ListItemIndex < ListItemNo) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                ItemAmountExcVat = ati.get(ListItemIndex).getAmountExcVat();
                try {
                    ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemAccountId = 0;
                }
                if (ItemAccountId > 0 && ItemAmountExcVat > 0) {
                    accjournal.setAccCoaId(ItemAccountId);
                    accjournal.setAccountCode(ItemAccountCode);
                    accjournal.setDebitAmount(ItemAmountExcVat);
                    accjournal.setCreditAmount(0);
                    accjournal.setNarration("COST/INVENTORY AMT");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
        return JobId;
    }

    public void postJournalExpenseEntry(Trans aTrans, List<TransItem> aTransItems, Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        try {
            //Cash account money paid from
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aTrans.getAccChildAccountId());
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
                CashAccountCode = "";
            }
            int APAccountId = 0;
            String APAccountCode = "2-00-000-010";//AP-Trade
            try {
                APAccountId = new AccCoaBean().getAccCoaByCodeOrId(APAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                APAccountId = 0;
            }
            int PurchaseVatInputTaxAccountId = 0;
            String PurchaseVatInputTaxAccountCode = "1-00-010-060";//AR Sales VAT Tax
            try {
                PurchaseVatInputTaxAccountId = new AccCoaBean().getAccCoaByCodeOrId(PurchaseVatInputTaxAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                PurchaseVatInputTaxAccountId = 0;
            }
            int ReceivedDiscAccountId = 0;
            String ReceivedDiscAccountCode = "4-10-000-030";//Sales Discount
            try {
                ReceivedDiscAccountId = new AccCoaBean().getAccCoaByCodeOrId(ReceivedDiscAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                ReceivedDiscAccountId = 0;
            }

            double GrossPurchaseAmount = 0;
            double NetPurchaseAmount = 0;
            double PaidCashAmount = 0;
            double PayableAmount = 0;
            double VatInputTaxAmount = 0;
            double ReceivedCashDiscAmount = 0;

            PaidCashAmount = aTrans.getAmountTendered();
            GrossPurchaseAmount = aTrans.getGrandTotal();
            PayableAmount = aTrans.getGrandTotal() - PaidCashAmount;
            VatInputTaxAmount = aTrans.getTotalVat();
            ReceivedCashDiscAmount = aTrans.getCashDiscount();
            NetPurchaseAmount = GrossPurchaseAmount - VatInputTaxAmount;//-CashDiscountAmount;

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aTrans.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            //accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aTrans.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //if (aBillTransactor != null) {
            //    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
            //}
            //CASH PAID
            if (PaidCashAmount > 0) {
                //Credit cash account
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("CASH PAYMENT FOR EXPENSE");
                this.saveAccJournal(accjournal);
            }
            //CREDIT PURCHASE
            if (PayableAmount > 0) {
                //Credit payable account
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(APAccountId);
                accjournal.setAccountCode(APAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PayableAmount);
                accjournal.setNarration("CREDIT PAYABLE TO SUPPLIER");
                this.saveAccJournal(accjournal);
            }
            //Sales tax (VAT) Receivable
            if (VatInputTaxAmount > 0) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(PurchaseVatInputTaxAccountId);
                accjournal.setAccountCode(PurchaseVatInputTaxAccountCode);
                accjournal.setDebitAmount(VatInputTaxAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("VAT INPUT RECEIVABLE");
                this.saveAccJournal(accjournal);
            }
            //Purchase Invoice Item Accounts
            //1. get account codes with summary of amounts invoiced
            List<TransItem> ati = new TransItemBean().getTransItemsSummaryByAccount(aTrans.getTransactionId());
            //1. post account invoice amounts
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            String ItemAccountCode = "";
            int ItemAccountId = 0;
            double ItemAmountExcVat = 0;
            while (ListItemIndex < ListItemNo) {
                ItemAccountCode = ati.get(ListItemIndex).getAccountCode();
                ItemAmountExcVat = ati.get(ListItemIndex).getAmountExcVat();
                try {
                    ItemAccountId = new AccCoaBean().getAccCoaByCodeOrId(ItemAccountCode, 0).getAccCoaId();
                } catch (NullPointerException npe) {
                    ItemAccountId = 0;
                }
                if (ItemAccountId > 0 && ItemAmountExcVat > 0) {
                    accjournal.setAccChildAccountId(0);
                    if (aBillTransactor != null) {
                        accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                    }
                    accjournal.setAccCoaId(ItemAccountId);
                    accjournal.setAccountCode(ItemAccountCode);
                    accjournal.setDebitAmount(ItemAmountExcVat);
                    accjournal.setCreditAmount(0);
                    accjournal.setNarration("COST/INVENTORY AMT");
                    this.saveAccJournal(accjournal);
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public void postJournalCashReceiptReceivable(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int ARAccountId = 0;
            String ARAccountCode = "1-00-010-010";
            try {
                ARAccountId = new AccCoaBean().getAccCoaByCodeOrId(ARAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                ARAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //Debit Cash
            double PaidCashAmount = aPay.getPaidAmount();
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                if (aPay.getPayMethodId() == 8) {// Acc/Rec e.g. Withholding Tax
                    accjournal.setBillTransactorId(0);
                    accjournal.setNarration("SALES WITHHOLDING TAX PAID");
                } else {
                    if (aBillTransactor != null) {
                        accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                    }
                    accjournal.setNarration("CASH RECEIVED FROM CREDIT SALE");
                }
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                this.saveAccJournal(accjournal);
            }
            //Credit Receivable
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(ARAccountId);
                accjournal.setAccountCode(ARAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                if (aPay.getPayMethodId() == 8) {// Acc/Rec e.g. Withholding Tax
                    accjournal.setNarration("SALES WITHHOLDING TAX PAID");
                } else {
                    accjournal.setNarration("CASH RECEIVED FROM CREDIT SALE");
                }
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashReceiptReceivableCANCEL(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int ARAccountId = 0;
            String ARAccountCode = "1-00-010-010";
            try {
                ARAccountId = new AccCoaBean().getAccCoaByCodeOrId(ARAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                ARAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //CANCEL - Debit Cash
            double PaidCashAmount = aPay.getPaidAmount();
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("CANCEL-CASH RECEIVED FROM CREDIT SALE");
                this.saveAccJournal(accjournal);
            }
            //Credit Receivable
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(ARAccountId);
                accjournal.setAccountCode(ARAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CANCEL-CASH RECEIVED FROM CREDIT SALE");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashReceiptCapital(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int CapitalAccountId = 0;
            String CapitalAccountCode = "";
            try {
                CapitalAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId2());
            } catch (NullPointerException npe) {
                CapitalAccountCode = "";
            }
            try {
                CapitalAccountId = new AccCoaBean().getAccCoaByCodeOrId(CapitalAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CapitalAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            //Debit Cash
            double PaidCashAmount = aPay.getPaidAmount();
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CAPITAL CONTRIBUTION RECEIVED");
                this.saveAccJournal(accjournal);
            }
            //Credit Capital
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId2());
                accjournal.setAccCoaId(CapitalAccountId);
                accjournal.setAccountCode(CapitalAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("CAPITAL CONTRIBUTION RECEIVED");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashReceiptPrepaidIncome(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int DepositAccountId = 0;
            String DepositAccountCode = "2-00-000-070";//Customer Advances and Deposits Payable
            try {
                DepositAccountId = new AccCoaBean().getAccCoaByCodeOrId(DepositAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                DepositAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }

            //Debit Cash
            double PaidCashAmount = aPay.getPaidAmount();
            if (PaidCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CUSTOMER DEPOSIT RECEIVED");
                this.saveAccJournal(accjournal);
            }
            //Credit Deposit
            if (PaidCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(0);
                accjournal.setAccCoaId(DepositAccountId);
                accjournal.setAccountCode(DepositAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("PREPAID INCOME");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashReceiptPrepaidIncomeCANCEL(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int DepositAccountId = 0;
            String DepositAccountCode = "2-00-000-070";//Customer Advances and Deposits Payable
            try {
                DepositAccountId = new AccCoaBean().getAccCoaByCodeOrId(DepositAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                DepositAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }

            //CANCEL Debit Cash
            double PaidCashAmount = aPay.getPaidAmount();
            if (PaidCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("CANCEL-CUSTOMER DEPOSIT RECEIVED");
                this.saveAccJournal(accjournal);
            }
            //CANCEL Credit Deposit
            if (PaidCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(0);
                accjournal.setAccCoaId(DepositAccountId);
                accjournal.setAccountCode(DepositAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CANCEL-PREPAID INCOME");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashReceiptCapitalCANCEL(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int CapitalAccountId = 0;
            String CapitalAccountCode = "";
            try {
                CapitalAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId2());
            } catch (NullPointerException npe) {
                CapitalAccountCode = "";
            }
            try {
                CapitalAccountId = new AccCoaBean().getAccCoaByCodeOrId(CapitalAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CapitalAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            //CANCEL Debit Cash
            double PaidCashAmount = aPay.getPaidAmount();
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("CANCEL-CAPITAL CONTRIBUTION RECEIVED");
                this.saveAccJournal(accjournal);
            }
            //CANCEL Credit Capital
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId2());
                accjournal.setAccCoaId(CapitalAccountId);
                accjournal.setAccountCode(CapitalAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CANCEL-CAPITAL CONTRIBUTION RECEIVED");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashReceiptLoan(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int LoanAccountId = 0;
            String LoanAccountCode = "";
            try {
                LoanAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId2());
            } catch (NullPointerException npe) {
                LoanAccountCode = "";
            }
            try {
                LoanAccountId = new AccCoaBean().getAccCoaByCodeOrId(LoanAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                LoanAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            //Debit Cash
            double PaidCashAmount = aPay.getPaidAmount();
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("LOAN RECEIVED");
                this.saveAccJournal(accjournal);
            }
            //Credit Loan
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId2());
                accjournal.setAccCoaId(LoanAccountId);
                accjournal.setAccountCode(LoanAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("LOAN RECEIVED");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashReceiptLoanCANCEL(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int LoanAccountId = 0;
            String LoanAccountCode = "";
            try {
                LoanAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId2());
            } catch (NullPointerException npe) {
                LoanAccountCode = "";
            }
            try {
                LoanAccountId = new AccCoaBean().getAccCoaByCodeOrId(LoanAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                LoanAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            //CANCEL Debit Cash
            double PaidCashAmount = aPay.getPaidAmount();
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("CANCEL-LOAN RECEIVED");
                this.saveAccJournal(accjournal);
            }
            //CANCEL Credit Loan
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId2());
                accjournal.setAccCoaId(LoanAccountId);
                accjournal.setAccountCode(LoanAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CANCEL-LOAN RECEIVED");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashPaymentLoan(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int LoanAccountId = 0;
            String LoanAccountCode = "";
            try {
                LoanAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId2());
            } catch (NullPointerException npe) {
                LoanAccountCode = "";
            }
            try {
                LoanAccountId = new AccCoaBean().getAccCoaByCodeOrId(LoanAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                LoanAccountId = 0;
            }
            int InterestAccountId = 0;
            String InterestAccountCode = "5-20-300-010";
            try {
                InterestAccountId = new AccCoaBean().getAccCoaByCodeOrId(InterestAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                InterestAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            double PaidCashAmount = aPay.getPaidAmount();
            double PrincipalAmount = aPay.getPrincipalAmount();
            double InterestAmount = aPay.getInterestAmount();
            //Credit Cash
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("LOAN INSTALLMENT REPAYMENT");
                this.saveAccJournal(accjournal);
            }
            //Debit Loan with principal amount
            if (PrincipalAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId2());
                accjournal.setAccCoaId(LoanAccountId);
                accjournal.setAccountCode(LoanAccountCode);
                accjournal.setDebitAmount(PrincipalAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("LOAN PRINCIPAL REPAID");
                this.saveAccJournal(accjournal);
            }
            //Debit Interest Expense with interest amount
            if (InterestAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId2());
                accjournal.setAccCoaId(InterestAccountId);
                accjournal.setAccountCode(InterestAccountCode);
                accjournal.setDebitAmount(InterestAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("LOAN INTEREST EXPENSE");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashPaymentLoanCANCEL(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int LoanAccountId = 0;
            String LoanAccountCode = "";
            try {
                LoanAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId2());
            } catch (NullPointerException npe) {
                LoanAccountCode = "";
            }
            try {
                LoanAccountId = new AccCoaBean().getAccCoaByCodeOrId(LoanAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                LoanAccountId = 0;
            }
            int InterestAccountId = 0;
            String InterestAccountCode = "5-20-300-010";
            try {
                InterestAccountId = new AccCoaBean().getAccCoaByCodeOrId(InterestAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                InterestAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            double PaidCashAmount = aPay.getPaidAmount();
            double PrincipalAmount = aPay.getPrincipalAmount();
            double InterestAmount = aPay.getInterestAmount();
            //CANCEL Credit Cash
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CANCEL-LOAN INSTALLMENT REPAYMENT");
                this.saveAccJournal(accjournal);
            }
            //CANCEL Debit Loan with principal amount
            if (PrincipalAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId2());
                accjournal.setAccCoaId(LoanAccountId);
                accjournal.setAccountCode(LoanAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PrincipalAmount);
                accjournal.setNarration("CANCEL-LOAN PRINCIPAL REPAID");
                this.saveAccJournal(accjournal);
            }
            //CANCEL Debit Interest Expense with interest amount
            if (InterestAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId2());
                accjournal.setAccCoaId(InterestAccountId);
                accjournal.setAccountCode(InterestAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(InterestAmount);
                accjournal.setNarration("CANCEL-LOAN INTEREST EXPENSE");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashPaymentDraw(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int DrawAccountId = 0;
            String DrawAccountCode = "3-10-000-070";
            try {
                DrawAccountId = new AccCoaBean().getAccCoaByCodeOrId(DrawAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                DrawAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            double DrawnCashAmount = aPay.getPaidAmount();
            //Credit Cash
            if (DrawnCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(DrawnCashAmount);
                accjournal.setNarration("CASH DRAWN BY OWNER");
                this.saveAccJournal(accjournal);
            }
            //Debit Drawn amount
            if (DrawnCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId2());
                accjournal.setAccCoaId(DrawAccountId);
                accjournal.setAccountCode(DrawAccountCode);
                accjournal.setDebitAmount(DrawnCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CASH DRAWN BY OWNER");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashPaymentLiability(Pay aPay, int aAccPeriodId, List<PayTrans> aPayTranss) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int LiabilityAccountId = 0;
            String LiabilityAccountCode = "";
            try {
                LiabilityAccountCode = aPayTranss.get(0).getAccount_code();
            } catch (Exception e) {
                LiabilityAccountCode = "";
            }
            try {
                LiabilityAccountId = new AccCoaBean().getAccCoaByCodeOrId(LiabilityAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                LiabilityAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            double PaidCashAmount = aPay.getPaidAmount();
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //Credit Cash
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("CASH PAID - LIABILITY");
                this.saveAccJournal(accjournal);
            }
            //Debit Liability Account
            if (PaidCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(0);
                accjournal.setAccCoaId(LiabilityAccountId);
                accjournal.setAccountCode(LiabilityAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CASH PAID - LIABILITY");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashReceiptOtherRevenue(Pay aPay, int aAccPeriodId, List<PayTrans> aPayTranss) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int RevenueAccountId = 0;
            String RevenueAccountCode = "";
            try {
                RevenueAccountCode = aPayTranss.get(0).getAccount_code();
            } catch (Exception e) {
                RevenueAccountCode = "";
            }
            try {
                RevenueAccountId = new AccCoaBean().getAccCoaByCodeOrId(RevenueAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                RevenueAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            double PaidCashAmount = aPay.getPaidAmount();
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //Debit Cash
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CASH RECEIVED - OTHER REVENUE");
                this.saveAccJournal(accjournal);
            }
            //Credit Revenue Account
            if (PaidCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(0);
                accjournal.setAccCoaId(RevenueAccountId);
                accjournal.setAccountCode(RevenueAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("OTHER REVENUE RECEIVED");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashReceiptOtherRevenueCANCEL(Pay aPay, int aAccPeriodId, List<PayTrans> aPayTranss) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int RevenueAccountId = 0;
            String RevenueAccountCode = "";
            try {
                RevenueAccountCode = aPayTranss.get(0).getAccount_code();
            } catch (Exception e) {
                RevenueAccountCode = "";
            }
            try {
                RevenueAccountId = new AccCoaBean().getAccCoaByCodeOrId(RevenueAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                RevenueAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            double PaidCashAmount = aPay.getPaidAmount();
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //CANCEL by Credit Cash
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("CANCEL-CASH RECEIVED - OTHER REVENUE");
                this.saveAccJournal(accjournal);
            }
            //CANCEL by Dedit Revenue Account
            if (PaidCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(0);
                accjournal.setAccCoaId(RevenueAccountId);
                accjournal.setAccountCode(RevenueAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CANCEL-OTHER REVENUE RECEIVED");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashPaymentPrepaidExpense(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int PrepaidExpenseAccountId = 0;
            String PrepaidExpenseAccountCode = "1-00-030-050";//PREPAID Other Prepaid Expenses
            try {
                PrepaidExpenseAccountId = new AccCoaBean().getAccCoaByCodeOrId(PrepaidExpenseAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                PrepaidExpenseAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            double PrepaidExpenseCashAmount = aPay.getPaidAmount();
            //Credit Cash
            if (PrepaidExpenseCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PrepaidExpenseCashAmount);
                accjournal.setNarration("CASH ADVANCE TO SUPPLIER");
                this.saveAccJournal(accjournal);
            }
            //Debit PrepaidExpense amount
            if (PrepaidExpenseCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(0);
                accjournal.setAccCoaId(PrepaidExpenseAccountId);
                accjournal.setAccountCode(PrepaidExpenseAccountCode);
                accjournal.setDebitAmount(PrepaidExpenseCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("PREPAID EXPENSE");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashPaymentPrepaidExpenseCANCEL(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int PrepaidExpenseAccountId = 0;
            String PrepaidExpenseAccountCode = "1-00-030-050";//PREPAID Other Prepaid Expenses
            try {
                PrepaidExpenseAccountId = new AccCoaBean().getAccCoaByCodeOrId(PrepaidExpenseAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                PrepaidExpenseAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            double PrepaidExpenseCashAmount = aPay.getPaidAmount();
            //CANCEL Credit Cash
            if (PrepaidExpenseCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(PrepaidExpenseCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CANCEL-CASH ADVANCE TO SUPPLIER");
                this.saveAccJournal(accjournal);
            }
            //CANCEL Debit PrepaidExpense amount
            if (PrepaidExpenseCashAmount > 0) {
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccChildAccountId(0);
                accjournal.setAccCoaId(PrepaidExpenseAccountId);
                accjournal.setAccountCode(PrepaidExpenseAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PrepaidExpenseCashAmount);
                accjournal.setNarration("CANCEL-PREPAID EXPENSE");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashPaymentDrawCANCEL(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int DrawAccountId = 0;
            String DrawAccountCode = "3-10-000-070";
            try {
                DrawAccountId = new AccCoaBean().getAccCoaByCodeOrId(DrawAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                DrawAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            accjournal.setBillTransactorId(0);
            double DrawnCashAmount = aPay.getPaidAmount();
            //CANCEL Credit Cash
            if (DrawnCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(DrawnCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CANCEL-CASH DRAWN BY OWNER");
                this.saveAccJournal(accjournal);
            }
            //CANCEL Debit Drawn amount
            if (DrawnCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId2());
                accjournal.setAccCoaId(DrawAccountId);
                accjournal.setAccountCode(DrawAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(DrawnCashAmount);
                accjournal.setNarration("CANCEL-CASH DRAWN BY OWNER");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashPaymentPurchase(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int APAccountId = 0;
            String APAccountCode = "2-00-000-010";//AP Trade
            try {
                APAccountId = new AccCoaBean().getAccCoaByCodeOrId(APAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                APAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //Credit Cash
            double PaidCashAmount = aPay.getPaidAmount();
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("CASH PAYMENT FOR CREDIT PURCHASE");
                this.saveAccJournal(accjournal);
            }
            //Debit Payable
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(APAccountId);
                accjournal.setAccountCode(APAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CASH PAYMENT FOR CREDIT PURCHASE");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalCashPaymentPurchaseCANCEL(Pay aPay, int aAccPeriodId) {
        long JobId = 0;
        if (aPay != null) {
            int CashAccountId = 0;
            String CashAccountCode = "";
            try {
                CashAccountCode = new AccChildAccountBean().getParentAccCodeByChildAccId(aPay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                CashAccountCode = "";
            }
            try {
                CashAccountId = new AccCoaBean().getAccCoaByCodeOrId(CashAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                CashAccountId = 0;
            }
            int APAccountId = 0;
            String APAccountCode = "2-00-000-010";//AP Trade
            try {
                APAccountId = new AccCoaBean().getAccCoaByCodeOrId(APAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                APAccountId = 0;
            }

            AccJournal accjournal = new AccJournal();
            //get job Id
            try {
                JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            accjournal.setJobId(JobId);
            accjournal.setAccJournalId(0);
            accjournal.setJournalDate(aPay.getPayDate());
            accjournal.setTransactionId(0);
            accjournal.setTransactionTypeId(0);
            accjournal.setTransactionReasonId(0);
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aPay.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            accjournal.setCurrencyCode(aPay.getCurrencyCode());
            accjournal.setXrate(aPay.getXRate());
            accjournal.setAddBy(aPay.getAddUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aPay.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            //CANCEL Credit Cash
            double PaidCashAmount = aPay.getPaidAmount();
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(CashAccountId);
                accjournal.setAccountCode(CashAccountCode);
                accjournal.setDebitAmount(PaidCashAmount);
                accjournal.setCreditAmount(0);
                accjournal.setNarration("CANCEL-CASH PAYMENT FOR CREDIT PURCHASE");
                this.saveAccJournal(accjournal);
            }
            //CANCEL Debit Payable
            if (PaidCashAmount > 0) {
                accjournal.setAccChildAccountId(0);
                if (aBillTransactor != null) {
                    accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
                }
                accjournal.setAccCoaId(APAccountId);
                accjournal.setAccountCode(APAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(PaidCashAmount);
                accjournal.setNarration("CANCEL-CASH PAYMENT FOR CREDIT PURCHASE");
                this.saveAccJournal(accjournal);
            }
        }
    }

    public void postJournalDepreciateAsset(Trans aTrans, Stock aStock, AccDepSchedule aAccDepSchedule, int aAccPeriodId, Pay aPay, long aParentJobId) {
        try {
            //Depreciation Expense Account(profit and loss)
            int DepAccountId = 0;
            String DepAccountCode = "5-20-250-010";
            try {
                DepAccountId = new AccCoaBean().getAccCoaByCodeOrId(DepAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                DepAccountId = 0;
            }
            //Accumulated Depreciation Account (balance sheet)
            int AccumDepAccountId = 0;
            String AccumDepAccountCode = "";
            switch (aStock.getAccountCode()) {
                case "1-20-000-020"://PPE Buildings
                    AccumDepAccountCode = "1-20-010-010";
                    break;
                case "1-20-000-030"://PPE Machinery & Equipment
                    AccumDepAccountCode = "1-20-010-020";
                    break;
                case "1-20-000-040"://PPE Vehicles
                    AccumDepAccountCode = "1-20-010-030";
                    break;
                case "1-20-000-050"://PPE Furniture and Fixtures
                    AccumDepAccountCode = "1-20-010-040";
                    break;
                case "1-20-000-060"://PPE Computer Equipment
                    AccumDepAccountCode = "1-20-010-050";
                    break;
                case "1-20-000-070"://PPE Other Property, Plant & Equipment
                    AccumDepAccountCode = "1-20-010-060";
                    break;
                default:
                    break;
            }
            try {
                AccumDepAccountId = new AccCoaBean().getAccCoaByCodeOrId(AccumDepAccountCode, 0).getAccCoaId();
            } catch (NullPointerException npe) {
                AccumDepAccountId = 0;
            }
            //get job Id
            long JobId = 0;
            try {
                if (aParentJobId > 0) {
                    JobId = aParentJobId;
                } else {
                    JobId = new UtilityBean().getNewTableColumnSeqNumber("acc_journal", "job_id");
                }
            } catch (NullPointerException npe) {
                JobId = 0;
            }
            AccJournal accjournal = new AccJournal();
            accjournal.setAccJournalId(0);
            accjournal.setJobId(JobId);
            accjournal.setJournalDate(aTrans.getTransactionDate());
            accjournal.setTransactionId(aTrans.getTransactionId());
            accjournal.setTransactionTypeId(aTrans.getTransactionTypeId());
            accjournal.setTransactionReasonId(aTrans.getTransactionReasonId());
            accjournal.setPayId(aPay.getPayId());
            accjournal.setPayTypeId(aPay.getPayTypeId());
            accjournal.setPayReasonId(aPay.getPayReasonId());
            accjournal.setStoreId(aStock.getStoreId());
            accjournal.setLedgerFolio("");
            accjournal.setAccPeriodId(aAccPeriodId);
            //accjournal.setAccChildAccountId(aPay.getAccChildAccountId());
            accjournal.setCurrencyCode(aTrans.getCurrencyCode());
            accjournal.setXrate(aTrans.getXrate());
            accjournal.setAddBy(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            Transactor aBillTransactor = null;
            try {
                aBillTransactor = new TransactorBean().getTransactor(aTrans.getBillTransactorId());
            } catch (NullPointerException npe) {
                aBillTransactor = null;
            }
            if (aBillTransactor != null) {
                accjournal.setBillTransactorId(aBillTransactor.getTransactorId());
            }
            //DEBIT DEPRECIATION
            if (aAccDepSchedule.getDepAmount() > 0) {
                accjournal.setAccCoaId(DepAccountId);
                accjournal.setAccountCode(DepAccountCode);
                accjournal.setDebitAmount(aAccDepSchedule.getDepAmount());
                accjournal.setCreditAmount(0);
                accjournal.setNarration("DEPRECIATION COST");
                this.saveAccJournal(accjournal);
            }

            //CREDIT ACCUM DEPRECIATION
            if (aAccDepSchedule.getDepAmount() > 0) {
                accjournal.setAccCoaId(AccumDepAccountId);
                accjournal.setAccountCode(AccumDepAccountCode);
                accjournal.setDebitAmount(0);
                accjournal.setCreditAmount(aAccDepSchedule.getDepAmount());
                accjournal.setNarration("ACCUM DEPRECIATION");
                this.saveAccJournal(accjournal);
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public void reportAccJournal(int aAccPeriodId, String aAccountCode, int aChildAccountId) {
        //String sql = "SELECT * FROM acc_journal al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE al.acc_journal_id>1";
        //view_ledger_general
        String sql = "SELECT * FROM acc_journal WHERE 1=1";

        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        if (aAccPeriodId > 0) {
            this.AccJournalList = new ArrayList<>();
            wheresql = " AND acc_period_id=" + aAccPeriodId;
            wheresql = wheresql + " AND account_code='" + aAccountCode + "'";
            if (aChildAccountId > 0) {
                wheresql = wheresql + " AND acc_child_account_id=" + aChildAccountId;
            }
            ordersql = " ORDER BY journal_date DESC,transaction_id DESC,pay_id DESC";
            sql = sql + wheresql + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                AccJournal accjournal = null;
                while (rs.next()) {
                    accjournal = new AccJournal();
                    this.setAccJournalFromResultset(accjournal, rs);
                    this.AccJournalList.add(accjournal);
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }
    }

    public void reportAccJournal(AccJournal aAccJournal, AccJournalBean aAccJournalBean) {
        //accJournal.accPeriodId,accJournal.accountCode,accJournal.accChildAccountId,accJournalBean.dateType,accJournalBean.date1,accJournalBean.date2
        if (aAccJournalBean.getDateType().length() == 0) {
            aAccJournalBean.setDateType("Add Date");
        }
        String sql = "SELECT * FROM acc_journal WHERE 1=1";
        String sqlsum = "SELECT currency_code,sum(debit_amount) as debit_amount,sum(credit_amount) as credit_amount FROM acc_journal WHERE 1=1";
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = " GROUP BY currency_code";
        ResultSet rs = null;
        this.AccJournalList = new ArrayList<>();
        this.AccJournalSummary = new ArrayList<>();
        if (aAccJournal.getAccPeriodId() > 0) {
            wheresql = wheresql + " AND acc_period_id=" + aAccJournal.getAccPeriodId();
        }
        if (aAccJournal.getAccountCode().length() > 0) {
            wheresql = wheresql + " AND account_code='" + aAccJournal.getAccountCode() + "'";
        }
        if (aAccJournal.getAccChildAccountId() > 0) {
            wheresql = wheresql + " AND acc_child_account_id=" + aAccJournal.getAccChildAccountId();
        }
        if (aAccJournalBean.getDateType().length() > 0 && aAccJournalBean.getDate1() != null && aAccJournalBean.getDate2() != null) {
            switch (aAccJournalBean.getDateType()) {
                case "Journal Date":
                    wheresql = wheresql + " AND journal_date BETWEEN '" + new java.sql.Date(aAccJournalBean.getDate1().getTime()) + "' AND '" + new java.sql.Date(aAccJournalBean.getDate2().getTime()) + "'";
                    break;
                case "Add Date":
                    wheresql = wheresql + " AND add_date BETWEEN '" + new java.sql.Timestamp(aAccJournalBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aAccJournalBean.getDate2().getTime()) + "'";
                    break;
            }
        }
        ordersql = " ORDER BY journal_date DESC,transaction_id DESC,pay_id DESC";
        ordersqlsum = " ORDER BY currency_code ASC";
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            AccJournal accjournal = null;
            while (rs.next()) {
                accjournal = new AccJournal();
                this.setAccJournalFromResultset(accjournal, rs);
                this.AccJournalList.add(accjournal);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccJournal accjournalsum = null;
            while (rs.next()) {
                accjournalsum = new AccJournal();
                try {
                    accjournalsum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accjournalsum.setCurrencyCode("");
                }
                try {
                    accjournalsum.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accjournalsum.setDebitAmount(0);
                }
                try {
                    accjournalsum.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accjournalsum.setCreditAmount(0);
                }
                this.AccJournalSummary.add(accjournalsum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportAccJournalCashFlowUser(AccJournal aAccJournal, AccJournalBean aAccJournalBean) {
        //accJournal.accPeriodId,accJournal.accountCode,accJournalBean.dateType,accJournalBean.date1,accJournalBean.date2,accJournal.addBy
        if (aAccJournalBean.getDateType().length() == 0) {
            aAccJournalBean.setDateType("Add Date");
        }
        String sql = "SELECT * FROM acc_journal WHERE account_code LIKE '1-00-000%'";
        String sqlsum = "SELECT add_by,account_code,acc_child_account_id,currency_code,sum(debit_amount) as debit_amount,sum(credit_amount) as credit_amount FROM acc_journal WHERE account_code LIKE '1-00-000%'";
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = " GROUP BY add_by,account_code,acc_child_account_id,currency_code";
        ResultSet rs = null;
        this.AccJournalList = new ArrayList<>();
        this.AccJournalSummary = new ArrayList<>();
        if (aAccJournal.getAccPeriodId() > 0) {
            wheresql = wheresql + " AND acc_period_id=" + aAccJournal.getAccPeriodId();
        }
        if (aAccJournal.getAccountCode().length() > 0) {
            wheresql = wheresql + " AND account_code='" + aAccJournal.getAccountCode() + "'";
        }
        if (aAccJournal.getAddBy() > 0) {
            wheresql = wheresql + " AND add_by=" + aAccJournal.getAddBy();
        }
        if (aAccJournalBean.getDateType().length() > 0 && aAccJournalBean.getDate1() != null && aAccJournalBean.getDate2() != null) {
            switch (aAccJournalBean.getDateType()) {
                case "Journal Date":
                    wheresql = wheresql + " AND journal_date BETWEEN '" + new java.sql.Date(aAccJournalBean.getDate1().getTime()) + "' AND '" + new java.sql.Date(aAccJournalBean.getDate2().getTime()) + "'";
                    break;
                case "Add Date":
                    wheresql = wheresql + " AND add_date BETWEEN '" + new java.sql.Timestamp(aAccJournalBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aAccJournalBean.getDate2().getTime()) + "'";
                    break;
            }
        }
        ordersql = " ORDER BY journal_date DESC,transaction_id DESC,pay_id DESC";
        ordersqlsum = " ORDER BY add_by,account_code,acc_child_account_id,currency_code";
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            AccJournal accjournal = null;
            while (rs.next()) {
                accjournal = new AccJournal();
                this.setAccJournalFromResultset(accjournal, rs);
                this.AccJournalList.add(accjournal);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccJournal accjournalsum = null;
            while (rs.next()) {
                accjournalsum = new AccJournal();
                //SELECT add_by,account_code,acc_child_account_id
                try {
                    accjournalsum.setAddBy(rs.getInt("add_by"));
                } catch (NullPointerException npe) {
                    accjournalsum.setAddBy(0);
                }
                try {
                    accjournalsum.setAccountCode(rs.getString("account_code"));
                } catch (NullPointerException npe) {
                    accjournalsum.setAccountCode("");
                }
                try {
                    accjournalsum.setAccChildAccountId(rs.getInt("acc_child_account_id"));
                } catch (NullPointerException npe) {
                    accjournalsum.setAccChildAccountId(0);
                }
                try {
                    accjournalsum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accjournalsum.setCurrencyCode("");
                }
                try {
                    accjournalsum.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accjournalsum.setDebitAmount(0);
                }
                try {
                    accjournalsum.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accjournalsum.setCreditAmount(0);
                }
                this.AccJournalSummary.add(accjournalsum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportAccJournalAccRecDetail(AccJournal aAccJournal, AccJournalBean aAccJournalBean) {
        //accJournal.accPeriodId,accJournal.accountCode,accJournalBean.dateType,accJournalBean.date1,accJournalBean.date2,accJournal.billTransactorId
        if (aAccJournalBean.getDateType().length() == 0) {
            aAccJournalBean.setDateType("Add Date");
        }
        String sql = "SELECT * FROM acc_journal WHERE account_code LIKE '1-00-010%'";
        String sqlsum = "SELECT account_code,currency_code,sum(debit_amount) as debit_amount,sum(credit_amount) as credit_amount FROM acc_journal WHERE account_code LIKE '1-00-010%'";
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = " GROUP BY account_code,currency_code";
        ResultSet rs = null;
        this.AccJournalList = new ArrayList<>();
        this.AccJournalSummary = new ArrayList<>();
        if (aAccJournal.getAccPeriodId() > 0) {
            wheresql = wheresql + " AND acc_period_id=" + aAccJournal.getAccPeriodId();
        }
        if (aAccJournal.getAccountCode().length() > 0) {
            wheresql = wheresql + " AND account_code='" + aAccJournal.getAccountCode() + "'";
        }
        if (aAccJournal.getBillTransactorId() > 0) {
            wheresql = wheresql + " AND bill_transactor_id=" + aAccJournal.getBillTransactorId();
        }
        if (aAccJournal.getStoreId() > 0) {
            wheresql = wheresql + " AND store_id=" + aAccJournal.getStoreId();
        }
        if (aAccJournalBean.getDateType().length() > 0 && aAccJournalBean.getDate1() != null && aAccJournalBean.getDate2() != null) {
            switch (aAccJournalBean.getDateType()) {
                case "Journal Date":
                    wheresql = wheresql + " AND journal_date BETWEEN '" + new java.sql.Date(aAccJournalBean.getDate1().getTime()) + "' AND '" + new java.sql.Date(aAccJournalBean.getDate2().getTime()) + "'";
                    break;
                case "Add Date":
                    wheresql = wheresql + " AND add_date BETWEEN '" + new java.sql.Timestamp(aAccJournalBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aAccJournalBean.getDate2().getTime()) + "'";
                    break;
            }
        }
        switch (aAccJournalBean.getDateType()) {
            case "Journal Date":
                ordersql = " ORDER BY journal_date DESC,transaction_id DESC";
                break;
            case "Add Date":
                ordersql = " ORDER BY add_date DESC,transaction_id DESC";
                break;
        }
        ordersqlsum = " ORDER BY account_code,currency_code";
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            AccJournal accjournal = null;
            while (rs.next()) {
                accjournal = new AccJournal();
                this.setAccJournalFromResultset(accjournal, rs);
                this.AccJournalList.add(accjournal);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccJournal accjournalsum = null;
            while (rs.next()) {
                accjournalsum = new AccJournal();
                try {
                    accjournalsum.setAccountCode(rs.getString("account_code"));
                } catch (NullPointerException npe) {
                    accjournalsum.setAccountCode("");
                }
                try {
                    accjournalsum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accjournalsum.setCurrencyCode("");
                }
                try {
                    accjournalsum.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accjournalsum.setDebitAmount(0);
                }
                try {
                    accjournalsum.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accjournalsum.setCreditAmount(0);
                }
                this.AccJournalSummary.add(accjournalsum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportAccJournalAccPayDetail(AccJournal aAccJournal, AccJournalBean aAccJournalBean) {
        //accJournal.accPeriodId,accJournal.accountCode,accJournalBean.dateType,accJournalBean.date1,accJournalBean.date2,accJournal.billTransactorId
        if (aAccJournalBean.getDateType().length() == 0) {
            aAccJournalBean.setDateType("Add Date");
        }
        String sql = "SELECT * FROM acc_journal WHERE account_code LIKE '2-00-000%'";
        String sqlsum = "SELECT account_code,currency_code,sum(debit_amount) as debit_amount,sum(credit_amount) as credit_amount FROM acc_journal WHERE account_code LIKE '2-00-000%'";
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = " GROUP BY account_code,currency_code";
        ResultSet rs = null;
        this.AccJournalList = new ArrayList<>();
        this.AccJournalSummary = new ArrayList<>();
        if (aAccJournal.getAccPeriodId() > 0) {
            wheresql = wheresql + " AND acc_period_id=" + aAccJournal.getAccPeriodId();
        }
        if (aAccJournal.getAccountCode().length() > 0) {
            wheresql = wheresql + " AND account_code='" + aAccJournal.getAccountCode() + "'";
        }
        if (aAccJournal.getBillTransactorId() > 0) {
            wheresql = wheresql + " AND bill_transactor_id=" + aAccJournal.getBillTransactorId();
        }
        if (aAccJournalBean.getDateType().length() > 0 && aAccJournalBean.getDate1() != null && aAccJournalBean.getDate2() != null) {
            switch (aAccJournalBean.getDateType()) {
                case "Journal Date":
                    wheresql = wheresql + " AND journal_date BETWEEN '" + new java.sql.Date(aAccJournalBean.getDate1().getTime()) + "' AND '" + new java.sql.Date(aAccJournalBean.getDate2().getTime()) + "'";
                    break;
                case "Add Date":
                    wheresql = wheresql + " AND add_date BETWEEN '" + new java.sql.Timestamp(aAccJournalBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aAccJournalBean.getDate2().getTime()) + "'";
                    break;
            }
        }
        switch (aAccJournalBean.getDateType()) {
            case "Journal Date":
                ordersql = " ORDER BY journal_date DESC,transaction_id DESC";
                break;
            case "Add Date":
                ordersql = " ORDER BY add_date DESC,transaction_id DESC";
                break;
        }
        ordersqlsum = " ORDER BY account_code,currency_code";
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            AccJournal accjournal = null;
            while (rs.next()) {
                accjournal = new AccJournal();
                this.setAccJournalFromResultset(accjournal, rs);
                this.AccJournalList.add(accjournal);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccJournal accjournalsum = null;
            while (rs.next()) {
                accjournalsum = new AccJournal();
                try {
                    accjournalsum.setAccountCode(rs.getString("account_code"));
                } catch (NullPointerException npe) {
                    accjournalsum.setAccountCode("");
                }
                try {
                    accjournalsum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accjournalsum.setCurrencyCode("");
                }
                try {
                    accjournalsum.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accjournalsum.setDebitAmount(0);
                }
                try {
                    accjournalsum.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accjournalsum.setCreditAmount(0);
                }
                this.AccJournalSummary.add(accjournalsum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public List<AccJournal> getAccJournalListByTrans(int aTransTypeId, int aTransReasId, long aTransId, int aPayTypeId, int aPayReasId, long aPayId) {
        long MaxJobId = new AccJournalBean().getMaxJobIdByTrans(aTransTypeId, aTransReasId, aTransId, aPayTypeId, aPayReasId, aPayId);
        String wheresql = "SELECT * FROM acc_journal WHERE narration NOT LIKE 'REVERSE-%' AND job_id=" + MaxJobId;
        ResultSet rs = null;
        List<AccJournal> accjournallist = new ArrayList<>();
        if (aTransTypeId > 0) {
            wheresql = wheresql + " AND transaction_type_id=" + aTransTypeId;
        }
        if (aTransReasId > 0) {
            wheresql = wheresql + " AND transaction_reason_id=" + aTransReasId;
        }
        if (aTransId > 0) {
            wheresql = wheresql + " AND transaction_id=" + aTransId;
        }
        if (aPayTypeId > 0) {
            wheresql = wheresql + " AND pay_type_id=" + aPayTypeId;
        }
        if (aPayReasId > 0) {
            wheresql = wheresql + " AND pay_reason_id=" + aPayReasId;
        }
        if (aPayId > 0) {
            wheresql = wheresql + " AND pay_id=" + aPayId;
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(wheresql);) {
            rs = ps.executeQuery();
            AccJournal accjournal = null;
            while (rs.next()) {
                accjournal = new AccJournal();
                this.setAccJournalFromResultset(accjournal, rs);
                accjournallist.add(accjournal);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return accjournallist;
    }

    public long getMaxJobIdByTrans(int aTransTypeId, int aTransReasId, long aTransId, int aPayTypeId, int aPayReasId, long aPayId) {
        String wheresql = "SELECT max(job_id) as max_job_id FROM acc_journal WHERE 1=1";
        ResultSet rs = null;
        long MaxJobId = 0;
        if (aTransId == 0 && aPayId == 0) {
            MaxJobId = 0;
        } else {
            if (aTransTypeId > 0) {
                wheresql = wheresql + " AND transaction_type_id=" + aTransTypeId;
            }
            if (aTransReasId > 0) {
                wheresql = wheresql + " AND transaction_reason_id=" + aTransReasId;
            }
            if (aTransId > 0) {
                wheresql = wheresql + " AND transaction_id=" + aTransId;
            }
            if (aPayTypeId > 0) {
                wheresql = wheresql + " AND pay_type_id=" + aPayTypeId;
            }
            if (aPayReasId > 0) {
                wheresql = wheresql + " AND pay_reason_id=" + aPayReasId;
            }
            if (aPayId > 0) {
                wheresql = wheresql + " AND pay_id=" + aPayId;
            }
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(wheresql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    MaxJobId = rs.getLong("max_job_id");
                } catch (NullPointerException npe) {
                    MaxJobId = 0;
                }
            }
        } catch (SQLException se) {
            MaxJobId = 0;
            System.err.println(se.getMessage());
        }
        return MaxJobId;
    }

    public String returnNetAmountString(double aDebitAmount, double aCreditAmount) {
        String aNetString = "";
        double aNetAmount;
        String aPattern = "###,###.###";
        if (aDebitAmount > aCreditAmount) {
            aNetAmount = aDebitAmount - aCreditAmount;
            aNetString = new UtilityBean().formatNumber(aPattern, aNetAmount) + " Dr";
            //aNetString = aNetAmount + " Dr";
        } else if (aCreditAmount > aDebitAmount) {
            aNetAmount = aCreditAmount - aDebitAmount;
            aNetString = new UtilityBean().formatNumber(aPattern, aNetAmount) + " Cr";
            //aNetString = aNetAmount + " Cr";
        } else {
            aNetAmount = 0;
        }
        return aNetString;
    }

    public String returnBalanceString(double aBalance) {
        String aNetString = "";
        String aPattern = "###,###.###;(###,###.###)";
        if (aBalance >= 0) {
            aNetString = new UtilityBean().formatNumber(aPattern, aBalance) + "";
        } else if (aBalance < 0) {
            aNetString = new UtilityBean().formatNumber(aPattern, aBalance) + "";
        }
        return aNetString;
    }

    public boolean IsAmountNegative(double aAmount) {
        if (aAmount >= 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setDateToToday() {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();
        this.setDate1(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getDate1());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate1(cal.getTime());

        this.setDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.getDate2());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate2(cal2.getTime());
    }

    public void setDateToYesturday() {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();

        this.setDate1(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getDate1());
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate1(cal.getTime());

        this.setDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.getDate2());
        cal2.add(Calendar.DATE, -1);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate2(cal2.getTime());
    }

    public void initResetAccJournalReport(AccJournal aAccJournal, AccJournalBean aAccJournalBean, Transactor aTransactor) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.resetAccJournalReport(aAccJournal, aAccJournalBean, aTransactor);
        }
    }

    public void resetAccJournalReport(AccJournal aAccJournal, AccJournalBean aAccJournalBean, Transactor aTransactor) {
        aAccJournalBean.setActionMessage("");
        try {
            this.clearAccJournal(aAccJournal);
        } catch (NullPointerException npe) {
        }
        try {
            if (null == aTransactor) {
                //do nothing
            } else {
                new TransactorBean().clearTransactor(aTransactor);
            }
        } catch (Exception e) {
        }
        try {
            aAccJournalBean.setDateType("");
            aAccJournalBean.setDate1(null);
            aAccJournalBean.setDate2(null);
            //aAccJournalBean.setFieldName("");
            aAccJournalBean.AccJournalList.clear();
            aAccJournalBean.AccJournalSummary.clear();
        } catch (NullPointerException npe) {
        }
    }

    public void clearAccJournal(AccJournal accjournal) {
        try {
            try {
                accjournal.setAccJournalId(0);
            } catch (NullPointerException npe) {
                accjournal.setAccJournalId(0);
            }
            try {
                accjournal.setJournalDate(null);
            } catch (NullPointerException npe) {
                accjournal.setJournalDate(null);
            }
            try {
                accjournal.setTransactionId(0);
            } catch (NullPointerException npe) {
                accjournal.setTransactionId(0);
            }
            try {
                accjournal.setTransactionTypeId(0);
            } catch (NullPointerException npe) {
                accjournal.setTransactionTypeId(0);
            }
            try {
                accjournal.setTransactionReasonId(0);
            } catch (NullPointerException npe) {
                accjournal.setTransactionReasonId(0);
            }
            try {
                accjournal.setPayId(0);
            } catch (NullPointerException npe) {
                accjournal.setPayId(0);
            }
            try {
                accjournal.setPayTypeId(0);
            } catch (NullPointerException npe) {
                accjournal.setPayTypeId(0);
            }
            try {
                accjournal.setPayReasonId(0);
            } catch (NullPointerException npe) {
                accjournal.setPayReasonId(0);
            }
            try {
                accjournal.setStoreId(0);
            } catch (NullPointerException npe) {
                accjournal.setStoreId(0);
            }
            try {
                accjournal.setBillTransactorId(0);
            } catch (NullPointerException npe) {
                accjournal.setBillTransactorId(0);
            }
            try {
                accjournal.setLedgerFolio("");
            } catch (NullPointerException npe) {
                accjournal.setLedgerFolio("");
            }
            try {
                accjournal.setAccCoaId(0);
            } catch (NullPointerException npe) {
                accjournal.setAccCoaId(0);
            }
            try {
                accjournal.setAccountCode("");
            } catch (NullPointerException npe) {
                accjournal.setAccountCode("");
            }
            try {
                accjournal.setCurrencyCode("");
            } catch (NullPointerException npe) {
                accjournal.setCurrencyCode("");
            }
            try {
                accjournal.setXrate(0);
            } catch (NullPointerException npe) {
                accjournal.setXrate(0);
            }
            try {
                accjournal.setDebitAmount(0);
            } catch (NullPointerException npe) {
                accjournal.setDebitAmount(0);
            }
            try {
                accjournal.setCreditAmount(0);
            } catch (NullPointerException npe) {
                accjournal.setCreditAmount(0);
            }
            try {
                accjournal.setNarration("");
            } catch (NullPointerException npe) {
                accjournal.setNarration("");
            }
            try {
                accjournal.setAccPeriodId(0);
            } catch (NullPointerException npe) {
                accjournal.setAccPeriodId(0);
            }
            try {
                accjournal.setAccChildAccountId(0);
            } catch (NullPointerException npe) {
                accjournal.setAccChildAccountId(0);
            }
            try {
                accjournal.setIsActive(0);
            } catch (NullPointerException npe) {
                accjournal.setIsActive(0);
            }
            try {
                accjournal.setIsDeleted(0);
            } catch (NullPointerException npe) {
                accjournal.setIsDeleted(0);
            }
            try {
                accjournal.setAddBy(0);
            } catch (NullPointerException npe) {
                accjournal.setAddBy(0);
            }
            try {
                accjournal.setLastEditBy(0);
            } catch (NullPointerException npe) {
                accjournal.setLastEditBy(0);
            }
            try {
                accjournal.setAddDate(null);
            } catch (NullPointerException npe) {
                accjournal.setAddDate(null);
            }
            try {
                accjournal.setLastEditDate(null);
            } catch (NullPointerException npe) {
                accjournal.setLastEditDate(null);
            }
            try {
                accjournal.setJobId(0);
            } catch (NullPointerException npe) {
                accjournal.setJobId(0);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
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
     * @return the AccJournalList
     */
    public List<AccJournal> getAccJournalList() {
        return AccJournalList;
    }

    /**
     * @param AccJournalList the AccJournalList to set
     */
    public void setAccJournalList(List<AccJournal> AccJournalList) {
        this.AccJournalList = AccJournalList;
    }

    /**
     * @return the AccJournalObj
     */
    public AccJournal getAccJournalObj() {
        return AccJournalObj;
    }

    /**
     * @param AccJournalObj the AccJournalObj to set
     */
    public void setAccJournalObj(AccJournal AccJournalObj) {
        this.AccJournalObj = AccJournalObj;
    }

    /**
     * @return the DateType
     */
    public String getDateType() {
        return DateType;
    }

    /**
     * @param DateType the DateType to set
     */
    public void setDateType(String DateType) {
        this.DateType = DateType;
    }

    /**
     * @return the Date1
     */
    public Date getDate1() {
        return Date1;
    }

    /**
     * @param Date1 the Date1 to set
     */
    public void setDate1(Date Date1) {
        this.Date1 = Date1;
    }

    /**
     * @return the Date2
     */
    public Date getDate2() {
        return Date2;
    }

    /**
     * @param Date2 the Date2 to set
     */
    public void setDate2(Date Date2) {
        this.Date2 = Date2;
    }

    /**
     * @return the AccJournalSummary
     */
    public List<AccJournal> getAccJournalSummary() {
        return AccJournalSummary;
    }

    /**
     * @param AccJournalSummary the AccJournalSummary to set
     */
    public void setAccJournalSummary(List<AccJournal> AccJournalSummary) {
        this.AccJournalSummary = AccJournalSummary;
    }

}
