package org.project.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.project.models.Subject;
import org.project.models.Teacher;
import org.project.service.JsonService;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.project.constants.Constants.ID;
import static org.project.constants.Constants.JSON_DB;

@WebServlet("/teachers/*")
public class TeachersServlet extends HttpServlet {
    JsonService jsonService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jsonService = (JsonService) config.getServletContext().getAttribute(JSON_DB);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = generateTeachersList(req);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type", "application/json");
        resp.getWriter().write(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (addTeacherFromReq(req)) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (addTeacher(req)) {resp.setStatus(HttpServletResponse.SC_OK);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter(ID) != null) {
            int id = Integer.parseInt(req.getParameter(ID));
            jsonService.getTeachers().stream()
                    .filter(t -> id==t.getId())
                    .findFirst()
                    .ifPresent(t -> { jsonService.getTeachers().remove(t); });
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
    private static String generateTeachersList(HttpServletRequest req) {
        JsonService jsonService = (JsonService) req.getServletContext().getAttribute(JSON_DB);
        List<Teacher> teachers = jsonService.getTeachers();
        if (req.getParameter("name") != null) {
            List<Teacher> teacherList = new ArrayList<>();
            teachers.stream().filter(teacher -> teacher.getName().equals(req.getParameter("name"))).forEach(teacher -> teacherList.add(teacher));
            teachers = teacherList;
        }
        if (req.getParameter("age") != null) {
            List<Teacher> teacherList = new ArrayList<>();
            teachers.stream().filter(teacher -> teacher.getAge()==Integer.parseInt(req.getParameter("age"))).forEach(teacher -> teacherList.remove(teacher));
            teachers = teacherList;
        }
        return teachers.toString();
    }
    private boolean addTeacherFromReq(HttpServletRequest req) {
        String getBody = getBody(req);
        JSONObject jObj = new JSONObject(getBody);

        Iterator<String> it = jObj.keys();

        JSONArray subjects = jObj.getJSONArray(it.next());
        List<Subject> subjectList = new ArrayList<>();
        String name = jObj.getString(it.next());
        int age = jObj.getInt(it.next());
        for (var value:subjects){
            subjectList.add(Subject.valueOf(value.toString()));
        }
        // List.of(subjects).stream().forEach(s -> subjectList.add(Subject.valueOf(s.toString())));
        return jsonService.getTeachers().add(new Teacher(name,age,subjectList));
    }
    @SneakyThrows
    private String getBody(HttpServletRequest req) {
        String jsonBody = new BufferedReader(new InputStreamReader(req.getInputStream())).lines().collect(
                Collectors.joining("\n"));
        if (jsonBody == null || jsonBody.trim().length() == 0) {
            // return error that jsonBody is empty
        }
        return jsonBody;
    }
    private boolean setNewData(HttpServletRequest req, Teacher teacher) {
        JSONObject jObj = new JSONObject(getBody(req));
        Iterator<String> it = jObj.keys();

        JSONArray subjects = jObj.getJSONArray(it.next());
        List<Subject> subjectList = new ArrayList<>();
        String name = jObj.getString(it.next());
        int age = jObj.getInt(it.next());
        for (var value:subjects) {
            subjectList.add(Subject.valueOf(value.toString()));
        }
        teacher.setName(name);
        teacher.setAge(age);
        teacher.setSubjects(subjectList);
        JsonService jsonService = (JsonService) req.getServletContext().getAttribute(JSON_DB);
        jsonService.saveToFileJson();
        return true;

    }
    private boolean addTeacher(HttpServletRequest req) {

        if (req.getParameter(ID) != null) {
            int id = Integer.parseInt(req.getParameter(ID).toString())-1;
             jsonService.getTeachers().stream()
                    .filter(t -> id==t.getId())
                    .findFirst()
                    .ifPresent(t -> {
                        Teacher filteredTeacher = jsonService.getTeachers().get(t.getId());
                        setNewData(req,filteredTeacher);
                    });
            return true;}
        else return false;
    }
}
