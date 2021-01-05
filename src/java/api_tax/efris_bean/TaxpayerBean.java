/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.GeneralUtilities;
import api_tax.efris.innerclasses.Taxpayer;
import beans.Parameter_listBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

/**
 *
 * @author bajuna
 */
public class TaxpayerBean {

    public Taxpayer getTaxpayerDetailFromTax(String aTIN) {
        Taxpayer tp = null;
        try {
            String json = "{\n"
                    + "	\"tin\":\"" + aTIN + "\",\n"
                    + "	\"ninBrn\":\"\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T119", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            String output = response.getEntity(String.class);
            //System.out.println(output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));

            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject obj = parentbasicInformationjsonObject.getJSONObject("taxpayer");
            Gson g = new Gson();
            tp = new Taxpayer();
            tp = g.fromJson(obj.toString(), Taxpayer.class);
        } catch (Exception e) {
            tp = null;
            //e.printStackTrace();
            System.err.println("getTaxpayerDetailFromTax:" + e.getMessage());
        }
        return tp;
    }
    
    public Taxpayer getTaxpayerDetailFromTaxOnline(String aTIN) {
        Taxpayer tp = null;
        try {
            String json = "{\n"
                    + "	\"tin\":\"" + aTIN + "\",\n"
                    + "	\"ninBrn\":\"\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_ONLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T119", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            String output = response.getEntity(String.class);
            //System.out.println(output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));

            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONObject obj = parentbasicInformationjsonObject.getJSONObject("taxpayer");
            Gson g = new Gson();
            tp = new Taxpayer();
            tp = g.fromJson(obj.toString(), Taxpayer.class);
        } catch (Exception e) {
            tp = null;
            //e.printStackTrace();
            System.err.println("getTaxpayerDetailFromTax:" + e.getMessage());
        }
        return tp;
    }

}
