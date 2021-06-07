package com.akhvatov.cvsreader;

import java.util.ArrayList;
import java.util.List;

public class Page {

    private final List<String[]> rows = new ArrayList<>();

    public static Page from(List<String[]> rows) {
        final Page page = new Page();
        rows.forEach(page::addRow);
        return page;
    }

    public void addRow(String[] row) {
        this.rows.add(row);
    }

    public List<String[]> getRows() {
        return rows;
    }
}
