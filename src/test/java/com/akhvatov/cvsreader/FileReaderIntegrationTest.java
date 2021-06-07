package com.akhvatov.cvsreader;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("manual")
class FileReaderIntegrationTest {

    @Test
    void shouldReadFileFromThePath() {
        // given
        final FileReader fileReader = new FileReader("C:\\Users\\alexm\\Downloads\\CSVViewer\\besucher.csv");

        // when
        final Stream<String> streamOfLines = fileReader.readLines();

        // then
        final Optional<String> firstLine = streamOfLines.findFirst();
        assertTrue(firstLine.isPresent());
        assertEquals("Name;Alter;Letzter Besuch;Ort", firstLine.get());
    }
}