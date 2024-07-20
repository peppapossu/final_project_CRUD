//package org.project.service;
//
//
//import org.project.models.TimeTable;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class TimeTableService {
//    private static List<TimeTable> timeTables= new CopyOnWriteArrayList<>();
//    private static JsonService jsonService;
//
//    public static List<TimeTable> getTimeTables() {
//        timeTables = jsonService.getTimeTables();
//        return timeTables;
//    }
//    public static boolean addTimeTable (TimeTable timeTable) {
//        boolean result = jsonService.getTimeTables().add(timeTable);
//        jsonService.saveToFileJson();
//        return result;
//    }
//    public void setJsonService(JsonService jsonService) {
//        this.jsonService = jsonService;
//    }
//    public LocalDateTime parseLocalDateTime(String date) {
//        return LocalDateTime.parse(date);
//    }
//}
