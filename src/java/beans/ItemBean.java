package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.Item;
import entities.Location;
import entities.Stock;
import entities.UserDetail;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import utilities.CustomValidator;

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
public class ItemBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Item> Items;
    private String ActionMessage = null;
    private Item SelectedItem = null;
    private Item SelectedItemX = null;
    private long SelectedItemId;
    private String SearchItemDesc = "";
    private List<Item> ItemObjectList;
    private String TypedItemCode;
    List<Item> ReportItems = new ArrayList<Item>();
    List<Item> ReportItemsSummary = new ArrayList<Item>();
    private List<Item> ItemsList;
    private List<Item> ItemsSummary;
    private Item ItemObj;
    private List<Location> LocationList;
    private List<Stock> StockList;
    private Item ParentItem;
    private Part file;
    private List<Item> producedItemList;

    public void updateItemObjName(Part aFile, Item aItem) {
        try {
            if (aFile.getName().length() > 0) {
                aItem.setItemImgUrl(aItem.getItemId() + ".png");
            }
        } catch (Exception e) {

        }
    }

    public void handleFileUpload(Item aItem) {
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, new File(new GeneralUserSetting().getITEM_IMAGE_LOCAL_LOCATION(), Long.toString(aItem.getItemId()) + ".png").toPath());
        } catch (IOException e) {
            // Show faces message?
        }
    }

    public void ItemBarCodeListener(AjaxBehaviorEvent event) {
        System.out.println("OkaY");
    }

    public void saveItem() {
        String msg = "";
        msg = this.validateItem(this.ItemObj);
        if (msg.length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            try {
                if (this.saveValidatedItem(this.ItemObj) == 1) {
                    this.setActionMessage("Saved Successfully");
                    this.clearItem();
                    this.refreshStockLocation(0);
                } else {
                    this.setActionMessage("Item NOT saved");
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Item NOT saved!"));
                }
            } catch (Exception se) {
                System.err.println(se.getMessage());
                this.setActionMessage("Item NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Item NOT saved!"));
            }
        }
    }

    public void saveItem(Item aItem) {
        String msg = "";
        msg = this.validateItem(aItem);
        if (msg.length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            try {
                if (this.saveValidatedItem(aItem) == 1) {
                    this.setActionMessage("Saved Successfully");
                    this.clearItem(aItem);
                    this.refreshStockLocation(0);
                } else {
                    this.setActionMessage("Item NOT saved");
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Item NOT saved!"));
                }
            } catch (Exception se) {
                System.err.println(se.getMessage());
                this.setActionMessage("Item NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Item NOT saved!"));
            }
        }
    }

    public String validateItem(Item aItem) {
        String msg = "";
        String sql2 = null;
        sql2 = "SELECT * FROM item WHERE (item_code!='' AND item_code='" + aItem.getItemCode() + "') or description='" + aItem.getDescription() + "'";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        if (aItem.getItemId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
        } else if (aItem.getItemId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
        } else if (aItem.getCategoryId() == 0) {
            msg = "Select a valid Category please";
        } else if (aItem.getItemType().length() <= 0) {
            msg = "Select a valid Item Type please";
        } else if (aItem.getUnitId() == 0) {
            msg = "Select a valid Unit please";
        } else if (new CustomValidator().TextSize(aItem.getDescription(), 1, 100).equals("FAIL")) {
            msg = "Enter Item Description!";
        } else if (new CustomValidator().TextSize(aItem.getIsSuspended(), 2, 3).equals("FAIL")) {
            msg = "Select value for Is Suspended!";
        } else if (new CustomValidator().TextSize(aItem.getVatRated(), 2, 10).equals("FAIL")) {
            msg = "Select value for Vat Rated!";
        } else if (aItem.getUnitCostPrice() > aItem.getUnitRetailsalePrice()) {
            msg = "Cost Price cannot be greater than Retailsale Price!";
        } else if (aItem.getUnitCostPrice() > aItem.getUnitWholesalePrice()) {
            msg = "Cost Price cannot be greater than Wholesale Price!";
        } else if ((new CustomValidator().CheckRecords(sql2) > 0 && aItem.getItemId() == 0) || (new CustomValidator().CheckRecords(sql2) > 0 && new CustomValidator().CheckRecords(sql2) != 1 && aItem.getItemId() > 0)) {
            msg = "Item Code OR Description already exists!";
        } else if (aItem.getDisplay_alias_name() == 1 && aItem.getAlias_name().length() == 0) {
            msg = "Please specify the Item Alias Name...";
        } else {
            msg = "";
        }
        return msg;
    }

    public int saveValidatedItem(Item aItem) {
        int save_status = 0;
        String sql = null;
        if (aItem.getItemId() == 0) {
            sql = "{call sp_insert_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (aItem.getItemId() > 0) {
            sql = "{call sp_update_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (aItem.getItemId() > 0) {
                cs.setLong("in_item_id", aItem.getItemId());
            }
            cs.setString("in_item_code", aItem.getItemCode());
            cs.setString("in_description", aItem.getDescription());
            cs.setInt("in_category_id", aItem.getCategoryId());
            cs.setInt("in_sub_category_id", aItem.getSubCategoryId());
            cs.setInt("in_unit_id", aItem.getUnitId());
            cs.setInt("in_reorder_level", aItem.getReorderLevel());
            cs.setDouble("in_unit_cost_price", aItem.getUnitCostPrice());
            cs.setDouble("in_unit_retailsale_price", aItem.getUnitRetailsalePrice());
            cs.setDouble("in_unit_wholesale_price", aItem.getUnitWholesalePrice());
            cs.setString("in_is_suspended", aItem.getIsSuspended());
            cs.setString("in_vat_rated", aItem.getVatRated());
            cs.setString("in_item_img_url", aItem.getItemImgUrl());
            cs.setString("in_item_type", aItem.getItemType());
            try {
                cs.setString("in_currency_code", aItem.getCurrencyCode());
            } catch (NullPointerException npe) {
                cs.setString("in_currency_code", "");
            }
            try {
                cs.setInt("in_is_general", aItem.getIsGeneral());
            } catch (NullPointerException npe) {
                cs.setInt("in_is_general", 0);
            }
            try {
                cs.setString("in_asset_type", aItem.getAssetType());
            } catch (NullPointerException npe) {
                cs.setString("in_asset_type", "");
            }
            try {
                cs.setInt("in_is_buy", aItem.getIsBuy());
            } catch (NullPointerException npe) {
                cs.setInt("in_is_buy", 0);
            }
            try {
                cs.setInt("in_is_sale", aItem.getIsSale());
            } catch (NullPointerException npe) {
                cs.setInt("in_is_sale", 0);
            }
            try {
                cs.setInt("in_is_track", aItem.getIsTrack());
            } catch (NullPointerException npe) {
                cs.setInt("in_is_track", 0);
            }
            try {
                cs.setInt("in_is_asset", aItem.getIsAsset());
            } catch (NullPointerException npe) {
                cs.setInt("in_is_asset", 0);
            }
            try {
                cs.setString("in_asset_account_code", aItem.getAssetAccountCode());
            } catch (NullPointerException npe) {
                cs.setString("in_asset_account_code", "");
            }
            try {
                cs.setString("in_expense_account_code", aItem.getExpenseAccountCode());
            } catch (NullPointerException npe) {
                cs.setString("in_expense_account_code", "");
            }
            try {
                cs.setInt("in_is_hire", aItem.getIs_hire());
            } catch (NullPointerException npe) {
                cs.setInt("in_is_hire", 0);
            }
            try {
                cs.setString("in_duration_type", aItem.getDuration_type());
            } catch (NullPointerException npe) {
                cs.setString("in_duration_type", "");
            }
            try {
                cs.setDouble("in_unit_hire_price", aItem.getUnit_hire_price());
            } catch (NullPointerException npe) {
                cs.setDouble("in_unit_hire_price", 0);
            }
            try {
                cs.setDouble("in_unit_special_price", aItem.getUnit_special_price());
            } catch (NullPointerException npe) {
                cs.setDouble("in_unit_special_price", 0);
            }
            try {
                cs.setDouble("in_unit_weight", aItem.getUnit_weight());
            } catch (NullPointerException npe) {
                cs.setDouble("in_unit_weight", 0);
            }
            if (null == aItem.getExpense_type()) {
                cs.setString("in_expense_type", "");
            } else {
                cs.setString("in_expense_type", aItem.getExpense_type());
            }
            if (null == aItem.getAlias_name()) {
                cs.setString("in_alias_name", "");
            } else {
                cs.setString("in_alias_name", aItem.getAlias_name());
            }
            try {
                cs.setInt("in_display_alias_name", aItem.getDisplay_alias_name());
            } catch (NullPointerException npe) {
                cs.setInt("in_display_alias_name", 0);
            }
            try {
                cs.setInt("in_is_free", aItem.getIs_free());
            } catch (NullPointerException npe) {
                cs.setInt("in_is_free", 0);
            }
            try {
                cs.setInt("in_specify_size", aItem.getSpecify_size());
            } catch (NullPointerException npe) {
                cs.setInt("in_specify_size", 0);
            }
            try {
                cs.setInt("in_size_to_specific_name", aItem.getSize_to_specific_name());
            } catch (NullPointerException npe) {
                cs.setInt("in_size_to_specific_name", 0);
            }
            cs.executeUpdate();
            save_status = 1;
        } catch (SQLException se) {
            save_status = 0;
            System.err.println(se.getMessage());
        }
        return save_status;
    }

    public void setItemFromResultset(Item aItem, ResultSet aResultSet) {
        try {
            try {
                aItem.setItemId(aResultSet.getInt("item_id"));
            } catch (NullPointerException npe) {
                aItem.setItemId(0);
            }
            try {
                aItem.setItemCode(aResultSet.getString("item_code"));
            } catch (NullPointerException npe) {
                aItem.setItemCode("");
            }
            try {
                aItem.setDescription(aResultSet.getString("description"));
            } catch (NullPointerException npe) {
                aItem.setDescription("");
            }
            try {
                aItem.setCategoryId(aResultSet.getInt("category_id"));
            } catch (NullPointerException npe) {
                aItem.setCategoryId(0);
            }
            try {
                aItem.setSubCategoryId(aResultSet.getInt("sub_category_id"));
            } catch (NullPointerException npe) {
                aItem.setSubCategoryId(0);
            }
            try {
                aItem.setUnitId(aResultSet.getInt("unit_id"));
            } catch (NullPointerException npe) {
                aItem.setUnitId(0);
            }
            try {
                aItem.setReorderLevel(aResultSet.getInt("reorder_level"));
            } catch (NullPointerException npe) {
                aItem.setReorderLevel(0);
            }
            try {
                aItem.setUnitCostPrice(aResultSet.getDouble("unit_cost_price"));
            } catch (NullPointerException npe) {
                aItem.setUnitCostPrice(0);
            }
            try {
                aItem.setUnitRetailsalePrice(aResultSet.getDouble("unit_retailsale_price"));
            } catch (NullPointerException npe) {
                aItem.setUnitRetailsalePrice(0);
            }
            try {
                aItem.setUnitWholesalePrice(aResultSet.getDouble("unit_wholesale_price"));
            } catch (NullPointerException npe) {
                aItem.setUnitWholesalePrice(0);
            }
            try {
                aItem.setIsSuspended(aResultSet.getString("is_suspended"));
            } catch (NullPointerException npe) {
                aItem.setIsSuspended("");
            }
            try {
                aItem.setVatRated(aResultSet.getString("vat_rated"));
            } catch (NullPointerException npe) {
                aItem.setVatRated("");
            }
            try {
                aItem.setItemImgUrl(aResultSet.getString("item_img_url"));
            } catch (NullPointerException npe) {
                aItem.setItemImgUrl("");
            }
            try {
                aItem.setItemType(aResultSet.getString("item_type"));
            } catch (NullPointerException npe) {
                aItem.setItemType("");
            }
            try {
                aItem.setCurrencyCode(aResultSet.getString("currency_code"));
            } catch (NullPointerException npe) {
                aItem.setCurrencyCode("");
            }
            try {
                aItem.setIsGeneral(aResultSet.getInt("is_general"));
            } catch (NullPointerException npe) {
                aItem.setIsGeneral(0);
            }
            try {
                aItem.setAssetType(aResultSet.getString("asset_type"));
            } catch (NullPointerException npe) {
                aItem.setAssetType("");
            }
            try {
                aItem.setIsBuy(aResultSet.getInt("is_buy"));
            } catch (NullPointerException npe) {
                aItem.setIsBuy(0);
            }
            try {
                aItem.setIsSale(aResultSet.getInt("is_sale"));
            } catch (NullPointerException npe) {
                aItem.setIsSale(0);
            }
            try {
                aItem.setIsTrack(aResultSet.getInt("is_track"));
            } catch (NullPointerException npe) {
                aItem.setIsTrack(0);
            }
            try {
                aItem.setIsAsset(aResultSet.getInt("is_asset"));
            } catch (NullPointerException npe) {
                aItem.setIsAsset(0);
            }
            try {
                aItem.setAssetAccountCode(aResultSet.getString("asset_account_code"));
            } catch (NullPointerException npe) {
                aItem.setAssetAccountCode("");
            }
            try {
                aItem.setExpenseAccountCode(aResultSet.getString("expense_account_code"));
            } catch (NullPointerException npe) {
                aItem.setExpenseAccountCode("");
            }
            try {
                aItem.setIs_hire(aResultSet.getInt("is_hire"));
            } catch (NullPointerException npe) {
                aItem.setIs_hire(0);
            }
            try {
                aItem.setDuration_type(aResultSet.getString("duration_type"));
            } catch (NullPointerException npe) {
                aItem.setDuration_type("");
            }
            try {
                aItem.setUnit_hire_price(aResultSet.getDouble("unit_hire_price"));
            } catch (NullPointerException npe) {
                aItem.setUnit_hire_price(0);
            }
            try {
                aItem.setUnit_special_price(aResultSet.getDouble("unit_special_price"));
            } catch (NullPointerException npe) {
                aItem.setUnit_special_price(0);
            }
            try {
                aItem.setUnit_weight(aResultSet.getDouble("unit_weight"));
            } catch (NullPointerException npe) {
                aItem.setUnit_weight(0);
            }
            try {
                aItem.setExpense_type(aResultSet.getString("expense_type"));
            } catch (NullPointerException npe) {
                aItem.setExpense_type("");
            }
            try {
                aItem.setAlias_name(aResultSet.getString("alias_name"));
            } catch (NullPointerException npe) {
                aItem.setAlias_name("");
            }
            try {
                aItem.setDisplay_alias_name(aResultSet.getInt("display_alias_name"));
            } catch (NullPointerException npe) {
                aItem.setDisplay_alias_name(0);
            }
            try {
                aItem.setIs_free(aResultSet.getInt("is_free"));
            } catch (NullPointerException npe) {
                aItem.setIs_free(0);
            }
            try {
                aItem.setSpecify_size(aResultSet.getInt("specify_size"));
            } catch (NullPointerException npe) {
                aItem.setSpecify_size(0);
            }
            try {
                aItem.setSize_to_specific_name(aResultSet.getInt("size_to_specific_name"));
            } catch (NullPointerException npe) {
                aItem.setSize_to_specific_name(0);
            }
        } catch (SQLException se) {
            System.err.println("setItemFromResultset:" + se.getMessage());
        }
    }

    public void setItemFromResultsetReport(Item aItem, ResultSet aResultSet) {
        try {
            try {
                aItem.setItemId(aResultSet.getInt("item_id"));
            } catch (NullPointerException npe) {
                aItem.setItemId(0);
            }
            try {
                aItem.setItemCode(aResultSet.getString("item_code"));
            } catch (NullPointerException npe) {
                aItem.setItemCode("");
            }
            try {
                aItem.setDescription(aResultSet.getString("description"));
            } catch (NullPointerException npe) {
                aItem.setDescription("");
            }
            try {
                aItem.setCategoryId(aResultSet.getInt("category_id"));
            } catch (NullPointerException npe) {
                aItem.setCategoryId(0);
            }
            try {
                aItem.setCategoryName(aResultSet.getString("category_name"));
            } catch (NullPointerException npe) {
                aItem.setCategoryName("");
            }
            try {
                aItem.setSubCategoryId(aResultSet.getInt("sub_category_id"));
            } catch (NullPointerException npe) {
                aItem.setSubCategoryId(0);
            }
            try {
                aItem.setSubCategoryName(aResultSet.getString("sub_category_name"));
            } catch (NullPointerException npe) {
                aItem.setSubCategoryName("");
            }
            try {
                aItem.setUnitId(aResultSet.getInt("unit_id"));
            } catch (NullPointerException npe) {
                aItem.setUnitId(0);
            }
            try {
                aItem.setUnitSymbol(aResultSet.getString("unit_symbol"));
            } catch (NullPointerException npe) {
                aItem.setUnitSymbol("");
            }
            try {
                aItem.setReorderLevel(aResultSet.getInt("reorder_level"));
            } catch (NullPointerException npe) {
                aItem.setReorderLevel(0);
            }
            try {
                aItem.setUnitCostPrice(aResultSet.getDouble("unit_cost_price"));
            } catch (NullPointerException npe) {
                aItem.setUnitCostPrice(0);
            }
            try {
                aItem.setUnitRetailsalePrice(aResultSet.getDouble("unit_retailsale_price"));
            } catch (NullPointerException npe) {
                aItem.setUnitRetailsalePrice(0);
            }
            try {
                aItem.setUnitWholesalePrice(aResultSet.getDouble("unit_wholesale_price"));
            } catch (NullPointerException npe) {
                aItem.setUnitWholesalePrice(0);
            }
            try {
                aItem.setIsSuspended(aResultSet.getString("is_suspended"));
            } catch (NullPointerException npe) {
                aItem.setIsSuspended("");
            }
            try {
                aItem.setVatRated(aResultSet.getString("vat_rated"));
            } catch (NullPointerException npe) {
                aItem.setVatRated("");
            }
            try {
                aItem.setItemImgUrl(aResultSet.getString("item_img_url"));
            } catch (NullPointerException npe) {
                aItem.setItemImgUrl("");
            }
            try {
                aItem.setItemType(aResultSet.getString("item_type"));
            } catch (NullPointerException npe) {
                aItem.setItemType("");
            }
            try {
                aItem.setCurrencyCode(aResultSet.getString("currency_code"));
            } catch (NullPointerException npe) {
                aItem.setCurrencyCode("");
            }
            try {
                aItem.setIsGeneral(aResultSet.getInt("is_general"));
            } catch (NullPointerException npe) {
                aItem.setIsGeneral(0);
            }
            try {
                aItem.setAssetType(aResultSet.getString("asset_type"));
            } catch (NullPointerException npe) {
                aItem.setAssetType("");
            }
            try {
                aItem.setIsBuy(aResultSet.getInt("is_buy"));
            } catch (NullPointerException npe) {
                aItem.setIsBuy(0);
            }
            try {
                aItem.setIsSale(aResultSet.getInt("is_sale"));
            } catch (NullPointerException npe) {
                aItem.setIsSale(0);
            }
            try {
                aItem.setIsTrack(aResultSet.getInt("is_track"));
            } catch (NullPointerException npe) {
                aItem.setIsTrack(0);
            }
            try {
                aItem.setIsAsset(aResultSet.getInt("is_asset"));
            } catch (NullPointerException npe) {
                aItem.setIsAsset(0);
            }
            try {
                aItem.setAssetAccountCode(aResultSet.getString("asset_account_code"));
            } catch (NullPointerException npe) {
                aItem.setAssetAccountCode("");
            }
            try {
                aItem.setExpenseAccountCode(aResultSet.getString("expense_account_code"));
            } catch (NullPointerException npe) {
                aItem.setExpenseAccountCode("");
            }
            try {
                aItem.setIs_hire(aResultSet.getInt("is_hire"));
            } catch (NullPointerException npe) {
                aItem.setIs_hire(0);
            }
            try {
                aItem.setDuration_type(aResultSet.getString("duration_type"));
            } catch (NullPointerException npe) {
                aItem.setDuration_type("");
            }
            try {
                aItem.setUnit_hire_price(aResultSet.getDouble("unit_hire_price"));
            } catch (NullPointerException npe) {
                aItem.setUnit_hire_price(0);
            }
            try {
                aItem.setUnit_special_price(aResultSet.getDouble("unit_special_price"));
            } catch (NullPointerException npe) {
                aItem.setUnit_special_price(0);
            }
            try {
                aItem.setUnit_weight(aResultSet.getDouble("unit_weight"));
            } catch (NullPointerException npe) {
                aItem.setUnit_weight(0);
            }
            try {
                aItem.setExpense_type(aResultSet.getString("expense_type"));
            } catch (NullPointerException npe) {
                aItem.setExpense_type("");
            }
            try {
                aItem.setAlias_name(aResultSet.getString("alias_name"));
            } catch (NullPointerException npe) {
                aItem.setAlias_name("");
            }
            try {
                aItem.setDisplay_alias_name(aResultSet.getInt("display_alias_name"));
            } catch (NullPointerException npe) {
                aItem.setDisplay_alias_name(0);
            }
            try {
                aItem.setIs_free(aResultSet.getInt("is_free"));
            } catch (NullPointerException npe) {
                aItem.setIs_free(0);
            }
            try {
                aItem.setSpecify_size(aResultSet.getInt("specify_size"));
            } catch (NullPointerException npe) {
                aItem.setSpecify_size(0);
            }
            try {
                aItem.setSize_to_specific_name(aResultSet.getInt("size_to_specific_name"));
            } catch (NullPointerException npe) {
                aItem.setSize_to_specific_name(0);
            }
        } catch (SQLException se) {
            System.err.println("setItemFromResultsetReport:" + se.getMessage());
        }
    }

    public Item getItem(long ItemId) {
        String sql = "{call sp_search_item_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, ItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                return item;
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

    public void setItem(long aItemId, Item aItem) {
        String sql = "{call sp_search_item_by_id(?)}";
        ResultSet rs = null;
        if (null == aItem) {
            aItem = new Item();
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                this.setItemFromResultset(aItem, rs);
            }
        } catch (Exception e) {
            System.err.println("setItem:" + e.getMessage());
        }
    }

    public Item findItem(long ItemId) {
        String sql = "{call sp_search_item_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, ItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                return item;
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

    public Item findItemByCode(String ItemCode) {
        String sql = "{call sp_search_item_by_code(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, ItemCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                return item;
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

    public Item findItemByCodeActive(String ItemCode) {
        if (ItemCode.trim().isEmpty()) {
            return null;
        }
        String sql = "{call sp_search_item_active_by_code(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, ItemCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                return item;
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

    public void deleteItem(Item aItem) {
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Delete") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            String sql = "DELETE FROM item WHERE item_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, aItem.getItemId());
                ps.executeUpdate();
                this.setActionMessage("Deleted Successfully!");
                this.clearItem(aItem);
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("Item NOT deleted");
            }
        }
    }

    public void displayItem(Item ItemFrom) {
        try {
            this.ItemObj = this.getItem(ItemFrom.getItemId());
            this.refreshStockLocation(ItemFrom.getItemId());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void clearItem() {
        try {
            if (null == this.ItemObj) {
                //do nothing
            } else {
                this.ItemObj.setItemId(0);
                this.ItemObj.setItemCode("");
                this.ItemObj.setDescription("");
                this.ItemObj.setCategoryId(0);
                this.ItemObj.setSubCategoryId(0);
                this.ItemObj.setUnitId(0);
                this.ItemObj.setReorderLevel(0);
                this.ItemObj.setUnitRetailsalePrice(0);
                this.ItemObj.setUnitWholesalePrice(0);
                this.ItemObj.setIsSuspended("");
                this.ItemObj.setVatRated("");
                this.ItemObj.setItemImgUrl("");
                this.ItemObj.setItemType("");
                this.ItemObj.setCurrencyCode("");
                this.ItemObj.setIsGeneral(0);
                this.ItemObj.setAssetType("");
                this.ItemObj.setIsBuy(0);
                this.ItemObj.setIsSale(0);
                this.ItemObj.setIsTrack(0);
                this.ItemObj.setIsAsset(0);
                this.ItemObj.setAssetAccountCode("");
                this.ItemObj.setExpenseAccountCode("");
                this.ItemObj.setIs_hire(0);
                this.ItemObj.setDuration_type("");
                this.ItemObj.setUnit_hire_price(0);
                this.ItemObj.setUnit_special_price(0);
                this.ItemObj.setUnit_weight(0);
                this.ItemObj.setExpense_type("");
                this.ItemObj.setAlias_name("");
                this.ItemObj.setDisplay_alias_name(0);
                this.ItemObj.setIs_free(0);
                this.ItemObj.setSpecify_size(0);
                this.ItemObj.setSize_to_specific_name(0);
                this.ItemObj.setUnitCostPrice(0);
                this.setSearchItemDesc("");
                this.refreshStockLocation(0);
                this.refreshItemsList(this.getSearchItemDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearItem(Item aItem) {
        try {
            if (aItem != null) {
                aItem.setItemId(0);
                aItem.setItemCode("");
                aItem.setDescription("");
                aItem.setCategoryId(0);
                aItem.setSubCategoryId(0);
                aItem.setUnitId(0);
                aItem.setReorderLevel(0);
                aItem.setUnitCostPrice(0);
                aItem.setUnitRetailsalePrice(0);
                aItem.setUnitWholesalePrice(0);
                aItem.setIsSuspended("");
                aItem.setVatRated("");
                aItem.setItemImgUrl("");
                aItem.setItemType("");
                aItem.setCurrencyCode("");
                aItem.setIsGeneral(0);
                aItem.setAssetType("");
                aItem.setIsBuy(0);
                aItem.setIsSale(0);
                aItem.setIsTrack(0);
                aItem.setIsAsset(0);
                aItem.setAssetAccountCode("");
                aItem.setExpenseAccountCode("");
                aItem.setIs_hire(0);
                aItem.setDuration_type("");
                aItem.setUnit_hire_price(0);
                aItem.setUnit_special_price(0);
                aItem.setUnit_weight(0);
                aItem.setExpense_type("");
                aItem.setAlias_name("");
                aItem.setDisplay_alias_name(0);
                aItem.setIs_free(0);
                aItem.setSpecify_size(0);
                aItem.setSize_to_specific_name(0);
                this.setSearchItemDesc("");
                this.refreshStockLocation(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initClearItem(Item aItem, List<Item> aItemList) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            try {
                if (aItem != null) {
                    this.clearItem(aItem);
                }
            } catch (NullPointerException npe) {
            }
            try {
                if (aItemList != null) {
                    aItemList.clear();
                }
            } catch (NullPointerException npe) {
            }
        }
    }

    public void initClearItemParent() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            try {
                if (null == this.ParentItem) {
                } else {
                    this.ParentItem = new Item();
                }
            } catch (NullPointerException npe) {
            }
        }
    }

    public void clearSelectedItem() {
        this.clearItem(this.getSelectedItem());
    }

    public List<Item> getItemObjectList(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_by_code_desc(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.updateLookUpsUI(item);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListActive(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_active_by_code_desc(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public void updateLookUpsUI(Item aItem) {
        try {
            //Item item = null;
            if (null == aItem) {
                //do nothing
            } else {
                //unit symbol
                try {
                    aItem.setUnitSymbol(new UnitBean().getUnit(aItem.getUnitId()).getUnitSymbol());
                } catch (NullPointerException npe) {
                    aItem.setUnitSymbol("");
                }
                //category
                try {
                    aItem.setCategoryName(new CategoryBean().getCategory(aItem.getCategoryId()).getCategoryName());
                } catch (NullPointerException npe) {
                    aItem.setCategoryName("");
                }
                //location
                try {
                    if (aItem.getIsTrack() == 1) {
                        LocationBean lb = new LocationBean();
                        aItem.setLocationName(lb.getLocationsString(lb.getLocationsByItem(aItem.getItemId())));
                    }
                } catch (NullPointerException npe) {
                    aItem.setLocationName("");
                }
            }
        } catch (Exception e) {
            System.out.println("updateLookUpsUI-Item:" + e.getMessage());
        }
    }

    public List<Item> getItemObjectListForSale(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_sale(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.updateLookUpsUI(item);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForHire(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_hire(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForStockDispose(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_stock_dispose(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForConsumption(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_consumption(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForTransfer(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_transfer(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForUnpack(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_unpack(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForPurchase(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_purchase(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForReceiveGoods(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_receive_goods(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForReceiveExpenses(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_receive_expenses(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForReceiveAssets(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_receive_assets(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForPurchaseExpense(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_purchase_expense(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForPurchaseGoods(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_purchase_goods(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getItemObjectListForAssetFixed(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_asset_fixed(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    /**
     * @param ItemObjectList the ItemObjectList to set
     */
    public void setItemObjectList(List<Item> ItemObjectList) {
        this.ItemObjectList = ItemObjectList;
    }

    /**
     * @param aNameOrCode
     * @return the Items
     */
    public List<Item> getItems(String aNameOrCode) {
        String sql = "{call sp_search_item_by_code_desc(?)}";
        ResultSet rs = null;
        Items = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aNameOrCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                Items.add(item);
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
        return Items;
    }

    public List<Item> getItemObjectListForQuickOrder(int aCategoryId, int aSubCategoryId) {
        String sql = "";
        String aWhereSql = "";
        if (aCategoryId > 0) {
            aWhereSql = aWhereSql + " AND category_id=" + aCategoryId;
        }
        if (aSubCategoryId > 0) {
            aWhereSql = aWhereSql + " AND sub_category_id=" + aSubCategoryId;
        }
        if (aSubCategoryId < 0) {
            aWhereSql = aWhereSql + " AND sub_category_id IS NULL";
        }
        sql = "SELECT * FROM item WHERE is_suspended='No' AND is_sale=1 AND is_asset=0 " + aWhereSql
                + " ORDER BY description ASC";
        //System.out.println("SQL:" + sql);
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setInt(1, aCategoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.updateLookUpsUI(item);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public void refreshItemsList(String aNameOrCode) {
        this.ItemsList = new ArrayList<>();
        if (aNameOrCode.replace(" ", "").length() <= 0) {
            this.ItemsList.clear();
        } else {
            //Asset:is_asset=1 AND is_sale=0
            //Stock:is_sale=1 AND is_asset=0 for stock;
            //Expense:is_sale=0 AND is_asset=0
            String ItemPurpose = "";
            int IsAsset = 0;
            int IsSale = 0;
            ItemPurpose = new GeneralUserSetting().getCurrentItemPurpose();
            if (ItemPurpose.equals("Asset")) {
                IsAsset = 1;
                IsSale = 0;
            }
            if (ItemPurpose.equals("Stock")) {
                IsAsset = 0;
                IsSale = 1;
            }
            if (ItemPurpose.equals("Expense")) {
                IsAsset = 0;
                IsSale = 0;
            }
            String sql = "{call sp_search_item_by_code_desc_purpose(?,?,?)}";
            ResultSet rs = null;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, aNameOrCode);
                ps.setInt(2, IsAsset);
                ps.setInt(3, IsSale);
                rs = ps.executeQuery();
                Item item = null;
                while (rs.next()) {
                    item = new Item();
                    this.setItemFromResultset(item, rs);
                    this.ItemsList.add(item);
                }
            } catch (SQLException se) {
                System.err.println("refreshItemsList:" + se.getMessage());
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
    }

    public List<Item> getReportItems(Item aItem, boolean RETRIEVE_REPORT) {
        String sql = "{call sp_report_item(?,?,?)}";
        ResultSet rs = null;
        this.ReportItems.clear();
        if (aItem != null && RETRIEVE_REPORT == true) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aItem.getCategoryId());
                try {
                    ps.setInt(2, aItem.getSubCategoryId());
                } catch (NullPointerException npe) {
                    ps.setInt(2, 0);
                }
                ps.setString(3, aItem.getIsSuspended());
                rs = ps.executeQuery();
                while (rs.next()) {
                    Item item = new Item();
                    aItem.setItemId(rs.getLong("item_id"));
                    aItem.setItemCode(rs.getString("item_code"));
                    aItem.setDescription(rs.getString("description"));
                    aItem.setCategoryId(rs.getInt("category_id"));
                    aItem.setCategoryName(rs.getString("category_name"));
                    try {
                        aItem.setSubCategoryId(rs.getInt("sub_category_id"));
                        aItem.setSubCategoryName(rs.getString("sub_category_name"));
                    } catch (NullPointerException npe) {
                        aItem.setSubCategoryId(0);
                        aItem.setSubCategoryName("");
                    }
                    aItem.setUnitId(rs.getInt("unit_id"));
                    aItem.setUnitSymbol(rs.getString("unit_symbol"));
                    aItem.setReorderLevel(rs.getInt("reorder_level"));
                    aItem.setUnitCostPrice(rs.getDouble("unit_cost_price"));
                    aItem.setUnitRetailsalePrice(rs.getDouble("unit_retailsale_price"));
                    aItem.setUnitWholesalePrice(rs.getDouble("unit_wholesale_price"));
                    aItem.setIsSuspended(rs.getString("is_suspended"));
                    aItem.setVatRated(rs.getString("vat_rated"));
                    try {
                        aItem.setItemImgUrl(rs.getString("item_img_url"));
                    } catch (NullPointerException npe) {
                        aItem.setItemImgUrl("");
                    }
                    aItem.setItemType(rs.getString("item_type"));
                    this.ReportItems.add(item);
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
        } else {
            this.ReportItems.clear();
        }
        return this.ReportItems;
    }

    public long getReportItemsCount() {
        return this.ReportItems.size();
    }

    public List<Item> getReportItemsSummary(Item aItem, boolean RETRIEVE_REPORT) {
        String sql = "{call sp_report_item_summary(?,?,?)}";
        ResultSet rs = null;
        this.ReportItemsSummary.clear();
        if (aItem != null && RETRIEVE_REPORT == true) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aItem.getCategoryId());
                try {
                    ps.setInt(2, aItem.getSubCategoryId());
                } catch (NullPointerException npe) {
                    ps.setInt(2, 0);
                }
                ps.setString(3, aItem.getIsSuspended());
                rs = ps.executeQuery();
                while (rs.next()) {
                    Item item = new Item();
                    aItem.setCategoryId(rs.getInt("category_id"));
                    aItem.setCategoryName(rs.getString("category_name"));
                    try {
                        aItem.setSubCategoryId(rs.getInt("sub_category_id"));
                        aItem.setSubCategoryName(rs.getString("sub_category_name"));
                    } catch (NullPointerException npe) {
                        aItem.setSubCategoryId(0);
                        aItem.setSubCategoryName("");
                    }
                    try {
                        aItem.setCountItems(rs.getInt("count_items"));
                    } catch (NullPointerException npe) {
                        aItem.setCountItems(0);
                    }

                    this.ReportItemsSummary.add(item);
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
        } else {
            this.ReportItemsSummary.clear();
        }
        return this.ReportItemsSummary;
    }

    public void reportItemDetailStock(String aItemType, int aCategoryId, int aSubCategoryId, String aCurrency, String aIsSuspended, int aIsGeneral) {
        String sql = "SELECT * FROM view_item_detail_stock WHERE 1=1";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        this.setItemsList(new ArrayList<>());
        this.setItemsSummary(new ArrayList<>());
        if (aItemType.length() > 0) {
            wheresql = wheresql + " AND item_type='" + aItemType + "'";
        }
        if (aCategoryId > 0) {
            wheresql = wheresql + " AND category_id=" + aCategoryId;
        }
        if (aSubCategoryId > 0) {
            wheresql = wheresql + " AND sub_category_id=" + aSubCategoryId;
        }
        if (aCurrency.length() > 0) {
            wheresql = wheresql + " AND currency_code='" + aCurrency + "'";
        }
        if (aIsSuspended.length() > 0) {
            wheresql = wheresql + " AND is_suspended='" + aIsSuspended + "'";
        }
        if (aIsGeneral == 10) {
            wheresql = wheresql + " AND is_general=0";
        }
        if (aIsGeneral == 11) {
            wheresql = wheresql + " AND is_general=1";
        }
        ordersql = " ORDER BY description ASC";
        sql = sql + wheresql + ordersql;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Item item = null;
            while (rs.next()) {
                item = new Item();
                this.setItemFromResultsetReport(item, rs);
                this.getItemsList().add(item);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportItemDetailAsset(String aItemType, int aCategoryId, int aSubCategoryId, String aCurrency, String aIsSuspended, int aIsGeneral) {
        String sql = "SELECT * FROM view_item_detail_asset WHERE 1=1";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        this.setItemsList(new ArrayList<>());
        this.setItemsSummary(new ArrayList<>());
        if (aItemType.length() > 0) {
            wheresql = wheresql + " AND item_type='" + aItemType + "'";
        }
        if (aCategoryId > 0) {
            wheresql = wheresql + " AND category_id=" + aCategoryId;
        }
        if (aSubCategoryId > 0) {
            wheresql = wheresql + " AND sub_category_id=" + aSubCategoryId;
        }
        if (aCurrency.length() > 0) {
            wheresql = wheresql + " AND currency_code='" + aCurrency + "'";
        }
        if (aIsSuspended.length() > 0) {
            wheresql = wheresql + " AND is_suspended='" + aIsSuspended + "'";
        }
        if (aIsGeneral == 10) {
            wheresql = wheresql + " AND is_general=0";
        }
        if (aIsGeneral == 11) {
            wheresql = wheresql + " AND is_general=1";
        }
        ordersql = " ORDER BY description ASC";
        sql = sql + wheresql + ordersql;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Item item = null;
            while (rs.next()) {
                item = new Item();
                this.setItemFromResultsetReport(item, rs);
                this.getItemsList().add(item);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportItemDetailExpense(String aItemType, int aCategoryId, int aSubCategoryId, String aCurrency, String aIsSuspended, int aIsGeneral) {
        String sql = "SELECT * FROM view_item_detail_expense WHERE 1=1";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        this.setItemsList(new ArrayList<>());
        this.setItemsSummary(new ArrayList<>());
        if (aItemType.length() > 0) {
            wheresql = wheresql + " AND item_type='" + aItemType + "'";
        }
        if (aCategoryId > 0) {
            wheresql = wheresql + " AND category_id=" + aCategoryId;
        }
        if (aSubCategoryId > 0) {
            wheresql = wheresql + " AND sub_category_id=" + aSubCategoryId;
        }
        if (aCurrency.length() > 0) {
            wheresql = wheresql + " AND currency_code='" + aCurrency + "'";
        }
        if (aIsSuspended.length() > 0) {
            wheresql = wheresql + " AND is_suspended='" + aIsSuspended + "'";
        }
        if (aIsGeneral == 10) {
            wheresql = wheresql + " AND is_general=0";
        }
        if (aIsGeneral == 11) {
            wheresql = wheresql + " AND is_general=1";
        }
        ordersql = " ORDER BY description ASC";
        sql = sql + wheresql + ordersql;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Item item = null;
            while (rs.next()) {
                item = new Item();
                this.setItemFromResultsetReport(item, rs);
                this.getItemsList().add(item);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void initItemObj() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            if (null == this.ItemObj) {
                this.ItemObj = new Item();
            }
            if (null == this.ParentItem) {
            } else {
                this.displayItem(this.ParentItem);
            }
        }
    }

    public void initStockLocation() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            long ItemId = 0;
            try {
                ItemId = this.ItemObj.getItemId();
            } catch (NullPointerException npe) {
            }
            if (ItemId > 0) {
                this.refreshStockLocation(ItemId);
            }
        }
    }

    public void refreshStockLocation(long aItemId) {
        try {
            this.LocationList.clear();
        } catch (NullPointerException npe) {
            this.LocationList = new ArrayList<>();
        }

        try {
            this.StockList.clear();
        } catch (NullPointerException npe) {
            this.StockList = new ArrayList<>();
        }

        if (aItemId > 0) {
            try {
                this.LocationList = new LocationBean().getLocationsByItem(aItemId);
            } catch (NullPointerException npe) {
            }
            try {
                this.StockList = new StockBean().getStocksByItem(aItemId);
            } catch (NullPointerException npe) {
            }
        }
    }

    public long getReportItemsSummaryCount() {
        return this.ReportItemsSummary.size();
    }

    public void openChildItem(String aItemPurpose) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        httpSession.setAttribute("ITEM_PURPOSE", aItemPurpose);

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("width", 600);
        options.put("height", 300);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("scrollable", true);
        options.put("maximizable", true);
        options.put("dynamic", true);
        org.primefaces.PrimeFaces.current().dialog().openDynamic("ItemChild", options, null);
    }

    public void openChildItem(String aItemPurpose, long aItemId) {
        if (new NavigationBean().checkAccessDeniedReturn("8", "View") == 1) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            httpSession.setAttribute("ITEM_PURPOSE", aItemPurpose);
            try {
                if (aItemId > 0) {
                    this.ParentItem = this.getItem(aItemId);
                }// else {
                //    this.ParentItem = new Item();
                //}
            } catch (NullPointerException npe) {
            }
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("modal", true);
            options.put("draggable", false);
            options.put("resizable", false);
            options.put("width", 600);
            options.put("height", 300);
            options.put("contentWidth", "100%");
            options.put("contentHeight", "100%");
            options.put("scrollable", true);
            options.put("maximizable", true);
            options.put("dynamic", true);
            org.primefaces.PrimeFaces.current().dialog().openDynamic("ItemChild", options, null);
        }
    }

    public List<Item> getProductionItemObjectList(String Query) {
        this.setTypedItemCode(Query);
        String sql;
        sql = "{call sp_search_item_for_production(?)}";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                this.setItemFromResultset(item, rs);
                this.updateLookUpsUI(item);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    /**
     * @param Items the Items to set
     */
    public void setItems(List<Item> Items) {
        this.Items = Items;
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
     * @return the SelectedItemId
     */
    public long getSelectedItemId() {
        return SelectedItemId;
    }

    /**
     * @param SelectedItemId the SelectedItemId to set
     */
    public void setSelectedItemId(long SelectedItemId) {
        this.SelectedItemId = SelectedItemId;
    }

    /**
     * @return the SearchItemDesc
     */
    public String getSearchItemDesc() {
        return SearchItemDesc;
    }

    /**
     * @param SearchItemDesc the SearchItemDesc to set
     */
    public void setSearchItemDesc(String SearchItemDesc) {
        this.SearchItemDesc = SearchItemDesc;
    }

    /**
     * @return the SelectedItem
     */
    public Item getSelectedItem() {
        return SelectedItem;
    }

    /**
     * @param SelectedItem the SelectedItem to set
     */
    public void setSelectedItem(Item SelectedItem) {
        this.SelectedItem = SelectedItem;
    }

    /**
     * @return the SelectedItemX
     */
    public Item getSelectedItemX() {
        return SelectedItemX;
    }

    /**
     * @param SelectedItemX the SelectedItemX to set
     */
    public void setSelectedItemX(Item SelectedItemX) {
        this.SelectedItemX = SelectedItemX;
    }

    /**
     * @return the TypedItemCode
     */
    public String getTypedItemCode() {
        return TypedItemCode;
    }

    /**
     * @param TypedItemCode the TypedItemCode to set
     */
    public void setTypedItemCode(String TypedItemCode) {
        this.TypedItemCode = TypedItemCode;
    }

    /**
     * @return the ItemsList
     */
    public List<Item> getItemsList() {
        return ItemsList;
    }

    /**
     * @param ItemsList the ItemsList to set
     */
    public void setItemsList(List<Item> ItemsList) {
        this.ItemsList = ItemsList;
    }

    /**
     * @return the ItemsSummary
     */
    public List<Item> getItemsSummary() {
        return ItemsSummary;
    }

    /**
     * @param ItemsSummary the ItemsSummary to set
     */
    public void setItemsSummary(List<Item> ItemsSummary) {
        this.ItemsSummary = ItemsSummary;
    }

    /**
     * @return the ItemObj
     */
    public Item getItemObj() {
        return ItemObj;
    }

    /**
     * @param ItemObj the ItemObj to set
     */
    public void setItemObj(Item ItemObj) {
        this.ItemObj = ItemObj;
    }

    /**
     * @return the LocationList
     */
    public List<Location> getLocationList() {
        return LocationList;
    }

    /**
     * @param LocationList the LocationList to set
     */
    public void setLocationList(List<Location> LocationList) {
        this.LocationList = LocationList;
    }

    /**
     * @return the StockList
     */
    public List<Stock> getStockList() {
        return StockList;
    }

    /**
     * @param StockList the StockList to set
     */
    public void setStockList(List<Stock> StockList) {
        this.StockList = StockList;
    }

    /**
     * @return the ParentItem
     */
    public Item getParentItem() {
        return ParentItem;
    }

    /**
     * @param ParentItem the ParentItem to set
     */
    public void setParentItem(Item ParentItem) {
        this.ParentItem = ParentItem;
    }

    /**
     * @return the file
     */
    public Part getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(Part file) {
        this.file = file;
    }

    /**
     * @return the producedItemList
     */
    public List<Item> getProducedItemList() {
        return producedItemList;
    }

    /**
     * @param producedItemList the producedItemList to set
     */
    public void setProducedItemList(List<Item> producedItemList) {
        this.producedItemList = producedItemList;
    }
}
