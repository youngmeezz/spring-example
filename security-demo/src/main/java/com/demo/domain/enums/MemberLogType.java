package com.demo.domain.enums;

/**
 * @author zacconding
 * @Date 2018-01-03
 * @GitHub : https://github.com/zacscoding
 */
public enum MemberLogType {
    NONE(0, ""),
    READ(1,"READ"),
    WRITE(2,"WRITE"),
    MODIFY(3,"MODIFY"),
    REMOVE(4,"REMOVE");


    private final int intValue;
    private final String stringValue;

    MemberLogType(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public int intVale() {
        return this.intValue;
    }

    public String stringValue() {
        return this.stringValue;
    }

    public static MemberLogType valueOf(int value) {
        switch(value) {
            case 0 :
                return NONE;
            case 1:
                return READ;
            case 2:
                return WRITE;
            case 3:
                return MODIFY;
            case 4:
                return REMOVE;
            default:
                return null;
        }
    }
}
