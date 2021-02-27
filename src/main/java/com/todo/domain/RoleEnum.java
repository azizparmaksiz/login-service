package com.todo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum RoleEnum {
    ADMIN,
    GUEST;

    @JsonCreator
    public static RoleEnum fromText(String text) {
        try {
            if (!StringUtils.isEmpty(text))
                return valueOf(text);
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }
}
