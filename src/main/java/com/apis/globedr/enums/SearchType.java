package com.apis.globedr.enums;

public enum SearchType {
    HOSPITAL("HOSPITAL"),
    CLINIC("CLINIC"),
    PHARMACY("PHARMACY"),
    ORGANIZATION("ORGANIZATION"),
    PROVIDER("PROVIDER"),
    OTHERS("OTHERS"),
    DOCTOR("DOCTOR");

    private final String text;

    SearchType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static SearchType fromText(String text) {
        for (SearchType b : SearchType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
