//package org.project.service;
//
//
//import org.project.models.Teacher;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class TeacherService {
//    private static List<Teacher> teachers= new CopyOnWriteArrayList<>();
//    private static JsonService jsonService;
//
//    public static List<Teacher> getTeachers() {
//        teachers = jsonService.getTeachers();
//        return teachers;
//    }
//    public static boolean addTeacher(Teacher teacher) {
//        boolean result = jsonService.getTeachers().add(teacher);
//        jsonService.saveToFileJson();
//        return result;
//    }
//    public void setJsonService(JsonService jsonService) {
//        this.jsonService = jsonService;
//    }
//}
