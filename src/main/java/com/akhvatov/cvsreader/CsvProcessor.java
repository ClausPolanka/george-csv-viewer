package com.akhvatov.cvsreader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvProcessor {

    private static final String DELIMITER = ";";

    public Table process(Stream<String> csv, int allowedRowsAtOnePage) {
        final List<String[]> lines = csv.map(line -> line.split(DELIMITER)).collect(Collectors.toList());
        if (isEmptyOrHasOnlyTitle(lines)) {
            return Table.empty();
        }

        final List<String> columns = extractColumn(lines.get(0));
        final List<Page> pages = extractPages(lines, allowedRowsAtOnePage);
        return new Table(columns, pages);
    }

    private List<Page> extractPages(List<String[]> lines, int allowedRowsAtOnePage) {
        int rowsCountAtPage = 0;
        Page page = null;

        final List<Page> pages = new ArrayList<>();
        for (int i = 1, linesSize = lines.size(); i < linesSize; i++) {
            final String[] row = lines.get(i);

            if (rowsCountAtPage == 0) {
                page = new Page();
                pages.add(page);
            }

            page.addRow(row);
            rowsCountAtPage++;

            if (rowsCountAtPage == allowedRowsAtOnePage) {
                rowsCountAtPage = 0;
            }
        }
        return pages;
    }

    private boolean isEmptyOrHasOnlyTitle(List<String[]> lines) {
        return lines.isEmpty() || lines.size() == 1;
    }

    private List<String> extractColumn(String[] line) {
        return Arrays.stream(line).collect(Collectors.toList());
    }
}
