package com.eteshis.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderEnum {

    MALE(1, "MALE"),

    FEMALE(2, "FEMALE"),

    ALL(3, "ALL"),

    OTHERS(4, "CLOSED");

    private int id;
    private String message;

    GenderEnum(int id, String message) {
        this.id = id;
        this.message = message;
    }


    public int getId() {
        return id;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }

    public static GenderEnum fromText(String text) {
        try {
            return valueOf(text);
        } catch (Exception var2) {
            return null;
        }
    }

}
