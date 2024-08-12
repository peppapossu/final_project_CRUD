package org.project.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import org.project.constants.Constants;
import org.project.service.JsonService;
import org.project.service.PropertyService;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        
        JsonService jsonService = new JsonService().fillDBFromJson();
        PropertyService propertyService = new PropertyService();

        servletContext.setAttribute(Constants.PROPERTY_SERVICE, propertyService);
        servletContext.setAttribute(Constants.JSON_DB, jsonService);
        ServletContextListener.super.contextInitialized(sce);
    }
}
