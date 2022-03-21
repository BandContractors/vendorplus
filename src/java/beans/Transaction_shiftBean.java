package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.Transaction_shift;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utilities.UtilityBean;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emmanuelmuwonge
 */
@ManagedBean
@SessionScoped
public class Transaction_shiftBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Transaction_shiftBean.class.getName());

    private List<Transaction_shift> Transaction_shiftList;
    private String ActionMessage = null;
    private Transaction_shift SelectedTransaction_shift = null;
    private int SelectedTransaction_shift_id;
    private String SearchTransaction_id = "";
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setTransaction_shiftFromResultset(Transaction_shift aTransaction_shift, ResultSet aResultSet) {
        try {
            try {
                aTransaction_shift.setTransaction_shift_id(aResultSet.getLong("transaction_shift_id"));
            } catch (Exception e) {
                aTransaction_shift.setTransaction_shift_id(0);
            }
            try {
                aTransaction_shift.setTransaction_id(aResultSet.getLong("transaction_id"));
            } catch (Exception e) {
                aTransaction_shift.setTransaction_id(0);
            }
            try {
                aTransaction_shift.setShift_id(aResultSet.getInt("shift_id"));
            } catch (Exception e) {
                aTransaction_shift.setShift_id(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshTransaction_shiftList() {
        String sql;
        sql = "{call sp_search_transaction_shift_by_none()}";
        ResultSet rs;
        try {
            this.Transaction_shiftList.clear();
        } catch (Exception e) {
            this.Transaction_shiftList = new ArrayList<>();
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Transaction_shift transaction_shift = new Transaction_shift();
                this.setTransaction_shiftFromResultset(transaction_shift, rs);
                this.Transaction_shiftList.add(transaction_shift);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveTransaction_shift(Transaction_shift transaction_shift) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        String sql = null;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (transaction_shift.getTransaction_shift_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (transaction_shift.getTransaction_shift_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            if (transaction_shift.getTransaction_shift_id() == 0) {
                sql = "{call sp_insert_transaction_shift(?,?)}";
            } else if (transaction_shift.getTransaction_shift_id() > 0) {
                sql = "{call sp_update_transaction_shift(?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (transaction_shift.getTransaction_shift_id() == 0) {
                    cs.setLong(1, transaction_shift.getTransaction_id());
                    cs.setInt(2, transaction_shift.getShift_id());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearTransaction_shift(transaction_shift);
                } else if (transaction_shift.getTransaction_shift_id() > 0) {
                    cs.setLong(1, transaction_shift.getTransaction_shift_id());
                    cs.setLong(2, transaction_shift.getTransaction_id());
                    cs.setInt(3, transaction_shift.getShift_id());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearTransaction_shift(transaction_shift);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Transaction Shift Not Saved"));
            }
        }
    }

    public Transaction_shift getTransaction_shift(int aTransaction_shift_id) {
        String sql = "{call sp_search_transaction_shift_by_id(?)}";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aTransaction_shift_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Transaction_shift transaction_shift = new Transaction_shift();
                this.setTransaction_shiftFromResultset(transaction_shift, rs);
                return transaction_shift;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public void deleteTransaction_shiftByObject(Transaction_shift transaction_shift) {
        this.deleteTransaction_shift(transaction_shift);
    }

    public void deleteTransaction_shift(Transaction_shift transaction_shift) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        String sql = "DELETE FROM transaction_shift WHERE transaction_shift_id=?";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (null == transaction_shift) {

        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, transaction_shift.getTransaction_shift_id());
                ps.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Deleted Successfully"));
                this.clearTransaction_shift(transaction_shift);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Transaction Shift Not Deleted"));
            }
        }
    }

    public void displayTransaction_shift(Transaction_shift Transaction_shiftFrom, Transaction_shift Transaction_shiftTo) {
        Transaction_shiftTo.setTransaction_shift_id(Transaction_shiftFrom.getTransaction_shift_id());
        Transaction_shiftTo.setTransaction_id(Transaction_shiftFrom.getTransaction_id());
        Transaction_shiftTo.setShift_id(Transaction_shiftFrom.getShift_id());
    }

    public void clearTransaction_shift(Transaction_shift transaction_shift) {
        try {
            transaction_shift.setTransaction_shift_id(0);
            transaction_shift.setTransaction_id(0);
            transaction_shift.setShift_id(0);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Transaction_shift> getTransaction_shiftAll() {
        String sql;
        sql = "{call sp_search_transaction_shift_by_none()}";
        ResultSet rs;
        Transaction_shiftList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Transaction_shift transaction_shift = new Transaction_shift();
                this.setTransaction_shiftFromResultset(transaction_shift, rs);
                Transaction_shiftList.add(transaction_shift);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Transaction_shiftList;
    }

    /**
     * @param Transaction_shiftList the Transaction_shiftList to set
     */
    public void setTransaction_shiftList(List<Transaction_shift> Transaction_shiftList) {
        this.Transaction_shiftList = Transaction_shiftList;
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
     * @return the SelectedTransaction_shift
     */
    public Transaction_shift getSelectedTransaction_shift() {
        return SelectedTransaction_shift;
    }

    /**
     * @param SelectedTransaction_shift the SelectedTransaction_shift to set
     */
    public void setSelectedTransaction_shift(Transaction_shift SelectedTransaction_shift) {
        this.SelectedTransaction_shift = SelectedTransaction_shift;
    }

    /**
     * @return the SelectedTransaction_shift_id
     */
    public int getSelectedTransaction_shift_id() {
        return SelectedTransaction_shift_id;
    }

    /**
     * @param SelectedTransaction_shift_id the SelectedTransaction_shift_id to
     * set
     */
    public void setSelectedTransaction_shift_id(int SelectedTransaction_shift_id) {
        this.SelectedTransaction_shift_id = SelectedTransaction_shift_id;
    }

    /**
     * @return the SearchTransaction_id
     */
    public String getSearchTransaction_id() {
        return SearchTransaction_id;
    }

    /**
     * @param SearchTransaction_id the SearchTransaction_id to set
     */
    public void setSearchTransaction_id(String SearchTransaction_id) {
        this.SearchTransaction_id = SearchTransaction_id;
    }
}
