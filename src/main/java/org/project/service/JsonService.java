package org.project.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.*;
import org.project.models.Group;
import org.project.models.Student;
import org.project.models.Teacher;
import org.project.models.TimeTable;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.project.constants.Constants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JsonService {
    private ArrayList<Student> students;
    private Map<Integer,Student> studentsMap;
    private ArrayList<Teacher> teachers;
    private ArrayList<Group> groups;
    private ArrayList<TimeTable> timeTables;

    @SneakyThrows
    public JsonService fillDBFromJson(){
        File file = pathToDataFile();
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//
        om.registerModule(new JavaTimeModule());//
        JsonService jsonService = om.readValue(file, JsonService.class);
        students = jsonService.getStudents();
        teachers = jsonService.getTeachers();
        groups = jsonService.getGroups();
        timeTables = jsonService.getTimeTables();
        //setJsonServiceToClasses(jsonService);
        generateMaps(jsonService);
        setCurrentsIdToClasses();
        return jsonService;
    }

    private void setCurrentsIdToClasses() {
        Student.setCurrentId(getStudents().size());
        Teacher.setCurrentId(getTeachers().size());
        TimeTable.setCurrentId(getTimeTables().size());
        Group.setCurrentId(getGroups().size());
    }

    @SneakyThrows
    private File pathToDataFile(){
        return new File ((Paths.get(JsonService.class.getClassLoader().getResource(REWRITABLE_DB).toURI())).toString());
    }

    @SneakyThrows
    public void saveToFileJson(){
        StringBuilder data = new StringBuilder();
        data.append("{\n");
        data.append("\"students\" : \n");
        data.append(students.toString()).append(",\n");
        data.append("\"teachers\" : \n");
        data.append(teachers.toString()).append(",\n");
        data.append("\"groups\" : \n");
        data.append(groups.toString()).append(",\n");
        data.append("\"timeTables\" : \n");
        data.append(timeTables.toString()).append("\n");
        data.append("}\n");
        File fileToSave = new File ((Paths.get(JsonService.class.getClassLoader().getResource(REWRITABLE_DB).toURI())).toString());
        FileOutputStream fos = new FileOutputStream(fileToSave);
        fos.write(data.toString().getBytes());
        fos.close();
        File targetFile = new File("/Users/admin/Downloads/project-maven/tree/Servlet_project_2/src/main/resources/out_data.json");
        if (targetFile.exists()) {
        Files.copy(fileToSave.toPath(),targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);}
    }

    private void generateMaps(JsonService jsonService){
        generateStudentsMap(jsonService);
    }

    private void generateStudentsMap(JsonService jsonService) {
        jsonService.studentsMap = new HashMap<>();
        for (Student student : students) {
            jsonService.studentsMap.put(student.getId(), student);
        }
    }
}
