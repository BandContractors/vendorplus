/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import beans.Cdc_generalBean;
import beans.Parameter_listBean;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import connections.DBConnection;
import java.util.Date;
import utilities.UtilityBean;

public class TaskManager {

    private Timer timer = new Timer();

    public void startTask() {
        try {
            long RepeatAfter = 24 * 60 * 60 * 1000;//1000 mls=1 sec
            DBConnection.readConnectionConfigurations("configurations.ConfigFile");
            new Parameter_listBean().refreshSavedParameterLists();
            //0:server_start,1:first_login,specific_time_e.g_00:00
            String DailySnapshotTime = new Parameter_listBean().getParameter_listByContextNameMemory("SNAPSHOT", "DAILY_SNAPSHOT_TIME").getParameter_value();
            if (DailySnapshotTime.equals("0")) {//server/tomcat start
                //check if it hasnt been taken
                if (new Cdc_generalBean().isTodaySnapshotFound()) {
                    //ignore
                } else {
                    new Cdc_generalBean().takeNewSnapshot_stock();
                }
            } else {//specific_time_e.g 00:00 or not set or invalid time format
                if (new UtilityBean().isTime24Hour(DailySnapshotTime)) {
                    String[] HrMn = DailySnapshotTime.split(":", 2);
                    Date StartTime = new Cdc_generalBean().getStartTime(Integer.parseInt(HrMn[0]), Integer.parseInt(HrMn[1]));
                    timer.schedule(new PeriodicTaskStockSnapshot(), StartTime, RepeatAfter);
                } else {
                    //do nothing
                }
            }
        } catch (FileNotFoundException e) {
        }
    }

    private class PeriodicTaskStockSnapshot extends TimerTask {

        @Override
        public void run() {
            try {
                new Cdc_generalBean().takeNewSnapshot_stock();
            } catch (Exception e) {
            }
        }
    }
}
