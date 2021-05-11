/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import api_sm_bi.CheckApiBean;
import api_sm_bi.SMbiBean;
import beans.Cdc_generalBean;
import beans.Parameter_listBean;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import utilities.UtilityBean;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class TaskManager {

    static Logger LOGGER = Logger.getLogger(TaskManager.class.getName());
    private Timer timer = new Timer();

    public void startTask() {
        try {
            //A) Branch level tasks such as snapshots, etc.
            long RepeatAfter = 24 * 60 * 60 * 1000;//1000 mls=1 sec
            //DBConnection.readConnectionConfigurations("configurations.ConfigFile");
            //new Parameter_listBean().refreshSavedParameterLists();
            //0:server_start,1:first_login,specific_time_e.g_00:00
            String DailySnapshotTime = "";
            try {
                DailySnapshotTime = new Parameter_listBean().getParameter_listByContextName("SNAPSHOT", "DAILY_SNAPSHOT_TIME").getParameter_value();
            } catch (Exception e) {
            }
            //System.out.println("DailySnapshotTime:" + DailySnapshotTime);
            if (DailySnapshotTime.equals("0")) {//server/tomcat start
                //STOCK-check if it hasnt been taken
                if (new Cdc_generalBean().isTodaySnapshotFoundSTOCK()) {
                    //ignore
                } else {
                    new Cdc_generalBean().takeNewSnapshot_stock();
                }
                //CASH-check if it hasnt been taken
                if (new Cdc_generalBean().isTodaySnapshotFoundCASH()) {
                    //ignore
                } else {
                    new Cdc_generalBean().takeNewSnapshot_cash();
                }
            } else {//specific_time_e.g 00:00 or not set or invalid time format
                if (new UtilityBean().isTime24Hour(DailySnapshotTime)) {
                    String[] HrMn = DailySnapshotTime.split(":", 2);
                    Date StartTime = new Cdc_generalBean().getStartTime(Integer.parseInt(HrMn[0]), Integer.parseInt(HrMn[1]));
                    //job for both STOCK and CASH snapshots
                    timer.schedule(new PeriodicTaskSnapshot(), StartTime, RepeatAfter);
                } else {
                    //do nothing
                }
            }
            //A) Inter-Branch level tasks such as SMbi.
            long RepeatAfterMinutes = 0;
            try {
                RepeatAfterMinutes = Long.parseLong(new Parameter_listBean().getParameter_listByContextName("API", "API_SMBI_SYNC_JOB_REPEAT_AFTER").getParameter_value());
            } catch (Exception e) {
            }
            //System.out.println("RepeatAfterMinutes:" + RepeatAfterMinutes);
            //1000 mls=1 sec
            long DelayMilliseconds = 1000;
            long RepeatAfterMilliseconds = 1000 * 60 * RepeatAfterMinutes;
            if (RepeatAfterMinutes > 0 && new Parameter_listBean().getParameter_listByContextName("API", "API_SMBI_URL").getParameter_value().length() > 0) {
                //System.out.println("SCHEDULE:" + new Date().toString());
                timer.schedule(new PeriodicTaskSyncSmBi(), DelayMilliseconds, RepeatAfterMilliseconds);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    private class PeriodicTaskSnapshot extends TimerTask {

        @Override
        public void run() {
            try {
                new Cdc_generalBean().takeNewSnapshot_stock();
                new Cdc_generalBean().takeNewSnapshot_cash();
            } catch (Exception e) {
            }
        }
    }

    private class PeriodicTaskSyncSmBi extends TimerTask {

        @Override
        public void run() {
            try {
                //System.out.println("TASK:" + new Date().toString());
                if (new CheckApiBean().IsSmBiAvailable() && new Parameter_listBean().getParameter_listByContextName("API", "API_SMBI_URL").getParameter_value().length() > 0) {
                    new SMbiBean().syncSMbiCall();
                }
            } catch (Exception e) {
            }
        }
    }
}
