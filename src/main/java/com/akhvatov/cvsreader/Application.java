package com.akhvatov.cvsreader;

import java.util.Scanner;

public class Application {

    public static final int DEFAULT_ALLOWED_ROWS_AT_ONE_PAGE = 3;

    public static void main(String[] args) {
        final FileReader fileReader = new FileReader(args[0]);
        final CsvProcessor csvProcessor = new CsvProcessor(getAllowedRowsAtOnePage(args));
        final TableRenderer tableRenderer = new TableRenderer();

        final UserInterface userInterface = new UserInterface(
                new Scanner(System.in), fileReader, csvProcessor, tableRenderer
        );
        userInterface.start();
    }

    private static Integer getAllowedRowsAtOnePage(String[] args) {
        return args.length == 2 ? Integer.parseInt(args[1]) : DEFAULT_ALLOWED_ROWS_AT_ONE_PAGE;
    }
}
