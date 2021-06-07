package com.akhvatov.cvsreader;

import java.util.ArrayList;
import java.util.List;

public class Page {

    private final List<List<String>> rows = new ArrayList<>();

    public static Page from(List<List<String>> rows) {
        final Page page = new Page();
        rows.forEach(page::addRow);
        return page;
    }

    public void addRow(List<String> row) {
        this.rows.add(row);
    }

    public List<List<String>> getRows() {
        return rows;
    }
}
