package com.ogidazepam.expense_tracker.model;

public enum Category {
    GROCERIES("G"),
    LEISURE("L"),
    ELECTRONICS("E"),
    UTILITIES("U"),
    CLOTHING("C"),
    HEALTH("H"),
    OTHERS("O");

    private final String code;

    Category(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Category formCode(String code){
        for(Category c : values()){
            if(c.getCode().equals(code)){
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
