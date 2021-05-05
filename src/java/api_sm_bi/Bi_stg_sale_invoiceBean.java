package api_sm_bi;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Bi_stg_sale_invoiceBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Bi_stg_sale_invoice saleInvoice;
    private List<Bi_stg_sale_invoice_item> saleInvoiceItems;

    /**
     * @return the saleInvoice
     */
    public Bi_stg_sale_invoice getSaleInvoice() {
        return saleInvoice;
    }

    /**
     * @param saleInvoice the saleInvoice to set
     */
    public void setSaleInvoice(Bi_stg_sale_invoice saleInvoice) {
        this.saleInvoice = saleInvoice;
    }

    /**
     * @return the saleInvoiceItems
     */
    public List<Bi_stg_sale_invoice_item> getSaleInvoiceItems() {
        return saleInvoiceItems;
    }

    /**
     * @param saleInvoiceItems the saleInvoiceItems to set
     */
    public void setSaleInvoiceItems(List<Bi_stg_sale_invoice_item> saleInvoiceItems) {
        this.saleInvoiceItems = saleInvoiceItems;
    }
}
