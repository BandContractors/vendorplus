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
        this.CashReceiptsList = new ArrayList<>();
        this.CashPaymentsList = new ArrayList<>();
        this.MonthListSelected = new ArrayList<>();
        String sqlCR = "";
        String sqlCP = "";
        String wheresql = "";
        Date Date1 = null, Date2 = null;
        try {
            if (this.AccountPeriodId > 0) {
                AccPeriod accp = new AccPeriodBean().getAccPeriodById(this.AccountPeriodId);
                Date1 = accp.getStartDate();
                Date2 = accp.getEndDate();
            } else if (this.CalendarYear.length() > 0) {
                int CalYear = Integer.parseInt(this.CalendarYear);
                Date1=new Date();
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
                
                Date2=new Date();
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

}
