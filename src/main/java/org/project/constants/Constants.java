package org.project.constants;

import lombok.Getter;

import java.time.format.DateTimeFormatter;

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
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String PROPERTY_SERVICE = "propertyService";
}
