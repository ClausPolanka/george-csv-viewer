package com.akhvatov.cvsreader;

public class Application {

    public static void main(String[] args) {
        final FileReader fileReader = new FileReader(args[0]);

        final CsvProcessor csvProcessor = new CsvProcessor();
        final State state = csvProcessor.process(fileReader.readLines());

        final TableRenderer tableRenderer = new TableRenderer();
        tableRenderer.render(state);
    }
}
