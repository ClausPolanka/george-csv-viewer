package com.akhvatov.cvsreader;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserInputParserTest {

    final UserInputParser parser = new UserInputParser();

    @ParameterizedTest
    @ValueSource(strings = {"J 1", "j 1"})
    void shouldParseJumpValue(String userInput) {
        // when
        final Optional<Integer> pageIndex = parser.parseJumpPage(userInput);

        // then
        assertTrue(pageIndex.isPresent());
        assertEquals(1, pageIndex.get());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"J1", "j_1", "j b", "j "})
    void shouldNotParseIncorrectInput(String userInput) {
        // when
        final Optional<Integer> pageIndex = parser.parseJumpPage(userInput);

        // then
        assertFalse(pageIndex.isPresent());
    }
}