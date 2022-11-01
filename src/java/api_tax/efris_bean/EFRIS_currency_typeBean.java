/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_currency_type;
import beans.AccCurrencyBean;
import beans.UnitBean;
import entities.Acc_currency_tax_list;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author emuwonge
 */
@ManagedBean
@SessionScoped
public class EFRIS_currency_typeBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(EFRIS_currency_typeBean.class.getName());

    public void setAcc_currency_tax_listFromEFRIS_currency_type(Acc_currency_tax_list aAcc_currency_tax_list, EFRIS_currency_type aEFRIS_currency_type) {
        try {
            try {
                aAcc_currency_tax_list.setCurrency_code_tax(aEFRIS_currency_type.getValue());
            } catch (Exception e) {
                aAcc_currency_tax_list.setCurrency_code_tax("");
            }
            try {
                aAcc_currency_tax_list.setCurrency_name_tax(aEFRIS_currency_type.getName());
            } catch (Exception e) {
                aAcc_currency_tax_list.setCurrency_name_tax("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveEFRIS_currency_type(List<EFRIS_currency_type> aEFRIS_currency_types) {
        int saved = 0;
        try {
            List<Acc_currency_tax_list> aAcc_currency_tax_list = new ArrayList<>();
            //loop through the list
            for (int i = 0, size = aEFRIS_currency_types.size(); i < size; i++) {
                Acc_currency_tax_list obj = new Acc_currency_tax_list();
                this.setAcc_currency_tax_listFromEFRIS_currency_type(obj, aEFRIS_currency_types.get(i));
                aAcc_currency_tax_list.add(obj);
            }
            //if Acc_currency_tax_list is not empty
            if (aAcc_currency_tax_list.size() > 0) {
                //then delete previous records
                int isAccCurrencyTaxListDeleted = new AccCurrencyBean().deleteAcc_currency_tax_list_All();
               if (isAccCurrencyTaxListDeleted == 1){
                    //then save new records
                    new AccCurrencyBean().saveAcc_currency_tax_list(aAcc_currency_tax_list);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}
