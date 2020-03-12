package com.eteshis.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomerTypeEnum {

    DOCTOR(1, "DOCTOR"),
    PATIENT(2, "PATIENT"),
    HEALTH_INSURANCE(3, "HEALTH_INSURANCE"),
    HOSPITAL(4, "HOSPITAL"),
    INTERPRETER(5, "INTERPRETER");

    private int id;
    private String name;

    CustomerTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    @JsonValue
    public String getName() {
        return name;
    }

    public static CustomerTypeEnum fromText(String text) {
        try {
            return valueOf(text);
        } catch (Exception e) {
            return null;
        }
    }
}
