package com.akhvatov.cvsreader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvProcessor {

    private static final String DELIMITER = ";";

    private final int allowedRowsAtOnePage;

    public CsvProcessor(int allowedRowsAtOnePage) {
        this.allowedRowsAtOnePage = allowedRowsAtOnePage;
    }

    public Table process(Stream<String> csv) {
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
        for (int rowIndex = 1, linesSize = lines.size(); rowIndex < linesSize; rowIndex++) {
            final List<String> row = new ArrayList<>();
            row.add(String.format("%d.", rowIndex));
            row.addAll(Arrays.stream(lines.get(rowIndex)).collect(Collectors.toList()));

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
        final List<String> columns = new ArrayList<>();
        columns.add("No.");
        columns.addAll(Arrays.stream(line).collect(Collectors.toList()));
        return columns;
    }
}
