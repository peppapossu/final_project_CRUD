package org.project.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.project.service.JsonService;
import org.project.service.ServletService;
import java.io.IOException;
import static org.project.constants.Constants.JSON_DB;
import static org.project.service.GroupService.*;

@Slf4j
@WebServlet("/groups/*")
public class GroupsServlet extends HttpServlet {
    JsonService jsonService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jsonService = (JsonService) config.getServletContext().getAttribute(JSON_DB);
        log.debug("Group servlet initialized");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String result;
        if (req.getRequestURI().startsWith("/groups/")) {
            result = getGroupById(req, jsonService);
        } else {result = generateGroupsList(req,jsonService);}
        ServletService.setJsonResponse(resp);
        resp.getWriter().write(result);
    }






}
