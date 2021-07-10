package com.atanasvasil.api.mycardocs;

import bg.forcar.api.utils.Identifier;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class IdentifierTest {

    private Identifier identifier;

    @Before
    public void setUp() {
        identifier = new Identifier();
    }

    @Test
    public void isUserId_TextContainsLetter_ReturnFalse() {
        String text = "654d";

        assertFalse(identifier.isUserId(text));
    }

    @Test
    public void isUserId_TextContainsOnlyDigits_ReturnTrue() {
        String text = "89574";

        assertTrue(identifier.isUserId(text));
    }

    @Test
    public void isUserId_TextContainsOnlyLetters_ReturnFalse() {
        String text = "test@gmail.com";

        assertFalse(identifier.isUserId(text));
    }
}
