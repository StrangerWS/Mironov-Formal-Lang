package com.ssu.strangerws.formallang.utils;

public enum Type {
    real(new String[]{"\\d"}, "REAL"),
    integer(new String[]{"\\d"}, "INT"),
    identifier(new String[]{"\\w", "\\d"}, "ID"),
    openBracket(null, "OB"),
    closeBracket(null, "CB"),
    space(new String[]{"\\s"}, "SP"),
    keyWord(null, "KW");

    public String[] masks;
    public String name;

    Type(String[] masks, String name){
        this.masks = masks;
        this.name = name;
    }
}
