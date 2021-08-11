package beans;

import entities.CompanySetting;
import entities.MenuItem;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.GeneralUserSetting;
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
@ManagedBean(name = "menuItemBean")
@SessionScoped
public class MenuItemBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(MenuItemBean.class.getName());
    private MenuItem MenuItemObj;

    public void refreshMenuItemObj() {
        try {
            if (null == this.MenuItemObj) {
                this.MenuItemObj = new MenuItem();
            }
            Parameter_listBean pb = new Parameter_listBean();
            GeneralUserSetting gus = new GeneralUserSetting();
            try {
                this.MenuItemObj.setSYSTEM_NAME(pb.getParameter_listByContextNameMemory("SYSTEM", "SYSTEM_NAME").getParameter_value());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setSYSTEM_NAME("");
            }
            try {
                this.MenuItemObj.setLICENSE_CLIENT_NAME(CompanySetting.getLICENSE_CLIENT_NAME());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setLICENSE_CLIENT_NAME("");
            }
            try {
                this.MenuItemObj.setSYSTEM_NAME_CLIENT(pb.getParameter_listByContextNameMemory("SYSTEM", "SYSTEM_NAME_CLIENT").getParameter_value());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setSYSTEM_NAME_CLIENT("");
            }
            try {
                this.MenuItemObj.setSYSTEM_VERSION(pb.getParameter_listByContextNameMemory("SYSTEM", "SYSTEM_VERSION").getParameter_value());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setSYSTEM_VERSION("");
            }
            try {
                this.MenuItemObj.setHIRE_MODULE_ON(gus.getHIRE_MODULE_ON());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setHIRE_MODULE_ON(0);
            }
            try {
                this.MenuItemObj.setLOGIN_TYPE(gus.getLoginType());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setLOGIN_TYPE(0);
            }
            try {
                this.MenuItemObj.setCURRENT_USER(gus.getCurrentUser());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setCURRENT_USER(null);
            }
            try {
                this.MenuItemObj.setCURRENT_STORE(gus.getCurrentStore());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setCURRENT_STORE(null);
            }
            try {
                String ParaValue = pb.getParameter_listByContextNameMemory("COMPANY_SETTING", "CUSTOMER_NAME").getParameter_value();
                if (ParaValue.length() == 0) {
                    ParaValue = "Customer";
                }
                this.MenuItemObj.setCUSTOMER_NAME(ParaValue);
            } catch (Exception e) {
                this.MenuItemObj.setCUSTOMER_NAME("Customer");
            }
            try {
                String ParaValue = pb.getParameter_listByContextNameMemory("COMPANY_SETTING", "SUPPLIER_NAME").getParameter_value();
                if (ParaValue.length() == 0) {
                    ParaValue = "Supplier";
                }
                this.MenuItemObj.setSUPPLIER_NAME(ParaValue);
            } catch (Exception e) {
                this.MenuItemObj.setSUPPLIER_NAME("Supplier");
            }
            try {
                this.MenuItemObj.setLOC_LEVEL_2_LABEL("District");
                this.MenuItemObj.setLOC_LEVEL_3_LABEL("Town");
                String ParaValue = pb.getParameter_listByContextNameMemory("LOCATION", "LEVEL_2_3_LABEL").getParameter_value();
                if (ParaValue.length() > 0) {
                    String[] Levs = ParaValue.split(",");
                    List<String> container = Arrays.asList(Levs);
                    for (int i = 0; i < container.size(); i++) {
                        if (i == 0 && container.get(i).length() > 0) {
                            this.MenuItemObj.setLOC_LEVEL_2_LABEL(container.get(i));
                        } else if (i == 1 && container.get(i).length() > 0) {
                            this.MenuItemObj.setLOC_LEVEL_3_LABEL(container.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
            try {
                this.MenuItemObj.setDEFAULT_FOCUS_CONTROL_ID(pb.getParameter_listByContextNameMemory("COMPANY_SETTING", "DEFAULT_FOCUS_CONTROL_ID").getParameter_value());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setDEFAULT_FOCUS_CONTROL_ID("");
            }
            try {
                this.MenuItemObj.setENABLE_AUTO_COMPLETE_ITEM_SEARCH(Integer.parseInt(pb.getParameter_listByContextNameMemory("COMPANY_SETTING", "ENABLE_AUTO_COMPLETE_ITEM_SEARCH").getParameter_value()));
            } catch (NullPointerException npe) {
                this.MenuItemObj.setENABLE_AUTO_COMPLETE_ITEM_SEARCH(1);
            }
            try {
                this.MenuItemObj.setPURCHASE_INVOICE_MODE(Integer.parseInt(pb.getParameter_listByContextNameMemory("COMPANY_SETTING", "PURCHASE_INVOICE_MODE").getParameter_value()));
            } catch (NullPointerException npe) {
                this.MenuItemObj.setPURCHASE_INVOICE_MODE(0);
            }
            try {
                String LangSys = "ENGLISH";
                if (null == gus.getCurrentUser().getLanguage_system()) {
                    //do nothing
                } else {
                    LangSys = gus.getCurrentUser().getLanguage_system();
                }
                if (LangSys.equals("ENGLISH")) {
                    this.MenuItemObj.setLANG_VAR_SYS("en");
                    this.MenuItemObj.setLANG_BASE_NAME_SYS("language_en");
                } else if (LangSys.equals("SIMPLIFIED_CHINESE")) {
                    this.MenuItemObj.setLANG_VAR_SYS("zh_CN");
                    this.MenuItemObj.setLANG_BASE_NAME_SYS("language_zh_CN");
                } else if (LangSys.equals("FRENCH")) {
                    this.MenuItemObj.setLANG_VAR_SYS("fr");
                    this.MenuItemObj.setLANG_BASE_NAME_SYS("language_fr");
                } else {
                    this.MenuItemObj.setLANG_VAR_SYS("en");
                    this.MenuItemObj.setLANG_BASE_NAME_SYS("language_en");
                }
            } catch (Exception e) {
                this.MenuItemObj.setLANG_VAR_SYS("en");
                this.MenuItemObj.setLANG_BASE_NAME_SYS("language_en");
            }
            try {
                String LangOut = "ENGLISH";
                if (null == gus.getCurrentUser().getLanguage_output()) {
                    //do nothing
                } else {
                    LangOut = gus.getCurrentUser().getLanguage_output();
                }
                if (LangOut.equals("ENGLISH")) {
                    this.MenuItemObj.setLANG_VAR_OUT("en");//language_en
                    this.MenuItemObj.setLANG_BASE_NAME_OUT("language_en");
                } else if (LangOut.equals("SIMPLIFIED_CHINESE")) {
                    this.MenuItemObj.setLANG_VAR_OUT("zh_CN");//language_zh_CN
                    this.MenuItemObj.setLANG_BASE_NAME_OUT("language_zh_CN");
                } else if (LangOut.equals("FRENCH")) {
                    this.MenuItemObj.setLANG_VAR_OUT("fr");//language_fr
                    this.MenuItemObj.setLANG_BASE_NAME_OUT("language_fr");
                } else {
                    this.MenuItemObj.setLANG_VAR_OUT("en");//language_en
                    this.MenuItemObj.setLANG_BASE_NAME_OUT("language_en");
                }
            } catch (Exception e) {
                this.MenuItemObj.setLANG_VAR_OUT("en");
                this.MenuItemObj.setLANG_BASE_NAME_OUT("language_en");
            }
            try {
                this.MenuItemObj.setSHOW_DETAIL_BY_DEFAULT(Integer.parseInt(pb.getParameter_listByContextNameMemory("COMPANY_SETTING", "SHOW_DETAIL_BY_DEFAULT").getParameter_value()));
            } catch (NullPointerException npe) {
                this.MenuItemObj.setSHOW_DETAIL_BY_DEFAULT(1);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    /**
     * @return the MenuItemObj
     */
    public MenuItem getMenuItemObj() {
        return MenuItemObj;
    }

    /**
     * @param MenuItemObj the MenuItemObj to set
     */
    public void setMenuItemObj(MenuItem MenuItemObj) {
        this.MenuItemObj = MenuItemObj;
    }
}
