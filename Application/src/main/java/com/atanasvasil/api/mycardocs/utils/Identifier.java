package com.atanasvasil.api.mycardocs.utils;

public class Identifier {

    public boolean isUserId(String identifier) {
        try {
            Long.parseLong(identifier);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
