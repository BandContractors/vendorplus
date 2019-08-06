package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.AccCoa;
import entities.CompanySetting;
import entities.TransItem;
import entities.GroupRight;
import entities.Transactor;
import entities.Item;
import entities.ItemProductionMap;
import entities.TransactionType;
import entities.UserDetail;
import entities.Stock;
import entities.Store;
import entities.Trans;
import entities.TransProduction;
import entities.TransProductionItem;
import entities.TransactionReason;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.VARCHAR;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class TransProductionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ActionMessage = null;
    private UserDetail TransUserDetail;
    private String DateType;
    private Date Date1;
    private Date Date2;
    private String FieldName;
    private TransactionType TransTypeObj;
    private TransactionReason TransReasonObj;
    private TransactionType TransTypeRefObj;
    private TransactionReason TransReasonRefObj;
    private List<Store> StoreList;
    private List<ItemProductionMap> itmCombinationList = new ArrayList<>();
    private ItemProductionMap ItemProductionMapObj = new ItemProductionMap();
    private List<TransProduction> TransList;
    private List<TransProduction> TransListSummary;
    private List<TransProductionItem> TransItemList;
    private String ActionMessageChild;
    private TransProduction TransProdObj;
    private String ActionType;

    public void updateTransItemBatchSpecific(ItemProductionMap aItemProductionMap, long aStockId) {
        try {
            aItemProductionMap.setBatchno("");
            aItemProductionMap.setCodeSpecific("");
            aItemProductionMap.setDescSpecific("");
            //aItemProductionMap.setItemExpryDate(null);
            //aItemProductionMap.setItemMnfDate(null);
            Stock stock = null;
            stock = new StockBean().getStock(aStockId);
            if (null == stock || null == aItemProductionMap) {
                aItemProductionMap.setBatchno("");
                aItemProductionMap.setCodeSpecific("");
                aItemProductionMap.setDescSpecific("");
                //aItemProductionMap.setItemExpryDate(null);
                //aItemProductionMap.setItemMnfDate(null);
            } else {
                aItemProductionMap.setBatchno(stock.getBatchno());
                aItemProductionMap.setCodeSpecific(stock.getCodeSpecific());
                aItemProductionMap.setDescSpecific(stock.getDescSpecific());
                try {
                    //aItemProductionMap.setItemExpryDate(stock.getItemExpDate());
                } catch (NullPointerException npe) {
                    //aItemProductionMap.setItemExpryDate(null);
                }
                try {
                    //aItemProductionMap.setItemMnfDate(stock.getItemMnfDate());
                } catch (NullPointerException npe) {
                    //aItemProductionMap.setItemMnfDate(null);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public TransProduction getTransProductionById(long aTransProductionId) {
        String sql = "{call sp_search_trans_production_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransProductionId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getTransProductionFromResultset(rs);
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

    public TransProduction getTransProductionFromResultset(ResultSet aResultSet) {
        try {
            TransProduction transProduction = new TransProduction();
            transProduction.setTransactionId(aResultSet.getLong("transaction_id"));
            transProduction.setTransactionDate(new Date(aResultSet.getDate("transaction_date").getTime()));
            transProduction.setStoreId(aResultSet.getInt("store_id"));

            try {
                transProduction.setStore2Id(aResultSet.getInt("store2_id"));
            } catch (NullPointerException npe) {
                transProduction.setStore2Id(0);
            }
            try {
                transProduction.setTransactionTypeId(aResultSet.getInt("transaction_type_id"));
            } catch (NullPointerException npe) {
                transProduction.setTransactionTypeId(0);
            }
            try {
                transProduction.setTransactionReasonId(aResultSet.getInt("transaction_reason_id"));
            } catch (NullPointerException npe) {
                transProduction.setTransactionReasonId(0);
            }
            try {
                transProduction.setTransactionRef(aResultSet.getString("transaction_ref"));
            } catch (NullPointerException npe) {
                transProduction.setTransactionRef("");
            }
            try {
                transProduction.setOutputItemId(aResultSet.getLong("output_item_id"));
            } catch (NullPointerException npe) {
                transProduction.setOutputItemId(0);
            }
            try {
                transProduction.setOutputQty(aResultSet.getDouble("output_qty"));
            } catch (NullPointerException npe) {
                transProduction.setOutputQty(0);
            }
            try {
                transProduction.setOutputUnitCost(aResultSet.getDouble("output_unit_cost"));
            } catch (NullPointerException npe) {
                transProduction.setOutputUnitCost(0);
            }
            try {
                transProduction.setOutputTotalCost(aResultSet.getDouble("output_total_cost"));
            } catch (NullPointerException npe) {
                transProduction.setOutputTotalCost(0);
            }
            try {
                transProduction.setTransactionComment(aResultSet.getString("transaction_comment"));
            } catch (NullPointerException npe) {
                transProduction.setTransactionComment("");
            }
            try {
                transProduction.setAddUserDetailId(aResultSet.getInt("add_user_detail_id"));
            } catch (NullPointerException npe) {
                transProduction.setAddUserDetailId(0);
            }
            try {
                transProduction.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                transProduction.setAddDate(null);
            }
            try {
                transProduction.setEditUserDetailId(aResultSet.getInt("edit_user_detail_id"));
            } catch (NullPointerException npe) {
                transProduction.setEditUserDetailId(0);
            }
            try {
                transProduction.setEditDate(new Date(aResultSet.getTimestamp("edit_date").getTime()));
            } catch (NullPointerException npe) {
                transProduction.setEditDate(null);
            }
            try {
                transProduction.setTransactionUserDetailId(aResultSet.getInt("transaction_user_detail_id"));
            } catch (NullPointerException | SQLException npe) {
                transProduction.setTransactionUserDetailId(0);
            }
            try {
                transProduction.setItemExpiryDate(new Date(aResultSet.getDate("item_expiry_date").getTime()));
            } catch (NullPointerException | SQLException npe) {
                transProduction.setItemExpiryDate(null);
            }
            try {
                transProduction.setItemMnfDate(new Date(aResultSet.getDate("item_expiry_date").getTime()));
            } catch (NullPointerException | SQLException npe) {
                transProduction.setItemMnfDate(null);
            }
            try {
                transProduction.setAccount_code(aResultSet.getString("account_code"));
            } catch (NullPointerException npe) {
                transProduction.setAccount_code("");
            }
            try {
                transProduction.setCurrencyCode(aResultSet.getString("currency_code"));
            } catch (NullPointerException | SQLException npe) {
                transProduction.setCurrencyCode("");
            }
            try {
                transProduction.setBatchno(aResultSet.getString("batchno"));
            } catch (NullPointerException npe) {
                transProduction.setBatchno("");
            }
            try {
                transProduction.setCodeSpecific(aResultSet.getString("code_specific"));
            } catch (NullPointerException npe) {
                transProduction.setCodeSpecific("");
            }
            try {
                transProduction.setDescSpecific(aResultSet.getString("desc_specific"));
            } catch (NullPointerException npe) {
                transProduction.setDescSpecific("");
            }
            try {
                transProduction.setDescMore(aResultSet.getString("desc_more"));
            } catch (NullPointerException npe) {
                transProduction.setDescMore("");
            }
            try {
                transProduction.setTransactor_id(aResultSet.getLong("transactor_id"));
            } catch (NullPointerException npe) {
                transProduction.setTransactor_id(0);
            }
            try {
                transProduction.setTransaction_number(aResultSet.getString("transaction_number"));
            } catch (NullPointerException npe) {
                transProduction.setTransaction_number("");
            }
            return transProduction;
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
        }
    }

    public void updateUnitCostProduction(TransItem aTransItem, int aStoreId) {
        if (new Parameter_listBean().getParameter_listByContextNameMemory("PRODUCTION", "CALC_OUTPUT_UNIT_COST_FROM_INPUT").getParameter_value().equals("1")) {
            double costprice = 0;
            costprice = this.getTotalUnitCostRawMaterials(aStoreId);
            aTransItem.setUnitCostPrice(costprice);
        } else {
            this.updateUnitCostProductionFromOutput(aTransItem, aStoreId);
        }
    }

    public void updateUnitCostProductionFromOutput(TransItem aTransItem, int aStoreId) {
        long LatestTransId = 0;
        try {
            LatestTransId = this.getItemUnitCostPriceLatestTransItemId(70, 107, aStoreId, aTransItem.getItemId(), "", "", "");
        } catch (NullPointerException npe) {
            LatestTransId = 0;
        }
        if (LatestTransId > 0) {
            try {
                aTransItem.setUnitCostPrice(this.getTransProductionById(LatestTransId).getOutputUnitCost());
            } catch (NullPointerException npe) {
            }
        }
    }

    public double getTotalUnitCostRawMaterials(int aStoreId) {
        double TotalUnitCost = 0;
        long LatestTransItemId = 0;
        for (int i = 0; i < this.getItmCombinationList().size(); i++) {
            LatestTransItemId = 0;
            try {
                LatestTransItemId = new TransItemBean().getItemUnitCostPriceLatestTransItemId(9, 32, aStoreId, this.getItmCombinationList().get(i).getInputItemId(), "", "", "");
            } catch (NullPointerException npe) {
                LatestTransItemId = 0;
            }
            if (LatestTransItemId > 0) {
                try {
                    TotalUnitCost = TotalUnitCost + new TransItemBean().getTransItem(LatestTransItemId).getUnitCostPrice();
                } catch (NullPointerException npe) {
                }
            }
        }
        return TotalUnitCost;
    }

    public long getItemUnitCostPriceLatestTransItemId(int aTransTypeId, int aTransReasId, int aStoreId, long aItemId, String aBatchno, String aCodeSpec, String aDescSpec) {
        String sql = "{call sp_item_unit_cost_price_latest_production(?,?,?,?,?)}";
        long transid = 0;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setInt("in_store_id", aStoreId);
            cs.setLong("in_item_id", aItemId);
            cs.setString("in_batchno", aBatchno);
            cs.setString("in_code_specific", aCodeSpec);
            cs.setString("in_desc_specific", aDescSpec);
            rs = cs.executeQuery();
            if (rs.next()) {
                try {
                    transid = rs.getLong("transaction_id");
                } catch (NullPointerException | SQLException npe) {
                    transid = 0;
                }
            }
        } catch (SQLException ex) {
            transid = 0;
            System.out.println("getItemUnitCostPriceLatestTransItemId:" + ex.getMessage());
        }
        return transid;
    }

    public void addTransProductionItem(TransItem aTransItem,Item aItem, ItemProductionMap aItemProductionMap) {
        String msg = "";
        if (null == aItemProductionMap || null == aItem) {
            msg = "Select Item to add...";
        } else if (aItemProductionMap.getInputItemId() == 0) {
            msg = "Select Item to add";
        } else if (aItemProductionMap.getInputQty() <= 0) {
            msg = "Check Raw Material Item Qty";
        } else if (this.itemExists(this.getItmCombinationList(), aItemProductionMap.getInputItemId(), aItemProductionMap.getBatchno(), aItemProductionMap.getCodeSpecific(), aItemProductionMap.getDescSpecific()) > -1) {
            msg = "Raw Material Item already exists";
        } else {
            ItemProductionMap ipm = new ItemProductionMap();
            ipm.setOutputItemId(0);
            ipm.setItemProductionMapId(0);
            ipm.setInputItemId(aItemProductionMap.getInputItemId());
            ipm.setBatchno(aItemProductionMap.getBatchno());
            ipm.setCodeSpecific(aItemProductionMap.getCodeSpecific());
            ipm.setDescSpecific(aItemProductionMap.getDescSpecific());
            ipm.setInputQty(aItemProductionMap.getInputQty());
            new ItemProductionMapBean().updateLookUpsUIInput(ipm);
            //add
            this.getItmCombinationList().add(0, ipm);
            this.updateUnitCostProduction(aTransItem,new GeneralUserSetting().getCurrentStore().getStoreId());
            new ItemProductionMapBean().clearItemProductionMap(aItemProductionMap);
            new ItemBean().clearItem(aItem);
        }
        if (msg.length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Add", new FacesMessage(msg));
        }
    }
    
    public void removeTransProductionItem(TransItem aTransItem, ItemProductionMap aItemProductionMap) {
            this.getItmCombinationList().remove(aItemProductionMap);
            this.updateUnitCostProduction(aTransItem,new GeneralUserSetting().getCurrentStore().getStoreId());
    }

    public int itemExists(List<ItemProductionMap> aItmCombinationList, Long ItemIdent, String BatchNumb, String aCodeSpec, String aDescSpec) {
        List<ItemProductionMap> ati = aItmCombinationList;
        int ItemFoundAtIndex = -1;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double SubT = 0;
        while (ListItemIndex < ListItemNo) {
            if (ati.get(ListItemIndex).getInputItemId() == ItemIdent && BatchNumb.equals(ati.get(ListItemIndex).getBatchno()) && aCodeSpec.equals(ati.get(ListItemIndex).getCodeSpecific()) && aDescSpec.equals(ati.get(ListItemIndex).getDescSpecific())) {
                ItemFoundAtIndex = ListItemIndex;
                break;
            } else {
                ItemFoundAtIndex = -1;
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return ItemFoundAtIndex;
    }

    public void clearTransProductionItem(ItemProductionMap aItemProductionMap) {
        try {
            if (aItemProductionMap != null) {
                aItemProductionMap.setItemProductionMapId(0);
                aItemProductionMap.setOutputItemId(0);
                aItemProductionMap.setInputItemId(0);
                aItemProductionMap.setInputQty(0);
            }
        } catch (Exception e) {
            System.out.println("clearTransProductionItem:" + e.getMessage());
        }
    }

    public void initTransProduction(ItemProductionMap aItemProductionMap, List<ItemProductionMap> aActiveTransItems, Item aParentItem, Item aChildItem) {
        this.clearTransProductionItem(aItemProductionMap);
        new ItemBean().clearItem(aParentItem);
        new ItemBean().clearItem(aChildItem);
        aActiveTransItems.clear();
    }

    public String getAnyItemTotalQtyGreaterThanCurrentQty(TransItem transItem, List<ItemProductionMap> aActiveTransItems, int aStoreId) {
        List<ItemProductionMap> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double producedQty = transItem.getItemQty();
        double TQty = 0;
        String ItemString = "";
        while (ListItemIndex < ListItemNo) {
            if (new TransItemBean().isItemTotalQtyGreaterThanCurrentQty(aStoreId, ati.get(ListItemIndex).getInputItemId(), ati.get(ListItemIndex).getBatchno(), (ati.get(ListItemIndex).getInputQty() * producedQty), ati.get(ListItemIndex).getCodeSpecific(), ati.get(ListItemIndex).getDescSpecific())) {
                ItemString = ati.get(ListItemIndex).getInputItemName();
                break;
            } else {
                ItemString = "";
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return ItemString;
    }

    public String validateTransProduction(int aStoreId, int aTransTypeId, int aTransReasonId, TransItem transItem, List<ItemProductionMap> aActiveTransItems, UserDetail aTransUserDetail, Trans trans) {
        String msg = "";
        String ItemMessage = "";
        try {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
            Store store = new StoreBean().getStore(aStoreId);
            try {
                ItemMessage = this.getAnyItemTotalQtyGreaterThanCurrentQty(transItem, aActiveTransItems, store.getStoreId());
                if (null == ItemMessage) {
                    ItemMessage = "";
                }
            } catch (NullPointerException npe) {
            }
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();

            if (null == transtype) {
                msg = "-.-.-. INVALID TRANSACTION -.-.-.";
            } else if (trans.getTransactionId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(transreason.getTransactionReasonId()), "Add") == 0) {
                msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            } else if (!ItemMessage.equals("")) {
                msg = "INSUFFICIENT STOCK FOR ITEM(" + ItemMessage + ")...";
            } else if (transItem.getUnitCostPrice() == 0) {
                msg = "PLEASE SPECIFY UNIT COST ...";
            } else if (transItem.getItemQty() == 0) {
                msg = "Produced Qty cannot be zero (0)... ";
            } else if (aActiveTransItems.isEmpty()) {
                msg = "Please add raw materials (Input Items)...";
            } else if (transItem.getItemId() == 0) {
                msg = "Specify Produced Item... ";
            } else if (trans.getTransactionUserDetailId() == 0 && transtype.getIsTransactionUserMandatory().equals("Yes")) {
                msg = "Specify Production User... ";
            }
        } catch (Exception e) {
            msg = "An error has occured during the validation process";
            System.err.println("--:validateTransProduction:--" + e.getMessage());
            e.printStackTrace();
        }
        return msg;
    }

    public void saveTransProductionCEC(int aStoreId, int aTransTypeId, int aTransReasonId, TransItem transItem, List<ItemProductionMap> aActiveTransItems, UserDetail aTransUserDetail, Trans trans, Item aProducedItem, Transactor aSelectedTransactor) {
        try {
            String ValidationMessage = this.validateTransProduction(aStoreId, aTransTypeId, aTransReasonId, transItem, aActiveTransItems, aTransUserDetail, trans);

            if (ValidationMessage.length() > 0) {
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ValidationMessage));
            } else {
                this.saveTransProduction(aStoreId, aTransTypeId, aTransReasonId, transItem, aActiveTransItems, trans);
                //clear
                //org.primefaces.context.RequestContext.getCurrentInstance().execute("doHiddenClear()");
                this.clearAll(trans, aActiveTransItems, transItem, aProducedItem, aSelectedTransactor, 2, null, aTransUserDetail, null, null, null);
                //this.itmCombinationList.clear();
                //display success message
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Saved Successfully"));
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void saveTransProduction(int aStoreId, int aTransTypeId, int aTransReasonId, TransItem transItem, List<ItemProductionMap> aTransProducts, Trans trans) {
        String sql = null;
        String msg = "";
        long InsertedTransId = 0;
        double InsertedOutputQty = 0;
        int InsertedStoreId = 0;
        TransactionTypeBean TransTypeBean = new TransactionTypeBean();
        StockBean StkBean = new StockBean();
        TransProduction aTransProduction = new TransProduction();
        List<TransProduction> trasProductItems = new ArrayList<>();

        if (1 == 2) {
        } else {
            if (aTransProduction.getTransactionId() == 0) {
                sql = "{call sp_insert_trans_production(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {

                if (aTransProduction.getTransactionId() == 0) {
                    //clean batch
                    TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
                    TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
                    Store store = new StoreBean().getStore(aStoreId);

                    cs.setDate("in_transaction_date", new java.sql.Date(trans.getTransactionDate().getTime()));
                    cs.setInt("in_store_id", store.getStoreId());
                    aTransProduction.setStoreId(store.getStoreId());
                    cs.setInt("in_transaction_type_id", aTransTypeId);
                    cs.setInt("in_transaction_reason_id", aTransReasonId);
                    cs.setString("in_transaction_ref", trans.getTransactionRef());
                    cs.setLong("in_output_item_id", transItem.getItemId());
                    cs.setDouble("in_output_qty", transItem.getItemQty());
                    cs.setDouble("in_output_unit_cost", transItem.getUnitCostPrice());
                    double total_cost;
                    total_cost = transItem.getItemQty() * transItem.getUnitCostPrice();
                    cs.setDouble("in_output_total_cost", total_cost);
                    cs.setString("in_batchno", transItem.getBatchno());
                    cs.registerOutParameter("out_transaction_id", VARCHAR);
                    cs.registerOutParameter("out_output_qty", VARCHAR);
                    cs.registerOutParameter("out_store_id", VARCHAR);
                    try {
                        cs.setDate("in_item_expiry_date", new java.sql.Date(transItem.getItemExpryDate().getTime()));
                    } catch (NullPointerException npe) {
                        cs.setDate("in_item_expiry_date", null);
                    }
                    try {
                        cs.setDate("in_item_mnf_date", new java.sql.Date(transItem.getItemMnfDate().getTime()));
                    } catch (NullPointerException npe) {
                        cs.setDate("in_item_mnf_date", null);
                    }
                    try {
                        if (null == transItem.getCodeSpecific()) {
                            cs.setString("in_code_specific", "");
                        } else {
                            cs.setString("in_code_specific", transItem.getCodeSpecific());
                        }
                    } catch (NullPointerException npe) {
                        cs.setString("in_code_specific", "");
                    }
                    try {
                        if (null == transItem.getDescSpecific()) {
                            cs.setString("in_desc_specific", "");
                        } else {
                            cs.setString("in_desc_specific", transItem.getDescSpecific());
                        }
                    } catch (NullPointerException npe) {
                        cs.setString("in_desc_specific", "");
                    }
                    try {
                        if (null == transItem.getDescMore()) {
                            cs.setString("in_desc_more", "");
                        } else {
                            cs.setString("in_desc_more", transItem.getDescMore());
                        }
                    } catch (NullPointerException npe) {
                        cs.setString("in_desc_more", "");
                    }
                    try {
                        if (null == trans.getTransactionComment()) {
                            cs.setString("in_transaction_comment", "");
                        } else {
                            cs.setString("in_transaction_comment", trans.getTransactionComment());
                        }
                    } catch (NullPointerException npe) {
                        cs.setString("in_transaction_comment", "");
                    }
                    try {
                        cs.setInt("in_add_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_add_user_detail_id", 0);
                    }
                    try {
                        cs.setTimestamp("in_add_date", new java.sql.Timestamp(aTransProduction.getAddDate().getTime()));
                    } catch (NullPointerException npe) {
                        cs.setTimestamp("in_add_date", null);
                    }
                    try {
                        cs.setInt("in_transaction_user_detail_id", trans.getTransactionUserDetailId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_transaction_user_detail_id", 0);
                    }
                    try {
                        cs.setInt("in_edit_user_detail_id", trans.getEditUserDetailId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_edit_user_detail_id", 0);
                    }
                    try {
                        cs.setTimestamp("in_edit_date", new java.sql.Timestamp(aTransProduction.getEditDate().getTime()));
                    } catch (NullPointerException npe) {
                        cs.setTimestamp("in_edit_date", null);
                    }
                    try {
                        if (null == trans.getCurrencyCode()) {
                            cs.setString("in_currency_code", "");
                        } else {
                            cs.setString("in_currency_code", trans.getCurrencyCode());
                        }
                    } catch (NullPointerException npe) {
                        cs.setString("in_currency_code", "");
                    }
                    try {
                        if (null == transItem.getAccountCode()) {
                            cs.setString("in_account_code", "");
                        } else {
                            cs.setString("in_account_code", transItem.getAccountCode());
                        }
                    } catch (NullPointerException npe) {
                        cs.setString("in_account_code", "");
                    }
                    try {
                        if (trans.getTransactionNumber().length() == 0) {
                            trans.setTransactionNumber(new Trans_number_controlBean().getNewTransNumber(transtype));
                            cs.setString("in_transaction_number", trans.getTransactionNumber());
                            new Trans_number_controlBean().updateTrans_number_control(transtype);
                        } else {
                            cs.setString("in_transaction_number", trans.getTransactionNumber());
                        }
                    } catch (NullPointerException npe) {
                        cs.setString("in_transaction_number", "");
                    }
                    try {
                        if (trans.getTransactorId() > 0) {
                            cs.setLong("in_transactor_id", trans.getTransactorId());
                        } else {
                            cs.setLong("in_transactor_id", 0);
                        }
                    } catch (NullPointerException npe) {
                        cs.setLong("in_transactor_id", 0);
                    }
                    //save
                    cs.executeUpdate();
                    InsertedTransId = cs.getLong("out_transaction_id");
                    InsertedOutputQty = cs.getLong("out_output_qty");
                    InsertedStoreId = cs.getInt("out_store_id");
                    //update stock
                    double UnitCostPrice = 0;
                    if (StkBean.getStock(store.getStoreId(), transItem.getItemId(), transItem.getBatchno(), transItem.getCodeSpecific(), transItem.getDescSpecific()) != null) {
                        //update/add
                        Stock stock = new Stock();
                        int i = 0;
                        stock.setStoreId(store.getStoreId());
                        stock.setItemId(transItem.getItemId());
                        stock.setBatchno(transItem.getBatchno());
                        stock.setCodeSpecific(transItem.getCodeSpecific());
                        stock.setDescSpecific(transItem.getDescSpecific());
                        UnitCostPrice = transItem.getUnitCostPrice();
                        stock.setUnitCost(UnitCostPrice);
                        i = new StockBean().addStock(stock, transItem.getItemQty());
                    } else {
                        //insert
                        Stock stock = new Stock();
                        int i = 0;
                        stock.setStoreId(store.getStoreId());
                        stock.setItemId(transItem.getItemId());
                        stock.setBatchno(transItem.getBatchno());
                        stock.setCodeSpecific(transItem.getCodeSpecific());
                        stock.setDescSpecific(transItem.getDescSpecific());
                        stock.setDescMore(transItem.getDescMore());
                        stock.setCurrentqty(transItem.getItemQty());
                        stock.setItemMnfDate(transItem.getItemMnfDate());
                        stock.setItemExpDate(transItem.getItemExpryDate());
                        UnitCostPrice = transItem.getUnitCostPrice();
                        stock.setUnitCost(UnitCostPrice);
                        stock.setWarrantyDesc("");
                        stock.setWarrantyExpiryDate(null);
                        stock.setAssetStatusId(1);
                        stock.setAssetStatusDesc("");
                        i = new StockBean().saveStock(stock);
                    }
                    new TransProductionItemBean().saveTransProductionItemsCEC(InsertedTransId, InsertedOutputQty, InsertedStoreId, aTransProducts);
                    //update stock
                    TransTypeBean = null;
                    StkBean = null;

                } else if (aTransProduction.getTransactionId() > 0) {
                    //do nothing; this is for edit
                }
            } catch (SQLException se) {
                se.printStackTrace();
                System.err.println(se.getMessage());
                this.setActionMessage("TransProduction NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("TransProduction NOT saved!"));
            }

        }
    }

    public void reportProductionDetail(TransProduction aTransProd, TransProductionBean aTransProdBean) {
        if (aTransProdBean.getDateType().length() == 0) {
            aTransProdBean.setDateType("Add Date");
        }
        aTransProdBean.setActionMessage("");
        ResultSet rs = null;
        this.setTransList(new ArrayList<>());
        this.setTransListSummary(new ArrayList<>());
        String sql = "SELECT * FROM trans_production WHERE 1=1";
        String sqlsum = "";
        if (aTransProdBean.getFieldName().length() > 0) {
            sqlsum = "SELECT " + aTransProdBean.getFieldName() + ",count(transaction_id) as output_qty FROM trans_production WHERE 1=1";
        } else {
            sqlsum = "SELECT count(transaction_id) as output_qty FROM trans_production WHERE 1=1";
        }
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = "";
        if (aTransProdBean.getFieldName().length() > 0) {
            groupbysql = " GROUP BY " + aTransProdBean.getFieldName();
        } else {
            groupbysql = "";
        }
        if (aTransProd.getStoreId() > 0) {
            wheresql = wheresql + " AND store_id=" + aTransProd.getStoreId();
        }
        if (aTransProd.getTransaction_number().length() > 0) {
            wheresql = wheresql + " AND transaction_number='" + aTransProd.getTransaction_number() + "'";
        }
        if (aTransProd.getTransactionRef().length() > 0) {
            wheresql = wheresql + " AND transaction_ref='" + aTransProd.getTransactionRef() + "'";
        }
        if (aTransProd.getAddUserDetailId() > 0) {
            wheresql = wheresql + " AND add_user_detail_id=" + aTransProd.getAddUserDetailId();
        }
        if (aTransProd.getTransactor_id() > 0) {
            wheresql = wheresql + " AND transactor_id=" + aTransProd.getTransactor_id();
        }
        if (aTransProd.getTransactionUserDetailId() > 0) {
            wheresql = wheresql + " AND transaction_user_detail_id=" + aTransProd.getTransactionUserDetailId();
        }
        if (aTransProdBean.getDateType().length() > 0 && aTransProdBean.getDate1() != null && aTransProdBean.getDate2() != null) {
            switch (aTransProdBean.getDateType()) {
                case "Production Date":
                    wheresql = wheresql + " AND transaction_date BETWEEN '" + new java.sql.Date(aTransProdBean.getDate1().getTime()) + "' AND '" + new java.sql.Date(aTransProdBean.getDate2().getTime()) + "'";
                    break;
                case "Add Date":
                    wheresql = wheresql + " AND add_date BETWEEN '" + new java.sql.Timestamp(aTransProdBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aTransProdBean.getDate2().getTime()) + "'";
                    break;
            }
        }
        ordersql = " ORDER BY add_date DESC,transaction_id DESC";
        if (aTransProdBean.getFieldName().length() > 0) {
            ordersqlsum = " ORDER BY " + aTransProdBean.getFieldName();
        } else {
            ordersqlsum = "";
        }
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            TransProduction trans = null;
            while (rs.next()) {
                trans = this.getTransProductionFromResultset(rs);
                this.updateLookup(trans);
                this.getTransList().add(trans);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            TransProduction transsum = null;
            while (rs.next()) {
                transsum = new TransProduction();
                if (aTransProdBean.getFieldName().length() > 0) {
                    switch (aTransProdBean.getFieldName()) {
                        case "add_user_detail_id":
                            try {
                                transsum.setAddUserDetailId(rs.getInt("add_user_detail_id"));
                            } catch (NullPointerException npe) {
                                transsum.setAddUserDetailId(0);
                            }
                            break;
                        case "transaction_user_detail_id":
                            try {
                                transsum.setTransactionUserDetailId(rs.getInt("transaction_user_detail_id"));
                            } catch (NullPointerException npe) {
                                transsum.setTransactionUserDetailId(0);
                            }
                            break;
                        case "transaction_date":
                            try {
                                transsum.setTransactionDate(new Date(rs.getDate("transaction_date").getTime()));
                            } catch (NullPointerException | SQLException npe) {
                                transsum.setTransactionDate(null);
                            }
                            break;
                    }
                }
                try {
                    transsum.setOutputQty(rs.getDouble("output_qty"));
                } catch (NullPointerException npe) {
                    transsum.setOutputQty(0);
                }
                this.getTransListSummary().add(transsum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void initResetProductionDetail(TransProduction aTransProd, TransProductionBean aTransProdBean, String aMode) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.resetProductionDetail(aTransProd, aTransProdBean, aMode);
        }
    }

    public void resetProductionDetail(TransProduction aTransProd, TransProductionBean aTransProdBean, String aMode) {
        aTransProdBean.setActionMessage("");
        try {
            this.clearTransProduction(aTransProd, aMode);
        } catch (NullPointerException npe) {
        }
        try {
            aTransProdBean.setDateType("");
            aTransProdBean.setDate1(null);
            aTransProdBean.setDate2(null);
            aTransProdBean.setFieldName("");
            aTransProdBean.getTransList().clear();
            aTransProdBean.getTransListSummary().clear();
        } catch (NullPointerException npe) {
        }
    }

    public void clearAll(Trans t, List<ItemProductionMap> aActiveTransItems, TransItem ti, Item aSelectedItem, Transactor aSelectedTransactor, int ClearNo, Transactor aSelectedBillTransactor, UserDetail aTransUserDetail, Transactor aSelectedSchemeTransactor, UserDetail aAuthorisedByUserDetail, AccCoa aSelectedAccCoa) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all  
        TransItemBean tib = new TransItemBean();
        ItemBean itmB = new ItemBean();
        TransactorBean trB = new TransactorBean();
        AccCoaBean acBean = new AccCoaBean();

        if (ClearNo == 1 || ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
            //clear autoCompletetd item
            itmB.clearSelectedItem();
            itmB.clearItem(aSelectedItem);
            //clear the selcted trans item
            tib.clearTransItem(ti);
            //clear selected AccCoa
            acBean.clearAccCoa(aSelectedAccCoa);
        }
        if (ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
            trB.clearTransactor(aSelectedTransactor);
            //code for clearing BILL customer/supplier/transactor
            //trB.clearSelectedBillTransactor();
            trB.clearTransactor(aSelectedBillTransactor);
            trB.clearTransactor(aSelectedSchemeTransactor);
            //clear all the item LIST
            //--//tib.getActiveTransItems().clear();
            aActiveTransItems.clear();

            //clear Trans inc. payments
            new TransBean().clearTrans(t);

            //clear TransUser / Service Offered by
            new UserDetailBean().clearUserDetail(aTransUserDetail);

            //clear Authorised By UserDetail
            new UserDetailBean().clearUserDetail(aAuthorisedByUserDetail);
        }
    }

    public void initClearAll(String aLevel, Trans t, List<ItemProductionMap> aActiveTransItems, TransItem ti, Item aSelectedItem, Transactor aSelectedTransactor, int ClearNo, Transactor aSelectedBillTransactor, UserDetail aTransUserDetail, Transactor aSelectedSchemeTransactor) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all  
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            TransItemBean tib = new TransItemBean();
            ItemBean itmB = new ItemBean();
            TransactorBean trB = new TransactorBean();

            if (ClearNo == 1 || ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
                //clear autoCompletetd item
                itmB.clearSelectedItem();
                itmB.clearItem(aSelectedItem);
                //clear the selcted trans item
                tib.clearTransItem(ti);
            }
            if (ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
                //code for clearing customer/supplier/transactor
                trB.clearSelectedTransactor();
                trB.clearTransactor(aSelectedTransactor);
                //code for clearing customer/supplier/transactor
                //trB.clearSelectedBillTransactor();
                trB.clearTransactor(aSelectedBillTransactor);
                trB.clearTransactor(aSelectedSchemeTransactor);
                //clear all the item LIST
                //--//tib.getActiveTransItems().clear();
                aActiveTransItems.clear();

                //clear Trans
                new TransBean().clearTrans(t);

                //clear transaction user / service offered by
                new UserDetailBean().clearUserDetail(aTransUserDetail);

                //clear current trans and pay ids in session
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                HttpSession httpSession = request.getSession(false);
                if (aLevel.equals("PARENT")) {
                    httpSession.setAttribute("CURRENT_TRANSACTION_ID", 0);
                    httpSession.setAttribute("CURRENT_PAY_ID", 0);
                    //clear action message
                    this.setActionMessage("");
                } else if (aLevel.equals("CHILD")) {
                    httpSession.setAttribute("CURRENT_TRANSACTION_ID_CHILD", 0);
                    httpSession.setAttribute("CURRENT_PAY_ID_CHILD", 0);
                    //clear action message
                    this.setActionMessageChild("");
                }
            }
            new OutputDetailBean().refreshOutput(aLevel, "");
        }
    }

    public void getItemProductionMapsByParentItemId(long aParentId) {
        String sql = "{call sp_search_item_production_map_by_output_item_id(?)}";
        ResultSet rs = null;
        this.setItmCombinationList(new ArrayList<ItemProductionMap>());
        getItmCombinationList().clear();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aParentId);
            rs = ps.executeQuery();
            ItemProductionMapBean ipmb = new ItemProductionMapBean();
            while (rs.next()) {
                ItemProductionMap itemProductionMap = new ItemProductionMap();
                //itemProductionMap.setItemProductionMapId(rs.getLong("item_production_map_id"));
                itemProductionMap.setItemProductionMapId(0);
                //itemProductionMap.setOutputItemId(rs.getLong("output_item_id"));
                itemProductionMap.setOutputItemId(0);
                itemProductionMap.setInputItemId(rs.getLong("input_item_id"));
                itemProductionMap.setInputQty(rs.getDouble("input_qty"));
                itemProductionMap.setBatchno("");
                itemProductionMap.setCodeSpecific("");
                itemProductionMap.setDescSpecific("");
                ipmb.updateLookUpsUIInput(itemProductionMap);
                getItmCombinationList().add(itemProductionMap);
            }
//            return ItemProductionMaps2;
        } catch (SQLException se) {
            System.err.println(se.getMessage());
//            return null;
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

    public void clearTransProduction(TransProduction aTransProduction, String aMode) {
        aTransProduction.setTransactionId(0);
        if (aMode.equals("ADD")) {
            try {
                aTransProduction.setTransactionDate(new CompanySetting().getCURRENT_SERVER_DATE());
            } catch (NullPointerException npe) {
            }
        } else {
            aTransProduction.setTransactionDate(null);
        }
        aTransProduction.setStoreId(0);
        aTransProduction.setStore2Id(0);
        aTransProduction.setTransactionTypeId(0);
        aTransProduction.setTransactionReasonId(0);
        aTransProduction.setTransactionRef("");
        aTransProduction.setOutputItemId(0);
        aTransProduction.setOutputQty(0);
        aTransProduction.setOutputUnitCost(0);
        aTransProduction.setOutputTotalCost(0);
        aTransProduction.setTransactionComment("");
        aTransProduction.setAddUserDetailId(0);
        aTransProduction.setAddDate(null);
        aTransProduction.setEditUserDetailId(0);
        aTransProduction.setEditDate(null);
        aTransProduction.setTransactionUserDetailId(0);
        aTransProduction.setItemExpiryDate(null);
        aTransProduction.setItemMnfDate(null);
        aTransProduction.setAccount_code("");
        aTransProduction.setCurrencyCode("");
        aTransProduction.setBatchno("");
        aTransProduction.setCodeSpecific("");
        aTransProduction.setDescSpecific("");
        aTransProduction.setDescMore("");
        aTransProduction.setTransactor_id(0);
        aTransProduction.setTransaction_number("");
    }

    public void clearAll(Trans t, List<TransItem> aActiveTransItems, TransItem ti, Item aSelectedItem, Transactor aSelectedTransactor, int ClearNo, AccCoa aSelectedAccCoa) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all  
        TransItemBean tib = new TransItemBean();
        ItemBean itmB = new ItemBean();
        TransactorBean trB = new TransactorBean();
        AccCoaBean acBean = new AccCoaBean();

        if (ClearNo == 1 || ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
            //clear autoCompletetd item
            itmB.clearSelectedItem();
            itmB.clearItem(aSelectedItem);
            //clear the selcted trans item
            tib.clearTransItem(ti);
            //clear selected AccCoa
            acBean.clearAccCoa(aSelectedAccCoa);
        }
        if (ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
            //put code for clearing customer/supplier/transactor
            trB.clearSelectedTransactor();
            trB.clearTransactor(aSelectedTransactor);
            //clear all the item LIST
            //--//tib.getActiveTransItems().clear();
            aActiveTransItems.clear();

            //clear Trans inc. payments
            new TransBean().clearTrans(t);
        }
    }

    public void initCurrencyCode(int aTransTypeId, Trans trans) {
        try {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            String DefaultCurrencyCode = "";
            String TransTypeCurrencyCode = "";
            String LocalCurrencyCode = "";

            try {
                LocalCurrencyCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
                if (null == LocalCurrencyCode) {
                    LocalCurrencyCode = "";
                }
            } catch (NullPointerException npe) {
                LocalCurrencyCode = "";
            }
            try {
                DefaultCurrencyCode = new GeneralUserSetting().getDEFAULT_CURRENCY_CODE();
                if (null == DefaultCurrencyCode) {
                    DefaultCurrencyCode = "";
                }
            } catch (NullPointerException npe) {
                DefaultCurrencyCode = "";
            }
            try {
                TransTypeCurrencyCode = transtype.getDefault_currency_code();
                if (null == TransTypeCurrencyCode) {
                    TransTypeCurrencyCode = "";
                }
            } catch (NullPointerException npe) {
                TransTypeCurrencyCode = "";
            }

            if (TransTypeCurrencyCode.length() > 0) {
                trans.setCurrencyCode(TransTypeCurrencyCode);
            } else if (DefaultCurrencyCode.length() > 0) {
                trans.setCurrencyCode(DefaultCurrencyCode);
            } else {
                trans.setCurrencyCode(LocalCurrencyCode);
            }
        } catch (NullPointerException npe) {
            trans.setCurrencyCode("");
        }
    }

    public void initTransType(int aTransTypeId, int aTransReasonId) {
        try {
            this.setTransTypeObj(new TransactionTypeBean().getTransactionType(aTransTypeId));
            this.setTransReasonObj(new TransactionReasonBean().getTransactionReason(aTransReasonId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initTransTypeRef(int aTransTypeId, int aTransReasonId) {
        try {
            this.setTransTypeRefObj(new TransactionTypeBean().getTransactionType(aTransTypeId));
            this.setTransReasonRefObj(new TransactionReasonBean().getTransactionReason(aTransReasonId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFieldName(String aFieldId, String aFieldName) {
        UserDetailBean udb = new UserDetailBean();
        TransactorBean tb = new TransactorBean();
        TransactionReasonBean trb = new TransactionReasonBean();
        String ReturnField = "";
        try {
            if ((aFieldName.equals("add_user_detail_id") || aFieldName.equals("edit_user_detail_id") || aFieldName.equals("transaction_user_detail_id")) && !aFieldId.equals("")) {
                ReturnField = udb.getUserDetail(Integer.parseInt(aFieldId)).getFirstName() + " " + udb.getUserDetail(Integer.parseInt(aFieldId)).getSecondName();
            } else if (aFieldName.equals("transactor_id") && !aFieldId.equals("")) {
                ReturnField = tb.getTransactor(Long.parseLong(aFieldId)).getTransactorNames();
            } else if (aFieldName.equals("bill_transactor_id") && !aFieldId.equals("")) {
                ReturnField = tb.getTransactor(Long.parseLong(aFieldId)).getTransactorNames();
            } else if (aFieldName.equals("transaction_reason_id") && !aFieldId.equals("")) {
                ReturnField = trb.getTransactionReason(Integer.parseInt(aFieldId)).getTransactionReasonName();
            } else {
                ReturnField = "Summary";
            }
            return ReturnField;
        } catch (NullPointerException npe) {
            return "";
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
        //Date CurrentServerDate=new CompanySetting().getCURRENT_SERVER_DATE();
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

    public void setTransDateToYesturday(TransProduction aTransProduction) {
        //Date CurrentServerDate=new CompanySetting().getCURRENT_SERVER_DATE();
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();

        aTransProduction.setTransactionDate(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(aTransProduction.getTransactionDate());
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        aTransProduction.setTransactionDate(cal.getTime());

        aTransProduction.setTransactionDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(aTransProduction.getTransactionDate2());
        cal2.add(Calendar.DATE, -1);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        aTransProduction.setTransactionDate2(cal2.getTime());
    }

    public void initTransProductionSession(long aTransId, String aAction) {
        //first set current selection in session
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("CURRENT_TRANSACTION_ID", aTransId);
        httpSession.setAttribute("CURRENT_TRANSACTION_ACTION", aAction);
        httpSession.setAttribute("CURRENT_PAY_ID", 0);
        this.setActionType(aAction);
        this.TransProdObj = this.getTransProductionById(aTransId);
        this.updateLookup(this.TransProdObj);
        this.TransItemList = new TransProductionItemBean().getTransProductionItemsByTransProductionId(aTransId);
        //refresh output
        new OutputDetailBean().refreshOutputProduction("PARENT", "");
    }

    public void updateLookup(TransProduction aTransProduction) {
        if (null != aTransProduction) {
            try {
                aTransProduction.setStoreName(new StoreBean().getStore(aTransProduction.getStoreId()).getStoreName());
            } catch (Exception e) {
                aTransProduction.setStoreName("");
            }
            try {
                aTransProduction.setStore2Name(new StoreBean().getStore(aTransProduction.getStore2Id()).getStoreName());
            } catch (Exception e) {
                aTransProduction.setStore2Name("");
            }
            try {
                aTransProduction.setTransactionUserDetailName(new UserDetailBean().getUserDetail(aTransProduction.getTransactionUserDetailId()).getUserName());
            } catch (Exception e) {
                aTransProduction.setTransactionUserDetailName("");
            }
            try {
                aTransProduction.setOutputItemName(new ItemBean().getItem(aTransProduction.getOutputItemId()).getDescription());
            } catch (Exception e) {
                aTransProduction.setOutputItemName("");
            }
            try {
                aTransProduction.setTransactor_names(new TransactorBean().getTransactor(aTransProduction.getTransactor_id()).getTransactorNames());
            } catch (Exception e) {
                aTransProduction.setTransactor_names("");
            }
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
     * @return the TransUserDetail
     */
    public UserDetail getTransUserDetail() {
        return TransUserDetail;
    }

    /**
     * @param TransUserDetail the TransUserDetail to set
     */
    public void setTransUserDetail(UserDetail TransUserDetail) {
        this.TransUserDetail = TransUserDetail;
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
     * @return the FieldName
     */
    public String getFieldName() {
        return FieldName;
    }

    /**
     * @param FieldName the FieldName to set
     */
    public void setFieldName(String FieldName) {
        this.FieldName = FieldName;
    }

    /**
     * @return the TransTypeObj
     */
    public TransactionType getTransTypeObj() {
        return TransTypeObj;
    }

    /**
     * @param TransTypeObj the TransTypeObj to set
     */
    public void setTransTypeObj(TransactionType TransTypeObj) {
        this.TransTypeObj = TransTypeObj;
    }

    /**
     * @return the TransReasonObj
     */
    public TransactionReason getTransReasonObj() {
        return TransReasonObj;
    }

    /**
     * @param TransReasonObj the TransReasonObj to set
     */
    public void setTransReasonObj(TransactionReason TransReasonObj) {
        this.TransReasonObj = TransReasonObj;
    }

    /**
     * @return the TransTypeRefObj
     */
    public TransactionType getTransTypeRefObj() {
        return TransTypeRefObj;
    }

    /**
     * @param TransTypeRefObj the TransTypeRefObj to set
     */
    public void setTransTypeRefObj(TransactionType TransTypeRefObj) {
        this.TransTypeRefObj = TransTypeRefObj;
    }

    /**
     * @return the TransReasonRefObj
     */
    public TransactionReason getTransReasonRefObj() {
        return TransReasonRefObj;
    }

    /**
     * @param TransReasonRefObj the TransReasonRefObj to set
     */
    public void setTransReasonRefObj(TransactionReason TransReasonRefObj) {
        this.TransReasonRefObj = TransReasonRefObj;
    }

    /**
     * @return the StoreList
     */
    public List<Store> getStoreList() {
        return StoreList;
    }

    /**
     * @param StoreList the StoreList to set
     */
    public void setStoreList(List<Store> StoreList) {
        this.StoreList = StoreList;
    }

    /**
     * @return the itmCombinationList
     */
    public List<ItemProductionMap> getItmCombinationList() {
        return itmCombinationList;
    }

    /**
     * @param itmCombinationList the itmCombinationList to set
     */
    public void setItmCombinationList(List<ItemProductionMap> itmCombinationList) {
        this.itmCombinationList = itmCombinationList;
    }

    /**
     * @return the ItemProductionMapObj
     */
    public ItemProductionMap getItemProductionMapObj() {
        return ItemProductionMapObj;
    }

    /**
     * @param ItemProductionMapObj the ItemProductionMapObj to set
     */
    public void setItemProductionMapObj(ItemProductionMap ItemProductionMapObj) {
        this.ItemProductionMapObj = ItemProductionMapObj;
    }

    /**
     * @return the TransList
     */
    public List<TransProduction> getTransList() {
        return TransList;
    }

    /**
     * @param TransList the TransList to set
     */
    public void setTransList(List<TransProduction> TransList) {
        this.TransList = TransList;
    }

    /**
     * @return the TransListSummary
     */
    public List<TransProduction> getTransListSummary() {
        return TransListSummary;
    }

    /**
     * @param TransListSummary the TransListSummary to set
     */
    public void setTransListSummary(List<TransProduction> TransListSummary) {
        this.TransListSummary = TransListSummary;
    }

    /**
     * @return the TransItemList
     */
    public List<TransProductionItem> getTransItemList() {
        return TransItemList;
    }

    /**
     * @param TransItemList the TransItemList to set
     */
    public void setTransItemList(List<TransProductionItem> TransItemList) {
        this.TransItemList = TransItemList;
    }

    /**
     * @return the ActionMessageChild
     */
    public String getActionMessageChild() {
        return ActionMessageChild;
    }

    /**
     * @param ActionMessageChild the ActionMessageChild to set
     */
    public void setActionMessageChild(String ActionMessageChild) {
        this.ActionMessageChild = ActionMessageChild;
    }

    /**
     * @return the TransProdObj
     */
    public TransProduction getTransProdObj() {
        return TransProdObj;
    }

    /**
     * @param TransProdObj the TransProdObj to set
     */
    public void setTransProdObj(TransProduction TransProdObj) {
        this.TransProdObj = TransProdObj;
    }

    /**
     * @return the ActionType
     */
    public String getActionType() {
        return ActionType;
    }

    /**
     * @param ActionType the ActionType to set
     */
    public void setActionType(String ActionType) {
        this.ActionType = ActionType;
    }
}
