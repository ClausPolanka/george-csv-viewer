package com.akhvatov.cvsreader;

import java.util.List;

public class Page {

    private final List<String[]> rows;

    public Page(List<String[]> rows) {
        this.rows = rows;
    }

    public List<String[]> getRows() {
        return rows;
    }
}
