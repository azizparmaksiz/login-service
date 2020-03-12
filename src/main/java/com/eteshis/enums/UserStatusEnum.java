package com.eteshis.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by developer on 7.02.2017.
 */
public enum UserStatusEnum {
    ACTIVE(1, "ACTIVE"), INACTIVE(2, "INACTIVE"),
    SUSPENDED(3, "SUSPENDED"), NOT_VERIFIED(4, "NOT_VERIFIED"),
    CLOSED(5, "CLOSED");

    private int id;
    private String nameOfEnum;

    UserStatusEnum(int id, String message) {
        this.id = id;
        this.nameOfEnum = message;
    }


    public int getId() {
        return id;
    }

    @JsonValue
    public String getNameOfEnum() {
        return nameOfEnum;
    }


}
