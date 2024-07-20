package org.project.models;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.util.List;

@Data
public class Teacher {
    private int id;
    private String name;
    private int age;
    private List<Subject> subjects;
    private static int currentId = 0;

    public Teacher(String name, int age, List<Subject> subjects) {
        this.id = ++currentId;
        this.name = name;
        this.age = age;
        this.subjects = subjects;
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("name", name)
                .append("age", age)
                .append("subjects", subjects)
                .toString();
    }
    public static void setCurrentId(int id){
        currentId = id;
        int i = 0;
    }
}
