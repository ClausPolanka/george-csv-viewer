package com.akhvatov.cvsreader;

public class Application {

    public static void main(String[] args) {
        final FileReader fileReader = new FileReader(args[0]);

        final CsvProcessor csvProcessor = new CsvProcessor();
        final Table table = csvProcessor.process(fileReader.readLines());

        final TableRenderer tableRenderer = new TableRenderer();
        final String renderedTable = tableRenderer.render(table, 0);

        System.out.println(renderedTable);
    }
}
