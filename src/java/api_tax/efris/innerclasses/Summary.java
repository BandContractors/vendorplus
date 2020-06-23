/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris.innerclasses;

/**
 *
 * @author bajuna
 */
    public class Summary {

    public Summary() {
        this.grossAmount="";
        this.itemCount="";
        this.modeCode="";
        this.netAmount="";
        this.qrCode="";
        this.remarks="";
        this.taxAmount="";
    }

    public Summary(String netAmount, String taxAmount, String grossAmount, String itemCount, String modeCode, String remarks, String qrCode) {
        this.netAmount = netAmount;
        this.taxAmount = taxAmount;
        this.grossAmount = grossAmount;
        this.itemCount = itemCount;
        this.modeCode = modeCode;
        this.remarks = remarks;
        this.qrCode = qrCode;
    }

        private String netAmount;
        private String taxAmount;
        private String grossAmount;
        private String itemCount;
        private String modeCode;
        private String remarks;
        private String qrCode;

        // Getter Methods 
        public String getNetAmount() {
            return netAmount;
        }

        public String getTaxAmount() {
            return taxAmount;
        }

        public String getGrossAmount() {
            return grossAmount;
        }

        public String getItemCount() {
            return itemCount;
        }

        public String getModeCode() {
            return modeCode;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getQrCode() {
            return qrCode;
        }

        // Setter Methods 
        public void setNetAmount(String netAmount) {
            this.netAmount = netAmount;
        }

        public void setTaxAmount(String taxAmount) {
            this.taxAmount = taxAmount;
        }

        public void setGrossAmount(String grossAmount) {
            this.grossAmount = grossAmount;
        }

        public void setItemCount(String itemCount) {
            this.itemCount = itemCount;
        }

        public void setModeCode(String modeCode) {
            this.modeCode = modeCode;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }
    }
