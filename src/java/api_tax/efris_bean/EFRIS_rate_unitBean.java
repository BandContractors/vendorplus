/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_rate_unit;
import beans.UnitBean;
import entities.Unit_tax_list;
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
public class EFRIS_rate_unitBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(EFRIS_rate_unitBean.class.getName());

    public void setUnit_tax_listFromEFRIS_rate_unit(Unit_tax_list aUnit_tax_list, EFRIS_rate_unit aEFRIS_rate_unit) {
        try {
            try {
                aUnit_tax_list.setUnit_symbol_tax(aEFRIS_rate_unit.getValue());
            } catch (Exception e) {
                aUnit_tax_list.setUnit_symbol_tax("");
            }
            try {
                aUnit_tax_list.setUnit_name_tax(aEFRIS_rate_unit.getName());
            } catch (Exception e) {
                aUnit_tax_list.setUnit_name_tax("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveEFRIS_rate_unit(List<EFRIS_rate_unit> aEFRIS_rate_units) {
        int saved = 0;
        try {
            List<Unit_tax_list> aUnit_tax_list = new ArrayList<>();
            //loop through the list
            for (int i = 0, size = aEFRIS_rate_units.size(); i < size; i++) {
                Unit_tax_list obj = new Unit_tax_list();
                this.setUnit_tax_listFromEFRIS_rate_unit(obj, aEFRIS_rate_units.get(i));
                aUnit_tax_list.add(obj);
            }
            //if Unit_tax_list is not empty
            if (aUnit_tax_list.size() > 0) {
                //then delete previous records
                int isUnitTaxListDeleted = new UnitBean().deleteUnit_tax_list_All();
               if (isUnitTaxListDeleted == 1){
                    //then save new records
                    new UnitBean().saveUnit_tax_list(aUnit_tax_list);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}
