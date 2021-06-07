package com.akhvatov.cvsreader;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvProcessor {

    public static final String DELIMITER = ";";

    public State process(Stream<String> csv) {
        final List<String> lines = csv.collect(Collectors.toList());
        if (lines.isEmpty()) {
            return State.empty();
        }

        final int maxColumnWidth = lines.stream().map(line -> line.split(DELIMITER))
                .flatMap(Arrays::stream)
                .mapToInt(String::length)
                .max()
                .orElse(0);


        final List<String> columns = processColumn(lines.get(0).split(DELIMITER));

        //csv.skip(1).map(line -> line.split(DELIMITER));


        return new State(maxColumnWidth, columns, Collections.emptyList());
    }

    private List<String> processColumn(String[] line) {
        return Arrays.stream(line).collect(Collectors.toList());
    }

    private void processRow(String[] line) {

    }
}
