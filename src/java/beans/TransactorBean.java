package beans;

import api_tax.efris.innerclasses.Taxpayer;
import api_tax.efris_bean.TaxpayerBean;
import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.MenuItem;
import entities.SalaryDeduction;
import entities.Trans;
import entities.TransactionType;
import entities.Transactor;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.VARCHAR;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class TransactorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Transactor> Transactors;
    private String ActionMessage = null;
    private String SearchTransactorNames = "";
    private Transactor SelectedTransactor;
    private Transactor SelectedBillTransactor;
    private Transactor SelectedSchemeTransactor;
    private List<Transactor> TransactorObjectList;
    private List<Transactor> ReportTransactors = new ArrayList<Transactor>();
    private List<SalaryDeduction> SalaryDeductions;
    private List<Transactor> TransactorList = new ArrayList<Transactor>();
    private Transactor TransactorObj;
    private List<Transactor> TransactorListSimilar = new ArrayList<Transactor>();
    private Transactor ParentTransactor;

    public void updateTaxpayer(Transactor aTransactor) {
        try {
            if (null == aTransactor) {
                //do nothing
            } else if (aTransactor.getTaxIdentity().length() > 0) {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value().length() > 0) {
                    //first clear
                    aTransactor.setTransactorNames("");
                    aTransactor.setPhysicalAddress("");
                    aTransactor.setEmail("");
                    aTransactor.setPhone("");
                    //get detail
                    Taxpayer taxpayer = new TaxpayerBean().getTaxpayerDetailFromTax(aTransactor.getTaxIdentity());
                    if (null == taxpayer) {
                        //do nothing
                    } else {
                        if (taxpayer.getLegalName().length() > 0) {
                            aTransactor.setTransactorNames(taxpayer.getLegalName());
                            aTransactor.setPhysicalAddress(taxpayer.getAddress());
                            aTransactor.setEmail(taxpayer.getContactEmail());
                            aTransactor.setPhone(taxpayer.getContactNumber());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("updateTaxpayer:" + e.getMessage());
        }
    }

    public String getDisplayName(MenuItem aMenuItem, Transactor aTransactor) {
        String display_name = "";
        if (aTransactor.getTransactorType().equals("CUSTOMER")) {
            display_name = aMenuItem.getCUSTOMER_NAME();
        } else if (aTransactor.getTransactorType().equals("SUPPLIER")) {
            display_name = aMenuItem.getSUPPLIER_NAME();
        } else if (aTransactor.getTransactorType().equals("SCHEME")) {
            display_name = "Scheme";
        } else if (aTransactor.getTransactorType().equals("PROVIDER")) {
            display_name = "Provider";
        }
        return display_name;
    }

    public Transactor findTransactor(Long TransactorId) {
        String sql = "{call sp_search_transactor_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, TransactorId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getTransactorFromResultSet(rs);
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

    public void initTransactorObj() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            if (null == this.TransactorObj) {
                this.TransactorObj = new Transactor();
            } else {
                this.clearTransactor(this.TransactorObj);
                this.SearchTransactorNames = "";
            }
            this.TransactorObj.setTransactorType(new GeneralUserSetting().getTransactorType());
            this.TransactorObj.setTrans_number_format(new TransactionTypeBean().getTransactionType(17).getTrans_number_format());
            //init transactor ref no
            //this.setNewTransctorRef(this.TransactorObj);
        }
    }

    public String validateTransactor(Transactor transactor, List<SalaryDeduction> aSalaryDeductions) {
        String sql = null;
        String msg = "";
        String sql2 = null;
        String sql3 = null;
        String sql4 = null;
        String sql5 = null;
        String TaxBranchNo = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value();
        sql2 = "SELECT * FROM transactor WHERE transactor_names='" + transactor.getTransactorNames() + "'";
        sql3 = "SELECT * FROM transactor WHERE transactor_names='" + transactor.getTransactorNames() + "' AND transactor_id!=" + transactor.getTransactorId();
        sql4 = "SELECT * FROM transactor WHERE transactor_ref='" + transactor.getTransactorRef() + "'";
        sql5 = "SELECT * FROM transactor WHERE transactor_ref='" + transactor.getTransactorRef() + "' AND transactor_id!=" + transactor.getTransactorId();
        TransactionType transtype = new TransactionTypeBean().getTransactionType(17);
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (transactor.getTransactorId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, new NavigationBean().getTransactorReasonStr(new GeneralUserSetting().getTransactorType()), "Add") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
        } else if (transactor.getTransactorId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, new NavigationBean().getTransactorReasonStr(new GeneralUserSetting().getTransactorType()), "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
        } else if (new CustomValidator().TextSize(transactor.getTransactorType(), 1, 20).equals("FAIL")) {
            msg = "Transactor Type MUST be specified and cannot exceed 20 characters";
        } else if (new CustomValidator().TextSize(transactor.getCategory(), 1, 20).equals("FAIL")) {
            msg = "Transactor Category MUST be specified and cannot exceed 20 characters";
        } else if (new CustomValidator().TextSize(transactor.getTransactorNames(), 3, 100).equals("FAIL")) {
            msg = "Names MUST be between 3-to-100 characters";
        } else if (new CustomValidator().TextSize(transactor.getPhone(), 0, 100).equals("FAIL")) {
            msg = "Transactor's Phone cannot exceed 100 characters";
        } else if (new CustomValidator().TextSize(transactor.getEmail(), 0, 100).equals("FAIL")) {
            msg = "Transactor's email cannot exceed 100 characters";
        } else if (new CustomValidator().TextSize(transactor.getWebsite(), 0, 100).equals("FAIL")) {
            msg = "Transactor's website cannot exceed 100 characters";
        } else if (new CustomValidator().TextSize(transactor.getCpName(), 0, 100).equals("FAIL")) {
            msg = "Contact person's name cannot exceed 100 characters";
        } else if (new CustomValidator().TextSize(transactor.getCpTitle(), 0, 100).equals("FAIL")) {
            msg = "Contact person's title cannot exceed 100 characters";
        } else if (new CustomValidator().TextSize(transactor.getCpPhone(), 0, 100).equals("FAIL")) {
            msg = "Contact person's phone cannot exceed 100 characters";
        } else if (new CustomValidator().TextSize(transactor.getCpEmail(), 0, 100).equals("FAIL")) {
            msg = "Contact person's email cannot exceed 100 characters";
        } else if (new CustomValidator().TextSize(transactor.getPhysicalAddress(), 0, 255).equals("FAIL")) {
            msg = "Physical address cannot exceed 255 characters";
        } else if (new CustomValidator().TextSize(transactor.getTaxIdentity(), 0, 100).equals("FAIL")) {
            msg = "Tax Identity cannot exceed 100 characters";
        } else if (new CustomValidator().TextSize(transactor.getAccountDetails(), 0, 255).equals("FAIL")) {
            msg = "Account details cannot exceed 255 characters";
        } else if ((new CustomValidator().CheckRecords(sql2) > 0 && transactor.getTransactorId() == 0) || (new CustomValidator().CheckRecords(sql3) > 0 && transactor.getTransactorId() > 0)) {
            msg = "Transactor Name(s) already exists, please enter different name(s) !";
        } else if (transtype.getTrans_number_format().length() == 0 && ((new CustomValidator().CheckRecords(sql4) > 0 && transactor.getTransactorId() == 0) || (new CustomValidator().CheckRecords(sql5) > 0 && transactor.getTransactorId() > 0))) {
            msg = "Transactor Reference Number already exists!";
        } else if (TaxBranchNo.length() > 0 && transactor.getCategory().equals("Business") && transactor.getTaxIdentity().length() == 0) {
            msg = "Specify Tax Identification Number for the Business";
        } else if (TaxBranchNo.length() > 0 && transactor.getCategory().equals("Government") && transactor.getTaxIdentity().length() == 0) {
            msg = "Specify Tax Identification Number for the Government Entity";
        } else if (TaxBranchNo.length() > 0 && transactor.getCategory().equals("Consumer") && transactor.getTaxIdentity().length() == 0 && transactor.getIdNumber().length() == 0 && transactor.getPhone().length() == 0) {
            msg = "Specify Phone Number or Identification Number for the Consumer";
        }
        return msg;
    }

    public void saveTransactor(Transactor transactor, List<SalaryDeduction> aSalaryDeductions) {
        String sql = null;
        String msg = "";
        GroupRightBean grb = new GroupRightBean();
        String ValidationMsg = this.validateTransactor(transactor, aSalaryDeductions);

        if (ValidationMsg.length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ValidationMsg));
        } else {
            try {
                long status = 0;
                try {
                    TransactionType transtype = new TransactionTypeBean().getTransactionType(17);
                    if (transactor.getTransactorId() == 0) {
                        if (transtype.getTrans_number_format().length() > 0) {
                            transactor.setTransactorRef(new Trans_number_controlBean().getNewTransNumber(transtype));
                            new Trans_number_controlBean().updateTrans_number_control(transtype);
                        }
                        transactor.setStore_id(new GeneralUserSetting().getCurrentStore().getStoreId());
                    }
                    status = this.insertUpdateTransactor(transactor);
                } catch (NullPointerException npe) {
                    status = 0;
                }
                if (status > 0) {
                    if (transactor.getTransactorType().equals("EMPLOYEE")) {
                        new SalaryDeductionBean().saveSalaryDeductions(status, aSalaryDeductions);
                    }
                    this.setActionMessage("Saved Successfully : " + transactor.getTransactorRef());
                    this.clearTransactor2(transactor, aSalaryDeductions);
                } else {
                    this.setActionMessage("Transaction NOT saved");
                }
            } catch (Exception se) {
                System.err.println(se.getMessage());
                se.printStackTrace();
                this.setActionMessage("Transactor NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Transactor NOT saved!"));
            }
        }
    }

    public long insertUpdateTransactor(Transactor transactor) {
        String sql = null;
        long status = 0;
        if (transactor.getTransactorId() == 0) {
            sql = "{call sp_insert_transactor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (transactor.getTransactorId() > 0) {
            sql = "{call sp_update_transactor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (transactor.getTransactorId() == 0) {
                cs.registerOutParameter("out_transactor_id", VARCHAR);
            } else {
                cs.setLong("in_transactor_id", transactor.getTransactorId());
            }
            if (transactor.getTransactorId() == 0) {
                cs.setInt("in_store_id", transactor.getStore_id());
            }
            cs.setString("in_transactor_type", transactor.getTransactorType());
            cs.setString("in_transactor_names", transactor.getTransactorNames());
            cs.setString("in_phone", transactor.getPhone());
            cs.setString("in_email", transactor.getEmail());
            cs.setString("in_website", transactor.getWebsite());
            cs.setString("in_cpname", transactor.getCpName());
            cs.setString("in_cptitle", transactor.getCpTitle());
            cs.setString("in_cpphone", transactor.getCpPhone());
            cs.setString("in_cpemail", transactor.getCpEmail());
            cs.setString("in_physical_address", transactor.getPhysicalAddress());
            cs.setString("in_tax_identity", transactor.getTaxIdentity());
            cs.setString("in_account_details", transactor.getAccountDetails());
            cs.setString("in_card_number", transactor.getCardNumber());
            try {
                cs.setDate("in_dob", new java.sql.Date(transactor.getDOB().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_dob", null);
            }
            cs.setString("in_is_suspended", transactor.getIsSuspended());
            cs.setString("in_suspended_reason", transactor.getSuspendedReason());
            cs.setString("in_category", transactor.getCategory());
            cs.setString("in_sex", transactor.getSex());
            cs.setString("in_occupation", transactor.getOccupation());
            cs.setString("in_loc_country", transactor.getLocCountry());
            cs.setString("in_loc_district", transactor.getLocDistrict());
            cs.setString("in_loc_town", transactor.getLocTown());
            try {
                cs.setDate("in_first_date", new java.sql.Date(transactor.getFirstDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_first_date", null);
            }
            cs.setString("in_file_reference", transactor.getFileReference());
            cs.setString("in_id_type", transactor.getIdType());
            cs.setString("in_id_number", transactor.getIdNumber());
            try {
                cs.setDate("in_id_expiry_date", new java.sql.Date(transactor.getIdExpiryDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_id_expiry_date", null);
            }
            try {
                cs.setString("in_transactor_ref", transactor.getTransactorRef());
            } catch (NullPointerException npe) {
                cs.setString("in_transactor_ref", "");
            }
            try {
                cs.setString("in_title", transactor.getTitle());
            } catch (NullPointerException npe) {
                cs.setString("in_title", "");
            }
            try {
                cs.setString("in_position", transactor.getPosition());
            } catch (NullPointerException npe) {
                cs.setString("in_position", "");
            }
            try {
                cs.setDouble("in_month_gross_pay", transactor.getMonthGrossPay());
            } catch (NullPointerException npe) {
                cs.setDouble("in_month_gross_pay", 0);
            }
            try {
                cs.setDouble("in_month_net_pay", transactor.getMonthNetPay());
            } catch (NullPointerException npe) {
                cs.setDouble("in_month_net_pay", 0);
            }
            try {
                cs.setInt("in_transactor_segment_id", transactor.getTransactor_segment_id());
            } catch (NullPointerException npe) {
                cs.setInt("in_transactor_segment_id", 0);
            }
            cs.executeUpdate();
            if (transactor.getTransactorId() == 0) {
                status = cs.getLong("out_transactor_id");
            } else {
                status = transactor.getTransactorId();
            }
        } catch (SQLException se) {
            status = 0;
            System.err.println(se.getMessage());
        }
        return status;
    }

    public Transactor getTransactorFromResultSet(ResultSet rs) {
        try {
            Transactor transactor = new Transactor();
            try {
                transactor.setTransactorId(rs.getLong("transactor_id"));
            } catch (NullPointerException npe) {
                transactor.setTransactorId(0);
            }
            try {
                transactor.setTransactorType(rs.getString("transactor_type"));
            } catch (NullPointerException npe) {
                transactor.setTransactorType("");
            }
            try {
                transactor.setTransactorNames(rs.getString("transactor_names"));
            } catch (NullPointerException npe) {
                transactor.setTransactorNames("");
            }
            try {
                transactor.setPhone(rs.getString("phone"));
            } catch (NullPointerException npe) {
                transactor.setPhone("");
            }
            try {
                transactor.setEmail(rs.getString("email"));
            } catch (NullPointerException npe) {
                transactor.setEmail("");
            }
            try {
                transactor.setWebsite(rs.getString("website"));
            } catch (NullPointerException npe) {
                transactor.setWebsite("");
            }
            try {
                transactor.setCpName(rs.getString("cpname"));
            } catch (NullPointerException npe) {
                transactor.setCpName("");
            }
            try {
                transactor.setCpTitle(rs.getString("cptitle"));
            } catch (NullPointerException npe) {
                transactor.setCpTitle("");
            }
            try {
                transactor.setCpPhone(rs.getString("cpphone"));
            } catch (NullPointerException npe) {
                transactor.setCpPhone("");
            }
            try {
                transactor.setCpEmail(rs.getString("cpemail"));
            } catch (NullPointerException npe) {
                transactor.setCpEmail("");
            }
            try {
                transactor.setPhysicalAddress(rs.getString("physical_address"));
            } catch (NullPointerException npe) {
                transactor.setPhysicalAddress("");
            }
            try {
                transactor.setTaxIdentity(rs.getString("tax_identity"));
            } catch (NullPointerException npe) {
                transactor.setTaxIdentity("");
            }
            try {
                transactor.setAccountDetails(rs.getString("account_details"));
            } catch (NullPointerException npe) {
                transactor.setAccountDetails("");
            }
            try {
                transactor.setCardNumber(rs.getString("card_number"));
            } catch (NullPointerException npe) {
                transactor.setCardNumber("");
            }
            try {
                transactor.setDOB(new Date(rs.getDate("dob").getTime()));
            } catch (NullPointerException npe) {
                transactor.setDOB(null);
            }
            try {
                transactor.setIsSuspended(rs.getString("is_suspended"));
            } catch (NullPointerException npe) {
                transactor.setIsSuspended("");
            }
            try {
                transactor.setSuspendedReason(rs.getString("suspended_reason"));
            } catch (NullPointerException npe) {
                transactor.setSuspendedReason("");
            }
            try {
                transactor.setCategory(rs.getString("category"));
            } catch (NullPointerException npe) {
                transactor.setCategory("");
            }
            try {
                transactor.setSex(rs.getString("sex"));
            } catch (NullPointerException npe) {
                transactor.setSex("");
            }
            try {
                transactor.setOccupation(rs.getString("occupation"));
            } catch (NullPointerException npe) {
                transactor.setOccupation("");
            }
            try {
                transactor.setLocCountry(rs.getString("loc_country"));
            } catch (NullPointerException npe) {
                transactor.setLocCountry("");
            }
            try {
                transactor.setLocDistrict(rs.getString("loc_district"));
            } catch (NullPointerException npe) {
                transactor.setLocDistrict("");
            }
            try {
                transactor.setLocTown(rs.getString("loc_town"));
            } catch (NullPointerException npe) {
                transactor.setLocTown("");
            }
            try {
                transactor.setFirstDate(new Date(rs.getDate("first_date").getTime()));
            } catch (NullPointerException npe) {
                transactor.setFirstDate(null);
            }

            try {
                transactor.setFileReference(rs.getString("file_reference"));
            } catch (NullPointerException npe) {
                transactor.setFileReference("");
            }
            try {
                transactor.setIdType(rs.getString("id_type"));
            } catch (NullPointerException npe) {
                transactor.setIdType("");
            }
            try {
                transactor.setIdNumber(rs.getString("id_number"));
            } catch (NullPointerException npe) {
                transactor.setIdNumber("");
            }
            try {
                transactor.setIdExpiryDate(new Date(rs.getDate("id_expiry_date").getTime()));
            } catch (NullPointerException npe) {
                transactor.setIdExpiryDate(null);
            }
            try {
                transactor.setTransactorRef(rs.getString("transactor_ref"));
            } catch (NullPointerException npe) {
                transactor.setTransactorRef("");
            }
            try {
                transactor.setTitle(rs.getString("title"));
            } catch (NullPointerException npe) {
                transactor.setTitle("");
            }
            try {
                transactor.setPosition(rs.getString("position"));
            } catch (NullPointerException npe) {
                transactor.setPosition("");
            }
            try {
                transactor.setMonthGrossPay(rs.getDouble("month_gross_pay"));
            } catch (NullPointerException npe) {
                transactor.setMonthGrossPay(0);
            }
            try {
                transactor.setMonthNetPay(rs.getDouble("month_net_pay"));
            } catch (NullPointerException npe) {
                transactor.setMonthNetPay(0);
            }
            try {
                transactor.setTransactor_segment_id(rs.getInt("transactor_segment_id"));
            } catch (NullPointerException npe) {
                transactor.setTransactor_segment_id(0);
            }
            return transactor;
        } catch (SQLException se) {
            return null;
        }
    }

    public void setTransactorFromResultSet(Transactor transactor, ResultSet rs) {
        try {
            //Transactor transactor = new Transactor();
            try {
                transactor.setTransactorId(rs.getLong("transactor_id"));
            } catch (NullPointerException npe) {
                transactor.setTransactorId(0);
            }
            try {
                transactor.setTransactorType(rs.getString("transactor_type"));
            } catch (NullPointerException npe) {
                transactor.setTransactorType("");
            }
            try {
                transactor.setTransactorNames(rs.getString("transactor_names"));
            } catch (NullPointerException npe) {
                transactor.setTransactorNames("");
            }
            try {
                transactor.setPhone(rs.getString("phone"));
            } catch (NullPointerException npe) {
                transactor.setPhone("");
            }
            try {
                transactor.setEmail(rs.getString("email"));
            } catch (NullPointerException npe) {
                transactor.setEmail("");
            }
            try {
                transactor.setWebsite(rs.getString("website"));
            } catch (NullPointerException npe) {
                transactor.setWebsite("");
            }
            try {
                transactor.setCpName(rs.getString("cpname"));
            } catch (NullPointerException npe) {
                transactor.setCpName("");
            }
            try {
                transactor.setCpTitle(rs.getString("cptitle"));
            } catch (NullPointerException npe) {
                transactor.setCpTitle("");
            }
            try {
                transactor.setCpPhone(rs.getString("cpphone"));
            } catch (NullPointerException npe) {
                transactor.setCpPhone("");
            }
            try {
                transactor.setCpEmail(rs.getString("cpemail"));
            } catch (NullPointerException npe) {
                transactor.setCpEmail("");
            }
            try {
                transactor.setPhysicalAddress(rs.getString("physical_address"));
            } catch (NullPointerException npe) {
                transactor.setPhysicalAddress("");
            }
            try {
                transactor.setTaxIdentity(rs.getString("tax_identity"));
            } catch (NullPointerException npe) {
                transactor.setTaxIdentity("");
            }
            try {
                transactor.setAccountDetails(rs.getString("account_details"));
            } catch (NullPointerException npe) {
                transactor.setAccountDetails("");
            }
            try {
                transactor.setCardNumber(rs.getString("card_number"));
            } catch (NullPointerException npe) {
                transactor.setCardNumber("");
            }
            try {
                transactor.setDOB(new Date(rs.getDate("dob").getTime()));
            } catch (NullPointerException npe) {
                transactor.setDOB(null);
            }
            try {
                transactor.setIsSuspended(rs.getString("is_suspended"));
            } catch (NullPointerException npe) {
                transactor.setIsSuspended("");
            }
            try {
                transactor.setSuspendedReason(rs.getString("suspended_reason"));
            } catch (NullPointerException npe) {
                transactor.setSuspendedReason("");
            }
            try {
                transactor.setCategory(rs.getString("category"));
            } catch (NullPointerException npe) {
                transactor.setCategory("");
            }
            try {
                transactor.setSex(rs.getString("sex"));
            } catch (NullPointerException npe) {
                transactor.setSex("");
            }
            try {
                transactor.setOccupation(rs.getString("occupation"));
            } catch (NullPointerException npe) {
                transactor.setOccupation("");
            }
            try {
                transactor.setLocCountry(rs.getString("loc_country"));
            } catch (NullPointerException npe) {
                transactor.setLocCountry("");
            }
            try {
                transactor.setLocDistrict(rs.getString("loc_district"));
            } catch (NullPointerException npe) {
                transactor.setLocDistrict("");
            }
            try {
                transactor.setLocTown(rs.getString("loc_town"));
            } catch (NullPointerException npe) {
                transactor.setLocTown("");
            }
            try {
                transactor.setFirstDate(new Date(rs.getDate("first_date").getTime()));
            } catch (NullPointerException npe) {
                transactor.setFirstDate(null);
            }

            try {
                transactor.setFileReference(rs.getString("file_reference"));
            } catch (NullPointerException npe) {
                transactor.setFileReference("");
            }
            try {
                transactor.setIdType(rs.getString("id_type"));
            } catch (NullPointerException npe) {
                transactor.setIdType("");
            }
            try {
                transactor.setIdNumber(rs.getString("id_number"));
            } catch (NullPointerException npe) {
                transactor.setIdNumber("");
            }
            try {
                transactor.setIdExpiryDate(new Date(rs.getDate("id_expiry_date").getTime()));
            } catch (NullPointerException npe) {
                transactor.setIdExpiryDate(null);
            }
            try {
                transactor.setTransactorRef(rs.getString("transactor_ref"));
            } catch (NullPointerException npe) {
                transactor.setTransactorRef("");
            }
            try {
                transactor.setTitle(rs.getString("title"));
            } catch (NullPointerException npe) {
                transactor.setTitle("");
            }
            try {
                transactor.setPosition(rs.getString("position"));
            } catch (NullPointerException npe) {
                transactor.setPosition("");
            }
            try {
                transactor.setMonthGrossPay(rs.getDouble("month_gross_pay"));
            } catch (NullPointerException npe) {
                transactor.setMonthGrossPay(0);
            }
            try {
                transactor.setMonthNetPay(rs.getDouble("month_net_pay"));
            } catch (NullPointerException npe) {
                transactor.setMonthNetPay(0);
            }
            try {
                transactor.setTransactor_segment_id(rs.getInt("transactor_segment_id"));
            } catch (NullPointerException npe) {
                transactor.setTransactor_segment_id(0);
            }
            try {
                transactor.setStore_id(rs.getInt("store_id"));
            } catch (NullPointerException npe) {
                transactor.setStore_id(0);
            }
        } catch (SQLException se) {
        }
    }

    public Transactor getTransactor(long TransactorId) {
        String sql = "{call sp_search_transactor_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, TransactorId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getTransactorFromResultSet(rs);
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

    public long getCurrentTransactorRefNo() {
        long curno = 0;
        String sql = "select cast(max(transactor_ref)+0 as SIGNED) as current_no from transactor WHERE transactor_ref>0";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    curno = rs.getLong("current_no");
                } catch (NullPointerException npe) {
                    curno = 0;
                }
            } else {
                curno = 0;
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
        if (curno == 0) {
            if (this.getCountTransactors() > 0) {
                curno = 0 - 1;
            }
        }
        return curno;
    }

    public long getCountTransactors() {
        long curcount = 0;
        String sql = "select count(*) as current_count from transactor;";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    curcount = rs.getLong("current_count");
                } catch (NullPointerException npe) {
                    curcount = 0;
                }
            } else {
                curcount = 0;
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
        return curcount;
    }

    public void setTransactor(Transactor aTransactor, long TransactorId) {
        String sql = "{call sp_search_transactor_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, TransactorId);
            rs = ps.executeQuery();
            if (rs.next()) {
                this.setTransactorFromResultSet(aTransactor, rs);
            }
        } catch (Exception e) {
            System.err.println("setTransactor:" + e.getMessage());
        }
    }

    public void deleteTransactorCall(Transactor aTransactor, List<SalaryDeduction> aSalaryDeductions) {
        String msg = "";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        try {
            if (aTransactor.getTransactorId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, new NavigationBean().getTransactorReasonStr(new GeneralUserSetting().getTransactorType()), "Delete") == 0) {
                msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            } else {
                if (this.getTransactorRecords(aTransactor.getTransactorId()) > 0) {
                    msg = new GeneralUserSetting().getTransactorType() + " has transactions in the system; cannot be deleted; try suspending instead!";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
                } else {
                    int status = this.deleteTransactor(aTransactor.getTransactorId());
                    if (status == 1) {
                        msg = "Deleted Successfully!";
                        FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
                        this.clearTransactor2(aTransactor, aSalaryDeductions);
                    } else {
                        msg = "Transactor NOT deleted!";
                        FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("deleteTransactorCall:" + e.getMessage());
            msg = "Transactor NOT deleted";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        }
    }

    public int deleteTransactor(long aTransactorId) {
        int status = 0;
        String sql = "DELETE FROM transactor WHERE transactor_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactorId);
            ps.executeUpdate();
            status = 1;
        } catch (Exception e) {
            status = 0;
            System.err.println("deleteTransactor:" + e.getMessage());
        }
        return status;
    }

    public void mergeTransactorRecordsCall(Trans aTrans, Transactor aTransactor, Transactor aBillTransactor) {
        String msg = "";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        try {
            if (aTrans.getTransactorId() == 0 || aTrans.getBillTransactorId() == 0) {
                msg = "Please select valid partner(s)...";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            } else if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "View") == 0) {
                msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            } else {
                int merged = this.mergeTransactorRecords(aTrans.getTransactorId(), aTrans.getBillTransactorId());
                int deleted = 0;
                if (merged == 0) {
                    msg = "Its possible records are not fully merged; please try again or contact administrator!";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
                } else {
                    deleted = this.deleteTransactor(aTrans.getBillTransactorId());
                }
                if (deleted == 1) {
                    msg = "Records merged and deleted Successfully!";
                    new TransBean().initTransactorMerge(aTrans, aTransactor, aBillTransactor);
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
                } else {
                    msg = "Its possible records are fully merged but partner not deleted; please try again or contact administrator!";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
                }
            }
        } catch (Exception e) {
            System.err.println("mergeTransactorRecordsCall:" + e.getMessage());
        }
    }

    public void displayTransactor(Transactor TransactorFrom, Transactor TransactorTo) {
        TransactorTo.setTransactorId(TransactorFrom.getTransactorId());
        TransactorTo.setTransactorType(TransactorFrom.getTransactorType());
        TransactorTo.setTransactorNames(TransactorFrom.getTransactorNames());
        TransactorTo.setPhone(TransactorFrom.getPhone());
        TransactorTo.setEmail(TransactorFrom.getEmail());
        TransactorTo.setWebsite(TransactorFrom.getWebsite());
        TransactorTo.setCpName(TransactorFrom.getCpName());
        TransactorTo.setCpTitle(TransactorFrom.getCpTitle());
        TransactorTo.setCpEmail(TransactorFrom.getCpEmail());
        TransactorTo.setCpPhone(TransactorFrom.getCpPhone());
        TransactorTo.setPhysicalAddress(TransactorFrom.getPhysicalAddress());
        TransactorTo.setTaxIdentity(TransactorFrom.getTaxIdentity());
        TransactorTo.setAccountDetails(TransactorFrom.getAccountDetails());
        TransactorTo.setCardNumber(TransactorFrom.getCardNumber());
        TransactorTo.setDOB(TransactorFrom.getDOB());
        TransactorTo.setIsSuspended(TransactorFrom.getIsSuspended());
        TransactorTo.setSuspendedReason(TransactorFrom.getSuspendedReason());
        TransactorTo.setCategory(TransactorFrom.getCategory());
        TransactorTo.setSex(TransactorFrom.getSex());
        TransactorTo.setOccupation(TransactorFrom.getOccupation());
        TransactorTo.setLocCountry(TransactorFrom.getLocCountry());
        TransactorTo.setLocDistrict(TransactorFrom.getLocDistrict());
        TransactorTo.setLocTown(TransactorFrom.getLocTown());
        TransactorTo.setFirstDate(TransactorFrom.getFirstDate());
        TransactorTo.setFileReference(TransactorFrom.getFileReference());
        TransactorTo.setIdType(TransactorFrom.getIdType());
        TransactorTo.setIdNumber(TransactorFrom.getIdNumber());
        TransactorTo.setIdExpiryDate(TransactorFrom.getIdExpiryDate());
        TransactorTo.setTransactorRef(TransactorFrom.getTransactorRef());
        TransactorTo.setTitle(TransactorFrom.getTitle());
        TransactorTo.setPosition(TransactorFrom.getPosition());
        TransactorTo.setMonthGrossPay(TransactorFrom.getMonthGrossPay());
        TransactorTo.setMonthNetPay(TransactorFrom.getMonthNetPay());
        TransactorTo.setTransactor_segment_id(TransactorFrom.getTransactor_segment_id());
        TransactorTo.setStore_id(TransactorFrom.getStore_id());
        //new SalaryDeductionBean().setSalaryDeductions(TransactorFrom.getTransactorId(), this.SalaryDeductions);
        this.SalaryDeductions = new SalaryDeductionBean().getSalaryDeductions(TransactorFrom.getTransactorId());
    }

    public void clearTransactor(Transactor transactor) {
        if (transactor != null) {
            transactor.setTransactorId(0);
            transactor.setTransactorType("");
            transactor.setTransactorNames("");
            transactor.setPhone("");
            transactor.setEmail("");
            transactor.setWebsite("");
            transactor.setCpName("");
            transactor.setCpTitle("");
            transactor.setCpEmail("");
            transactor.setCpPhone("");
            transactor.setPhysicalAddress("");
            transactor.setTaxIdentity("");
            transactor.setAccountDetails("");
            transactor.setCardNumber("");
            transactor.setDOB(null);
            transactor.setIsSuspended("");
            transactor.setSuspendedReason("");
            transactor.setCategory("");
            transactor.setSex("");
            transactor.setOccupation("");
            transactor.setLocCountry("");
            transactor.setLocDistrict("");
            transactor.setLocTown("");
            transactor.setFirstDate(null);
            transactor.setFileReference("");
            transactor.setIdType("");
            transactor.setIdNumber("");
            transactor.setIdExpiryDate(null);
            transactor.setTransactorRef("");
            transactor.setTitle("");
            transactor.setPosition("");
            transactor.setMonthGrossPay(0);
            transactor.setMonthNetPay(0);
            transactor.setTransactor_segment_id(0);
            transactor.setStore_id(0);
            transactor.setLocCountry(new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "COUNTRY_CODE").getParameter_value());
        }
    }

    public void clearTransactor2(Transactor transactor, List<SalaryDeduction> aSalaryDeductions) {
        if (transactor != null) {
            transactor.setTransactorId(0);
            //transactor.setTransactorType("");
            transactor.setTransactorNames("");
            transactor.setPhone("");
            transactor.setEmail("");
            transactor.setWebsite("");
            transactor.setCpName("");
            transactor.setCpTitle("");
            transactor.setCpEmail("");
            transactor.setCpPhone("");
            transactor.setPhysicalAddress("");
            transactor.setTaxIdentity("");
            transactor.setAccountDetails("");
            transactor.setCardNumber("");
            transactor.setDOB(null);
            transactor.setIsSuspended("");
            transactor.setSuspendedReason("");
            transactor.setCategory("");
            transactor.setSex("");
            transactor.setOccupation("");
            transactor.setLocCountry("");
            transactor.setLocDistrict("");
            transactor.setLocTown("");
            transactor.setFirstDate(null);
            transactor.setFileReference("");
            transactor.setIdType("");
            transactor.setIdNumber("");
            transactor.setIdExpiryDate(null);
            transactor.setTransactorRef("");
            transactor.setTitle("");
            transactor.setPosition("");
            transactor.setMonthGrossPay(0);
            transactor.setMonthNetPay(0);
            transactor.setTransactor_segment_id(0);
            transactor.setStore_id(0);
            try {
                aSalaryDeductions.clear();
            } catch (NullPointerException npe) {
            }
            this.setSearchTransactorNames("");
            try {
                this.TransactorList.clear();
            } catch (Exception e) {
                //do nothing
            }
            //init transactor ref no
            //this.setNewTransctorRef(transactor);
            transactor.setLocCountry(new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "COUNTRY_CODE").getParameter_value());
        }
    }

    public void initClearTransactor(Transactor transactor) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            if (transactor != null) {
                transactor.setTransactorId(0);
                //transactor.setTransactorType("");
                transactor.setTransactorNames("");
                transactor.setPhone("");
                transactor.setEmail("");
                transactor.setWebsite("");
                transactor.setCpName("");
                transactor.setCpTitle("");
                transactor.setCpEmail("");
                transactor.setCpPhone("");
                transactor.setPhysicalAddress("");
                transactor.setTaxIdentity("");
                transactor.setAccountDetails("");
                transactor.setCardNumber("");
                transactor.setDOB(null);
                transactor.setIsSuspended("");
                transactor.setSuspendedReason("");
                transactor.setCategory("");
                transactor.setSex("");
                transactor.setOccupation("");
                transactor.setLocCountry("");
                transactor.setLocDistrict("");
                transactor.setLocTown("");
                transactor.setFirstDate(null);
                transactor.setFileReference("");
                transactor.setIdType("");
                transactor.setIdNumber("");
                transactor.setIdExpiryDate(null);
                transactor.setTransactorRef("");
                transactor.setTitle("");
                transactor.setPosition("");
                transactor.setMonthGrossPay(0);
                transactor.setMonthNetPay(0);
                transactor.setTransactor_segment_id(0);
                transactor.setStore_id(0);
                this.setSearchTransactorNames("");
            }
        }
    }

    public void initClearTransactor2(Transactor transactor, List<SalaryDeduction> aSalaryDeductions) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            if (transactor != null) {
                transactor.setTransactorId(0);
                transactor.setTransactorType("");
                transactor.setTransactorNames("");
                transactor.setPhone("");
                transactor.setEmail("");
                transactor.setWebsite("");
                transactor.setCpName("");
                transactor.setCpTitle("");
                transactor.setCpEmail("");
                transactor.setCpPhone("");
                transactor.setPhysicalAddress("");
                transactor.setTaxIdentity("");
                transactor.setAccountDetails("");
                transactor.setCardNumber("");
                transactor.setDOB(null);
                transactor.setIsSuspended("");
                transactor.setSuspendedReason("");
                transactor.setCategory("");
                transactor.setSex("");
                transactor.setOccupation("");
                transactor.setLocCountry("");
                transactor.setLocDistrict("");
                transactor.setLocTown("");
                transactor.setFirstDate(null);
                transactor.setFileReference("");
                transactor.setIdType("");
                transactor.setIdNumber("");
                transactor.setIdExpiryDate(null);
                transactor.setTransactorRef("");
                transactor.setTitle("");
                transactor.setPosition("");
                transactor.setMonthGrossPay(0);
                transactor.setMonthNetPay(0);
                transactor.setTransactor_segment_id(0);
                transactor.setStore_id(0);
                try {
                    aSalaryDeductions.clear();
                } catch (NullPointerException npe) {
                }
                this.TransactorList = null;
            }
        }
    }

    public void clearSelectedTransactor() {
        this.clearTransactor(this.getSelectedTransactor());
    }

    public void clearSelectedBillTransactor() {
        this.clearTransactor(this.getSelectedBillTransactor());
    }

    public List<Transactor> getTransactors() {
        String sql;
        sql = "{call sp_search_transactor_by_name(?)}";
        ResultSet rs = null;
        Transactors = new ArrayList<Transactor>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, this.getSearchTransactorNames());
            rs = ps.executeQuery();
            while (rs.next()) {
                Transactors.add(this.getTransactorFromResultSet(rs));
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
        return Transactors;
    }

    public void clearList() {
        try {
            this.TransactorList.clear();
        } catch (NullPointerException npe) {
            //do nothing
        }
    }

    public void refreshTransactorsListByNameRefFile(String aName, String aType) {
        String sql;
        sql = "{call sp_search_transactor_by_name_ref_file(?,?)}";
        ResultSet rs = null;
        this.TransactorList = new ArrayList<Transactor>();
        if (aName.length() > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, aName);
                ps.setString(2, aType);
                rs = ps.executeQuery();
                while (rs.next()) {
                    this.TransactorList.add(this.getTransactorFromResultSet(rs));
                }
            } catch (Exception e) {
                System.err.println("refreshTransactorsListByNameRefFile:" + e.getMessage());
            }
        }
    }

    public void refreshTransactorsListSimilar(String aName, String aType) {
        String sql;
        sql = "{call sp_search_transactor_by_name_ref_file(?,?)}";
        ResultSet rs = null;
        this.TransactorListSimilar = new ArrayList<Transactor>();
        if (aName.length() > 0 && aType.length() > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, aName);
                ps.setString(2, aType);
                rs = ps.executeQuery();
                while (rs.next()) {
                    this.TransactorListSimilar.add(this.getTransactorFromResultSet(rs));
                }
            } catch (Exception e) {
                System.err.println("refreshTransactorsListSimilar:" + e.getMessage());
            }
        }
    }

    public List<Transactor> getTransactorsByNameType(String aName, String aType) {
        String sql;
        sql = "{call sp_search_transactor_by_name_type(?,?)}";
        ResultSet rs = null;
        List<Transactor> NewTransactors = new ArrayList<Transactor>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aName);
            ps.setString(2, aType);
            rs = ps.executeQuery();
            while (rs.next()) {
                NewTransactors.add(this.getTransactorFromResultSet(rs));
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
        return NewTransactors;
    }

    public void setTransactors(List<Transactor> Transactors) {
        this.Transactors = Transactors;
    }

    /**
     * @param Query
     * @return the TransactorStringList
     */
    public List<String> getTransactorStringList(String Query) {
        String sql;
        sql = "{call sp_search_transactor_by_name(?)}";
        ResultSet rs = null;
        List<String> TransactorStringList = new ArrayList<String>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                TransactorStringList.add(rs.getString("transactor_names"));
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
        return TransactorStringList;
    }

    /**
     * @param Query
     * @return the TransactorObjectList
     */
    public List<Transactor> getTransactorObjectList(String Query) {
        String sql;
        sql = "{call sp_search_transactor_by_name(?)}";
        ResultSet rs = null;
        TransactorObjectList = new ArrayList<Transactor>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                TransactorObjectList.add(this.getTransactorFromResultSet(rs));
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
        return TransactorObjectList;
    }

    /**
     * @param Query
     * @return the TransactorObjectList
     */
    public List<Transactor> getTransactorActiveObjectList(String Query) {
        String sql;
        sql = "{call sp_search_transactor_active_by_name(?)}";
        ResultSet rs = null;
        TransactorObjectList = new ArrayList<Transactor>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                TransactorObjectList.add(this.getTransactorFromResultSet(rs));
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
        return TransactorObjectList;
    }

    public List<Transactor> getReportTransactors(Transactor aTransactor, boolean RETRIEVE_REPORT) {
        String sql = "{call sp_report_transactor(?)}";
        ResultSet rs = null;
        this.ReportTransactors.clear();
        if (aTransactor != null && RETRIEVE_REPORT == true) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, aTransactor.getTransactorType());
                rs = ps.executeQuery();
                while (rs.next()) {
                    this.ReportTransactors.add(this.getTransactorFromResultSet(rs));
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
            this.ReportTransactors.clear();
        }
        return this.ReportTransactors;
    }

    public void reportTransactors(String aTransactorType) {
        String sql = "{call sp_report_transactor(?)}";
        ResultSet rs = null;
        this.TransactorList = new ArrayList<>();
        if (aTransactorType.length() > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, aTransactorType);
                rs = ps.executeQuery();
                while (rs.next()) {
                    this.TransactorList.add(this.getTransactorFromResultSet(rs));
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
        }
    }

    public void addSalaryDeduction() {
        try {
            if (null == this.getSalaryDeductions()) {
                this.setSalaryDeductions(new ArrayList<SalaryDeduction>());
            }
            SalaryDeduction sd = new SalaryDeduction();
            sd.setAccountCode("");
            sd.setPerc(0);
            sd.setAmount(0);
            sd.setDeductionName("");
            this.getSalaryDeductions().add(sd);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void removeSalaryDeduction(Transactor aTransactor, SalaryDeduction aSalaryDeduction) {
        if (null == this.getSalaryDeductions()) {

        } else {
            this.getSalaryDeductions().remove(aSalaryDeduction);
        }
        this.refreshTotalSalaryDeductions(aTransactor);
    }

    public void calcSalaryDeductionPerc(Transactor aTransactor, SalaryDeduction aSalaryDeduction) {
        if (null == aSalaryDeduction) {

        } else {
            if (aTransactor.getMonthGrossPay() > 0) {
                aSalaryDeduction.setPerc((aSalaryDeduction.getAmount() / aTransactor.getMonthGrossPay()) * 100);
            } else {
                aSalaryDeduction.setPerc(0);
            }
            aTransactor.setMonthNetPay(aTransactor.getMonthGrossPay() - this.getTotalSalaryDeductions());
        }
    }

    public void calcSalaryDeductionAmount(Transactor aTransactor, SalaryDeduction aSalaryDeduction) {
        if (null == aSalaryDeduction) {

        } else {
            aSalaryDeduction.setAmount((aSalaryDeduction.getPerc() * aTransactor.getMonthGrossPay()) / 100);
        }
        aTransactor.setMonthNetPay(aTransactor.getMonthGrossPay() - this.getTotalSalaryDeductions());
    }

    public void setTotalSalaryDeductions(double aAmount) {
        List<SalaryDeduction> sds = this.getSalaryDeductions();
        int ListItemIndex = 0;
        int ListItemNo = sds.size();
        double Total = 0;
        while (ListItemIndex < ListItemNo) {
            Total = Total + (sds.get(ListItemIndex).getAmount());
            ListItemIndex = ListItemIndex + 1;
        }
        aAmount = Total;
    }

    public double getTotalSalaryDeductions() {
        List<SalaryDeduction> sds = this.getSalaryDeductions();
        int ListItemIndex = 0;
        int ListItemNo = sds.size();
        double Total = 0;
        while (ListItemIndex < ListItemNo) {
            Total = Total + (sds.get(ListItemIndex).getAmount());
            ListItemIndex = ListItemIndex + 1;
        }
        return Total;
    }

    public void initClearTransactor(Transactor aTransactor, List<Transactor> aTransactorList, List<Transactor> aTransactorListSummary) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            try {
                if (aTransactor != null) {
                    this.clearTransactor(aTransactor);
                }
            } catch (NullPointerException npe) {
            }
            try {
                if (null != aTransactorList) {
                    aTransactorList.clear();
                }
            } catch (NullPointerException npe) {
            }
            try {
                if (null != aTransactorListSummary) {
                    aTransactorListSummary.clear();
                }
            } catch (NullPointerException npe) {
            }
        }
    }

    public void openChildTransactor(String aTransactorType) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        httpSession.setAttribute("TRANSACTOR_TYPE", aTransactorType);

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
        org.primefaces.PrimeFaces.current().dialog().openDynamic("TransactorChild", options, null);
    }

    public void openChildTransactor(String aTransactorType, long aTransactorId) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        httpSession.setAttribute("TRANSACTOR_TYPE", aTransactorType);
        try {
            if (aTransactorId > 0) {
                this.ParentTransactor = this.getTransactor(aTransactorId);
            }
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
        org.primefaces.PrimeFaces.current().dialog().openDynamic("TransactorChild", options, null);
    }

    public long getTransactorRecords(long aTransactorId) {
        String sql = "{call sp_search_records_by_transactor(?)}";
        ResultSet rs = null;
        long records = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactorId);
            rs = ps.executeQuery();
            while (rs.next()) {
                records = records + rs.getLong("records");
            }
        } catch (Exception e) {
            System.err.println("getTransactorRecords:" + e.getMessage());
        }
        return records;
    }

    public int mergeTransactorRecords(long aToTransactorId, long aFromTransactorId) {
        int status = 0;
        String sql = "{call sp_merge_records_by_transactor(?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aFromTransactorId);
            ps.setLong(2, aToTransactorId);
            ps.executeUpdate();
            status = 1;
        } catch (Exception e) {
            status = 0;
            System.err.println("mergeTransactorRecords:" + e.getMessage());
        }
        return status;
    }

    public List<Transactor> getTransactorsAll() {
        String sql;
        sql = "SELECT * FROM transactor";
        ResultSet rs = null;
        Transactors = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Transactors.add(this.getTransactorFromResultSet(rs));
            }
        } catch (Exception e) {
            System.err.println("getTransactorsAll:" + e.getMessage());
        }
        return Transactors;
    }

    public void refreshTotalSalaryDeductions(Transactor aTransactor) {
        aTransactor.setMonthNetPay(aTransactor.getMonthGrossPay() - this.getTotalSalaryDeductions());
    }

    public long getReportTransactorsCount() {
        return this.ReportTransactors.size();
    }

    /**
     * @param TransactorObjectList the TransactorObjectList to set
     */
    public void setTransactorObjectList(List<Transactor> TransactorObjectList) {
        this.TransactorObjectList = TransactorObjectList;
    }

    /**
     * @return the SearchTransactorNames
     */
    public String getSearchTransactorNames() {
        return SearchTransactorNames;
    }

    /**
     * @param SearchTransactorNames the SearchTransactorNames to set
     */
    public void setSearchTransactorNames(String SearchTransactorNames) {
        this.SearchTransactorNames = SearchTransactorNames;
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
     * @return the SelectedTransactor
     */
    public Transactor getSelectedTransactor() {
        return SelectedTransactor;
    }

    /**
     * @param SelectedTransactor the SelectedTransactor to set
     */
    public void setSelectedTransactor(Transactor SelectedTransactor) {
        this.SelectedTransactor = SelectedTransactor;
    }

    /**
     * @return the SelectedBillTransactor
     */
    public Transactor getSelectedBillTransactor() {
        return SelectedBillTransactor;
    }

    /**
     * @param SelectedBillTransactor the SelectedBillTransactor to set
     */
    public void setSelectedBillTransactor(Transactor SelectedBillTransactor) {
        this.SelectedBillTransactor = SelectedBillTransactor;
    }

    /**
     * @return the SelectedSchemeTransactor
     */
    public Transactor getSelectedSchemeTransactor() {
        return SelectedSchemeTransactor;
    }

    /**
     * @param SelectedSchemeTransactor the SelectedSchemeTransactor to set
     */
    public void setSelectedSchemeTransactor(Transactor SelectedSchemeTransactor) {
        this.SelectedSchemeTransactor = SelectedSchemeTransactor;
    }

    /**
     * @return the SalaryDeductions
     */
    public List<SalaryDeduction> getSalaryDeductions() {
        return SalaryDeductions;
    }

    /**
     * @param SalaryDeductions the SalaryDeductions to set
     */
    public void setSalaryDeductions(List<SalaryDeduction> SalaryDeductions) {
        this.SalaryDeductions = SalaryDeductions;
    }

    /**
     * @return the TransactorList
     */
    public List<Transactor> getTransactorList() {
        return TransactorList;
    }

    /**
     * @param TransactorList the TransactorList to set
     */
    public void setTransactorList(List<Transactor> TransactorList) {
        this.TransactorList = TransactorList;
    }

    /**
     * @return the TransactorObj
     */
    public Transactor getTransactorObj() {
        return TransactorObj;
    }

    /**
     * @param TransactorObj the TransactorObj to set
     */
    public void setTransactorObj(Transactor TransactorObj) {
        this.TransactorObj = TransactorObj;
    }

    /**
     * @return the TransactorListSimilar
     */
    public List<Transactor> getTransactorListSimilar() {
        return TransactorListSimilar;
    }

    /**
     * @param TransactorListSimilar the TransactorListSimilar to set
     */
    public void setTransactorListSimilar(List<Transactor> TransactorListSimilar) {
        this.TransactorListSimilar = TransactorListSimilar;
    }

    /**
     * @return the ParentTransactor
     */
    public Transactor getParentTransactor() {
        return ParentTransactor;
    }

    /**
     * @param ParentTransactor the ParentTransactor to set
     */
    public void setParentTransactor(Transactor ParentTransactor) {
        this.ParentTransactor = ParentTransactor;
    }
}
