package com.akhvatov.cvsreader;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Name|Age|City    |
 * -----+---+--------+
 * Peter|42 |New York|
 * Paul |57 |London  |
 * Mary |35 |Munich  |
 * N(ext page, P(revious page, F(irst page, L(ast page, eX(it
 */
public class TableRenderer {

    private static final String COLUMN_SEPARATOR = "|";
    private static final String PLUS_SYMBOL = "+";

    public String render(Table table, int pageIndexToRender) {
        if (table.getPages().isEmpty()) {
            return "";
        }

        final StringBuilder stringBuilder = new StringBuilder();

        final Page page = table.getPages().get(pageIndexToRender);
        final List<String> header = table.getColumnsNames();
        final List<List<String>> rows = page.getRows();

        // calculate width
        final Map<Integer, Integer> widthByColumnIndex = calculateWidthMap(page, header);

        // render header
        renderHeader(header, widthByColumnIndex, stringBuilder);

        // render each row
        rows.forEach(row -> renderRow(row, widthByColumnIndex, stringBuilder));

        stringBuilder.append("N(ext page, P(revious page, F(irst page, L(ast page, eX(it");
        return stringBuilder.toString();
    }

    private void renderHeader(
            List<String> headers,
            Map<Integer, Integer> widthByColumnIndex,
            StringBuilder stringBuilder
    ) {
        // header row
        IntStream.range(0, headers.size()).boxed()
                .forEach(index -> stringBuilder
                        .append(appendSymbol(headers.get(index), widthByColumnIndex.get(index), " "))
                        .append(COLUMN_SEPARATOR));

        stringBuilder.append("\n");

        // header break line
        IntStream.range(0, headers.size()).boxed()
                .forEach(index -> stringBuilder
                        .append(appendSymbol("-", widthByColumnIndex.get(index), "-"))
                        .append(PLUS_SYMBOL));

        stringBuilder.append("\n");
    }

    private void renderRow(
            List<String> row,
            Map<Integer, Integer> widthByColumnIndex,
            StringBuilder stringBuilder
    ) {
        IntStream.range(0, row.size()).boxed()
                .forEach(index -> stringBuilder
                        .append(appendSymbol(row.get(index), widthByColumnIndex.get(index), " "))
                        .append(COLUMN_SEPARATOR));

        stringBuilder.append("\n");
    }

    private String appendSymbol(String value, int width, String symbol) {
        if (value.length() == width) {
            return value;
        }

        final StringBuilder stringBuilder = new StringBuilder(value);
        final int spacesRequired = width - value.length();
        IntStream.range(0, spacesRequired).boxed().forEach(index -> stringBuilder.append(symbol));
        return stringBuilder.toString();
    }

    private Map<Integer, Integer> calculateWidthMap(Page page, List<String> header) {
        return IntStream.range(0, header.size())
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        columnIndex -> calculateWidthPerColumn(columnIndex, page, header.get(columnIndex))
                ));
    }

    private int calculateWidthPerColumn(int columnIndex, Page page, String headerName) {
        int maxWidth = page.getRows().stream()
                .map(columns -> columns.get(columnIndex))
                .mapToInt(String::length)
                .max()
                .orElse(0);

        return Math.max(headerName.length(), maxWidth);
    }
}
