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
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sessions.GeneralUserSetting;
import utilities.UtilityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
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
    private int filterRenewalDateRange = 0;
    private String filterRecurring = "";
    private Transactor filterTransactor;
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
            SubscriptionTo.setCurrent_status(SubscriptionFrom.getCurrent_status());
            SubscriptionTo.setFrequency(SubscriptionFrom.getFrequency());
            SubscriptionTo.setSubscription_date(SubscriptionFrom.getSubscription_date());
            SubscriptionTo.setRenewal_date(SubscriptionFrom.getRenewal_date());
            SubscriptionTo.setAdd_date(SubscriptionFrom.getAdd_date());
            SubscriptionTo.setAdded_by(SubscriptionFrom.getAdded_by());
            SubscriptionTo.setLast_edit_date(SubscriptionFrom.getLast_edit_date());
            SubscriptionTo.setLast_edited_by(SubscriptionFrom.getLast_edited_by());
            this.setSelectedTransactor(new TransactorBean().findTransactor(SubscriptionFrom.getTransactor_id()));
            this.setSelectedSubscriptionCategory(new SubscriptionCategoryBean().getSubscriptionCategory(SubscriptionFrom.getSubscription_category_id()));
            this.setSelectedItem(new ItemBean().findItem(SubscriptionFrom.getItem_id()));
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
                aSubscription.setSubscription_date(null);
                aSubscription.setRenewal_date(null);
                aSubscription.setAdd_date(null);
                aSubscription.setAdded_by("");
                aSubscription.setLast_edit_date(null);
                aSubscription.setLast_edited_by("");
                this.setSelectedTransactor(null);
                this.setSelectedSubscriptionCategory(null);
                this.setSelectedItem(null);
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
            this.setFilterRenewalDateRange(0);
            this.setFilterRecurring("");
            //this.getSubscriptionsByDescriptionCurrentStatusFrequency(this.SearchSubscriptionNames);
            this.getFilteredSubscriptions();
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

    public long insertUpdateSubscription(Subscription aSubscription) {
        String sql = null;
        long status = 0;
        int returnedSubscriptionId = 0;
        if (aSubscription.getSubscription_id() == 0) {
            sql = "{call sp_insert_subscription(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (aSubscription.getSubscription_id() > 0) {
            sql = "{call sp_update_subscription(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
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
                cs.setString(8, aSubscription.getFrequency());
                cs.setTimestamp(9, new java.sql.Timestamp(aSubscription.getSubscription_date().getTime()));
                cs.setTimestamp(10, new java.sql.Timestamp(aSubscription.getRenewal_date().getTime()));
                cs.setTimestamp(11, new java.sql.Timestamp(new java.util.Date().getTime()));
                cs.setString(12, new GeneralUserSetting().getCurrentUser().getUserName());
                cs.setDate(13, null);
                cs.setString(14, aSubscription.getLast_edited_by());
                cs.setInt(15, returnedSubscriptionId);

                cs.executeUpdate();
                status = 1;
                //get Id of newly inserted subscription
                returnedSubscriptionId = cs.getInt(15);

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
                cs.setString(9, aSubscription.getFrequency());
                cs.setTimestamp(10, new java.sql.Timestamp(aSubscription.getSubscription_date().getTime()));
                cs.setTimestamp(11, new java.sql.Timestamp(aSubscription.getRenewal_date().getTime()));
                cs.setTimestamp(12, new java.sql.Timestamp(new java.util.Date().getTime()));
                cs.setString(13, aSubscription.getAdded_by());
                cs.setTimestamp(14, new java.sql.Timestamp(new java.util.Date().getTime()));
                cs.setString(15, new GeneralUserSetting().getCurrentUser().getUserName());
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

    public List<Subscription> getSubscriptionsByStatusCategoryRenewalDateRangeRecurring() {
        String sql;
        sql = "{call sp_search_subscription_by_status_category_renewalrange_recurring(?,?,?,?)}";
        //sql = "select * from subscription where current_status = ? AND subscription_category_id = ? AND timestampdiff(MONTH, NOW(),renewal_date) = ? AND is_recurring = ?";
        ResultSet rs;
        Subscriptions = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, this.filterStatus);
            ps.setInt(2, this.filterSubscriptionCategoryId);
            ps.setInt(3, this.filterRenewalDateRange);
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

    public void getFilteredSubscriptions() {
        String sql;
        sql = "select * from subscription where subscription_id > 0";
        //String sqlsum = "SELECT current_status, count(*) as numbers, sum(amount) as amount FROM subscription where subscription_id > 0";
        String wheresql = "";
        if (this.filterTransactor != null) {
            wheresql = wheresql + " AND transactor_id=" + this.filterTransactor.getTransactorId();
        }
        if (this.filterStatus.length() > 0) {
            wheresql = wheresql + " AND current_status='" + this.filterStatus + "'";
        }
        if (this.filterSubscriptionCategoryId > 0) {
            wheresql = wheresql + " AND subscription_category_id=" + this.filterSubscriptionCategoryId;
        }
        if (this.filterRenewalDateRange > 0) {
            //wheresql = wheresql + " AND timestampdiff(MONTH, NOW(),renewal_date) =" + this.filterRenewalDateRange;
            if (this.filterRenewalDateRange == 30) {
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) >" + 0;
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) <=" + this.filterRenewalDateRange;
            } else if (this.filterRenewalDateRange == 60) {
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) >" + 30;
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) <=" + this.filterRenewalDateRange;
            } else if (this.filterRenewalDateRange == 90) {
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) >" + 60;
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) <=" + this.filterRenewalDateRange;
            } else {
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) > " + this.filterRenewalDateRange;
            }
        }
        if (this.filterRecurring.length() > 0) {
            wheresql = wheresql + " AND is_recurring='" + this.filterRecurring + "'";
        }
        sql = sql + wheresql;
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
        if (this.filterRenewalDateRange > 0) {
            //wheresql = wheresql + " AND timestampdiff(MONTH, NOW(),renewal_date) =" + this.filterRenewalDateRange;
            if (this.filterRenewalDateRange == 30) {
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) >" + 0;
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) <=" + this.filterRenewalDateRange;
            } else if (this.filterRenewalDateRange == 60) {
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) >" + 30;
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) <=" + this.filterRenewalDateRange;
            } else if (this.filterRenewalDateRange == 90) {
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) >" + 60;
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) <=" + this.filterRenewalDateRange;
            } else {
                wheresql = wheresql + " AND datediff(renewal_date,NOW()) > " + this.filterRenewalDateRange;
            }
        }
        if (!this.filterRecurring.isEmpty()) {
            wheresql = wheresql + " AND is_recurring='" + this.filterRecurring + "'";
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
     * @return the filterRenewalDateRange
     */
    public int getFilterRenewalDateRange() {
        return filterRenewalDateRange;
    }

    /**
     * @param filterRenewalDateRange the filterRenewalDateRange to set
     */
    public void setFilterRenewalDateRange(int filterRenewalDateRange) {
        this.filterRenewalDateRange = filterRenewalDateRange;
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
}
