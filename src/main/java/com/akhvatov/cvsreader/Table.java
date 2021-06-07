package com.akhvatov.cvsreader;

import java.util.Collections;
import java.util.List;

public class Table {

    private final List<String> columnsNames;
    private final List<Page> pages;

    public static Table empty() {
        return new Table(Collections.emptyList(), Collections.emptyList());
    }

    public Table(List<String> columnsNames, List<Page> pages) {
        this.columnsNames = columnsNames;
        this.pages = pages;
    }

    public List<String> getColumnsNames() {
        return columnsNames;
    }

    public List<Page> getPages() {
        return pages;
    }
}
