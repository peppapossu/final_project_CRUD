package org.project.constants;

import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class Constants {
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String BIRTHDATE = "birthDate";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String GROUP_NUMBER = "groupNumber";
    public static final String ID = "id";
    public static final String ORIGINAL_DB = "data.json";
    public static final String REWRITABLE_DB = "out_data.json";
    public static final String JSON_DB = "DB";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(Locale.ENGLISH);
    public static final String PROPERTY_SERVICE = "propertyService";
    public static final String STUDENTS = "students";
    public static final String TEACHERS = "teachers";
    public static final String GROUPS = "groups";
    public static final String TIMETABLE = "timetable";
}
