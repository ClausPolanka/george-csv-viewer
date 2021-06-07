package com.akhvatov.cvsreader;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *  Name|Age|City    |
 * -----+---+--------+
 * Peter|42 |New York|
 * Paul |57 |London  |
 * Mary |35 |Munich  |
 * N(ext page, P(revious page, F(irst page, L(ast page, eX(it
 */
public class TableRenderer {

    public String render(Table table, int pageIndexToRender) {
        final StringBuilder stringBuilder = new StringBuilder();

        renderPage(pageIndexToRender, table);

        stringBuilder.append("N(ext page, P(revious page, F(irst page, L(ast page, eX(it");
        return stringBuilder.toString();
    }

    private void renderPage(int pageIndex, Table table) {
        final Page page = table.getPages().get(pageIndex);
        final List<String> columnsNames = table.getColumnsNames();

        final List<String[]> rows = page.getRows();
        calculateWidthPerColumn(0, page, columnsNames);

    }

    private void renderRow() {

    }

    private int calculateWidthPerColumn(int columnIndex, Page page, List<String> columnsNames) {
        int maxWidth = page.getRows().stream()
                .map(columns -> columns[columnIndex])
                .mapToInt(String::length)
                .max()
                .orElse(0);

        return Math.max(columnsNames.get(columnIndex).length(), maxWidth);
    }
}
