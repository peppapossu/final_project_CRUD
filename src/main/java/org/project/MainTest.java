package org.project;

import org.project.models.Subject;
import org.project.service.JsonService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        formatter = formatter.withLocale( Locale.ENGLISH );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
//        LocalDate birthDate = LocalDate.parse("2005-10-12", formatter);
//        System.out.println(birthDate);
        //тест базы
        JsonService jsonService = initDB();
        //  if (JsonService.getStudentsList(jsonService) != null) {JsonService.getStudentsList(jsonService).forEach(System.out::println);}
        jsonService.getTeachers().forEach(System.out::println);
        jsonService.getGroups().forEach(System.out::println);
        jsonService.getTimeTables().forEach(System.out::println);

//        String enumStr = "MATH";
//        List<Subject> list = new ArrayList<>();
//        list.add(Subject.valueOf(enumStr));
//        System.out.println(list.size());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        System.out.println(LocalDateTime.now().format(formatter));

          //  System.out.println(LocalDateTime.parse("2024-07-10 12:15", formatter).format(formatter));
    }

    private static JsonService initDB() {
        JsonService jsonService = new JsonService();
        jsonService= jsonService.fillDBFromJson();
        jsonService.saveToFileJson();
        return jsonService;
    }
}
