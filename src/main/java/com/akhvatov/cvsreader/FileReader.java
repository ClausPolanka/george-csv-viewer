package com.akhvatov.cvsreader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Convenient class for reading all lines from file as a stream
 */
public class FileReader {

    private final String filePath;

    public FileReader(String filePath) {
        this.filePath = filePath;
    }

    public Stream<String> readLines() {
        try {
            return Files.lines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error while reading %s file", filePath));
        }
    }
}
