package com.ssu.strangerws.formallang.utils;

public enum Type {
    real(new String[]{"\\d"}),
    integer(new String[]{"\\d"}),
    identifier(new String[]{"\\w", "\\d"}),
    openBracket(null),
    closeBracket(null),
    space(new String[]{"\\s"}),
    keyWord(null);

    public String[] masks;

    Type(String[] masks){
        this.masks = masks;
    }
}
