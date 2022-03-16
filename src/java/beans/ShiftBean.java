package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.Shift;
import entities.GroupRight;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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
public class ShiftBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(ShiftBean.class.getName());

    private List<Shift> Shifts;
    private String ActionMessage = null;
    private Shift SelectedShift = null;
    private int SelectedShiftId;
    private String SearchShiftName = "";
    private Date startTime = null;
    private Date endTime = null;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setShiftFromResultset(Shift aShift, ResultSet aResultSet) {
        try {
            try {
                aShift.setShift_id(aResultSet.getInt("shift_id"));
            } catch (Exception e) {
                aShift.setShift_id(0);
            }
            try {
                aShift.setShift_name(aResultSet.getString("shift_name"));
            } catch (Exception e) {
                aShift.setShift_name("");
            }
            try {
                aShift.setDescription(aResultSet.getString("description"));
            } catch (Exception e) {
                aShift.setDescription("");
            }
            try {
                aShift.setStart_time(aResultSet.getTime("start_time").toLocalTime());
            } catch (Exception e) {
                aShift.setStart_time(null);
            }
            try {
                aShift.setEnd_time(aResultSet.getTime("end_time").toLocalTime());
            } catch (Exception e) {
                aShift.setEnd_time(null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveShift(Shift aShift) {
        try {
            if (this.getStartTime() != null) {
                aShift.setStart_time(LocalDateTime.ofInstant(this.getStartTime().toInstant(), ZoneId.systemDefault()).toLocalTime());
            }
            if (this.getEndTime() != null) {
                aShift.setEnd_time(LocalDateTime.ofInstant(this.getEndTime().toInstant(), ZoneId.systemDefault()).toLocalTime());
            }
        } catch (Exception e) {
        }

        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg;
        String sql = null;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (aShift.getShift_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (aShift.getShift_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (aShift.getShift_name().length() <= 0 || aShift.getStart_time() == null || aShift.getEnd_time() == null) {
            msg = "Shift Name, Start and End time Cannot be Empty";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            if (aShift.getShift_id() == 0) {
                sql = "{call sp_insert_shift(?,?,?,?)}";
            } else if (aShift.getShift_id() > 0) {
                sql = "{call sp_update_shift(?,?,?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (aShift.getShift_id() == 0) {
                    cs.setString(1, aShift.getShift_name());
                    cs.setString(2, aShift.getDescription());
                    cs.setTime(3, Time.valueOf(aShift.getStart_time()));
                    cs.setTime(4, Time.valueOf(aShift.getEnd_time()));
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearShift(aShift);
                } else if (aShift.getShift_id() > 0) {
                    cs.setInt(1, aShift.getShift_id());
                    cs.setString(2, aShift.getShift_name());
                    cs.setString(3, aShift.getDescription());
                    cs.setTime(4, Time.valueOf(aShift.getStart_time()));
                    cs.setTime(5, Time.valueOf(aShift.getEnd_time()));
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearShift(aShift);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Shift NOT saved"));
            }
        }

    }

    public Shift getShift(int aShiftId) {
        String sql = "{call sp_search_shift_by_id(?)}";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aShiftId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Shift shift = new Shift();
                this.setShiftFromResultset(shift, rs);
                return shift;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public void deleteShift() {
        this.deleteShiftById(this.SelectedShiftId);
    }

    public void deleteShiftByObject(Shift shift) {
        this.deleteShiftById(shift.getShift_id());
    }

    public void deleteShiftById(int ShiftId) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String sql = "DELETE FROM shift WHERE shift_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, ShiftId);
                ps.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Deleted Successfully"));
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Shift Not Deleted"));
            }
        }
    }

    public void displayShift(Shift ShiftFrom, Shift ShiftTo) {
        try {
            this.clearShift(ShiftTo);
            ShiftTo.setShift_id(ShiftFrom.getShift_id());
            ShiftTo.setShift_name(ShiftFrom.getShift_name());
            ShiftTo.setDescription(ShiftFrom.getDescription());
            ShiftTo.setStart_time(ShiftFrom.getStart_time());
            ShiftTo.setStart_time(ShiftFrom.getStart_time());
            //set date from localTime
            Instant startDateinstant = ShiftFrom.getStart_time().atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
            this.setStartTime(Date.from(startDateinstant));
            Instant endDateinstant = ShiftFrom.getEnd_time().atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
            this.setEndTime(Date.from(endDateinstant));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearShift(Shift shift) {
        try {
            shift.setShift_id(0);
            shift.setShift_name("");
            shift.setDescription("");
            shift.setStart_time(null);
            shift.setEnd_time(null);
            this.setStartTime(null);
            this.setEndTime(null);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Shift> getShift() {
        String sql;
        sql = "{call sp_search_shift_by_none()}";
        ResultSet rs;
        Shifts = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Shift shift = new Shift();
                this.setShiftFromResultset(shift, rs);
                Shifts.add(shift);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Shifts;
    }

    /**
     * @param aShiftName
     * @return the Shifts
     */
    public List<Shift> getShiftByShiftName(String aShiftName) {
        String sql;
        sql = "{call sp_search_shift_by_name(?)}";
        ResultSet rs;
        Shifts = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aShiftName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Shift shift = new Shift();
                this.setShiftFromResultset(shift, rs);
                Shifts.add(shift);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Shifts;
    }

    /**
     * @param Shifts the Shifts to set
     */
    public void setShifts(List<Shift> Shifts) {
        this.Shifts = Shifts;
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
     * @return the SelectedShift
     */
    public Shift getSelectedShift() {
        return SelectedShift;
    }

    /**
     * @param SelectedShift the SelectedShift to set
     */
    public void setSelectedShift(Shift SelectedShift) {
        this.SelectedShift = SelectedShift;
    }

    /**
     * @return the SelectedShiftId
     */
    public int getSelectedShiftId() {
        return SelectedShiftId;
    }

    /**
     * @param SelectedShiftId the SelectedShiftId to set
     */
    public void setSelectedShiftId(int SelectedShiftId) {
        this.SelectedShiftId = SelectedShiftId;
    }

    /**
     * @return the SearchShiftName
     */
    public String getSearchShiftName() {
        return SearchShiftName;
    }

    /**
     * @param SearchShiftName the SearchShiftName to set
     */
    public void setSearchShiftName(String SearchShiftName) {
        this.SearchShiftName = SearchShiftName;
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
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
