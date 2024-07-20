package org.project.servlets;

import jakarta.servlet.ServletConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.project.models.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.service.JsonService;
import org.slf4j.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import static org.project.constants.Constants.*;
@Slf4j
@WebServlet("/" + STUDENTS + "/*")
public class StudentsServlet extends HttpServlet {
    JsonService jsonService;
     //Logger logger = LoggerFactory.getLogger(StudentsServlet.class.getName());
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jsonService = (JsonService) config.getServletContext().getAttribute(JSON_DB);
        log.info("Students servlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String result;
        if (req.getRequestURI().startsWith("/" + STUDENTS + "/")) {
            result = getStudentById(req);
        } else {result = generateStudentsList(req);}
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type", "application/json");
        //resp.setStatus(resp.SC_OK);
        resp.getWriter().write(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (addStudentFromReq(req)) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            log.info("Student added");
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.warn("Wrong request, student not added");

    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter(ID) != null) {
            int id = Integer.parseInt(req.getParameter(ID));
            jsonService.getStudents().stream()
                    .filter(student -> id==student.getId())
                    .findFirst()
                    .ifPresent(student -> { jsonService.getStudents().remove(student); });
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            log.debug("Student deleted");
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.warn("Wrong request, user not deleted");
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter(ID) != null) {
            int id = Integer.parseInt(req.getParameter(ID).toString())-1;
            jsonService.getStudents().stream()
                    .filter(student -> id==student.getId())
                    .findFirst()
                    .ifPresent(student -> {
                        Student filteredStudent = jsonService.getStudents().get(student.getId());
                        setNewData(req,filteredStudent);
                    });
            resp.setStatus(HttpServletResponse.SC_OK);
            log.debug("Student updated");
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.warn("Wrong request, user not updated");
    }


    private boolean addStudentFromReq(HttpServletRequest req) {
        JSONObject jObj = new JSONObject(getBody(req));

        Iterator<String> it = jObj.keys();

        String firstName = jObj.getString(it.next());
        String lastName = jObj.getString(it.next());
        String phoneNumber = jObj.getString(it.next());
        String birthDateString = jObj.getString(it.next());
        LocalDate birthDate = getLocalDate(birthDateString);
        return jsonService.getStudents().add(new Student(firstName,lastName,birthDate,phoneNumber));
    }
    private static LocalDate getLocalDate(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    @SneakyThrows
    private String getBody(HttpServletRequest req) {
        String jsonBody = new BufferedReader(new InputStreamReader(req.getInputStream())).lines().collect(
                Collectors.joining("\n"));
        if (jsonBody == null || jsonBody.trim().length() == 0) {
            log.error("Empty body");
            throw new RuntimeException("Empty body");
        }
        return jsonBody;
    }
    private String generateStudentsList(HttpServletRequest req) {
        List<Student> students = jsonService.getStudents();

        if (req.getParameterMap().size()==0) {
            return students.toString();
        }

        //скорее всего это все можно описать стримом просто используя 4 раза метод .filter
        //но я не разобрался как пропускать метод если параметр null
        if (req.getParameter(FIRSTNAME) != null) {
            List<Student> filteredStudents = new ArrayList<>();
            students.stream()
                    .filter(student -> student.getFirstName().equalsIgnoreCase(req.getParameter(FIRSTNAME)))
                    .forEach(student -> filteredStudents.add(student));
            students = filteredStudents;
        }
        if (req.getParameter(LASTNAME) != null) {
            List<Student> filteredStudents = new ArrayList<>();
            students.stream()
                    .filter(student -> student.getLastName().equalsIgnoreCase(req.getParameter(LASTNAME)))
                    .forEach(student -> filteredStudents.add(student));
            students = filteredStudents;
        }
        if (req.getParameter(BIRTHDATE) != null) {
            List<Student> filteredStudents = new ArrayList<>();
            students.stream()
                    .filter(student -> student.getBirthDate().equals(req.getParameter(BIRTHDATE)))
                    .forEach(student -> filteredStudents.add(student));
            students = filteredStudents;
        }
        if (req.getParameter(PHONE_NUMBER) != null) {
            List<Student> filteredStudents = new ArrayList<>();
            students.stream()
                    .filter(student -> student.getPhoneNumber().equalsIgnoreCase(req.getParameter(PHONE_NUMBER)))
                    .forEach(student -> filteredStudents.add(student));
            students = filteredStudents;
        }
        return students.toString();
    }
    private boolean setNewData(HttpServletRequest req, Student student) {
        JSONObject jObj = new JSONObject(getBody(req));
        Iterator<String> it = jObj.keys();

        student.setFirstName(jObj.getString(it.next()));
        student.setLastName(jObj.getString(it.next()));
        student.setPhoneNumber(jObj.getString(it.next()));
        student.setBirthDate(getLocalDate(jObj.getString(it.next())));
        //student.setName(student.getFirstName()+student.getLastName());
        JsonService jsonService = (JsonService) req.getServletContext().getAttribute(JSON_DB);
        jsonService.saveToFileJson();
        return true;
    }
    private String getStudentById(HttpServletRequest req) {
        String result;
        int id = Integer.parseInt(req.getRequestURI().split("/")[2]);
        result = jsonService.getStudents().get(id-1).toString();
        return result;
    }
}
