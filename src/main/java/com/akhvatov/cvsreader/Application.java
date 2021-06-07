package com.akhvatov.cvsreader;

public class Application {

    public static final int DEFAULT_ALLOWED_ROWS_AT_ONE_PAGE = 3;

    public static void main(String[] args) {
        final FileReader fileReader = new FileReader(args[0]);

        final CsvProcessor csvProcessor = new CsvProcessor();
        final Table table = csvProcessor.process(fileReader.readLines(), getAllowedRowsAtOnePage(args));

        final TableRenderer tableRenderer = new TableRenderer();
        final String renderedTable = tableRenderer.render(table, 0);

        System.out.println(renderedTable);
    }

    private static Integer getAllowedRowsAtOnePage(String[] args) {
        return args.length == 2 ? Integer.parseInt(args[1]) : DEFAULT_ALLOWED_ROWS_AT_ONE_PAGE;
    }
}
