package it.polimi.ingsw.ps60.utils;

import it.polimi.ingsw.ps60.GlobalVariables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringRegexValidationTest {

    StringRegexValidation stringRegexValidation;

    public void tearDown() throws Exception {
        stringRegexValidation = null;
    }

    @Test
    public void isValidIpv4Test() {
        stringRegexValidation = new StringRegexValidation(GlobalVariables.StringPatterns.IPv4.getPattern());

        assertTrue(stringRegexValidation.isValid("192.168.1.1"));
    }

    @Test
    public void isNotValidIpv4Test() {
        stringRegexValidation = new StringRegexValidation(GlobalVariables.StringPatterns.IPv4.getPattern());

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
}
