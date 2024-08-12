package org.project.service;

import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.project.models.Subject;
import org.project.models.Teacher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.project.constants.Constants.ID;
import static org.project.service.ServletService.getBody;

public class TeacherService {
    public static void deleteTeacher(HttpServletRequest req, JsonService jsonService) {
        int id = Integer.parseInt(req.getParameter(ID));
        jsonService.getTeachers().stream()
                .filter(t -> id==t.getId())
                .findFirst()
                .ifPresent(t -> jsonService.getTeachers().remove(t));
    }

    public static String generateTeachersList(HttpServletRequest req,JsonService jsonService) {
        List<Teacher> teachers = jsonService.getTeachers();
        if (req.getParameter("name") != null) {
            List<Teacher> teacherList = new ArrayList<>();
            teachers.stream().filter(teacher -> teacher.getName().equals(req.getParameter("name"))).forEach(teacherList::add);
            teachers = teacherList;
        }
        if (req.getParameter("age") != null) {
            List<Teacher> teacherList = new ArrayList<>();
            teachers.stream().filter(teacher -> teacher.getAge()==Integer.parseInt(req.getParameter("age"))).forEach(teacherList::add);
            teachers = teacherList;
        }
        return teachers.toString();
    }

    public static boolean addTeacherFromReq(HttpServletRequest req, JsonService jsonService) {
        JSONObject jObj = new JSONObject(getBody(req));
        Iterator<String> it = jObj.keys();
        JSONArray subjects = jObj.getJSONArray(it.next());
        List<Subject> subjectList = new ArrayList<>();
        String name = jObj.getString(it.next());
        int age = jObj.getInt(it.next());
        for (var value:subjects){
            subjectList.add(Subject.valueOf(value.toString()));
        }
        return jsonService.getTeachers().add(new Teacher(name,age,subjectList));
    }

    public static boolean setNewData(HttpServletRequest req, Teacher teacher) {
        JSONObject jObj = new JSONObject(getBody(req));
        Iterator<String> it = jObj.keys();
        JSONArray subjects = jObj.getJSONArray(it.next());
        List<Subject> subjectList = new ArrayList<>();
        teacher.setName(jObj.getString(it.next()));
        teacher.setAge(jObj.getInt(it.next()));
        for (var value:subjects) {
            subjectList.add(Subject.valueOf(value.toString()));
        }
        teacher.setSubjects(subjectList);
        return true;
    }

    public static boolean addTeacher(HttpServletRequest req, JsonService jsonService) {

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
