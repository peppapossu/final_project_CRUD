//package org.project.service;
//
//import org.project.models.Group;
//import org.project.models.Student;
//
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class GroupService {
//    private static List<Group> groups= new CopyOnWriteArrayList<>();
//    private static JsonService jsonService;
//
//    public static List<Group> getGroups(){
//        groups = jsonService.getGroups();
//        return groups;
//    }
//    public static boolean addGroup(Group group){
//        boolean result = jsonService.getGroups().add(group);
//        jsonService.saveToFileJson();
//        return result;
//    }
//    public void setJsonService(JsonService jsonService) {
//        this.jsonService = jsonService;
//    }
//}
