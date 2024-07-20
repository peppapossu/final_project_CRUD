package org.project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Data
public class Group {
    private String numberName;
    private List<Integer> studentsId;
    private int id;
    private static int currentId = 0;

    public Group(String numberName, List<Integer> studentsId) {
        this.numberName = numberName;
        this.studentsId = studentsId;
        this.id = ++currentId;
    }

    public Group() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("numberName", numberName)
                .append("studentsId", studentsId)
                .append("id", id)
                .toString();
    }
    public static void setCurrentId(int id){
        currentId = id;
    }

}
