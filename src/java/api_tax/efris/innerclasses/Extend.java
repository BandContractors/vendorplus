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
    public class Extend {

    public Extend() {
        this.reason="";
        this.reasonCode="";
    }

    public Extend(String reason, String reasonCode) {
        this.reason = reason;
        this.reasonCode = reasonCode;
    }

        private String reason;
        private String reasonCode;

        // Getter Methods 
        public String getReason() {
            return reason;
        }

        public String getReasonCode() {
            return reasonCode;
        }

        // Setter Methods 
        public void setReason(String reason) {
            this.reason = reason;
        }

        public void setReasonCode(String reasonCode) {
            this.reasonCode = reasonCode;
        }
    }
