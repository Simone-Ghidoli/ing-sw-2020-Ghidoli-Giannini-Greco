package it.polimi.ingsw.ps60.utils;

import it.polimi.ingsw.ps60.GlobalVariables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringRegexValidationTest {

    StringRegexValidation stringRegexValidation;

    @After
    public void tearDown(){
        stringRegexValidation = null;
    }

    @Test
    public void isValidIpv4Test() {
        stringRegexValidation = new StringRegexValidation(GlobalVariables.StringPatterns.IPv4.getPattern());

        assertTrue(stringRegexValidation.isValid("192.168.1.1"));
        assertFalse(stringRegexValidation.isValid("192.1681.1.1"));
    }

    @Test
    public void isValidNickname() {
        stringRegexValidation = new StringRegexValidation(GlobalVariables.StringPatterns.Nickname.getPattern());

        assertTrue(stringRegexValidation.isValid("Vincenzo"));
        assertTrue(stringRegexValidation.isValid("Vincenzo22"));
        assertFalse(stringRegexValidation.isValid("VincenzoGreco"));
        assertTrue(stringRegexValidation.isValid("Vin22Gre22"));
    }

    @Test
    public void isValidDate() {
        stringRegexValidation = new StringRegexValidation(GlobalVariables.StringPatterns.Date.getPattern());

        assertTrue(stringRegexValidation.isValid("1998/08/27"));
        assertFalse(stringRegexValidation.isValid("1998/08/7"));
        assertFalse(stringRegexValidation.isValid("27/8/1998"));

    }
}
