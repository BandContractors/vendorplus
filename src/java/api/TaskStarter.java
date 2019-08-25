/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;

/**
 * @author Wence Benda Twesigye
 */
@SuppressWarnings("serial")
public class TaskStarter extends HttpServlet {

    @Override
    public void init() throws ServletException {
        TaskManager manager = new TaskManager();
        manager.startTask();
    }
}
