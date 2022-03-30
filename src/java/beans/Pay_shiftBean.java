package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.Pay_shift;
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
public class Pay_shiftBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Pay_shiftBean.class.getName());

    private List<Pay_shift> Pay_shiftList;
    private String ActionMessage = null;
    private Pay_shift SelectedPay_shift = null;
    private int SelectedPay_shift_id;
    private String SearchPay_id = "";
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setPay_shiftFromResultset(Pay_shift aPay_shift, ResultSet aResultSet) {
        try {
            try {
                aPay_shift.setPay_shift_id(aResultSet.getLong("pay_shift_id"));
            } catch (Exception e) {
                aPay_shift.setPay_shift_id(0);
            }
            try {
                aPay_shift.setPay_id(aResultSet.getLong("pay_id"));
            } catch (Exception e) {
                aPay_shift.setPay_id(0);
            }
            try {
                aPay_shift.setShift_id(aResultSet.getInt("shift_id"));
            } catch (Exception e) {
                aPay_shift.setShift_id(0);
            }
            try {
                aPay_shift.setPay_type_id(aResultSet.getInt("pay_type_id"));
            } catch (Exception e) {
                aPay_shift.setPay_type_id(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshPay_shiftList() {
        String sql;
        sql = "{call sp_search_pay_shift_by_none()}";
        ResultSet rs;
        try {
            this.Pay_shiftList.clear();
        } catch (Exception e) {
            this.Pay_shiftList = new ArrayList<>();
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Pay_shift pay_shift = new Pay_shift();
                this.setPay_shiftFromResultset(pay_shift, rs);
                this.Pay_shiftList.add(pay_shift);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void savePay_shift(Pay_shift pay_shift) {
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

        if (pay_shift.getPay_shift_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (pay_shift.getPay_shift_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            if (pay_shift.getPay_shift_id() == 0) {
                sql = "{call sp_insert_pay_shift(?,?,?)}";
            } else if (pay_shift.getPay_shift_id() > 0) {
                sql = "{call sp_update_pay_shift(?,?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (pay_shift.getPay_shift_id() == 0) {
                    cs.setLong(1, pay_shift.getPay_id());
                    cs.setInt(2, pay_shift.getShift_id());
                    cs.setInt(3, pay_shift.getPay_type_id());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearPay_shift(pay_shift);
                } else if (pay_shift.getPay_shift_id() > 0) {
                    cs.setLong(1, pay_shift.getPay_shift_id());
                    cs.setLong(2, pay_shift.getPay_id());
                    cs.setInt(3, pay_shift.getShift_id());
                    cs.setInt(4, pay_shift.getPay_type_id());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearPay_shift(pay_shift);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Pay Shift Not Saved"));
            }
        }
    }

    public Pay_shift getPay_shift(int aPay_shift_id) {
        String sql = "{call sp_search_pay_shift_by_id(?)}";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aPay_shift_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Pay_shift pay_shift = new Pay_shift();
                this.setPay_shiftFromResultset(pay_shift, rs);
                return pay_shift;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public void deletePay_shiftByObject(Pay_shift pay_shift) {
        this.deletePay_shift(pay_shift);
    }

    public void deletePay_shift(Pay_shift pay_shift) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        String sql = "DELETE FROM pay_shift WHERE pay_shift_id=?";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (null == pay_shift) {

        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, pay_shift.getPay_shift_id());
                ps.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Deleted Successfully"));
                this.clearPay_shift(pay_shift);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Pay Shift Not Deleted"));
            }
        }
    }

    public void displayPay_shift(Pay_shift Pay_shiftFrom, Pay_shift Pay_shiftTo) {
        try {
            Pay_shiftTo.setPay_shift_id(Pay_shiftFrom.getPay_shift_id());
            Pay_shiftTo.setPay_id(Pay_shiftFrom.getPay_id());
            Pay_shiftTo.setShift_id(Pay_shiftFrom.getShift_id());
            Pay_shiftTo.setPay_type_id(Pay_shiftFrom.getPay_type_id());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearPay_shift(Pay_shift aPay_shift) {
        try {
            aPay_shift.setPay_shift_id(0);
            aPay_shift.setPay_id(0);
            aPay_shift.setShift_id(0);
            aPay_shift.setPay_type_id(0);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Pay_shift> getPay_shiftAll() {
        String sql;
        sql = "{call sp_search_pay_shift_by_none()}";
        ResultSet rs;
        Pay_shiftList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Pay_shift pay_shift = new Pay_shift();
                this.setPay_shiftFromResultset(pay_shift, rs);
                Pay_shiftList.add(pay_shift);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Pay_shiftList;
    }

    /**
     * @param Pay_shiftList the Pay_shiftList to set
     */
    public void setPay_shiftList(List<Pay_shift> Pay_shiftList) {
        this.Pay_shiftList = Pay_shiftList;
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
     * @return the SelectedPay_shift
     */
    public Pay_shift getSelectedPay_shift() {
        return SelectedPay_shift;
    }

    /**
     * @param SelectedPay_shift the SelectedPay_shift to set
     */
    public void setSelectedPay_shift(Pay_shift SelectedPay_shift) {
        this.SelectedPay_shift = SelectedPay_shift;
    }

    /**
     * @return the SelectedPay_shift_id
     */
    public int getSelectedPay_shift_id() {
        return SelectedPay_shift_id;
    }

    /**
     * @param SelectedPay_shift_id the SelectedPay_shift_id to set
     */
    public void setSelectedPay_shift_id(int SelectedPay_shift_id) {
        this.SelectedPay_shift_id = SelectedPay_shift_id;
    }

    /**
     * @return the SearchPay_id
     */
    public String getSearchPay_id() {
        return SearchPay_id;
    }

    /**
     * @param SearchPay_id the SearchPay_id to set
     */
    public void setSearchPay_id(String SearchPay_id) {
        this.SearchPay_id = SearchPay_id;
    }
}
