/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.innerclasses.GoodsCommodity;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.GzipUtils;

/**
 *
 * @author bajuna
 */
public class T124 {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//
//        try {
//            String returnCode = "";
//            String returnMessage = "";
//            String AntifakeCode = "";
//            String InvoiceNo = "";
//
//            String json = "{\n"
//                    + "	\"pageNo\": \"1\",\n"
//                    + "	\"pageSize\": \"99\"\n"
//                    + "}";
//
//            AntifakeCode = "";
//            InvoiceNo = "";
//            returnCode = "";
//            returnMessage = "";
//
////            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
////                    new javax.net.ssl.HostnameVerifier() {
////
////                public boolean verify(String hostname,
////                        javax.net.ssl.SSLSession sslSession) {
////                    return hostname.equals("127.0.0.1");
////                }
////            });
//            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
//            //WebResource webResource = client.resource("https://efristest.ura.go.ug/efrisws/ws/taapp/getInformation");
//            //System.setProperty("javax.net.ssl.trustStore", "C:/TaxControlService/tcs_tomcat/conf/p12.p12");
//            //System.setProperty("javax.net.ssl.trustStorePassword", "bajuna");
//            WebResource webResource = client.resource("http://127.0.0.1:9880/efristcs/ws/tcsapp/getInformation");
//
//            System.out.println(Base64.encodeBase64String(json.getBytes("UTF-8")));
//
//            String PostData = PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", "TCS443cd31997972232", "T124", "1001068009");
//            //String PostData = "{\"data\":{\"content\":\"ewoJInBhZ2VObyI6ICIxIiwKCSJwYWdlU2l6ZSI6ICIxMCIKfQ==\",\"signature\":\"\",\"dataDescription\":{\"codeType\":\"0\",\"encryptCode\":\"0\",\"zipCode\":\"0\"}},\"globalInfo\":{\"appId\":\"AP04\",\"brn\":\"\",\"dataExchangeId\":\"20201027103536.0410800\",\"deviceMAC\":\"1\",\"deviceNo\":\"TCS443cd31997972232\",\"extendField\":{\"responseDateFormat\": \"dd/MM/yyyy\",\"responseTimeFormat\": \"dd/MM/yyyy HH:mm:ss\"},\"longitude\":\"0\",\"latitude\":\"0\",\"interfaceCode\":\"T124\",\"requestCode\":\"TP\",\"requestTime\":\"2020-11-02 23:39:36\",\"responseCode\":\"TA\",\"taxpayerID\":\"1\",\"tin\":\"1001068009\",\"userName\":\"admin\",\"version\":\"1.1.20191201\"},\"returnStateInfo\":{\"returnCode\":\"\",\"returnMessage\":\"\"}}";
//            //System.out.println(PostData);
//            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, PostData);
//            String output = response.getEntity(String.class);
//            //System.out.println(output);
//
//            JSONObject parentjsonObject = new JSONObject(output);
//            JSONObject dataobject = parentjsonObject.getJSONObject("returnStateInfo");
//            returnCode = dataobject.getString("returnCode");
//            returnMessage = dataobject.getString("returnMessage");
//            JSONObject dataobjectcontent = parentjsonObject.getJSONObject("data");
//            String content = dataobjectcontent.getString("content");
//            JSONObject dataDescription = dataobjectcontent.getJSONObject("dataDescription");
//            String zipCode = dataDescription.getString("zipCode");
//
//            String DecryptedContent = "";
//            if (zipCode.equals("0")) {
//                DecryptedContent = new String(Base64.decodeBase64(content));
//            } else {
//                byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
//                DecryptedContent = new String(str);
//            }
//
//            /**
//             * Get total page size
//             */
//            JSONObject parentbasicInformationjsonObject = new JSONObject(DecryptedContent);
//            JSONObject page = parentbasicInformationjsonObject.getJSONObject("page");
//            int counter = page.getInt("pageCount");
//            /**
//             * End Get total page size
//             */
//
//            XSSFWorkbook workbook2 = new XSSFWorkbook();
//            XSSFSheet sheet2 = workbook2.createSheet("Category");// creating a blank sheet
//            int rownum = 0;
//
//            Row row = sheet2.createRow(rownum++);
//            Cell cell = row.createCell(0);
//            cell.setCellValue("commodityCategoryCode");
//            cell = row.createCell(1);
//            cell.setCellValue("parentCode");
//            cell = row.createCell(2);
//            cell.setCellValue("commodityCategoryName");
//            cell = row.createCell(3);
//            cell.setCellValue("commodityCategoryLevel");
//            cell = row.createCell(4);
//            cell.setCellValue("rate");
//            cell = row.createCell(5);
//            cell.setCellValue("isLeafNode");
//            cell = row.createCell(6);
//            cell.setCellValue("serviceMark");
//            cell = row.createCell(7);
//            cell.setCellValue("isZeroRate");
//            cell = row.createCell(8);
//            cell.setCellValue("zeroRateStartDate");
//            cell = row.createCell(9);
//            cell.setCellValue("zeroRateEndDate");
//            cell = row.createCell(10);
//            cell.setCellValue("isExempt");
//            cell = row.createCell(11);
//            cell.setCellValue("exemptRateStartDate");
//            cell = row.createCell(12);
//            cell.setCellValue("exemptRateEndDate");
//            cell = row.createCell(13);
//            cell.setCellValue("enableStatusCode");
//            cell = row.createCell(14);
//            cell.setCellValue("exclusion");
//
//            for (int x = 1; x <= counter; x++) {
//                json = "{\n"
//                        + "	\"pageNo\": \"" + counter + "\",\n"
//                        + "	\"pageSize\": \"99\"\n"
//                        + "}";
//                System.out.println(x);
//                PostData = PostData_Offline(Base64.encodeBase64String(json.getBytes("UTF-8")), "", "AP04", "", "9230489223014123", "123", "TCS443cd31997972232", "T124", "1001068009");
//                //String PostData = "{\"data\":{\"content\":\"ewoJInBhZ2VObyI6ICIxIiwKCSJwYWdlU2l6ZSI6ICIxMCIKfQ==\",\"signature\":\"\",\"dataDescription\":{\"codeType\":\"0\",\"encryptCode\":\"0\",\"zipCode\":\"0\"}},\"globalInfo\":{\"appId\":\"AP04\",\"brn\":\"\",\"dataExchangeId\":\"20201027103536.0410800\",\"deviceMAC\":\"1\",\"deviceNo\":\"TCS443cd31997972232\",\"extendField\":{\"responseDateFormat\": \"dd/MM/yyyy\",\"responseTimeFormat\": \"dd/MM/yyyy HH:mm:ss\"},\"longitude\":\"0\",\"latitude\":\"0\",\"interfaceCode\":\"T124\",\"requestCode\":\"TP\",\"requestTime\":\"2020-11-02 23:39:36\",\"responseCode\":\"TA\",\"taxpayerID\":\"1\",\"tin\":\"1001068009\",\"userName\":\"admin\",\"version\":\"1.1.20191201\"},\"returnStateInfo\":{\"returnCode\":\"\",\"returnMessage\":\"\"}}";
//                //System.out.println(PostData);
//                response = webResource.type("application/json").post(ClientResponse.class, PostData);
//                output = response.getEntity(String.class);
//                //System.out.println(output);
//
//                parentjsonObject = new JSONObject(output);
//                dataobjectcontent = parentjsonObject.getJSONObject("data");
//                content = dataobjectcontent.getString("content");
//
//                dataDescription = dataobjectcontent.getJSONObject("dataDescription");
//                zipCode = dataDescription.getString("zipCode");
//
//                DecryptedContent = "";
//                if (zipCode.equals("0")) {
//                    DecryptedContent = new String(Base64.decodeBase64(content));
//                } else {
//                    byte[] str = GzipUtils.decompress(Base64.decodeBase64(content));
//                    DecryptedContent = new String(str);
//                }
//
//                JSONObject jSONObject = new JSONObject(DecryptedContent);
//                JSONArray array = jSONObject.getJSONArray("records");
//                List<GoodsCommodity> arList = new ArrayList<>();
//                for (int i = 0; i < array.length(); i++) {
//                    Gson g = new Gson();
//                    GoodsCommodity temp = g.fromJson(array.get(i).toString(), GoodsCommodity.class);
//                    arList.add(temp);
//                }
//                for (GoodsCommodity goodsCommodity : arList) {
//                    row = sheet2.createRow(rownum++);
//                    cell = row.createCell(0);
//                    cell.setCellValue(goodsCommodity.getCommodityCategoryCode());
//                    cell = row.createCell(1);
//                    cell.setCellValue(goodsCommodity.getParentCode());
//                    cell = row.createCell(2);
//                    cell.setCellValue(goodsCommodity.getCommodityCategoryName());
//                    cell = row.createCell(3);
//                    cell.setCellValue(goodsCommodity.getCommodityCategoryLevel());
//                    cell = row.createCell(4);
//                    cell.setCellValue(goodsCommodity.getRate());
//                    cell = row.createCell(5);
//                    cell.setCellValue(goodsCommodity.getIsLeafNode());
//                    cell = row.createCell(6);
//                    cell.setCellValue(goodsCommodity.getServiceMark());
//                    cell = row.createCell(7);
//                    cell.setCellValue(goodsCommodity.getIsZeroRate());
//                    cell = row.createCell(8);
//                    cell.setCellValue(goodsCommodity.getZeroRateStartDate());
//                    cell = row.createCell(9);
//                    cell.setCellValue(goodsCommodity.getZeroRateEndDate());
//                    cell = row.createCell(10);
//                    cell.setCellValue(goodsCommodity.getIsExempt());
//                    cell = row.createCell(11);
//                    cell.setCellValue(goodsCommodity.getExemptRateStartDate());
//                    cell = row.createCell(13);
//                    cell.setCellValue(goodsCommodity.getEnableStatusCode());
//                    cell = row.createCell(14);
//                    cell.setCellValue(goodsCommodity.getExclusion());
//                }
//
//            }
//            FileOutputStream out = new FileOutputStream(new File("D:\\Commodity.xlsx")); // file name with path
//            workbook2.write(out);
//            out.close();
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(T124.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(T124.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(T124.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(T124.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public static String PostData_Offline(String content, String signature, String appId, String brn, String dataExchangeId, String deviceMAC, String deviceNo, String interfaceCode, String tin) {
//        String PostData = "{\n"
//                + "    \"data\": {\n"
//                + "        \"content\": \"" + content + "\",\n"
//                + "        \"signature\": \"" + signature + "\",\n"
//                + "        \"dataDescription\": {\n"
//                + "            \"codeType\": \"0\",\n"
//                + "            \"encryptCode\": \"0\",\n"
//                + "            \"zipCode\": \"0\"\n"
//                + "        }\n"
//                + "    },\n"
//                + "    \"globalInfo\": {\n"
//                + "        \"appId\": \"" + appId + "\",\n"
//                + "        \"brn\": \"" + brn + "\",\n"
//                + "        \"dataExchangeId\": \"" + dataExchangeId + "\",\n"
//                + "        \"deviceMAC\": \"" + deviceMAC + "\",\n"
//                + "        \"deviceNo\": \"" + deviceNo + "\",\n"
//                + "        \"interfaceCode\": \"" + interfaceCode + "\",\n"
//                + "        \"requestCode\": \"TP\",\n"
//                + "        \"requestTime\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\n"
//                + "        \"responseCode\": \"TA\",\n"
//                + "        \"taxpayerID\": \"1\",\n"
//                + "        \"tin\": \"" + tin + "\",\n"
//                + "        \"userName\": \"admin\",\n"
//                + "        \"version\": \"1.1.20191201\",\n"
//                + "        \"longitude\": \"116.397128\",\n"
//                + "        \"latitude\": \"39.916527\",\n"
//                + "        \"extendField\": {\n"
//                + "        \"responseDateFormat\": \"dd/MM/yyyy\",\n"
//                + "        \"responseTimeFormat\": \"dd/MM/yyyy HH:mm:ss\"}\n"
//                + "        },\n"
//                + "    \"returnStateInfo\": {\n"
//                + "        \"returnCode\": \"\",\n"
//                + "        \"returnMessage\": \"\"\n"
//                + "    }\n"
//                + "}";
//        return PostData;
//    }
}
