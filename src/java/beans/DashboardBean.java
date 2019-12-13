package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.Pay;
import entities.Stock_ledger;
import entities.Trans;
import utilities.UtilityBean;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@ManagedBean(name = "dashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String YearsSalesByYear;
    private String AmountSalesByYear;
    private String MonthSalesByMonth;
    private String AmountSalesByMonth;
    private String DaySalesByDay;
    private String AmountSalesByDay;
    private String AvgMonthSalesByMonth;
    private String AvgMonthQtyByMonth;
    private String AvgAmountSalesByMonth;
    private int CurrentYear;
    private int CurrentMonthNo;
    private int SelectedYear;
    private int SelectedMonthNo;
    private String SelectedMonthName;
    private int SelectedDisplayYears;
    private int SelectedDisplayItems;
    private String ItemsTopSoldItemsByYear;
    private String AmountTopSoldItemsByYear;
    private String QtyTopSoldItemsByYear;
    private String CatItemsTopSoldItemsByYear;
    private String CatAmountTopSoldItemsByYear;
    private String CatQtyTopSoldItemsByYear;
    private List<Integer> YearsList;
    private List<Integer> MonthNoList;
    private int ClickedYear;
    private int ClickedMonthNo;
    private String ClickedMonthName;

    private String YearsExpensesByYear;
    private String AmountExpensesByYear;
    private String MonthExpensesByMonth;
    private String AmountExpensesByMonth;
    private String DayExpensesByDay;
    private String AmountExpensesByDay;

    private String ItemsTopExpenseItemsByYear;
    private String AmountTopExpenseItemsByYear;
    private String CatItemsTopExpenseItemsByYear;
    private String CatAmountTopExpenseItemsByYear;

    private List<Date> MultipleDates;
    private List<Pay> DayCloseCashReceiptList;
    private List<Trans> DayClosePurchaseList;
    private List<Pay> DayCloseCashPaymentList;
    private DashboardModel model;
    private List<Trans> DayCloseSalesList2;
    private List<Pay> DayCloseCashReceiptList2;
    private List<Trans> DayClosePurchaseList2;
    private List<Pay> DayCloseCashPaymentList2;
    private Date FromDate;
    private Date ToDate;
    private double TotalSales;
    private double TotalCashDiscount;
    private List<Trans> DayCloseSalesCash;
    private List<Trans> DayCloseSalesCredit;
    private List<Trans> DayCloseSalesStore;
    private double TotalCashReceipt;
    private List<Pay> DayCloseCashReceiptType;
    private List<Pay> DayCloseCashReceiptAccount;
    private double TotalPurchases;
    private List<Trans> DayClosePurchasesCash;
    private List<Trans> DayClosePurchasesCredit;
    private List<Trans> DayClosePurchasesType;
    private List<Trans> DayClosePurchasesStore;
    private double TotalCashPayment;
    private List<Pay> DayCloseCashPaymentType;
    private List<Pay> DayCloseCashPaymentAccount;

    private long CountItemsAdded;
    private long CountItemsSubtracted;
    private List<Stock_ledger> DayCloseStockMovementAdded;
    private List<Stock_ledger> DayCloseStockMovementSubtracted;

    private String DayStockByDay;
    private String AmountStockByDay;
    
    private String stock_type;

    public void initSalesDashboard() {
        try {
            this.CurrentYear = new UtilityBean().getCurrentYear();
            this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
            this.SelectedYear = this.CurrentYear;
            this.SelectedMonthNo = this.CurrentMonthNo;
            this.ClickedYear = this.CurrentYear;
            this.ClickedMonthNo = this.CurrentMonthNo;
            this.SelectedMonthName = new UtilityBean().convertMonthNoToName(this.SelectedMonthNo, 0);
            this.ClickedMonthName = new UtilityBean().convertMonthNoToName(this.ClickedMonthNo, 0);
            this.SelectedDisplayYears = 5;
            this.refreshSalesByYear(this.SelectedYear, this.SelectedDisplayYears);
            this.refreshSalesByMonth(this.ClickedYear);
            this.refreshAvgSalesByMonth(this.ClickedYear);
            this.refreshSalesByDay(this.ClickedYear, this.ClickedMonthNo, "");
        } catch (Exception e) {
            System.err.println("initSalesDashboard:" + e.getMessage());
        }
    }

    public void initStockDashboard() {
        try {
            this.CurrentYear = new UtilityBean().getCurrentYear();
            this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
            this.SelectedYear = this.CurrentYear;
            this.SelectedMonthNo = this.CurrentMonthNo;
            this.ClickedYear = this.CurrentYear;
            this.ClickedMonthNo = this.CurrentMonthNo;
            this.SelectedMonthName = new UtilityBean().convertMonthNoToName(this.SelectedMonthNo, 0);
            this.ClickedMonthName = new UtilityBean().convertMonthNoToName(this.ClickedMonthNo, 0);
            this.SelectedDisplayYears = 5;
            this.stock_type="";
            this.refreshStockByYear(this.SelectedYear, this.SelectedDisplayYears,this.stock_type);
        } catch (Exception e) {
            System.err.println("initStockDashboard:" + e.getMessage());
        }
    }

    public void initExpensesDashboard() {
        try {
            this.CurrentYear = new UtilityBean().getCurrentYear();
            this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
            this.SelectedYear = this.CurrentYear;
            this.SelectedMonthNo = this.CurrentMonthNo;
            this.ClickedYear = this.CurrentYear;
            this.ClickedMonthNo = this.CurrentMonthNo;
            this.SelectedMonthName = new UtilityBean().convertMonthNoToName(this.SelectedMonthNo, 0);
            this.ClickedMonthName = new UtilityBean().convertMonthNoToName(this.ClickedMonthNo, 0);
            this.SelectedDisplayYears = 5;
            this.refreshExpensesByYear(this.SelectedYear, this.SelectedDisplayYears);
            this.refreshExpensesByMonth(this.ClickedYear);
            this.refreshExpensesByDay(this.ClickedYear, this.ClickedMonthNo, "");
        } catch (Exception e) {
            System.err.println("initExpensesDashboard:" + e.getMessage());
        }
    }

    public void initSaleItemsDashboard() {
        try {
            this.CurrentYear = new UtilityBean().getCurrentYear();
            this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
            this.SelectedYear = this.CurrentYear;
            this.SelectedMonthNo = this.CurrentMonthNo;
            this.SelectedMonthName = new UtilityBean().convertMonthNoToName(this.SelectedMonthNo, 0);
            this.SelectedDisplayItems = 5;
            this.refreshSaleItemsByYear(this.SelectedYear, this.SelectedDisplayItems);
            this.refreshCategorySaleItemsByYear(this.SelectedYear, this.SelectedDisplayItems);
        } catch (Exception e) {
            System.err.println("initSaleItemsDashboard:" + e.getMessage());
        }
    }

    public void initExpenseItemsDashboard() {
        try {
            this.CurrentYear = new UtilityBean().getCurrentYear();
            this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
            this.SelectedYear = this.CurrentYear;
            this.SelectedMonthNo = this.CurrentMonthNo;
            this.SelectedMonthName = new UtilityBean().convertMonthNoToName(this.SelectedMonthNo, 0);
            this.SelectedDisplayItems = 5;
            this.refreshExpenseItemsByYear(this.SelectedYear, this.SelectedDisplayItems);
            this.refreshCategoryExpenseItemsByYear(this.SelectedYear, this.SelectedDisplayItems);
        } catch (Exception e) {
            System.err.println("initExpenseItemsDashboard:" + e.getMessage());
        }
    }

    public void searchSalesDashboard() {
        try {
            this.CurrentYear = new UtilityBean().getCurrentYear();
            this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
            if (this.SelectedYear == this.CurrentYear) {
                this.SelectedMonthNo = this.CurrentMonthNo;
            } else {
                this.SelectedMonthNo = 12;
            }
            this.ClickedYear = this.SelectedYear;
            this.ClickedMonthNo = this.SelectedMonthNo;
            this.SelectedMonthName = new UtilityBean().convertMonthNoToName(this.SelectedMonthNo, 0);
            this.ClickedMonthName = new UtilityBean().convertMonthNoToName(this.ClickedMonthNo, 0);
            this.refreshSalesByYear(this.SelectedYear, this.SelectedDisplayYears);
            this.refreshSalesByMonth(this.ClickedYear);
            this.refreshAvgSalesByMonth(this.ClickedYear);
            this.refreshSalesByDay(this.ClickedYear, this.ClickedMonthNo, "");
        } catch (Exception e) {
            System.err.println("searchSalesDashboard:" + e.getMessage());
        }
    }

    public void searchStockDashboard() {
        try {
            this.CurrentYear = new UtilityBean().getCurrentYear();
            this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
            if (this.SelectedYear == this.CurrentYear) {
                this.SelectedMonthNo = this.CurrentMonthNo;
            } else {
                this.SelectedMonthNo = 12;
            }
            this.ClickedYear = this.SelectedYear;
            this.ClickedMonthNo = this.SelectedMonthNo;
            this.SelectedMonthName = new UtilityBean().convertMonthNoToName(this.SelectedMonthNo, 0);
            this.ClickedMonthName = new UtilityBean().convertMonthNoToName(this.ClickedMonthNo, 0);
            this.refreshStockByYear(this.SelectedYear, this.SelectedDisplayYears,this.stock_type);
        } catch (Exception e) {
            System.err.println("searchStockDashboard:" + e.getMessage());
        }
    }

    public void searchExpensesDashboard() {
        try {
            this.CurrentYear = new UtilityBean().getCurrentYear();
            this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
            if (this.SelectedYear == this.CurrentYear) {
                this.SelectedMonthNo = this.CurrentMonthNo;
            } else {
                this.SelectedMonthNo = 12;
            }
            this.ClickedYear = this.SelectedYear;
            this.ClickedMonthNo = this.SelectedMonthNo;
            this.SelectedMonthName = new UtilityBean().convertMonthNoToName(this.SelectedMonthNo, 0);
            this.ClickedMonthName = new UtilityBean().convertMonthNoToName(this.ClickedMonthNo, 0);
            this.refreshExpensesByYear(this.SelectedYear, this.SelectedDisplayYears);
            this.refreshExpensesByMonth(this.ClickedYear);
            this.refreshExpensesByDay(this.ClickedYear, this.ClickedMonthNo, "");
        } catch (Exception e) {
            System.err.println("searchExpensesDashboard:" + e.getMessage());
        }
    }

    public void searchSaleItemsDashboard() {
        try {
            this.CurrentYear = new UtilityBean().getCurrentYear();
            this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
            if (this.SelectedYear == this.CurrentYear) {
                this.SelectedMonthNo = this.CurrentMonthNo;
            } else {
                this.SelectedMonthNo = 12;
            }
            this.SelectedMonthName = new UtilityBean().convertMonthNoToName(this.SelectedMonthNo, 0);
            this.refreshSaleItemsByYear(this.SelectedYear, this.SelectedDisplayItems);
            this.refreshCategorySaleItemsByYear(this.SelectedYear, this.SelectedDisplayItems);
        } catch (Exception e) {
            System.err.println("searchSaleItemsDashboard:" + e.getMessage());
        }
    }

    public void searchExpenseItemsDashboard() {
        try {
            this.CurrentYear = new UtilityBean().getCurrentYear();
            this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
            if (this.SelectedYear == this.CurrentYear) {
                this.SelectedMonthNo = this.CurrentMonthNo;
            } else {
                this.SelectedMonthNo = 12;
            }
            this.SelectedMonthName = new UtilityBean().convertMonthNoToName(this.SelectedMonthNo, 0);
            this.refreshExpenseItemsByYear(this.SelectedYear, this.SelectedDisplayItems);
            this.refreshCategoryExpenseItemsByYear(this.SelectedYear, this.SelectedDisplayItems);
        } catch (Exception e) {
            System.err.println("searchExpenseItemsDashboard:" + e.getMessage());
        }
    }

    public void refreshSalesByYear(int aSelYear, int aSelDisplayYears) {
        YearsSalesByYear = "";
        AmountSalesByYear = "";
        this.YearsList = new ArrayList<>();
        String sql = "";
        sql = "SELECT y,sum(amount) as amount FROM view_fact_sales where y<=" + aSelYear + " and y>" + (aSelYear - aSelDisplayYears) + " group by y order by y ASC";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                if (YearsSalesByYear.length() == 0) {
                    YearsSalesByYear = "\"" + rs.getString("y") + "\"";
                } else {
                    YearsSalesByYear = YearsSalesByYear + ",\"" + rs.getString("y") + "\"";
                }
                this.YearsList.add(rs.getInt("y"));
                if (AmountSalesByYear.length() == 0) {
                    AmountSalesByYear = "" + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                } else {
                    AmountSalesByYear = AmountSalesByYear + "," + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void refreshStockByYear(int aSelYear, int aSelDisplayYears, String aStock_type) {
        DayStockByDay = "";
        AmountStockByDay = "";
        String StockSql = "";
        if (aStock_type.length() > 0) {
            StockSql = " and stock_type='" + aStock_type + "'";
        }
        String sql = "";
        sql = "SELECT "
                + "	c.y,c.m,c.d,c.snapshot_no,"
                + "	s.currency_code,s.cp_value  "
                + "FROM "
                + "("
                + "	select year(cdc_date) as y,month(cdc_date) as m,day(cdc_date) as d,max(snapshot_no) as snapshot_no from cdc_general "
                + "	where cdc_function='STOCK' and is_passed=1 and year(cdc_date)=" + aSelYear
                + "	group by year(cdc_date),month(cdc_date),day(cdc_date) "
                + "	order by year(cdc_date),month(cdc_date),day(cdc_date) "
                + ") AS c "
                + "INNER JOIN "
                + "("
                + "	select snapshot_no,currency_code,sum(cp_value) as cp_value from view_snapshot_stock_value where year(snapshot_date)=" + aSelYear + StockSql + " group by snapshot_no,currency_code"
                + ") AS s ON c.snapshot_no=s.snapshot_no";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                if (DayStockByDay.length() == 0) {
                    DayStockByDay = "\"" + rs.getString("d") + "/" + rs.getString("m") + "\"";
                } else {
                    DayStockByDay = DayStockByDay + ",\"" + rs.getString("d") + "/" + rs.getString("m") + "\"";
                }
                //this.YearsList.add(rs.getInt("y"));
                if (AmountStockByDay.length() == 0) {
                    AmountStockByDay = "" + new UtilityBean().formatNumber("###", rs.getDouble("cp_value"));
                } else {
                    AmountStockByDay = AmountStockByDay + "," + new UtilityBean().formatNumber("###", rs.getDouble("cp_value"));
                }
            }
        } catch (Exception e) {
            System.err.println("refreshStockByYear:" + e.getMessage());
        }
    }

    public void refreshExpensesByYear(int aSelYear, int aSelDisplayYears) {
        YearsExpensesByYear = "";
        AmountExpensesByYear = "";
        AmountSalesByYear = "";
        this.YearsList = new ArrayList<>();
        String sql = "";
        sql = "SELECT y,sum(amount) as amount FROM view_fact_expenses where y<=" + aSelYear + " and y>" + (aSelYear - aSelDisplayYears) + " group by y order by y ASC";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                if (YearsExpensesByYear.length() == 0) {
                    YearsExpensesByYear = "\"" + rs.getString("y") + "\"";
                } else {
                    YearsExpensesByYear = YearsExpensesByYear + ",\"" + rs.getString("y") + "\"";
                }
                this.YearsList.add(rs.getInt("y"));
                //Expenses
                if (AmountExpensesByYear.length() == 0) {
                    AmountExpensesByYear = "" + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                } else {
                    AmountExpensesByYear = AmountExpensesByYear + "," + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                }
                //Sales for that Year
                double SalesForYear = this.getSalesForYear(rs.getInt("y"));
                if (AmountSalesByYear.length() == 0) {
                    AmountSalesByYear = "" + new UtilityBean().formatNumber("###", SalesForYear);
                } else {
                    AmountSalesByYear = AmountSalesByYear + "," + new UtilityBean().formatNumber("###", SalesForYear);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public double getSalesForYear(int aSelYear) {
        double SalesForYear = 0;
        String sql = "";
        sql = "SELECT sum(amount) as amount FROM view_fact_sales_no_items where y=" + aSelYear;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                SalesForYear = rs.getDouble("amount");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return SalesForYear;
    }

    public void refreshSaleItemsByYear(int aSelYear, int aSelDisplayItems) {
        ItemsTopSoldItemsByYear = "";
        AmountTopSoldItemsByYear = "";
        QtyTopSoldItemsByYear = "";
        String sql = "";
        sql = "SELECT item_id,sum(amount) as amount,sum(item_qty) as item_qty FROM view_fact_sales_items where y=" + aSelYear + " group by item_id order by sum(amount) DESC LIMIT " + aSelDisplayItems;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            ItemBean ib = new ItemBean();
            while (rs.next()) {
                String ItemDesc = "";
                try {
                    ItemDesc = ib.getItem(rs.getLong("item_id")).getDescription();
                } catch (Exception e) {
                }
                if (ItemsTopSoldItemsByYear.length() == 0) {
                    ItemsTopSoldItemsByYear = "\"" + ItemDesc + "\"";
                } else {
                    ItemsTopSoldItemsByYear = ItemsTopSoldItemsByYear + ",\"" + ItemDesc + "\"";
                }
                if (AmountTopSoldItemsByYear.length() == 0) {
                    AmountTopSoldItemsByYear = "" + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                } else {
                    AmountTopSoldItemsByYear = AmountTopSoldItemsByYear + "," + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                }
                if (QtyTopSoldItemsByYear.length() == 0) {
                    QtyTopSoldItemsByYear = "" + new UtilityBean().formatNumber("###", rs.getDouble("item_qty"));
                } else {
                    QtyTopSoldItemsByYear = QtyTopSoldItemsByYear + "," + new UtilityBean().formatNumber("###", rs.getDouble("item_qty"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void refreshExpenseItemsByYear(int aSelYear, int aSelDisplayItems) {
        ItemsTopExpenseItemsByYear = "";
        AmountTopExpenseItemsByYear = "";
        String sql = "";
        sql = "SELECT item_id,sum(amount) as amount FROM view_fact_expenses_items where y=" + aSelYear + " group by item_id order by sum(amount) DESC LIMIT " + aSelDisplayItems;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            ItemBean ib = new ItemBean();
            while (rs.next()) {
                String ItemDesc = "";
                try {
                    ItemDesc = ib.getItem(rs.getLong("item_id")).getDescription();
                } catch (Exception e) {
                }
                if (ItemsTopExpenseItemsByYear.length() == 0) {
                    ItemsTopExpenseItemsByYear = "\"" + ItemDesc + "\"";
                } else {
                    ItemsTopExpenseItemsByYear = ItemsTopExpenseItemsByYear + ",\"" + ItemDesc + "\"";
                }
                if (AmountTopExpenseItemsByYear.length() == 0) {
                    AmountTopExpenseItemsByYear = "" + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                } else {
                    AmountTopExpenseItemsByYear = AmountTopExpenseItemsByYear + "," + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void refreshCategorySaleItemsByYear(int aSelYear, int aSelDisplayItems) {
        CatItemsTopSoldItemsByYear = "";
        CatAmountTopSoldItemsByYear = "";
        CatQtyTopSoldItemsByYear = "";
        String sql = "";
        sql = "SELECT i.category_id,sum(v.amount) as amount,sum(v.item_qty) as item_qty FROM view_fact_sales_items v "
                + "inner join item i on v.item_id=i.item_id "
                + "where v.y=" + aSelYear + " group by i.category_id order by sum(v.amount) DESC LIMIT " + aSelDisplayItems;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            CategoryBean cb = new CategoryBean();
            while (rs.next()) {
                String CatName = "";
                try {
                    CatName = cb.getCategory(rs.getInt("category_id")).getCategoryName();
                } catch (Exception e) {
                }
                if (CatItemsTopSoldItemsByYear.length() == 0) {
                    CatItemsTopSoldItemsByYear = "\"" + CatName + "\"";
                } else {
                    CatItemsTopSoldItemsByYear = CatItemsTopSoldItemsByYear + ",\"" + CatName + "\"";
                }
                if (CatAmountTopSoldItemsByYear.length() == 0) {
                    CatAmountTopSoldItemsByYear = "" + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                } else {
                    CatAmountTopSoldItemsByYear = CatAmountTopSoldItemsByYear + "," + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                }
                if (CatQtyTopSoldItemsByYear.length() == 0) {
                    CatQtyTopSoldItemsByYear = "" + new UtilityBean().formatNumber("###", rs.getDouble("item_qty"));
                } else {
                    CatQtyTopSoldItemsByYear = CatQtyTopSoldItemsByYear + "," + new UtilityBean().formatNumber("###", rs.getDouble("item_qty"));
                }
            }
        } catch (Exception e) {
            System.err.println("refreshCategorySaleItemsByYear:" + e.getMessage());
        }
    }

    public void refreshCategoryExpenseItemsByYear(int aSelYear, int aSelDisplayItems) {
        CatItemsTopExpenseItemsByYear = "";
        CatAmountTopExpenseItemsByYear = "";
        String sql = "";
        sql = "SELECT i.category_id,sum(v.amount) as amount FROM view_fact_expenses_items v "
                + "inner join item i on v.item_id=i.item_id "
                + "where v.y=" + aSelYear + " group by i.category_id order by sum(v.amount) DESC LIMIT " + aSelDisplayItems;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            CategoryBean cb = new CategoryBean();
            while (rs.next()) {
                String CatName = "";
                try {
                    CatName = cb.getCategory(rs.getInt("category_id")).getCategoryName();
                } catch (Exception e) {
                }
                if (CatItemsTopExpenseItemsByYear.length() == 0) {
                    CatItemsTopExpenseItemsByYear = "\"" + CatName + "\"";
                } else {
                    CatItemsTopExpenseItemsByYear = CatItemsTopExpenseItemsByYear + ",\"" + CatName + "\"";
                }
                if (CatAmountTopExpenseItemsByYear.length() == 0) {
                    CatAmountTopExpenseItemsByYear = "" + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                } else {
                    CatAmountTopExpenseItemsByYear = CatAmountTopExpenseItemsByYear + "," + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                }
            }
        } catch (Exception e) {
            System.err.println("refreshCategoryExpenseItemsByYear:" + e.getMessage());
        }
    }

    public void refreshSalesByYearClick(int aYear) {
        this.ClickedYear = aYear;
        this.refreshSalesByMonth(aYear);
        this.refreshAvgSalesByMonth(aYear);
        this.CurrentYear = new UtilityBean().getCurrentYear();
        this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
        if (aYear == this.CurrentYear) {
            this.ClickedMonthNo = this.CurrentMonthNo;
        } else {
            this.ClickedMonthNo = 12;
        }
        this.ClickedMonthName = new UtilityBean().convertMonthNoToName(this.ClickedMonthNo, 0);
        this.refreshSalesByDay(aYear, this.ClickedMonthNo, "");
    }

    public void refreshExpensesByYearClick(int aYear) {
        this.ClickedYear = aYear;
        this.refreshExpensesByMonth(aYear);
        this.CurrentYear = new UtilityBean().getCurrentYear();
        this.CurrentMonthNo = new UtilityBean().getCurrentMonth();
        if (aYear == this.CurrentYear) {
            this.ClickedMonthNo = this.CurrentMonthNo;
        } else {
            this.ClickedMonthNo = 12;
        }
        this.ClickedMonthName = new UtilityBean().convertMonthNoToName(this.ClickedMonthNo, 0);
        this.refreshExpensesByDay(aYear, this.ClickedMonthNo, "");
    }

    public void refreshSalesByMonthClick(int aMonthNo) {
        this.ClickedMonthNo = aMonthNo;
        this.ClickedMonthName = new UtilityBean().convertMonthNoToName(this.ClickedMonthNo, 0);
        this.refreshSalesByDay(this.ClickedYear, aMonthNo, "");
    }

    public void refreshExpensesByMonthClick(int aMonthNo) {
        this.ClickedMonthNo = aMonthNo;
        this.ClickedMonthName = new UtilityBean().convertMonthNoToName(this.ClickedMonthNo, 0);
        this.refreshExpensesByDay(this.ClickedYear, aMonthNo, "");
    }

    public void refreshSalesByMonth(int aYear) {
        MonthSalesByMonth = "";
        AmountSalesByMonth = "";
        this.MonthNoList = new ArrayList<>();
        String sql = "";
        sql = "SELECT m,sum(amount) as amount FROM view_fact_sales where y=" + aYear + " group by m order by m ASC";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            UtilityBean ub = new UtilityBean();
            while (rs.next()) {
                String mname = ub.convertMonthNoToName(rs.getInt("m"), 1);
                this.MonthNoList.add(rs.getInt("m"));
                if (MonthSalesByMonth.length() == 0) {
                    MonthSalesByMonth = "\"" + mname + "\"";
                } else {
                    MonthSalesByMonth = MonthSalesByMonth + ",\"" + mname + "\"";
                }
                if (AmountSalesByMonth.length() == 0) {
                    AmountSalesByMonth = "" + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                } else {
                    AmountSalesByMonth = AmountSalesByMonth + "," + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void refreshExpensesByMonth(int aYear) {
        MonthExpensesByMonth = "";
        AmountExpensesByMonth = "";
        this.MonthNoList = new ArrayList<>();
        String sql = "";
        sql = "SELECT m,sum(amount) as amount FROM view_fact_expenses where y=" + aYear + " group by m order by m ASC";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            UtilityBean ub = new UtilityBean();
            while (rs.next()) {
                String mname = ub.convertMonthNoToName(rs.getInt("m"), 1);
                this.MonthNoList.add(rs.getInt("m"));
                if (MonthExpensesByMonth.length() == 0) {
                    MonthExpensesByMonth = "\"" + mname + "\"";
                } else {
                    MonthExpensesByMonth = MonthExpensesByMonth + ",\"" + mname + "\"";
                }
                if (AmountExpensesByMonth.length() == 0) {
                    AmountExpensesByMonth = "" + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                } else {
                    AmountExpensesByMonth = AmountExpensesByMonth + "," + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void refreshSalesByDay(int aYear, int aMonthNo, String aMonthName) {
        int monthno = 0;
        UtilityBean ub = new UtilityBean();
        if (aMonthNo > 0) {
            monthno = aMonthNo;
        } else if (aMonthName.length() > 0) {
            monthno = ub.convertMonthNameToNo(aMonthName);
        }
        DaySalesByDay = "";
        AmountSalesByDay = "";
        String sql = "";
        sql = "SELECT d,sum(amount) as amount FROM view_fact_sales where y=" + aYear + " and m=" + monthno + " group by d order by d ASC";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                if (DaySalesByDay.length() == 0) {
                    DaySalesByDay = "\"" + rs.getInt("d") + "\"";
                } else {
                    DaySalesByDay = DaySalesByDay + ",\"" + rs.getInt("d") + "\"";
                }
                if (AmountSalesByDay.length() == 0) {
                    AmountSalesByDay = "" + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                } else {
                    AmountSalesByDay = AmountSalesByDay + "," + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void refreshExpensesByDay(int aYear, int aMonthNo, String aMonthName) {
        int monthno = 0;
        UtilityBean ub = new UtilityBean();
        if (aMonthNo > 0) {
            monthno = aMonthNo;
        } else if (aMonthName.length() > 0) {
            monthno = ub.convertMonthNameToNo(aMonthName);
        }
        DayExpensesByDay = "";
        AmountExpensesByDay = "";
        String sql = "";
        sql = "SELECT d,sum(amount) as amount FROM view_fact_expenses where y=" + aYear + " and m=" + monthno + " group by d order by d ASC";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                if (DayExpensesByDay.length() == 0) {
                    DayExpensesByDay = "\"" + rs.getInt("d") + "\"";
                } else {
                    DayExpensesByDay = DayExpensesByDay + ",\"" + rs.getInt("d") + "\"";
                }
                if (AmountExpensesByDay.length() == 0) {
                    AmountExpensesByDay = "" + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                } else {
                    AmountExpensesByDay = AmountExpensesByDay + "," + new UtilityBean().formatNumber("###", rs.getDouble("amount"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void refreshAvgSalesByMonth(int aYear) {
        AvgMonthSalesByMonth = "";
        AvgAmountSalesByMonth = "";
        AvgMonthQtyByMonth = "";
        String sql = "";
        sql = "SELECT m,sum(amount) as amount,count(transaction_id) as n,AVG(c) as c FROM view_fact_sales where y=" + aYear + " group by m order by m ASC";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            UtilityBean ub = new UtilityBean();
            while (rs.next()) {
                String mname = ub.convertMonthNoToName(rs.getInt("m"), 1);

                if (AvgMonthSalesByMonth.length() == 0) {
                    AvgMonthSalesByMonth = "\"" + mname + "\"";
                } else {
                    AvgMonthSalesByMonth = AvgMonthSalesByMonth + ",\"" + mname + "\"";
                }
                if (AvgAmountSalesByMonth.length() == 0) {
                    AvgAmountSalesByMonth = "" + new UtilityBean().formatNumber("###", 1.0 * rs.getDouble("amount") / rs.getInt("n"));
                } else {
                    AvgAmountSalesByMonth = AvgAmountSalesByMonth + "," + new UtilityBean().formatNumber("###", 1.0 * rs.getDouble("amount") / rs.getInt("n"));
                }
                //AvgMonthQtyByMonth
                if (AvgMonthQtyByMonth.length() == 0) {
                    AvgMonthQtyByMonth = "" + new UtilityBean().formatNumber("###", 1.0 * rs.getDouble("c"));
                } else {
                    AvgMonthQtyByMonth = AvgMonthQtyByMonth + "," + new UtilityBean().formatNumber("###", 1.0 * rs.getDouble("c"));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void refreshDayCloseSalesList(String aBtnFrmToDate) {
        ResultSet rs;
        ResultSet rs2;
        double totalsales = 0;
        double totaldisc = 0;
        this.DayCloseSalesCash = new ArrayList<>();
        this.DayCloseSalesCredit = new ArrayList<>();
        this.DayCloseSalesStore = new ArrayList<>();
        String sql = "SELECT "
                + "t.currency_code,sum(t.grand_total) as grand_total,sum(t.cash_discount) as cash_discount,"
                + "sum(t.grand_total*t.xrate) as grand_total_loc,sum(t.cash_discount*t.xrate) as cash_discount_loc,"
                + "SUM(IF(t.amount_tendered=0,t.grand_total,0)) as credit_sale,SUM(IF(t.amount_tendered>0,t.grand_total,0)) as cash_sale "
                + "FROM transaction t WHERE t.transaction_type_id IN(2,65,68) "
                + "AND t.transaction_date " + aBtnFrmToDate + " "
                + "GROUP BY t.currency_code";
        String sql2 = "SELECT "
                + "t.store_id,t.currency_code,sum(t.grand_total) as grand_total "
                + "FROM transaction t WHERE t.transaction_type_id IN(2,65,68) "
                + "AND t.transaction_date " + aBtnFrmToDate + " "
                + "GROUP BY t.store_id,t.currency_code "
                + "ORDER BY t.store_id,t.currency_code ";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Trans cashsale;
            Trans creditsale;
            long i = 0;
            while (rs.next()) {
                i = i + 1;
                //cash sale
                cashsale = new Trans();
                cashsale.setTransactionId(i);
                try {
                    cashsale.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    cashsale.setCurrencyCode("");
                }
                try {
                    cashsale.setGrandTotal(rs.getDouble("cash_sale"));
                } catch (NullPointerException npe) {
                    cashsale.setGrandTotal(0);
                }
                if (cashsale.getGrandTotal() > 0) {
                    this.DayCloseSalesCash.add(cashsale);
                }
                //credit sale
                creditsale = new Trans();
                creditsale.setTransactionId(i);
                try {
                    creditsale.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    creditsale.setCurrencyCode("");
                }
                try {
                    creditsale.setGrandTotal(rs.getDouble("credit_sale"));
                } catch (NullPointerException npe) {
                    creditsale.setGrandTotal(0);
                }
                if (creditsale.getGrandTotal() > 0) {
                    this.DayCloseSalesCredit.add(creditsale);
                }
                //total sales
                try {
                    totalsales = totalsales + rs.getDouble("grand_total_loc");
                } catch (NullPointerException npe) {
                    //do nothing
                }
                try {
                    totaldisc = totaldisc + rs.getDouble("cash_discount_loc");
                } catch (NullPointerException npe) {
                    //do nothing
                }
            }
            this.TotalSales = totalsales;
            this.TotalCashDiscount = totaldisc;
        } catch (Exception e) {
            System.err.println("refreshDayCloseSalesList:" + e.getMessage());
        }
        //for store
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql2);) {
            rs2 = ps.executeQuery();
            Trans storesale;
            long i = 0;
            while (rs2.next()) {
                i = i + 1;
                storesale = new Trans();
                storesale.setTransactionId(i);
                try {
                    storesale.setCurrencyCode(rs2.getString("currency_code"));
                } catch (NullPointerException npe) {
                    storesale.setCurrencyCode("");
                }
                try {
                    storesale.setGrandTotal(rs2.getDouble("grand_total"));
                } catch (NullPointerException npe) {
                    storesale.setGrandTotal(0);
                }
                try {
                    storesale.setStoreId(rs2.getInt("store_id"));
                } catch (NullPointerException npe) {
                    storesale.setStoreId(0);
                }
                try {
                    storesale.setStoreName(new StoreBean().getStore(storesale.getStoreId()).getStoreName());
                } catch (NullPointerException npe) {
                    storesale.setStoreName("");
                }
                if (storesale.getGrandTotal() > 0) {
                    this.DayCloseSalesStore.add(storesale);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseSalesList:" + e.getMessage());
        }
    }

    public void refreshDayClosePurchaseList(String aBtnFrmToDate) {
        ResultSet rs;
        ResultSet rs2;
        ResultSet rs3;
        ResultSet rs4;
        double totalpurchases = 0;
        this.DayClosePurchasesCash = new ArrayList<>();
        this.DayClosePurchasesCredit = new ArrayList<>();
        this.DayClosePurchasesType = new ArrayList<>();
        this.DayClosePurchasesStore = new ArrayList<>();
        String sql = "SELECT "
                + "sum(t.grand_total*t.xrate) as grand_total "
                + "FROM transaction t "
                + "WHERE t.transaction_type_id IN(1,19) "
                + "AND t.transaction_date " + aBtnFrmToDate;
        String sql2 = "SELECT "
                + "t.currency_code,"
                + "SUM(IF(t.amount_tendered=0,t.grand_total,0)) as credit_purchase,SUM(IF(t.amount_tendered>0,t.grand_total,0)) as cash_purchase "
                + "FROM transaction t WHERE t.transaction_type_id IN(1,19) "
                + "AND t.transaction_date " + aBtnFrmToDate + " "
                + "GROUP BY t.currency_code";
        String sql3 = "SELECT "
                + "tr.transaction_reason_name,t.currency_code,sum(t.grand_total) as grand_total "
                + "FROM transaction t inner join transaction_reason tr on t.transaction_reason_id=tr.transaction_reason_id "
                + "WHERE t.transaction_type_id IN(1,19) "
                + "AND t.transaction_date " + aBtnFrmToDate + " "
                + "GROUP BY tr.transaction_reason_name,t.currency_code "
                + "ORDER BY tr.transaction_reason_name,t.currency_code";
        String sql4 = "SELECT "
                + "st.store_name,t.currency_code,sum(t.grand_total) as grand_total "
                + "FROM transaction t inner join store st on t.store_id=st.store_id "
                + "WHERE t.transaction_type_id IN(1,19) "
                + "AND t.transaction_date " + aBtnFrmToDate + " "
                + "GROUP BY st.store_name,t.currency_code "
                + "ORDER BY st.store_name,t.currency_code ";
        //for total
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    totalpurchases = totalpurchases + rs.getDouble("grand_total");
                } catch (NullPointerException npe) {
                    //do nothing
                }
            }
            this.TotalPurchases = totalpurchases;
        } catch (Exception e) {
            System.err.println("refreshDayClosePurchaseList-Total:" + e.getMessage());
        }
        //credit and cash purchase
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql2);) {
            rs2 = ps.executeQuery();
            Trans cashpurchase;
            Trans creditpurchase;
            long i = 0;
            while (rs2.next()) {
                i = i + 1;
                //cash purchase
                cashpurchase = new Trans();
                cashpurchase.setTransactionId(i);
                try {
                    cashpurchase.setCurrencyCode(rs2.getString("currency_code"));
                } catch (NullPointerException npe) {
                    cashpurchase.setCurrencyCode("");
                }
                try {
                    cashpurchase.setGrandTotal(rs2.getDouble("cash_purchase"));
                } catch (NullPointerException npe) {
                    cashpurchase.setGrandTotal(0);
                }
                if (cashpurchase.getGrandTotal() > 0) {
                    this.DayClosePurchasesCash.add(cashpurchase);
                }
                //credit purchase
                creditpurchase = new Trans();
                creditpurchase.setTransactionId(i);
                try {
                    creditpurchase.setCurrencyCode(rs2.getString("currency_code"));
                } catch (NullPointerException npe) {
                    creditpurchase.setCurrencyCode("");
                }
                try {
                    creditpurchase.setGrandTotal(rs2.getDouble("credit_purchase"));
                } catch (NullPointerException npe) {
                    creditpurchase.setGrandTotal(0);
                }
                if (creditpurchase.getGrandTotal() > 0) {
                    this.DayClosePurchasesCredit.add(creditpurchase);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshDayClosePurchaseList-CashCredit:" + e.getMessage());
        }
        //for type
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql3);) {
            rs3 = ps.executeQuery();
            Trans typepurchase;
            long i = 0;
            while (rs3.next()) {
                i = i + 1;
                typepurchase = new Trans();
                typepurchase.setTransactionId(i);
                try {
                    typepurchase.setCurrencyCode(rs3.getString("currency_code"));
                } catch (NullPointerException npe) {
                    typepurchase.setCurrencyCode("");
                }
                try {
                    typepurchase.setGrandTotal(rs3.getDouble("grand_total"));
                } catch (NullPointerException npe) {
                    typepurchase.setGrandTotal(0);
                }
                try {
                    typepurchase.setTransactionReasonName(rs3.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    typepurchase.setTransactionReasonName("");
                }
                if (typepurchase.getGrandTotal() > 0) {
                    this.DayClosePurchasesType.add(typepurchase);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshDayClosePurchaseList-Type:" + e.getMessage());
        }
        //for store
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql4);) {
            rs4 = ps.executeQuery();
            Trans storepurchase;
            long i = 0;
            while (rs4.next()) {
                i = i + 1;
                storepurchase = new Trans();
                storepurchase.setTransactionId(i);
                try {
                    storepurchase.setCurrencyCode(rs4.getString("currency_code"));
                } catch (NullPointerException npe) {
                    storepurchase.setCurrencyCode("");
                }
                try {
                    storepurchase.setGrandTotal(rs4.getDouble("grand_total"));
                } catch (NullPointerException npe) {
                    storepurchase.setGrandTotal(0);
                }
                try {
                    storepurchase.setStoreName(rs4.getString("store_name"));
                } catch (NullPointerException npe) {
                    storepurchase.setStoreName("");
                }
                if (storepurchase.getGrandTotal() > 0) {
                    this.DayClosePurchasesStore.add(storepurchase);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshDayClosePurchaseList-Store:" + e.getMessage());
        }
    }

//    public void refreshDayClosePurchaseList(String aDatesString) {
//        ResultSet rs;
//        ResultSet rs2;
//        this.DayClosePurchaseList = new ArrayList<>();
//        this.DayClosePurchaseList2 = new ArrayList<>();
//        String sql = "SELECT "
//                + "tr.transaction_reason_name,t.currency_code,sum(t.grand_total) as grand_total "
//                + "FROM transaction t inner join transaction_reason tr on t.transaction_reason_id=tr.transaction_reason_id "
//                + "WHERE t.transaction_type_id IN(1,19) "
//                + "AND cast(t.transaction_date as date) IN(" + aDatesString + ") "
//                + "GROUP BY tr.transaction_reason_name,t.currency_code";
//        String sql2 = "SELECT "
//                + "tr.transaction_reason_name,t.currency_code,sum(t.grand_total) as grand_total "
//                + "FROM transaction t inner join transaction_reason tr on t.transaction_reason_id=tr.transaction_reason_id "
//                + "WHERE t.transaction_type_id IN(1,19) "
//                + "AND cast(t.add_date as date) IN(" + aDatesString + ") "
//                + "GROUP BY tr.transaction_reason_name,t.currency_code";
//        //for TransDate
//        try (
//                Connection conn = DBConnection.getMySQLConnection();
//                PreparedStatement ps = conn.prepareStatement(sql);) {
//            rs = ps.executeQuery();
//            Trans trans;
//            while (rs.next()) {
//                trans = new Trans();
//                try {
//                    trans.setCurrencyCode(rs.getString("currency_code"));
//                } catch (NullPointerException npe) {
//                    trans.setCurrencyCode("");
//                }
//                try {
//                    trans.setGrandTotal(rs.getDouble("grand_total"));
//                } catch (NullPointerException npe) {
//                    trans.setGrandTotal(0);
//                }
//                try {
//                    trans.setTransactionReasonName(rs.getString("transaction_reason_name"));
//                } catch (NullPointerException npe) {
//                    trans.setTransactionReasonName("");
//                }
//                this.DayClosePurchaseList.add(trans);
//            }
//        } catch (Exception e) {
//            System.err.println("refreshDayClosePurchaseList:" + e.getMessage());
//        }
//
//        //for AddDate
//        try (
//                Connection conn = DBConnection.getMySQLConnection();
//                PreparedStatement ps = conn.prepareStatement(sql2);) {
//            rs2 = ps.executeQuery();
//            Trans trans2;
//            while (rs2.next()) {
//                trans2 = new Trans();
//                try {
//                    trans2.setCurrencyCode(rs2.getString("currency_code"));
//                } catch (NullPointerException npe) {
//                    trans2.setCurrencyCode("");
//                }
//                try {
//                    trans2.setGrandTotal(rs2.getDouble("grand_total"));
//                } catch (NullPointerException npe) {
//                    trans2.setGrandTotal(0);
//                }
//                try {
//                    trans2.setTransactionReasonName(rs2.getString("transaction_reason_name"));
//                } catch (NullPointerException npe) {
//                    trans2.setTransactionReasonName("");
//                }
//                this.DayClosePurchaseList2.add(trans2);
//            }
//        } catch (Exception e) {
//            System.err.println("refreshDayClosePurchaseList2:" + e.getMessage());
//        }
//    }
    public void refreshDayCloseCashReceiptList(String aBtnFrmToDate) {
        ResultSet rs;
        ResultSet rs2;
        ResultSet rs3;
        double totalreceipt = 0;
        this.DayCloseCashReceiptType = new ArrayList<>();
        this.DayCloseCashReceiptAccount = new ArrayList<>();
        String sql = "SELECT "
                + "sum(p.paid_amount*p.xrate) as paid_amount FROM pay p "
                + "WHERE p.pay_type_id=14 AND p.pay_method_id!=6 "
                + "AND p.pay_date " + aBtnFrmToDate;
        String sql2 = "SELECT "
                + "tr.transaction_reason_name,p.currency_code,sum(p.paid_amount) as paid_amount "
                + "FROM pay p inner join transaction_reason tr on p.pay_reason_id=tr.transaction_reason_id "
                + "WHERE p.pay_type_id=14 AND p.pay_method_id!=6 "
                + "AND p.pay_date " + aBtnFrmToDate + " "
                + "GROUP BY tr.transaction_reason_name,p.currency_code "
                + "ORDER BY tr.transaction_reason_name,p.currency_code";
        String sql3 = "SELECT "
                + "ca.child_account_name,p.currency_code,sum(p.paid_amount) as paid_amount "
                + "FROM pay p inner join acc_child_account ca on p.acc_child_account_id=ca.acc_child_account_id "
                + "WHERE p.pay_type_id=14 AND p.pay_method_id!=6 "
                + "AND p.pay_date " + aBtnFrmToDate + " "
                + "GROUP BY ca.child_account_name,p.currency_code "
                + "ORDER BY ca.child_account_name,p.currency_code";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    totalreceipt = totalreceipt + rs.getDouble("paid_amount");
                } catch (NullPointerException npe) {
                    //do nothing
                }
            }
            this.TotalCashReceipt = totalreceipt;
        } catch (Exception e) {
            System.err.println("refreshDayCloseCashReceiptList:" + e.getMessage());
        }
        //Cash Receipt Type
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql2);) {
            rs2 = ps.executeQuery();
            Pay typereceipt;
            long i = 0;
            while (rs2.next()) {
                i = i + 1;
                typereceipt = new Pay();
                typereceipt.setPayId(i);
                try {
                    typereceipt.setCurrencyCode(rs2.getString("currency_code"));
                } catch (NullPointerException npe) {
                    typereceipt.setCurrencyCode("");
                }
                try {
                    typereceipt.setPayReasonName(rs2.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    typereceipt.setPayReasonName("");
                }
                try {
                    typereceipt.setPaidAmount(rs2.getDouble("paid_amount"));
                } catch (NullPointerException npe) {
                    typereceipt.setPaidAmount(0);
                }
                if (typereceipt.getPaidAmount() > 0) {
                    this.DayCloseCashReceiptType.add(typereceipt);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseCashReceiptList-Type:" + e.getMessage());
        }
        //Cash Receipt Account
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql3);) {
            rs3 = ps.executeQuery();
            Pay accreceipt;
            long i = 0;
            while (rs3.next()) {
                i = i + 1;
                accreceipt = new Pay();
                accreceipt.setPayId(i);
                try {
                    accreceipt.setCurrencyCode(rs3.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accreceipt.setCurrencyCode("");
                }
                try {
                    accreceipt.setChildAccountName(rs3.getString("child_account_name"));
                } catch (NullPointerException npe) {
                    accreceipt.setChildAccountName("");
                }
                try {
                    accreceipt.setPaidAmount(rs3.getDouble("paid_amount"));
                } catch (NullPointerException npe) {
                    accreceipt.setPaidAmount(0);
                }
                if (accreceipt.getPaidAmount() > 0) {
                    this.DayCloseCashReceiptAccount.add(accreceipt);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseCashReceiptList-Account:" + e.getMessage());
        }
    }

//    public void refreshDayCloseCashReceiptList(String aDatesString) {
//        ResultSet rs;
//        ResultSet rs2;
//        this.DayCloseCashReceiptList = new ArrayList<>();
//        this.DayCloseCashReceiptList2 = new ArrayList<>();
//        String sql = "SELECT "
//                + "tr.transaction_reason_name,p.currency_code,sum(p.paid_amount) as paid_amount "
//                + "FROM pay p inner join transaction_reason tr on p.pay_reason_id=tr.transaction_reason_id "
//                + "WHERE p.pay_type_id=14 AND p.pay_method_id!=6 "
//                + "AND cast(p.pay_date as date) IN(" + aDatesString + ") "
//                + "GROUP BY tr.transaction_reason_name,p.currency_code";
//        String sql2 = "SELECT "
//                + "tr.transaction_reason_name,p.currency_code,sum(p.paid_amount) as paid_amount "
//                + "FROM pay p inner join transaction_reason tr on p.pay_reason_id=tr.transaction_reason_id "
//                + "WHERE p.pay_type_id=14 AND p.pay_method_id!=6 "
//                + "AND cast(p.add_date as date) IN(" + aDatesString + ") "
//                + "GROUP BY tr.transaction_reason_name,p.currency_code";
//        //for PayDate
//        try (
//                Connection conn = DBConnection.getMySQLConnection();
//                PreparedStatement ps = conn.prepareStatement(sql);) {
//            rs = ps.executeQuery();
//            Pay pay;
//            while (rs.next()) {
//                pay = new Pay();
//                try {
//                    pay.setCurrencyCode(rs.getString("currency_code"));
//                } catch (NullPointerException npe) {
//                    pay.setCurrencyCode("");
//                }
//                try {
//                    pay.setPaidAmount(rs.getDouble("paid_amount"));
//                } catch (NullPointerException npe) {
//                    pay.setPaidAmount(0);
//                }
//                try {
//                    pay.setPayRefNo(rs.getString("transaction_reason_name"));
//                } catch (NullPointerException npe) {
//                    pay.setPayRefNo("");
//                }
//                this.DayCloseCashReceiptList.add(pay);
//            }
//        } catch (Exception e) {
//            System.err.println("refreshDayCloseCashReceiptList:" + e.getMessage());
//        }
//
//        //for AddDate
//        try (
//                Connection conn = DBConnection.getMySQLConnection();
//                PreparedStatement ps = conn.prepareStatement(sql2);) {
//            rs2 = ps.executeQuery();
//            Pay pay2;
//            while (rs2.next()) {
//                pay2 = new Pay();
//                try {
//                    pay2.setCurrencyCode(rs2.getString("currency_code"));
//                } catch (NullPointerException npe) {
//                    pay2.setCurrencyCode("");
//                }
//                try {
//                    pay2.setPaidAmount(rs2.getDouble("paid_amount"));
//                } catch (NullPointerException npe) {
//                    pay2.setPaidAmount(0);
//                }
//                try {
//                    pay2.setPayRefNo(rs2.getString("transaction_reason_name"));
//                } catch (NullPointerException npe) {
//                    pay2.setPayRefNo("");
//                }
//                this.DayCloseCashReceiptList2.add(pay2);
//            }
//        } catch (Exception e) {
//            System.err.println("refreshDayCloseCashReceiptList2:" + e.getMessage());
//        }
//    }
    public void refreshDayCloseStockMovementList(String aBtnFrmToDate) {
        ResultSet rs;
        ResultSet rs2;
        ResultSet rs3;
        ResultSet rs4;
        long totaladded = 0;
        long totalsubtracted = 0;
        this.DayCloseStockMovementAdded = new ArrayList<>();
        this.DayCloseStockMovementSubtracted = new ArrayList<>();
        String sql = "SELECT "
                + "count(distinct item_id) as items_added from stock_ledger "
                + "WHERE qty_added>0 "
                + "AND cast(add_date as date) " + aBtnFrmToDate;
        String sql2 = "SELECT "
                + "count(distinct item_id) as items_subtracted "
                + "from stock_ledger "
                + "WHERE qty_subtracted>0 "
                + "AND cast(add_date as date) " + aBtnFrmToDate;
        String sql3 = "SELECT "
                + "tt.transaction_type_name,"
                + "count(distinct sl.item_id) as items_added,"
                + "sum(sl.qty_added) as qty_added "
                + "from stock_ledger sl INNER JOIN transaction_type tt ON tt.transaction_type_id=sl.transaction_type_id "
                + "WHERE sl.qty_added>0 "
                + "AND cast(sl.add_date as date) " + aBtnFrmToDate + " "
                + "GROUP BY tt.transaction_type_name";
        String sql4 = "SELECT "
                + "tt.transaction_type_name,"
                + "count(distinct sl.item_id) as items_subtracted,"
                + "sum(sl.qty_subtracted) as qty_subtracted "
                + "from stock_ledger sl INNER JOIN transaction_type tt ON tt.transaction_type_id=sl.transaction_type_id "
                + "WHERE sl.qty_subtracted>0 "
                + "AND cast(sl.add_date as date) " + aBtnFrmToDate + " "
                + "GROUP BY tt.transaction_type_name";
        //added
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    totaladded = totaladded + rs.getLong("items_added");
                } catch (NullPointerException npe) {
                    //do nothing
                }
            }
            this.CountItemsAdded = totaladded;
        } catch (Exception e) {
            System.err.println("refreshDayCloseStockMovementList-Added:" + e.getMessage());
        }
        //subtracted
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql2);) {
            rs2 = ps.executeQuery();
            while (rs2.next()) {
                try {
                    totalsubtracted = totalsubtracted + rs2.getLong("items_subtracted");
                } catch (NullPointerException npe) {
                    //do nothing
                }
            }
            this.CountItemsSubtracted = totalsubtracted;
        } catch (Exception e) {
            System.err.println("refreshDayCloseStockMovementList-Subtracted:" + e.getMessage());
        }
        //added-type
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql3);) {
            rs3 = ps.executeQuery();
            Stock_ledger typeadded;
            long i = 0;
            while (rs3.next()) {
                i = i + 1;
                typeadded = new Stock_ledger();
                typeadded.setStock_ledger_id(i);
                try {
                    typeadded.setTransaction_type_name(rs3.getString("transaction_type_name"));
                } catch (NullPointerException npe) {
                    typeadded.setTransaction_type_name("");
                }
                try {
                    typeadded.setItem_id(rs3.getLong("items_added"));
                } catch (NullPointerException npe) {
                    typeadded.setItem_id(0);
                }
                try {
                    typeadded.setQty_added(rs3.getLong("qty_added"));
                } catch (NullPointerException npe) {
                    typeadded.setQty_added(0);
                }
                if (typeadded.getQty_added() > 0) {
                    this.DayCloseStockMovementAdded.add(typeadded);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseStockMovementList-Added-Type:" + e.getMessage());
        }
        //subtracted-type
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql4);) {
            rs4 = ps.executeQuery();
            Stock_ledger typesubtracted;
            long i = 0;
            while (rs4.next()) {
                i = i + 1;
                typesubtracted = new Stock_ledger();
                typesubtracted.setStock_ledger_id(i);
                try {
                    typesubtracted.setTransaction_type_name(rs4.getString("transaction_type_name"));
                } catch (NullPointerException npe) {
                    typesubtracted.setTransaction_type_name("");
                }
                try {
                    typesubtracted.setItem_id(rs4.getLong("items_subtracted"));
                } catch (NullPointerException npe) {
                    typesubtracted.setItem_id(0);
                }
                try {
                    typesubtracted.setQty_subtracted(rs4.getLong("qty_subtracted"));
                } catch (NullPointerException npe) {
                    typesubtracted.setQty_subtracted(0);
                }
                if (typesubtracted.getQty_subtracted() > 0) {
                    this.DayCloseStockMovementSubtracted.add(typesubtracted);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseStockMovementList-Subtracted-Type:" + e.getMessage());
        }
    }

    public void refreshDayCloseCashPaymentList(String aBtnFrmToDate) {
        ResultSet rs;
        ResultSet rs2;
        ResultSet rs3;
        double totalpayment = 0;
        this.DayCloseCashPaymentType = new ArrayList<>();
        this.DayCloseCashPaymentAccount = new ArrayList<>();
        String sql = "SELECT "
                + "sum(p.paid_amount*p.xrate) as paid_amount FROM pay p "
                + "WHERE p.pay_type_id=15 AND p.pay_method_id!=7 "
                + "AND p.pay_date " + aBtnFrmToDate;
        String sql2 = "SELECT "
                + "tr.transaction_reason_name,p.currency_code,sum(p.paid_amount) as paid_amount "
                + "FROM pay p inner join transaction_reason tr on p.pay_reason_id=tr.transaction_reason_id "
                + "WHERE p.pay_type_id=15 AND p.pay_method_id!=7 "
                + "AND p.pay_date " + aBtnFrmToDate + " "
                + "GROUP BY tr.transaction_reason_name,p.currency_code "
                + "ORDER BY tr.transaction_reason_name,p.currency_code";
        String sql3 = "SELECT "
                + "ca.child_account_name,p.currency_code,sum(p.paid_amount) as paid_amount "
                + "FROM pay p inner join acc_child_account ca on p.acc_child_account_id=ca.acc_child_account_id "
                + "WHERE p.pay_type_id=15 AND p.pay_method_id!=7 "
                + "AND p.pay_date " + aBtnFrmToDate + " "
                + "GROUP BY ca.child_account_name,p.currency_code "
                + "ORDER BY ca.child_account_name,p.currency_code";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    totalpayment = totalpayment + rs.getDouble("paid_amount");
                } catch (NullPointerException npe) {
                    //do nothing
                }
            }
            this.TotalCashPayment = totalpayment;
        } catch (Exception e) {
            System.err.println("refreshDayCloseCashPaymentList-Total:" + e.getMessage());
        }
        //Cash Payment Type
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql2);) {
            rs2 = ps.executeQuery();
            Pay typepayment;
            long i = 0;
            while (rs2.next()) {
                i = i + 1;
                typepayment = new Pay();
                typepayment.setPayId(i);
                try {
                    typepayment.setCurrencyCode(rs2.getString("currency_code"));
                } catch (NullPointerException npe) {
                    typepayment.setCurrencyCode("");
                }
                try {
                    typepayment.setPayReasonName(rs2.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    typepayment.setPayReasonName("");
                }
                try {
                    typepayment.setPaidAmount(rs2.getDouble("paid_amount"));
                } catch (NullPointerException npe) {
                    typepayment.setPaidAmount(0);
                }
                if (typepayment.getPaidAmount() > 0) {
                    this.DayCloseCashPaymentType.add(typepayment);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseCashPaymentList-Type:" + e.getMessage());
        }
        //Cash Payment Account
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql3);) {
            rs3 = ps.executeQuery();
            Pay accpayment;
            long i = 0;
            while (rs3.next()) {
                i = i + 1;
                accpayment = new Pay();
                accpayment.setPayId(i);
                try {
                    accpayment.setCurrencyCode(rs3.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accpayment.setCurrencyCode("");
                }
                try {
                    accpayment.setChildAccountName(rs3.getString("child_account_name"));
                } catch (NullPointerException npe) {
                    accpayment.setChildAccountName("");
                }
                try {
                    accpayment.setPaidAmount(rs3.getDouble("paid_amount"));
                } catch (NullPointerException npe) {
                    accpayment.setPaidAmount(0);
                }
                if (accpayment.getPaidAmount() > 0) {
                    this.DayCloseCashPaymentAccount.add(accpayment);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseCashPaymentList-Account:" + e.getMessage());
        }
    }

//    public void refreshDayCloseCashPaymentList(String aDatesString) {
//        ResultSet rs;
//        ResultSet rs2;
//        this.DayCloseCashPaymentList = new ArrayList<>();
//        this.DayCloseCashPaymentList2 = new ArrayList<>();
//        String sql = "SELECT "
//                + "tr.transaction_reason_name,p.currency_code,sum(p.paid_amount) as paid_amount "
//                + "FROM pay p inner join transaction_reason tr on p.pay_reason_id=tr.transaction_reason_id "
//                + "WHERE p.pay_type_id=15 AND p.pay_method_id!=7 "
//                + "AND cast(p.pay_date as date) IN(" + aDatesString + ") "
//                + "GROUP BY tr.transaction_reason_name,p.currency_code";
//        String sql2 = "SELECT "
//                + "tr.transaction_reason_name,p.currency_code,sum(p.paid_amount) as paid_amount "
//                + "FROM pay p inner join transaction_reason tr on p.pay_reason_id=tr.transaction_reason_id "
//                + "WHERE p.pay_type_id=15 AND p.pay_method_id!=7 "
//                + "AND cast(p.add_date as date) IN(" + aDatesString + ") "
//                + "GROUP BY tr.transaction_reason_name,p.currency_code";
//        //for PayDate
//        try (
//                Connection conn = DBConnection.getMySQLConnection();
//                PreparedStatement ps = conn.prepareStatement(sql);) {
//            rs = ps.executeQuery();
//            Pay pay;
//            while (rs.next()) {
//                pay = new Pay();
//                try {
//                    pay.setCurrencyCode(rs.getString("currency_code"));
//                } catch (NullPointerException npe) {
//                    pay.setCurrencyCode("");
//                }
//                try {
//                    pay.setPaidAmount(rs.getDouble("paid_amount"));
//                } catch (NullPointerException npe) {
//                    pay.setPaidAmount(0);
//                }
//                try {
//                    pay.setPayRefNo(rs.getString("transaction_reason_name"));
//                } catch (NullPointerException npe) {
//                    pay.setPayRefNo("");
//                }
//                this.DayCloseCashPaymentList.add(pay);
//            }
//        } catch (Exception e) {
//            System.err.println("refreshDayCloseCashPaymentList:" + e.getMessage());
//        }
//
//        //for AddDate
//        try (
//                Connection conn = DBConnection.getMySQLConnection();
//                PreparedStatement ps = conn.prepareStatement(sql2);) {
//            rs2 = ps.executeQuery();
//            Pay pay2;
//            while (rs2.next()) {
//                pay2 = new Pay();
//                try {
//                    pay2.setCurrencyCode(rs2.getString("currency_code"));
//                } catch (NullPointerException npe) {
//                    pay2.setCurrencyCode("");
//                }
//                try {
//                    pay2.setPaidAmount(rs2.getDouble("paid_amount"));
//                } catch (NullPointerException npe) {
//                    pay2.setPaidAmount(0);
//                }
//                try {
//                    pay2.setPayRefNo(rs2.getString("transaction_reason_name"));
//                } catch (NullPointerException npe) {
//                    pay2.setPayRefNo("");
//                }
//                this.DayCloseCashPaymentList2.add(pay2);
//            }
//        } catch (Exception e) {
//            System.err.println("refreshDayCloseCashPaymentList2:" + e.getMessage());
//        }
//    }
    public void searchDayCloseDashboard() {
        try {
            String BtnFrmToDate = "";
            if (null != this.FromDate && null != this.ToDate) {
                BtnFrmToDate = "BETWEEN '" + new java.sql.Date(this.FromDate.getTime()) + "' AND '" + new java.sql.Date(this.ToDate.getTime()) + "'";
                this.refreshDayCloseSalesList(BtnFrmToDate);
                this.refreshDayClosePurchaseList(BtnFrmToDate);
                this.refreshDayCloseCashReceiptList(BtnFrmToDate);
                this.refreshDayCloseCashPaymentList(BtnFrmToDate);
                this.refreshDayCloseStockMovementList(BtnFrmToDate);

            } else {

            }
        } catch (Exception e) {
            System.err.println("searchDayCloseDashboard:" + e.getMessage());
        }
    }

    public void initDayCloseDashboard() {
        try {
            this.setDateToToday();
            this.searchDayCloseDashboard();
        } catch (Exception e) {
            System.err.println("initDayCloseDashboard:" + e.getMessage());
        }
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

    public void setDateToYesturday() {
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

//    public void initDayCloseDashboard() {
//        try {
//            Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();
//            try {
//                this.MultipleDates.clear();
//            } catch (Exception e) {
//                this.MultipleDates = new ArrayList<>();
//            }
//            this.MultipleDates.add(CurrentServerDate);
//            this.searchDayCloseDashboard();
//        } catch (Exception e) {
//            System.err.println("initDayCloseDashboard:" + e.getMessage());
//        }
//    }
    @PostConstruct
    public void initDayCloseDashboardUI() {
        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();
        DashboardColumn column4 = new DefaultDashboardColumn();

        column1.addWidget("sales");
        column2.addWidget("receipts");
        column3.addWidget("purchases");
        column4.addWidget("payments");

        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);
        model.addColumn(column4);
    }

    /**
     * @return the YearsSalesByYear
     */
    public String getYearsSalesByYear() {
        return YearsSalesByYear;
    }

    /**
     * @param YearsSalesByYear the YearsSalesByYear to set
     */
    public void setYearsSalesByYear(String YearsSalesByYear) {
        this.YearsSalesByYear = YearsSalesByYear;
    }

    /**
     * @return the AmountSalesByYear
     */
    public String getAmountSalesByYear() {
        return AmountSalesByYear;
    }

    /**
     * @param AmountSalesByYear the AmountSalesByYear to set
     */
    public void setAmountSalesByYear(String AmountSalesByYear) {
        this.AmountSalesByYear = AmountSalesByYear;
    }

    /**
     * @return the MonthSalesByMonth
     */
    public String getMonthSalesByMonth() {
        return MonthSalesByMonth;
    }

    /**
     * @param MonthSalesByMonth the MonthSalesByMonth to set
     */
    public void setMonthSalesByMonth(String MonthSalesByMonth) {
        this.MonthSalesByMonth = MonthSalesByMonth;
    }

    /**
     * @return the AmountSalesByMonth
     */
    public String getAmountSalesByMonth() {
        return AmountSalesByMonth;
    }

    /**
     * @param AmountSalesByMonth the AmountSalesByMonth to set
     */
    public void setAmountSalesByMonth(String AmountSalesByMonth) {
        this.AmountSalesByMonth = AmountSalesByMonth;
    }

    /**
     * @return the DaySalesByDay
     */
    public String getDaySalesByDay() {
        return DaySalesByDay;
    }

    /**
     * @param DaySalesByDay the DaySalesByDay to set
     */
    public void setDaySalesByDay(String DaySalesByDay) {
        this.DaySalesByDay = DaySalesByDay;
    }

    /**
     * @return the AmountSalesByDay
     */
    public String getAmountSalesByDay() {
        return AmountSalesByDay;
    }

    /**
     * @param AmountSalesByDay the AmountSalesByDay to set
     */
    public void setAmountSalesByDay(String AmountSalesByDay) {
        this.AmountSalesByDay = AmountSalesByDay;
    }

    /**
     * @return the AvgMonthSalesByMonth
     */
    public String getAvgMonthSalesByMonth() {
        return AvgMonthSalesByMonth;
    }

    /**
     * @param AvgMonthSalesByMonth the AvgMonthSalesByMonth to set
     */
    public void setAvgMonthSalesByMonth(String AvgMonthSalesByMonth) {
        this.AvgMonthSalesByMonth = AvgMonthSalesByMonth;
    }

    /**
     * @return the AvgAmountSalesByMonth
     */
    public String getAvgAmountSalesByMonth() {
        return AvgAmountSalesByMonth;
    }

    /**
     * @param AvgAmountSalesByMonth the AvgAmountSalesByMonth to set
     */
    public void setAvgAmountSalesByMonth(String AvgAmountSalesByMonth) {
        this.AvgAmountSalesByMonth = AvgAmountSalesByMonth;
    }

    /**
     * @return the CurrentYear
     */
    public int getCurrentYear() {
        return CurrentYear;
    }

    /**
     * @param CurrentYear the CurrentYear to set
     */
    public void setCurrentYear(int CurrentYear) {
        this.CurrentYear = CurrentYear;
    }

    /**
     * @return the CurrentMonthNo
     */
    public int getCurrentMonthNo() {
        return CurrentMonthNo;
    }

    /**
     * @param CurrentMonthNo the CurrentMonthNo to set
     */
    public void setCurrentMonthNo(int CurrentMonthNo) {
        this.CurrentMonthNo = CurrentMonthNo;
    }

    /**
     * @return the SelectedYear
     */
    public int getSelectedYear() {
        return SelectedYear;
    }

    /**
     * @param SelectedYear the SelectedYear to set
     */
    public void setSelectedYear(int SelectedYear) {
        this.SelectedYear = SelectedYear;
    }

    /**
     * @return the SelectedMonthNo
     */
    public int getSelectedMonthNo() {
        return SelectedMonthNo;
    }

    /**
     * @param SelectedMonthNo the SelectedMonthNo to set
     */
    public void setSelectedMonthNo(int SelectedMonthNo) {
        this.SelectedMonthNo = SelectedMonthNo;
    }

    /**
     * @return the SelectedMonthName
     */
    public String getSelectedMonthName() {
        return SelectedMonthName;
    }

    /**
     * @param SelectedMonthName the SelectedMonthName to set
     */
    public void setSelectedMonthName(String SelectedMonthName) {
        this.SelectedMonthName = SelectedMonthName;
    }

    /**
     * @return the SelectedDisplayYears
     */
    public int getSelectedDisplayYears() {
        return SelectedDisplayYears;
    }

    /**
     * @param SelectedDisplayYears the SelectedDisplayYears to set
     */
    public void setSelectedDisplayYears(int SelectedDisplayYears) {
        this.SelectedDisplayYears = SelectedDisplayYears;
    }

    /**
     * @return the SelectedDisplayItems
     */
    public int getSelectedDisplayItems() {
        return SelectedDisplayItems;
    }

    /**
     * @param SelectedDisplayItems the SelectedDisplayItems to set
     */
    public void setSelectedDisplayItems(int SelectedDisplayItems) {
        this.SelectedDisplayItems = SelectedDisplayItems;
    }

    /**
     * @return the ItemsTopSoldItemsByYear
     */
    public String getItemsTopSoldItemsByYear() {
        return ItemsTopSoldItemsByYear;
    }

    /**
     * @param ItemsTopSoldItemsByYear the ItemsTopSoldItemsByYear to set
     */
    public void setItemsTopSoldItemsByYear(String ItemsTopSoldItemsByYear) {
        this.ItemsTopSoldItemsByYear = ItemsTopSoldItemsByYear;
    }

    /**
     * @return the AmountTopSoldItemsByYear
     */
    public String getAmountTopSoldItemsByYear() {
        return AmountTopSoldItemsByYear;
    }

    /**
     * @param AmountTopSoldItemsByYear the AmountTopSoldItemsByYear to set
     */
    public void setAmountTopSoldItemsByYear(String AmountTopSoldItemsByYear) {
        this.AmountTopSoldItemsByYear = AmountTopSoldItemsByYear;
    }

    /**
     * @return the QtyTopSoldItemsByYear
     */
    public String getQtyTopSoldItemsByYear() {
        return QtyTopSoldItemsByYear;
    }

    /**
     * @param QtyTopSoldItemsByYear the QtyTopSoldItemsByYear to set
     */
    public void setQtyTopSoldItemsByYear(String QtyTopSoldItemsByYear) {
        this.QtyTopSoldItemsByYear = QtyTopSoldItemsByYear;
    }

    /**
     * @return the CatItemsTopSoldItemsByYear
     */
    public String getCatItemsTopSoldItemsByYear() {
        return CatItemsTopSoldItemsByYear;
    }

    /**
     * @param CatItemsTopSoldItemsByYear the CatItemsTopSoldItemsByYear to set
     */
    public void setCatItemsTopSoldItemsByYear(String CatItemsTopSoldItemsByYear) {
        this.CatItemsTopSoldItemsByYear = CatItemsTopSoldItemsByYear;
    }

    /**
     * @return the CatAmountTopSoldItemsByYear
     */
    public String getCatAmountTopSoldItemsByYear() {
        return CatAmountTopSoldItemsByYear;
    }

    /**
     * @param CatAmountTopSoldItemsByYear the CatAmountTopSoldItemsByYear to set
     */
    public void setCatAmountTopSoldItemsByYear(String CatAmountTopSoldItemsByYear) {
        this.CatAmountTopSoldItemsByYear = CatAmountTopSoldItemsByYear;
    }

    /**
     * @return the CatQtyTopSoldItemsByYear
     */
    public String getCatQtyTopSoldItemsByYear() {
        return CatQtyTopSoldItemsByYear;
    }

    /**
     * @param CatQtyTopSoldItemsByYear the CatQtyTopSoldItemsByYear to set
     */
    public void setCatQtyTopSoldItemsByYear(String CatQtyTopSoldItemsByYear) {
        this.CatQtyTopSoldItemsByYear = CatQtyTopSoldItemsByYear;
    }

    /**
     * @return the YearsList
     */
    public List<Integer> getYearsList() {
        return YearsList;
    }

    /**
     * @param YearsList the YearsList to set
     */
    public void setYearsList(List<Integer> YearsList) {
        this.YearsList = YearsList;
    }

    /**
     * @return the ClickedYear
     */
    public int getClickedYear() {
        return ClickedYear;
    }

    /**
     * @param ClickedYear the ClickedYear to set
     */
    public void setClickedYear(int ClickedYear) {
        this.ClickedYear = ClickedYear;
    }

    /**
     * @return the ClickedMonthNo
     */
    public int getClickedMonthNo() {
        return ClickedMonthNo;
    }

    /**
     * @param ClickedMonthNo the ClickedMonthNo to set
     */
    public void setClickedMonthNo(int ClickedMonthNo) {
        this.ClickedMonthNo = ClickedMonthNo;
    }

    /**
     * @return the ClickedMonthName
     */
    public String getClickedMonthName() {
        return ClickedMonthName;
    }

    /**
     * @param ClickedMonthName the ClickedMonthName to set
     */
    public void setClickedMonthName(String ClickedMonthName) {
        this.ClickedMonthName = ClickedMonthName;
    }

    /**
     * @return the MonthNoList
     */
    public List<Integer> getMonthNoList() {
        return MonthNoList;
    }

    /**
     * @param MonthNoList the MonthNoList to set
     */
    public void setMonthNoList(List<Integer> MonthNoList) {
        this.MonthNoList = MonthNoList;
    }

    /**
     * @return the AvgMonthQtyByMonth
     */
    public String getAvgMonthQtyByMonth() {
        return AvgMonthQtyByMonth;
    }

    /**
     * @param AvgMonthQtyByMonth the AvgMonthQtyByMonth to set
     */
    public void setAvgMonthQtyByMonth(String AvgMonthQtyByMonth) {
        this.AvgMonthQtyByMonth = AvgMonthQtyByMonth;
    }

    /**
     * @return the YearsExpensesByYear
     */
    public String getYearsExpensesByYear() {
        return YearsExpensesByYear;
    }

    /**
     * @param YearsExpensesByYear the YearsExpensesByYear to set
     */
    public void setYearsExpensesByYear(String YearsExpensesByYear) {
        this.YearsExpensesByYear = YearsExpensesByYear;
    }

    /**
     * @return the AmountExpensesByYear
     */
    public String getAmountExpensesByYear() {
        return AmountExpensesByYear;
    }

    /**
     * @param AmountExpensesByYear the AmountExpensesByYear to set
     */
    public void setAmountExpensesByYear(String AmountExpensesByYear) {
        this.AmountExpensesByYear = AmountExpensesByYear;
    }

    /**
     * @return the MonthExpensesByMonth
     */
    public String getMonthExpensesByMonth() {
        return MonthExpensesByMonth;
    }

    /**
     * @param MonthExpensesByMonth the MonthExpensesByMonth to set
     */
    public void setMonthExpensesByMonth(String MonthExpensesByMonth) {
        this.MonthExpensesByMonth = MonthExpensesByMonth;
    }

    /**
     * @return the AmountExpensesByMonth
     */
    public String getAmountExpensesByMonth() {
        return AmountExpensesByMonth;
    }

    /**
     * @param AmountExpensesByMonth the AmountExpensesByMonth to set
     */
    public void setAmountExpensesByMonth(String AmountExpensesByMonth) {
        this.AmountExpensesByMonth = AmountExpensesByMonth;
    }

    /**
     * @return the DayExpensesByDay
     */
    public String getDayExpensesByDay() {
        return DayExpensesByDay;
    }

    /**
     * @param DayExpensesByDay the DayExpensesByDay to set
     */
    public void setDayExpensesByDay(String DayExpensesByDay) {
        this.DayExpensesByDay = DayExpensesByDay;
    }

    /**
     * @return the AmountExpensesByDay
     */
    public String getAmountExpensesByDay() {
        return AmountExpensesByDay;
    }

    /**
     * @param AmountExpensesByDay the AmountExpensesByDay to set
     */
    public void setAmountExpensesByDay(String AmountExpensesByDay) {
        this.AmountExpensesByDay = AmountExpensesByDay;
    }

    /**
     * @return the ItemsTopExpenseItemsByYear
     */
    public String getItemsTopExpenseItemsByYear() {
        return ItemsTopExpenseItemsByYear;
    }

    /**
     * @param ItemsTopExpenseItemsByYear the ItemsTopExpenseItemsByYear to set
     */
    public void setItemsTopExpenseItemsByYear(String ItemsTopExpenseItemsByYear) {
        this.ItemsTopExpenseItemsByYear = ItemsTopExpenseItemsByYear;
    }

    /**
     * @return the AmountTopExpenseItemsByYear
     */
    public String getAmountTopExpenseItemsByYear() {
        return AmountTopExpenseItemsByYear;
    }

    /**
     * @param AmountTopExpenseItemsByYear the AmountTopExpenseItemsByYear to set
     */
    public void setAmountTopExpenseItemsByYear(String AmountTopExpenseItemsByYear) {
        this.AmountTopExpenseItemsByYear = AmountTopExpenseItemsByYear;
    }

    /**
     * @return the CatItemsTopExpenseItemsByYear
     */
    public String getCatItemsTopExpenseItemsByYear() {
        return CatItemsTopExpenseItemsByYear;
    }

    /**
     * @param CatItemsTopExpenseItemsByYear the CatItemsTopExpenseItemsByYear to
     * set
     */
    public void setCatItemsTopExpenseItemsByYear(String CatItemsTopExpenseItemsByYear) {
        this.CatItemsTopExpenseItemsByYear = CatItemsTopExpenseItemsByYear;
    }

    /**
     * @return the CatAmountTopExpenseItemsByYear
     */
    public String getCatAmountTopExpenseItemsByYear() {
        return CatAmountTopExpenseItemsByYear;
    }

    /**
     * @param CatAmountTopExpenseItemsByYear the CatAmountTopExpenseItemsByYear
     * to set
     */
    public void setCatAmountTopExpenseItemsByYear(String CatAmountTopExpenseItemsByYear) {
        this.CatAmountTopExpenseItemsByYear = CatAmountTopExpenseItemsByYear;
    }

    /**
     * @return the DayCloseCashReceiptList
     */
    public List<Pay> getDayCloseCashReceiptList() {
        return DayCloseCashReceiptList;
    }

    /**
     * @param DayCloseCashReceiptList the DayCloseCashReceiptList to set
     */
    public void setDayCloseCashReceiptList(List<Pay> DayCloseCashReceiptList) {
        this.DayCloseCashReceiptList = DayCloseCashReceiptList;
    }

    /**
     * @return the DayClosePurchaseList
     */
    public List<Trans> getDayClosePurchaseList() {
        return DayClosePurchaseList;
    }

    /**
     * @param DayClosePurchaseList the DayClosePurchaseList to set
     */
    public void setDayClosePurchaseList(List<Trans> DayClosePurchaseList) {
        this.DayClosePurchaseList = DayClosePurchaseList;
    }

    /**
     * @return the DayCloseCashPaymentList
     */
    public List<Pay> getDayCloseCashPaymentList() {
        return DayCloseCashPaymentList;
    }

    /**
     * @param DayCloseCashPaymentList the DayCloseCashPaymentList to set
     */
    public void setDayCloseCashPaymentList(List<Pay> DayCloseCashPaymentList) {
        this.DayCloseCashPaymentList = DayCloseCashPaymentList;
    }

    /**
     * @return the MultipleDates
     */
    public List<Date> getMultipleDates() {
        return MultipleDates;
    }

    /**
     * @param MultipleDates the MultipleDates to set
     */
    public void setMultipleDates(List<Date> MultipleDates) {
        this.MultipleDates = MultipleDates;
    }

    /**
     * @return the model
     */
    public DashboardModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(DashboardModel model) {
        this.model = model;
    }

    /**
     * @return the DayCloseSalesList2
     */
    public List<Trans> getDayCloseSalesList2() {
        return DayCloseSalesList2;
    }

    /**
     * @param DayCloseSalesList2 the DayCloseSalesList2 to set
     */
    public void setDayCloseSalesList2(List<Trans> DayCloseSalesList2) {
        this.DayCloseSalesList2 = DayCloseSalesList2;
    }

    /**
     * @return the DayCloseCashReceiptList2
     */
    public List<Pay> getDayCloseCashReceiptList2() {
        return DayCloseCashReceiptList2;
    }

    /**
     * @param DayCloseCashReceiptList2 the DayCloseCashReceiptList2 to set
     */
    public void setDayCloseCashReceiptList2(List<Pay> DayCloseCashReceiptList2) {
        this.DayCloseCashReceiptList2 = DayCloseCashReceiptList2;
    }

    /**
     * @return the DayClosePurchaseList2
     */
    public List<Trans> getDayClosePurchaseList2() {
        return DayClosePurchaseList2;
    }

    /**
     * @param DayClosePurchaseList2 the DayClosePurchaseList2 to set
     */
    public void setDayClosePurchaseList2(List<Trans> DayClosePurchaseList2) {
        this.DayClosePurchaseList2 = DayClosePurchaseList2;
    }

    /**
     * @return the DayCloseCashPaymentList2
     */
    public List<Pay> getDayCloseCashPaymentList2() {
        return DayCloseCashPaymentList2;
    }

    /**
     * @param DayCloseCashPaymentList2 the DayCloseCashPaymentList2 to set
     */
    public void setDayCloseCashPaymentList2(List<Pay> DayCloseCashPaymentList2) {
        this.DayCloseCashPaymentList2 = DayCloseCashPaymentList2;
    }

    /**
     * @return the FromDate
     */
    public Date getFromDate() {
        return FromDate;
    }

    /**
     * @param FromDate the FromDate to set
     */
    public void setFromDate(Date FromDate) {
        this.FromDate = FromDate;
    }

    /**
     * @return the ToDate
     */
    public Date getToDate() {
        return ToDate;
    }

    /**
     * @param ToDate the ToDate to set
     */
    public void setToDate(Date ToDate) {
        this.ToDate = ToDate;
    }

    /**
     * @return the TotalSales
     */
    public double getTotalSales() {
        return TotalSales;
    }

    /**
     * @param TotalSales the TotalSales to set
     */
    public void setTotalSales(double TotalSales) {
        this.TotalSales = TotalSales;
    }

    /**
     * @return the TotalCashDiscount
     */
    public double getTotalCashDiscount() {
        return TotalCashDiscount;
    }

    /**
     * @param TotalCashDiscount the TotalCashDiscount to set
     */
    public void setTotalCashDiscount(double TotalCashDiscount) {
        this.TotalCashDiscount = TotalCashDiscount;
    }

    /**
     * @return the DayCloseSalesCash
     */
    public List<Trans> getDayCloseSalesCash() {
        return DayCloseSalesCash;
    }

    /**
     * @param DayCloseSalesCash the DayCloseSalesCash to set
     */
    public void setDayCloseSalesCash(List<Trans> DayCloseSalesCash) {
        this.DayCloseSalesCash = DayCloseSalesCash;
    }

    /**
     * @return the DayCloseSalesCredit
     */
    public List<Trans> getDayCloseSalesCredit() {
        return DayCloseSalesCredit;
    }

    /**
     * @param DayCloseSalesCredit the DayCloseSalesCredit to set
     */
    public void setDayCloseSalesCredit(List<Trans> DayCloseSalesCredit) {
        this.DayCloseSalesCredit = DayCloseSalesCredit;
    }

    /**
     * @return the DayCloseSalesStore
     */
    public List<Trans> getDayCloseSalesStore() {
        return DayCloseSalesStore;
    }

    /**
     * @param DayCloseSalesStore the DayCloseSalesStore to set
     */
    public void setDayCloseSalesStore(List<Trans> DayCloseSalesStore) {
        this.DayCloseSalesStore = DayCloseSalesStore;
    }

    /**
     * @return the TotalCashReceipt
     */
    public double getTotalCashReceipt() {
        return TotalCashReceipt;
    }

    /**
     * @param TotalCashReceipt the TotalCashReceipt to set
     */
    public void setTotalCashReceipt(double TotalCashReceipt) {
        this.TotalCashReceipt = TotalCashReceipt;
    }

    /**
     * @return the DayCloseCashReceiptType
     */
    public List<Pay> getDayCloseCashReceiptType() {
        return DayCloseCashReceiptType;
    }

    /**
     * @param DayCloseCashReceiptType the DayCloseCashReceiptType to set
     */
    public void setDayCloseCashReceiptType(List<Pay> DayCloseCashReceiptType) {
        this.DayCloseCashReceiptType = DayCloseCashReceiptType;
    }

    /**
     * @return the DayCloseCashReceiptAccount
     */
    public List<Pay> getDayCloseCashReceiptAccount() {
        return DayCloseCashReceiptAccount;
    }

    /**
     * @param DayCloseCashReceiptAccount the DayCloseCashReceiptAccount to set
     */
    public void setDayCloseCashReceiptAccount(List<Pay> DayCloseCashReceiptAccount) {
        this.DayCloseCashReceiptAccount = DayCloseCashReceiptAccount;
    }

    /**
     * @return the TotalPurchases
     */
    public double getTotalPurchases() {
        return TotalPurchases;
    }

    /**
     * @param TotalPurchases the TotalPurchases to set
     */
    public void setTotalPurchases(double TotalPurchases) {
        this.TotalPurchases = TotalPurchases;
    }

    /**
     * @return the DayClosePurchasesCash
     */
    public List<Trans> getDayClosePurchasesCash() {
        return DayClosePurchasesCash;
    }

    /**
     * @param DayClosePurchasesCash the DayClosePurchasesCash to set
     */
    public void setDayClosePurchasesCash(List<Trans> DayClosePurchasesCash) {
        this.DayClosePurchasesCash = DayClosePurchasesCash;
    }

    /**
     * @return the DayClosePurchasesCredit
     */
    public List<Trans> getDayClosePurchasesCredit() {
        return DayClosePurchasesCredit;
    }

    /**
     * @param DayClosePurchasesCredit the DayClosePurchasesCredit to set
     */
    public void setDayClosePurchasesCredit(List<Trans> DayClosePurchasesCredit) {
        this.DayClosePurchasesCredit = DayClosePurchasesCredit;
    }

    /**
     * @return the DayClosePurchasesType
     */
    public List<Trans> getDayClosePurchasesType() {
        return DayClosePurchasesType;
    }

    /**
     * @param DayClosePurchasesType the DayClosePurchasesType to set
     */
    public void setDayClosePurchasesType(List<Trans> DayClosePurchasesType) {
        this.DayClosePurchasesType = DayClosePurchasesType;
    }

    /**
     * @return the DayClosePurchasesStore
     */
    public List<Trans> getDayClosePurchasesStore() {
        return DayClosePurchasesStore;
    }

    /**
     * @param DayClosePurchasesStore the DayClosePurchasesStore to set
     */
    public void setDayClosePurchasesStore(List<Trans> DayClosePurchasesStore) {
        this.DayClosePurchasesStore = DayClosePurchasesStore;
    }

    /**
     * @return the TotalCashPayment
     */
    public double getTotalCashPayment() {
        return TotalCashPayment;
    }

    /**
     * @param TotalCashPayment the TotalCashPayment to set
     */
    public void setTotalCashPayment(double TotalCashPayment) {
        this.TotalCashPayment = TotalCashPayment;
    }

    /**
     * @return the DayCloseCashPaymentType
     */
    public List<Pay> getDayCloseCashPaymentType() {
        return DayCloseCashPaymentType;
    }

    /**
     * @param DayCloseCashPaymentType the DayCloseCashPaymentType to set
     */
    public void setDayCloseCashPaymentType(List<Pay> DayCloseCashPaymentType) {
        this.DayCloseCashPaymentType = DayCloseCashPaymentType;
    }

    /**
     * @return the DayCloseCashPaymentAccount
     */
    public List<Pay> getDayCloseCashPaymentAccount() {
        return DayCloseCashPaymentAccount;
    }

    /**
     * @param DayCloseCashPaymentAccount the DayCloseCashPaymentAccount to set
     */
    public void setDayCloseCashPaymentAccount(List<Pay> DayCloseCashPaymentAccount) {
        this.DayCloseCashPaymentAccount = DayCloseCashPaymentAccount;
    }

    /**
     * @return the CountItemsAdded
     */
    public long getCountItemsAdded() {
        return CountItemsAdded;
    }

    /**
     * @param CountItemsAdded the CountItemsAdded to set
     */
    public void setCountItemsAdded(long CountItemsAdded) {
        this.CountItemsAdded = CountItemsAdded;
    }

    /**
     * @return the CountItemsSubtracted
     */
    public long getCountItemsSubtracted() {
        return CountItemsSubtracted;
    }

    /**
     * @param CountItemsSubtracted the CountItemsSubtracted to set
     */
    public void setCountItemsSubtracted(long CountItemsSubtracted) {
        this.CountItemsSubtracted = CountItemsSubtracted;
    }

    /**
     * @return the DayCloseStockMovementAdded
     */
    public List<Stock_ledger> getDayCloseStockMovementAdded() {
        return DayCloseStockMovementAdded;
    }

    /**
     * @param DayCloseStockMovementAdded the DayCloseStockMovementAdded to set
     */
    public void setDayCloseStockMovementAdded(List<Stock_ledger> DayCloseStockMovementAdded) {
        this.DayCloseStockMovementAdded = DayCloseStockMovementAdded;
    }

    /**
     * @return the DayCloseStockMovementSubtracted
     */
    public List<Stock_ledger> getDayCloseStockMovementSubtracted() {
        return DayCloseStockMovementSubtracted;
    }

    /**
     * @param DayCloseStockMovementSubtracted the
     * DayCloseStockMovementSubtracted to set
     */
    public void setDayCloseStockMovementSubtracted(List<Stock_ledger> DayCloseStockMovementSubtracted) {
        this.DayCloseStockMovementSubtracted = DayCloseStockMovementSubtracted;
    }

    /**
     * @return the DayStockByDay
     */
    public String getDayStockByDay() {
        return DayStockByDay;
    }

    /**
     * @param DayStockByDay the DayStockByDay to set
     */
    public void setDayStockByDay(String DayStockByDay) {
        this.DayStockByDay = DayStockByDay;
    }

    /**
     * @return the AmountStockByDay
     */
    public String getAmountStockByDay() {
        return AmountStockByDay;
    }

    /**
     * @param AmountStockByDay the AmountStockByDay to set
     */
    public void setAmountStockByDay(String AmountStockByDay) {
        this.AmountStockByDay = AmountStockByDay;
    }

    /**
     * @return the stock_type
     */
    public String getStock_type() {
        return stock_type;
    }

    /**
     * @param stock_type the stock_type to set
     */
    public void setStock_type(String stock_type) {
        this.stock_type = stock_type;
    }

}
