package com.eteshis.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum LangCodeEnum {

    EN(1, "EN"),

    TR(2, "TR"),

    FR(3, "FR"),

    RU(4, "RU"),

    AE(5, "AE");

    private int id;
    private String nameOfEnum;
    private final static Logger logger = LoggerFactory.getLogger(LangCodeEnum.class);

    LangCodeEnum(int id, String nameOfEnum) {
        this.id = id;
        this.nameOfEnum = nameOfEnum;

    }


    public int getId() {
        return id;
    }
    @JsonValue
    public String getNameOfEnum() {
        return nameOfEnum;
    }



    public static LangCodeEnum fromText(String text) {
        try {

            if(text!=null){
                return valueOf(text.toUpperCase());
            }
            logger.warn("Lan Code is null, default EN returned");
            return EN;
        } catch (Exception e) {
            logger.warn(e.getMessage() + " default EN returned");
            return EN;
        }
    }
}
