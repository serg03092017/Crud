package com.app.DBentities;


public enum Purchase {
    TV, SMARTPHONE, JUICER, HEADPHONES, KEYBOARD;

    public String value() {
        return name();
    }

    public static Purchase fromValue(String v) {
        return valueOf(v);
    }
}
