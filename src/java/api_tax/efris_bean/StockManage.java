/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.GeneralUtilities;
import api_tax.efris.innerclasses.ItemTax;
import beans.Parameter_listBean;
import beans.Stock_ledgerBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import entities.Stock;
import entities.Stock_ledger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author bajuna
 */
public class StockManage {

    public void addStockCall(Stock aStock, long aTax_update_id) {
        try {
            if (null != aStock && aTax_update_id > 0) {
                //1. indicate record4Sync
                int record4Sync = new Stock_ledgerBean().updateTaxStock_ledger(aTax_update_id, 1, 0);
                String id = this.getItemIdFromTax(Long.toString(aStock.getItemId()));
                if (id.length() > 0 && record4Sync == 1) {
                    //2. update tax db and check if synced yes
                    String recordSynced = this.addStock(id, Double.toString(aStock.getCurrentqty()), Double.toString(aStock.getUnitCost()));
                    if (recordSynced.equals("SUCCESS")) {
                        //3. update local db that synced yes
                        int x = new Stock_ledgerBean().updateTaxStock_ledger(aTax_update_id, 1, 1);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("addStockCall:" + e.getMessage());
        }
    }

    public void subtractStockCall(Stock aStock, long aTax_update_id) {
        try {
            if (null != aStock && aTax_update_id > 0) {
                //1. indicate record4Sync
                int record4Sync = new Stock_ledgerBean().updateTaxStock_ledger(aTax_update_id, 1, 0);
                String id = this.getItemIdFromTax(Long.toString(aStock.getItemId()));
                if (id.length() > 0 && record4Sync == 1) {
                    //2. update tax db and check if synced yes
                    String recordSynced = this.subtractStock(id, Double.toString(aStock.getCurrentqty()), Double.toString(aStock.getUnitCost()));
                    if (recordSynced.equals("SUCCESS")) {
                        //3. update local db that synced yes
                        int x = new Stock_ledgerBean().updateTaxStock_ledger(aTax_update_id, 1, 1);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("subtractStockCall:" + e.getMessage());
        }
    }

    public void addStockCallThread(Stock aStock, long aTax_update_id) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    addStockCall(aStock, aTax_update_id);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            System.err.println("addStockCallThread:" + e.getMessage());
        }
    }

    public void subtractStockCallThread(Stock aStock, long aTax_update_id) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    subtractStockCall(aStock, aTax_update_id);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            System.err.println("subtractStockCallThread:" + e.getMessage());
        }
    }

    public String getItemIdFromTax(String aGoodsCode) {
        String itemid = "";
        try {
            /**
             * Goods inquiry
             */
            //"	\"goodsCode\": \"147\",\n"
            String json = "{\n"
                    + "	\"goodsCode\": \"" + aGoodsCode + "\",\n"
                    + "	\"goodsName \": \"\",\n"
                    + "	\"commodityCategoryName\": \"\",\n"
                    + "	\"pageNo\": \"1\",\n"
                    + "	\"pageSize\": \"10\"\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T127", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            String output = response.getEntity(String.class);
            //System.out.println(output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));

            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray = parentbasicInformationjsonObject.getJSONArray("records");
            List<ItemTax> itemslist = new ArrayList<>();
            for (int i = 0, size = jSONArray.length(); i < size; i++) {
                JSONObject objectInArray = jSONArray.getJSONObject(i);
                Gson g = new Gson();
                ItemTax item = g.fromJson(objectInArray.toString(), ItemTax.class);
                itemslist.add(item);
            }
            itemid = itemslist.get(0).getId();
        } catch (Exception ex) {
            System.err.println("getItemIdFromTax:" + ex.getMessage());
        }
        return itemid;
    }

    public String addStock(String aId, String aQty, String aUnitPrice) {
        String ReturnMsg = "";
        try {
            String json = "[\n"
                    + "	{\n"
                    //+ "\"commodityGoodsId\": \"" + "350638171648238454" + "\",\n"
                    + "\"commodityGoodsId\": \"" + aId + "\",\n"
                    + "\"quantity\": \"" + aQty + "\",\n"
                    + "\"unitPrice\": \"" + aUnitPrice + "\"\n"
                    + "},\n"
                    + "]";

            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T131", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            String output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));
            //System.out.println(DecryptedContent);
            System.out.println("returnMessage--ehh:" + dataobject.getString("returnMessage"));
            ReturnMsg = dataobject.getString("returnMessage");
        } catch (Exception ex) {
            System.err.println("addStock:" + ex.getMessage());
        }
        return ReturnMsg;
    }

    public String subtractStock(String aId, String aQty, String aUnitPrice) {
        String ReturnMsg = "";
        try {
            String json = "[\n"
                    + "	{\n"
                    //+ "\"commodityGoodsId\": \"" + "350638171648238454" + "\",\n"
                    + "\"commodityGoodsId\": \"" + aId + "\",\n"
                    + "\"quantity\": \"" + "-" + aQty + "\",\n"
                    + "\"unitPrice\": \"" + aUnitPrice + "\"\n"
                    + "},\n"
                    + "]";

            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T131", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            String output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));
            ReturnMsg = dataobject.getString("returnMessage");
        } catch (Exception ex) {
            System.err.println("subtractStock:" + ex.getMessage());
        }
        return ReturnMsg;
    }

}
