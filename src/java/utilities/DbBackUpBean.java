/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import entities.CompanySetting;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author HP
 */
@ManagedBean
@RequestScoped
public class DbBackUpBean {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(DbBackUpBean.class.getName());

//    public static void main(String[] args) {
//        new DbBackUpBean().Backupdbtosql();
//    }
    public void Backupdbtosql(String adbName, String adbUser, String adbPass) {
        try {
            /*NOTE: Creating Database Constraints*/
            //String dbName = "daytoday_sm_branch";
            //String dbUser = "root";
            //String dbPass = "WTLura456";
            String dbUser = adbUser;
            String dbPass = adbPass;
            String dbName = adbName;

            /*NOTE: Creating Path Constraints for folder saving*/
            /*NOTE: Here the backup folder is created for saving inside it*/
            //String folderPath = jarDir + "\\backup";
            String folderPath = "D:\\DBbackup";
            //System.out.println("folderPath: " + folderPath);

            /*NOTE: Creating Folder if it does not exist*/
            File f1 = new File(folderPath);
            f1.mkdir();

            /*NOTE: Creating Path Constraints for backup saving*/
            /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
            String fileName;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            fileName = sdf.format(new CompanySetting().getCURRENT_SERVER_DATE());
            //System.out.println("fileName: " + fileName);
            String savePath = "\"" + folderPath + "\\backup-" + adbName + "-" + fileName + ".sql\"";
            //System.out.println("Save path: " + savePath);

            /*NOTE: Used to create a cmd command*/
            //String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;
            //String executeCmd = "C:\\wamp\\bin\\mysql\\mysql5.6.17\\bin\\mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;
            //String executeCmd = "C:\\wamp\\bin\\mysql\\mysql5.6.17\\bin\\mysqldump -u" + dbUser + " -p" + dbPass + " " + dbName + " -r " + savePath;
            String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " " + dbName + " -r " + savePath;

            /*NOTE: Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                System.out.println("Backup Failure");
            }

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}
