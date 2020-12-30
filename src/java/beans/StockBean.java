package beans;

import connections.DBConnection;
import entities.AccCurrency;
import entities.ItemProductionMap;
import entities.Stock;
import entities.TransItem;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
public class StockBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Stock> Stocks;
    private String ActionMessage = null;
    private Stock SelectedStock = null;
    private Long SelectedStockId;
    private List<Stock> StocksList;
    private List<Stock> StocksSummary;
    private List<Stock> BatchList;
    private List<Stock> SpecificList;
    private double StockTotalCostPrice;

    public void setStockFromResultset(Stock aStock, ResultSet aResultSet) {
        try {
            try {
                aStock.setStockId(aResultSet.getInt("stock_id"));
            } catch (NullPointerException npe) {
                aStock.setStockId(0);
            }
            try {
                aStock.setStoreId(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                aStock.setStoreId(0);
            }
            try {
                aStock.setItemId(aResultSet.getLong("item_id"));
            } catch (NullPointerException npe) {
                aStock.setItemId(0);
            }
            try {
                aStock.setBatchno(aResultSet.getString("batchno"));
            } catch (NullPointerException npe) {
                aStock.setBatchno("");
            }
            try {
                aStock.setUnitCost(aResultSet.getDouble("unit_cost"));
            } catch (NullPointerException npe) {
                aStock.setUnitCost(0);
            }
            try {
                aStock.setCurrentqty(aResultSet.getDouble("currentqty"));
            } catch (NullPointerException npe) {
                aStock.setCurrentqty(0);
            }
            try {
                aStock.setItemMnfDate(new Date(aResultSet.getDate("item_mnf_date").getTime()));
            } catch (NullPointerException npe) {
                aStock.setItemMnfDate(null);
            }
            try {
                aStock.setItemExpDate(new Date(aResultSet.getDate("item_exp_date").getTime()));
            } catch (NullPointerException npe) {
                aStock.setItemExpDate(null);
            }
            try {
                aStock.setCodeSpecific(aResultSet.getString("code_specific"));
            } catch (NullPointerException npe) {
                aStock.setCodeSpecific("");
            }
            try {
                aStock.setDescSpecific(aResultSet.getString("desc_specific"));
            } catch (NullPointerException npe) {
                aStock.setDescSpecific("");
            }
            try {
                aStock.setDescMore(aResultSet.getString("desc_more"));
            } catch (NullPointerException npe) {
                aStock.setDescMore("");
            }
            try {
                aStock.setWarrantyDesc(aResultSet.getString("warranty_desc"));
            } catch (NullPointerException npe) {
                aStock.setWarrantyDesc("");
            }
            try {
                aStock.setWarrantyExpiryDate(new Date(aResultSet.getDate("warranty_expiry_date").getTime()));
            } catch (NullPointerException npe) {
                aStock.setWarrantyExpiryDate(null);
            }
            try {
                aStock.setPurchaseDate(new Date(aResultSet.getDate("purchase_date").getTime()));
            } catch (NullPointerException npe) {
                aStock.setPurchaseDate(null);
            }
            try {
                aStock.setDepStartDate(new Date(aResultSet.getDate("dep_start_date").getTime()));
            } catch (NullPointerException npe) {
                aStock.setDepStartDate(null);
            }
            try {
                aStock.setDepMethodId(aResultSet.getInt("dep_method_id"));
            } catch (NullPointerException npe) {
                aStock.setDepMethodId(0);
            }
            try {
                aStock.setDepRate(aResultSet.getDouble("dep_rate"));
            } catch (NullPointerException npe) {
                aStock.setDepRate(0);
            }
            try {
                aStock.setAverageMethodId(aResultSet.getInt("average_method_id"));
            } catch (NullPointerException npe) {
                aStock.setAverageMethodId(0);
            }
            try {
                aStock.setEffectiveLife(aResultSet.getInt("effective_life"));
            } catch (NullPointerException npe) {
                aStock.setEffectiveLife(0);
            }
            try {
                aStock.setAccountCode(aResultSet.getString("account_code"));
            } catch (NullPointerException npe) {
                aStock.setAccountCode("");
            }
            try {
                aStock.setResidualValue(aResultSet.getDouble("residual_value"));
            } catch (NullPointerException npe) {
                aStock.setResidualValue(0);
            }
            try {
                aStock.setAssetStatusId(aResultSet.getInt("asset_status_id"));
            } catch (NullPointerException npe) {
                aStock.setAssetStatusId(0);
            }
            try {
                aStock.setAssetStatusDesc(aResultSet.getString("asset_status_desc"));
            } catch (NullPointerException npe) {
                aStock.setAssetStatusDesc("");
            }
            try {
                aStock.setQty_damage(aResultSet.getDouble("qty_damage"));
            } catch (NullPointerException npe) {
                aStock.setQty_damage(0);
            }
            try {
                if (aResultSet.getDouble("specific_size") > 0) {
                    aStock.setSpecific_size(aResultSet.getDouble("specific_size"));
                } else {
                    aStock.setSpecific_size(1);
                }
            } catch (NullPointerException npe) {
                aStock.setSpecific_size(1);
            }
        } catch (SQLException se) {
            System.err.println("setStockFromResultset:" + se.getMessage());
        }
    }

    public void setStockFromResultsetReport(Stock aStock, ResultSet aResultSet) {
        try {
            try {
                aStock.setStockId(aResultSet.getInt("stock_id"));
            } catch (NullPointerException npe) {
                aStock.setStockId(0);
            }
            try {
                aStock.setStoreId(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                aStock.setStoreId(0);
            }
            try {
                aStock.setItemId(aResultSet.getLong("item_id"));
            } catch (NullPointerException npe) {
                aStock.setItemId(0);
            }
            try {
                aStock.setBatchno(aResultSet.getString("batchno"));
            } catch (NullPointerException npe) {
                aStock.setBatchno("");
            }
            try {
                aStock.setUnitCost(aResultSet.getDouble("unit_cost"));
            } catch (NullPointerException npe) {
                aStock.setUnitCost(0);
            }
            try {
                aStock.setCurrentqty(aResultSet.getDouble("currentqty"));
            } catch (NullPointerException npe) {
                aStock.setCurrentqty(0);
            }
            try {
                aStock.setItemMnfDate(new Date(aResultSet.getDate("item_mnf_date").getTime()));
            } catch (NullPointerException npe) {
                aStock.setItemMnfDate(null);
            }
            try {
                aStock.setItemExpDate(new Date(aResultSet.getDate("item_exp_date").getTime()));
            } catch (NullPointerException npe) {
                aStock.setItemExpDate(null);
            }
            try {
                aStock.setCodeSpecific(aResultSet.getString("code_specific"));
            } catch (NullPointerException npe) {
                aStock.setCodeSpecific("");
            }
            try {
                aStock.setDescSpecific(aResultSet.getString("desc_specific"));
            } catch (NullPointerException npe) {
                aStock.setDescSpecific("");
            }
            try {
                aStock.setDescMore(aResultSet.getString("desc_more"));
            } catch (NullPointerException npe) {
                aStock.setDescMore("");
            }
            try {
                aStock.setWarrantyDesc(aResultSet.getString("warranty_desc"));
            } catch (NullPointerException npe) {
                aStock.setWarrantyDesc("");
            }
            try {
                aStock.setWarrantyExpiryDate(new Date(aResultSet.getDate("warranty_expiry_date").getTime()));
            } catch (NullPointerException npe) {
                aStock.setWarrantyExpiryDate(null);
            }
            try {
                aStock.setPurchaseDate(new Date(aResultSet.getDate("purchase_date").getTime()));
            } catch (NullPointerException npe) {
                aStock.setPurchaseDate(null);
            }
            try {
                aStock.setDepStartDate(new Date(aResultSet.getDate("dep_start_date").getTime()));
            } catch (NullPointerException npe) {
                aStock.setDepStartDate(null);
            }
            try {
                aStock.setDepMethodId(aResultSet.getInt("dep_method_id"));
            } catch (NullPointerException npe) {
                aStock.setDepMethodId(0);
            }
            try {
                aStock.setDepRate(aResultSet.getDouble("dep_rate"));
            } catch (NullPointerException npe) {
                aStock.setDepRate(0);
            }
            try {
                aStock.setAverageMethodId(aResultSet.getInt("average_method_id"));
            } catch (NullPointerException npe) {
                aStock.setAverageMethodId(0);
            }
            try {
                aStock.setEffectiveLife(aResultSet.getInt("effective_life"));
            } catch (NullPointerException npe) {
                aStock.setEffectiveLife(0);
            }
            try {
                aStock.setAccountCode(aResultSet.getString("account_code"));
            } catch (NullPointerException npe) {
                aStock.setAccountCode("");
            }
            try {
                aStock.setResidualValue(aResultSet.getDouble("residual_value"));
            } catch (NullPointerException npe) {
                aStock.setResidualValue(0);
            }
            try {
                aStock.setAssetStatusId(aResultSet.getInt("asset_status_id"));
            } catch (NullPointerException npe) {
                aStock.setAssetStatusId(0);
            }
            try {
                aStock.setAssetStatusDesc(aResultSet.getString("asset_status_desc"));
            } catch (NullPointerException npe) {
                aStock.setAssetStatusDesc("");
            }

            //for Report
            try {
                aStock.setItemCode(aResultSet.getString("item_code"));
            } catch (Exception e) {
                aStock.setItemCode("");
            }
            try {
                aStock.setDescription(aResultSet.getString("description"));
            } catch (Exception e) {
                aStock.setDescription("");
            }
            try {
                aStock.setUnitSymbol(aResultSet.getString("unit_symbol"));
            } catch (Exception e) {
                aStock.setUnitSymbol("");
            }
            try {
                aStock.setCurrencyCode(aResultSet.getString("currency_code"));
            } catch (Exception e) {
                aStock.setCurrencyCode("");
            }
            try {
                aStock.setReorderLevel(aResultSet.getInt("reorder_level"));
            } catch (Exception e) {
                aStock.setReorderLevel(0);
            }
            try {
                aStock.setUnitCostPrice(aResultSet.getDouble("unit_cost_price"));
            } catch (Exception e) {
                aStock.setUnitCostPrice(0);
            }
            try {
                aStock.setUnitRetailsalePrice(aResultSet.getDouble("unit_retailsale_price"));
            } catch (Exception e) {
                aStock.setUnitRetailsalePrice(0);
            }
            try {
                aStock.setUnitWholesalePrice(aResultSet.getDouble("unit_wholesale_price"));
            } catch (Exception e) {
                aStock.setUnitWholesalePrice(0);
            }
            try {
                aStock.setUnitSpecialPrice(aResultSet.getDouble("unit_special_price"));
            } catch (Exception e) {
                aStock.setUnitSpecialPrice(0);
            }
            try {
                aStock.setStoreName(aResultSet.getString("store_name"));
            } catch (Exception e) {
                aStock.setStoreName("");
            }
            try {
                aStock.setCategoryName(aResultSet.getString("category_name"));
            } catch (Exception e) {
                aStock.setCategoryName("");
            }
            try {
                aStock.setUnitName(aResultSet.getString("unit_name"));
            } catch (Exception e) {
                aStock.setUnitName("");
            }
            try {
                aStock.setSubCategoryName(aResultSet.getString("sub_category_name"));
            } catch (Exception e) {
                aStock.setSubCategoryName("");
            }
            try {
                aStock.setCostValue(aResultSet.getDouble("cost_value"));
            } catch (Exception e) {
                aStock.setCostValue(0);
            }
            try {
                aStock.setRetailsaleValue(aResultSet.getDouble("retailsale_value"));
            } catch (Exception e) {
                aStock.setRetailsaleValue(0);
            }
            try {
                aStock.setWholesaleValue(aResultSet.getDouble("wholesale_value"));
            } catch (Exception e) {
                aStock.setWholesaleValue(0);
            }
            try {
                aStock.setQty_damage(aResultSet.getDouble("qty_damage"));
            } catch (Exception e) {
                aStock.setQty_damage(0);
            }
            try {
                if (aResultSet.getDouble("specific_size") > 0) {
                    aStock.setSpecific_size(aResultSet.getDouble("specific_size"));
                } else {
                    aStock.setSpecific_size(1);
                }
            } catch (NullPointerException npe) {
                aStock.setSpecific_size(1);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public Stock getStockCurrentExpiryStatus(long aItem_id, String aBatchno, String aCodeSpecific, String aDescSpecific) {
        String sql = "SELECT * FROM view_stock_expiry_status_vw WHERE item_id=" + aItem_id + " AND batchno='" + aBatchno + "' AND code_specific='" + aCodeSpecific + "' AND desc_specific='" + aDescSpecific + "'";
        ResultSet rs = null;
        Stock stock = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                stock = new Stock();
                this.setStockFromResultsetReport(stock, rs);
                this.setStockFromResultsetAppendExpiryStatus(stock, rs);
            }
        } catch (Exception e) {
            System.err.println("getStockCurrentExpiryStatus:" + e.getMessage());
        }
        return stock;
    }

    public void setStockFromResultsetAppendExpiryStatus(Stock aStock, ResultSet aResultSet) {
        try {
            try {
                aStock.setStock_type(aResultSet.getString("stock_type"));
            } catch (Exception e) {
                aStock.setStock_type("");
            }
            try {
                aStock.setDays_to_expiry(aResultSet.getInt("days_to_expiry"));
            } catch (Exception e) {
                aStock.setDays_to_expiry(0);
            }
            try {
                aStock.setStatus(aResultSet.getString("expiry_status"));
            } catch (Exception e) {
                aStock.setStatus("");
            }
            try {
                aStock.setStatus_qty(aResultSet.getDouble("status_qty"));
            } catch (Exception e) {
                aStock.setStatus_qty(0);
            }
            try {
                aStock.setStatus_perc(aResultSet.getDouble("status_perc"));
            } catch (Exception e) {
                aStock.setStatus_perc(0);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void reportStockExpiryStatus(String aItemType, int aCategoryId, int aSubCategoryId, int aIsGeneral, String aStockType, String aExpiryStatus) {
        String sql = "SELECT * FROM view_stock_expiry_status_vw WHERE 1=1";
        String sqlsum = "SELECT expiry_status,count(*) as status_qty FROM view_stock_expiry_status_vw WHERE 1=1";
        String wheresql = "";
        String ordersql = " ORDER BY stock_type_order,expiry_status,description ASC";
        String ordersqlsum = " ORDER BY status_qty DESC";
        String groupbysum = " GROUP BY expiry_status";
        ResultSet rs = null;
        ResultSet rs2 = null;
        this.setStocksList(new ArrayList<>());
        this.setStocksSummary(new ArrayList<>());
        if (aStockType.length() > 0) {
            wheresql = wheresql + " AND stock_type='" + aStockType + "'";
        }
        if (aItemType.length() > 0) {
            wheresql = wheresql + " AND item_type='" + aItemType + "'";
        }
        if (aCategoryId > 0) {
            wheresql = wheresql + " AND category_id=" + aCategoryId;
        }
        if (aSubCategoryId > 0) {
            wheresql = wheresql + " AND sub_category_id=" + aSubCategoryId;
        }
        if (aIsGeneral == 10) {
            wheresql = wheresql + " AND is_general=0";
        }
        if (aIsGeneral == 11) {
            wheresql = wheresql + " AND is_general=1";
        }
        if (aExpiryStatus.length() > 0) {
            wheresql = wheresql + " AND expiry_status='" + aExpiryStatus + "'";
        }
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysum + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stock stock = null;
            while (rs.next()) {
                stock = new Stock();
                this.setStockFromResultsetReport(stock, rs);
                this.setStockFromResultsetAppendExpiryStatus(stock, rs);
                this.getStocksList().add(stock);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        //summary
        double totalitems = this.getStocksList().size();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps2 = conn.prepareStatement(sqlsum);) {
            rs2 = ps2.executeQuery();
            Stock stock2 = null;
            while (rs2.next()) {
                stock2 = new Stock();
                try {
                    stock2.setStatus(rs2.getString("expiry_status"));
                } catch (NullPointerException npe) {
                    stock2.setStatus("");
                }
                try {
                    stock2.setStatus_qty(rs2.getDouble("status_qty"));
                } catch (NullPointerException npe) {
                    stock2.setStatus_qty(0);
                }
                if (totalitems > 0) {
                    stock2.setStatus_perc(100.0 * stock2.getStatus_qty() / totalitems);
                }
                this.getStocksSummary().add(stock2);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public int saveStock(Stock stock) {
        String sql = null;
        int status = 0;
        if (stock.getStockId() == 0) {
            sql = "{call sp_insert_stock(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (stock.getStockId() > 0) {
            sql = "{call sp_update_stock(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (stock.getStockId() > 0) {
                cs.setLong("in_stock_id", stock.getStockId());
            }
            cs.setInt("in_store_id", stock.getStoreId());
            cs.setLong("in_item_id", stock.getItemId());
            cs.setString("in_batchno", stock.getBatchno());
            cs.setDouble("in_currentqty", stock.getCurrentqty());
            try {
                cs.setDate("in_item_mnf_date", new java.sql.Date(stock.getItemMnfDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_item_mnf_date", null);
            }
            try {
                cs.setDate("in_item_exp_date", new java.sql.Date(stock.getItemExpDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_item_exp_date", null);
            }
            try {
                cs.setDouble("in_unit_cost", stock.getUnitCost());
            } catch (NullPointerException npe) {
                cs.setDouble("in_unit_cost", 0);
            }
            try {
                cs.setString("in_code_specific", stock.getCodeSpecific());
            } catch (NullPointerException npe) {
                cs.setString("in_code_specific", "");
            }
            try {
                cs.setString("in_desc_specific", stock.getDescSpecific());
            } catch (NullPointerException npe) {
                cs.setString("in_desc_specific", "");
            }
            try {
                cs.setString("in_desc_more", stock.getDescMore());
            } catch (NullPointerException npe) {
                cs.setString("in_desc_more", "");
            }
            try {
                cs.setString("in_warranty_desc", stock.getWarrantyDesc());
            } catch (NullPointerException npe) {
                cs.setString("in_warranty_desc", "");
            }
            try {
                cs.setDate("in_warranty_expiry_date", new java.sql.Date(stock.getWarrantyExpiryDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_warranty_expiry_date", null);
            }
            try {
                cs.setDate("in_purchase_date", new java.sql.Date(stock.getPurchaseDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_purchase_date", null);
            }
            try {
                cs.setDate("in_dep_start_date", new java.sql.Date(stock.getDepStartDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_dep_start_date", null);
            }
            try {
                cs.setInt("in_dep_method_id", stock.getDepMethodId());
            } catch (NullPointerException npe) {
                cs.setInt("in_dep_method_id", 0);
            }
            try {
                cs.setDouble("in_dep_rate", stock.getDepRate());
            } catch (NullPointerException npe) {
                cs.setDouble("in_dep_rate", 0);
            }
            try {
                cs.setInt("in_average_method_id", stock.getAverageMethodId());
            } catch (NullPointerException npe) {
                cs.setInt("in_average_method_id", 0);
            }
            try {
                cs.setInt("in_effective_life", stock.getEffectiveLife());
            } catch (NullPointerException npe) {
                cs.setInt("in_effective_life", 0);
            }
            try {
                cs.setInt("in_asset_status_id", stock.getAssetStatusId());
            } catch (NullPointerException npe) {
                cs.setInt("in_asset_status_id", 0);
            }
            try {
                cs.setString("in_asset_status_desc", stock.getAssetStatusDesc());
            } catch (NullPointerException npe) {
                cs.setString("in_asset_status_desc", "");
            }
            try {
                cs.setString("in_account_code", stock.getAccountCode());
            } catch (NullPointerException npe) {
                cs.setString("in_account_code", "");
            }
            try {
                cs.setDouble("in_residual_value", stock.getResidualValue());
            } catch (NullPointerException npe) {
                cs.setDouble("in_residual_value", 0);
            }
            try {
                cs.setDouble("in_qty_damage", stock.getQty_damage());
            } catch (NullPointerException npe) {
                cs.setDouble("in_qty_damage", 0);
            }
            try {
                if (stock.getSpecific_size() > 0) {
                    cs.setDouble("in_specific_size", stock.getSpecific_size());
                } else {
                    cs.setDouble("in_specific_size", 1);
                }
            } catch (NullPointerException npe) {
                cs.setDouble("in_specific_size", 1);
            }
            cs.executeUpdate();
            status = 1;
        } catch (Exception e) {
            status = 0;
            System.err.println("saveStock:" + e.getMessage());
        }
        return status;
    }

    public int addStock(Stock aStock, double aQty) {
        String sql = "{call sp_add_stock_bms(?,?,?,?,?,?,?)}";
        int status = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setInt("in_store_id", aStock.getStoreId());
            cs.setLong("in_item_id", aStock.getItemId());
            cs.setString("in_batchno", aStock.getBatchno());
            cs.setString("in_code_specific", aStock.getCodeSpecific());
            cs.setString("in_desc_specific", aStock.getDescSpecific());
            cs.setDouble("in_qty", aQty);
            cs.setDouble("in_unit_cost", aStock.getUnitCost());
            cs.executeUpdate();
            status = 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            status = 0;
        }
        return status;
    }

    public int subtractStock(Stock aStock, double aQty) {
        String sql = "{call sp_subtract_stock_bms(?,?,?,?,?,?)}";
        int status = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setInt("in_store_id", aStock.getStoreId());
            cs.setLong("in_item_id", aStock.getItemId());
            cs.setString("in_batchno", aStock.getBatchno());
            cs.setString("in_code_specific", aStock.getCodeSpecific());
            cs.setString("in_desc_specific", aStock.getDescSpecific());
            cs.setDouble("in_qty", aQty);
            cs.executeUpdate();
            status = 1;
        } catch (SQLException ex) {
            status = 0;
            Logger.getLogger(StockBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public int updateStockDamage(Stock aStock, double aQty, String aType) {
        String sql = "";
        if (aType.equals("Add")) {
            sql = "{call sp_update_stock_damage_add(?,?,?,?,?,?)}";
        } else if (aType.equals("Subtract")) {
            sql = "{call sp_update_stock_damage_subtract(?,?,?,?,?,?)}";
        }
        int status = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setInt("in_store_id", aStock.getStoreId());
            cs.setLong("in_item_id", aStock.getItemId());
            cs.setString("in_batchno", aStock.getBatchno());
            cs.setString("in_code_specific", aStock.getCodeSpecific());
            cs.setString("in_desc_specific", aStock.getDescSpecific());
            cs.setDouble("in_qty", aQty);
            cs.executeUpdate();
            status = 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            status = 0;
        }
        return status;
    }

    public Stock getStock(Long StockId) {
        String sql = "{call sp_search_stock_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, StockId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Stock stock = new Stock();
                this.setStockFromResultset(stock, rs);
                return stock;
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println("getStock1:" + se.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getStock1:" + ex.getMessage());
                }
            }
        }

    }

    public Stock getStock(int aStoreId, long aItemId, String aBatchNo, String aCodeSpecific, String aDescSpecific) {
        String sql = "{call sp_search_stock_bms(?,?,?,?,?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aStoreId);
            ps.setLong(2, aItemId);
            if (null == aBatchNo) {
                ps.setString(3, "");
            } else {
                ps.setString(3, aBatchNo);
            }
            if (null == aCodeSpecific) {
                ps.setString(4, "");
            } else {
                ps.setString(4, aCodeSpecific);
            }
            if (null == aDescSpecific) {
                ps.setString(5, "");
            } else {
                ps.setString(5, aDescSpecific);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                Stock stock = new Stock();
                this.setStockFromResultset(stock, rs);
                return stock;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("getStock2:" + e.getMessage());
            return null;
        }
    }

    public double getStockAtHand(long aItemId) {
        double qty_total = 0;
        String sql = "SELECT * FROM view_inventory_low_out_vw WHERE item_id=?";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                qty_total = rs.getDouble("qty_total");
            }
        } catch (Exception e) {
            System.err.println("getStockAtHand:" + e.getMessage());
        }
        return qty_total;
    }

    public Stock getStockAnyStore(long aItemId, String aBatchNo, String aCodeSpecific, String aDescSpecific) {
        String sql = "{call sp_search_stock_bms_any_store(?,?,?,?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aItemId);
            if (null == aBatchNo) {
                ps.setString(2, "");
            } else {
                ps.setString(2, aBatchNo);
            }
            if (null == aCodeSpecific) {
                ps.setString(3, "");
            } else {
                ps.setString(3, aCodeSpecific);
            }
            if (null == aDescSpecific) {
                ps.setString(4, "");
            } else {
                ps.setString(4, aDescSpecific);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                Stock stock = new Stock();
                this.setStockFromResultset(stock, rs);
                return stock;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("getStock3:" + e.getMessage());
            return null;
        }
    }

    public void setStockCurrentQty(Stock aStock, int aStoreId, long aItemId, String aBatchNo, String aCodeSpecific, String aDescSpecific) {
        double CurrentQty = 0;
        if (null == aStock) {
            //do nothing
        } else {
            if (aItemId == 0 || aStoreId == 0) {
                //do nothing
            } else {
                try {
                    CurrentQty = this.getStock(aStoreId, aItemId, aBatchNo, aCodeSpecific, aDescSpecific).getCurrentqty();
                } catch (Exception e) {
                    //do nothing
                }
            }
            aStock.setCurrentqty(CurrentQty);
        }
    }

    public void setStockCurrentQty(Stock aStock, int aStoreId, long aItemId, long aStockId) {
        Stock retriedstock = null;
        if (null == aStock) {
            //do nothing
        } else {
            if (aStockId == 0 && aItemId == 0) {
                //do nothing
            } else {
                try {
                    if (aStockId > 0) {
                        Stock st = this.getStock(aStockId);
                        retriedstock = this.getStock(aStoreId, st.getItemId(), st.getBatchno(), st.getCodeSpecific(), st.getDescSpecific());
                    } else if (aItemId > 0) {
                        List<Stock> sts = this.getStocks(aStoreId, aItemId);
                        if (sts.size() > 1) {
                            //many stocks, do nothing
                        } else if (sts.size() == 1) {
                            retriedstock = sts.get(0);
                        }
                    }
                } catch (Exception e) {
                    //do nothing
                }
            }
            if (null != retriedstock) {
                aStock.setCurrentqty(retriedstock.getCurrentqty());
                aStock.setBatchno(retriedstock.getBatchno());
                aStock.setCodeSpecific(retriedstock.getCodeSpecific());
                aStock.setDescSpecific(retriedstock.getDescSpecific());
            }
        }
    }

    public void setStockCurrentQty(TransItem aTransItem, int aStoreId, long aItemId, long aStockId) {
        //System.out.println("aStockId:" + aStockId + " aItemId:" + aItemId);
        double CurrentQty = 0;
        if (null == aTransItem) {
            //do nothing
        } else {
            if (aStockId == 0 && aItemId == 0) {
                //do nothing
            } else {
                try {
                    if (aStockId > 0) {
                        Stock st = this.getStock(aStockId);
                        CurrentQty = this.getStock(aStoreId, st.getItemId(), st.getBatchno(), st.getCodeSpecific(), st.getDescSpecific()).getCurrentqty();
                    } else if (aItemId > 0) {
                        List<Stock> sts = this.getStocks(aStoreId, aItemId);
                        if (sts.size() > 1) {
                            //many stocks, do nothing
                        } else if (sts.size() == 1) {
                            Stock st2 = sts.get(0);
                            //CurrentQty = this.getStock(aStoreId, st2.getItemId(), st2.getBatchno(), st2.getCodeSpecific(), st2.getDescSpecific()).getCurrentqty();
                            CurrentQty = sts.get(0).getCurrentqty();
                        }
                    }
                } catch (Exception e) {
                    //do nothing
                }
            }
            aTransItem.setQty_total(CurrentQty);
        }
    }

    public void setStockCurrentQty(ItemProductionMap aItemProductionMap, int aStoreId, long aItemId) {
        double CurrentQty = 0;
        if (null == aItemProductionMap) {
            //do nothing
        } else {
            if (aItemId == 0) {
                //do nothing
            } else {
                try {
                    CurrentQty = this.getStock(aStoreId, aItemId, aItemProductionMap.getBatchno(), aItemProductionMap.getCodeSpecific(), aItemProductionMap.getDescSpecific()).getCurrentqty();
                } catch (Exception e) {
                    //do nothing
                }
            }
            aItemProductionMap.setInputQtyCurrent(CurrentQty);
            aItemProductionMap.setInputQtyBalance(CurrentQty - aItemProductionMap.getInputQtyTotal());
        }
    }

    public double getItemUnitCostPrice(int aStoreId, long aItemId, String aBatchNo, String aCodeSpecific, String aDescSpecific) {
        String sql = "{call sp_search_stock_bms(?,?,?,?,?)}";
        ResultSet rs = null;
        double UnitCostPrice = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aStoreId);
            ps.setLong(2, aItemId);
            ps.setString(3, aBatchNo);
            ps.setString(4, aCodeSpecific);
            ps.setString(5, aDescSpecific);

            rs = ps.executeQuery();
            if (rs.next()) {
                Stock stock = new Stock();
                try {
                    UnitCostPrice = rs.getDouble("unit_cost");
                } catch (NullPointerException npe) {
                    UnitCostPrice = 0;
                }
                return UnitCostPrice;
            } else {
                return UnitCostPrice;
            }
        } catch (SQLException | NullPointerException se) {
            System.err.println(se.getMessage());
            return 0;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public void addStock(int StoreId, Long ItemId, String BatchNo, double AddQty) {
        String sql = "{call sp_add_stock_by_store_item_batch(?,?,?,?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, StoreId);
            ps.setLong(2, ItemId);
            ps.setString(3, BatchNo);
            ps.setDouble(4, AddQty);
            ps.executeUpdate();
            //clean stock
            StockBean.deleteZeroQtyStock();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public void subtractStock(int StoreId, Long ItemId, String BatchNo, double SubtractQty) {
        String sql = "{call sp_subtract_stock_by_store_item_batch(?,?,?,?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, StoreId);
            ps.setLong(2, ItemId);
            ps.setString(3, BatchNo);
            ps.setDouble(4, SubtractQty);
            ps.executeUpdate();
            //clean stock
            StockBean.deleteZeroQtyStock();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public void deleteStock() {
        this.deleteStockById(this.getSelectedStockId());
    }

    public void deleteStockByObject(Stock stock) {
        this.deleteStockById(stock.getStockId());
    }

    public void deleteStockById(Long StockId) {
        String sql = "DELETE FROM stock WHERE stock_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, StockId);
            ps.executeUpdate();
            this.setActionMessage("Deleted Successfully!");
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            this.setActionMessage("Stock NOT deleted");
        }
    }

    public void displayStock(Stock StockFrom, Stock StockTo) {
        StockTo.setStockId(StockFrom.getStockId());
        StockTo.setStoreId(StockFrom.getStoreId());
        StockTo.setItemId(StockFrom.getItemId());
        StockTo.setBatchno(StockFrom.getBatchno());
        StockTo.setCurrentqty(StockFrom.getCurrentqty());
        StockTo.setItemMnfDate(StockFrom.getItemMnfDate());
        StockTo.setItemExpDate(StockFrom.getItemExpDate());
    }

    public void clearStock(Stock stock) {
        stock.setStockId(0);
        stock.setStockId(0);
        stock.setStoreId(0);
        stock.setItemId(0);
        stock.setBatchno("");
        stock.setCurrentqty(0);
        //stock.setItemMnfDate(null);
        //stock.setItemExpDate(null);
        stock.setStock_type("");
    }

    /**
     * @return the Stocks
     */
    public List<Stock> getStocks() {
        String sql;
        sql = "SELECT * FROM stock";
        ResultSet rs = null;
        Stocks = new ArrayList<Stock>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Stock stock = new Stock();
                this.setStockFromResultset(stock, rs);
                Stocks.add(stock);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return Stocks;
    }

    public List<Stock> getStocks(int StoreId, long ItemId) {
        String sql;
        sql = "{call sp_search_stock_by_store_item(?,?)}";
        ResultSet rs = null;
        Stocks = new ArrayList<Stock>();
        if (ItemId == 0 || StoreId == 0) {
            //do nothing
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, StoreId);
                ps.setLong(2, ItemId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Stock stock = new Stock();
                    this.setStockFromResultset(stock, rs);
                    Stocks.add(stock);
                }
            } catch (Exception e) {
                System.err.println("getStocks:" + e.getMessage());
            }
        }
        return Stocks;
    }

    public void setStocks(List<Stock> aStockList, int StoreId, long ItemId) {
        String sql;
        sql = "{call sp_search_stock_by_store_item(?,?)}";
        ResultSet rs = null;
        try {
            aStockList.clear();
        } catch (Exception e) {
            aStockList = new ArrayList<Stock>();
        }
        if (ItemId == 0 || StoreId == 0) {
            //do nothing
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, StoreId);
                ps.setLong(2, ItemId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Stock stock = new Stock();
                    this.setStockFromResultset(stock, rs);
                    aStockList.add(stock);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void refreshItemBatchList(int StoreId, long ItemId) {
        String sql;
        sql = "{call sp_search_stock_by_store_item(?,?)}";
        ResultSet rs = null;
        try {
            this.BatchList.clear();
        } catch (Exception e) {
            this.BatchList = new ArrayList<Stock>();
        }
        if (ItemId == 0 || StoreId == 0) {
            //do nothing
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, StoreId);
                ps.setLong(2, ItemId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Stock stock = new Stock();
                    this.setStockFromResultset(stock, rs);
                    this.BatchList.add(stock);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void refreshBatchList(int StoreId, long ItemId) {
        String sql;
        sql = "{call sp_search_stock_distinct_batch_by_store_item(?,?)}";
        ResultSet rs = null;
        try {
            this.BatchList.clear();
        } catch (Exception e) {
            this.BatchList = new ArrayList<>();
        }
        try {
            this.SpecificList.clear();
        } catch (Exception e) {
            this.SpecificList = new ArrayList<>();
        }
        if (ItemId == 0 || StoreId == 0) {
            //do nothing
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, StoreId);
                ps.setLong(2, ItemId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Stock stock = new Stock();
                    //this.setStockFromResultset(stock, rs);
                    try {
                        stock.setBatchno(rs.getString("batchno"));
                    } catch (NullPointerException npe) {
                        stock.setBatchno("");
                    }
                    try {
                        stock.setItemExpDate(new Date(rs.getDate("item_exp_date").getTime()));
                    } catch (NullPointerException npe) {
                        stock.setItemExpDate(null);
                    }
                    this.BatchList.add(stock);
                }
                //update for the specific items if list has 1 item
                try {
                    if (this.BatchList.size() == 1) {
                        this.refreshSpecificList(StoreId, ItemId, this.BatchList.get(0).getBatchno());
                    }
                } catch (Exception e) {

                }
            } catch (SQLException se) {
                System.err.println("refreshSpecificList:" + se.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        System.err.println("refreshSpecificList:" + ex.getMessage());
                    }
                }
            }
        }
    }

    public void clearLists() {
        try {
            this.BatchList.clear();
        } catch (Exception e) {
            this.BatchList = new ArrayList<>();
        }
        try {
            this.SpecificList.clear();
        } catch (Exception e) {
            this.SpecificList = new ArrayList<>();
        }
    }

    public List<Stock> getStocks(int aStoreId, long aItemId, String aBatchno) {
        String sql;
        sql = "{call sp_search_stock_by_store_item_batch(?,?,?)}";
        ResultSet rs = null;
        Stocks = new ArrayList<Stock>();
        if (aItemId == 0 || aStoreId == 0) {
            //do nothing
        }
        {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aStoreId);
                ps.setLong(2, aItemId);
                ps.setString(3, aBatchno);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Stock stock = new Stock();
                    this.setStockFromResultset(stock, rs);
                    Stocks.add(stock);
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        }
        return Stocks;
    }

    public void refreshSpecificList(int aStoreId, long aItemId, String aBatchno) {
        String sql;
        sql = "{call sp_search_stock_by_store_item_batch(?,?,?)}";
        ResultSet rs = null;
        try {
            this.SpecificList.clear();
        } catch (Exception e) {
            this.SpecificList = new ArrayList<>();
        }
        if (aItemId == 0 || aStoreId == 0) {
            //do nothing
        }
        {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aStoreId);
                ps.setLong(2, aItemId);
                ps.setString(3, aBatchno);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Stock stock = new Stock();
                    this.setStockFromResultset(stock, rs);
                    this.SpecificList.add(stock);
                }
            } catch (SQLException se) {
                System.err.println("refreshSpecificList:" + se.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        System.err.println("refreshSpecificList:" + ex.getMessage());
                    }
                }
            }
        }
    }

    public void refreshSpecificList(int aStoreId, long aItemId) {
        String sql;
        sql = "{call sp_search_stock_by_store_item(?,?)}";
        ResultSet rs = null;
        try {
            this.SpecificList.clear();
        } catch (Exception e) {
            this.SpecificList = new ArrayList<>();
        }
        if (aItemId == 0 || aStoreId == 0) {
            //do nothing
        }
        {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aStoreId);
                ps.setLong(2, aItemId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Stock stock = new Stock();
                    this.setStockFromResultset(stock, rs);
                    this.SpecificList.add(stock);
                }
            } catch (SQLException se) {
                System.err.println("refreshSpecificList2:" + se.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        System.err.println("refreshSpecificList2:" + ex.getMessage());
                    }
                }
            }
        }
    }

    public List<Stock> getStocksByItem(long ItemId) {
        String sql;
        sql = "{call sp_search_stock_by_item_id(?)}";
        ResultSet rs = null;
        Stocks = new ArrayList<Stock>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, ItemId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Stock stock = new Stock();
                this.setStockFromResultset(stock, rs);
                Stocks.add(stock);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        //GeneralSetting.setLIST_ITEMS_COUNT(Stocks.size());
        return Stocks;
    }

    public List<Stock> getStocks(int aStoreId) {
        String sql;
        sql = "{call sp_search_stock_by_store_id(?)}";
        ResultSet rs = null;
        Stocks = new ArrayList<Stock>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aStoreId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Stock stock = new Stock();
                this.setStockFromResultset(stock, rs);
                Stocks.add(stock);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return Stocks;
    }

    public double getStockAtHandCostPriceValue() {
        double CpValue = 0;
        String sql = "";
        sql = "select sv.currency_code,sum(cp_value) as cp_value from view_stock_value sv group by sv.currency_code";
        ResultSet rs = null;
        List<Stock> stocks = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            //for stock currency is different from local currency currency, we first convert
            double xrate = 1;
            double XrateMultiply = 1;
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            while (rs.next()) {
                String cur = "";
                double amt = 0;
                double amt_lc = 0;
                try {
                    cur = rs.getString("currency_code");
                } catch (NullPointerException npe) {
                }
                try {
                    amt = rs.getDouble("cp_value");
                } catch (NullPointerException npe) {
                }
                try {
                    xrate = new AccXrateBean().getXrate(cur, LocalCurrency.getCurrencyCode());
                } catch (NullPointerException npe) {
                    xrate = 1;
                }
                try {
                    if (cur.equals(LocalCurrency.getCurrencyCode()) && !LocalCurrency.getCurrencyCode().equals(LocalCurrency.getCurrencyCode())) {
                        XrateMultiply = 1 / xrate;
                    } else {
                        XrateMultiply = xrate;
                    }
                } catch (NullPointerException npe) {
                    XrateMultiply = 1;
                }
                amt_lc = amt * XrateMultiply;
                CpValue = CpValue + amt_lc;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return CpValue;
    }

    public int getSnapshotMaxNoStockValue(int aAccPeriodId) {
        String sql;
        sql = "select max(snapshot_no) as snapshot_no from snapshot_stock_value where acc_period_id=" + aAccPeriodId;
        ResultSet rs = null;
        int SnapshotMaxNo = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    SnapshotMaxNo = rs.getInt("snapshot_no");
                } catch (NullPointerException npe) {
                    SnapshotMaxNo = 0;
                }
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return SnapshotMaxNo;
    }

    public double getStockAtHandCostPriceValueSnapshot(int aAccPeriodId) {
        double CpValue = 0;
        int SnapshopMaxNo = 0;
        SnapshopMaxNo = this.getSnapshotMaxNoStockValue(aAccPeriodId);
        String sql = "";
        sql = "select sv.currency_code,sum(cp_value) as cp_value from snapshot_stock_value sv where snapshot_no=" + SnapshopMaxNo + " group by sv.currency_code";
        ResultSet rs = null;
        List<Stock> stocks = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            //for stock currency is different from local currency currency, we first convert
            double xrate = 1;
            double XrateMultiply = 1;
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            while (rs.next()) {
                String cur = "";
                double amt = 0;
                double amt_lc = 0;
                try {
                    cur = rs.getString("currency_code");
                } catch (NullPointerException npe) {
                }
                try {
                    amt = rs.getDouble("cp_value");
                } catch (NullPointerException npe) {
                }
                try {
                    xrate = new AccXrateBean().getXrateSnapshot(cur, LocalCurrency.getCurrencyCode(), aAccPeriodId);
                } catch (NullPointerException npe) {
                    xrate = 1;
                }
                try {
                    if (cur.equals(LocalCurrency.getCurrencyCode()) && !LocalCurrency.getCurrencyCode().equals(LocalCurrency.getCurrencyCode())) {
                        XrateMultiply = 1 / xrate;
                    } else {
                        XrateMultiply = xrate;
                    }
                } catch (NullPointerException npe) {
                    XrateMultiply = 1;
                }
                amt_lc = amt * XrateMultiply;
                CpValue = CpValue + amt_lc;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return CpValue;
    }

    public void reportInventoryStock(int aStoreId, int aCategoryId, Date aDate1, Date aDate2) {
        String sql = "SELECT * FROM view_inventory_stock WHERE 1=1";
        //unit_cost_price excahnged for unit_cost
        String sqlsum = "SELECT currency_code,sum(currentqty*unit_cost) as cost_value,"
                + "sum(currentqty*unit_retailsale_price) as retailsale_value,"
                + "sum(currentqty*unit_wholesale_price) as wholesale_value FROM view_inventory_stock WHERE 1=1";
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = " GROUP BY currency_code";
        ResultSet rs = null;
        this.StocksList = new ArrayList<>();
        this.StocksSummary = new ArrayList<>();
        if (aStoreId > 0) {
            wheresql = wheresql + " AND store_id=" + aStoreId;
        }
        if (aCategoryId > 0) {
            wheresql = wheresql + " AND category_id=" + aCategoryId;
        }
        if (aDate1 != null && aDate2 != null) {
            wheresql = wheresql + " AND item_exp_date BETWEEN '" + new java.sql.Timestamp(aDate1.getTime()) + "' AND '" + new java.sql.Timestamp(aDate2.getTime()) + "'";
        }
        ordersql = " ORDER BY description ASC";
        ordersqlsum = " ORDER BY currency_code ASC";
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stock stock = null;
            while (rs.next()) {
                stock = new Stock();
                this.setStockFromResultsetReport(stock, rs);
                this.StocksList.add(stock);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            Stock stocksum = null;
            while (rs.next()) {
                stocksum = new Stock();
                try {
                    stocksum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    stocksum.setCurrencyCode("");
                }
                try {
                    stocksum.setCostValue(rs.getDouble("cost_value"));
                } catch (NullPointerException npe) {
                    stocksum.setCostValue(0);
                }
                try {
                    stocksum.setRetailsaleValue(rs.getDouble("retailsale_value"));
                } catch (NullPointerException npe) {
                    stocksum.setRetailsaleValue(0);
                }
                try {
                    stocksum.setWholesaleValue(rs.getDouble("wholesale_value"));
                } catch (NullPointerException npe) {
                    stocksum.setWholesaleValue(0);
                }
                this.StocksSummary.add(stocksum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportInventoryExpense(int aStoreId, int aCategoryId, Date aDate1, Date aDate2) {
        String sql = "SELECT * FROM view_inventory_expense WHERE 1=1";
        String sqlsum = "SELECT currency_code,sum(currentqty*unit_cost) as cost_value FROM view_inventory_expense WHERE 1=1";
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = " GROUP BY currency_code";
        ResultSet rs = null;
        this.StocksList = new ArrayList<>();
        this.StocksSummary = new ArrayList<>();
        if (aStoreId > 0) {
            wheresql = wheresql + " AND store_id=" + aStoreId;
        }
        if (aCategoryId > 0) {
            wheresql = wheresql + " AND category_id=" + aCategoryId;
        }
        if (aDate1 != null && aDate2 != null) {
            wheresql = wheresql + " AND item_exp_date BETWEEN '" + new java.sql.Timestamp(aDate1.getTime()) + "' AND '" + new java.sql.Timestamp(aDate2.getTime()) + "'";
        }
        ordersql = " ORDER BY description ASC";
        ordersqlsum = " ORDER BY currency_code ASC";
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stock stock = null;
            while (rs.next()) {
                stock = new Stock();
                this.setStockFromResultsetReport(stock, rs);
                this.StocksList.add(stock);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            Stock stocksum = null;
            while (rs.next()) {
                stocksum = new Stock();
                try {
                    stocksum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    stocksum.setCurrencyCode("");
                }
                try {
                    stocksum.setCostValue(rs.getDouble("cost_value"));
                } catch (NullPointerException npe) {
                    stocksum.setCostValue(0);
                }
                this.StocksSummary.add(stocksum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportInventoryAsset(int aStoreId, String aAssetType) {
        String sql = "SELECT * FROM view_inventory_asset WHERE 1=1";
        //unit_cost_price excahnged for unit_cost
        String sqlsum = "SELECT currency_code,sum(currentqty*unit_cost) as cost_value FROM view_inventory_asset WHERE 1=1";
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = " GROUP BY currency_code";
        ResultSet rs = null;
        this.StocksList = new ArrayList<>();
        this.StocksSummary = new ArrayList<>();
        if (aStoreId > 0) {
            wheresql = wheresql + " AND store_id=" + aStoreId;
        }
        if (aAssetType.length() > 0) {
            wheresql = wheresql + " AND asset_type='" + aAssetType + "'";
        }
        ordersql = " ORDER BY description ASC";
        ordersqlsum = " ORDER BY currency_code ASC";
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stock stock = null;
            while (rs.next()) {
                stock = new Stock();
                this.setStockFromResultsetReport(stock, rs);
                try {
                    stock.setQty_out(new Stock_outBean().getStock_outTotal(stock.getStoreId(), stock.getItemId(), stock.getBatchno(), stock.getCodeSpecific(), stock.getDescSpecific()));
                } catch (NullPointerException npe) {

                }
                this.StocksList.add(stock);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            Stock stocksum = null;
            while (rs.next()) {
                stocksum = new Stock();
                try {
                    stocksum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    stocksum.setCurrencyCode("");
                }
                try {
                    stocksum.setCostValue(rs.getDouble("cost_value"));
                } catch (NullPointerException npe) {
                    stocksum.setCostValue(0);
                }
                this.StocksSummary.add(stocksum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportStock(int aStoreId, int aCategoryId, Date aDate1, Date aDate2, String aStockyType) {
        String sql = "SELECT * FROM view_inventory_in WHERE 1=1";
        String sqlsum = "SELECT stock_type_order,stock_type,currency_code,sum(currentqty*unit_cost) as cost_value,"
                + "sum(currentqty*unit_retailsale_price) as retailsale_value,"
                + "sum(currentqty*unit_wholesale_price) as wholesale_value FROM view_inventory_in WHERE 1=1";
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = " GROUP BY stock_type_order,stock_type,currency_code";
        ResultSet rs = null;
        this.StocksList = new ArrayList<>();
        this.StocksSummary = new ArrayList<>();
        if (aStockyType.length() > 0) {
            wheresql = wheresql + " AND stock_type='" + aStockyType + "'";
        }
        if (aStoreId > 0) {
            wheresql = wheresql + " AND store_id=" + aStoreId;
        }
        if (aCategoryId > 0) {
            wheresql = wheresql + " AND category_id=" + aCategoryId;
        }
        if (aDate1 != null && aDate2 != null) {
            wheresql = wheresql + " AND item_exp_date BETWEEN '" + new java.sql.Timestamp(aDate1.getTime()) + "' AND '" + new java.sql.Timestamp(aDate2.getTime()) + "'";
        }
        ordersql = " ORDER BY stock_type_order,stock_type,description ASC";
        ordersqlsum = " ORDER BY stock_type_order,stock_type,currency_code ASC";
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stock stock = null;
            while (rs.next()) {
                stock = new Stock();
                this.setStockFromResultsetReport(stock, rs);
                try {
                    stock.setStock_type(rs.getString("stock_type"));
                } catch (NullPointerException npe) {
                    stock.setStock_type("");
                }
                this.StocksList.add(stock);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            Stock stocksum = null;
            this.StockTotalCostPrice = 0;
            while (rs.next()) {
                stocksum = new Stock();
                try {
                    stocksum.setStock_type(rs.getString("stock_type"));
                } catch (NullPointerException npe) {
                    stocksum.setStock_type("");
                }
                try {
                    stocksum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    stocksum.setCurrencyCode("");
                }
                try {
                    stocksum.setCostValue(rs.getDouble("cost_value"));
                    double xrate = this.getXrateMultiplyToLocal(stocksum.getCurrencyCode());
                    this.StockTotalCostPrice = this.StockTotalCostPrice + (stocksum.getCostValue() * xrate);
                } catch (NullPointerException npe) {
                    stocksum.setCostValue(0);
                }
                try {
                    stocksum.setRetailsaleValue(rs.getDouble("retailsale_value"));
                } catch (NullPointerException npe) {
                    stocksum.setRetailsaleValue(0);
                }
                try {
                    stocksum.setWholesaleValue(rs.getDouble("wholesale_value"));
                } catch (NullPointerException npe) {
                    stocksum.setWholesaleValue(0);
                }
                this.StocksSummary.add(stocksum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportStockPricingQtyError(int aStoreId, int aCategoryId, int aIsGeneral, String aIsSuspended, String aStockyType) {
        String sql = "select "
                + "i.expense_type,s.*,i.description,i.currency_code,i.category_id,i.unit_retailsale_price,i.unit_wholesale_price,i.unit_special_price,"
                + "i.is_suspended,t.store_name,c.category_name,u.unit_name,u.unit_symbol "
                + "from stock s inner join item i on s.item_id=i.item_id "
                + "INNER JOIN store t ON s.store_id=t.store_id "
                + "INNER JOIN category c ON i.category_id=c.category_id "
                + "INNER JOIN unit u ON i.unit_id=u.unit_id "
                + "WHERE i.is_track=1 AND "
                + "(i.is_sale=1 OR (i.is_sale=0 AND i.is_asset=0 AND i.is_buy=1)) AND "
                + "("
                + "	((s.unit_cost>=i.unit_retailsale_price and i.unit_retailsale_price>0) OR (s.unit_cost>=i.unit_wholesale_price and i.unit_wholesale_price>0) OR (s.unit_cost>=i.unit_special_price and i.unit_special_price>0)) OR "
                + "	(s.currentqty>=s.unit_cost)"
                + ") AND "
                + "("
                + "	ifnull(i.is_general,0)=0 OR "
                + "	("
                + "		ifnull(i.is_general,0)=1 AND (i.unit_retailsale_price+i.unit_wholesale_price+i.unit_special_price)>0"
                + "	)"
                + ")";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        this.StocksList = new ArrayList<>();
        this.StocksSummary = new ArrayList<>();
        if (aStockyType.length() > 0) {
            wheresql = wheresql + " AND i.expense_type='" + aStockyType + "'";
        }
        if (aStoreId > 0) {
            wheresql = wheresql + " AND s.store_id=" + aStoreId;
        }
        if (aCategoryId > 0) {
            wheresql = wheresql + " AND i.category_id=" + aCategoryId;
        }
        if (aIsSuspended.length() > 0) {
            wheresql = wheresql + " AND i.is_suspended='" + aIsSuspended + "'";
        }
        if (aIsGeneral == 10) {
            wheresql = wheresql + " AND i.is_general=0";
        }
        if (aIsGeneral == 11) {
            wheresql = wheresql + " AND i.is_general=1";
        }
        ordersql = " ORDER BY expense_type,description ASC";
        sql = sql + wheresql + ordersql;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stock stock = null;
            while (rs.next()) {
                stock = new Stock();
                this.setStockFromResultsetReport(stock, rs);
                try {
                    stock.setStock_type(rs.getString("expense_type"));
                } catch (NullPointerException npe) {
                    stock.setStock_type("");
                }
                this.StocksList.add(stock);
            }
        } catch (Exception e) {
            System.err.println("reportStockPricingQtyError:" + e.getMessage());
        }
    }

    public double getXrateMultiplyToLocal(String aFromCur) {
        double xrate = 1;
        double XrateMultiply = 1;
        AccCurrency LocalCurrency = null;
        LocalCurrency = new AccCurrencyBean().getLocalCurrency();
        if (aFromCur.equals(LocalCurrency.getCurrencyCode())) {
            XrateMultiply = 1;
        } else {
            try {
                xrate = new AccXrateBean().getXrate(aFromCur, LocalCurrency.getCurrencyCode());
            } catch (NullPointerException npe) {
                xrate = 1;
            }
            //XrateMultiply = 1 / xrate;
            XrateMultiply = xrate;
        }
        return XrateMultiply;
    }

    /**
     * @param Stocks the Stocks to set
     */
    public void setStocks(List<Stock> Stocks) {
        this.Stocks = Stocks;
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
     * @return the SelectedStock
     */
    public Stock getSelectedStock() {
        return SelectedStock;
    }

    /**
     * @param SelectedStock the SelectedStock to set
     */
    public void setSelectedStock(Stock SelectedStock) {
        this.SelectedStock = SelectedStock;
    }

    /**
     * @return the SelectedStockId
     */
    public Long getSelectedStockId() {
        return SelectedStockId;
    }

    /**
     * @param SelectedStockId the SelectedStockId to set
     */
    public void setSelectedStockId(Long SelectedStockId) {
        this.SelectedStockId = SelectedStockId;
    }

    public static void deleteZeroQtyStock() {
        String sql = "DELETE FROM stock WHERE currentqty=0";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.executeUpdate();
        } catch (SQLException se) {
            System.err.println("deleteZeroQtyStock:" + se.getMessage());
        }
    }

    public String getExpiryListString(Date aExpiryDate) {
        String dateString = "";
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
        try {
            dateString = "(" + sdfr.format(aExpiryDate) + ")";
        } catch (Exception ex) {
            dateString = "";
        }
        return dateString;
    }

    public void initClearStock(Stock aStock, List<Stock> aStockList, List<Stock> aStockListSummary) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            try {
                if (aStock != null) {
                    this.clearStock(aStock);
                }
            } catch (NullPointerException npe) {
            }
            try {
                if (null != aStockList) {
                    aStockList.clear();
                }
            } catch (NullPointerException npe) {
            }
            try {
                if (null != aStockListSummary) {
                    aStockListSummary.clear();
                }
            } catch (NullPointerException npe) {
            }
        }
    }

    /**
     * @return the StocksList
     */
    public List<Stock> getStocksList() {
        return StocksList;
    }

    /**
     * @param StocksList the StocksList to set
     */
    public void setStocksList(List<Stock> StocksList) {
        this.StocksList = StocksList;
    }

    /**
     * @return the StocksSummary
     */
    public List<Stock> getStocksSummary() {
        return StocksSummary;
    }

    /**
     * @param StocksSummary the StocksSummary to set
     */
    public void setStocksSummary(List<Stock> StocksSummary) {
        this.StocksSummary = StocksSummary;
    }

    /**
     * @return the BatchList
     */
    public List<Stock> getBatchList() {
        return BatchList;
    }

    /**
     * @param BatchList the BatchList to set
     */
    public void setBatchList(List<Stock> BatchList) {
        this.BatchList = BatchList;
    }

    /**
     * @return the SpecificList
     */
    public List<Stock> getSpecificList() {
        return SpecificList;
    }

    /**
     * @param SpecificList the SpecificList to set
     */
    public void setSpecificList(List<Stock> SpecificList) {
        this.SpecificList = SpecificList;
    }

    /**
     * @return the StockTotalCostPrice
     */
    public double getStockTotalCostPrice() {
        return StockTotalCostPrice;
    }

    /**
     * @param StockTotalCostPrice the StockTotalCostPrice to set
     */
    public void setStockTotalCostPrice(double StockTotalCostPrice) {
        this.StockTotalCostPrice = StockTotalCostPrice;
    }
}
