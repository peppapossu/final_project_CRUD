package org.project.service;

import jakarta.servlet.http.HttpServletRequest;
import org.project.models.Group;
import org.project.models.GroupDTO;
import org.project.models.Student;

import java.util.ArrayList;
import java.util.List;

public class GroupService {
    public static String getGroupById(HttpServletRequest req,JsonService jsonService) {
        String result;
        int id = Integer.parseInt(req.getRequestURI().split("/")[2]);
        result = getGroupDTO(jsonService.getGroups().get(id-1),jsonService).toString();
        return result;
    }
    public static GroupDTO getGroupDTO(Group group, JsonService jsonService) {
        List<Student> students = new ArrayList<>();
        for (var id: group.getStudentsId()){
            students.add(jsonService.getStudentsMap().get(id));
        }
        return new GroupDTO(group.getNumberName(),group.getId(),students);
    }
    public static List<GroupDTO> generateGroups(JsonService jsonService) {
        List<Group> groups = jsonService.getGroups();
        List<GroupDTO> result = new ArrayList<>();
        for (Group group : groups) {
            result.add(getGroupDTO(group,jsonService));
        }
        return result;
    }
    public static String generateGroupsList(HttpServletRequest req,JsonService jsonService) {
        List<GroupDTO> groups = generateGroups(jsonService);
        if (req.getParameterMap().size() == 0) {
            return groups.toString();
        }
        //дальше реализация не перенесена
        return groups.toString();
    }
}
