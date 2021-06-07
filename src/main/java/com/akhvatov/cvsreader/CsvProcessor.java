package com.akhvatov.cvsreader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CsvProcessor {

    private static final String DELIMITER = ";";
    private static final int NO_INDEX_TO_SORT = -1;
    private static final int HEADER_ROW_INDEX = 1;

    private final int allowedRowsAtOnePage;

    public CsvProcessor(int allowedRowsAtOnePage) {
        this.allowedRowsAtOnePage = allowedRowsAtOnePage;
    }

    public Table process(Stream<String> csv, String sortColumn) {
        final List<String[]> lines = csv.map(line -> line.split(DELIMITER)).collect(Collectors.toList());

        if (isEmptyOrHasOnlyTitle(lines)) {
            return Table.empty();
        }

        final List<String> columns = extractColumn(lines.get(0));
        final List<Page> pages = extractPages(lines, allowedRowsAtOnePage, findIndexToSort(sortColumn, columns));
        return new Table(columns, pages);
    }

    private List<Page> extractPages(List<String[]> lines, int allowedRowsAtOnePage, int columnIndexToSort) {
        final List<String[]> rowsToProcess = lines.stream().skip(1).collect(Collectors.toList());

        if (columnIndexToSort != NO_INDEX_TO_SORT) {
            rowsToProcess.sort(Comparator.comparing(line -> line[columnIndexToSort]));
        }

        int rowsCountAtPage = 0;
        Page page = null;

        final List<Page> pages = new ArrayList<>();
        for (int rowIndex = 0, linesSize = rowsToProcess.size(); rowIndex < linesSize; rowIndex++) {
            final List<String> row = new ArrayList<>();
            row.add(String.format("%d.", rowIndex + 1));
            row.addAll(Arrays.stream(rowsToProcess.get(rowIndex)).collect(Collectors.toList()));

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

    private int findIndexToSort(String columnToSort, List<String> columnsName) {
        if (columnToSort == null || columnToSort.isEmpty()) {
            return -1;
        }

        return IntStream.range(0, columnsName.size())
                .boxed()
                .filter(columnIndex -> columnsName.get(columnIndex).equalsIgnoreCase(columnToSort))
                .findFirst()
                .map(columnIndex -> columnIndex - 1)
                .orElse(NO_INDEX_TO_SORT);
    }
}
