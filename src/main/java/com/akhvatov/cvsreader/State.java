package com.akhvatov.cvsreader;

import java.util.Collections;
import java.util.List;

public class State {

    private final int width;
    private final List<String> columnsNames;
    private final List<Page> pages;

    public static State empty() {
        return new State(0, Collections.emptyList(), Collections.emptyList());
    }

    public State(int width, List<String> columnsNames, List<Page> pages) {
        this.width = width;
        this.columnsNames = columnsNames;
        this.pages = pages;
    }

    public int getWidth() {
        return width;
    }

    public List<String> getColumnsNames() {
        return columnsNames;
    }

    public List<Page> getPages() {
        return pages;
    }
}
