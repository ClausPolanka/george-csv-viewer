package com.akhvatov.cvsreader;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CsvProcessorTest {

    static final int ALLOWED_ROWS_AT_ONE_PAGE = 3;

    final CsvProcessor processor = new CsvProcessor(ALLOWED_ROWS_AT_ONE_PAGE);

    @Test
    void shouldExtractColumnNames() {
        // when
        final Table table = processor.process(lines(), null);

        // then
        assertIterableEquals(Arrays.asList("No.", "Name", "Age", "City"), table.getColumnsNames());
    }

    @Test
    void shouldExtractPages() {
        // when
        final Table table = processor.process(lines(), null);

        // then
        final List<Page> pages = table.getPages();
        assertEquals(3, pages.size());

        assertPage(
                Arrays.asList(
                        Arrays.asList("1.", "Peter", "42", "NewYork"),
                        Arrays.asList("2.", "Paul", "57", "London"),
                        Arrays.asList("3.", "Mary", "35", "Munich")
                ),
                pages.get(0)
        );

        assertPage(
                Arrays.asList(
                        Arrays.asList("4.", "Jaques", "66", "Paris"),
                        Arrays.asList("5.", "Yuri", "23", "Moscow"),
                        Arrays.asList("6.", "Stephanie", "47", "Stockholm")
                ),
                pages.get(1)
        );

        assertPage(
                Collections.singletonList(Arrays.asList("7.", "Nadia", "29", "Madrid")),
                pages.get(2)
        );
    }

    @Test
    void shouldSortByColumnName() {
        // when
        final Table table = processor.process(lines(), "name");

        // then
        final List<Page> pages = table.getPages();
        assertEquals(3, pages.size());

        assertPage(
                Arrays.asList(
                        Arrays.asList("1.", "Jaques", "66", "Paris"),
                        Arrays.asList("2.", "Mary", "35", "Munich"),
                        Arrays.asList("3.", "Nadia", "29", "Madrid")

                ),
                pages.get(0)
        );

        assertPage(
                Arrays.asList(
                        Arrays.asList("4.", "Paul", "57", "London"),
                        Arrays.asList("5.", "Peter", "42", "NewYork"),
                        Arrays.asList("6.", "Stephanie", "47", "Stockholm")
                ),
                pages.get(1)
        );

        assertPage(
                Collections.singletonList(Arrays.asList("7.", "Yuri", "23", "Moscow")),
                pages.get(2)
        );
    }

    void assertPage(List<List<String>> expectedRows, Page page) {
        final List<List<String>> rows = page.getRows();
        for (int i = 0; i < expectedRows.size(); i++) {
            assertIterableEquals(expectedRows.get(i), rows.get(i));
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