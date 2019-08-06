package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.Pay;
import entities.Trans;
import utilities.UtilityBean;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    private List<Trans> DayCloseSalesList;
    private List<Pay> DayCloseCashReceiptList;
    private List<Trans> DayClosePurchaseList;
    private List<Pay> DayCloseCashPaymentList;
    private DashboardModel model;
    private List<Trans> DayCloseSalesList2;
    private List<Pay> DayCloseCashReceiptList2;
    private List<Trans> DayClosePurchaseList2;
    private List<Pay> DayCloseCashPaymentList2;

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

    public void refreshDayCloseSalesList(String aDatesString) {
        ResultSet rs;
        ResultSet rs2;
        this.DayCloseSalesList = new ArrayList<>();
        this.DayCloseSalesList2 = new ArrayList<>();
        String sql = "SELECT "
                + "tr.transaction_reason_name,t.currency_code,sum(t.grand_total) as grand_total,sum(t.cash_discount) as cash_discount "
                + "FROM transaction t inner join transaction_reason tr on t.transaction_reason_id=tr.transaction_reason_id "
                + "WHERE t.transaction_type_id IN(2,65,68) "
                + "AND cast(t.transaction_date as date) IN(" + aDatesString + ") "
                + "GROUP BY tr.transaction_reason_name,t.currency_code";
        String sql2 = "SELECT "
                + "tr.transaction_reason_name,t.currency_code,sum(t.grand_total) as grand_total,sum(t.cash_discount) as cash_discount "
                + "FROM transaction t inner join transaction_reason tr on t.transaction_reason_id=tr.transaction_reason_id "
                + "WHERE t.transaction_type_id IN(2,65,68) "
                + "AND cast(t.add_date as date) IN(" + aDatesString + ") "
                + "GROUP BY tr.transaction_reason_name,t.currency_code";
        //for Trans Date
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Trans trans;
            int i=0;
            while (rs.next()) {
                trans = new Trans();
                i=i+1;
                trans.setTransactionId(i);
                try {
                    trans.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    trans.setCurrencyCode("");
                }
                try {
                    trans.setGrandTotal(rs.getDouble("grand_total"));
                } catch (NullPointerException npe) {
                    trans.setGrandTotal(0);
                }
                try {
                    trans.setCashDiscount(rs.getDouble("cash_discount"));
                } catch (NullPointerException npe) {
                    trans.setCashDiscount(0);
                }
                try {
                    trans.setTransactionReasonName(rs.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    trans.setTransactionReasonName("");
                }
                this.DayCloseSalesList.add(trans);
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseSalesList:" + e.getMessage());
        }

        //for Add Date
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql2);) {
            rs2 = ps.executeQuery();
            Trans trans2;
            while (rs2.next()) {
                trans2 = new Trans();
                try {
                    trans2.setCurrencyCode(rs2.getString("currency_code"));
                } catch (NullPointerException npe) {
                    trans2.setCurrencyCode("");
                }
                try {
                    trans2.setGrandTotal(rs2.getDouble("grand_total"));
                } catch (NullPointerException npe) {
                    trans2.setGrandTotal(0);
                }
                try {
                    trans2.setCashDiscount(rs2.getDouble("cash_discount"));
                } catch (NullPointerException npe) {
                    trans2.setCashDiscount(0);
                }
                try {
                    trans2.setTransactionReasonName(rs2.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    trans2.setTransactionReasonName("");
                }
                this.DayCloseSalesList2.add(trans2);
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseSalesList2:" + e.getMessage());
        }
    }

    public void refreshDayClosePurchaseList(String aDatesString) {
        ResultSet rs;
        ResultSet rs2;
        this.DayClosePurchaseList = new ArrayList<>();
        this.DayClosePurchaseList2 = new ArrayList<>();
        String sql = "SELECT "
                + "tr.transaction_reason_name,t.currency_code,sum(t.grand_total) as grand_total "
                + "FROM transaction t inner join transaction_reason tr on t.transaction_reason_id=tr.transaction_reason_id "
                + "WHERE t.transaction_type_id IN(1,19) "
                + "AND cast(t.transaction_date as date) IN(" + aDatesString + ") "
                + "GROUP BY tr.transaction_reason_name,t.currency_code";
        String sql2 = "SELECT "
                + "tr.transaction_reason_name,t.currency_code,sum(t.grand_total) as grand_total "
                + "FROM transaction t inner join transaction_reason tr on t.transaction_reason_id=tr.transaction_reason_id "
                + "WHERE t.transaction_type_id IN(1,19) "
                + "AND cast(t.add_date as date) IN(" + aDatesString + ") "
                + "GROUP BY tr.transaction_reason_name,t.currency_code";
        //for TransDate
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Trans trans;
            while (rs.next()) {
                trans = new Trans();
                try {
                    trans.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    trans.setCurrencyCode("");
                }
                try {
                    trans.setGrandTotal(rs.getDouble("grand_total"));
                } catch (NullPointerException npe) {
                    trans.setGrandTotal(0);
                }
                try {
                    trans.setTransactionReasonName(rs.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    trans.setTransactionReasonName("");
                }
                this.DayClosePurchaseList.add(trans);
            }
        } catch (Exception e) {
            System.err.println("refreshDayClosePurchaseList:" + e.getMessage());
        }
        
        //for AddDate
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql2);) {
            rs2 = ps.executeQuery();
            Trans trans2;
            while (rs2.next()) {
                trans2 = new Trans();
                try {
                    trans2.setCurrencyCode(rs2.getString("currency_code"));
                } catch (NullPointerException npe) {
                    trans2.setCurrencyCode("");
                }
                try {
                    trans2.setGrandTotal(rs2.getDouble("grand_total"));
                } catch (NullPointerException npe) {
                    trans2.setGrandTotal(0);
                }
                try {
                    trans2.setTransactionReasonName(rs2.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    trans2.setTransactionReasonName("");
                }
                this.DayClosePurchaseList2.add(trans2);
            }
        } catch (Exception e) {
            System.err.println("refreshDayClosePurchaseList2:" + e.getMessage());
        }
    }

    public void refreshDayCloseCashReceiptList(String aDatesString) {
        ResultSet rs;
        ResultSet rs2;
        this.DayCloseCashReceiptList = new ArrayList<>();
        this.DayCloseCashReceiptList2 = new ArrayList<>();
        String sql = "SELECT "
                + "tr.transaction_reason_name,p.currency_code,sum(p.paid_amount) as paid_amount "
                + "FROM pay p inner join transaction_reason tr on p.pay_reason_id=tr.transaction_reason_id "
                + "WHERE p.pay_type_id=14 AND p.pay_method_id!=6 "
                + "AND cast(p.pay_date as date) IN(" + aDatesString + ") "
                + "GROUP BY tr.transaction_reason_name,p.currency_code";
        String sql2 = "SELECT "
                + "tr.transaction_reason_name,p.currency_code,sum(p.paid_amount) as paid_amount "
                + "FROM pay p inner join transaction_reason tr on p.pay_reason_id=tr.transaction_reason_id "
                + "WHERE p.pay_type_id=14 AND p.pay_method_id!=6 "
                + "AND cast(p.add_date as date) IN(" + aDatesString + ") "
                + "GROUP BY tr.transaction_reason_name,p.currency_code";
        //for PayDate
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Pay pay;
            while (rs.next()) {
                pay = new Pay();
                try {
                    pay.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    pay.setCurrencyCode("");
                }
                try {
                    pay.setPaidAmount(rs.getDouble("paid_amount"));
                } catch (NullPointerException npe) {
                    pay.setPaidAmount(0);
                }
                try {
                    pay.setPayRefNo(rs.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    pay.setPayRefNo("");
                }
                this.DayCloseCashReceiptList.add(pay);
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseCashReceiptList:" + e.getMessage());
        }
        
        //for AddDate
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql2);) {
            rs2 = ps.executeQuery();
            Pay pay2;
            while (rs2.next()) {
                pay2 = new Pay();
                try {
                    pay2.setCurrencyCode(rs2.getString("currency_code"));
                } catch (NullPointerException npe) {
                    pay2.setCurrencyCode("");
                }
                try {
                    pay2.setPaidAmount(rs2.getDouble("paid_amount"));
                } catch (NullPointerException npe) {
                    pay2.setPaidAmount(0);
                }
                try {
                    pay2.setPayRefNo(rs2.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    pay2.setPayRefNo("");
                }
                this.DayCloseCashReceiptList2.add(pay2);
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseCashReceiptList2:" + e.getMessage());
        }
    }

    public void refreshDayCloseCashPaymentList(String aDatesString) {
        ResultSet rs;
        ResultSet rs2;
        this.DayCloseCashPaymentList = new ArrayList<>();
        this.DayCloseCashPaymentList2 = new ArrayList<>();
        String sql = "SELECT "
                + "tr.transaction_reason_name,p.currency_code,sum(p.paid_amount) as paid_amount "
                + "FROM pay p inner join transaction_reason tr on p.pay_reason_id=tr.transaction_reason_id "
                + "WHERE p.pay_type_id=15 AND p.pay_method_id!=7 "
                + "AND cast(p.pay_date as date) IN(" + aDatesString + ") "
                + "GROUP BY tr.transaction_reason_name,p.currency_code";
        String sql2 = "SELECT "
                + "tr.transaction_reason_name,p.currency_code,sum(p.paid_amount) as paid_amount "
                + "FROM pay p inner join transaction_reason tr on p.pay_reason_id=tr.transaction_reason_id "
                + "WHERE p.pay_type_id=15 AND p.pay_method_id!=7 "
                + "AND cast(p.add_date as date) IN(" + aDatesString + ") "
                + "GROUP BY tr.transaction_reason_name,p.currency_code";
        //for PayDate
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Pay pay;
            while (rs.next()) {
                pay = new Pay();
                try {
                    pay.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    pay.setCurrencyCode("");
                }
                try {
                    pay.setPaidAmount(rs.getDouble("paid_amount"));
                } catch (NullPointerException npe) {
                    pay.setPaidAmount(0);
                }
                try {
                    pay.setPayRefNo(rs.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    pay.setPayRefNo("");
                }
                this.DayCloseCashPaymentList.add(pay);
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseCashPaymentList:" + e.getMessage());
        }
        
        //for AddDate
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql2);) {
            rs2 = ps.executeQuery();
            Pay pay2;
            while (rs2.next()) {
                pay2 = new Pay();
                try {
                    pay2.setCurrencyCode(rs2.getString("currency_code"));
                } catch (NullPointerException npe) {
                    pay2.setCurrencyCode("");
                }
                try {
                    pay2.setPaidAmount(rs2.getDouble("paid_amount"));
                } catch (NullPointerException npe) {
                    pay2.setPaidAmount(0);
                }
                try {
                    pay2.setPayRefNo(rs2.getString("transaction_reason_name"));
                } catch (NullPointerException npe) {
                    pay2.setPayRefNo("");
                }
                this.DayCloseCashPaymentList2.add(pay2);
            }
        } catch (Exception e) {
            System.err.println("refreshDayCloseCashPaymentList2:" + e.getMessage());
        }
    }

    public void searchDayCloseDashboard() {
        try {
            String DatesString = "";
            int n = this.MultipleDates.size();
            for (int i = 0; i < n; i++) {
                if (DatesString.isEmpty()) {
                    DatesString = "'" + new UtilityBean().formatDateServer(this.MultipleDates.get(i)) + "'";
                } else {
                    DatesString = DatesString + ",'" + new UtilityBean().formatDateServer(this.MultipleDates.get(i)) + "'";
                }
            }
            this.refreshDayCloseSalesList(DatesString);
            this.refreshDayClosePurchaseList(DatesString);
            this.refreshDayCloseCashReceiptList(DatesString);
            this.refreshDayCloseCashPaymentList(DatesString);
        } catch (Exception e) {
            System.err.println("searchDayCloseDashboard:" + e.getMessage());
        }
    }

    public void initDayCloseDashboard() {
        try {
            Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();
            try {
                this.MultipleDates.clear();
            } catch (Exception e) {
                this.MultipleDates = new ArrayList<>();
            }
            this.MultipleDates.add(CurrentServerDate);
            this.searchDayCloseDashboard();
        } catch (Exception e) {
            System.err.println("initDayCloseDashboard:" + e.getMessage());
        }
    }

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
     * @return the DayCloseSalesList
     */
    public List<Trans> getDayCloseSalesList() {
        return DayCloseSalesList;
    }

    /**
     * @param DayCloseSalesList the DayCloseSalesList to set
     */
    public void setDayCloseSalesList(List<Trans> DayCloseSalesList) {
        this.DayCloseSalesList = DayCloseSalesList;
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

}
