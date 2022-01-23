package beans;

import connections.DBConnection;
import entities.GroupRight;
import entities.Item;
import entities.Subscription;
import entities.Subscription_category;
import entities.Subscription_log;
import entities.Transactor;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import sessions.GeneralUserSetting;
import utilities.UtilityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author emuwonge
 */
@ManagedBean(name = "subscriptionBean")
@SessionScoped
public class SubscriptionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(SubscriptionBean.class.getName());

    private String ActionMessage = null;
    private List<Subscription> SubscriptionList = new ArrayList<>();
    //private int CountList;
    private String SearchSubscriptionNames = "";
    private List<Subscription> Subscriptions;
    private List<Subscription> subscriptionsSummary;
    private List<Subscription_log> SubscriptionsLogList;
    private Transactor selectedTransactor;
    private Item selectedItem;
    private Subscription_category selectedSubscriptionCategory;
    private String filterStatus = "";
    private int filterSubscriptionCategoryId = 0;
    private int filterExpiryDateRange = 0;
    private String filterRecurring = "";
    private Transactor filterTransactor;
    private Item filterItem;
    private String filterAgent = "";
    private List<String> uniqueAgentList;
    private double itemUnitPrice = 0;
    private Date oldExpiryDate = null;
    private Date subscriptionDateRenewal = null;
    private Subscription subscriptionRenewal;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setSubscriptionFromResultset(Subscription aSubscription, ResultSet aResultSet) {
        try {
            try {
                aSubscription.setSubscription_id(aResultSet.getInt("subscription_id"));
            } catch (Exception e) {
                aSubscription.setSubscription_id(0);
            }
            try {
                aSubscription.setTransactor_id(aResultSet.getLong("transactor_id"));
            } catch (Exception e) {
                aSubscription.setTransactor_id(0);
            }
            try {
                aSubscription.setSubscription_category_id(aResultSet.getInt("subscription_category_id"));
            } catch (Exception e) {
                aSubscription.setSubscription_category_id(0);
            }
            try {
                aSubscription.setItem_id(aResultSet.getLong("item_id"));
            } catch (Exception e) {
                aSubscription.setItem_id(0);
            }
            try {
                aSubscription.setDescription(aResultSet.getString("description"));
            } catch (Exception e) {
                aSubscription.setDescription("");
            }
            try {
                aSubscription.setAmount(aResultSet.getDouble("amount"));
            } catch (Exception e) {
                aSubscription.setAmount(0);
            }
            try {
                aSubscription.setIs_recurring(aResultSet.getString("is_recurring"));
            } catch (Exception e) {
                aSubscription.setIs_recurring("");
            }
            try {
                aSubscription.setCurrent_status(aResultSet.getString("current_status"));
            } catch (Exception e) {
                aSubscription.setCurrent_status("");
            }
            try {
                aSubscription.setFrequency(aResultSet.getString("frequency"));
            } catch (Exception e) {
                aSubscription.setFrequency("");
            }
            try {
                aSubscription.setUnit_price(aResultSet.getDouble("unit_price"));
            } catch (Exception e) {
                aSubscription.setUnit_price(0);
            }
            try {
                aSubscription.setQty(aResultSet.getDouble("qty"));
            } catch (Exception e) {
                aSubscription.setQty(1);
            }
            try {
                aSubscription.setAgent(aResultSet.getString("agent"));
            } catch (Exception e) {
                aSubscription.setAgent("");
            }
            try {
                //aSubscription.setSubscription_date(aResultSet.getDate("subscription_date"));
                aSubscription.setSubscription_date(new Date(aResultSet.getTimestamp("subscription_date").getTime()));
            } catch (Exception e) {
                aSubscription.setSubscription_date(null);
            }
            try {
                //aSubscription.setRenewal_date(aResultSet.getDate("renewal_date"));
                aSubscription.setRenewal_date(new Date(aResultSet.getTimestamp("renewal_date").getTime()));
            } catch (Exception e) {
                aSubscription.setRenewal_date(null);
            }
            try {
                aSubscription.setExpiry_date(new Date(aResultSet.getTimestamp("expiry_date").getTime()));
            } catch (Exception e) {
                aSubscription.setExpiry_date(null);
            }
            try {
                //aSubscription.setAdd_date(aResultSet.getDate("add_date"));
                aSubscription.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (Exception e) {
                aSubscription.setAdd_date(null);
            }
            try {
                aSubscription.setAdded_by(aResultSet.getString("added_by"));
            } catch (Exception e) {
                aSubscription.setAdded_by("");
            }
            try {
                //aSubscription.setLast_edit_date(aResultSet.getDate("last_edit_date"));
                aSubscription.setLast_edit_date(new Date(aResultSet.getTimestamp("last_edit_date").getTime()));
            } catch (Exception e) {
                aSubscription.setLast_edit_date(null);
            }
            try {
                aSubscription.setLast_edited_by(aResultSet.getString("last_edited_by"));
            } catch (Exception e) {
                aSubscription.setLast_edited_by("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void displaySubscription(Subscription SubscriptionFrom, Subscription SubscriptionTo) {
        try {
            this.clearSubscription(SubscriptionTo);
            SubscriptionTo.setSubscription_id(SubscriptionFrom.getSubscription_id());
            SubscriptionTo.setTransactor_id(SubscriptionFrom.getTransactor_id());
            SubscriptionTo.setSubscription_category_id(SubscriptionFrom.getSubscription_category_id());
            SubscriptionTo.setItem_id(SubscriptionFrom.getItem_id());
            SubscriptionTo.setDescription(SubscriptionFrom.getDescription());
            SubscriptionTo.setAmount(SubscriptionFrom.getAmount());
            SubscriptionTo.setIs_recurring(SubscriptionFrom.getIs_recurring());
            if ("Opted Out".equals(SubscriptionFrom.getCurrent_status())) {
                SubscriptionTo.setCurrent_status(SubscriptionFrom.getCurrent_status());
            } else if (SubscriptionFrom.getExpiry_date() == null) {
                SubscriptionTo.setCurrent_status("Active");
            } else {
                if (SubscriptionFrom.getExpiry_date().after(new Date()) || SubscriptionFrom.getExpiry_date().equals(new Date())) {
                    SubscriptionTo.setCurrent_status("Active");
                } else {
                    SubscriptionTo.setCurrent_status("Expired");
                }
            }
            //SubscriptionTo.setCurrent_status(SubscriptionFrom.getCurrent_status());
            SubscriptionTo.setFrequency(SubscriptionFrom.getFrequency());
            SubscriptionTo.setUnit_price(SubscriptionFrom.getUnit_price());
            SubscriptionTo.setQty(SubscriptionFrom.getQty());
            SubscriptionTo.setAgent(SubscriptionFrom.getAgent());
            SubscriptionTo.setSubscription_date(SubscriptionFrom.getSubscription_date());
            SubscriptionTo.setRenewal_date(SubscriptionFrom.getRenewal_date());
            SubscriptionTo.setExpiry_date(SubscriptionFrom.getExpiry_date());
            SubscriptionTo.setAdd_date(SubscriptionFrom.getAdd_date());
            SubscriptionTo.setAdded_by(SubscriptionFrom.getAdded_by());
            SubscriptionTo.setLast_edit_date(SubscriptionFrom.getLast_edit_date());
            SubscriptionTo.setLast_edited_by(SubscriptionFrom.getLast_edited_by());
            this.setSelectedTransactor(new TransactorBean().findTransactor(SubscriptionFrom.getTransactor_id()));
            this.setSelectedSubscriptionCategory(new SubscriptionCategoryBean().getSubscriptionCategory(SubscriptionFrom.getSubscription_category_id()));
            this.setSelectedItem(new ItemBean().findItem(SubscriptionFrom.getItem_id()));
            this.setItemUnitPrice(SubscriptionFrom.getUnit_price());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void displaySubscriptionRenewal(Subscription SubscriptionFrom, Subscription SubscriptionTo) {
        try {
            this.clearSubscription(SubscriptionTo);
            SubscriptionTo.setSubscription_id(SubscriptionFrom.getSubscription_id());
            SubscriptionTo.setTransactor_id(SubscriptionFrom.getTransactor_id());
            SubscriptionTo.setSubscription_category_id(SubscriptionFrom.getSubscription_category_id());
            SubscriptionTo.setItem_id(SubscriptionFrom.getItem_id());
            SubscriptionTo.setDescription(SubscriptionFrom.getDescription());
            SubscriptionTo.setAmount(SubscriptionFrom.getAmount());
            SubscriptionTo.setIs_recurring(SubscriptionFrom.getIs_recurring());
            if ("Opted Out".equals(SubscriptionFrom.getCurrent_status())) {
                SubscriptionTo.setCurrent_status(SubscriptionFrom.getCurrent_status());
            } else if (SubscriptionFrom.getExpiry_date() == null) {
                SubscriptionTo.setCurrent_status("Active");
            } else {
                if (SubscriptionFrom.getExpiry_date().after(new Date()) || SubscriptionFrom.getExpiry_date().equals(new Date())) {
                    SubscriptionTo.setCurrent_status("Active");
                } else {
                    SubscriptionTo.setCurrent_status("Expired");
                }
            }
            //SubscriptionTo.setCurrent_status(SubscriptionFrom.getCurrent_status());
            SubscriptionTo.setFrequency(SubscriptionFrom.getFrequency());
            SubscriptionTo.setUnit_price(SubscriptionFrom.getUnit_price());
            SubscriptionTo.setQty(SubscriptionFrom.getQty());
            SubscriptionTo.setAgent(SubscriptionFrom.getAgent());
            SubscriptionTo.setSubscription_date(SubscriptionFrom.getSubscription_date());
            SubscriptionTo.setRenewal_date(SubscriptionFrom.getRenewal_date());
            //SubscriptionTo.setExpiry_date(SubscriptionFrom.getExpiry_date());
            SubscriptionTo.setAdd_date(SubscriptionFrom.getAdd_date());
            SubscriptionTo.setAdded_by(SubscriptionFrom.getAdded_by());
            SubscriptionTo.setLast_edit_date(SubscriptionFrom.getLast_edit_date());
            SubscriptionTo.setLast_edited_by(SubscriptionFrom.getLast_edited_by());
            this.setSelectedTransactor(new TransactorBean().findTransactor(SubscriptionFrom.getTransactor_id()));
            this.setSelectedSubscriptionCategory(new SubscriptionCategoryBean().getSubscriptionCategory(SubscriptionFrom.getSubscription_category_id()));
            this.setSelectedItem(new ItemBean().findItem(SubscriptionFrom.getItem_id()));
            this.setItemUnitPrice(SubscriptionFrom.getUnit_price());
            this.setOldExpiryDate(SubscriptionFrom.getExpiry_date());
            this.setSubscriptionDateRenewal(SubscriptionFrom.getSubscription_date());
            
            this.setSubscriptionRenewal(SubscriptionFrom);
            
            //set new expiry date
            this.setExpiryDateRenewal(SubscriptionTo);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void displaySubscriptionRenewal(Subscription SubscriptionFrom) {
        try {
            this.clearSubscription(this.subscriptionRenewal);
            this.subscriptionRenewal.setSubscription_id(SubscriptionFrom.getSubscription_id());
            this.subscriptionRenewal.setTransactor_id(SubscriptionFrom.getTransactor_id());
            this.subscriptionRenewal.setSubscription_category_id(SubscriptionFrom.getSubscription_category_id());
            this.subscriptionRenewal.setItem_id(SubscriptionFrom.getItem_id());
            this.subscriptionRenewal.setDescription(SubscriptionFrom.getDescription());
            this.subscriptionRenewal.setAmount(SubscriptionFrom.getAmount());
            this.subscriptionRenewal.setIs_recurring(SubscriptionFrom.getIs_recurring());
            if ("Opted Out".equals(SubscriptionFrom.getCurrent_status())) {
                this.subscriptionRenewal.setCurrent_status(SubscriptionFrom.getCurrent_status());
            } else if (SubscriptionFrom.getExpiry_date() == null) {
                this.subscriptionRenewal.setCurrent_status("Active");
            } else {
                if (SubscriptionFrom.getExpiry_date().after(new Date()) || SubscriptionFrom.getExpiry_date().equals(new Date())) {
                    this.subscriptionRenewal.setCurrent_status("Active");
                } else {
                    this.subscriptionRenewal.setCurrent_status("Expired");
                }
            }
            //this.subscriptionRenewal.setCurrent_status(SubscriptionFrom.getCurrent_status());
            this.subscriptionRenewal.setFrequency(SubscriptionFrom.getFrequency());
            this.subscriptionRenewal.setUnit_price(SubscriptionFrom.getUnit_price());
            this.subscriptionRenewal.setQty(SubscriptionFrom.getQty());
            this.subscriptionRenewal.setAgent(SubscriptionFrom.getAgent());
            this.subscriptionRenewal.setSubscription_date(SubscriptionFrom.getSubscription_date());
            this.subscriptionRenewal.setRenewal_date(SubscriptionFrom.getRenewal_date());
            //this.subscriptionRenewal.setExpiry_date(SubscriptionFrom.getExpiry_date());
            this.subscriptionRenewal.setAdd_date(SubscriptionFrom.getAdd_date());
            this.subscriptionRenewal.setAdded_by(SubscriptionFrom.getAdded_by());
            this.subscriptionRenewal.setLast_edit_date(SubscriptionFrom.getLast_edit_date());
            this.subscriptionRenewal.setLast_edited_by(SubscriptionFrom.getLast_edited_by());
            this.setSelectedTransactor(new TransactorBean().findTransactor(SubscriptionFrom.getTransactor_id()));
            this.setSelectedSubscriptionCategory(new SubscriptionCategoryBean().getSubscriptionCategory(SubscriptionFrom.getSubscription_category_id()));
            this.setSelectedItem(new ItemBean().findItem(SubscriptionFrom.getItem_id()));
            this.setItemUnitPrice(SubscriptionFrom.getUnit_price());
            this.setOldExpiryDate(SubscriptionFrom.getExpiry_date());
            this.setSubscriptionDateRenewal(SubscriptionFrom.getSubscription_date());
            
            //set new expiry date
            this.setExpiryDateRenewal(this.subscriptionRenewal);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearSubscription(Subscription aSubscription) {
        try {
            if (aSubscription != null) {
                aSubscription.setSubscription_id(0);
                aSubscription.setTransactor_id(0);
                aSubscription.setSubscription_category_id(0);
                aSubscription.setItem_id(0);
                aSubscription.setDescription("");
                aSubscription.setAmount(0);
                aSubscription.setIs_recurring("");
                aSubscription.setCurrent_status("");
                aSubscription.setFrequency("");
                aSubscription.setUnit_price(0);
                aSubscription.setQty(1);
                aSubscription.setAgent("");
                aSubscription.setSubscription_date(null);
                aSubscription.setRenewal_date(null);
                aSubscription.setExpiry_date(null);
                aSubscription.setAdd_date(null);
                aSubscription.setAdded_by("");
                aSubscription.setLast_edit_date(null);
                aSubscription.setLast_edited_by("");
                this.setSelectedTransactor(null);
                this.setSelectedSubscriptionCategory(null);
                this.setSelectedItem(null);
                this.setItemUnitPrice(0);
                this.setOldExpiryDate(null);
                this.setSubscriptionDateRenewal(null);
                this.setSubscriptionRenewal(null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearFilter() {
        try {
            this.setFilterTransactor(null);
            this.setFilterStatus("");
            this.setFilterSubscriptionCategoryId(0);
            this.setFilterExpiryDateRange(0);
            this.setFilterRecurring("");
            this.setFilterItem(null);
            this.setFilterAgent("");
            this.getFilteredSubscriptions();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setExpiryDate(Subscription aSubscription) {
        try {
            if (aSubscription.getSubscription_date() != null) {
                //get qty
                int qty = 1;
                if (aSubscription.getQty() > 0) {
                    qty = (int) aSubscription.getQty();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(aSubscription.getSubscription_date());
                if ("No".equals(aSubscription.getIs_recurring())) {
                    aSubscription.setExpiry_date(null);
                } else if ("Weekly".equals(aSubscription.getFrequency())) {
                    c.add(Calendar.DAY_OF_MONTH, 7 * qty);
                    aSubscription.setExpiry_date(c.getTime());
                } else if ("Monthly".equals(aSubscription.getFrequency())) {
                    c.add(Calendar.MONTH, 1 * qty);
                    aSubscription.setExpiry_date(c.getTime());
                } else if ("Yearly".equals(aSubscription.getFrequency())) {
                    c.add(Calendar.YEAR, 1 * qty);
                    aSubscription.setExpiry_date(c.getTime());
                } else {
                    c.add(Calendar.DAY_OF_MONTH, 7 * qty);
                    aSubscription.setExpiry_date(c.getTime());
                }
                //aSubscription.setRenewal_date(c.getTime());
                this.setCurrentStatus(aSubscription);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setExpiryDateRenewal(Subscription aSubscription) {
        try {
            if (this.getOldExpiryDate() != null) {
                //get qty
                int qty = 1;
                if (aSubscription.getQty() > 0) {
                    qty = (int) aSubscription.getQty();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(this.getOldExpiryDate());
                if ("No".equals(aSubscription.getIs_recurring())) {
                    aSubscription.setExpiry_date(null);
                } else if ("Weekly".equals(aSubscription.getFrequency())) {
                    c.add(Calendar.DAY_OF_MONTH, 7 * qty);
                    aSubscription.setExpiry_date(c.getTime());
                } else if ("Monthly".equals(aSubscription.getFrequency())) {
                    c.add(Calendar.MONTH, 1 * qty);
                    aSubscription.setExpiry_date(c.getTime());
                } else if ("Yearly".equals(aSubscription.getFrequency())) {
                    c.add(Calendar.YEAR, 1 * qty);
                    aSubscription.setExpiry_date(c.getTime());
                } else {
                    c.add(Calendar.DAY_OF_MONTH, 7 * qty);
                    aSubscription.setExpiry_date(c.getTime());
                }
                this.setCurrentStatus(aSubscription);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setItemPrice(SelectEvent event) {
        try {
            Item aItem = (Item) event.getObject();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setItemPrice(AjaxBehaviorEvent event) {
        try {
            Subscription subs = (Subscription) event.getComponent().getAttributes().get("subs");
            //Item aItem = ((Item)event.getSource());
            Object val = ((UIInput) event.getSource()).getValue();
            if (val != null) {
                Item aItem = (Item) val;
                this.itemUnitPrice = aItem.getUnitRetailsalePrice();
            } else {
                this.itemUnitPrice = 0;
            }
            //set the amount
            this.setItemAmount(subs);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setItemAmount(Subscription aSubscription) {
        try {
            double amount = aSubscription.getQty() * itemUnitPrice;
            aSubscription.setAmount(amount);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setCurrentStatus(Subscription aSubscription) {
        try {
            if (aSubscription.getExpiry_date() == null) {
                aSubscription.setCurrent_status("Active");
            } else {
                if (aSubscription.getExpiry_date().after(new Date()) || aSubscription.getExpiry_date().equals(new Date())) {
                    aSubscription.setCurrent_status("Active");
                } else {
                    aSubscription.setCurrent_status("Expired");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveSubscription(Subscription aSubscription) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();
            String msgV = "";
            String msgS = "";
            long status = 0;

            if (aSubscription.getSubscription_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "129", "Add") == 0) {
                msgV = "Not Allowed to Access this Function";
            } else if (aSubscription.getSubscription_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "129", "Edit") == 0) {
                msgV = "Not Allowed to Access this Function";
            }
            if (msgV.length() > 0) {
                this.setActionMessage(ub.translateWordsInText(BaseName, msgV));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msgV)));
            } else {
                status = this.insertUpdateSubscription(aSubscription);
                if (status > 0) {
                    msgS = "Saved Successfully";
                    this.clearSubscription(aSubscription);
                    this.getFilteredSubscriptions();
                } else {
                    msgS = "Subscription Not Saved";
                }
                this.setActionMessage(ub.translateWordsInText(BaseName, msgS));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msgS)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void optOut(Subscription aSubscription) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();
            String msgV = "";
            String msgS = "";
            long status = 0;

            if (aSubscription.getSubscription_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "129", "Add") == 0) {
                msgV = "Not Allowed to Access this Function";
            } else if (aSubscription.getSubscription_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "129", "Edit") == 0) {
                msgV = "Not Allowed to Access this Function";
            }
            if (msgV.length() > 0) {
                this.setActionMessage(ub.translateWordsInText(BaseName, msgV));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msgV)));
            } else {
                aSubscription.setCurrent_status("Opted Out");
                this.selectedTransactor = new TransactorBean().findTransactor(aSubscription.getTransactor_id());
                this.selectedSubscriptionCategory = new SubscriptionCategoryBean().getSubscriptionCategory(aSubscription.getSubscription_category_id());
                this.selectedItem = new ItemBean().findItem(aSubscription.getItem_id());
                this.itemUnitPrice = aSubscription.getUnit_price();
                status = this.insertUpdateSubscription(aSubscription);
                if (status > 0) {
                    msgS = "Saved Successfully";
                    this.clearSubscription(aSubscription);
                    this.getFilteredSubscriptions();
                } else {
                    msgS = "Subscription Not Saved";
                }
                this.setActionMessage(ub.translateWordsInText(BaseName, msgS));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msgS)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void renew(Subscription aSubscription) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();
            String msgV = "";
            String msgS = "";
            long status = 0;

            if (aSubscription.getSubscription_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "129", "Add") == 0) {
                msgV = "Not Allowed to Access this Function";
            } else if (aSubscription.getSubscription_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "129", "Edit") == 0) {
                msgV = "Not Allowed to Access this Function";
            }
            if (msgV.length() > 0) {
                this.setActionMessage(ub.translateWordsInText(BaseName, msgV));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msgV)));
            } else {
                this.subscriptionRenewal.setQty(aSubscription.getQty());
                this.subscriptionRenewal.setUnit_price(aSubscription.getUnit_price());
                this.subscriptionRenewal.setAmount(aSubscription.getAmount());
                this.subscriptionRenewal.setRenewal_date(new Date());
                this.subscriptionRenewal.setExpiry_date(aSubscription.getExpiry_date());
                this.subscriptionRenewal.setCurrent_status("Active");
                //aSubscription.setRenewal_date(new Date());
                //aSubscription.setSubscription_date(this.getSubscriptionDateRenewal());
                this.selectedTransactor = new TransactorBean().findTransactor(this.subscriptionRenewal.getTransactor_id());
                this.selectedSubscriptionCategory = new SubscriptionCategoryBean().getSubscriptionCategory(this.subscriptionRenewal.getSubscription_category_id());
                this.selectedItem = new ItemBean().findItem(this.subscriptionRenewal.getItem_id());
                this.itemUnitPrice = aSubscription.getUnit_price();
                status = this.insertUpdateSubscription(this.subscriptionRenewal);
                if (status > 0) {
                    msgS = "Saved Successfully";
                    this.clearSubscription(aSubscription);
                    this.getFilteredSubscriptions();
                } else {
                    msgS = "Subscription Not Saved";
                }
                this.setActionMessage(ub.translateWordsInText(BaseName, msgS));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msgS)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public long insertUpdateSubscription(Subscription aSubscription) {
        String sql = null;
        long status = 0;
        int returnedSubscriptionId = 0;
        if (aSubscription.getSubscription_id() == 0) {
            sql = "{call sp_insert_subscription(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (aSubscription.getSubscription_id() > 0) {
            sql = "{call sp_update_subscription(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (aSubscription.getSubscription_id() == 0) {
                cs.setLong(1, this.selectedTransactor.getTransactorId());
                cs.setInt(2, this.selectedSubscriptionCategory.getSubscription_category_id());
                cs.setLong(3, this.selectedItem.getItemId());
                cs.setString(4, aSubscription.getDescription());
                cs.setDouble(5, aSubscription.getAmount());
                cs.setString(6, aSubscription.getIs_recurring());
                cs.setString(7, aSubscription.getCurrent_status());
                if ("No".equals(aSubscription.getIs_recurring())) {
                    cs.setString(8, null);
                } else {
                    cs.setString(8, aSubscription.getFrequency());
                }
                //cs.setDouble(9, aSubscription.getUnit_price());
                cs.setDouble(9, this.itemUnitPrice);
                cs.setDouble(10, aSubscription.getQty());
                cs.setString(11, aSubscription.getAgent());
                cs.setTimestamp(12, new java.sql.Timestamp(aSubscription.getSubscription_date().getTime()));
                cs.setTimestamp(13, null);
                if ("No".equals(aSubscription.getIs_recurring())) {
                    cs.setTimestamp(14, null);
                } else {
                    cs.setTimestamp(14, new java.sql.Timestamp(aSubscription.getExpiry_date().getTime()));
                }
                cs.setTimestamp(15, new java.sql.Timestamp(new java.util.Date().getTime()));
                cs.setString(16, new GeneralUserSetting().getCurrentUser().getUserName());
                cs.setDate(17, null);
                cs.setString(18, aSubscription.getLast_edited_by());
                cs.setInt(19, returnedSubscriptionId);

                cs.executeUpdate();
                status = 1;
                //get Id of newly inserted subscription
                returnedSubscriptionId = cs.getInt(19);

                //save subscription log
                this.saveSubscriptionLog(returnedSubscriptionId, "Subscribed");

            } else if (aSubscription.getSubscription_id() > 0) {
                cs.setInt(1, aSubscription.getSubscription_id());
                cs.setLong(2, this.selectedTransactor.getTransactorId());
                cs.setInt(3, this.selectedSubscriptionCategory.getSubscription_category_id());
                cs.setLong(4, this.selectedItem.getItemId());
                cs.setString(5, aSubscription.getDescription());
                cs.setDouble(6, aSubscription.getAmount());
                cs.setString(7, aSubscription.getIs_recurring());
                cs.setString(8, aSubscription.getCurrent_status());
                if ("No".equals(aSubscription.getIs_recurring())) {
                    cs.setString(9, null);
                } else {
                    cs.setString(9, aSubscription.getFrequency());
                }
                //cs.setDouble(10, aSubscription.getUnit_price());
                cs.setDouble(10, this.itemUnitPrice);
                cs.setDouble(11, aSubscription.getQty());
                cs.setString(12, aSubscription.getAgent());
                cs.setTimestamp(13, new java.sql.Timestamp(aSubscription.getSubscription_date().getTime()));
                if (aSubscription.getRenewal_date() != null) {
                    cs.setTimestamp(14, new java.sql.Timestamp(aSubscription.getRenewal_date().getTime()));
                } else {
                    cs.setTimestamp(14, null);
                }
                if ("No".equals(aSubscription.getIs_recurring())) {
                    cs.setTimestamp(15, null);
                } else {
                    cs.setTimestamp(15, new java.sql.Timestamp(aSubscription.getExpiry_date().getTime()));
                }
                cs.setTimestamp(16, new java.sql.Timestamp(aSubscription.getAdd_date().getTime()));
                cs.setString(17, aSubscription.getAdded_by());
                cs.setTimestamp(18, new java.sql.Timestamp(new java.util.Date().getTime()));
                cs.setString(19, new GeneralUserSetting().getCurrentUser().getUserName());
                cs.executeUpdate();
                status = 1;

                //save subscription log                
                String action = aSubscription.getCurrent_status().equals("Opted Out") || aSubscription.getCurrent_status().equals("Expired") ? aSubscription.getCurrent_status() : "Renewed";
                this.saveSubscriptionLog(aSubscription.getSubscription_id(), action);
            }
        } catch (Exception e) {
            status = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return status;
    }

    public List<Subscription> getSubscriptionAll() {
        String sql;
        //sql = "select * from subscription";
        sql = "{call sp_search_subscription_by_none()}";
        ResultSet rs;
        Subscriptions = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription subscription = new Subscription();
                this.setSubscriptionFromResultset(subscription, rs);
                Subscriptions.add(subscription);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Subscriptions;
    }

    public Subscription getSubscriptionById(int aSubscriptionId) {
        String sql;
        if (aSubscriptionId > 0) {
            //sql = "SELECT * FROM subscription WHERE subscription_id = ?";
            sql = "{call sp_search_subscription_by_id(?)}";
        } else {
            return null;
        }
        ResultSet rs;
        Subscription subscription = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aSubscriptionId);
            rs = ps.executeQuery();
            if (rs.next()) {
                subscription = new Subscription();
                this.setSubscriptionFromResultset(subscription, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return subscription;
    }

    public List<Subscription> getSubscriptionByCat(int aCategoryId) {
        String sql;
        sql = "select * from subscription where subscription_category_id = ?";
        ResultSet rs;
        List<Subscription> subscriptionList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aCategoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription subscription = new Subscription();
                this.setSubscriptionFromResultset(subscription, rs);
                subscriptionList.add(subscription);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return subscriptionList;
    }

    public List<Subscription> getSubscriptionsByStatusCategoryExpiryDateRangeRecurring() {
        String sql;
        sql = "{call sp_search_subscription_by_status_category_expiryrange_recurring(?,?,?,?)}";
        ResultSet rs;
        Subscriptions = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, this.filterStatus);
            ps.setInt(2, this.filterSubscriptionCategoryId);
            ps.setInt(3, this.filterExpiryDateRange);
            ps.setString(4, this.filterRecurring);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription aSubscription = new Subscription();
                this.setSubscriptionFromResultset(aSubscription, rs);
                getSubscriptions().add(aSubscription);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return getSubscriptions();
    }

    public List<String> getUniqueSubscriptionAgents() {
        String sql;
        sql = "SELECT DISTINCT agent from subscription where agent != ''";
        ResultSet rs;
        uniqueAgentList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                String agent;
                try {
                    agent = rs.getString("agent");
                } catch (Exception e) {
                    agent = "";
                }
                uniqueAgentList.add(agent);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return uniqueAgentList;
    }

    public void getFilteredSubscriptions() {
        String sql;
        sql = "select * from subscription where subscription_id > 0";
        String wheresql = "";
        String ordersql = " ORDER BY subscription_id DESC";
        if (this.filterTransactor != null) {
            wheresql = wheresql + " AND transactor_id=" + this.filterTransactor.getTransactorId();
        }
        if (this.filterStatus.length() > 0) {
            wheresql = wheresql + " AND current_status='" + this.filterStatus + "'";
        }
        if (this.filterSubscriptionCategoryId > 0) {
            wheresql = wheresql + " AND subscription_category_id=" + this.filterSubscriptionCategoryId;
        }
        if (this.filterExpiryDateRange > 0) {
            //wheresql = wheresql + " AND timestampdiff(MONTH, NOW(),expiry_date) =" + this.filterExpiryDateRange;
            if (this.filterExpiryDateRange == 30) {
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) >" + 0;
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) <=" + this.filterExpiryDateRange;
            } else if (this.filterExpiryDateRange == 60) {
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) >" + 30;
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) <=" + this.filterExpiryDateRange;
            } else if (this.filterExpiryDateRange == 90) {
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) >" + 60;
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) <=" + this.filterExpiryDateRange;
            } else {
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) > " + this.filterExpiryDateRange;
            }
        }
        if (this.filterRecurring.length() > 0) {
            wheresql = wheresql + " AND is_recurring='" + this.filterRecurring + "'";
        }
        if (this.filterItem != null) {
            wheresql = wheresql + " AND item_id=" + this.filterItem.getItemId();
        }
        if (this.filterAgent.length() > 0) {
            wheresql = wheresql + " AND agent='" + this.filterAgent + "'";
        }
        sql = sql + wheresql + ordersql;
        ResultSet rs;
        Subscriptions = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription aSubscription = new Subscription();
                this.setSubscriptionFromResultset(aSubscription, rs);
                getSubscriptions().add(aSubscription);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        //summary
        this.getFilteredSubscriptionsSummary();
    }

    public void getFilteredSubscriptionsSummary() {
        String sql;
        sql = "SELECT current_status, count(*) as numbers, sum(amount) as amount FROM subscription where subscription_id > 0";
        String wheresql = "";
        String groupbysum = " GROUP BY current_status";
        if (this.filterTransactor != null) {
            wheresql = wheresql + " AND transactor_id=" + this.filterTransactor.getTransactorId();
        }
        if (!this.filterStatus.isEmpty()) {
            wheresql = wheresql + " AND current_status='" + this.filterStatus + "'";
        }
        if (this.filterSubscriptionCategoryId > 0) {
            wheresql = wheresql + " AND subscription_category_id=" + this.filterSubscriptionCategoryId;
        }
        if (this.filterExpiryDateRange > 0) {
            if (this.filterExpiryDateRange == 30) {
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) >" + 0;
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) <=" + this.filterExpiryDateRange;
            } else if (this.filterExpiryDateRange == 60) {
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) >" + 30;
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) <=" + this.filterExpiryDateRange;
            } else if (this.filterExpiryDateRange == 90) {
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) >" + 60;
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) <=" + this.filterExpiryDateRange;
            } else {
                wheresql = wheresql + " AND datediff(expiry_date,NOW()) > " + this.filterExpiryDateRange;
            }
        }
        if (!this.filterRecurring.isEmpty()) {
            wheresql = wheresql + " AND is_recurring='" + this.filterRecurring + "'";
        }
        if (this.filterItem != null) {
            wheresql = wheresql + " AND item_id=" + this.filterItem.getItemId();
        }
        if (this.filterAgent.length() > 0) {
            wheresql = wheresql + " AND agent='" + this.filterAgent + "'";
        }
        sql = sql + wheresql + groupbysum;
        ResultSet rs;
        subscriptionsSummary = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription summary = new Subscription();
                try {
                    summary.setCurrent_status(rs.getString("current_status"));
                } catch (Exception e) {
                    summary.setCurrent_status("");
                }
                try {
                    summary.setDescription(rs.getString("numbers"));
                } catch (Exception e) {
                    summary.setDescription("");
                }
                try {
                    summary.setAmount(rs.getDouble("amount"));
                } catch (Exception e) {
                    summary.setAmount(0);
                }
                getSubscriptionsSummary().add(summary);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Subscription> getSubscriptionsByDescriptionCurrentStatusFrequency(String aSearchName) {
        String sql;
        sql = "{call sp_search_subscription_by_description_currenstatus_frequency(?)}";
        ResultSet rs;
        Subscriptions = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aSearchName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription aSubscription = new Subscription();
                this.setSubscriptionFromResultset(aSubscription, rs);
                getSubscriptions().add(aSubscription);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return getSubscriptions();
    }

    public void deleteSubscription(Subscription aSubscription) {
        String sql;
        sql = "delete from subscription where subscription_id=" + aSubscription.getSubscription_id();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.executeUpdate(sql);
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Deleted Successfully"));
            this.setActionMessage("Deleted successdfully");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Subscription List NOT deleted"));
            this.setActionMessage("Subscription List NOT deleted");
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveSubscriptionLog(int aSubscriptionId, String aAction) {
        try {
            Subscription_log subscriptionLog = new Subscription_log();
            subscriptionLog.setSubscription_id(aSubscriptionId);
            subscriptionLog.setAction(aAction);

            new SubscriptionLogBean().insertSubscription_log(subscriptionLog);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Subscription_log> getSubscriptionLogSubscriptionById(int aSubscriptionId) {
        try {
            this.SubscriptionsLogList = new SubscriptionLogBean().getSubscriptionlogBySubscriptionId(aSubscriptionId);

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return this.SubscriptionsLogList;
    }

    /**
     * @return the SubscriptionList
     */
    public List<Subscription> getSubscriptionList() {
        return SubscriptionList;
    }

    /**
     * @param SubscriptionList the SubscriptionList to set
     */
    public void setSubscriptionList(List<Subscription> SubscriptionList) {
        this.SubscriptionList = SubscriptionList;
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
     * @return the SearchSubscriptionNames
     */
    public String getSearchSubscriptionNames() {
        return SearchSubscriptionNames;
    }

    /**
     * @param SearchSubscriptionNames the SearchSubscriptionNames to set
     */
    public void setSearchSubscriptionNames(String SearchSubscriptionNames) {
        this.SearchSubscriptionNames = SearchSubscriptionNames;
    }

    /**
     * @param Subscriptions the Subscriptions to set
     */
    public void setSubscriptions(List<Subscription> Subscriptions) {
        this.Subscriptions = Subscriptions;
    }

    /**
     * @return the Subscriptions
     */
    public List<Subscription> getSubscriptions() {
        return Subscriptions;
    }

    /**
     * @return the selectedTransactor
     */
    public Transactor getSelectedTransactor() {
        return selectedTransactor;
    }

    /**
     * @param selectedTransactor the selectedTransactor to set
     */
    public void setSelectedTransactor(Transactor selectedTransactor) {
        this.selectedTransactor = selectedTransactor;
    }

    /**
     * @return the selectedItem
     */
    public Item getSelectedItem() {
        return selectedItem;
    }

    /**
     * @param selectedItem the selectedItem to set
     */
    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * @return the selectedSubscriptionCategory
     */
    public Subscription_category getSelectedSubscriptionCategory() {
        return selectedSubscriptionCategory;
    }

    /**
     * @param selectedSubscriptionCategory the selectedSubscriptionCategory to
     * set
     */
    public void setSelectedSubscriptionCategory(Subscription_category selectedSubscriptionCategory) {
        this.selectedSubscriptionCategory = selectedSubscriptionCategory;
    }

    /**
     * @return the filterStatus
     */
    public String getFilterStatus() {
        return filterStatus;
    }

    /**
     * @param filterStatus the filterStatus to set
     */
    public void setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
    }

    /**
     * @return the filterSubscriptionCategoryId
     */
    public int getFilterSubscriptionCategoryId() {
        return filterSubscriptionCategoryId;
    }

    /**
     * @param filterSubscriptionCategoryId the filterSubscriptionCategoryId to
     * set
     */
    public void setFilterSubscriptionCategoryId(int filterSubscriptionCategoryId) {
        this.filterSubscriptionCategoryId = filterSubscriptionCategoryId;
    }

    /**
     * @return the filterExpiryDateRange
     */
    public int getFilterExpiryDateRange() {
        return filterExpiryDateRange;
    }

    /**
     * @param filterExpiryDateRange the filterExpiryDateRange to set
     */
    public void setFilterExpiryDateRange(int filterExpiryDateRange) {
        this.filterExpiryDateRange = filterExpiryDateRange;
    }

    /**
     * @return the filterRecurring
     */
    public String getFilterRecurring() {
        return filterRecurring;
    }

    /**
     * @param filterRecurring the filterRecurring to set
     */
    public void setFilterRecurring(String filterRecurring) {
        this.filterRecurring = filterRecurring;
    }

    /**
     * @return the SubscriptionsLogList
     */
    public List<Subscription_log> getSubscriptionsLogList() {
        return SubscriptionsLogList;
    }

    /**
     * @param SubscriptionsLogList the SubscriptionsLogList to set
     */
    public void setSubscriptionsLogList(List<Subscription_log> SubscriptionsLogList) {
        this.SubscriptionsLogList = SubscriptionsLogList;
    }

    /**
     * @return the filterTransactor
     */
    public Transactor getFilterTransactor() {
        return filterTransactor;
    }

    /**
     * @param filterTransactor the filterTransactor to set
     */
    public void setFilterTransactor(Transactor filterTransactor) {
        this.filterTransactor = filterTransactor;
    }

    /**
     * @return the subscriptionsSummary
     */
    public List<Subscription> getSubscriptionsSummary() {
        return subscriptionsSummary;
    }

    /**
     * @param subscriptionsSummary the subscriptionsSummary to set
     */
    public void setSubscriptionsSummary(List<Subscription> subscriptionsSummary) {
        this.subscriptionsSummary = subscriptionsSummary;
    }

    /**
     * @return the menuItemBean
     */
    public MenuItemBean getMenuItemBean() {
        return menuItemBean;
    }

    /**
     * @param menuItemBean the menuItemBean to set
     */
    public void setMenuItemBean(MenuItemBean menuItemBean) {
        this.menuItemBean = menuItemBean;
    }

    /**
     * @return the filterItem
     */
    public Item getFilterItem() {
        return filterItem;
    }

    /**
     * @param filterItem the filterItem to set
     */
    public void setFilterItem(Item filterItem) {
        this.filterItem = filterItem;
    }

    /**
     * @return the filterAgents
     */
    public String getFilterAgent() {
        return filterAgent;
    }

    /**
     * @param filterAgent the filterAgents to set
     */
    public void setFilterAgent(String filterAgent) {
        this.filterAgent = filterAgent;
    }

    /**
     * @return the uniqueAgentList
     */
    public List<String> getUniqueAgentList() {
        return uniqueAgentList;
    }

    /**
     * @param uniqueAgentList the uniqueAgentList to set
     */
    public void setUniqueAgentList(List<String> uniqueAgentList) {
        this.uniqueAgentList = uniqueAgentList;
    }

    /**
     * @return the itemUnitPrice
     */
    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    /**
     * @param itemUnitPrice the itemUnitPrice to set
     */
    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    /**
     * @return the oldExpiryDate
     */
    public Date getOldExpiryDate() {
        return oldExpiryDate;
    }

    /**
     * @param oldExpiryDate the oldExpiryDate to set
     */
    public void setOldExpiryDate(Date oldExpiryDate) {
        this.oldExpiryDate = oldExpiryDate;
    }

    /**
     * @return the subscriptionDateRenewal
     */
    public Date getSubscriptionDateRenewal() {
        return subscriptionDateRenewal;
    }

    /**
     * @param subscriptionDateRenewal the subscriptionDateRenewal to set
     */
    public void setSubscriptionDateRenewal(Date subscriptionDateRenewal) {
        this.subscriptionDateRenewal = subscriptionDateRenewal;
    }

    /**
     * @return the subscriptionRenewal
     */
    public Subscription getSubscriptionRenewal() {
        return subscriptionRenewal;
    }

    /**
     * @param subscriptionRenewal the subscriptionRenewal to set
     */
    public void setSubscriptionRenewal(Subscription subscriptionRenewal) {
        this.subscriptionRenewal = subscriptionRenewal;
    }
}
