package beans;

import api_tax.efris_bean.StockManage;
import connections.DBConnection;
import entities.CompanySetting;
import entities.Item;
import entities.Item_tax_map;
import entities.TransItem;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "item_tax_mapBean")
@SessionScoped
public class Item_tax_mapBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Item_tax_mapBean.class.getName());

    public void setItem_tax_mapFromResultset(Item_tax_map aItem_tax_map, ResultSet aResultSet) {
        try {
            try {
                aItem_tax_map.setItem_tax_map_id(aResultSet.getLong("item_tax_map_id"));
            } catch (NullPointerException npe) {
                aItem_tax_map.setItem_tax_map_id(0);
            }
            try {
                aItem_tax_map.setItem_id(aResultSet.getLong("item_id"));
            } catch (NullPointerException npe) {
                aItem_tax_map.setItem_id(0);
            }
            try {
                aItem_tax_map.setItem_id_tax(aResultSet.getString("item_id_tax"));
            } catch (NullPointerException npe) {
                aItem_tax_map.setItem_id_tax("");
            }
            try {
                aItem_tax_map.setItem_code_tax(aResultSet.getString("item_code_tax"));
            } catch (NullPointerException npe) {
                aItem_tax_map.setItem_code_tax("");
            }
            try {
                aItem_tax_map.setIs_synced(aResultSet.getInt("is_synced"));
            } catch (NullPointerException npe) {
                aItem_tax_map.setIs_synced(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public Item_tax_map getItem_tax_map(long aItemId) {
        String sql = "SELECT * FROM item_tax_map WHERE item_id=" + aItemId;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                Item_tax_map im = new Item_tax_map();
                this.setItem_tax_mapFromResultset(im, rs);
                return im;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public Item_tax_map getItem_tax_mapByIdTax(String aItemIdTax) {
        String sql = "SELECT * FROM item_tax_map WHERE item_id_tax='" + aItemIdTax + "'";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                Item_tax_map im = new Item_tax_map();
                this.setItem_tax_mapFromResultset(im, rs);
                return im;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public Item_tax_map getItem_tax_mapSynced(long aItemId) {
        String sql = "SELECT * FROM item_tax_map WHERE item_id=" + aItemId + " AND is_synced=1";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                Item_tax_map im = new Item_tax_map();
                this.setItem_tax_mapFromResultset(im, rs);
                return im;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public int countItemsMappedSynced(List<TransItem> aTransItems) {
        int ItemsMappedSynced = 0;
        String ComaSepIdStr = new utilities.UtilityBean().getCommaSeperatedItemIds(aTransItems);
        String sql = "SELECT count(*) as n FROM item_tax_map WHERE item_id IN(" + ComaSepIdStr + ") AND is_synced=1";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                ItemsMappedSynced = rs.getInt("n");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ItemsMappedSynced;
    }

    public int countItemsNotMappedSynced(List<TransItem> aTransItems) {
        int ItemsNotMappedSynced = 0;
        try {
            for (int i = 0; i < aTransItems.size(); i++) {
                if (null == this.getItem_tax_mapSynced(aTransItems.get(i).getItemId())) {
                    ItemsNotMappedSynced = ItemsNotMappedSynced + 1;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ItemsNotMappedSynced;
    }

    public void saveItem_tax_mapCall(String aItemDesc, String aItemCodeTax, String aItemIdTaxUser) {
        try {
            long ItemId = 0;
            //ItemIdTaxSys is generated by system, aItemIdTaxUser is provided by the user
            String ItemIdTaxSys = "";
            if (aItemDesc.length() > 0 && aItemCodeTax.length() > 0) {
                Item itm = new ItemBean().getItemByDesc(aItemDesc);
                if (itm != null) {
                    ItemId = itm.getItemId();
                    if (aItemIdTaxUser.length() == 0) {
                        ItemIdTaxSys = CompanySetting.getBranchId() + "_" + ItemId;
                    }
                }
                if (ItemId > 0) {
                    //check if already mapped
                    Item_tax_map itmap = this.getItem_tax_map(ItemId);
                    Item_tax_map itmap4save = new Item_tax_map();
                    if (null == itmap) {//insert
                        itmap4save.setItem_tax_map_id(0);
                        itmap4save.setItem_id(ItemId);
                        if (aItemIdTaxUser.length() > 0) {
                            itmap4save.setItem_id_tax(aItemIdTaxUser);
                        } else if (ItemIdTaxSys.length() > 0) {
                            itmap4save.setItem_id_tax(ItemIdTaxSys);
                        } else {
                            itmap4save.setItem_id_tax(Long.toString(ItemId));
                        }
                        itmap4save.setItem_code_tax(aItemCodeTax);
                        itmap4save.setIs_synced(0);
                        int x = this.saveItem_tax_map(itmap4save);
                        if (x == 1 && new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value().length() > 0) {//register to URA
                            new StockManage().registerItemCallThread(ItemId, itmap4save.getItem_id_tax(), aItemCodeTax);
                        }
                    } else {//update
                        if (itmap.getItem_code_tax().equals(aItemCodeTax) && itmap.getIs_synced() == 1) {
                            //nothing has changed
                        } else {
                            itmap4save.setItem_tax_map_id(itmap.getItem_tax_map_id());
                            itmap4save.setItem_id(ItemId);
                            if (aItemIdTaxUser.length() > 0) {
                                itmap4save.setItem_id_tax(aItemIdTaxUser);
                            } else if (itmap.getItem_id_tax().length() > 0) {
                                itmap4save.setItem_id_tax(itmap.getItem_id_tax());
                            } else {
                                itmap4save.setItem_id_tax(Long.toString(ItemId));
                            }
                            itmap4save.setItem_code_tax(aItemCodeTax);
                            itmap4save.setIs_synced(0);
                            int x = this.saveItem_tax_map(itmap4save);
                            if (x == 1 && new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value().length() > 0) {//register to URA
                                new StockManage().registerItemCallThread(ItemId, itmap4save.getItem_id_tax(), aItemCodeTax);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int saveItem_tax_map(Item_tax_map aItem_tax_map) {
        int saved = 0;
        String sql = "{call sp_save_item_tax_map(?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aItem_tax_map) {
                try {
                    cs.setLong("in_item_tax_map_id", aItem_tax_map.getItem_tax_map_id());
                } catch (NullPointerException npe) {
                    cs.setLong("in_item_tax_map_id", 0);
                }
                try {
                    cs.setLong("in_item_id", aItem_tax_map.getItem_id());
                } catch (NullPointerException npe) {
                    cs.setLong("in_item_id", 0);
                }
                try {
                    cs.setString("in_item_id_tax", aItem_tax_map.getItem_id_tax());
                } catch (NullPointerException npe) {
                    cs.setString("in_item_id_tax", "");
                }
                try {
                    cs.setString("in_item_code_tax", aItem_tax_map.getItem_code_tax());
                } catch (NullPointerException npe) {
                    cs.setString("in_item_code_tax", "");
                }
                try {
                    cs.setInt("in_is_synced", aItem_tax_map.getIs_synced());
                } catch (NullPointerException npe) {
                    cs.setInt("in_is_synced", 0);
                }
                cs.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int saveItem_tax_mapSync(long aItemId, int aIs_synced) {
        int saved = 0;
        String sql = "UPDATE item_tax_map SET is_synced=" + aIs_synced + " WHERE item_tax_map_id>0 and item_id=" + aItemId;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (aItemId > 0) {
                ps.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }
}
