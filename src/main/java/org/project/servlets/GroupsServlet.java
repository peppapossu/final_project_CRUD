package org.project.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.project.models.Group;
import org.project.models.GroupDTO;
import org.project.models.Student;
import org.project.service.JsonService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.project.constants.Constants.JSON_DB;

@Slf4j
@WebServlet("/groups/*")
public class GroupsServlet extends HttpServlet {
    JsonService jsonService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jsonService = (JsonService) config.getServletContext().getAttribute(JSON_DB);
        log.debug("Group servlet initialized");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String result;
        if (req.getRequestURI().startsWith("/groups/")) {
            result = getGroupById(req);
        } else {result = generateGroupsList(req);}
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type", "application/json");
        //resp.setStatus(resp.SC_OK);
        resp.getWriter().write(result);
    }
    private String getGroupById(HttpServletRequest req) {
        String result;
        int id = Integer.parseInt(req.getRequestURI().split("/")[2]);
        result = getGroupDTO(jsonService.getGroups().get(id-1)).toString();
        return result;
    }
    private String generateGroupsList(HttpServletRequest req) {
        //List<Group> groups = jsonService.getGroups();
        List<GroupDTO> groups = generateGroups();
        if (req.getParameterMap().size() == 0) {
            return groups.toString();
        }
        return groups.toString();
    }

    private List<GroupDTO> generateGroups() {
        List<Group> groups = jsonService.getGroups();
        List<GroupDTO> result = new ArrayList<>();
        for (Group group : groups) {
            result.add(getGroupDTO(group));
        }
        return result;
    }

    private GroupDTO getGroupDTO(Group group) {
        List<Student> students = new ArrayList<>();
        for (var id: group.getStudentsId()){
            students.add(jsonService.getStudentsMap().get(id));
        }
        return new GroupDTO(group.getNumberName(),group.getId(),students);
    }
}
