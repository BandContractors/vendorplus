package beans;

import entities.CompanySetting;
import entities.TransItem;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
@ManagedBean
@SessionScoped
public class TransItemExtBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(TransItemExtBean.class.getName());

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void calVatFromAmountAtPurchase(TransItem aTransItem, String aCurrencyCode) {
        try {
            if (null != aTransItem) {
                double VatPerc = CompanySetting.getVatPerc();
                double VatAmount = 0;
                double AmountExcVat = 0;
                double AmountIncVat = aTransItem.getUnitPrice();
                if (VatPerc > 0 && AmountIncVat > 0) {
                    AmountExcVat = AmountIncVat / (1 + (VatPerc / 100));
                    AmountExcVat = new AccCurrencyBean().roundAmount(aCurrencyCode, AmountExcVat);
                } else {
                    AmountExcVat = AmountIncVat;
                }
                VatAmount = AmountIncVat - AmountExcVat;
                aTransItem.setUnitPrice(AmountExcVat);
                aTransItem.setUnitVat(VatAmount);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
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
