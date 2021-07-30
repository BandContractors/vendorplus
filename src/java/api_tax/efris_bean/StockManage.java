/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.GeneralUtilities;
import api_tax.efris.innerclasses.ItemTax;
import beans.AccCurrencyBean;
import beans.ItemBean;
import beans.Item_tax_mapBean;
import beans.Parameter_listBean;
import beans.StockBean;
import beans.Stock_ledgerBean;
import beans.TransItemBean;
import beans.TransactorBean;
import beans.UnitBean;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.CompanySetting;
import entities.Item;
import entities.Stock;
import entities.Transactor;
import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.Security;
import utilities.SecurityPKI;

/**
 *
 * @author bajuna
 */
public class StockManage implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(StockManage.class.getName());

    public void callAddStockFromItemReg(Item aItem, String aItemIdTax) {//, Item_tax_map aItem_tax_map
        try {
            double qtybal = 0;
            try {
                qtybal = new StockBean().getStockAtHand(aItem.getItemId());
            } catch (Exception e) {
                qtybal = 0;
            }
            if (qtybal > 0) {
                Stock stockadd = new Stock();
                stockadd.setItemId(aItem.getItemId());
                stockadd.setCurrentqty(qtybal);
                stockadd.setUnitCost(new TransItemBean().getItemLatestUnitCostPrice(aItem.getItemId(), "", "", ""));
                String SupplierTIN = "";
                String SupplierName = "";
                long SupplierId = 0;
                if (SupplierId == 0) {
                    SupplierTIN = CompanySetting.getTaxIdentity();
                    SupplierName = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "PAYEE_NAME").getParameter_value();
                } else {
                    Transactor tr = new TransactorBean().getTransactor(SupplierId);
                    SupplierTIN = tr.getTaxIdentity();
                    SupplierName = tr.getTransactorNames();
                }
                new StockManage().addStockCallFromItemReg(stockadd, aItemIdTax, SupplierTIN, SupplierName);
            }
        } catch (Exception e) {
            //System.err.println("callAddStockUponItemReg:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void addStockCall(Stock aStock, String aItemIdTax, long aTax_update_id, String aSupplierTin, String aSupplierName) {
        try {
            if (null != aStock && aTax_update_id > 0) {
                //1. indicate record4Sync
                String TableName = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "CURRENT_TABLE_NAME_STOCK_LEDGER").getParameter_value();
                int record4Sync = new Stock_ledgerBean().updateTaxStock_ledger(TableName, aTax_update_id, 1, 0);
                String id = "";
                String APIMode = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value();
                if (APIMode.equals("OFFLINE")) {
                    id = this.getItemIdFromTaxOffline(aItemIdTax);
                } else {
                    id = this.getItemIdFromTaxOnline(aItemIdTax);
                }
                if (id.length() > 0 && record4Sync == 1) {
                    //2. update tax db and check if synced yes
                    String recordSynced = "";
                    if (APIMode.equals("OFFLINE")) {
                        recordSynced = this.addStockOffline(id, Double.toString(aStock.getCurrentqty()), Double.toString(aStock.getUnitCost()), aSupplierTin, aSupplierName);
                    } else {
                        recordSynced = this.addStockOnline(id, Double.toString(aStock.getCurrentqty()), Double.toString(aStock.getUnitCost()), aSupplierTin, aSupplierName);
                    }
                    if (recordSynced.equals("SUCCESS")) {
                        //3. update local db that synced yes
                        int x = new Stock_ledgerBean().updateTaxStock_ledger(TableName, aTax_update_id, 1, 1);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void addStockCallFromItemReg(Stock aStock, String aItemIdTax, String aSupplierTin, String aSupplierName) {
        try {
            if (null != aStock) {
                String id = "";
                String APIMode = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value();
                if (APIMode.equals("OFFLINE")) {
                    id = this.getItemIdFromTaxOffline(aItemIdTax);
                } else {
                    id = this.getItemIdFromTaxOnline(aItemIdTax);
                }
                if (id.length() > 0) {
                    //2. update tax db and check if synced yes
                    String recordSynced = "";
                    if (APIMode.equals("OFFLINE")) {
                        recordSynced = this.addStockOffline(id, Double.toString(aStock.getCurrentqty()), Double.toString(aStock.getUnitCost()), aSupplierTin, aSupplierName);
                    } else {
                        recordSynced = this.addStockOnline(id, Double.toString(aStock.getCurrentqty()), Double.toString(aStock.getUnitCost()), aSupplierTin, aSupplierName);
                    }
                    if (recordSynced.equals("SUCCESS")) {
                        //3. update local db that synced yes
                        //int x = new Stock_ledgerBean().updateTaxStock_ledger(aTax_update_id, 1, 1);
                    }
                }
            }
        } catch (Exception e) {
            //System.err.println("addStockCallFromItemReg:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void subtractStockCall(Stock aStock, String aItemIdTax, long aTax_update_id, String aAdjustType) {
        try {
            if (null != aStock && aTax_update_id > 0) {
                //1. indicate record4Sync
                String TableName = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "CURRENT_TABLE_NAME_STOCK_LEDGER").getParameter_value();
                int record4Sync = new Stock_ledgerBean().updateTaxStock_ledger(TableName, aTax_update_id, 1, 0);
                String APIMode = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value();
                String id = "";
                if (APIMode.equals("OFFLINE")) {
                    id = this.getItemIdFromTaxOffline(aItemIdTax);
                } else {
                    id = this.getItemIdFromTaxOnline(aItemIdTax);
                }
                if (id.length() > 0 && record4Sync == 1) {
                    //2. update tax db and check if synced yes
                    String recordSynced = "";
                    if (APIMode.equals("OFFLINE")) {
                        recordSynced = this.subtractStockOffline(id, Double.toString(aStock.getCurrentqty()), Double.toString(aStock.getUnitCost()), aAdjustType);
                    } else {
                        recordSynced = this.subtractStockOnline(id, Double.toString(aStock.getCurrentqty()), Double.toString(aStock.getUnitCost()), aAdjustType);
                    }
                    if (recordSynced.equals("SUCCESS")) {
                        //3. update local db that synced yes
                        int x = new Stock_ledgerBean().updateTaxStock_ledger(TableName, aTax_update_id, 1, 1);
                    }
                }
            }
        } catch (Exception e) {
            //System.err.println("subtractStockCall:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void addStockCallThread(Stock aStock, String aItemIdTax, long aTax_update_id, String aSupplierTin, String aSupplierName) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    addStockCall(aStock, aItemIdTax, aTax_update_id, aSupplierTin, aSupplierName);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            //System.err.println("addStockCallThread:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void subtractStockCallThread(Stock aStock, String aItemIdTax, long aTax_update_id, String aAdjustType) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    subtractStockCall(aStock, aItemIdTax, aTax_update_id, aAdjustType);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            //System.err.println("subtractStockCallThread:" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
    }

    public String getItemIdFromTaxOffline(String aGoodsCode) {
        String itemid = "";
        String output = "";
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
            output = response.getEntity(String.class);
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
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return itemid;
    }

    public ItemTax getItemFromTaxOffline(String aGoodsCode) {
        ItemTax itemtax = null;
        String output = "";
        try {
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
            output = response.getEntity(String.class);
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
            itemtax = itemslist.get(0);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return itemtax;
    }

    public String getItemIdFromTaxOnline(String aGoodsCode) {
        String itemid = "";
        String output = "";
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
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            //String AESpublickeystring = SecurityPKI.decrypt(new SecurityPKI().AESPublicKey(CompanySetting.getTaxIdentity(), new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value()), key);
            String AESpublickeystring = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            /**
             * Encrypt Content
             */
            String encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T127", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
            //System.out.println(output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            String DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));

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
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return itemid;
    }

    public ItemTax getItemFromTaxOnline(String aGoodsCode) {
        ItemTax itemtax = null;
        String output = "";
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
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            //String AESpublickeystring = SecurityPKI.decrypt(new SecurityPKI().AESPublicKey(CompanySetting.getTaxIdentity(), new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value()), key);
            String AESpublickeystring = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            /**
             * Encrypt Content
             */
            String encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T127", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);
            //System.out.println(output);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            String DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));

            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
            JSONArray jSONArray = parentbasicInformationjsonObject.getJSONArray("records");
            List<ItemTax> itemslist = new ArrayList<>();
            for (int i = 0, size = jSONArray.length(); i < size; i++) {
                JSONObject objectInArray = jSONArray.getJSONObject(i);
                Gson g = new Gson();
                ItemTax item = g.fromJson(objectInArray.toString(), ItemTax.class);
                itemslist.add(item);
            }
            itemtax = itemslist.get(0);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return itemtax;
    }

    public String addStockOffline(String aId, String aQty, String aUnitPrice, String aSupplierTin, String aSupplierName) {
        String ReturnMsg = "";
        String output = "";
        //System.out.println("aUnitPrice:" + aUnitPrice);
        try {
            String json = "{\n"
                    + " \"goodsStockIn\": {\n"
                    + " \"operationType\": \"101\",\n"
                    + " \"supplierTin\": \"" + aSupplierTin + "\",\n"
                    + " \"supplierName\": \"" + aSupplierName + "\",\n"
                    + " \"adjustType\": \"\",\n"
                    + " \"stockInType\": \"102\",\n"
                    + " \"remarks\": \"Increase inventory\"\n"
                    + " },\n"
                    + " \"goodsStockInItem\": [{\n"
                    + " \"commodityGoodsId\": \"" + aId + "\",\n"
                    + " \"quantity\": \"" + aQty + "\",\n"
                    + " \"unitPrice\": \"" + aUnitPrice + "\"\n"
                    + " }]\n"
                    + "}";

            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T131", CompanySetting.getTaxIdentity());
            //System.out.println("json:" + json);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));
            //System.out.println("DecryptedContent:" + DecryptedContent);
            //System.out.println("returnMessage:" + dataobject.getString("returnMessage"));
            if (DecryptedContent.length() == 2) {
                ReturnMsg = dataobject.getString("returnMessage");
            } else {
                //System.out.println("addStockOffline:DecryptedContent:" + DecryptedContent);
                //System.out.println("addStockOffline:returnMessage:" + dataobject.getString("returnMessage"));
                ReturnMsg = dataobject.getString("returnMessage") + ":DecryptedContent";
                LOGGER.log(Level.INFO, ReturnMsg);
            }
        } catch (Exception e) {
            //System.err.println("addStockOffline:" + ex.getMessage());
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return ReturnMsg;
    }

    public String addStockOnline(String aId, String aQty, String aUnitPrice, String aSupplierTin, String aSupplierName) {
        String ReturnMsg = "";
        String output = "";
        //System.out.println("aUnitPrice:" + aUnitPrice);
        try {
            String json = "{\n"
                    + " \"goodsStockIn\": {\n"
                    + " \"operationType\": \"101\",\n"
                    + " \"supplierTin\": \"" + aSupplierTin + "\",\n"
                    + " \"supplierName\": \"" + aSupplierName + "\",\n"
                    + " \"adjustType\": \"\",\n"
                    + " \"stockInType\": \"102\",\n"
                    + " \"remarks\": \"Increase inventory\"\n"
                    + " },\n"
                    + " \"goodsStockInItem\": [{\n"
                    + " \"commodityGoodsId\": \"" + aId + "\",\n"
                    + " \"quantity\": \"" + aQty + "\",\n"
                    + " \"unitPrice\": \"" + aUnitPrice + "\"\n"
                    + " }]\n"
                    + "}";

            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            //String AESpublickeystring = SecurityPKI.decrypt(new SecurityPKI().AESPublicKey(CompanySetting.getTaxIdentity(), new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value()), key);
            String AESpublickeystring = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            /**
             * Encrypt Content
             */
            String encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T131", CompanySetting.getTaxIdentity());
            //System.out.println("json:" + json);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            String DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
            //System.out.println("DecryptedContent:" + DecryptedContent);
            //System.out.println("returnMessage:" + dataobject.getString("returnMessage"));
            if (DecryptedContent.length() == 2) {
                ReturnMsg = dataobject.getString("returnMessage");
            } else {
                //System.out.println("addStockOnline:DecryptedContent:" + DecryptedContent);
                //System.out.println("addStockOnline:returnMessage:" + dataobject.getString("returnMessage"));
                ReturnMsg = dataobject.getString("returnMessage") + ":DecryptedContent";
                LOGGER.log(Level.INFO, ReturnMsg);
            }
        } catch (Exception e) {
            //System.err.println("addStockOnline:" + ex.getMessage());
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return ReturnMsg;
    }

    public String subtractStockOffline(String aId, String aQty, String aUnitPrice, String aAdjustType) {
        String ReturnMsg = "";
        String output = "";
        //System.out.println("aUnitPrice:" + aUnitPrice);
        try {
            String json = "{\n"
                    + " \"goodsStockIn\": {\n"
                    + " \"operationType\": \"102\",\n"
                    + " \"supplierTin\": \"\",\n"
                    + " \"supplierName\": \"\",\n"
                    + " \"adjustType\": \"" + aAdjustType + "\",\n"
                    //+ " \"stockInType\": \"" + 102 + "\",\n"
                    + " \"remarks\": \"Decrease inventory\"\n"
                    + " },\n"
                    + " \"goodsStockInItem\": [{\n"
                    + " \"commodityGoodsId\": \"" + aId + "\",\n"
                    + " \"quantity\": \"" + aQty + "\",\n"
                    + " \"unitPrice\": \"" + aUnitPrice + "\"\n"
                    + " }]\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T131", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));
            //System.out.println("DecryptedContent:" + DecryptedContent);
            //System.out.println("ReturnMsg:" + ReturnMsg);
            if (DecryptedContent.length() == 2) {
                ReturnMsg = dataobject.getString("returnMessage");
            } else {
                //System.out.println("subtractStock:DecryptedContent:" + DecryptedContent);
                //System.out.println("subtractStock:returnMessage:" + dataobject.getString("returnMessage"));
                ReturnMsg = dataobject.getString("returnMessage") + ":DecryptedContent";
                LOGGER.log(Level.INFO, ReturnMsg);
            }
        } catch (Exception e) {
            //System.err.println("subtractStockOffline:" + ex.getMessage());
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return ReturnMsg;
    }

    public String subtractStockOnline(String aId, String aQty, String aUnitPrice, String aAdjustType) {
        String ReturnMsg = "";
        String output = "";
        //System.out.println("aUnitPrice:" + aUnitPrice);
        try {
            String json = "{\n"
                    + " \"goodsStockIn\": {\n"
                    + " \"operationType\": \"102\",\n"
                    + " \"supplierTin\": \"\",\n"
                    + " \"supplierName\": \"\",\n"
                    + " \"adjustType\": \"" + aAdjustType + "\",\n"
                    //+ " \"stockInType\": \"" + 102 + "\",\n"
                    + " \"remarks\": \"Decrease inventory\"\n"
                    + " },\n"
                    + " \"goodsStockInItem\": [{\n"
                    + " \"commodityGoodsId\": \"" + aId + "\",\n"
                    + " \"quantity\": \"" + aQty + "\",\n"
                    + " \"unitPrice\": \"" + aUnitPrice + "\"\n"
                    + " }]\n"
                    + "}";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            //String AESpublickeystring = SecurityPKI.decrypt(new SecurityPKI().AESPublicKey(CompanySetting.getTaxIdentity(), new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value()), key);
            String AESpublickeystring = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            /**
             * Encrypt Content
             */
            String encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T131", CompanySetting.getTaxIdentity());

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            String DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
            //System.out.println("DecryptedContent:" + DecryptedContent);
            //System.out.println("ReturnMsg:" + ReturnMsg);
            if (DecryptedContent.length() == 2) {
                ReturnMsg = dataobject.getString("returnMessage");
            } else {
                //System.out.println("subtractStockOnline:DecryptedContent:" + DecryptedContent);
                //System.out.println("subtractStockOnline:returnMessage:" + dataobject.getString("returnMessage"));
                ReturnMsg = dataobject.getString("returnMessage") + ":DecryptedContent";
                LOGGER.log(Level.INFO, ReturnMsg);
            }
        } catch (Exception e) {
            //System.err.println("subtractStockOnline:" + ex.getMessage());
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return ReturnMsg;
    }

    public void registerItemCallThread(long aItemId, String aItemIdTax, String aItemCodeTax) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    registerItemCall(aItemId, aItemIdTax, aItemCodeTax);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void registerItemCall(long aItemId, String aItemIdTax, String aItemCodeTax) {
        try {
            Item item = new ItemBean().getItem(aItemId);
            if (null != item) {
                item.setItem_code_tax(aItemCodeTax);
                //unit
                try {
                    String UnitSymbolTax = new UnitBean().getUnit(item.getUnitId()).getUnit_symbol_tax();
                    if (null == UnitSymbolTax) {
                        item.setUnit_symbol_tax("PCE");
                    } else {
                        item.setUnit_symbol_tax(UnitSymbolTax);
                    }
                } catch (Exception e) {
                    item.setUnit_symbol_tax("PCE");
                }
                //currency
                try {
                    String CurCodeTax = new AccCurrencyBean().getCurrency(item.getCurrencyCode()).getCurrency_code_tax();
                    if (null == CurCodeTax) {
                        item.setCurrency_code_tax("101");
                    } else {
                        item.setCurrency_code_tax(CurCodeTax);
                    }
                } catch (Exception e) {
                    item.setCurrency_code_tax("101");
                }
                //reorder level
                if (item.getReorderLevel() == 0) {
                    item.setReorderLevel(1);
                }
                //register
                String recordSynced = "";
                String APIMode = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value();
                if (APIMode.equals("OFFLINE")) {
                    recordSynced = this.registerItemOffline(item, aItemIdTax);
                } else {
                    recordSynced = this.registerItemOnline(item, aItemIdTax);
                }
                if (recordSynced.equals("SUCCESS")) {
                    //update local db that synced yes
                    int x = new Item_tax_mapBean().saveItem_tax_mapSync(aItemId, 1);
                    //do stock taking
                    //Item_tax_map im = new Item_tax_mapBean().getItem_tax_mapSynced(item.getItemId());
                    this.callAddStockFromItemReg(item, aItemIdTax);//this.callAddStockFromItemReg(item, im);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public String registerItemOffline(Item aItem, String aItemIdTax) {
        String ReturnMsg = "";
        String output = "";
        try {
            String UnitPriceStr = "";
            if (aItem.getUnitRetailsalePrice() > 0) {
                UnitPriceStr = " \"unitPrice\": \"" + aItem.getUnitRetailsalePrice() + "\",\n";
            } else {
                UnitPriceStr = " \"unitPrice\": \"1\",\n";
            }
            String json = "[\n"
                    + "	{\n"
                    + "	\"goodsName\": \"" + aItem.getDescription() + "\",\n"
                    + "	\"goodsCode\": \"" + aItemIdTax + "\",\n"
                    + "	\"measureUnit\": \"" + aItem.getUnit_symbol_tax() + "\",\n"
                    + UnitPriceStr
                    + "	\"currency\": \"" + aItem.getCurrency_code_tax() + "\",\n"
                    + "	\"commodityCategoryId\": \"" + aItem.getItem_code_tax() + "\",\n"
                    + "	\"haveExciseTax\": \"102\",\n"
                    + "	\"description\": \"1\",\n"
                    + "	\"stockPrewarning\": \"" + aItem.getReorderLevel() + "\",\n"
                    + "	\"pieceMeasureUnit\": \"\",\n"
                    + "	\"havePieceUnit\": \"102\",\n"
                    + "	\"pieceUnitPrice\": \"\",\n"
                    + "	\"packageScaledValue\": \"\",\n"
                    + "	\"pieceScaledValue\": \"\",\n"
                    + "	\"exciseDutyCode\": \"\"\n"
                    + "},\n"
                    + "]";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_OFFLINE").getParameter_value());
            String PostData = GeneralUtilities.PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T130", CompanySetting.getTaxIdentity());
            //System.out.println("json>:" + json);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");

            String DecryptedContent = new String(Base64.decodeBase64(content));
            //System.out.println("DecryptedContent:" + DecryptedContent);
            //System.out.println("returnMessage:" + dataobject.getString("returnMessage"));
            if (DecryptedContent.length() == 2) {
                ReturnMsg = dataobject.getString("returnMessage");
            } else {
                //System.out.println("DecryptedContent:" + DecryptedContent);
                //System.out.println("returnMessage:" + dataobject.getString("returnMessage"));
                ReturnMsg = dataobject.getString("returnMessage") + ":DecryptedContent";
                LOGGER.log(Level.INFO, ReturnMsg);
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return ReturnMsg;
    }

    public String registerItemOnline(Item aItem, String aItemIdTax) {
        String ReturnMsg = "";
        String output = "";
        try {
            String UnitPriceStr = "";
            if (aItem.getUnitRetailsalePrice() > 0) {
                UnitPriceStr = " \"unitPrice\": \"" + aItem.getUnitRetailsalePrice() + "\",\n";
            } else {
                UnitPriceStr = " \"unitPrice\": \"1\",\n";
            }
            String json = "[\n"
                    + "	{\n"
                    + "	\"goodsName\": \"" + aItem.getDescription() + "\",\n"
                    + "	\"goodsCode\": \"" + aItemIdTax + "\",\n"
                    + "	\"measureUnit\": \"" + aItem.getUnit_symbol_tax() + "\",\n"
                    + UnitPriceStr
                    + "	\"currency\": \"" + aItem.getCurrency_code_tax() + "\",\n"
                    + "	\"commodityCategoryId\": \"" + aItem.getItem_code_tax() + "\",\n"
                    + "	\"haveExciseTax\": \"102\",\n"
                    + "	\"description\": \"1\",\n"
                    + "	\"stockPrewarning\": \"" + aItem.getReorderLevel() + "\",\n"
                    + "	\"pieceMeasureUnit\": \"\",\n"
                    + "	\"havePieceUnit\": \"102\",\n"
                    + "	\"pieceUnitPrice\": \"\",\n"
                    + "	\"packageScaledValue\": \"\",\n"
                    + "	\"pieceScaledValue\": \"\",\n"
                    + "	\"exciseDutyCode\": \"\"\n"
                    + "},\n"
                    + "]";
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_URL_ONLINE").getParameter_value());
            /**
             * Read Private Key
             */
            PrivateKey key = new SecurityPKI().getPrivate(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_FILE").getParameter_value(), Security.Decrypt(new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_PASSWORD").getParameter_value()), new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_KEYSTORE_ALIAS").getParameter_value());
            //String AESpublickeystring = SecurityPKI.decrypt(new SecurityPKI().AESPublicKey(CompanySetting.getTaxIdentity(), new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value()), key);
            String AESpublickeystring = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_AES_PUBLIC_KEY").getParameter_value();
            /**
             * Encrypt Content
             */
            String encryptedcontent = SecurityPKI.AESencrypt(json, Base64.decodeBase64(AESpublickeystring));
            String signedcontent = Base64.encodeBase64String(new SecurityPKI().sign(encryptedcontent, key));
            /**
             * Post Data
             */
            String PostData = GeneralUtilities.PostData_Online(encryptedcontent, signedcontent, "AP04", "", "9230489223014123", "123", new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value(), "T130", CompanySetting.getTaxIdentity());
            //System.out.println("json>:" + json);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
            output = response.getEntity(String.class);

            JSONObject parentjsonObject = new JSONObject(output);
            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");

            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
            String content = dataobjectcontent.getString("content");
            /**
             * Decrypt Response
             */
            String DecryptedContent = SecurityPKI.AESdecrypt(content, Base64.decodeBase64(AESpublickeystring));
            //System.out.println("DecryptedContent:" + DecryptedContent);
            //System.out.println("returnMessage:" + dataobject.getString("returnMessage"));
            if (DecryptedContent.length() == 2) {
                ReturnMsg = dataobject.getString("returnMessage");
            } else {
                //System.out.println("DecryptedContent:" + DecryptedContent);
                //System.out.println("returnMessage:" + dataobject.getString("returnMessage"));
                ReturnMsg = dataobject.getString("returnMessage") + ":DecryptedContent";
                LOGGER.log(Level.INFO, ReturnMsg);
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, output);
            LOGGER.log(Level.ERROR, e);
        }
        return ReturnMsg;
    }

}
