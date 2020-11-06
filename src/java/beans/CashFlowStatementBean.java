package beans;

import connections.DBConnection;
import entities.AccPeriod;
import entities.CashFlowStatement;
import entities.MonthList;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import utilities.UtilityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean
@SessionScoped
public class CashFlowStatementBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<CashFlowStatement> CashReceiptsList;
    private List<CashFlowStatement> CashPaymentsList;
    private String ActionMessage;
    private List<MonthList> MonthListSelected;
    private String CalendarYear;
    private int AccountPeriodId;
    private CashFlowStatement CashAtBegin;
    private CashFlowStatement CashAtEnd;
    private CashFlowStatement CashReceiptsTotals;
    private CashFlowStatement CashPaymentsTotals;
    private CashFlowStatement NetCashChange;

    public void setCashFlowStatementFromResultset(CashFlowStatement aCashFlowStatement, ResultSet rs) {
        try {
            try {
                aCashFlowStatement.setCash_category(rs.getString("cash_category"));
            } catch (Exception e) {
                aCashFlowStatement.setCash_category("");
            }
            try {
                aCashFlowStatement.setJan(rs.getDouble("Jan"));
            } catch (Exception e) {
                aCashFlowStatement.setJan(0.0);
            }
            try {
                aCashFlowStatement.setFeb(rs.getDouble("Feb"));
            } catch (Exception e) {
                aCashFlowStatement.setFeb(0.0);
            }
            try {
                aCashFlowStatement.setMar(rs.getDouble("Mar"));
            } catch (Exception e) {
                aCashFlowStatement.setMar(0.0);
            }
            try {
                aCashFlowStatement.setApr(rs.getDouble("Apr"));
            } catch (Exception e) {
                aCashFlowStatement.setApr(0.0);
            }
            try {
                aCashFlowStatement.setMay(rs.getDouble("May"));
            } catch (Exception e) {
                aCashFlowStatement.setMay(0.0);
            }
            try {
                aCashFlowStatement.setJun(rs.getDouble("Jun"));
            } catch (Exception e) {
                aCashFlowStatement.setJun(0.0);
            }
            try {
                aCashFlowStatement.setJul(rs.getDouble("Jul"));
            } catch (Exception e) {
                aCashFlowStatement.setJul(0.0);
            }
            try {
                aCashFlowStatement.setAug(rs.getDouble("Aug"));
            } catch (Exception e) {
                aCashFlowStatement.setAug(0.0);
            }
            try {
                aCashFlowStatement.setSep(rs.getDouble("Sep"));
            } catch (Exception e) {
                aCashFlowStatement.setSep(0.0);
            }
            try {
                aCashFlowStatement.setOct(rs.getDouble("Oct"));
            } catch (Exception e) {
                aCashFlowStatement.setOct(0.0);
            }
            try {
                aCashFlowStatement.setNov(rs.getDouble("Nov"));
            } catch (Exception e) {
                aCashFlowStatement.setNov(0.0);
            }
            try {
                aCashFlowStatement.setDec(rs.getDouble("Dec"));
            } catch (Exception e) {
                aCashFlowStatement.setDec(0.0);
            }
        } catch (Exception e) {
            System.out.println("setCashFlowStatementFromResultset:" + e.getMessage());
        }
    }

    public void reportCashFlowStatement() {
        ResultSet rsCR = null;
        ResultSet rsCP = null;
        ResultSet rsCB = null;
        ResultSet rsCE = null;
        this.CashReceiptsList = new ArrayList<>();
        this.CashPaymentsList = new ArrayList<>();
        this.MonthListSelected = new ArrayList<>();
        this.CashAtBegin = new CashFlowStatement();
        this.CashAtEnd = new CashFlowStatement();
        this.CashReceiptsTotals = new CashFlowStatement();
        this.CashPaymentsTotals = new CashFlowStatement();
        this.NetCashChange = new CashFlowStatement();
        String sqlCR = "";
        String sqlCP = "";
        String sqlCB = "";
        String sqlCE = "";
        String wheresql = "";
        Date Date1 = null, Date2 = null;
        try {
            if (this.AccountPeriodId > 0) {
                AccPeriod accp = new AccPeriodBean().getAccPeriodById(this.AccountPeriodId);
                Date1 = accp.getStartDate();
                Date2 = accp.getEndDate();
            } else if (this.CalendarYear.length() > 0) {
                int CalYear = Integer.parseInt(this.CalendarYear);
                Date1 = new Date();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, CalYear);
                cal.set(Calendar.MONTH, 0);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                // Put it back in the Date object
                Date1.setTime(cal.getTime().getTime());

                Date2 = new Date();
                Calendar cal2 = Calendar.getInstance();
                cal2.set(Calendar.YEAR, CalYear);
                cal2.set(Calendar.MONTH, 11);
                cal2.set(Calendar.DAY_OF_MONTH, 31);
                cal2.set(Calendar.HOUR_OF_DAY, 23);
                cal2.set(Calendar.MINUTE, 59);
                cal2.set(Calendar.SECOND, 0);
                cal2.set(Calendar.MILLISECOND, 0);
                // Put it back in the Date object
                Date2.setTime(cal2.getTime().getTime());
            }
        } catch (Exception e) {
            //do nothing
        }

        if (Date1 != null && Date2 != null) {
            wheresql = wheresql + " AND pay_date BETWEEN '" + new java.sql.Date(Date1.getTime()) + "' AND '" + new java.sql.Date(Date2.getTime()) + "'";
            sqlCR = "SELECT "
                    + "	cash_category,"
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 1, paid_amount, '') SEPARATOR '') AS 'Jan', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 2, paid_amount, '') SEPARATOR '') AS 'Feb', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 3, paid_amount, '') SEPARATOR '') AS 'Mar', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 4, paid_amount, '') SEPARATOR '') AS 'Apr', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 5, paid_amount, '') SEPARATOR '') AS 'May', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 6, paid_amount, '') SEPARATOR '') AS 'Jun', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 7, paid_amount, '') SEPARATOR '') AS 'Jul', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 8, paid_amount, '') SEPARATOR '') AS 'Aug', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 9, paid_amount, '') SEPARATOR '') AS 'Sep', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 10, paid_amount, '') SEPARATOR '') AS 'Oct', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 11, paid_amount, '') SEPARATOR '') AS 'Nov', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 12, paid_amount, '') SEPARATOR '') AS 'Dec' "
                    + "FROM "
                    + "("
                    + " SELECT display_order,cash_category,year_cal,month_cal,sum(paid_amount) as paid_amount"
                    + " FROM view_cash_receipt_detail WHERE 1=1 " + wheresql
                    + " GROUP BY display_order,cash_category,year_cal,month_cal ORDER BY cash_category,year_cal,month_cal"
                    + ") as rs "
                    + " GROUP BY display_order,cash_category";
            sqlCP = "SELECT "
                    + "	cash_category,"
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 1, paid_amount, '') SEPARATOR '') AS 'Jan', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 2, paid_amount, '') SEPARATOR '') AS 'Feb', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 3, paid_amount, '') SEPARATOR '') AS 'Mar', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 4, paid_amount, '') SEPARATOR '') AS 'Apr', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 5, paid_amount, '') SEPARATOR '') AS 'May', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 6, paid_amount, '') SEPARATOR '') AS 'Jun', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 7, paid_amount, '') SEPARATOR '') AS 'Jul', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 8, paid_amount, '') SEPARATOR '') AS 'Aug', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 9, paid_amount, '') SEPARATOR '') AS 'Sep', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 10, paid_amount, '') SEPARATOR '') AS 'Oct', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 11, paid_amount, '') SEPARATOR '') AS 'Nov', "
                    + "	GROUP_CONCAT(DISTINCT if(month_cal = 12, paid_amount, '') SEPARATOR '') AS 'Dec' "
                    + "FROM "
                    + "("
                    + " SELECT display_order,cash_category,year_cal,month_cal,sum(paid_amount) as paid_amount "
                    + " FROM view_cash_paid_detail "
                    + " WHERE 1=1 " + wheresql
                    + " GROUP BY display_order,cash_category,year_cal,month_cal "
                    + " ORDER BY display_order,cash_category,year_cal,month_cal"
                    + ") as rs "
                    + "GROUP BY display_order,cash_category";
            //CR
            //System.out.println("CR:" + sqlCR);
            try (
                    Connection connCR = DBConnection.getMySQLConnection();
                    PreparedStatement psCR = connCR.prepareStatement(sqlCR);) {
                rsCR = psCR.executeQuery();
                while (rsCR.next()) {
                    CashFlowStatement cfsCR = new CashFlowStatement();
                    this.setCashFlowStatementFromResultset(cfsCR, rsCR);
                    this.CashReceiptsList.add(cfsCR);
                }
            } catch (Exception e) {
                System.err.println("reportCashFlowStatement:CR:" + e.getMessage());
            }
            //CP
            //System.out.println("CP:" + sqlCP);
            try (
                    Connection connCP = DBConnection.getMySQLConnection();
                    PreparedStatement psCP = connCP.prepareStatement(sqlCP);) {
                rsCP = psCP.executeQuery();
                while (rsCP.next()) {
                    CashFlowStatement cfsCP = new CashFlowStatement();
                    this.setCashFlowStatementFromResultset(cfsCP, rsCP);
                    this.CashPaymentsList.add(cfsCP);
                }
            } catch (Exception e) {
                System.err.println("reportCashFlowStatement:CP:" + e.getMessage());
            }
            //Cash at Beginning
            sqlCB = "SELECT "
                    + "'Cash at Beginning' as cash_category,"
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 1, s.debit_balance_lc, '') SEPARATOR '') AS 'Jan', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 2, s.debit_balance_lc, '') SEPARATOR '') AS 'Feb', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 3, s.debit_balance_lc, '') SEPARATOR '') AS 'Mar', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 4, s.debit_balance_lc, '') SEPARATOR '') AS 'Apr', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 5, s.debit_balance_lc, '') SEPARATOR '') AS 'May', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 6, s.debit_balance_lc, '') SEPARATOR '') AS 'Jun', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 7, s.debit_balance_lc, '') SEPARATOR '') AS 'Jul', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 8, s.debit_balance_lc, '') SEPARATOR '') AS 'Aug', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 9, s.debit_balance_lc, '') SEPARATOR '') AS 'Sep', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 10, s.debit_balance_lc, '') SEPARATOR '') AS 'Oct', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 11, s.debit_balance_lc, '') SEPARATOR '') AS 'Nov', "
                    + "	GROUP_CONCAT(DISTINCT if(c.m = 12, s.debit_balance_lc, '') SEPARATOR '') AS 'Dec' "
                    + "FROM "
                    + "("
                    + "	select year(cdc_date) as y,month(cdc_date) as m,min(snapshot_no) as snapshot_no from cdc_general "
                    + "	where cdc_function='CASH' and is_passed=1 and cdc_date between '" + new java.sql.Date(Date1.getTime()) + "' AND '" + new java.sql.Date(Date2.getTime()) + "'"
                    + "	group by year(cdc_date),month(cdc_date) "
                    + "	order by year(cdc_date),month(cdc_date) "
                    + ") AS c "
                    + "INNER JOIN "
                    + "("
                    + "	select snapshot_no,sum(debit_balance_lc) as debit_balance_lc  "
                    + "	from snapshot_cash_balance where snapshot_date between '" + new java.sql.Date(Date1.getTime()) + "' AND '" + new java.sql.Date(Date2.getTime()) + "'"
                    + "	group by snapshot_no"
                    + ") AS s ON c.snapshot_no=s.snapshot_no "
                    + "GROUP BY c.y,c.m,c.snapshot_no";
            //System.out.println("sqlCB:" + sqlCB);
            try (
                    Connection connCB = DBConnection.getMySQLConnection();
                    PreparedStatement psCB = connCB.prepareStatement(sqlCB);) {
                rsCB = psCB.executeQuery();
                if (rsCB.next()) {
                    this.setCashFlowStatementFromResultset(this.CashAtBegin, rsCB);
                }
            } catch (Exception e) {
                System.err.println("reportCashFlowStatement:CB:" + e.getMessage());
            }
            //Cash at End

            //MonthList
            int MinM = 0, MinY = 0, MaxM = 0, MaxY = 0;
            Calendar cal1 = new GregorianCalendar();
            cal1.setTime(Date1);
            MinM = cal1.get(Calendar.MONTH) + 1;
            MinY = cal1.get(Calendar.YEAR);
            cal1.setTime(Date2);
            MaxM = cal1.get(Calendar.MONTH) + 1;
            MaxY = cal1.get(Calendar.YEAR);
            //System.out.println("From:" + MinM + "/" + MinY + " To:" + MaxM + "/" + MaxY);
            int M = 0, Y = 0;
            MonthList mon = null;
            if (MinY == MaxY) {
                Y = MinY;
                M = MinM;
                while (M <= MaxM) {
                    mon = new MonthList();
                    mon.setMonthNo(M);
                    mon.setMonthName(new UtilityBean().convertMonthNoToName(M, 1));
                    mon.setMonthYearName(mon.getMonthName() + "/" + Y);
                    this.MonthListSelected.add(mon);
                    M = M + 1;
                }
            } else if (MinY < MaxY) {
                Y = MinY;
                M = MinM;
                while (M <= 12) {
                    mon = new MonthList();
                    mon.setMonthNo(M);
                    mon.setMonthName(new UtilityBean().convertMonthNoToName(M, 1));
                    mon.setMonthYearName(mon.getMonthName() + "/" + Y);
                    this.MonthListSelected.add(mon);
                    M = M + 1;
                }
                Y = MaxY;
                M = 1;
                while (M <= MaxM) {
                    mon = new MonthList();
                    mon.setMonthNo(M);
                    mon.setMonthName(new UtilityBean().convertMonthNoToName(M, 1));
                    mon.setMonthYearName(mon.getMonthName() + "/" + Y);
                    this.MonthListSelected.add(mon);
                    M = M + 1;
                }
            }
            //Totals-CR
            this.CashReceiptsTotals.setCash_category("Cash Receipts Total");
            for (int i = 0; i < this.CashReceiptsList.size(); i++) {
                this.CashReceiptsTotals.setJan(this.CashReceiptsTotals.getJan() + this.CashReceiptsList.get(i).getJan());
                this.CashReceiptsTotals.setFeb(this.CashReceiptsTotals.getFeb() + this.CashReceiptsList.get(i).getFeb());
                this.CashReceiptsTotals.setMar(this.CashReceiptsTotals.getMar() + this.CashReceiptsList.get(i).getMar());
                this.CashReceiptsTotals.setApr(this.CashReceiptsTotals.getApr() + this.CashReceiptsList.get(i).getApr());
                this.CashReceiptsTotals.setMay(this.CashReceiptsTotals.getMay() + this.CashReceiptsList.get(i).getMay());
                this.CashReceiptsTotals.setJun(this.CashReceiptsTotals.getJun() + this.CashReceiptsList.get(i).getJun());
                this.CashReceiptsTotals.setJul(this.CashReceiptsTotals.getJul() + this.CashReceiptsList.get(i).getJul());
                this.CashReceiptsTotals.setAug(this.CashReceiptsTotals.getAug() + this.CashReceiptsList.get(i).getAug());
                this.CashReceiptsTotals.setSep(this.CashReceiptsTotals.getSep() + this.CashReceiptsList.get(i).getSep());
                this.CashReceiptsTotals.setOct(this.CashReceiptsTotals.getOct() + this.CashReceiptsList.get(i).getOct());
                this.CashReceiptsTotals.setNov(this.CashReceiptsTotals.getNov() + this.CashReceiptsList.get(i).getNov());
                this.CashReceiptsTotals.setDec(this.CashReceiptsTotals.getDec() + this.CashReceiptsList.get(i).getDec());
            }
            //Totals-CP
            this.CashPaymentsTotals.setCash_category("Cash Payments Total");
            for (int i = 0; i < this.CashPaymentsList.size(); i++) {
                this.CashPaymentsTotals.setJan(this.CashPaymentsTotals.getJan() + this.CashPaymentsList.get(i).getJan());
                this.CashPaymentsTotals.setFeb(this.CashPaymentsTotals.getFeb() + this.CashPaymentsList.get(i).getFeb());
                this.CashPaymentsTotals.setMar(this.CashPaymentsTotals.getMar() + this.CashPaymentsList.get(i).getMar());
                this.CashPaymentsTotals.setApr(this.CashPaymentsTotals.getApr() + this.CashPaymentsList.get(i).getApr());
                this.CashPaymentsTotals.setMay(this.CashPaymentsTotals.getMay() + this.CashPaymentsList.get(i).getMay());
                this.CashPaymentsTotals.setJun(this.CashPaymentsTotals.getJun() + this.CashPaymentsList.get(i).getJun());
                this.CashPaymentsTotals.setJul(this.CashPaymentsTotals.getJul() + this.CashPaymentsList.get(i).getJul());
                this.CashPaymentsTotals.setAug(this.CashPaymentsTotals.getAug() + this.CashPaymentsList.get(i).getAug());
                this.CashPaymentsTotals.setSep(this.CashPaymentsTotals.getSep() + this.CashPaymentsList.get(i).getSep());
                this.CashPaymentsTotals.setOct(this.CashPaymentsTotals.getOct() + this.CashPaymentsList.get(i).getOct());
                this.CashPaymentsTotals.setNov(this.CashPaymentsTotals.getNov() + this.CashPaymentsList.get(i).getNov());
                this.CashPaymentsTotals.setDec(this.CashPaymentsTotals.getDec() + this.CashPaymentsList.get(i).getDec());
            }
            //Net Cash Change
            this.NetCashChange.setCash_category("Net Cash Change");
            if (this.getCashPaymentsTotals().getJan() >= 0) {
                this.NetCashChange.setJan(this.CashReceiptsTotals.getJan() - this.getCashPaymentsTotals().getJan());
            } else {
                this.NetCashChange.setJan(this.CashReceiptsTotals.getJan() + this.getCashPaymentsTotals().getJan());
            }
            if (this.getCashPaymentsTotals().getFeb() >= 0) {
                this.NetCashChange.setFeb(this.CashReceiptsTotals.getFeb() - this.getCashPaymentsTotals().getFeb());
            } else {
                this.NetCashChange.setFeb(this.CashReceiptsTotals.getFeb() + this.getCashPaymentsTotals().getFeb());
            }
            if (this.getCashPaymentsTotals().getMar() >= 0) {
                this.NetCashChange.setMar(this.CashReceiptsTotals.getMar() - this.getCashPaymentsTotals().getMar());
            } else {
                this.NetCashChange.setMar(this.CashReceiptsTotals.getMar() + this.getCashPaymentsTotals().getMar());
            }
            if (this.getCashPaymentsTotals().getApr() >= 0) {
                this.NetCashChange.setApr(this.CashReceiptsTotals.getApr() - this.getCashPaymentsTotals().getApr());
            } else {
                this.NetCashChange.setApr(this.CashReceiptsTotals.getApr() + this.getCashPaymentsTotals().getApr());
            }
            if (this.getCashPaymentsTotals().getMay() >= 0) {
                this.NetCashChange.setMay(this.CashReceiptsTotals.getMay() - this.getCashPaymentsTotals().getMay());
            } else {
                this.NetCashChange.setMay(this.CashReceiptsTotals.getMay() + this.getCashPaymentsTotals().getMay());
            }
            if (this.getCashPaymentsTotals().getJun() >= 0) {
                this.NetCashChange.setJun(this.CashReceiptsTotals.getJun() - this.getCashPaymentsTotals().getJun());
            } else {
                this.NetCashChange.setJun(this.CashReceiptsTotals.getJun() + this.getCashPaymentsTotals().getJun());
            }
            if (this.getCashPaymentsTotals().getJul() >= 0) {
                this.NetCashChange.setJul(this.CashReceiptsTotals.getJul() - this.getCashPaymentsTotals().getJul());
            } else {
                this.NetCashChange.setJul(this.CashReceiptsTotals.getJul() + this.getCashPaymentsTotals().getJul());
            }
            if (this.getCashPaymentsTotals().getAug() >= 0) {
                this.NetCashChange.setAug(this.CashReceiptsTotals.getAug() - this.getCashPaymentsTotals().getAug());
            } else {
                this.NetCashChange.setAug(this.CashReceiptsTotals.getAug() + this.getCashPaymentsTotals().getAug());
            }
            if (this.getCashPaymentsTotals().getSep() >= 0) {
                this.NetCashChange.setSep(this.CashReceiptsTotals.getSep() - this.getCashPaymentsTotals().getSep());
            } else {
                this.NetCashChange.setSep(this.CashReceiptsTotals.getSep() + this.getCashPaymentsTotals().getSep());
            }
            if (this.getCashPaymentsTotals().getOct() >= 0) {
                this.NetCashChange.setOct(this.CashReceiptsTotals.getOct() - this.getCashPaymentsTotals().getOct());
            } else {
                this.NetCashChange.setOct(this.CashReceiptsTotals.getOct() + this.getCashPaymentsTotals().getOct());
            }
            if (this.getCashPaymentsTotals().getNov() >= 0) {
                this.NetCashChange.setNov(this.CashReceiptsTotals.getNov() - this.getCashPaymentsTotals().getNov());
            } else {
                this.NetCashChange.setNov(this.CashReceiptsTotals.getNov() + this.getCashPaymentsTotals().getNov());
            }
            if (this.getCashPaymentsTotals().getDec() >= 0) {
                this.NetCashChange.setDec(this.CashReceiptsTotals.getDec() - this.getCashPaymentsTotals().getDec());
            } else {
                this.NetCashChange.setDec(this.CashReceiptsTotals.getDec() + this.getCashPaymentsTotals().getDec());
            }
            //Cash at End
            this.CashAtEnd.setCash_category("Cash at End");
            this.CashAtEnd.setJan(this.CashAtBegin.getJan() + this.getNetCashChange().getJan());
            this.CashAtEnd.setFeb(this.CashAtBegin.getFeb() + this.getNetCashChange().getFeb());
            this.CashAtEnd.setMar(this.CashAtBegin.getMar() + this.getNetCashChange().getMar());
            this.CashAtEnd.setApr(this.CashAtBegin.getApr() + this.getNetCashChange().getApr());
            this.CashAtEnd.setMay(this.CashAtBegin.getMay() + this.getNetCashChange().getMay());
            this.CashAtEnd.setJun(this.CashAtBegin.getJun() + this.getNetCashChange().getJun());
            this.CashAtEnd.setJul(this.CashAtBegin.getJul() + this.getNetCashChange().getJul());
            this.CashAtEnd.setAug(this.CashAtBegin.getAug() + this.getNetCashChange().getAug());
            this.CashAtEnd.setSep(this.CashAtBegin.getSep() + this.getNetCashChange().getSep());
            this.CashAtEnd.setOct(this.CashAtBegin.getOct() + this.getNetCashChange().getOct());
            this.CashAtEnd.setNov(this.CashAtBegin.getNov() + this.getNetCashChange().getNov());
            this.CashAtEnd.setDec(this.CashAtBegin.getDec() + this.getNetCashChange().getDec());
        }
    }

    public double getMonthValueFromCR(String aCashCategory, String aMonthName) {
        double MonthValue = 0.0;
        try {
            if (aCashCategory.length() > 0 && aMonthName.length() > 0) {
                for (int i = 0; i < this.CashReceiptsList.size(); i++) {
                    if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Jan")) {
                        MonthValue = this.CashReceiptsList.get(i).getJan();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Feb")) {
                        MonthValue = this.CashReceiptsList.get(i).getFeb();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Mar")) {
                        MonthValue = this.CashReceiptsList.get(i).getMar();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Apr")) {
                        MonthValue = this.CashReceiptsList.get(i).getApr();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("May")) {
                        MonthValue = this.CashReceiptsList.get(i).getMay();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Jun")) {
                        MonthValue = this.CashReceiptsList.get(i).getJun();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Jul")) {
                        MonthValue = this.CashReceiptsList.get(i).getJul();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Aug")) {
                        MonthValue = this.CashReceiptsList.get(i).getAug();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Sep")) {
                        MonthValue = this.CashReceiptsList.get(i).getSep();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Oct")) {
                        MonthValue = this.CashReceiptsList.get(i).getOct();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Nov")) {
                        MonthValue = this.CashReceiptsList.get(i).getNov();
                        break;
                    } else if (this.CashReceiptsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Dec")) {
                        MonthValue = this.CashReceiptsList.get(i).getDec();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            //do nothing
        }
        return MonthValue;
    }

    public double getMonthValueFromCP(String aCashCategory, String aMonthName) {
        double MonthValue = 0.0;
        try {
            if (aCashCategory.length() > 0 && aMonthName.length() > 0) {
                for (int i = 0; i < this.CashPaymentsList.size(); i++) {
                    if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Jan")) {
                        MonthValue = this.CashPaymentsList.get(i).getJan();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Feb")) {
                        MonthValue = this.CashPaymentsList.get(i).getFeb();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Mar")) {
                        MonthValue = this.CashPaymentsList.get(i).getMar();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Apr")) {
                        MonthValue = this.CashPaymentsList.get(i).getApr();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("May")) {
                        MonthValue = this.CashPaymentsList.get(i).getMay();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Jun")) {
                        MonthValue = this.CashPaymentsList.get(i).getJun();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Jul")) {
                        MonthValue = this.CashPaymentsList.get(i).getJul();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Aug")) {
                        MonthValue = this.CashPaymentsList.get(i).getAug();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Sep")) {
                        MonthValue = this.CashPaymentsList.get(i).getSep();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Oct")) {
                        MonthValue = this.CashPaymentsList.get(i).getOct();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Nov")) {
                        MonthValue = this.CashPaymentsList.get(i).getNov();
                        break;
                    } else if (this.CashPaymentsList.get(i).getCash_category().equals(aCashCategory) && aMonthName.equals("Dec")) {
                        MonthValue = this.CashPaymentsList.get(i).getDec();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            //do nothing
        }
        return MonthValue;
    }

    public double getMonthValueFromCB(String aMonthName) {
        double MonthValue = 0.0;
        try {
            if (aMonthName.length() > 0) {
                if (aMonthName.equals("Jan")) {
                    MonthValue = this.CashAtBegin.getJan();
                } else if (aMonthName.equals("Feb")) {
                    MonthValue = this.CashAtBegin.getFeb();
                } else if (aMonthName.equals("Mar")) {
                    MonthValue = this.CashAtBegin.getMar();
                } else if (aMonthName.equals("Apr")) {
                    MonthValue = this.CashAtBegin.getApr();
                } else if (aMonthName.equals("May")) {
                    MonthValue = this.CashAtBegin.getMay();
                } else if (aMonthName.equals("Jun")) {
                    MonthValue = this.CashAtBegin.getJun();
                } else if (aMonthName.equals("Jul")) {
                    MonthValue = this.CashAtBegin.getJul();
                } else if (aMonthName.equals("Aug")) {
                    MonthValue = this.CashAtBegin.getAug();
                } else if (aMonthName.equals("Sep")) {
                    MonthValue = this.CashAtBegin.getSep();
                } else if (aMonthName.equals("Oct")) {
                    MonthValue = this.CashAtBegin.getOct();
                } else if (aMonthName.equals("Nov")) {
                    MonthValue = this.CashAtBegin.getNov();
                } else if (aMonthName.equals("Dec")) {
                    MonthValue = this.CashAtBegin.getDec();
                }
            }
        } catch (Exception e) {
            //do nothing
        }
        return MonthValue;
    }

    public double getMonthValueFromCRT(String aMonthName) {
        double MonthValue = 0.0;
        try {
            if (aMonthName.length() > 0) {
                if (aMonthName.equals("Jan")) {
                    MonthValue = this.CashReceiptsTotals.getJan();
                } else if (aMonthName.equals("Feb")) {
                    MonthValue = this.CashReceiptsTotals.getFeb();
                } else if (aMonthName.equals("Mar")) {
                    MonthValue = this.CashReceiptsTotals.getMar();
                } else if (aMonthName.equals("Apr")) {
                    MonthValue = this.CashReceiptsTotals.getApr();
                } else if (aMonthName.equals("May")) {
                    MonthValue = this.CashReceiptsTotals.getMay();
                } else if (aMonthName.equals("Jun")) {
                    MonthValue = this.CashReceiptsTotals.getJun();
                } else if (aMonthName.equals("Jul")) {
                    MonthValue = this.CashReceiptsTotals.getJul();
                } else if (aMonthName.equals("Aug")) {
                    MonthValue = this.CashReceiptsTotals.getAug();
                } else if (aMonthName.equals("Sep")) {
                    MonthValue = this.CashReceiptsTotals.getSep();
                } else if (aMonthName.equals("Oct")) {
                    MonthValue = this.CashReceiptsTotals.getOct();
                } else if (aMonthName.equals("Nov")) {
                    MonthValue = this.CashReceiptsTotals.getNov();
                } else if (aMonthName.equals("Dec")) {
                    MonthValue = this.CashReceiptsTotals.getDec();
                }
            }
        } catch (Exception e) {
            //do nothing
        }
        return MonthValue;
    }

    public double getMonthValueFromCPT(String aMonthName) {
        double MonthValue = 0.0;
        try {
            if (aMonthName.length() > 0) {
                if (aMonthName.equals("Jan")) {
                    MonthValue = this.CashPaymentsTotals.getJan();
                } else if (aMonthName.equals("Feb")) {
                    MonthValue = this.CashPaymentsTotals.getFeb();
                } else if (aMonthName.equals("Mar")) {
                    MonthValue = this.CashPaymentsTotals.getMar();
                } else if (aMonthName.equals("Apr")) {
                    MonthValue = this.CashPaymentsTotals.getApr();
                } else if (aMonthName.equals("May")) {
                    MonthValue = this.CashPaymentsTotals.getMay();
                } else if (aMonthName.equals("Jun")) {
                    MonthValue = this.CashPaymentsTotals.getJun();
                } else if (aMonthName.equals("Jul")) {
                    MonthValue = this.CashPaymentsTotals.getJul();
                } else if (aMonthName.equals("Aug")) {
                    MonthValue = this.CashPaymentsTotals.getAug();
                } else if (aMonthName.equals("Sep")) {
                    MonthValue = this.CashPaymentsTotals.getSep();
                } else if (aMonthName.equals("Oct")) {
                    MonthValue = this.CashPaymentsTotals.getOct();
                } else if (aMonthName.equals("Nov")) {
                    MonthValue = this.CashPaymentsTotals.getNov();
                } else if (aMonthName.equals("Dec")) {
                    MonthValue = this.CashPaymentsTotals.getDec();
                }
            }
        } catch (Exception e) {
            //do nothing
        }
        return MonthValue;
    }
    
    public double getMonthValueFromNCC(String aMonthName) {
        double MonthValue = 0.0;
        try {
            if (aMonthName.length() > 0) {
                if (aMonthName.equals("Jan")) {
                    MonthValue = this.NetCashChange.getJan();
                } else if (aMonthName.equals("Feb")) {
                    MonthValue = this.NetCashChange.getFeb();
                } else if (aMonthName.equals("Mar")) {
                    MonthValue = this.NetCashChange.getMar();
                } else if (aMonthName.equals("Apr")) {
                    MonthValue = this.NetCashChange.getApr();
                } else if (aMonthName.equals("May")) {
                    MonthValue = this.NetCashChange.getMay();
                } else if (aMonthName.equals("Jun")) {
                    MonthValue = this.NetCashChange.getJun();
                } else if (aMonthName.equals("Jul")) {
                    MonthValue = this.NetCashChange.getJul();
                } else if (aMonthName.equals("Aug")) {
                    MonthValue = this.NetCashChange.getAug();
                } else if (aMonthName.equals("Sep")) {
                    MonthValue = this.NetCashChange.getSep();
                } else if (aMonthName.equals("Oct")) {
                    MonthValue = this.NetCashChange.getOct();
                } else if (aMonthName.equals("Nov")) {
                    MonthValue = this.NetCashChange.getNov();
                } else if (aMonthName.equals("Dec")) {
                    MonthValue = this.NetCashChange.getDec();
                }
            }
        } catch (Exception e) {
            //do nothing
        }
        return MonthValue;
    }
    
    public double getMonthValueFromCAE(String aMonthName) {
        double MonthValue = 0.0;
        try {
            if (aMonthName.length() > 0) {
                if (aMonthName.equals("Jan")) {
                    MonthValue = this.CashAtEnd.getJan();
                } else if (aMonthName.equals("Feb")) {
                    MonthValue = this.CashAtEnd.getFeb();
                } else if (aMonthName.equals("Mar")) {
                    MonthValue = this.CashAtEnd.getMar();
                } else if (aMonthName.equals("Apr")) {
                    MonthValue = this.CashAtEnd.getApr();
                } else if (aMonthName.equals("May")) {
                    MonthValue = this.CashAtEnd.getMay();
                } else if (aMonthName.equals("Jun")) {
                    MonthValue = this.CashAtEnd.getJun();
                } else if (aMonthName.equals("Jul")) {
                    MonthValue = this.CashAtEnd.getJul();
                } else if (aMonthName.equals("Aug")) {
                    MonthValue = this.CashAtEnd.getAug();
                } else if (aMonthName.equals("Sep")) {
                    MonthValue = this.CashAtEnd.getSep();
                } else if (aMonthName.equals("Oct")) {
                    MonthValue = this.CashAtEnd.getOct();
                } else if (aMonthName.equals("Nov")) {
                    MonthValue = this.CashAtEnd.getNov();
                } else if (aMonthName.equals("Dec")) {
                    MonthValue = this.CashAtEnd.getDec();
                }
            }
        } catch (Exception e) {
            //do nothing
        }
        return MonthValue;
    }

    public void initCashFlowStatement() {
        this.AccountPeriodId = 0;
        this.CalendarYear = "";
        this.ActionMessage = "";
        try {
            this.CashReceiptsList.clear();
        } catch (Exception e) {
            //do nothing
        }
        try {
            this.CashPaymentsList.clear();
        } catch (Exception e) {
            //do nothing
        }
        try {
            this.MonthListSelected.clear();
        } catch (Exception e) {
            //do nothing
        }
    }

    /**
     * @return the CashReceiptsList
     */
    public List<CashFlowStatement> getCashReceiptsList() {
        return CashReceiptsList;
    }

    /**
     * @param CashReceiptsList the CashReceiptsList to set
     */
    public void setCashReceiptsList(List<CashFlowStatement> CashReceiptsList) {
        this.CashReceiptsList = CashReceiptsList;
    }

    /**
     * @return the CashPaymentsList
     */
    public List<CashFlowStatement> getCashPaymentsList() {
        return CashPaymentsList;
    }

    /**
     * @param CashPaymentsList the CashPaymentsList to set
     */
    public void setCashPaymentsList(List<CashFlowStatement> CashPaymentsList) {
        this.CashPaymentsList = CashPaymentsList;
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
     * @return the MonthListSelected
     */
    public List<MonthList> getMonthListSelected() {
        return MonthListSelected;
    }

    /**
     * @param MonthListSelected the MonthListSelected to set
     */
    public void setMonthListSelected(List<MonthList> MonthListSelected) {
        this.MonthListSelected = MonthListSelected;
    }

    /**
     * @return the CalendarYear
     */
    public String getCalendarYear() {
        return CalendarYear;
    }

    /**
     * @param CalendarYear the CalendarYear to set
     */
    public void setCalendarYear(String CalendarYear) {
        this.CalendarYear = CalendarYear;
    }

    /**
     * @return the AccountPeriodId
     */
    public int getAccountPeriodId() {
        return AccountPeriodId;
    }

    /**
     * @param AccountPeriodId the AccountPeriodId to set
     */
    public void setAccountPeriodId(int AccountPeriodId) {
        this.AccountPeriodId = AccountPeriodId;
    }

    /**
     * @return the CashAtBegin
     */
    public CashFlowStatement getCashAtBegin() {
        return CashAtBegin;
    }

    /**
     * @param CashAtBegin the CashAtBegin to set
     */
    public void setCashAtBegin(CashFlowStatement CashAtBegin) {
        this.CashAtBegin = CashAtBegin;
    }

    /**
     * @return the CashAtEnd
     */
    public CashFlowStatement getCashAtEnd() {
        return CashAtEnd;
    }

    /**
     * @param CashAtEnd the CashAtEnd to set
     */
    public void setCashAtEnd(CashFlowStatement CashAtEnd) {
        this.CashAtEnd = CashAtEnd;
    }

    /**
     * @return the CashReceiptsTotals
     */
    public CashFlowStatement getCashReceiptsTotals() {
        return CashReceiptsTotals;
    }

    /**
     * @param CashReceiptsTotals the CashReceiptsTotals to set
     */
    public void setCashReceiptsTotals(CashFlowStatement CashReceiptsTotals) {
        this.CashReceiptsTotals = CashReceiptsTotals;
    }

    /**
     * @return the CashPaymentsTotals
     */
    public CashFlowStatement getCashPaymentsTotals() {
        return CashPaymentsTotals;
    }

    /**
     * @param CashPaymentsTotals the CashPaymentsTotals to set
     */
    public void setCashPaymentsTotals(CashFlowStatement CashPaymentsTotals) {
        this.CashPaymentsTotals = CashPaymentsTotals;
    }

    /**
     * @return the NetCashChange
     */
    public CashFlowStatement getNetCashChange() {
        return NetCashChange;
    }

    /**
     * @param NetCashChange the NetCashChange to set
     */
    public void setNetCashChange(CashFlowStatement NetCashChange) {
        this.NetCashChange = NetCashChange;
    }

}
