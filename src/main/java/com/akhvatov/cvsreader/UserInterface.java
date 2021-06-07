package com.akhvatov.cvsreader;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;
    private final FileReader fileReader;
    private final CsvProcessor csvProcessor;
    private final TableRenderer tableRenderer;

    public UserInterface(
            Scanner scanner,
            FileReader fileReader,
            CsvProcessor csvProcessor,
            TableRenderer tableRenderer
    ) {
        this.scanner = scanner;
        this.fileReader = fileReader;
        this.csvProcessor = csvProcessor;
        this.tableRenderer = tableRenderer;
    }

    public void start() {
        final Table table = csvProcessor.process(fileReader.readLines());
        final String renderedTable = tableRenderer.render(table, 0);
        System.out.println(renderedTable);
    }
}
