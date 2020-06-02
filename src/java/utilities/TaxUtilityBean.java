package utilities;

import beans.TransBean;
import beans.TransItemBean;
import entities.Trans;
import entities.TransItem;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class TaxUtilityBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void submitTaxInvoice(long aTransId) {
        try {
            String TaxInvoiceNo = "";
            Trans trans = new TransBean().getTrans(aTransId);
            List<TransItem> transitems = new TransItemBean().getTransItemsByTransactionId(aTransId);
        //post to tax API
            //save posted invoice
        } catch (Exception e) {
            System.out.println("submitTaxInvoice:" + e.getMessage());
        }
    }

}
