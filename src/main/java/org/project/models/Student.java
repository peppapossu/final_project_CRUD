package org.project.models;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.time.LocalDate;


@Data
public class Student {
    private int id;
    //
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private static int currentId = 0;

    public Student(String firstName, String lastName, LocalDate birthDate, String phoneNumber) {
        this.id = ++currentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public Student() {
    }


    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("birthDate", birthDate)
                .append("phoneNumber", phoneNumber)
                .toString();
    }

    private void setBirthDate(String birthDate) {
        this.birthDate = LocalDate.parse(birthDate);
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public static void setCurrentId(int id){
        currentId = id;
    }
}
