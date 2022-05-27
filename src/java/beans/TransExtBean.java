package beans;

import entities.TransItem;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "transExtBean")
@SessionScoped
public class TransExtBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(TransExtBean.class.getName());
    private boolean ShowDetail;

    public void reverseShowDetail() {
        if (this.ShowDetail) {
            this.ShowDetail = false;
        } else {
            this.ShowDetail = true;
        }
    }

    public void resetShowDetail(int aValue) {
        if (aValue == 1) {
            this.ShowDetail = true;
        } else {
            this.ShowDetail = false;
        }
    }
    
    public void reverseOverridePrices(TransItem aTransItem) {
        if (aTransItem.isOverridePrices()) {
            aTransItem.setOverridePrices(false);
        } else {
            aTransItem.setOverridePrices(true);
        }
    }

    /**
     * @return the ShowDetail
     */
    public boolean isShowDetail() {
        return ShowDetail;
    }

    /**
     * @param ShowDetail the ShowDetail to set
     */
    public void setShowDetail(boolean ShowDetail) {
        this.ShowDetail = ShowDetail;
    }

}
