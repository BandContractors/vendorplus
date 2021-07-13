package api_sm_bi;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Bi_stg_sale_cr_dr_noteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Bi_stg_sale_cr_dr_note saleCreditDebitNote;
    private List<Bi_stg_sale_cr_dr_note_item> saleCreditDebitNoteItems;
    private String transactionType = "";//DEBIT NOTE or CREDIT NOTE

    /**
     * @return the saleCreditDebitNote
     */
    public Bi_stg_sale_cr_dr_note getSaleCreditDebitNote() {
        return saleCreditDebitNote;
    }

    /**
     * @param saleCreditDebitNote the saleCreditDebitNote to set
     */
    public void setSaleCreditDebitNote(Bi_stg_sale_cr_dr_note saleCreditDebitNote) {
        this.saleCreditDebitNote = saleCreditDebitNote;
    }

    /**
     * @return the saleCreditDebitNoteItems
     */
    public List<Bi_stg_sale_cr_dr_note_item> getSaleCreditDebitNoteItems() {
        return saleCreditDebitNoteItems;
    }

    /**
     * @param saleCreditDebitNoteItems the saleCreditDebitNoteItems to set
     */
    public void setSaleCreditDebitNoteItems(List<Bi_stg_sale_cr_dr_note_item> saleCreditDebitNoteItems) {
        this.saleCreditDebitNoteItems = saleCreditDebitNoteItems;
    }

    /**
     * @return the transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
