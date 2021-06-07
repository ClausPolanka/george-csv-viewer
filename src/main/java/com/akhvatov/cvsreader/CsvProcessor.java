package com.akhvatov.cvsreader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvProcessor {

    public static final String DELIMITER = ";";

    public Table process(Stream<String> csv) {
        final List<String[]> lines = csv.map(line -> line.split(DELIMITER)).collect(Collectors.toList());
        if (isEmptyOrHasOnlyTitle(lines)) {
            return Table.empty();
        }

        final List<String> columns = processColumn(lines.get(0));

        int rowsCountAtPage = 0;
        Page page = new Page();
        final List<Page> pages = new ArrayList<>();

        for (int i = 1, linesSize = lines.size(); i < linesSize; i++) {
            final String[] row = lines.get(i);


            if (rowsCountAtPage == 0) {
                page = new Page();
                pages.add(page);
            }

            page.addRow(row);
            rowsCountAtPage++;

            if (rowsCountAtPage == 3) {
                rowsCountAtPage = 0;
            }
        }

        return new Table(columns, pages);
    }

    private boolean isEmptyOrHasOnlyTitle(List<String[]> lines) {
        return lines.isEmpty() || lines.size() == 1;
    }

    private List<String> processColumn(String[] line) {
        return Arrays.stream(line).collect(Collectors.toList());
    }
}
