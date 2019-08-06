package beans;

import connections.DBConnection;
import entities.Site;
import entities.Transactor;
import java.io.Serializable;
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "siteBean")
@SessionScoped
public class SiteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = null;
    private int ActionMessageType = 0;//0-Not specified; 1-Pass; 2-Fail
    private List<Site> Sites = new ArrayList<>();
    private Site SiteObject = new Site();
    private Transactor SearchTransactor;
    private List<Site> SitesList;

    public void setSiteFromResultset(Site aSite, ResultSet aResultSet) {
        try {
            try {
                aSite.setSite_id(aResultSet.getLong("site_id"));
            } catch (NullPointerException npe) {
                aSite.setSite_id(0);
            }
            try {
                aSite.setTransactor_id(aResultSet.getLong("transactor_id"));
            } catch (NullPointerException npe) {
                aSite.setTransactor_id(0);
            }
            try {
                aSite.setSite_name(aResultSet.getString("site_name"));
            } catch (NullPointerException npe) {
                aSite.setSite_name("");
            }
            try {
                aSite.setCountry(aResultSet.getString("country"));
            } catch (NullPointerException npe) {
                aSite.setCountry("");
            }
            try {
                aSite.setDistrict(aResultSet.getString("district"));
            } catch (NullPointerException npe) {
                aSite.setDistrict("");
            }
            try {
                aSite.setVillage(aResultSet.getString("village"));
            } catch (NullPointerException npe) {
                aSite.setVillage("");
            }
        } catch (SQLException se) {
            System.err.println("setSiteFromResultset:" + se.getMessage());
        }
    }

    public void saveSiteCall(Site aSite) {
        String status = "";
        status = this.saveSite(aSite);
        if (status.length() == 0) {
            this.ActionMessage = "Saved Successfully!";
            this.ActionMessageType = 1;
        } else {
            this.ActionMessage = status;
            this.ActionMessageType = 2;
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(status));
        }
    }

    public String saveSite(Site aSite) {
        String sql = "";
        String x = "";
        if (aSite.getTransactor_id() == 0) {
            x = "Please select Customer...";
        } else if (aSite.getSite_name().length() <= 0) {
            x = "Please specify Site Name...";
        } else {
            if (aSite.getSite_id() == 0) {//Insert
                sql = "INSERT INTO site(transactor_id,site_name,country,district,village) VALUES(?,?,?,?,?)";
            } else {//Update
                sql = "UPDATE site SET transactor_id=?,site_name=?,country=?,district=?,village=? WHERE site_id=?";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                try {
                    ps.setLong(1, aSite.getTransactor_id());
                } catch (NullPointerException npe) {
                    ps.setLong(1, 0);
                }
                try {
                    ps.setString(2, aSite.getSite_name());
                } catch (NullPointerException npe) {
                    ps.setString(2, "");
                }
                try {
                    ps.setString(3, aSite.getCountry());
                } catch (NullPointerException npe) {
                    ps.setString(3, "");
                }
                try {
                    ps.setString(4, aSite.getDistrict());
                } catch (NullPointerException npe) {
                    ps.setString(4, "");
                }
                try {
                    ps.setString(5, aSite.getVillage());
                } catch (NullPointerException npe) {
                    ps.setString(5, "");
                }
                if (aSite.getSite_id() > 0) {
                    try {
                        ps.setLong(6, aSite.getSite_id());
                    } catch (NullPointerException npe) {
                        ps.setLong(6, 0);
                    }
                }
                ps.executeUpdate();
                this.clearSite(aSite);
                x = "";
            } catch (Exception e) {
                x = "An error has occured! Site not Saved";
                e.printStackTrace();
            }
        }
        return x;
    }

    public Site getSiteById(long aSite_id) {
        String sql;
        sql = "SELECT * FROM site WHERE site_id=" + aSite_id;
        ResultSet rs = null;
        Site obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Site();
                this.setSiteFromResultset(obj, rs);
            }
        } catch (SQLException se) {
            System.err.println("getSite:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getSiteById:" + ex.getMessage());
                }
            }
        }
        return obj;
    }

    public void setSiteById(Site aSite, int aSite_id) {
        String sql;
        sql = "SELECT * FROM site WHERE site_id=" + aSite_id;
        if (null == aSite) {
            aSite = new Site();
        }
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                this.setSiteFromResultset(aSite, rs);
            }
        } catch (SQLException se) {
            System.err.println("setSite:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("setSiteById:" + ex.getMessage());
                }
            }
        }
    }

    public List<Site> getSitesByTransactor(long aTransactor_id) {
        String sql;
        sql = "SELECT * FROM site WHERE transactor_id=" + aTransactor_id;
        ResultSet rs = null;
        List<Site> objs = new ArrayList<Site>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Site obj = new Site();
                this.setSiteFromResultset(obj, rs);
                objs.add(obj);
            }
        } catch (SQLException se) {
            System.err.println("getSitesByTransactor:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getSitesByTransactor:" + ex.getMessage());
                }
            }
        }
        return objs;
    }

    public void refreshSitesByTransactor(long aTransactor_id) {
        String sql;
        sql = "SELECT * FROM site WHERE transactor_id=" + aTransactor_id;
        ResultSet rs = null;
        this.Sites = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Site obj = new Site();
                this.setSiteFromResultset(obj, rs);
                this.Sites.add(obj);
            }
        } catch (SQLException se) {
            System.err.println("refreshSitesByTransactor:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("refreshSitesByTransactor:" + ex.getMessage());
                }
            }
        }
    }

    public void initClearSite(Site aSite) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            if (aSite != null) {
                this.clearSite(aSite);
            }
        }
    }

    public void initClearSite(Site aSite, Transactor aTransactor) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            if (aSite != null) {
                this.clearSite(aSite);
            }
            if (aTransactor != null) {
                new TransactorBean().clearTransactor(aTransactor);
            }
        }
    }

    public void clearSite(Site aSite) {
        try {
            if (null != aSite) {
                aSite.setSite_id(0);
                aSite.setTransactor_id(0);
                aSite.setSite_name("");
                aSite.setCountry("");
                aSite.setDistrict("");
                aSite.setVillage("");
            }
            this.ActionMessage = "";
            this.ActionMessageType = 0;
            this.Sites.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initSiteSession(Site aSite, int aSiteId) {
        try {
            this.setSiteById(aSite, aSiteId);
        } catch (Exception e) {
        }
    }

    public void copySite(Site aFrom, Site aTo) {

    }

    public void openChildSite() {
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
        //: org.primefaces.context.RequestContext.getCurrentInstance().openDialog("Site", options, null);
        org.primefaces.PrimeFaces.current().dialog().openDynamic("Site", options, null);
    }

    public void clearSitesList() {
        try {
            this.SitesList.clear();
        } catch (NullPointerException npe) {
            this.SitesList = new ArrayList<>();
        }
    }

    public void refreshSitesList(long aTransactorId) {
        try {
            this.SitesList.clear();
        } catch (NullPointerException npe) {
            this.SitesList = new ArrayList<>();
        }
        if (aTransactorId > 0) {
            this.SitesList = this.getSitesByTransactor(aTransactorId);
        }
    }

    public void refreshSitesListIfZero(long aTransactorId) {
        if (aTransactorId == 0) {
            try {
                this.SitesList.clear();
            } catch (NullPointerException npe) {
                this.SitesList = new ArrayList<>();
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
     * @return the Sites
     */
    public List<Site> getSites() {
        return Sites;
    }

    /**
     * @param Sites the Sites to set
     */
    public void setSites(List<Site> Sites) {
        this.Sites = Sites;
    }

    /**
     * @return the SiteObject
     */
    public Site getSiteObject() {
        return SiteObject;
    }

    /**
     * @param SiteObject the SiteObject to set
     */
    public void setSiteObject(Site SiteObject) {
        this.SiteObject = SiteObject;
    }

    /**
     * @return the ActionMessageType
     */
    public int getActionMessageType() {
        return ActionMessageType;
    }

    /**
     * @param ActionMessageType the ActionMessageType to set
     */
    public void setActionMessageType(int ActionMessageType) {
        this.ActionMessageType = ActionMessageType;
    }

    /**
     * @return the SearchTransactor
     */
    public Transactor getSearchTransactor() {
        return SearchTransactor;
    }

    /**
     * @param SearchTransactor the SearchTransactor to set
     */
    public void setSearchTransactor(Transactor SearchTransactor) {
        this.SearchTransactor = SearchTransactor;
    }

    /**
     * @return the SitesList
     */
    public List<Site> getSitesList() {
        return SitesList;
    }

    /**
     * @param SitesList the SitesList to set
     */
    public void setSitesList(List<Site> SitesList) {
        this.SitesList = SitesList;
    }
}
