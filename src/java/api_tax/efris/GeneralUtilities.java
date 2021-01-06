/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author bajuna
 */
public class GeneralUtilities {
    
    public static String PostData_Online(String content, String signature, String appId, String brn, String dataExchangeId, String deviceMAC, String deviceNo, String interfaceCode, String tin) {
        String PostData = "{\n"
                + "    \"data\": {\n"
                + "        \"content\": \"" + content + "\",\n"
                + "        \"signature\": \"" + signature + "\",\n"
                + "        \"dataDescription\": {\n"
                + "            \"codeType\": \"0\",\n"
                + "            \"encryptCode\": \"0\",\n"
                + "            \"zipCode\": \"0\"\n"
                + "        }\n"
                + "    },\n"
                + "    \"globalInfo\": {\n"
                + "        \"appId\": \"" + appId + "\",\n"
                + "        \"brn\": \"" + brn + "\",\n"
                + "        \"dataExchangeId\": \"" + dataExchangeId + "\",\n"
                + "        \"deviceMAC\": \"" + deviceMAC + "\",\n"
                + "        \"deviceNo\": \"" + deviceNo + "\",\n"
                + "        \"interfaceCode\": \"" + interfaceCode + "\",\n"
                + "        \"requestCode\": \"TP\",\n"
                + "        \"requestTime\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\n"
                + "        \"responseCode\": \"TA\",\n"
                + "        \"taxpayerID\": \"1\",\n"
                + "        \"tin\": \"" + tin + "\",\n"
                + "        \"userName\": \"admin\",\n"
                + "        \"version\": \"1.1.20191201\",\n"
                + "        \"longitude\": \"116.397128\",\n"
                + "        \"latitude\": \"39.916527\",\n"
                + "        \"extendField\": {\n"
                + "        \"responseDateFormat\": \"dd/MM/yyyy\",\n"
                + "        \"responseTimeFormat\": \"dd/MM/yyyy HH:mm:ss\"}\n"
                + "        },\n"
                + "    \"returnStateInfo\": {\n"
                + "        \"returnCode\": \"\",\n"
                + "        \"returnMessage\": \"\"\n"
                + "    }\n"
                + "}";
        return PostData;
    }
    public static String PostData_Offline(String content, String signature, String appId, String brn, String dataExchangeId, String deviceMAC, String deviceNo, String interfaceCode, String tin) {
        String PostData = "{\n"
                + "    \"data\": {\n"
                + "        \"content\": \"" + content + "\",\n"
                + "        \"signature\": \"" + signature + "\",\n"
                + "        \"dataDescription\": {\n"
                + "            \"codeType\": \"0\",\n"
                + "            \"encryptCode\": \"0\",\n"
                + "            \"zipCode\": \"0\"\n"
                + "        }\n"
                + "    },\n"
                + "    \"globalInfo\": {\n"
                + "        \"appId\": \"" + appId + "\",\n"
                + "        \"brn\": \"" + brn + "\",\n"
                + "        \"dataExchangeId\": \"" + dataExchangeId + "\",\n"
                + "        \"deviceMAC\": \"" + deviceMAC + "\",\n"
                + "        \"deviceNo\": \"" + deviceNo + "\",\n"
                + "        \"interfaceCode\": \"" + interfaceCode + "\",\n"
                + "        \"requestCode\": \"TP\",\n"
                + "        \"requestTime\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\n"
                + "        \"responseCode\": \"TA\",\n"
                + "        \"taxpayerID\": \"1\",\n"
                + "        \"tin\": \"" + tin + "\",\n"
                + "        \"userName\": \"admin\",\n"
                + "        \"version\": \"1.1.20191201\",\n"
                + "        \"longitude\": \"116.397128\",\n"
                + "        \"latitude\": \"39.916527\",\n"
                + "        \"extendField\": {\n"
                + "        \"responseDateFormat\": \"dd/MM/yyyy\",\n"
                + "        \"responseTimeFormat\": \"dd/MM/yyyy HH:mm:ss\"}\n"
                + "        },\n"
                + "    \"returnStateInfo\": {\n"
                + "        \"returnCode\": \"\",\n"
                + "        \"returnMessage\": \"\"\n"
                + "    }\n"
                + "}";
        return PostData;
    }
}
