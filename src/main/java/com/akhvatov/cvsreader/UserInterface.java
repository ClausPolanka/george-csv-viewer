package com.akhvatov.cvsreader;

import java.util.Scanner;

public class UserInterface {

    private static final int FIRST_PAGE = 1;

    private final Scanner scanner;
    private final UserInputParser userInputParser;
    private final FileReader fileReader;
    private final CsvProcessor csvProcessor;
    private final TableRenderer tableRenderer;

    public UserInterface(
            Scanner scanner,
            UserInputParser userInputParser,
            FileReader fileReader,
            CsvProcessor csvProcessor,
            TableRenderer tableRenderer
    ) {
        this.scanner = scanner;
        this.userInputParser = userInputParser;
        this.fileReader = fileReader;
        this.csvProcessor = csvProcessor;
        this.tableRenderer = tableRenderer;
    }

    public void start() {
        renderPage(FIRST_PAGE);

        while (scanner.hasNext()) {
            final String userInput = scanner.nextLine();
            userInputParser.parseJumpPage(userInput).ifPresent(this::renderPage);
        }
    }

    private void renderPage(int page) {
        final Table table = csvProcessor.process(fileReader.readLines());
        final String renderedTable = tableRenderer.render(table, page);
        System.out.println(renderedTable);
    }
}
