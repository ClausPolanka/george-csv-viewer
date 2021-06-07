package com.akhvatov.cvsreader;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CsvProcessorTest {

    final CsvProcessor processor = new CsvProcessor();

    @Test
    void shouldCalculateLongestWidth() {
        // when
        final State state = processor.process(lines());

        // then
        assertEquals(9, state.getWidth());
    }

    @Test
    void shouldExtractColumnNames() {
        // when
        final State state = processor.process(lines());

        // then
        assertIterableEquals(Arrays.asList("Name", "Age", "City"), state.getColumnsNames());
    }

    @Test
    void shouldExtractPages() {
        // when
        final State state = processor.process(lines());

        // then
        final List<Page> pages = state.getPages();
        assertEquals(3, pages.size());

        assertIterableEquals(
                Arrays.asList(
                        new String[]{"Peter", "42", "NewYork"},
                        new String[]{"Paul", "57", "London"},
                        new String[]{"Mary", "35", "Munich"}
                ),
                pages.get(0).getRows()
        );

        assertIterableEquals(
                Arrays.asList(
                        new String[]{"Jaques", "66", "Paris"},
                        new String[]{"Yuri", "23", "Moscow"},
                        new String[]{"Stephanie", "47", "Stockholm"}
                ),
                pages.get(1).getRows()
        );

        assertIterableEquals(
                Collections.singletonList(new String[]{"Nadia", "29", "Madrid"}),
                pages.get(1).getRows()
        );
    }

    static Stream<String> lines() {
        return Stream.of(
                "Name;Age;City",
                "Peter;42;NewYork",
                "Paul;57;London",
                "Mary;35;Munich",
                "Jaques;66;Paris",
                "Yuri;23;Moscow",
                "Stephanie;47;Stockholm",
                "Nadia;29;Madrid"
        );
    }
}