package com.akhvatov.cvsreader;

import java.util.Optional;

public class UserInputParser {

    public Optional<Integer> parseJumpPage(String userInput) {
        if (userInput == null) {
            return Optional.empty();
        }

        final String[] splitInput = userInput.trim().split(" ");
        if (splitInput.length != 2) {
            return Optional.empty();
        }

        try {
            return "J".equalsIgnoreCase(splitInput[0]) ? Optional.of(Integer.parseInt(splitInput[1])) : Optional.empty();
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
