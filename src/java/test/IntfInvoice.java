package test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
public class IntfInvoice implements Serializable {

    private static final long serialVersionUID = 1L;
    private IntfTrans intfTrans;
    private List<IntfTransItem> intfTransItems = new ArrayList<>();

    /**
     * @return the intfTrans
     */
    public IntfTrans getIntfTrans() {
        return intfTrans;
    }

    /**
     * @param intfTrans the intfTrans to set
     */
    public void setIntfTrans(IntfTrans intfTrans) {
        this.intfTrans = intfTrans;
    }

    /**
     * @return the intfTransItems
     */
    public List<IntfTransItem> getIntfTransItems() {
        return intfTransItems;
    }

    /**
     * @param intfTransItems the intfTransItems to set
     */
    public void setIntfTransItems(List<IntfTransItem> intfTransItems) {
        this.intfTransItems = intfTransItems;
    }

}
