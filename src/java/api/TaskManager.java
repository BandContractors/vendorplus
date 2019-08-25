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

public class TaskManager {

    private Timer timer = new Timer();

    public void startTask() {
        try {
            DBConnection.readConnectionConfigurations("configurations.ConfigFile");
            new Parameter_listBean().refreshSavedParameterLists();
            Date StartTime = new Cdc_generalBean().getStartTime(23, 55);
            long RepeatAfter = 24 * 60 * 60 * 1000;//1000 mls=1 sec
            timer.schedule(new PeriodicTaskStockSnapshot(), StartTime, RepeatAfter);
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
