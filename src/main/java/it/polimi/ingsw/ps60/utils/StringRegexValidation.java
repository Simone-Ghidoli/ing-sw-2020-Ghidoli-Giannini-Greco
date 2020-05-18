package it.polimi.ingsw.ps60.utils;

import java.util.regex.Pattern;

public class StringRegexValidation {

    private final Pattern regex;

    /**
     * It can validate a string if it respects a pattern
     * @param pattern is a regular grammar
     */
    public StringRegexValidation(String pattern){
        this.regex = Pattern.compile(pattern);
    }

    /**
     *
     * @param stringToCheck is the string that needs to be checked
     * @return true if a string is valid, false otherwise
     */
    public boolean isValid(String stringToCheck){
        return  (regex.matcher(stringToCheck).matches());
    }
}