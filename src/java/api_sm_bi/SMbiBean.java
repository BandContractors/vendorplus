package api_sm_bi;

import beans.ItemBean;
import beans.Parameter_listBean;
import beans.StoreBean;
import beans.TransBean;
import beans.TransItemBean;
import beans.TransactionReasonBean;
import beans.TransactorBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import entities.Trans;
import entities.TransItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class SMbiBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(SMbiBean.class.getName());
    private Bi_stg_sale_invoice saleInvoice;
    private List<Bi_stg_sale_invoice_item> saleInvoiceItems;

    public void sendInvoice(long aTransactionId) {
        try {
            Trans t = new TransBean().getTrans(aTransactionId);
            List<TransItem> tis = new TransItemBean().getTransItemsByTransactionId(aTransactionId);
            if (null == t || null == tis) {
                //do nothing
            } else {
                Gson gson = new Gson();
                String json = "";
                //init objects
                saleInvoice = new Bi_stg_sale_invoice();
                saleInvoiceItems = new ArrayList<>();
                //prepare
                this.prepareInvoice(t, tis);
                //creating JSON STRING from Object - Branch
                Bi_stg_sale_invoiceBean invBean = new Bi_stg_sale_invoiceBean();
                invBean.setSaleInvoice(saleInvoice);
                invBean.setSaleInvoiceItems(saleInvoiceItems);
                json = gson.toJson(invBean);
                System.out.println("invBean:" + json);
                com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
                WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_SMBI_URL").getParameter_value());
                ClientResponse response = webResource.type("application/json").post(ClientResponse.class, json);
                String output = response.getEntity(String.class);
                Status s = gson.fromJson(output, Status.class);
                System.out.println("Success:" + s.getSuccess() + ",Description" + s.getDescription());
                //update the database table:transaction_smbi_map
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareInvoice(Trans aTrans, List<TransItem> aTransItems) {
        try {
            //invoice
            try {
                saleInvoice.setSection_code(new StoreBean().getStore(aTrans.getStoreId()).getStore_code());
            } catch (Exception e) {
                saleInvoice.setSection_code("");
            }
            saleInvoice.setBranch_code(Integer.toString(CompanySetting.getBranchId()));
            saleInvoice.setBusiness_code(CompanySetting.getCompanyName());
            saleInvoice.setGroup_code(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_SMBI_GROUP_CODE").getParameter_value());
            saleInvoice.setInvoice_number(aTrans.getTransactionNumber());
            saleInvoice.setSales_date(aTrans.getTransactionDate());
            try {
                if (aTrans.getBillTransactorId() > 0) {
                    saleInvoice.setCustomer_name(new TransactorBean().getTransactor(aTrans.getBillTransactorId()).getTransactorNames());
                } else {
                    saleInvoice.setCustomer_name("Walk-In Customer");
                }
            } catch (Exception e) {
                //
            }
            saleInvoice.setGross_amount(aTrans.getGrandTotal());
            saleInvoice.setTrade_discount(aTrans.getTotalTradeDiscount());
            saleInvoice.setCash_discount(aTrans.getCashDiscount());
            saleInvoice.setTax_amount(aTrans.getTotalVat());
            saleInvoice.setProfit_margin(aTrans.getTotalProfitMargin());
            saleInvoice.setAmount_tendered(aTrans.getAmountTendered());
            saleInvoice.setStaff_code("");
            saleInvoice.setCurrency_code(aTrans.getCurrencyCode());
            saleInvoice.setCountry_code("");
            saleInvoice.setLoc_level2_code("");
            saleInvoice.setLoc_level3_code("");
            //invoice items
            Bi_stg_sale_invoice_item item = null;
            for (int i = 0; i < aTransItems.size(); i++) {
                item = new Bi_stg_sale_invoice_item();
                item.setBi_item_code(Long.toString(aTransItems.get(i).getItemId()));
                item.setSrc_item_description(new ItemBean().getItem(aTransItems.get(i).getItemId()).getDescription());
                item.setQty(aTransItems.get(i).getItemQty());
                item.setUnit_price(aTransItems.get(i).getUnitPriceExcVat());
                item.setUnit_trade_discount(aTransItems.get(i).getUnitTradeDiscount());
                item.setUnit_vat(aTransItems.get(i).getUnitVat());
                item.setAmount(aTransItems.get(i).getAmountIncVat());
                item.setVat_rated(aTransItems.get(i).getVatRated());
                item.setUnit_cost_price(aTransItems.get(i).getUnitCostPrice());
                item.setUnit_profit_margin(aTransItems.get(i).getUnitProfitMargin());
                saleInvoiceItems.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.ERROR, e);
        }
    }

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
