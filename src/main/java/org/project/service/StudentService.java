//package org.project.service;
//
//
//import org.project.models.Student;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class StudentService {
//    private static List<Student> students= new CopyOnWriteArrayList<>();
//    private static JsonService jsonService;
//
//    public static List<Student> getStudents(){
//        students = jsonService.getStudents();
//        return students;
//    }
//    public static boolean addStudent(Student student){
//        boolean result = jsonService.getStudents().add(student);
//        jsonService.saveToFileJson();
//        return result;
//    }
//    public void setJsonService(JsonService jsonService) {
//        this.jsonService = jsonService;
//    }
//}
