package org.project.service;

import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.project.models.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.project.constants.Constants.*;
import static org.project.service.ServletService.getBody;

public class StudentService {
    public static void deleteStudent(HttpServletRequest req, JsonService jsonService) {
        int id = Integer.parseInt(req.getParameter(ID));
        jsonService.getStudents().stream()
                .filter(student -> id==student.getId())
                .findFirst()
                .ifPresent(student -> { jsonService.getStudents().remove(student); });
    }
    public static void editStudent(HttpServletRequest req, JsonService jsonService) {
        int id = Integer.parseInt(req.getParameter(ID).toString())-1;
        jsonService.getStudents().stream()
                .filter(student -> id==student.getId())
                .findFirst()
                .ifPresent(student -> {
                    Student filteredStudent = jsonService.getStudents().get(student.getId());
                    setNewData(req,filteredStudent);
                });
    }
    public static boolean setNewData(HttpServletRequest req, Student student) {
        JSONObject jObj = new JSONObject(getBody(req));
        Iterator<String> it = jObj.keys();

        student.setFirstName(jObj.getString(it.next()));
        student.setLastName(jObj.getString(it.next()));
        student.setPhoneNumber(jObj.getString(it.next()));
        student.setBirthDate(getLocalDate(jObj.getString(it.next())));
        JsonService jsonService = (JsonService) req.getServletContext().getAttribute(JSON_DB);
        jsonService.saveToFileJson();
        return true;
    }

    public static LocalDate getLocalDate(String date) {
        return LocalDate.parse(date, FORMATTER_BIRTHDATE);
    }
    public static boolean addStudentFromReq(HttpServletRequest req, JsonService jsonService) {
        JSONObject jObj = new JSONObject(getBody(req));

        Iterator<String> it = jObj.keys();

        String firstName = jObj.getString(it.next());
        String lastName = jObj.getString(it.next());
        String phoneNumber = jObj.getString(it.next());
        String birthDateString = jObj.getString(it.next());
        LocalDate birthDate = getLocalDate(birthDateString);
        return jsonService.getStudents().add(new Student(firstName,lastName,birthDate,phoneNumber));
    }

    public static String generateStudentsList(HttpServletRequest req, JsonService jsonService) {
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
    public static String getStudentById(HttpServletRequest req, JsonService jsonService) {
        String result;
        int id = Integer.parseInt(req.getRequestURI().split("/")[2]);
        result = jsonService.getStudents().get(id-1).toString();
        return result;
    }


}
