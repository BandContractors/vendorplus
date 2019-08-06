package beans;

import connections.DBConnection;
import entities.AccCoa;
import entities.Category;
import entities.Stock;
import entities.TransProductionItem;
import entities.Item;
import entities.ItemProductionMap;
import entities.SubCategory;
import entities.TransProduction;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
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
public class TransProductionItemBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<TransProductionItem> TransProductionItems;
    private String ActionMessage = null;
    private TransProductionItem SelectedTransProductionItem = null;
    private int SelectedTransactionItemId;
    private String SearchTransProductionItem = "";
    private List<TransProductionItem> ActiveTransProductionItems = new ArrayList<TransProductionItem>();
    List<TransProductionItem> ReportTransProductionItem = new ArrayList<TransProductionItem>();
    private String ItemString = "";
    private AccCoa SelectedAccCoa = null;
    private int total_items;
    private List<Integer> total_items_list;
    private List<TransProductionItem> ActiveTransProductionItemsChild = new ArrayList<TransProductionItem>();
    private List<Category> CategoryList;
    private List<SubCategory> SubCategoryList;
    private List<Item> ItemList;
    private String CategoryName;
    private String SubCategoryName;
    private List<TransProductionItem> atransProItemsList = new ArrayList<>();

    public TransProductionItem getTransProductionItemById(long aTransProductionItemId) {
        String sql = "{call sp_search_trans_production_item_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransProductionItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getTransProductionItemFromResultset(rs);
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
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

    public List<TransProductionItem> getTransProductionItemsByTransProductionId(long aTransProductionId) {
        String sql;
        sql = "{call sp_search_trans_production_item_by_trans_production_id(?)}";
        ResultSet rs = null;
        setTransProductionItems(new ArrayList<TransProductionItem>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransProductionId);
            rs = ps.executeQuery();
            TransProductionItem tpi = null;
            while (rs.next()) {
                tpi = this.getTransProductionItemFromResultset(rs);
                this.updateLookUpsUI(tpi);
                getTransProductionItems().add(tpi);
            }
        } catch (SQLException se) {
            System.err.println("getTransProductionItemsByTransProductionId:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return getTransProductionItems();
    }

    public void updateLookUpsUI(TransProductionItem aTransProductionItem) {
        try {
            Item item = null;
            if (null == aTransProductionItem) {
                //do nothing
            } else {
                item = new ItemBean().getItem(aTransProductionItem.getInputItemId());
                aTransProductionItem.setInputItemName(item.getDescription());
            }
        } catch (Exception e) {
            System.out.println("updateLookUpsUI:" + e.getMessage());
        }
    }

    public TransProductionItem getTransProductionItemFromResultset(ResultSet aResultSet) {
        try {
            TransProductionItem transProductionItem = new TransProductionItem();
            transProductionItem.setTransProductionItemId(aResultSet.getLong("trans_production_item_id"));
            transProductionItem.setTransProductionId(aResultSet.getLong("transaction_id"));

            try {
                transProductionItem.setInputItemId(aResultSet.getLong("input_item_id"));
            } catch (NullPointerException npe) {
                transProductionItem.setInputItemId(0);
            }
            try {
                transProductionItem.setInputQty(aResultSet.getDouble("input_qty"));
            } catch (NullPointerException npe) {
                transProductionItem.setInputQty(0);
            }
            try {
                transProductionItem.setInputUnitCost(aResultSet.getDouble("input_unit_cost"));
            } catch (NullPointerException npe) {
                transProductionItem.setInputUnitCost(0);
            }
            try {
                transProductionItem.setBatchno(aResultSet.getString("batchno"));
            } catch (NullPointerException npe) {
                transProductionItem.setBatchno("");
            }
            try {
                transProductionItem.setCodeSpecific(aResultSet.getString("code_specific"));
            } catch (NullPointerException npe) {
                transProductionItem.setCodeSpecific("");
            }
            try {
                transProductionItem.setDescSpecific(aResultSet.getString("desc_specific"));
            } catch (NullPointerException npe) {
                transProductionItem.setDescSpecific("");
            }
            try {
                transProductionItem.setDescMore(aResultSet.getString("desc_more"));
            } catch (NullPointerException npe) {
                transProductionItem.setDescMore("");
            }

            return transProductionItem;

        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
        }
    }

    public void saveTransProductionItemsCEC(long aTransProductionId, double aOutputQty, int aStoreId, List<ItemProductionMap> aActiveTransItems) {
        try {
            List<ItemProductionMap> ati = aActiveTransItems;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            while (ListItemIndex < ListItemNo) {
                this.saveTransProductionItems(aTransProductionId, aOutputQty, aStoreId, ati.get(ListItemIndex));
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void saveTransProductionItems(long aTransProductionID, double aOutputQty, int aStoreId, ItemProductionMap aItemProductionMap) {
        String sql = null;
        String msg = "";
        long InsertedTransId = 0;
        double calInputQty = 0;
        TransactionTypeBean TransTypeBean = new TransactionTypeBean();
        StockBean StkBean = new StockBean();
        TransProductionItem aTransProductionItem = new TransProductionItem();

        if (1 == 2) {
        } else {
            if (aTransProductionItem.getTransProductionItemId() == 0) {
                sql = "{call sp_insert_trans_production_item(?,?,?,?,?,?,?,?)}";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {

                if (aTransProductionItem.getTransProductionItemId() == 0) {
                    //clean batch
                    if (aTransProductionItem.getBatchno() == null) {
                        aTransProductionItem.setBatchno("");
                    }
                    cs.setLong("in_transaction_id", aTransProductionID);
                    cs.setLong("in_input_item_id", aItemProductionMap.getInputItemId());
                    if (aOutputQty >= 1) {
                        calInputQty = aItemProductionMap.getInputQty() * aOutputQty;
                        cs.setDouble("in_input_qty", calInputQty);
                    } else {
                        calInputQty = aItemProductionMap.getInputQty();
                        cs.setDouble("in_input_qty", calInputQty);
                    }
                    try {
                        cs.setDouble("in_input_unit_cost", 0);
                    } catch (NullPointerException npe) {
                        cs.setDouble("in_input_unit_cost", 0);
                    }
                    try {
                        cs.setString("in_batchno", aItemProductionMap.getBatchno());
                    } catch (NullPointerException npe) {
                        cs.setString("in_batchno", "");
                    }

                    try {
                        cs.setString("in_code_specific", aItemProductionMap.getCodeSpecific());
                    } catch (NullPointerException npe) {
                        cs.setString("in_code_specific", "");
                    }
                    try {
                        cs.setString("in_desc_specific", aItemProductionMap.getDescSpecific());
                    } catch (NullPointerException npe) {
                        cs.setString("in_desc_specific", "");
                    }
                    try {
                        cs.setString("in_desc_more", "");
                    } catch (NullPointerException npe) {
                        cs.setString("in_desc_more", "");
                    }
                    //save
                    cs.executeUpdate();
                    //System.out.println("Added Pro Item");

                    //update stock
                    double FromUnitCost = 0;

                    Stock stock = new Stock();
                    int i = 0;
                    stock.setStoreId(aStoreId);
                    stock.setItemId(aItemProductionMap.getInputItemId());
                    stock.setBatchno(aItemProductionMap.getBatchno());
                    stock.setCodeSpecific(aItemProductionMap.getCodeSpecific());
                    stock.setDescSpecific(aItemProductionMap.getDescSpecific());
                    i = new StockBean().subtractStock(stock, calInputQty);
                    try {
                        FromUnitCost = new StockBean().getStock(stock.getStoreId(), stock.getItemId(), stock.getBatchno(), stock.getCodeSpecific(), stock.getDescSpecific()).getUnitCost();
                    } catch (NullPointerException npe) {

                    }
                    TransProductionBean tpb = new TransProductionBean();

                    TransTypeBean = null;
                    StkBean = null;

                } else if (aTransProductionItem.getTransProductionId() > 0) {
                    //do nothing; this is for edit
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("TransProduction NOT saved!"));
            }

        }
    }

    public void saveTransProductionItemsCEC(TransProduction aTransProduction) {
        try {
            for (Iterator<ItemProductionMap> it = this.getItemProductionMaps(aTransProduction.getOutputItemId()).iterator(); it.hasNext();) {
                ItemProductionMap itmc = it.next();
                TransProductionItem aTransProductionItem = new TransProductionItem();
                aTransProductionItem.setInputItemId(itmc.getInputItemId());
                aTransProductionItem.setInputQty(itmc.getInputQty());
                this.atransProItemsList.add(aTransProductionItem);
                this.saveTransProItemsCEC(aTransProduction, aTransProductionItem);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void saveTransProItemsCEC(TransProduction aTransProduction, TransProductionItem aTransProductionItem) {
        String sql = null;
        String msg = "";

        TransactionTypeBean TransTypeBean = new TransactionTypeBean();
        StockBean StkBean = new StockBean();

        if (1 == 2) {
        } else {
            if (aTransProductionItem.getTransProductionItemId() == 0) {
                sql = "{call sp_insert_trans_production_item(?,?,?,?,?,?,?,?)}";
            } else if (aTransProductionItem.getTransProductionItemId() > 0) {
                sql = "{call sp_insert_trans_production_item(?,?,?,?,?,?,?,?,?)}";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {

                if (aTransProductionItem.getTransProductionItemId() == 0) {
                    //clean batch
                    if (aTransProductionItem.getBatchno() == null) {
                        aTransProductionItem.setBatchno("");
                    }
                    cs.setLong("in_transaction_id", aTransProductionItem.getTransProductionItemId());
                    cs.setLong("in_input_item_id", aTransProductionItem.getInputItemId());
                    cs.setDouble("in_input_qty", aTransProductionItem.getInputQty());
                    cs.setDouble("in_input_unit_cost", aTransProductionItem.getInputUnitCost());
                    cs.setString("in_batchno", aTransProductionItem.getBatchno());
                    try {
                        cs.setString("in_code_specific", aTransProductionItem.getCodeSpecific());
                    } catch (NullPointerException npe) {
                        cs.setString("in_code_specific", "");
                    }
                    try {
                        cs.setString("in_desc_specific", aTransProductionItem.getDescSpecific());
                    } catch (NullPointerException npe) {
                        cs.setString("in_desc_specific", "");
                    }
                    try {
                        cs.setString("in_desc_more", aTransProductionItem.getDescMore());
                    } catch (NullPointerException npe) {
                        cs.setString("in_desc_more", "");
                    }
                    //save
                    cs.executeUpdate();
                    System.out.println("Added Pro Item");
                    //repeat for the unpacked ones

                    //update stock
                    TransTypeBean = null;
                    StkBean = null;

                } else if (aTransProductionItem.getTransProductionItemId() > 0) {
                    //do nothing; this is for edit
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
//                this.setActionMessage("TransItem NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("TransItem NOT saved!"));
            }
        }
    }

    public List<ItemProductionMap> getItemProductionMaps(long aOutputItemId) {
        List<ItemProductionMap> ItemProductionMaps = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM item_combination WHERE output_item_id=" + aOutputItemId + "";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                ItemProductionMap ic = new ItemProductionMap();
                ItemProductionMapBean icb = new ItemProductionMapBean();
                icb.setItemProductionMapFromResultset(ic, rs);
                ItemProductionMaps.add(ic);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return ItemProductionMaps;
    }

    /**
     * @return the SelectedAccCoa
     */
    public AccCoa getSelectedAccCoa() {
        return SelectedAccCoa;
    }

    /**
     * @param SelectedAccCoa the SelectedAccCoa to set
     */
    public void setSelectedAccCoa(AccCoa SelectedAccCoa) {
        this.SelectedAccCoa = SelectedAccCoa;
    }

    /**
     * @return the total_items
     */
    public int getTotal_items() {
        return total_items;
    }

    /**
     * @param total_items the total_items to set
     */
    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }

    /**
     * @return the total_items_list
     */
    public List<Integer> getTotal_items_list() {
        return total_items_list;
    }

    /**
     * @param total_items_list the total_items_list to set
     */
    public void setTotal_items_list(List<Integer> total_items_list) {
        this.total_items_list = total_items_list;
    }

    /**
     * @return the ActiveTransProductionItemsChild
     */
    public List<TransProductionItem> getActiveTransProductionItemsChild() {
        return ActiveTransProductionItemsChild;
    }

    /**
     * @param ActiveTransProductionItemsChild the
     * ActiveTransProductionItemsChild to set
     */
    public void setActiveTransProductionItemsChild(List<TransProductionItem> ActiveTransProductionItemsChild) {
        this.ActiveTransProductionItemsChild = ActiveTransProductionItemsChild;
    }

    /**
     * @return the TransProductionItems
     */
    public List<TransProductionItem> getTransProductionItems() {
        return TransProductionItems;
    }

    /**
     * @param TransProductionItems the TransProductionItems to set
     */
    public void setTransProductionItems(List<TransProductionItem> TransProductionItems) {
        this.TransProductionItems = TransProductionItems;
    }

    /**
     * @return the CategoryList
     */
    public List<Category> getCategoryList() {
        return CategoryList;
    }

    /**
     * @param CategoryList the CategoryList to set
     */
    public void setCategoryList(List<Category> CategoryList) {
        this.CategoryList = CategoryList;
    }

    /**
     * @return the ItemList
     */
    public List<Item> getItemList() {
        return ItemList;
    }

    /**
     * @param ItemList the ItemList to set
     */
    public void setItemList(List<Item> ItemList) {
        this.ItemList = ItemList;
    }

    /**
     * @return the SubCategoryList
     */
    public List<SubCategory> getSubCategoryList() {
        return SubCategoryList;
    }

    /**
     * @param SubCategoryList the SubCategoryList to set
     */
    public void setSubCategoryList(List<SubCategory> SubCategoryList) {
        this.SubCategoryList = SubCategoryList;
    }

    /**
     * @return the CategoryName
     */
    public String getCategoryName() {
        return CategoryName;
    }

    /**
     * @param CategoryName the CategoryName to set
     */
    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    /**
     * @return the SubCategoryName
     */
    public String getSubCategoryName() {
        return SubCategoryName;
    }

    /**
     * @param SubCategoryName the SubCategoryName to set
     */
    public void setSubCategoryName(String SubCategoryName) {
        this.SubCategoryName = SubCategoryName;
    }

    /**
     * @return the atransProItemsList
     */
    public List<TransProductionItem> getAtransProItemsList() {
        return atransProItemsList;
    }

    /**
     * @param atransProItemsList the atransProItemsList to set
     */
    public void setAtransProItemsList(List<TransProductionItem> atransProItemsList) {
        this.atransProItemsList = atransProItemsList;
    }

    /**
     * @return the itemCombinationList
     */
}
