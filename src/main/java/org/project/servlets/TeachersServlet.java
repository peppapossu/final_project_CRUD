package org.project.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.project.service.JsonService;
import java.io.IOException;

import static org.project.constants.Constants.ID;
import static org.project.constants.Constants.JSON_DB;
import static org.project.service.ServletService.*;
import static org.project.service.TeacherService.*;

@Slf4j
@WebServlet("/teachers/*")
public class TeachersServlet extends HttpServlet {
    JsonService jsonService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jsonService = (JsonService) config.getServletContext().getAttribute(JSON_DB);
        log.info("TeachersServlet init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String result = generateTeachersList(req,jsonService);
        setJsonResponse(resp);
        resp.getWriter().write(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (addTeacherFromReq(req,jsonService)) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            jsonService.saveToFileJson();
            log.info("Teacher added successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.warn("Teacher not added successfully");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        if (addTeacher(req,jsonService)) {resp.setStatus(HttpServletResponse.SC_OK);
            jsonService.saveToFileJson();
            log.info("Teacher changed successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.warn("Teacher not changed successfully");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter(ID) != null) {
            deleteTeacher(req,jsonService);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            jsonService.saveToFileJson();
            log.info("Teacher deleted successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.warn("Teacher not deleted successfully");
        }
    }
}
