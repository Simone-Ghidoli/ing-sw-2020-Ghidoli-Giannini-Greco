package it.polimi.ingsw.ps60.utils;

import java.util.regex.Pattern;

public class StringRegexValidation {

    Pattern regex;

    public StringRegexValidation(String pattern){
        this.regex = Pattern.compile(pattern);
    }

    public boolean isValid(String stringToCheck){
        return  (regex.matcher(stringToCheck).matches());
    }
}