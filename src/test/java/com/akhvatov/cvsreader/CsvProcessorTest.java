package com.akhvatov.cvsreader;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CsvProcessorTest {

    static final int ALLOWED_ROWS_AT_ONE_PAGE = 3;

    final CsvProcessor processor = new CsvProcessor();

    @Test
    void shouldExtractColumnNames() {
        // when
        final Table table = processor.process(lines(), ALLOWED_ROWS_AT_ONE_PAGE);

        // then
        assertIterableEquals(Arrays.asList("Name", "Age", "City"), table.getColumnsNames());
    }

    @Test
    void shouldExtractPages() {
        // when
        final Table table = processor.process(lines(), ALLOWED_ROWS_AT_ONE_PAGE);

        // then
        final List<Page> pages = table.getPages();
        assertEquals(3, pages.size());

        assertPage(
                Arrays.asList(
                        new String[]{"Peter", "42", "NewYork"},
                        new String[]{"Paul", "57", "London"},
                        new String[]{"Mary", "35", "Munich"}
                ),
                pages.get(0)
        );

        assertPage(
                Arrays.asList(
                        new String[]{"Jaques", "66", "Paris"},
                        new String[]{"Yuri", "23", "Moscow"},
                        new String[]{"Stephanie", "47", "Stockholm"}
                ),
                pages.get(1)
        );

        assertPage(
                Collections.singletonList(new String[]{"Nadia", "29", "Madrid"}),
                pages.get(2)
        );
    }

    void assertPage(List<String[]> expectedRows, Page page) {
        final List<String[]> rows = page.getRows();
        for (int i = 0; i < expectedRows.size(); i++) {
            assertArrayEquals(expectedRows.get(i), rows.get(i));
        }
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