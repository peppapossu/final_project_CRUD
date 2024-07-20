package org.project.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import lombok.extern.slf4j.Slf4j;
import org.project.service.JsonService;

import static org.project.constants.Constants.JSON_DB;

@Slf4j
@WebServlet("/group/*")
public class GroupsServlet extends HttpServlet {
    JsonService jsonService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jsonService = (JsonService) config.getServletContext().getAttribute(JSON_DB);
        log.debug("Group servlet initialized");
    }

}
